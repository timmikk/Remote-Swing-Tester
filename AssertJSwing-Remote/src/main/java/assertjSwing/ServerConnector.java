package assertjSwing;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by timo on 17.3.2016.
 */
class ServerConnector extends Thread {
    private final static Logger LOGGER = Logger.getLogger(ServerConnector.class.getName());

    private final RemoteApplicationImpl client;
    private boolean connection;


    ServerConnector(RemoteApplicationImpl client) {
        this.client = client;
    }

    public void run() {
        while(true) {
            try {
                waitServer();
            } catch (MalformedURLException e) {
                LOGGER.log(Level.SEVERE, "Invalid Server URL, giving up with connection attempts.", e);
                return;
            }
            if(testConnection()) {
                setConnected(true);
                watchConnection();
            }
        }
    }

    private boolean connect() throws MalformedURLException {
        try {
            client.connectToServer();
        } catch (RemoteException e) {
            return false;
        } catch (NotBoundException e) {
            return false;
        }
        return true;
    }



    boolean testConnection() {
        ApplicationHandler server = client.getServer();
        if(server == null)
            return false;
        try {
            server.ping();
        } catch (RemoteException e) {
            return false;
        }
        return true;
    }

    void watchConnection() {
        while(testConnection()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void waitServer() throws MalformedURLException {
        while (!connect()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setConnected(boolean connection) {
        this.connection = connection;
    }

    public boolean isConnected() {
        return connection;
    }
}
