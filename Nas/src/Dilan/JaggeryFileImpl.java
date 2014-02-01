package Dilan;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.ConsString;

import jdk.nashorn.internal.runtime.Source;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JaggeryFileImpl {

    public static Object getStatPath() {
        return new Statpath();
    }

    public static Object getReadContent() {
        return new ReadFile();
    }

    private static final class Statpath extends JSObject {

        @Override
        public Object call(String s, Object... objects) {

            final boolean hasArgs = objects.length == 2;
            final Object file = (objects[1] instanceof ConsString) ? (ConsString)objects[1] : (String)objects[1];
            Path fileName = null;
            if(hasArgs) {
                fileName = Paths.get(file.toString());
            } else {
                //throw an error to script env
            }

            boolean isRegularReadableFile;
            try {
                isRegularReadableFile = Files.isRegularFile(fileName) & Files.isReadable(fileName);

                if(isRegularReadableFile) {
                    boolean pathType = Files.isRegularFile(fileName) && (file.toString().contains(".js") ||
                            file.toString().contains(".jag"));

                    if(pathType) {
                        return file;
                    }
                } else {
                    boolean pathType = Files.isDirectory(fileName);
                    if(pathType) {
                        return file;
                    }
                }
            }catch(SecurityException se) {
                //throw error to script env
            }
            return false;
        }

        @Override
        public Object newObject(String s, Object... objects) {
            return null;
        }

        @Override
        public Object eval(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getMember(String s) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getSlot(int i) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void removeMember(String s) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setMember(String s, Object o) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setSlot(int i, Object o) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private static final class ReadFile extends JSObject {

        @Override
        public Object call(String s, Object... objects) {
            StringBuffer sbf = new StringBuffer();

            final boolean hasArgs = objects.length == 2;
            final Object file = (objects[1] instanceof ConsString) ? (ConsString)objects[1] : (String)objects[1];
            Path fileName = null;
            if(hasArgs) {
                fileName = Paths.get(file.toString());
            } else {
                //throw an error to script env
            }

            /*String line = null;
            Charset charset = StandardCharsets.UTF_8;
            try(BufferedReader reader = Files.newBufferedReader(fileName, charset)) {
                while ((line = reader.readLine()) != null) {
                    sbf.append(line);
                    //System.out.print(line);
                }
            } catch (IOException e) {
                System.out.print("error occours");
            }
            return sbf;    */
            File filePath = fileName.toFile();
            Source source = null;
            try {
                source = new Source(fileName.toString(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return source.getString();
        }

        @Override
        public Object newObject(String s, Object... objects) {
            return null;
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
    }
}
