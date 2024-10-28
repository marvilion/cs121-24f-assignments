import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class Unit {
    public static Map<String, Throwable> testClass(String name) {
	throw new UnsupportedOperationException();
    }

    public static Map<String, Object[]> quickCheckClass(String name) {
	throw new UnsupportedOperationException();
    }

    private List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
    return Arrays.stream(clazz.getDeclaredMethods())
                 .filter(method -> method.isAnnotationPresent(annotation))
                 .sorted(Comparator.comparing(Method::getName)) // way to sort alphabetically
                 .collect(Collectors.toList());
    }

    private Throwable invokeMethod(Method method, Object instance) {
        try {
            method.invoke(instance);
            return null; // case where test has passed
        } catch (InvocationTargetException e) {
            return e.getCause();
        } catch (IllegalAccessException e) {
            return e;
        }
    }

    private void invokeOrderedMethods(List<Method> methods, Object instance) throws Exception {
        for (Method method : methods) {
            Throwable t = invokeMethod(method, instance);
            if (t != null) {
                throw new RuntimeException("Exception in ordered method: " + method.getName(), t);
            }
        }
    }

    private void validateStaticMethods(List<Method> methods) throws Exception {
        for (Method method : methods) {
            if (!Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException("Method " + method.getName() + " is not static");
            }
        }
    }

    private Map<String, Throwable> runTests(List<Method> testMethods, Object instance) {
        Map<String, Throwable> results = new HashMap<>();
        for (Method testMethod : testMethods) {
            Throwable result = invokeMethod(testMethod, instance);
            results.put(testMethod.getName(), result); // will contain either null for success or exception for failure
        }
        return results;
    }

}