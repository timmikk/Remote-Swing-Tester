package assertjSwing;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by timo on 19.3.2016.
 */
public class ServerConnectorTest {
    private ApplicationHandler mockServer;
    RemoteApplicationImpl mockClient;

    @Before
    public void setUp() throws Exception {
        mockServer = mock(ApplicationHandler.class, withSettings().serializable());
        mockClient = mock(RemoteApplicationImpl.class);
    }

    @Test
    public void should_return_false_when_no_connection() throws Exception {
        mockClient.setServer(mockServer);
        doThrow(new RemoteException()).when(mockServer).ping();
        ServerConnector connector = new ServerConnector(mockClient);
        assertFalse(connector.testConnection());
    }

    @Test
    public void should_return_false_client_has_no_server_defined() throws Exception {
        RemoteApplicationImpl client = new RemoteApplicationImpl();
        ServerConnector connector = new ServerConnector(client);
        assertFalse(connector.testConnection());
    }

    @Test
    public void should_return_true_on_connection() throws Exception {
        final RemoteApplicationImpl client = new RemoteApplicationImpl();
        client.setServer(mockServer);
        ServerConnector connector = new ServerConnector(client);
        assertTrue(connector.testConnection());
    }

    @Test
    public void should_watch_connection_until_connection_lost() throws Exception {
        final RemoteApplicationImpl client = new RemoteApplicationImpl();
        ServerConnector connector = new ServerConnector(client);
        client.setServer(mockServer);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.setServer(null);
            }
        };
        thread.start();

        long startTime = System.currentTimeMillis();
        connector.watchConnection();

        assertTrue(System.currentTimeMillis()-startTime > 1000);
    }

    @Test
    public void should_wait_server_until_connection() throws Exception {
        ServerConnector connector = new ServerConnector(mockClient);
        doThrow(new RemoteException()).when(mockClient).connectToServer();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    doNothing().when(mockClient).connectToServer();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        long startTime = System.currentTimeMillis();
        connector.waitServer();
        assertTrue(System.currentTimeMillis()-startTime > 1000);
    }

    @Test(timeout = 3000)
    public void should_reconnect_after_connection_lost() throws Exception {
        doReturn(mockServer).when(mockClient).getServer();
        ServerConnector connector = new ServerConnector(mockClient);
        connector.start();
        while(!connector.isConnected()){Thread.sleep(50);} //Wait for connected status
    }
}