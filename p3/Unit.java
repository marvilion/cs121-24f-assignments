import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class Unit {
    public static Map<String, Throwable> testClass(String name) {
        Map<String, Throwable> results = new HashMap<>();
        try {
            
            Class<?> clazz = Class.forName(name);
            Object instance;

            try {
                instance = clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException("Error in creating an instance of the following test class: " + name, e);
            }
            
            List<Method> beforeClassMethods = getAnnotatedMethods(clazz, BeforeClass.class);
            List<Method> beforeMethods = getAnnotatedMethods(clazz, Before.class);
            List<Method> testMethods = getAnnotatedMethods(clazz, Test.class);
            List<Method> afterMethods = getAnnotatedMethods(clazz, After.class);
            List<Method> afterClassMethods = getAnnotatedMethods(clazz, AfterClass.class);

            validateStaticMethods(beforeClassMethods);
            validateStaticMethods(afterClassMethods);

            try {
                invokeOrderedMethods(beforeClassMethods, null);
            } catch (Throwable t) {
                throw new RuntimeException("Exception in @BeforeClass method", t);
            }


            for (Method testMethod : testMethods){
                try {
                    invokeOrderedMethods(beforeMethods, instance);

                    Throwable result = invokeMethod(testMethod, instance);
                    results.put(testMethod.getName(), result);

                    invokeOrderedMethods(afterMethods, instance);

                } catch (Throwable t) {
                    results.put(testMethod.getName(), t);
                }
            }


            try {
                invokeOrderedMethods(afterClassMethods, null);
            } catch (Throwable t) {
                throw new RuntimeException("Exception in @AfterClass method", t);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error in initializing the test class", e);
        }

        return results;
    }

    public static Map<String, Object[]> quickCheckClass(String name) {
	throw new UnsupportedOperationException();
    }

    private static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
    return Arrays.stream(clazz.getDeclaredMethods())
                 .filter(method -> method.isAnnotationPresent(annotation))
                 .sorted(Comparator.comparing(Method::getName)) // way to sort alphabetically
                 .collect(Collectors.toList());
    }

    private static Throwable invokeMethod(Method method, Object instance) {
        try {
            method.invoke(instance);
            return null; // case where test has passed
        } catch (InvocationTargetException e) {
            return e.getCause();
        } catch (IllegalAccessException e) {
            return e;
        }
    }

    private static void invokeOrderedMethods(List<Method> methods, Object instance) throws Exception {
        for (Method method : methods) {
            Throwable t = invokeMethod(method, instance);
            if (t != null) {
                throw new RuntimeException("Exception in ordered method: " + method.getName(), t);
            }
        }
    }

    private static void validateStaticMethods(List<Method> methods) {
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