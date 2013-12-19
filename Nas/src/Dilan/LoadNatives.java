package Dilan;
//this process is need to happen with the startup of the engine.initializing globals.

//when adding new core module to the source this file must be updated.
import jdk.nashorn.internal.runtime.*;

import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.*;

import static jdk.nashorn.internal.lookup.Lookup.MH;

public class LoadNatives extends ScriptObject{

    //store all native module's source codes;
    private static final PropertyMap map$;
    private final HashMap<Object, Object> propertySet ;    //get data from read file

    private static final MethodHandle GET_CACHE = findOwnMH("getCache", Object.class,Object.class);
    private static final MethodHandle GET_DEBUGGER = findOwnMH("getDebugger", Object.class,Object.class);
    private static final MethodHandle GET_FS = findOwnMH("getFs", Object.class,Object.class);
    private static final MethodHandle GET_MODULE = findOwnMH("getModule", Object.class,Object.class);
    private static final MethodHandle GET_PATH = findOwnMH("getPath", Object.class,Object.class);
    private static final MethodHandle GET_QUERYSTRING = findOwnMH("getQueryString", Object.class,Object.class);
    private static final MethodHandle GET_URI = findOwnMH("getURI", Object.class,Object.class);
    private static final MethodHandle GET_UTIL = findOwnMH("getUtil", Object.class,Object.class);
    private static final MethodHandle GET_VM = findOwnMH("getVM", Object.class,Object.class);

    static {

        final ArrayList<Property> properties = new ArrayList<>();

        properties.add(AccessorProperty.create("cache", Property.NOT_ENUMERABLE, GET_CACHE, null));
        properties.add(AccessorProperty.create("debugger", Property.NOT_ENUMERABLE, GET_DEBUGGER, null));
        properties.add(AccessorProperty.create("fs", Property.NOT_ENUMERABLE, GET_FS, null));
        properties.add(AccessorProperty.create("module", Property.NOT_ENUMERABLE, GET_MODULE, null));
        properties.add(AccessorProperty.create("path", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_PATH, null));
        properties.add(AccessorProperty.create("querystring", Property.NOT_ENUMERABLE, GET_QUERYSTRING, null));
        properties.add(AccessorProperty.create("uri", Property.NOT_ENUMERABLE, GET_URI, null));
        properties.add(AccessorProperty.create("util", Property.NOT_ENUMERABLE, GET_UTIL, null));
        properties.add(AccessorProperty.create("vm", Property.NOT_ENUMERABLE, GET_VM, null));

        map$ = PropertyMap.newMap(properties);
    }


    public LoadNatives() throws IOException {
        super(map$);
        ReadSource r = new ReadSource();
        propertySet = r.SourceString();

    }

    static PropertyMap getInitialMap() {
        return map$;
    }


    static Object getCache(final Object self){
        return (self instanceof LoadNatives) ?
                ((LoadNatives)self).getCache("cache") :
                UNDEFINED;
    }

    static Object getDebugger(final Object self){
        return (self instanceof LoadNatives) ?
                ((LoadNatives)self).getModule("debugger") :
                UNDEFINED;
    }

    static Object getFs(final Object self){
        return (self instanceof LoadNatives) ?
                ((LoadNatives)self).getModule("fs") :
                UNDEFINED;
    }

    static Object getModule(final Object self){
        return (self instanceof LoadNatives) ?
                ((LoadNatives)self).getModule("module") :
                UNDEFINED;
    }

    static Object getPath(final Object self){
        return (self instanceof LoadNatives) ?
                ((LoadNatives)self).getModule("path") :
                UNDEFINED;
    }

    static Object getQueryString(final Object self){
        return (self instanceof LoadNatives) ?
                ((LoadNatives)self).getModule("querystring") :
                UNDEFINED;
    }

    static Object getURI(final Object self){
        return (self instanceof LoadNatives) ?
                ((LoadNatives)self).getModule("uri") :
                UNDEFINED;
    }

    static Object getUtil(final Object self){
        return (self instanceof LoadNatives) ?
                ((LoadNatives)self).getModule("util") :
                UNDEFINED;
    }

    static Object getVM(final Object self){
        return (self instanceof LoadNatives) ?
                ((LoadNatives)self).getModule("vm") :
                UNDEFINED;
    }

    private Object getModule(final String moduleName){
        return propertySet.containsKey(moduleName) ? propertySet.get(moduleName) : UNDEFINED;
    }


    private static MethodHandle findOwnMH(final String name, final Class<?> rtype, final Class<?>... types) {
        return MH.findStatic(MethodHandles.lookup(), LoadNatives.class, name, MH.type(rtype, types));
    }

    @Override
    public String getClassName() {
        return "natives";
    }
}
