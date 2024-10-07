package ahodanenok.ftp.server.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultFtpCommandParserTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        " ",
        "  ",
        "      "
    })
    public void testParseEmptyString(String command) {
        FtpCommandParser.CommandParseResult result = new DefaultFtpCommandParser().parse(command);
        assertEquals(false, result.success());
        assertEquals(null, result.getName());
        assertEquals(null, result.getArguments());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "CDUP",
        " CDUP",
        "CDUP  ",
        "  CDUP   "
    })
    public void testParseCommandWithoutArguments(String command) {
        FtpCommandParser.CommandParseResult result = new DefaultFtpCommandParser().parse(command);
        assertEquals(true, result.success());
        assertEquals("CDUP", result.getName());
        assertArrayEquals(new String[0], result.getArguments());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "RNTO test/path",
        " RNTO test/path",
        "RNTO test/path ",
        "RNTO   test/path",
        "   RNTO   test/path     "
    })
    public void testParseCommandWithOneArgument(String command) {
        FtpCommandParser.CommandParseResult result = new DefaultFtpCommandParser().parse(command);
        assertEquals(true, result.success());
        assertEquals("RNTO", result.getName());
        assertArrayEquals(new String[] { "test/path" }, result.getArguments());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "RMD foo bar",
        " RMD foo bar",
        "RMD foo bar ",
        "RMD  foo  bar",
        "  RMD  foo  bar   "
    })
    public void testParseCommandWithTwoArguments(String command) {
        FtpCommandParser.CommandParseResult result = new DefaultFtpCommandParser().parse(command);
        assertEquals(true, result.success());
        assertEquals("RMD", result.getName());
        assertArrayEquals(new String[] { "foo", "bar" }, result.getArguments());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "ALLO x y z",
        " ALLO x y z",
        " ALLO x y z ",
        "ALLO  x y   z",
        "   ALLO  x y   z   "
    })
    public void testParseCommandWithThreeArguments(String command) {
        FtpCommandParser.CommandParseResult result = new DefaultFtpCommandParser().parse(command);
        assertEquals(true, result.success());
        assertEquals("ALLO", result.getName());
        assertArrayEquals(new String[] { "x", "y", "z" }, result.getArguments());
    }
}
