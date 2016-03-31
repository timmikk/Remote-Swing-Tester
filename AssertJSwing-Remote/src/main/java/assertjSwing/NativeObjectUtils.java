package assertjSwing;

import org.assertj.core.util.introspection.Introspection;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by timo on 2.3.2016.
 */
public class NativeObjectUtils {

    public static  Object invokeMethod(Method method, Object invokeFrom, Object... parameters) {
        return invokeMethod(method, invokeFrom, Object.class, parameters);
    }

    public static  Object invokeMethod(String methodName, Object invokeFrom, Object... parameters) {
        Method method = getMethod(invokeFrom.getClass(), methodName, parameters);
        return invokeMethod(method, invokeFrom, Object.class, parameters);
    }

    public static  <T> T invokeMethod(String methodName, Object invokeFrom, Class<T> returnType, Object... parameters) {
        Method method = getMethod(invokeFrom.getClass(), methodName, parameters);
        return invokeMethod(method, invokeFrom, returnType, parameters);
    }

    public static  <T> T invokeMethod(Method method, Object invokeFrom, Class<T> returnType, Object... parameters) {
        method.getReturnType();
        T result = null;

        try {
            Object resultObject = method.invoke(invokeFrom, parameters);
            if(resultObject != null) {
                result = returnType.cast(resultObject);
            }
        } catch (IllegalAccessException e) {
            String message = String.format("Given method \"%s\" is not accessible in target class [%s]",
                    method.getName(), invokeFrom.getClass().toString());
            throw new IllegalArgumentException(message, e);
        } catch (InvocationTargetException e) {
            String message = String.format("Invocation of method [%s]  with target class [%s] with parameters [%s]failed with exception.",
                    method.toString(), invokeFrom.getClass().toString(), Arrays.toString(parameters));
            throw new RuntimeException(message, e.getCause());
        }
        return result;
    }

    public static Method getMethod(Class<?> type, String methodName, Object[] parameters) {
        if(type == null) {
            throw new NullPointerException("Type cannot be null");
        }
        if(methodName == null) {
            throw new NullPointerException("Method name cannot be null");
        }
        if(parameters == null) {
            throw new NullPointerException("Parameters cannot be null");
        }

        for (Method method : type.getMethods()) {
            if (methodName.equals(method.getName()) && method.getParameterCount() == parameters.length) {

                if(variablesMatchTypes(parameters, method.getParameterTypes()))
                    return method;
            }
        }
        String message = String.format("Given method name \"%s\" with parameter types [%s] is not supported by the target class [%s]",
                methodName, Arrays.toString(parameters), type.toString());
        throw new IllegalArgumentException(message);
    }

    static boolean variablesMatchTypes(Object[] variables, Class[] types) {
        for(int i = 0; i < variables.length; i++) {
            if(types[i] == null
                    || (variables[i] == null && types[i].isPrimitive())
                    || (variables[i] != null && !types[i].isAssignableFrom(variables[i].getClass()))) {

                return false;
            } //In other words variables[i] can be null if types[i] is not null or primitive type.
        }
        return true;
    }



    public static Map<String, MethodDescriptor> getMethods(Class type){
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(type);
        } catch (IntrospectionException e) {
            String message = "Failed to load methods for class " + type.getName();
            throw new RuntimeException(message, e);
        }
        MethodDescriptor[] descriptors = info.getMethodDescriptors();
        Map<String, MethodDescriptor> methodDescriptors = new HashMap<String, MethodDescriptor>(descriptors.length);
        for(MethodDescriptor descriptor : descriptors) {
            methodDescriptors.put(descriptor.getName(), descriptor);
        }
        return methodDescriptors;
    }

    public static <T> T getPropertyValue(String propertyName, Object propertyOwner, Class<T> returnType) {
        PropertyDescriptor property = Introspection.getProperty(propertyName, propertyOwner);
        return invokeMethod(property.getReadMethod(), propertyOwner, returnType);
    }

    public static void setPropertyValue(String propertyName, Object propertyOwner, Object value) {
        PropertyDescriptor property = Introspection.getProperty(propertyName, propertyOwner);
        invokeMethod(property.getWriteMethod(), propertyOwner, value);
    }
}
