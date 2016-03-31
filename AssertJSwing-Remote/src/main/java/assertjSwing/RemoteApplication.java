package assertjSwing;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by timo on 28.2.2016.
 */
public interface RemoteApplication extends Remote {


    void action(String method, RemoteObject component) throws RemoteException;

    RemoteHierarchy getHierarchy() throws RemoteException;

    public void ping() throws RemoteException;
}
