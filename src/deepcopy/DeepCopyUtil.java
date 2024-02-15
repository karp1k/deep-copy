package deepcopy;

import deepcopy.entities.AnotherAnnotation;
import deepcopy.entities.Company;
import deepcopy.entities.Man;
import deepcopy.entities.SomeAnnotation;
import sun.reflect.ReflectionFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DeepCopyUtil {

    private static final Set<Class<?>> immutables = new HashSet<>();

    static {
        immutables.addAll(List.of(
                String.class,
                Short.class,
                Integer.class,
                Long.class,
                Float.class,
                Double.class,
                Boolean.class,
                BigDecimal.class,
                BigInteger.class,
                Class.class,
                Void.class
        ));
    }

    public static void main(String[] args) {
        var man = createARealMan();
        var copier = new DeepCopyUtil();
        var youAreJustACopy = copier.deepCopy(man);
    }

    public static Man createARealMan() {
        var man = new Man("Astartes", "Vic", 10,
                List.of("Martin Eden", "Anna Karenina"), new BigDecimal("30.1"), true);
        man.giveManADog();
        man.setParent1(new Man("Gassburg", "Henry", 35, List.of("The Art of War"), new BigDecimal("99999999"), false));
        man.addStaff("annotation", new AnotherAnnotation() {

                    @Override
                    public Class<? extends Annotation> annotationType() {
                        return SomeAnnotation.class;
                    }

                    @Override
                    public int someInt() {
                        return 0;
                    }

                    @Override
                    public Class<?> someClass() {
                        return Void.class;
                    }
                })
                .addStaff("object", new Company());
        man.getEnumeration().setCompany(new Company().setAddress("Georgia, Tbilisi"));
        return man;
    }

    // for inner class ref
    private final Map<Object, Object> clones = new HashMap<>();

    public <T> T deepCopy(T object) {
        if (object == null) {
            return null;
        }

        var copy = instance(object);
        clones.put(object, copy);
        if (copy == null) {
            return null;
        }
        var clazz = object.getClass();
        do {
            for (Field field : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    try {
                        var fieldValue = field.get(object);
                        var clone = clones.get(fieldValue);
                        Object copyFieldValue;
                        if (clone != null) {
                            copyFieldValue = clone;
                        } else {
                            copyFieldValue = deepCoyField(fieldValue);
                        }
                        field.set(copy, copyFieldValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } while ((clazz = clazz.getSuperclass()) != Object.class && clazz != null);

        return copy;
    }

    private Object deepCoyField(Object fieldValue) {
        if (fieldValue == null) {
            return null;
        }
        var type = fieldValue.getClass();

        if (type.isArray()) {
            if (type.getComponentType().isPrimitive()) {
                return fieldValue;
            } else {
                Object[] array = (Object[]) fieldValue;
                Object[] copyArray = (Object[]) Array.newInstance(type.getComponentType(), array.length);
                for (int i = 0; i < array.length; i++) {
                    copyArray[i] = deepCopy(array[i]);
                }
                return copyArray;
            }
        } else if (type.isPrimitive() || immutables.contains(type) || type.isSynthetic()) {
            return fieldValue;
        } else {
            return deepCopy(fieldValue);
        }
    }

    private static <T> T instance(T object) {
        var reflectionFactory = ReflectionFactory.getReflectionFactory();
        Constructor<T> constructor;
        try {
            constructor = (Constructor<T>) reflectionFactory.newConstructorForSerialization(object.getClass(), Object.class.getConstructor((Class[]) null));
            constructor.setAccessible(true);
            return constructor.newInstance((Object[]) null);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
