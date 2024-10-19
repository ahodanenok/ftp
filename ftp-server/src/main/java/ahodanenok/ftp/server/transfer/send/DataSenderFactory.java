package ahodanenok.ftp.server.transfer.send;

import ahodanenok.ftp.server.transfer.DataType;
import ahodanenok.ftp.server.transfer.StructureType;
import ahodanenok.ftp.server.transfer.TransferMode;

public interface DataSenderFactory {

    DataSender createSender(
        DataType type,
        StructureType structureType,
        TransferMode transferMode);
}
