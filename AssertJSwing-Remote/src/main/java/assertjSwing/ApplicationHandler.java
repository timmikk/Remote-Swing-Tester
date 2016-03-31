package assertjSwing;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by timo on 12.2.2016.
 */
public interface ApplicationHandler extends Remote {

    void register(RemoteApplication client) throws RemoteException;

    void unregister(RemoteApplication app) throws RemoteException;

    public void ping() throws RemoteException;

}
