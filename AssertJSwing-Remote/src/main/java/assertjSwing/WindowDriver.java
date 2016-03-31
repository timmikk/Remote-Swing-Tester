package assertjSwing;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by timo on 8.3.2016.
 */
interface WindowDriver extends Remote, RemoteComponent {
    public void move(int x, int y) throws RemoteException;

    public void resizeWidthTo(int width) throws RemoteException;

    public void resizeHeightTo(int height) throws RemoteException;

    public void resizeTo(Dimension size) throws RemoteException;

    public void moveTo(Point where) throws RemoteException;

    public void close() throws RemoteException;

    public void show() throws RemoteException;

    public void show(Dimension size) throws RemoteException;

    public void moveToFront() throws RemoteException;

    public void moveToBack() throws RemoteException;

}
