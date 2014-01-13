package Dilan;

import static jdk.nashorn.internal.runtime.ECMAErrors.typeError;

import jdk.nashorn.internal.objects.annotations.*;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

import java.io.IOException;

import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

@ScriptClass("JaggeryObj")
public class JaggeryObj extends ScriptObject {

    public volatile static Object natives;
    private static ScriptObject   builtinNatives;

    public volatile static Object contextify;
    private static ScriptObject   builtinContextify;


    // initialized by nasgen
    private static PropertyMap $nasgenmap$;

    static PropertyMap getInitialMap() {
        return $nasgenmap$;
    }

    //read only properties
    /*
    @return jag current version;
    * */
    @Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object version = version();

    @Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object moduleLoadList = UNDEFINED;  //[Object Array]

    @Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object arch = UNDEFINED; //which architecture we running on

    @Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object platform = UNDEFINED;

    @Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object argv = UNDEFINED; //commandline args

    @Property(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static final Object env = UNDEFINED;  //an object contain user environ


    /**
     *Jaggery bind implementation
     *
     * @param self  self reference
     * @param value     argument for bind function
     *
     * @return Javascript Object of relevant binding
     */
    @Function(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static Object bind(final Object self, final Object value) {
        final boolean hasArgs = value != null;
        final String key = hasArgs ? (String)value : null;

        if(key == null){
            throw typeError("WARNING <JAGGERY> : no.arguments.to.function " , ScriptRuntime.safeToString(key) ) ;
        } else {

            Object result = UNDEFINED;
            switch (key){
                case "natives":
                    result = getNatives();
                    break;

                case "contextify":
                    result = getContextify();
                    break;

                case "fs":
                    break;
            }
            return result;
        }
    }

    /**
     *jag.chdir() implementation
     *
     * @param self  self reference
     * @param value  directory to go
     *
     * @return change dir from curr directory upon given dir
     */
    @Function(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR) //if no param what todo
    public static Object chdir(final Object self, final Object value) {
        return UNDEFINED;
    }

    /**
     *jag.cwd() implementation
     *
     * @param self  self reference
     * @param value  empty
     *
     * @return return the current working dir
     */
    @Function(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static Object cwd(final Object self, final Object value) {
        return UNDEFINED;
    }

    /**
     *jag.exit() bind implementation
     *
     * @param self  self reference
     * @param value  empty
     *
     * @return exit jaggery
     */
    @Function(attributes = Attribute.CONSTANT, where = Where.CONSTRUCTOR)
    public static Object exit(final Object self, final Object value) {
        return UNDEFINED;
    }





    /*var foo = jag.bind('contextify').implementation;
    * */
    private static Object getContextify() {
        builtinContextify = initConstructor("Contextify");
        contextify        = builtinContextify;
        return contextify;
    }

    private static Object getNatives() {
        builtinNatives = initConstructor("Natives");
        natives        = builtinNatives;
        return natives;
    }

    @Override
    public String getClassName() {
        return "JAGGERY";
    }

    public static String version(){
        return "jaggery2.0";
    }

    private static ScriptObject initConstructor(final String name) {
        try {
            final StringBuilder sb = new StringBuilder("Dilan.");

            sb.append("Jaggery");
            sb.append(name);
            sb.append("$Constructor");

            final Class<?>     funcClass = Class.forName(sb.toString());
            final ScriptObject res       = (ScriptObject)funcClass.newInstance();

            return res;

        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //run this as a seperate program using ant while project is building
    //read core js files in src/lib
    public void readCore() throws IOException {

       /* final StringBuilder libPath = new StringBuilder(currDir);
        libPath.append("");//append lib path from current dir

        File libDir = new File(libPath.toString());
        if( !libDir.exists() || !libDir.isDirectory()){
            //throw an error.
            return;
        }

        final File[] jsFile = libDir.listFiles();
        for(final File js : jsFile){
            if( !js.isFile() &&  !js.getName().endsWith(".js")){
                continue;
            }
            Source jsSource = new Source(js.getName(), js);
            property.put(js.getName() ,jsSource.getString());
        } */
    }
}
