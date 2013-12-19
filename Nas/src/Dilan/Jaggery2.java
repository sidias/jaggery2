package Dilan;

import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;

import java.io.IOException;

import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

public class Jaggery2 {


    private static final class JaggeryPro extends ScriptObject{

        private static PropertyMap $nasgenmap$;

        static PropertyMap getInitialMap() {
            return $nasgenmap$;
        }

        private static Object bind(final Object self, final Object x) throws IOException {

            final boolean hasArgs = (x != null);
            String proParam = hasArgs ? (String)x : UNDEFINED.toString();

            Object result = UNDEFINED;

            switch (proParam){
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
}
