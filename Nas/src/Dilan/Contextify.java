/*make this as a template to every functionalities in jaggery Pro.bind object and call this class to register all methods to
  jaggery 2
*/

/*don't create prototype object for function.emit prototype object*/

//is this correct or do we need to initialize nasgen to generate this class
//inherited from prototype object class
package Dilan;


import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.*;
import jdk.nashorn.internal.runtime.arrays.ArrayData;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.*;

import static jdk.nashorn.internal.lookup.Lookup.MH;
import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

//this object cannot access directly.only access via Pro.bind('contextify');
public final class Contextify extends ScriptObject{

    private static final PropertyMap maps$;
    private final ContextifyImpl contextifyImpl;

    private static final MethodHandle MAKE_CONTEXT      = findOwnMH("makeContext", Object.class, Object.class);
    private static final MethodHandle IS_CONTEXT        = findOwnMH("isContext", Object.class, Object.class);
    private static final MethodHandle CONTEXTIFY_SCRIPT = findOwnMH("contextifyScript", Object.class, Object.class);

    static {

        final ArrayList<jdk.nashorn.internal.runtime.Property> properties = new ArrayList<>(3);

        properties.add(AccessorProperty.create("makeContext", Property.NOT_ENUMERABLE,MAKE_CONTEXT, null));
        properties.add(AccessorProperty.create("isContext", jdk.nashorn.internal.runtime.Property.NOT_ENUMERABLE, IS_CONTEXT, null));
        properties.add(AccessorProperty.create("contextifyScript", jdk.nashorn.internal.runtime.Property.NOT_ENUMERABLE, CONTEXTIFY_SCRIPT, null));
        maps$ = PropertyMap.newMap(properties);
    }

    static PropertyMap getInitialMap() {
        return maps$;
    }

    public Contextify(){
        super(maps$);
        contextifyImpl = (ContextifyImpl)initConstructors("ContextifyImpl");
    }


    private Object makeContextImpl(){
        return contextifyImpl.getMethod("makeContext");
    }

    private Object isContextImpl(){
        return contextifyImpl.getMethod("isContext");
    }

    private Object contextifyScriptImpl(){
        return contextifyImpl.getMethod("contextifyScript");
    }


    static Object makeContext(final Object self){
        return (self instanceof Contextify) ?
                ((Contextify)self).makeContextImpl() :
                UNDEFINED;
    }

    static Object isContext(final Object self){
        return (self instanceof Contextify) ?
                ((Contextify)self).isContextImpl() :
                UNDEFINED;
    }

    static Object contextifyScript(final Object self){
        return (self instanceof Contextify) ?
                ((Contextify)self).contextifyScriptImpl() :
                UNDEFINED;
    }

    @Override
    public String getClassName() {
        return "Contextify";
    }

    private static Object initConstructors(final String name) {
        try {

            final StringBuilder sb = new StringBuilder("Dilan.");
            sb.append(name);

            final Class<?>     funcClass = Class.forName(sb.toString());
            final Object res = funcClass.newInstance();

            return res;

        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static MethodHandle findOwnMH(final String name, final Class<?> rtype, final Class<?>... types) {
        return MH.findStatic(MethodHandles.lookup(), Contextify.class, name, MH.type(rtype, types));
    }
}


