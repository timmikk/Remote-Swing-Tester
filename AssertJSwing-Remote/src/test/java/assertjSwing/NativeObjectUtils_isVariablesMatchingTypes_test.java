package assertjSwing;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;


/**
 * Created by timo on 2.3.2016.
 */
public class NativeObjectUtils_isVariablesMatchingTypes_test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldMatchWhenVariableHasExpectedType() throws Exception {
        Object[] variables = new Object[] {""};
        Class[] types = new Class[] {String.class};
        Assert.assertTrue(NativeObjectUtils.variablesMatchTypes(variables, types));
    }

    @Test
    public void shouldNotMatchWhenVariableHasDifferentTypes() throws Exception {
        Object[] variables = new Object[] {""};
        Class[] types = new Class[] {Integer.class};
        Assert.assertFalse(NativeObjectUtils.variablesMatchTypes(variables, types));
    }

    @Test
    public void shouldMatchWhenVariableIsNull() throws Exception {
        Object[] variables = new Object[] {null};
        Class[] types = new Class[] {String.class};
        Assert.assertTrue(NativeObjectUtils.variablesMatchTypes(variables, types));
    }

    @Test
    public void shouldMatchWithVariableWhichTypeIsInheritedFromType() throws Exception {
        Object[] variables = new Object[] {""};
        Class[] types = new Class[] {Object.class};
        Assert.assertTrue(NativeObjectUtils.variablesMatchTypes(variables, types));
    }

    @Test
    public void shouldMatchMultipleVariablesToTypes() throws Exception {
        Object[] variables = new Object[] {"", null, new JTextField("some text")};
        Class[] types = new Class[] {String.class, Object.class, Component.class};
        Assert.assertTrue(NativeObjectUtils.variablesMatchTypes(variables, types));
    }

    @Test
    public void shouldNotMatchWhenVariableTypeDiffersFromType() throws Exception {
        Object[] variables = new Object[] {""};
        Class[] types = new Class[] {Integer.class};
        Assert.assertFalse(NativeObjectUtils.variablesMatchTypes(variables, types));
    }

}
