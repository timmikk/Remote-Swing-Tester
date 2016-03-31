package assertjSwing;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by timo on 26.3.2016.
 */
public interface RemoteContainer<O> extends RemoteObject<O>, Remote {

    public RemoteButton getButton(SerializableMatcher matcher) throws RemoteException;
    public RemoteButton getButton(java.util.List<SerializableMatcher> matcherChain) throws RemoteException;

}
