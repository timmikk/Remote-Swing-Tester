package assertjSwing;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by timo on 28.2.2016.
 */
public interface RemoteObject<O> extends Remote {

    <T> T getPoperty(String propertyName, Class<T> returnType) throws RemoteException;

    Object getPoperty(String propertyName) throws RemoteException;

    void setPoperty(String propertyName, Object value) throws RemoteException;

    Class<O> getTargetClass() throws RemoteException;

    void invokeMethod(String methodName, Object... args) throws RemoteException;

    <T> T invokeMethod(String methodName, Class<T> returnType, Object... args) throws RemoteException;

    public void requireProperty(String property, Object value, long timeoutInMillis) throws RemoteException;

    public void waitUntilProperty(String property, Object value, long timeoutInMillis) throws RemoteException;

    public boolean isProperty(String property, Object value, long timeoutInMillis) throws RemoteException;

}
