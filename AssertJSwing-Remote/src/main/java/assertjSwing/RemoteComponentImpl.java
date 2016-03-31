package assertjSwing;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentMatcher;
import org.assertj.swing.core.MouseButton;
import org.assertj.swing.driver.ComponentDriver;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.regex.Pattern;

import static assertjSwing.FinderUtils.*;

/**
 * Created by timo on 28.2.2016.
 */
public class RemoteComponentImpl<D extends ComponentDriver, C extends Container> extends RemoteObjectImpl<C> implements RemoteComponent<C> {
    D baseDriver;
    private final BasicRobot robot;
    Class<C> targetClass;


    RemoteComponentImpl(Class<C> targetClass, C targetComponent, BasicRobot robot, D realDriver) throws RemoteException {
        super(targetClass, targetComponent);
        UnicastRemoteObject.exportObject(this, RemoteApplicationImpl.defaultPort);
        this.targetClass = targetClass;
        this.robot = robot;
        this.baseDriver = realDriver;
    }


    public RemoteObject<Component> getObject(ComponentMatcher matcher) {
        return new RemoteObjectImpl<>(Component.class, robot.finder().find(matcher));
    }

    public RemoteButton getButton(SerializableMatcher matcher) throws RemoteException {
        return new RemoteButtonImpl(find(AbstractButton.class, matcher, getTarget(), getRobot().finder()), getRobot());
    }

    public RemoteButton getButton(List<SerializableMatcher> matcherChain) throws RemoteException {
        return new RemoteButtonImpl(find(AbstractButton.class, matcherChain, getTarget(), getRobot().finder()), getRobot());
    }

    protected BasicRobot getRobot() {
        return this.robot;
    }

    public void click() throws RemoteException {
        baseDriver.click(getTarget());
    }

    public void doubleClick() throws RemoteException {
        baseDriver.doubleClick(getTarget());
    }

    public void rightClick() throws RemoteException {
        baseDriver.rightClick(getTarget());
    }

    public void click(int button, int times) throws RemoteException {
        baseDriver.click(getTarget(), MouseButton.lookup(button), times);
    }

    public void click(Point where) throws RemoteException {
        baseDriver.click(getTarget());
    }

    public Font getFont() throws RemoteException {
        return baseDriver.fontOf(getTarget());
    }

    public Color getBackgroundColor() throws RemoteException {
        return baseDriver.backgroundOf(getTarget());
    }

    public Color getForegroundColor() throws RemoteException {
        return baseDriver.foregroundOf(getTarget());
    }

    public RemoteObject<JPopupMenu> invokePopupMenu() throws RemoteException {
        return new RemoteObjectImpl<>(JPopupMenu.class, baseDriver.invokePopupMenu(getTarget()));
    }

    public RemoteObject<JPopupMenu> invokePopupMenu(Point p) throws RemoteException {
        return new RemoteObjectImpl<>(JPopupMenu.class, baseDriver.invokePopupMenu(getTarget(), p));
    }

    public void pressAndReleaseKeys(int... keyCodes) throws RemoteException {
        baseDriver.pressAndReleaseKeys(getTarget(), keyCodes);
    }

    public void pressAndReleaseKey(int keyCode, int[] modifiers) throws RemoteException {
        baseDriver.pressAndReleaseKey(getTarget(), keyCode, modifiers);
    }

    public void pressKey(int keyCode) throws RemoteException {
        baseDriver.pressKey(getTarget(), keyCode);
    }

    public void releaseKey(int keyCode) throws RemoteException {
        baseDriver.releaseKey(getTarget(), keyCode);
    }

    public void focusAndWaitForFocusGain() throws RemoteException {
        baseDriver.focusAndWaitForFocusGain(getTarget());
    }

    public void focus() throws RemoteException {
        baseDriver.focus(getTarget());
    }


    public void requireShowing(long timeoutInMillis) throws RemoteException {

    }

    public void isShowing(long timeout) throws RemoteException {

    }

    public void waitUntilShowing(long timeout) throws RemoteException {

    }

    public void requireTooltip(String expected) throws RemoteException {

    }

    public void requireTooltip(Pattern pattern) throws RemoteException {

    }

}
