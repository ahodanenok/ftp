package ahodanenok.ftp.server.request;

import ahodanenok.ftp.server.connection.DataWriter;

public interface FtpSession {

    DataWriter getDataWriter();
}
