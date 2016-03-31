package assertjSwing;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentMatcher;
import org.assertj.swing.core.Robot;

import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by timo on 28.2.2016.
 */
public class RemoteApplicationImpl implements RemoteApplication {
    private final static Logger LOGGER = Logger.getLogger(RemoteApplicationImpl.class.getName());
    public static final int defaultPort = 1099; //Port used to communicate with rmi registry
    public static final String defaultRegistryName = "rmi://localhost:1099/registerClient";
    public static final Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
    private ServerConnector serverConnector;
    private ApplicationHandler server;
    private int serverPort;


    public static void premain(String agentArgs) throws RemoteException {
        LOGGER.info("Agent started");

        try {
            int serverPort = Integer.parseInt(agentArgs);
            RemoteApplicationImpl agent = new RemoteApplicationImpl(serverPort);
        } catch (NumberFormatException e) {
            RemoteApplicationImpl agent = new RemoteApplicationImpl();
        }
    }


    RemoteApplicationImpl() {
        this(defaultPort);
    }

    RemoteApplicationImpl(int serverPort) {
        setServerPort(serverPort);
        setServerConnector(new ServerConnector(this));
    }

    protected void setServerConnector(ServerConnector connector) {
        this.serverConnector = connector;
    }

    public void startWaitingConnection() {
        this.serverConnector.start();
    }

    public void connectToServer() throws RemoteException, NotBoundException, MalformedURLException {
        Registry registry = LocateRegistry.getRegistry(getServerPort());
        ApplicationHandler server = (ApplicationHandler) registry.lookup(AbstractApplicationHandler.serverLookupName);

        //ApplicationHandler server = (ApplicationHandler) Naming.lookup(defaultRegistryName);
        UnicastRemoteObject.exportObject(this, getServerPort());
        setServer(server);
        server.register(this);
    }

    public void setServer(ApplicationHandler server) {
        this.server = server;
    }

    public ApplicationHandler getServer() {
        return server;
    }

    public void ping() throws RemoteException {
        LOGGER.finest("Got ping reguest.");
    }


    public void action(String method, RemoteObject component) {
        //TODO: action
    }

    public RemoteHierarchy getHierarchy() {
        //TODO: Get hierarchy
        return null;
    }

    public boolean hasConnection() {
        return this.serverConnector.isConnected();
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }


}
