package assertjSwing;

import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.timing.Condition;
import org.assertj.swing.timing.Pause;

import java.rmi.RemoteException;
import java.util.regex.Pattern;

import static org.assertj.core.util.Objects.areEqual;
import static org.assertj.swing.util.Strings.areEqualOrMatch;
import static org.assertj.swing.util.Strings.match;

/**
 * Created by timo on 2.3.2016.
 */
public class RemoteObjectImpl<O> implements RemoteObject<O> {

    private final O target;
    private final Class<O> targetClass;

    RemoteObjectImpl(Class<O> objectType, O object) {
        this.targetClass = objectType;
        this.target = object;
    }

    @Override
    public <T> T getPoperty(String propertyName, Class<T> returnType) {
        return NativeObjectUtils.getPropertyValue(propertyName, target, returnType);
    }

    @Override
    public Object getPoperty(String propertyName) throws RemoteException {
        return getPoperty(propertyName, Object.class);

    }

    @Override
    public void setPoperty(String propertyName, Object value) {
        NativeObjectUtils.setPropertyValue(propertyName, target, value);
    }

    @Override
    public Class<O> getTargetClass() {
        return targetClass;
    }

    @Override
    public void invokeMethod(String methodName, Object... args) {
        NativeObjectUtils.invokeMethod(methodName, target, args);
    }

    @Override
    public <T> T invokeMethod(String methodName, Class<T> returnType, Object... args) {
        return NativeObjectUtils.invokeMethod(methodName, target, returnType, args);
    }

    @Override
    public void requireProperty(String property, Object value, long timeoutInMillis) throws RemoteException {

    }

    @Override
    public void waitUntilProperty(String property, Object value, long timeoutInMillis) throws RemoteException {

    }

    @Override
    public boolean isProperty(final String property, final Object value, long timeoutInMillis) throws RemoteException {
        Condition condition = new Condition("") {
            @Override
            public boolean test() {
                try {
                    return matches(value, getPoperty(property));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return false;
            }
        };

        try {
            Pause.pause(condition, timeoutInMillis);
        } catch (WaitTimedOutError e) {
            return false;
        }

        return true;
    }

    public O getTarget() {
        return this.target;
    }


    private boolean matches(Object expected, Object actual) {
        if (expected instanceof String && actual instanceof String) {
            return areEqualOrMatch((String) expected, (String) actual);
        }
        if (expected instanceof Pattern && actual instanceof CharSequence) {
            return match((Pattern) expected, (CharSequence) actual);
        }
        return areEqual(expected, actual);
    }
}
