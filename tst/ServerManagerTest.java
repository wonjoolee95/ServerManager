import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

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

}
