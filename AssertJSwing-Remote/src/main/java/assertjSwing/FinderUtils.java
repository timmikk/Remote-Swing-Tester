package assertjSwing;

import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.core.ComponentMatcher;
import org.assertj.swing.exception.ComponentLookupException;

import java.awt.*;
import java.util.*;

/**
 * Created by timo on 29.3.2016.
 */
public class FinderUtils {

    public static  <T> T find(Class<T> expectedType, ComponentMatcher matcher, ComponentFinder finder) {
        return find(expectedType, matcher, null, finder);
    }

    public static <T> T find(Class<T> expectedType, ComponentMatcher matcher, Container parent, ComponentFinder finder) {
        Component c = finder.find(parent, matcher);
        if(c.getClass().isAssignableFrom(expectedType)) {
            return expectedType.cast(c);
        } else {
            throw new ComponentLookupException("Type of found component is not as expected.");
            //TODO: Better error message
        }
    }

    public static <T> T find(Class<T> expectedType, java.util.List<SerializableMatcher> matcherChain, Container parent, ComponentFinder finder) {
        for(ComponentMatcher matcher : matcherChain) {
            if(parent == null) {
                throw new ComponentLookupException("Invalid matcher chain.");
                //TODO: Better error message
            }
            parent = find(Container.class, matcher, parent, finder);
        }

        if(parent.getClass().isAssignableFrom(expectedType)) {
            return expectedType.cast(parent);
        } else {
            throw new ComponentLookupException("Type of found component is not as expected.");
            //TODO: Better error message
        }
    }
}
