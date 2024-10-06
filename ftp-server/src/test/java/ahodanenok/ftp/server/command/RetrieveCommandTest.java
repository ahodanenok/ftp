package ahodanenok.ftp.server.command;

import java.io.ByteArrayInputStream;

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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RetrieveCommandTest {

    @Test
    public void testReadGivenFile() throws Exception {
        FileStorage storage = mock(FileStorage.class);
        when(storage.read("test/file.txt"))
            .thenReturn(new ByteArrayInputStream(new byte[] { 0x61, 0x62, 0x63 }));

        ResponseWriter responseWriter = mock(ResponseWriter.class);
        TestDataWriter dataWriter = new TestDataWriter();
        FtpSession session = mock(FtpSession.class);
        when(session.isDataConnectionOpened()).thenReturn(true);
        when(session.getResponseWriter()).thenReturn(responseWriter);
        when(session.getDataWriter()).thenReturn(dataWriter);

        FtpCommand command = new RetrieveCommand(storage);
        command.handle(new DefaultFtpRequest(session, "RETR", new String[] { "test/file.txt" }));

        assertArrayEquals(new byte[] { 0x61, 0x62, 0x63 }, dataWriter.toBytes());
        verify(responseWriter).write(FtpReply.CODE_125);
        verify(responseWriter).write(FtpReply.CODE_250);
        verify(session, never()).openDataConnection();
    }
}
