package assertjSwing;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentMatcher;
import org.assertj.swing.exception.ComponentLookupException;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by timo on 29.3.2016.
 */
public class RemoteContainerImpl<C extends Container> extends RemoteObjectImpl<C> implements RemoteContainer<C> {
    private final BasicRobot robot;
    Class<C> targetClass;

    RemoteContainerImpl(Class<C> targetClass, C targetObject, BasicRobot robot) throws RemoteException {
        super(targetClass, targetObject);
        UnicastRemoteObject.exportObject(this, RemoteApplicationImpl.defaultPort);
        this.targetClass = targetClass;
        this.robot = robot;
    }

    public RemoteButton getButton(SerializableMatcher matcher) throws RemoteException {
        return new RemoteButtonImpl(find(AbstractButton.class, matcher), getRobot());
    }

    public RemoteButton getButton(List<SerializableMatcher> matcherChain) throws RemoteException {
        return new RemoteButtonImpl(find(AbstractButton.class, matcherChain), getRobot());
    }

    @Override
    public Object getPoperty(String propertyName, Class returnType) {
        return null;
    }

    @Override
    public Object invokeMethod(String methodName, Class returnType, Object... args) {
        return null;
    }


    protected <T> T find(Class<T> expectedType, ComponentMatcher matcher) {
        return find(expectedType, matcher, getTarget());
    }

    protected <T> T find(Class<T> expectedType, ComponentMatcher matcher, Container container) {
        Component c = getRobot().finder().find(container, matcher);
        if(c.getClass().isAssignableFrom(expectedType)) {
            return expectedType.cast(c);
        } else {
            throw new ComponentLookupException("Type of found component is not as expected.");
            //TODO: Better error message
        }
    }

    protected <T> T find(Class<T> expectedType, List<SerializableMatcher> matcherChain) {
        Component c = getTarget();
        for(ComponentMatcher matcher : matcherChain) {
            if(!(c instanceof Container)) {
                throw new ComponentLookupException("Invalid matcher chain.");
                //TODO: Better error message
            }
            c = find(Container.class, matcher, (Container) c);
        }

        if(c.getClass().isAssignableFrom(expectedType)) {
            return expectedType.cast(c);
        } else {
            throw new ComponentLookupException("Type of found component is not as expected.");
            //TODO: Better error message
        }
    }


    protected BasicRobot getRobot() {
        return this.robot;
    }
}
