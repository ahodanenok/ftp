package ahodanenok.ftp.server.command;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import ahodanenok.ftp.server.command.FtpCommand;
import ahodanenok.ftp.server.command.NameListCommand;
import ahodanenok.ftp.server.connection.ResponseWriter;
import ahodanenok.ftp.server.connection.TestDataWriter;
import ahodanenok.ftp.server.request.DefaultFtpRequest;
import ahodanenok.ftp.server.request.FtpRequest;
import ahodanenok.ftp.server.request.FtpReply;
import ahodanenok.ftp.server.request.FtpSession;
import ahodanenok.ftp.server.storage.FileStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NameListCommandTest {

    @Test
    public void testListInCurrentDirectory() throws Exception {
        FileStorage storage = mock(FileStorage.class);
        when(storage.names("home")).thenReturn(Stream.of("a", "b", "c"));

        ResponseWriter responseWriter = mock(ResponseWriter.class);
        TestDataWriter dataWriter = new TestDataWriter();
        FtpSession session = mock(FtpSession.class);
        when(session.getCurrentDirectory()).thenReturn("home");
        when(session.isDataConnectionOpened()).thenReturn(true);
        when(session.getResponseWriter()).thenReturn(responseWriter);
        when(session.getDataWriter()).thenReturn(dataWriter);

        FtpCommand command = new NameListCommand(storage);
        command.handle(new DefaultFtpRequest(session, "NLST", new String[0]));

        assertEquals("a\r\nb\r\nc", dataWriter.toAsciiString());
        verify(responseWriter).write(FtpReply.CODE_125);
        verify(responseWriter).write(FtpReply.CODE_250);
        verify(session, never()).openDataConnection();
    }

    @Test
    public void testListInGivenDirectory() throws Exception {
        FileStorage storage = mock(FileStorage.class);
        when(storage.names("dir")).thenReturn(Stream.of("x", "y"));

        ResponseWriter responseWriter = mock(ResponseWriter.class);
        TestDataWriter dataWriter = new TestDataWriter();
        FtpSession session = mock(FtpSession.class);
        when(session.isDataConnectionOpened()).thenReturn(true);
        when(session.getResponseWriter()).thenReturn(responseWriter);
        when(session.getDataWriter()).thenReturn(dataWriter);

        FtpCommand command = new NameListCommand(storage);
        command.handle(new DefaultFtpRequest(session, "NLST", new String[] { "dir" }));

        assertEquals("x\r\ny", dataWriter.toAsciiString());
        verify(responseWriter).write(FtpReply.CODE_125);
        verify(responseWriter).write(FtpReply.CODE_250);
        verify(session, never()).openDataConnection();
    }
}
