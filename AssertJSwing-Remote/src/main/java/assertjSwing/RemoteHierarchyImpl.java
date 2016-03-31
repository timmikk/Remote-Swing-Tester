package assertjSwing;

import org.assertj.swing.dependency.jsr305.Nonnull;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by timo on 20.3.2016.
 */
public class RemoteHierarchyImpl implements RemoteHierarchy {



    @Override
    public Collection<RemoteObject<Container>> roots() {
        return null;
    }

    @Override
    public Collection<RemoteObject<Component>> childrenOf(@Nonnull Component var1) {
        return null;
    }

    @Override
    public Container parentOf(@Nonnull RemoteObject<Component> var1) {
        return null;
    }

    @Override
    public boolean contains(@Nonnull RemoteObject<Component> var1) {
        return false;
    }

    @Override
    public void dispose(@Nonnull RemoteObject<Window> var1) {

    }
}
