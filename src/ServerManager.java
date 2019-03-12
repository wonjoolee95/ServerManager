import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 *  >> tracker = Tracker.new()
 *  >> tracker.allocate("apibox")
 *  "apibox1"
 *  >> tracker.allocate("apibox")
 *  "apibox2"
 *  >> tracker.deallocate("apibox1")
 *  nil
 *  >> tracker.allocate("apibox")
 *  "apibox1"
 *  >> tracker.allocate("sitebox")
 *  "sitebox1"
 *
 **/

public class ServerManager {

    private HashMap<String, HashSet<Integer>> servers;
    private HashMap<String, LinkedList<Integer>> reusableServers;

    public ServerManager() {
        this.servers = new HashMap<>();
        this.reusableServers = new HashMap<>();
    }

    public String allocate(String hostType) {
        if (!servers.containsKey(hostType)) {
            servers.put(hostType, new HashSet<>());
            reusableServers.put(hostType, new LinkedList<>());
        }

        int availableHostNumber;
        if (reusableServers.get(hostType).isEmpty()) {
            availableHostNumber = servers.get(hostType).size() + 1;
        } else {
            availableHostNumber = reusableServers.get(hostType).removeFirst();
        }

        // Add hostNumber to hostType.
        servers.get(hostType).add(availableHostNumber);

        return hostType + Integer.toString(availableHostNumber);
    }

    public boolean deallocate(String hostName) {
        Pair<String, Integer> parsedHostName = parseHostName(hostName);
        String hostType = parsedHostName.getKey();
        int hostNumber = parsedHostName.getValue();

        // Verify hostType and hostNumber exists.
        if (!servers.containsKey(hostType) || !servers.get(hostType).contains(hostNumber)) {
            return false;
        }

        // Remove hotsNumber from hostType.
        servers.get(hostType).remove(hostNumber);

        int index = 0;
        for (Integer num : reusableServers.get(hostType)) {
            if (num < hostNumber) {
                break;
            }
            index++;
        }

        reusableServers.get(hostType).add(index, hostNumber);

        return true;
    }

    private Pair<String, Integer> parseHostName(String hostName) {
        StringBuilder hostType = new StringBuilder();
        StringBuilder hostNumber = new StringBuilder();

        for (char c : hostName.toCharArray()) {
            if (Character.isLetter(c)) {
                hostType.append(c);
            } else {
                hostNumber.append(c);
            }
        }

        return new Pair<>(hostType.toString(), Integer.parseInt(hostNumber.toString()));
    }
}
