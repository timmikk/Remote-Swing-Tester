package assertjSwing;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by timo on 28.2.2016.
 */
public class AbstractApplicationHandler implements ApplicationHandler {
    private final static Logger LOGGER = Logger.getLogger(AbstractApplicationHandler.class.getName());

    public static final int defaultPort = 1099;
    //public static final String defaultRegistryName = "rmi://localhost:5002/registerClient";

    public static final String serverLookupName = "agentServer";
    private int port;
    private Registry registry;
    private List<RemoteApplication> applications;

    AbstractApplicationHandler(int port) throws RemoteException, MalformedURLException {
        applications = new ArrayList<>();
        this.port = port;
        this.registry = createRegistry(port);
        registerAgentRegistryService(this.registry, serverLookupName, port);

        //TODO: Start app with client agent
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        int port = Integer.parseInt(System.getProperty("port", ""+defaultPort));
        new AbstractApplicationHandler(port);
    }

    static Registry createRegistry(int port) throws RemoteException {
        LOGGER.info("creating registry listening on port: " + port);

        boolean registryExists = false;
        try {
            Registry registry = LocateRegistry.getRegistry(port);
            registry.list();
            registryExists = true;
        } catch (RemoteException ignored) {}

        if(registryExists)
            throw new RemoteException("Other RMI registry is already using the given port: " + port);

        return LocateRegistry.createRegistry(port);
    }

    void registerAgentRegistryService(Registry registry, String registryName, int port) throws RemoteException, MalformedURLException {
        LOGGER.info("Registering agent registry with name: " + registryName);
        Remote remoteStub = UnicastRemoteObject.exportObject(this, port);
        registry.rebind(registryName, remoteStub);
        //Naming.rebind(registryName, remoteStub);
    }

    public void register(RemoteApplication app) throws RemoteException {
        LOGGER.fine("Agent client requesting registration.");

        try {
            RemoteServer.getClientHost();
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        applications.add(app);

        /*
        RemoteButton driver = app.getButton(new SerializableMatcher() {
            public boolean matches(@Nullable Component component) {
                return component instanceof JButton;
            }
        });
        driver.click();
*/
        //driver.action("click");
  /*      RemoteObject<AbstractButton> object = driver.getTargetComponent();
        String text = driver.getTargetComponent().getPoperty("text", String.class);
        System.out.println(text);*/
    }

    @Override
    public void unregister(RemoteApplication app) throws RemoteException {
        this.applications.remove(app);
    }

    public void ping() throws RemoteException {
        LOGGER.finest("Got ping reguest");
    }

    Registry getRegistry() {
        return registry;
    }

    public List<RemoteApplication> getApplications() {
        return applications;
    }
}
