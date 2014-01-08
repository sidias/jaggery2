package Dilan.src;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.*;

import static jdk.nashorn.internal.runtime.ECMAErrors.typeError;
import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

public class BindImpl extends JSObject {

    private static String CLASSNAME = "BIND";

    @Override
    public Object call(String s, Object... objects) {
        final int arglength   = objects.length;
        final boolean hasArgs = arglength > 1;
        final String key = hasArgs ? (String)objects[1] : null;

        //parameters to jag.bind;
        if(key == null){
            throw typeError("WARNING <JAGGERY> : no.arguments.to.function " + CLASSNAME , ScriptRuntime.safeToString(key) ) ;
        } else {
            Object result = UNDEFINED;
            switch (key){
                case "natives":
                    result = nativesImpl();
                    break;
                case "contextify":
                    result = ContextifyScriptImpl();
                    break;
            }
            return result;
        }
    }

    @Override
    public Object newObject(String s, Object... objects) {
        ErrorManager em = Context.getContext().getErrorManager();
        em.warning("WARNING <JAGGERY> : " + CLASSNAME + " is not a constructor function");
        return UNDEFINED;
    }

    @Override
    public Object eval(String s) {
        return null;
    }

    @Override
    public Object getMember(String s) {
        return null;
    }

    @Override
    public Object getSlot(int i) {
        return null;
    }

    @Override
    public void removeMember(String s) {
    }

    @Override
    public void setMember(String s, Object o) {
    }

    @Override
    public void setSlot(int i, Object o) {
    }

    //var foo = Pro.bind('contextify').contextifyScript; implementation;
    private static Object ContextifyScriptImpl(){
        return initObject("Contextify");
        /* eg: var foo = Pro.bind('contextify'); ---------> this is a object
        *     var script = foo.contextifyScript; --------> this is a object of that function
        * */
    }

    private static Object nativesImpl(){
        return initObject("LoadNatives");
    }


    private static ScriptObject initObject(final String name) {
        try {
            final StringBuilder sb = new StringBuilder("Dilan.");
            sb.append(name);

            final Class<?>     funcClass = Class.forName(sb.toString());
            final ScriptObject res       = (ScriptObject)funcClass.newInstance();
            if(res.getProto() != null){
                res.remove(res.getProto());
            }
            return res;

        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
