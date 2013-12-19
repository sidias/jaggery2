package Dilan;

import jdk.nashorn.internal.runtime.Source;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ReadSource {

    final private HashMap<Object, Object> property = new HashMap<>();


    public ReadSource() throws IOException {
        readCore();
        System.out.print("in readcore : "+property.size());
    }

    public HashMap<Object, Object> SourceString(){
        return property;
    }

    public void readCore() throws IOException {

        File file = new File("/home/buddhi/IdeaProjects/Jaggery2.0/libs/path.js");


        //this thing is complete.find a way around to read all js file in the directory
        Source source = new Source(file.getName(), file);

        //String sourceContent = source.getString();
          String sourceContent = "exports.name = 12; exports.age = function(){print('hello buddhi')}";

        property.put("path",sourceContent);
    }
}
