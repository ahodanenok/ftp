package ahodanenok.ftp.server.transfer;

import ahodanenok.ftp.server.request.FtpSession;

public interface DataTransferFactory {

    DataTransferHandler createHandler(
        DataType type,
        StructureType structureType,
        TransferMode transferMode);
}
