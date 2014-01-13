package Dilan;


import jdk.nashorn.internal.objects.annotations.*;
import jdk.nashorn.internal.runtime.*;

import java.util.HashMap;

import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

@ScriptClass("Natives")
public class JaggeryNatives extends ScriptObject {

    // initialized by nasgen
    private static PropertyMap $nasgenmap$;

    /*
    core modules in src/lib are read and stored as string.
    * */
    private static final HashMap<Object, Object> propertySet = new HashMap<>();


    private JaggeryNatives() {
        throw new UnsupportedOperationException();
    }

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object cache = getModule("cache");

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object debugger = getModule("debugger");

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object fs = getModule("fs");

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object module = getModule("module");

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object path = getModule("path");

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object querystring = getModule("querystring");

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object uri = getModule("uri");

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object util = getModule("util");

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object vm = getModule("vm");


    @Override
    public String getClassName() {
        return "Natives";
    }

    private static Object getModule(final String moduleName){
        String paths = "exports.name = 45; exports.age = function(){print('my age is 23')}";
        propertySet.put("path", paths);
        return propertySet.containsKey(moduleName) ? propertySet.get(moduleName) : UNDEFINED;
    }

}
