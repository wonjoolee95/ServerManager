import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerManagerTest {
    private ServerManager manager;

    @BeforeEach
    public void setup() {
        manager = new ServerManager();
    }

    @Test
    public void testAllocateSingleServer() {
        String hostName = manager.allocate("apibox");

        assertEquals("apibox1", hostName);
    }

    @Test
    public void testAllocateMultipleMultipleServers() {
        manager.allocate("apibox");
        String hostName = manager.allocate("apibox");

        assertEquals("apibox2", hostName);
    }

    @Test
    public void testDeallocate() {
        manager.allocate("apibox");
        manager.allocate("apibox");
        manager.allocate("apibox");
        manager.deallocate("apibox2");

        String hostName = manager.allocate("apibox");

        assertEquals("apibox2", hostName);
    }

    @Test
    public void testAllocateAfterDeallocate() {
        manager.allocate("apibox");
        manager.allocate("apibox");
        manager.allocate("apibox");
        manager.deallocate("apibox2");
        manager.allocate("apibox");

        String hostName = manager.allocate("apibox");

        assertEquals("apibox4", hostName);
    }

}
