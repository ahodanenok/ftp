package ahodanenok.ftp.server.transfer.receive;

import ahodanenok.ftp.server.transfer.DataType;
import ahodanenok.ftp.server.transfer.StructureType;
import ahodanenok.ftp.server.transfer.TransferMode;

public interface DataReceiverFactory {

    DataReceiver createReceiver(
        DataType type,
        StructureType structureType,
        TransferMode transferMode);
}
