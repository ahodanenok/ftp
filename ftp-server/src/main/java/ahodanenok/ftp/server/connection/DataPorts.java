package ahodanenok.ftp.server.connection;

import java.util.LinkedList;

public final class DataPorts {

    private final LinkedList<Integer> availablePorts;

    public DataPorts(int rangeStart, int rangeEnd) {
        availablePorts = new LinkedList<>();
        for (int p = rangeStart; p <= rangeEnd; p++) {
            availablePorts.add(p);
        }
    }

    public synchronized int allocate() {
        if (availablePorts.isEmpty()) {
            return -1;
        }

        return availablePorts.removeFirst();
    }

    public synchronized void free(int port) {
        // todo: should validate or whatever?
        availablePorts.addLast(port);
    }
}
