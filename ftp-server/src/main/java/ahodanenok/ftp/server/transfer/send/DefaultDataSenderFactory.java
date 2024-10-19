package ahodanenok.ftp.server.transfer.send;

import ahodanenok.ftp.server.transfer.DataType;
import ahodanenok.ftp.server.transfer.StructureType;
import ahodanenok.ftp.server.transfer.TransferMode;

public final class DefaultDataSenderFactory implements DataSenderFactory {

    @Override
    public DataSender createSender(
            DataType type,
            StructureType structureType,
            TransferMode transferMode) {

        // todo: impl
        return new FileStreamSender();
    }
}
