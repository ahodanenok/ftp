package ahodanenok.ftp.server.transfer;

public final class DefaultDataTransferFactory implements DataTransferFactory {

    @Override
    public DataTransferHandler createHandler(
            DataType type,
            StructureType structureType,
            TransferMode transferMode) {

        // todo: impl
        return new FileStreamTransferHandler();
    }
}
