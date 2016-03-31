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
public class NativeObjectUtils_getMethod_test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldReturnCorrectPropertyValue() {
        String text = "Some text";
        JTextField someTextField = new JTextField(text);
        String propertyValue = NativeObjectUtils.getPropertyValue("text", someTextField, String.class);
        Assert.assertEquals(text, propertyValue);
    }

    @Test
    public void shouldFindCorrectParameterlessMethod() throws Exception {
        Method method = NativeObjectUtils.getMethod(TestClass.class, "testMethod", new Object[0]);
        Assert.assertEquals("testMethod", method.getName());
    }


    @Test
    @Ignore
    public void shouldFindCorrectMethodWithParameters() throws Exception {
        Object[] params = new Object[] {new B()};
        Method method = NativeObjectUtils.getMethod(TestClass.class, "testMethodB", params);
        Assert.assertEquals("testMethodB", method.getName());
    }

    @Test
    public void shouldFindCorrectMethodWithNullParameter() throws Exception {
        Object[] params = new Object[] {null};
        Method method = NativeObjectUtils.getMethod(TestClass.class, "testMethodA", params);
        Assert.assertEquals("testMethodA", method.getName());
    }

    @Test
    public void shouldFindCorrectMethodWithInheritingParameters() throws Exception {
        Object[] params = new Object[] {new B()};

        Method method = NativeObjectUtils.getMethod(TestClass.class, "testMethodA", params);
        Assert.assertEquals("testMethodA", method.getName());
    }

    @Test
    public void shouldThrowExceptionWhenMethodNotFound() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        Method method = NativeObjectUtils.getMethod(TestClass.class, "missingMethod", new Object[] {});
    }

    class A {
        public void methodOfA() {

        }
    }

    class B extends A {

    }

    class TestClass {
        public void testMethod() {

        }
        public void testMethodA(A arg) {
        }
        public void testMethodB(B arg) {
        }

    }

}
