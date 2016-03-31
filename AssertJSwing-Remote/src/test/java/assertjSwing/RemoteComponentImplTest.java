package assertjSwing;

import org.junit.Assert;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JComponentDriver;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by timo on 15.3.2016.
 */
public class RemoteComponentImplTest {

    JComponentDriver mockDriver;
    Robot mockRobot;
    SerializableMatcher mockMatcher;
    ComponentLocator mockLocator;
    Component mockComponent;
    JPopupMenu mockPopupMenu;

    @Before
    public void setUp() throws Exception {
        mockDriver = mock(JComponentDriver.class);
        mockRobot = mock(Robot.class);
        mockMatcher = mock(SerializableMatcher.class);
        mockLocator = mock(ComponentLocator.class);
        mockComponent = mock(Component.class);
        mockPopupMenu = mock(JPopupMenu.class);

        when(mockLocator.findComponent()).thenReturn(mockComponent);
        when(mockDriver.invokePopupMenu(mockComponent)).thenReturn(mockPopupMenu);
    }

    @Test
    public void shouldCallDriversInvokePopupMenuWhenPopupMenuInvoked() throws Exception {
        RemoteComponent driver = new RemoteComponentImpl(Component.class, mockLocator, mockDriver);
        driver.invokePopupMenu();
        verify(mockDriver).invokePopupMenu(mockComponent);
    }

    @Test
    public void shouldInvokePopupMenuWithCorrectTargetComponent() throws Exception {
        RemoteComponent driver = new RemoteComponentImpl(Component.class, mockLocator, mockDriver);
        RemoteObject<JPopupMenu> menu = driver.invokePopupMenu();
        Assert.assertEquals(mockPopupMenu, ((RemoteObjectImpl) menu).getTarget());
    }


    @Test
    public void shouldCallDriversInvokePopupMenuWithPointWhenPopupMenuInvoked() throws Exception {
        Point point = new Point(10, 10);
        when(mockDriver.invokePopupMenu(mockComponent, point)).thenReturn(mockPopupMenu);
        RemoteComponent driver = new RemoteComponentImpl(Component.class, mockLocator, mockDriver);
        driver.invokePopupMenu(point);
        verify(mockDriver).invokePopupMenu(mockComponent, point);
    }

    @Test
    public void shouldInvokePopupMenuFromPointWithCorrectTargetComponent() throws Exception {
        Point point = new Point(10, 10);
        when(mockDriver.invokePopupMenu(mockComponent, point)).thenReturn(mockPopupMenu);
        RemoteComponent driver = new RemoteComponentImpl(Component.class, mockLocator, mockDriver);
        RemoteObject<JPopupMenu> menu = driver.invokePopupMenu(point);
        Assert.assertEquals(mockPopupMenu, ((RemoteObjectImpl) menu).getTarget());
    }
}