package Dilan;

import jdk.nashorn.internal.runtime.ScriptObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NasAccess {

    static ScriptObject prototypeAccess() {
        Constructor<?> constructor;

        try {
            Class<?> proto = Class.forName("jdk.nashorn.internal.objects.PrototypeObject");
            constructor = proto.getDeclaredConstructor();
            constructor.setAccessible(true);

            return (ScriptObject) constructor.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

}
