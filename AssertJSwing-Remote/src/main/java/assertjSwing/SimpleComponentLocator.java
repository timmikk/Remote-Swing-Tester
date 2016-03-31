package assertjSwing;

import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.exception.ComponentLookupException;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by timo on 15.3.2016.
 */
public class SimpleComponentLocator<C> implements ComponentLocator<C> {
    private final ComponentFinder finder;
    Class<C> targetType;
    List<SerializableMatcher> matcherChain;

    public SimpleComponentLocator(Class<C> targetType, SerializableMatcher matcher, ComponentFinder finder) {
        this.targetType = targetType;
        this.matcherChain = new ArrayList<SerializableMatcher>(1);
        matcherChain.add(matcher);
        this.finder = finder;
    }

    public SimpleComponentLocator(Class<C> targetType, List<SerializableMatcher> matcherChain, ComponentFinder finder) {
        this.targetType = targetType;
        this.matcherChain = matcherChain;
        this.finder = finder;
    }

    public C findComponent() {
        return targetType.cast(findComponent(null, matcherChain, 0));
    }

    Component findComponent(Container container, List<SerializableMatcher> matcherChain, int chainIndex) {
        Component component = finder().find(container, matcherChain.get(chainIndex));
        if(chainIndex < matcherChain.size()-1) {
            if (component instanceof Container) {
                return findComponent((Container) component, matcherChain, ++chainIndex);
            }
            else {
                throw new ComponentLookupException("Found component when expecting container");
            }
        } else {
            return component;
        }
    }

    private ComponentFinder finder() {
        return this.finder;
    }

}
