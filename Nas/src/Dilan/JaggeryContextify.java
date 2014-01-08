package Dilan;

import Dilan.src.Contextify;
import jdk.nashorn.internal.objects.annotations.*;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

@ScriptClass("Contextify")
public class JaggeryContextify extends ScriptObject {

    private volatile static Object contextify_Script;
    private static ScriptFunction builtinContextifyScript;

    private volatile static Object make_Context;
    private static ScriptFunction builtinMakeContext;

    // initialized by nasgen
    private static PropertyMap $nasgenmap$;

    private JaggeryContextify() {
        // don't create me!
        throw new UnsupportedOperationException();
    }

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object contextifyScript = contextifyScriptImpl();

    @jdk.nashorn.internal.objects.annotations.Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object makeContext = makeContextImpl();

    /**
     * var isContext = jag.bind('contextify').isContext; implementation
     *
     * @param self  self reference
     * @param x     argument
     *
     * @return script function
     */
    @Function(attributes = Attribute.NOT_ENUMERABLE,  where = Where.CONSTRUCTOR)
    public static Object isContext(final Object self, final Object x) {
        return UNDEFINED;
        //tru ScriptObject.isOfContext() method;
    }

    /*
    * make Context implementation
    * */
    private static Object makeContextImpl() {
        builtinMakeContext = (ScriptFunction)initConstructor("MakeContextImpl");
        make_Context = builtinMakeContext;
        return make_Context;
    }

    /*
    * contextifyScript Implementation
    * */
    private static Object contextifyScriptImpl() {
        builtinContextifyScript = (ScriptFunction)initConstructor("ContextifyScriptImpl");
        contextify_Script = builtinContextifyScript;
        return contextify_Script;
    }


    private static ScriptObject initConstructor(final String name) {
        try {
            // Assuming class name pattern is JaggeryClassName ;
            final StringBuilder sb = new StringBuilder("Dilan.");

            sb.append("Jaggery");
            sb.append(name);
            sb.append("$Constructor");

            final Class<?>     funcClass = Class.forName(sb.toString());
            final ScriptObject res       = (ScriptObject)funcClass.newInstance();

            if (res instanceof ScriptFunction) {
                // All global constructor prototypes are not-writable,
                // not-enumerable and not-configurable.
                final ScriptFunction func = (ScriptFunction)res;
                func.modifyOwnProperty(func.getProperty("prototype"), Attribute.NON_ENUMERABLE_CONSTANT);
            }

            if (res.getProto() == null) {
                System.out.print("prototype is null");
                //res.setProto(getObjectPrototype());
            }

            return res;

        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
