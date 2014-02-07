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

    private static final MethodHandle RUN_IN_NEW_CONTEXT      = findOwnMH("runInNewContext", Object.class, Object.class);
    private static final MethodHandle IS_CONTEXT            = findOwnMH("isContext", Object.class, Object.class);
    private static final MethodHandle CONTEXTIFY_SCRIPT     = findOwnMH("contextifyScript", Object.class, Object.class);
    private static final MethodHandle COMPILE_SCRIPT        = findOwnMH("compileScript", Object.class, Object.class);
    private static final MethodHandle RUN_SCRIPT            = findOwnMH("runScripts", Object.class, Object.class);

    static {

        final ArrayList<jdk.nashorn.internal.runtime.Property> properties = new ArrayList<>(4);

        properties.add(AccessorProperty.create("runInNewContext", Property.WRITABLE_ENUMERABLE_CONFIGURABLE,RUN_IN_NEW_CONTEXT, null));
        properties.add(AccessorProperty.create("isContext", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, IS_CONTEXT, null));
        properties.add(AccessorProperty.create("contextifyScript", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, CONTEXTIFY_SCRIPT, null));
        properties.add(AccessorProperty.create("compileScript", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, COMPILE_SCRIPT, null));
        properties.add(AccessorProperty.create("runScripts", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, RUN_SCRIPT, null));

        maps$ = PropertyMap.newMap(properties);
    }

    static PropertyMap getInitialMap() {
        return maps$;
    }

    public JaggeryContextify(){
        super(maps$);
    }


    private Object runInNewContextImpl(){
        return JaggeryContextifyImpl.getMethod("runInNewContext");
    }

    private Object isContextImpl(){
        return JaggeryContextifyImpl.getMethod("isContext");
    }

    private Object contextifyScriptImpl(){
        return JaggeryContextifyImpl.getMethod("contextifyScript");
    }

    private Object compileScript() {
        return JaggeryContextifyImpl.getMethod("compileScript");
    }

    private Object runScripts() {
        return JaggeryContextifyImpl.getMethod("runScript");
    }


    static Object runInNewContext(final Object self){
        return (self instanceof JaggeryContextify) ?
                ((JaggeryContextify)self).runInNewContextImpl() :
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

    static Object compileScript(final Object self) {
        return (self instanceof JaggeryContextify) ?
                ((JaggeryContextify)self).compileScript() :
                UNDEFINED;
    }

    static Object runScripts(final Object self) {
        return (self instanceof JaggeryContextify) ?
                ((JaggeryContextify)self).runScripts() :
                UNDEFINED;
    }

    @Override
    public String getClassName() {
        return "JaggeryContextify";
    }

    private static MethodHandle findOwnMH(final String name, final Class<?> rtype, final Class<?>... types) {
        return MH.findStatic(MethodHandles.lookup(), JaggeryContextify.class, name, MH.type(rtype, types));
    }
}


