package deepcopy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/** @see stole from here https://github.com/kostaskougios/cloning  */
public class SunReflectionFactoryInstantiator<T> {

    private final Constructor<T> mungedConstructor;

    public SunReflectionFactoryInstantiator(Class<T> type) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Constructor<Object> javaLangObjectConstructor = getJavaLangObjectConstructor();
        this.mungedConstructor = SunReflectionFactoryHelper.newConstructorForSerialization(type, javaLangObjectConstructor);
        this.mungedConstructor.setAccessible(true);
    }

    public T newInstance() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return this.mungedConstructor.newInstance((Object[])null);
    }

    private static Constructor<Object> getJavaLangObjectConstructor() throws NoSuchMethodException {
        return Object.class.getConstructor((Class[])null);
    }
}
