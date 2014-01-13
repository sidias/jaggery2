package Dilan;

import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Property;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;

import static jdk.nashorn.internal.lookup.Lookup.MH;
import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

public class Jaggery2 {

    final private static HashMap<Object, Object> property = new HashMap<>();

    public static ScriptObject getJagGlobal(){
        final ScriptObject pros = new JaggeryPro();
        return pros;
    }

    public static HashMap<Object, Object> sourceString(){
        return property;
    }

    //class represent jaggery2 Pro Object
    private static final class JaggeryPro extends ScriptObject {

        private static PropertyMap $nasgenmap$;

        JaggeryPro(){
            super($nasgenmap$);
        }

        static PropertyMap getInitialMap() {
            return $nasgenmap$;
        }

        private static final MethodHandle GET_CACHE = findOwnMH("getCache", Object.class,Object.class);
        private static final MethodHandle GET_BIND  = findOwnMH("getBind", Object.class, Object.class);

        static {

            final ArrayList<Property> properties = new ArrayList<>();
            properties.add(AccessorProperty.create("caches", Property.WRITABLE_ENUMERABLE_CONFIGURABLE, GET_CACHE, null));
            properties.add(AccessorProperty.create("bind", Property.WRITABLE_ENUMERABLE_CONFIGURABLE,GET_BIND, null));

            $nasgenmap$ = PropertyMap.newMap(properties);
        }


        static Object getCache(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getModule() :
                    UNDEFINED;
        }

        static Object getBind(final Object self) {
            return (self instanceof JaggeryPro) ?
                    ((JaggeryPro)self).getBind() :
                    UNDEFINED;
        }

        private Object getModule(){
            return 1;
        }

        private Object getBind(){
            return new BindImpl();
        }

        private static MethodHandle findOwnMH(final String name, final Class<?> rtype, final Class<?>... types) {
            return MH.findStatic(MethodHandles.lookup(), JaggeryPro.class, name, MH.type(rtype, types));
        }

        @Override
        public String getClassName() {
            return "JAGGERYPROCESS";
        }
    }

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
