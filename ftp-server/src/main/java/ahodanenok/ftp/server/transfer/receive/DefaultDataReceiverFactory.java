package ahodanenok.ftp.server.transfer.receive;

import ahodanenok.ftp.server.transfer.DataType;
import ahodanenok.ftp.server.transfer.StructureType;
import ahodanenok.ftp.server.transfer.TransferMode;

public final class DefaultDataReceiverFactory implements DataReceiverFactory {

    @Override
    public DataReceiver createReceiver(
            DataType type,
            StructureType structureType,
            TransferMode transferMode) {

        // todo: impl
        return new FileStreamReceiver();
    }
}
