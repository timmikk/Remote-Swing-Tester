package assertjSwing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by timo on 16.3.2016.
 */
public class RemoteApplicationImplTest {
    private ApplicationHandler mockServer;
    private Registry registry;


    @After
    public void tearDown() throws Exception {
        // Tear down RMI registry
        if(registry != null) {
            for(String name :registry.list()) {
                registry.unbind(name);
            }
            UnicastRemoteObject.unexportObject(registry, true);
        }
    }

    @Before
    public void setUp() throws Exception {
        mockServer = mock(ApplicationHandler.class, withSettings().serializable());

    }

    private void startMockServer() throws RemoteException {
        registry = LocateRegistry.createRegistry(1099);
        //registry = LocateRegistry.getRegistry();

        Remote remoteStub = UnicastRemoteObject.exportObject(mockServer, 1099);
        registry.rebind(AbstractApplicationHandler.serverLookupName, remoteStub);
    }

    @Test
    public void should_register_to_server_on_connection() throws Exception {
        startMockServer();

        RemoteApplicationImpl agentClient = new RemoteApplicationImpl();
        agentClient.connectToServer();

        verify(mockServer).register(Mockito.any(RemoteApplication.class));
    }

    @Test
    public void should_wait_server() throws Exception {
        try{ //Verify that the RMI registry is not running
            LocateRegistry.getRegistry(1099).list();
            fail("RMI server already initialized.");
        } catch (RemoteException e) {}

        RemoteApplicationImpl agentClient = new RemoteApplicationImpl();
        agentClient.startWaitingConnection();
        Thread.sleep(200);
        startMockServer();
        verify(mockServer, timeout(500)).register(Mockito.any(RemoteApplication.class));
    }
}