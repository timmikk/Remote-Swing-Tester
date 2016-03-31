package assertjSwing;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by timo on 8.3.2016.
 */
interface RemoteButton extends Remote, RemoteComponent<AbstractButton> {

    public void select() throws RemoteException;

    public void deselect() throws RemoteException;
}
