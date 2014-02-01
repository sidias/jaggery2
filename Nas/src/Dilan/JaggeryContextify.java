/*make this as a template to every functionalities in jaggery Pro.bind object and call this class to register all methods to
  jaggery 2
*/

/*don't create prototype object for function.emit prototype object*/

//is this correct or do we need to initialize nasgen to generate this class
//inherited from prototype object class
package Dilan;


import jdk.nashorn.internal.runtime.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.*;

import static jdk.nashorn.internal.lookup.Lookup.MH;
import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

//this object cannot access directly.only access via jag.bind('contextify');
public final class JaggeryContextify extends ScriptObject{

    private static final PropertyMap maps$;
    private final JaggeryContextifyImpl jaggeryContextifyImpl;

    private static final MethodHandle MAKE_CONTEXT      = findOwnMH("makeContext", Object.class, Object.class);
    private static final MethodHandle IS_CONTEXT        = findOwnMH("isContext", Object.class, Object.class);
    private static final MethodHandle CONTEXTIFY_SCRIPT = findOwnMH("contextifyScript", Object.class, Object.class);

    static {

        final ArrayList<jdk.nashorn.internal.runtime.Property> properties = new ArrayList<>(3);

        properties.add(AccessorProperty.create("makeContext", Property.WRITABLE_ENUMERABLE_CONFIGURABLE,MAKE_CONTEXT, null));
        properties.add(AccessorProperty.create("isContext", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, IS_CONTEXT, null));
        properties.add(AccessorProperty.create("contextifyScript", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, CONTEXTIFY_SCRIPT, null));

        maps$ = PropertyMap.newMap(properties);
    }

    static PropertyMap getInitialMap() {
        return maps$;
    }

    public JaggeryContextify(){
        super(maps$);
        jaggeryContextifyImpl = (JaggeryContextifyImpl)initObject("JaggeryContextifyImpl");
    }


    private Object makeContextImpl(){
        return jaggeryContextifyImpl.getMethod("makeContext");
    }

    private Object isContextImpl(){
        return jaggeryContextifyImpl.getMethod("isContext");
    }

    private Object contextifyScriptImpl(){
        return jaggeryContextifyImpl.getMethod("contextifyScript");
    }


    static Object makeContext(final Object self){
        return (self instanceof JaggeryContextify) ?
                ((JaggeryContextify)self).makeContextImpl() :
                UNDEFINED;
    }

    static Object isContext(final Object self){
        return (self instanceof JaggeryContextify) ?
                ((JaggeryContextify)self).isContextImpl() :
                UNDEFINED;
    }

    static Object contextifyScript(final Object self){
        return (self instanceof JaggeryContextify) ?
                ((JaggeryContextify)self).contextifyScriptImpl() :
                UNDEFINED;
    }

    @Override
    public String getClassName() {
        return "JaggeryContextify";
    }

    private static Object initObject(final String name) {
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
        return MH.findStatic(MethodHandles.lookup(), JaggeryContextify.class, name, MH.type(rtype, types));
    }
}


