package Dilan;

import jdk.nashorn.internal.runtime.*;
import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import static jdk.nashorn.internal.lookup.Lookup.MH;

public final class JaggeryFs extends ScriptObject {

    private static PropertyMap $nasgenmap$;

    JaggeryFs() {
        super($nasgenmap$);
    }

    static PropertyMap getInitialMap() {
        return $nasgenmap$;
    }

    private static MethodHandle STATPATH = findOwnMH("get_statPath", Object.class, Object.class);
    private static MethodHandle READPATH = findOwnMH("get_fileRead", Object.class, Object.class);

    static {
        final ArrayList<Property> properties = new ArrayList<>();
        properties.add(AccessorProperty.create("statPath", Property.WRITABLE_ENUMERABLE_CONFIGURABLE,STATPATH, null));
        properties.add(AccessorProperty.create("readFile", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, READPATH, null));

        $nasgenmap$ = PropertyMap.newMap(properties);
    }

    static Object get_statPath(final Object self) {
        return (self instanceof JaggeryFs) ?
                ((JaggeryFs)self).get_statPath() :
                UNDEFINED;
    }

    static Object get_fileRead(final Object self) {
        return  (self instanceof JaggeryFs) ?
                ((JaggeryFs)self).get_readFile() :
                UNDEFINED;
    }

    private Object get_statPath() {
        return JaggeryFileImpl.getStatPath();
    }

    private Object get_readFile() {
        return  JaggeryFileImpl.getReadContent();
    }

    private static MethodHandle findOwnMH(final String name, final Class<?> rtype, final Class<?>... types) {
        return MH.findStatic(MethodHandles.lookup(), JaggeryFs.class, name, MH.type(rtype, types));
    }
}
