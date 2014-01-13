/*plesase ignor this class
* implemented in jaggery2 as readCore method*/

/*package Dilan;
//delete this class;
import jdk.nashorn.internal.runtime.Source;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.HashMap;

public class ReadSource {

    //make volatile
    final private HashMap<Object, Object> property = new HashMap<>();


    public ReadSource() throws IOException {
        readCore();
    }

    public HashMap<Object, Object> sourceString(){
        return property;
    }

    public void readCore() throws IOException {

        /*final StringBuilder libPath = new StringBuilder(currDir);
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
        }
    }
}       */
