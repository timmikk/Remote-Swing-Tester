package assertjSwing;

import javax.swing.*;
import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

/**
 * Created by timo on 26.2.2016.
 */
public interface RemoteComponent<C> extends Remote, RemoteObject<C> {

    void click() throws RemoteException;

    void doubleClick() throws RemoteException;

    void rightClick() throws RemoteException;

    void click(int button, int times) throws RemoteException;

    void click(Point where) throws RemoteException;

    Font getFont() throws RemoteException;

    Color getBackgroundColor() throws RemoteException;

    Color getForegroundColor() throws RemoteException;

    RemoteObject<JPopupMenu> invokePopupMenu() throws RemoteException;

    RemoteObject<JPopupMenu> invokePopupMenu(Point p) throws RemoteException;

    void pressAndReleaseKeys(int... keyCodes) throws RemoteException;

    void pressAndReleaseKey(int keyCode, int[] modifiers) throws RemoteException;

    void pressKey(int keyCode) throws RemoteException;

    void releaseKey(int keyCode) throws RemoteException;

    void focusAndWaitForFocusGain() throws RemoteException;

    void focus() throws RemoteException;

    void requireShowing(long timeoutInMillis) throws RemoteException;

    void isShowing(long timeout) throws RemoteException;

    void waitUntilShowing(long timeout) throws RemoteException;

    void requireTooltip(String expected) throws RemoteException;

    void requireTooltip(Pattern pattern) throws RemoteException;
}
