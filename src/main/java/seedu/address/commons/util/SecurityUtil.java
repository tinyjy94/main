package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;


/**
 * Contains encryption and decryption functions for files
 */
public class SecurityUtil {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final int AES_Key_Size = 128;
    private static String password = "dummypass";

    /**
     *
     */
    public static void main(String[] args) throws IOException, InvalidKeySpecException {
        File infile = new File("data/movieplanner.xml");
        File outfile = new File("data/movieplanner.xml");
        encrypt(infile, outfile, password);
        decrypt(infile, outfile, password);
    }

    /**
     * Encrypts the given file using AES key using password given
     */
    public static void encrypt(File infile, File outfile, String password) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Key aesKey = generateKey(password);

            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            processFile(cipher, infile, outfile);

        } catch (InvalidKeyException ike) {
            logger.severe("Invalid key length provided " + StringUtil.getDetails(ike));
            throw new AssertionError("Invalid key length.");
        } catch (NoSuchAlgorithmException nsae) {
            logger.severe("Invalid algorithm provided " + StringUtil.getDetails(nsae));
            throw new AssertionError("Invalid algorithm.");
        } catch (NoSuchPaddingException bpe) {
            logger.severe("Invalid padding provided " + StringUtil.getDetails(bpe));
            throw new AssertionError("Invalid padding.");
        }
    }

    /**
     * Decrypts the given file using AES key using password given.
     */
    public static void decrypt(File inputFile, File outfile, String password) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Key aesKey = generateKey(password);

            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            processFile(cipher, outfile, inputFile);
        } catch (InvalidKeyException ike) {
            logger.severe("Invalid key length provided " + StringUtil.getDetails(ike));
            throw new AssertionError("Invalid key length.");
        } catch (NoSuchAlgorithmException nsae) {
            logger.severe("Invalid algorithm provided " + StringUtil.getDetails(nsae));
            throw new AssertionError("Invalid algorithm.");
        } catch (NoSuchPaddingException bpe) {
            logger.severe("Invalid padding provided " + StringUtil.getDetails(bpe));
            throw new AssertionError("Invalid padding.");
        }
    }

    /**
     * Encrypt or decrypt the {@code inputFile} and writes out into {@code outputFile} based on cipher given.
     */
    private static void processFile(Cipher cipher, File inputFile, File outputFile) throws IOException {

        try {
            byte[] inputByteArray = new byte[(int) inputFile.length()];
            FileInputStream fis = new FileInputStream(inputFile); //create file input stream
            fis.read(inputByteArray); //reads from fis to byte[]

            byte[] outputBytes = cipher.doFinal(inputByteArray); //encrypt or decrypt the byte[]
            FileOutputStream fos = new FileOutputStream(outputFile);

            fos.write(outputBytes);
            fis.close();
            fos.close();

        } catch (BadPaddingException bpe) {
            logger.severe("Bad padding provided" + StringUtil.getDetails(bpe));
            throw new AssertionError("Bad padding provided");
        } catch (IllegalBlockSizeException ibse) {
            logger.info("File is in plain text, no decryption required." + StringUtil.getDetails(ibse));
        }
    }

    /**
     * Generate a secret AES key
     */
    public static Key generateKey(String password) {
        try {
            byte[] nb = new byte[16];
            KeySpec spec = new PBEKeySpec(password.toCharArray(), nb, 65536, AES_Key_Size);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey tmp = factory.generateSecret(spec);
            return new SecretKeySpec(tmp.getEncoded(), "AES");

        } catch (NoSuchAlgorithmException nsae) {
            logger.severe("Invalid algorithm provided " + StringUtil.getDetails(nsae));
            throw new AssertionError("Invalid algorithm.");
        } catch (InvalidKeySpecException ikse) {
            logger.severe("Invalid key specifications provided " + StringUtil.getDetails(ikse));
            throw new AssertionError("Invalid key specifications.");
        }

    }
}
