package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddMovieCommand;


public class AddMovieCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
    }

    @Test
    public void parse_optionalFieldsMissing_success() {

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

    }

    @Test
    public void parse_invalidValue_failure() {

    }
}
