package deepcopy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** @see stole from here https://github.com/kostaskougios/cloning  */
class SunReflectionFactoryHelper {
    SunReflectionFactoryHelper() {
    }

    public static <T> Constructor<T> newConstructorForSerialization(Class<T> type, Constructor<?> constructor) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        Class<?> reflectionFactoryClass = getReflectionFactoryClass();
        Object reflectionFactory = createReflectionFactory(reflectionFactoryClass);
        Method newConstructorForSerializationMethod = getNewConstructorForSerializationMethod(reflectionFactoryClass);
        return (Constructor)newConstructorForSerializationMethod.invoke(reflectionFactory, type, constructor);
    }

    private static Class<?> getReflectionFactoryClass() throws ClassNotFoundException {
        return Class.forName("sun.reflect.ReflectionFactory");
    }

    private static Object createReflectionFactory(Class<?> reflectionFactoryClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = reflectionFactoryClass.getDeclaredMethod("getReflectionFactory");
        return method.invoke((Object)null);
    }

    private static Method getNewConstructorForSerializationMethod(Class<?> reflectionFactoryClass) throws NoSuchMethodException {
        return reflectionFactoryClass.getDeclaredMethod("newConstructorForSerialization", Class.class, Constructor.class);
    }
}
