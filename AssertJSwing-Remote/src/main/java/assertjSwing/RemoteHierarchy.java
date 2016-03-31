package assertjSwing;

import org.assertj.swing.dependency.jsr305.Nonnull;
import org.assertj.swing.dependency.jsr305.Nullable;
import org.assertj.swing.hierarchy.ComponentHierarchy;

import java.awt.*;
import java.rmi.Remote;
import java.util.Collection;

/**
 * Created by timo on 28.2.2016.
 */
public interface RemoteHierarchy extends Remote {
    @Nonnull
    Collection<RemoteObject<Container>> roots();

    @Nonnull
    Collection<RemoteObject<Component>> childrenOf(@Nonnull Component var1);

    @Nullable
    Container parentOf(@Nonnull RemoteObject<Component> var1);

    boolean contains(@Nonnull RemoteObject<Component> var1);

    void dispose(@Nonnull RemoteObject<Window> var1);
}
