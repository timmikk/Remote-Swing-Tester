package assertjSwing;

import org.junit.After;
import org.junit.Test;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by timo on 20.3.2016.
 */
public class AbstractApplicationHandlerTest {

    Registry registry;
    AbstractApplicationHandler appHandler;

    public static void tearDownRegistry(Registry registry) throws RemoteException, NotBoundException {
        // Tear down RMI registry
        if(registry != null) {
            for(String name :registry.list()) {
                registry.unbind(name);
            }
            try {
                UnicastRemoteObject.unexportObject(registry, true);
            } catch (NoSuchObjectException e) {}
        }
    }

    @After
    public void tearDown() throws Exception {
        tearDownRegistry(this.registry);
        if(appHandler != null) {
            tearDownRegistry(appHandler.getRegistry());
        }
    }

    @Test
    public void should_create_registry_on_initialization() throws Exception {
        registry = LocateRegistry.getRegistry(1099);
        try {
            registry.list();
            fail("Registry already exists. Cannot run the test.");
        } catch (RemoteException e){}

        appHandler = new AbstractApplicationHandler(1099);
        assertNotNull(registry.list());
    }

    @Test
    public void should_fail_if_registry_already_exists() throws Exception {
        Registry secondRegistry = null;
        registry = LocateRegistry.createRegistry(1099);
        try {
            secondRegistry = AbstractApplicationHandler.createRegistry(1099);
            fail();
        } catch (RemoteException e) {
            tearDownRegistry(secondRegistry);
        }
    }

    @Test
    public void should_bind_self_to_registry_for_client() throws Exception {
        appHandler = new AbstractApplicationHandler(1099);
        registry = LocateRegistry.getRegistry(1099);
        assertNotNull(registry.lookup(AbstractApplicationHandler.serverLookupName));
    }

    @Test
    public void should_include_client_to_registered_clients_after_registration() throws Exception {
        appHandler = new AbstractApplicationHandler(1099);
        RemoteApplication app = mock(RemoteApplication.class);
        appHandler.register(app);
        List<RemoteApplication> apps = appHandler.getApplications();
        assertTrue(apps.contains(app));
    }

    @Test
    public void should_remote_client_from_registered_clients_after_unregistration() throws Exception {
        appHandler = new AbstractApplicationHandler(1099);
        RemoteApplication app = mock(RemoteApplication.class);
        appHandler.register(app);
        List<RemoteApplication> apps = appHandler.getApplications();
        assertTrue(apps.contains(app));
        appHandler.unregister(app);
        assertFalse(apps.contains(app));
    }
}