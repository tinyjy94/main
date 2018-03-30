package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

import com.google.common.eventbus.Subscribe;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.EncryptionRequestEvent;

/**
 * Contains encryption and decryption functions for files
 */
public class SecurityUtil {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final int AES_Key_Size = 128;
    private static final int iteration = 65536;
/*
    public static void encrypt(String filepath) throws IOException{
        encrypt(new File(filepath));
    }

    /**
     * Encrypts the given file using AES key using Key given.
     */
    public static void encrypt(File infile, Key password) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, password);
            processFile(cipher, infile);

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
     * Decrypts the given file using AES key using Key given.
     */
    public static void decrypt(File inputFile, Key password) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, password);
            processFile(cipher, inputFile);
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
     * Encrypts or decrypts the {@code inputFile} and write out into the same {@code inputFile} based on cipher given.
     */
    private static void processFile(Cipher cipher, File inputFile) throws IOException {

        try {
            byte[] inputByteArray = new byte[(int) inputFile.length()];
            FileInputStream fis = new FileInputStream(inputFile); //create file input stream
            fis.read(inputByteArray); //reads from fis to byte[]

            byte[] outputBytes = cipher.doFinal(inputByteArray); //encrypt or decrypt the byte[]
            FileOutputStream fos = new FileOutputStream(inputFile);
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
            byte[] inputByte = new byte[16];
            //byte[] salt = generateSalt();
            //KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iteration, AES_Key_Size);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), inputByte, iteration, AES_Key_Size);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey secretkey = factory.generateSecret(spec);
            return new SecretKeySpec(secretkey.getEncoded(), "AES");

        } catch (NoSuchAlgorithmException nsae) {
            logger.severe("Invalid algorithm provided " + StringUtil.getDetails(nsae));
            throw new AssertionError("Invalid algorithm.");
        } catch (InvalidKeySpecException ikse) {
            logger.severe("Invalid key specifications provided " + StringUtil.getDetails(ikse));
            throw new AssertionError("Invalid key specifications.");
        }
    }
/**
    @Subscribe
    public void handleEncryptionRequestEvent(EncryptionRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        System.out.println(event.getPassword());
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
*/
}
