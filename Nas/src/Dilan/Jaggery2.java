package Dilan;

import jdk.nashorn.internal.runtime.*;

import java.io.IOException;
import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static jdk.nashorn.internal.lookup.Lookup.MH;
import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

public class Jaggery2 {

    final private static HashMap<String, String> property = new HashMap<>();

    public static ScriptObject getJagGlobal(){
        final ScriptObject pros = new JaggeryPro();
        return pros;
    }

    public static HashMap<String, String> sourceString(){
        return property;
    }

    //class represent jaggery2 Pro Object
    private static final class JaggeryPro extends ScriptObject {

        private static PropertyMap $nasgenmap$;

        JaggeryPro(){
            super($nasgenmap$);

            //delete later.test purpose only
            try {
                readCore();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        static PropertyMap getInitialMap() {
            return $nasgenmap$;
        }

        private static final MethodHandle GET_BIND              = findOwnMH("get_bind", Object.class, Object.class);
        private static final MethodHandle GET_VERSION           = findOwnMH("get_version", Object.class, Object.class);
        private static final MethodHandle GET_MODULE_LOAD_LIST  = findOwnMH("get_module_load_list",Object.class, Object.class);
        private static final MethodHandle GET_ARCH              = findOwnMH("get_arch", Object.class, Object.class);
        private static final MethodHandle GET_PLATFORM          = findOwnMH("get_platform", Object.class, Object.class);
        private static final MethodHandle GET_FILES             = findOwnMH("get_files", Object.class, Object.class);
        private static final MethodHandle GET_ARGV              = findOwnMH("get_argv", Object.class, Object.class);
        private static final MethodHandle GET_ENV               = findOwnMH("get_env", Object.class, Object.class);
        private static final MethodHandle GET_CWD               = findOwnMH("get_cwd", Object.class, Object.class);

        static {

            final ArrayList<Property> properties = new ArrayList<>();
            properties.add(AccessorProperty.create("bind", Property.WRITABLE_ENUMERABLE_CONFIGURABLE,GET_BIND, null));
            properties.add(AccessorProperty.create("version", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_VERSION, null));
            properties.add(AccessorProperty.create("moduleLoadList", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_MODULE_LOAD_LIST, null));
            properties.add(AccessorProperty.create("arch", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_ARCH, null));
            properties.add(AccessorProperty.create("platform", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_PLATFORM, null));
            properties.add(AccessorProperty.create("files", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_FILES, null));
            properties.add(AccessorProperty.create("argv", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_ARGV, null));
            properties.add(AccessorProperty.create("env", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_ENV, null));
            properties.add(AccessorProperty.create("cwd", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_CWD, null));

            $nasgenmap$ = PropertyMap.newMap(properties);
        }

        static Object get_bind(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getBind() :
                    UNDEFINED;
        }

        static Object get_version(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getVersion() :
                    UNDEFINED;
        }

        static Object get_module_load_list(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getModuleLoadList() :
                    UNDEFINED;
        }

        static Object get_arch(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getArch() :
                    UNDEFINED;
        }

        static Object get_platform(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getPlatform() :
                    UNDEFINED;
        }

        static Object get_files(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getFiles() :
                    UNDEFINED;
        }

        static Object get_argv(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getArgv() :
                    UNDEFINED;
        }

        static Object get_env(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getEnv() :
                    UNDEFINED;
        }

        static Object get_cwd(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getCwd() :
                    UNDEFINED;
        }

        private Object getBind() {
            return new JaggeryBindImpl();
        }

        private Object getVersion() {
            return "jaggery 2.0";
        }

        private Object getModuleLoadList() {
            return 1;
        }

        private Object getArch() {
            return System.getProperty("os.arch");
        }

        private Object getPlatform() {
            return System.getProperty("os.name");
        }

        /*
        files need to execute via jaggery.command line second argument
        */
        private Object getFiles() {
            final ScriptEnvironment env = Context.getContext().getEnv();
            final List<String> files = env.getFiles();
            if(files.isEmpty()) {
                //in REPL mode.
                return UNDEFINED;       //change if need
            }
            return files;
        }

        private Object getArgv() {
            final ScriptEnvironment env = Context.getContext().getEnv();
            final List<String> args = env.getFiles();
            if(args.isEmpty()) {
                return UNDEFINED;
            }
            return args;
        }

        private Object getEnv() {
            return System.getenv();
        }

        private Object getCwd() {
            return System.getProperty("user.dir");
        }

        private static MethodHandle findOwnMH(final String name, final Class<?> rtype, final Class<?>... types) {
            return MH.findStatic(MethodHandles.lookup(), JaggeryPro.class, name, MH.type(rtype, types));
        }

        @Override
        public String getClassName() {
            return "JAGGERY";
        }

        //eager boostrap method.read to source at the build
        //read core js files in src/lib
        public void readCore() throws IOException {

            final StringBuilder libPath = new StringBuilder("/home/buddhi/IdeaProjects/Nas/src/Dilan/lib");
            //libPath.append("");//append lib path from current dir

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
                String fileName = js.getName().replace( ".js","");
                property.put(fileName ,jsSource.getString());
            }
        }
    }


}
