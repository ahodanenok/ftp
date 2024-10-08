package ahodanenok.ftp.server.request;

import org.junit.jupiter.api.Test;

import ahodanenok.ftp.server.connection.ResponseWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class FtpRequestDispatcherTest {

    @Test
    public void testDispatchUnknownCommand() {
        FtpRequestDispatcher dispatcher =
            new FtpRequestDispatcher(__ -> fail("executor should not be called"));
        dispatcher.register("RETR", __ -> fail("RETR should not be called"));

        ResponseWriter responseWriter = mock(ResponseWriter.class);
        FtpSession session = mock(FtpSession.class);
        when(session.getResponseWriter()).thenReturn(responseWriter);
        FtpRequest request = new DefaultFtpRequest(session, "ALLO", new String[0]);
        dispatcher.dispatch(request);

        verify(responseWriter).write(FtpReply.CODE_500);
    }

    @Test
    public void testRegisterMultipleCommandsWithTheSameName() {
        FtpRequestDispatcher dispatcher = new FtpRequestDispatcher(r -> r.run());
        dispatcher.register("allo", __ -> fail("ALLO should not be called"));
        IllegalStateException e = assertThrows(
            IllegalStateException.class,
            () -> dispatcher.register("ALLO", __ -> fail("ALLO should not be called")));
        assertEquals("Command 'ALLO' already registered", e.getMessage());
    }

    @Test
    public void testDispatchCommand() {
        int[] called = new int[] { 0 };

        FtpRequestDispatcher dispatcher = new FtpRequestDispatcher(r -> r.run());
        dispatcher.register("RETR", __ -> fail("RETR should not be called"));
        dispatcher.register("ALLO", __ -> called[0]++);

        ResponseWriter responseWriter = mock(ResponseWriter.class);
        FtpSession session = mock(FtpSession.class);
        when(session.getResponseWriter()).thenReturn(responseWriter);
        FtpRequest request = new DefaultFtpRequest(session, "ALLO", new String[0]);
        dispatcher.dispatch(request);

        assertEquals(1, called[0]);
        verifyNoInteractions(responseWriter);
    }
}