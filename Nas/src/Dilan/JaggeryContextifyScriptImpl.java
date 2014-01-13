

package Dilan;

import static jdk.nashorn.internal.runtime.ECMAErrors.typeError;
import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.objects.annotations.Attribute;
import jdk.nashorn.internal.objects.annotations.Constructor;
import jdk.nashorn.internal.objects.annotations.Function;
import jdk.nashorn.internal.objects.annotations.ScriptClass;
import jdk.nashorn.internal.runtime.*;
import jdk.nashorn.internal.runtime.options.Options;


@ScriptClass("ContextifyScriptImpl")
public final class JaggeryContextifyScriptImpl extends ScriptObject {

    // initialized by nasgen
    private static PropertyMap $nasgenmap$;

    static PropertyMap getInitialMap() {
        return $nasgenmap$;
    }


    @Override
    public String safeToString() {
        return "[ContextifyScript " + toString() + "]";
    }

    @Override
    public String toString() {
        return "Buddhi nipun";
    }

    @Override
    public String getClassName() {
        return "ContextifyScript";
    }

    @Constructor(arity = 1)
    public static Object constructor(final boolean newObj, final Object self, final Object... value) {

        if (newObj) {

            final int arglength = value.length;
            //assert (arglength > 0 && arglength <= 2) : "parameter insufficient";
            final boolean hasArgs = ( arglength > 0 );

            final Object code = (arglength == 2) ? value[1] : UNDEFINED;
            if(code.equals(UNDEFINED)) {
                typeError(Context.getGlobal(), "parameter insufficient");
            }
            final Object fn = value[0];
            final Object fileName = hasArgs ? (fn instanceof ConsString ? (ConsString)fn : (String)fn ):
                    null;
            //---- check what happen if really no args.

            Object trimCode = (code instanceof ConsString) ? (ConsString)code : (String)code;

            //remove end and begin ( );
            //trimCode = trimCode.substring(1,trimCode.length()-1);

            // code.toString == '(function (exports, require, module, __filename, __dirname) { });  */
            //remember to use ; end of this function otherwise it will throw an error.

            Object function = null;

            // new context to execute script file
            //this code segment equal to shell script execution same thing this function run on the
            //top of new context;
            Context context = createContext();
            final ScriptObject global = context.createGlobal();
            ErrorManager errors = context.getErrorManager();

            final ScriptFunction func = context.compileScript(new Source(fileName.toString(),trimCode.toString()), global);
            //file name must be the filename stored in the js object in the jag('natives');

            if(func == null || errors.getNumberOfErrors() != 0 ){
                typeError(global, "filename and code must be supplied");
            }

            try{
                function = ScriptRuntime.apply(func, global);
            } catch (final NashornException e){
                errors.error(e.toString());
                if(context.getEnv()._dump_on_error){
                    e.printStackTrace(context.getErr());
                }
            } finally {
                context.getOut().flush();
                context.getErr().flush();
            }

            return function;
        }

        ErrorManager warning = new ErrorManager();
        warning.warning("WARNING <JAGGERY> : ContextifyScript must call as a Constructor");
        return UNDEFINED;
    }

    private static Context createContext(){

        final ErrorManager errors = new ErrorManager();
        final Options option = new Options("nashorn");
        final Context context = new Context(option, errors, Thread.currentThread().getContextClassLoader());

        return context;
    }


}
