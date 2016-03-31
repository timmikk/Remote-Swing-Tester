package assertjSwing;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.AbstractButtonDriver;

import javax.swing.*;
import java.rmi.RemoteException;

/**
 * Created by timo on 8.3.2016.
 */
public class RemoteButtonImpl extends RemoteComponentImpl<AbstractButtonDriver, AbstractButton> implements RemoteButton {

    RemoteButtonImpl(AbstractButton targetComponent, BasicRobot robot) throws RemoteException {
        super(AbstractButton.class, targetComponent, robot, new AbstractButtonDriver(robot));
    }

    public void select() throws RemoteException {
        baseDriver.select(getTarget());
    }

    public void deselect() throws RemoteException {
        baseDriver.deselect(getTarget());
    }
}
