/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

//

package Dilan.src;

//we must set a prototype to this function
//these classes cannot inherite from scriptObjectMirror class (no default constructror, constructor is pack private)

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.objects.PrototypeObject;
import jdk.nashorn.internal.runtime.*;
import jdk.nashorn.internal.runtime.options.Options;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;


public final class ContextifyImpl {

    public Object getMethod(final String method){
        Object methodObject = null;
        switch (method){
            case "isContext":
                methodObject =  new IsContext();
                break;

            case "makeContext" :
                methodObject = new MakeContext();
                break;

            case "contextifyScript" :
                methodObject = new ContextifyScript();
        }
        return methodObject;
    }


    private static final class MakeContext extends JSObject {

        private static PropertyMap maps$;
        private static String CLASSNAME = "MakeContext";

        static PropertyMap getInitialMap() {
            return maps$;
        }

        @Override
        public Object call(String s, Object... objects) {
            //throw ecma error
            return UNDEFINED;
        }

        @Override
        public Object newObject(String s, Object... objects) {
            //create new context and return it
            //don't allow to create multiple context;
            return createContext();
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

    private static final class IsContext extends JSObject{

        private static PropertyMap maps$;
        private final String CLASSNAME = "IsContext";

        static PropertyMap getInitialMap() {
            return maps$;
        }

        @Override
        public Object call(String s, Object... objects) {
            ErrorManager warning = new ErrorManager();
            warning.warning("WARNING <JAGGERY> : " + CLASSNAME + " must call as a Constructor");

            return warning;
        }

        @Override
        public Object newObject(String s, Object... objects) {
            final int arglength =  objects.length;
            final boolean hasArgs = arglength > 0 ;

            if(!hasArgs){
                //throw error
                System.out.print("sandbox must be a object");
            }
        //this or following approch is good.
            final Object sandbox = objects[0];
            return 4;

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

    private static final class ContextifyScript extends JSObject{

        private static PropertyMap maps$;
        private final String CLASSNAME = "ContextifyScript";

        static PropertyMap getInitialMap() {
            return maps$;
        }

        @Override
        public Object call(String s, Object... objects) {

            ErrorManager warning = new ErrorManager();
            warning.warning("WARNING <JAGGERY> : " + CLASSNAME + " must call as a Constructor");

            return warning;
        }

        @Override
        public Object newObject(final String s, final Object... objects) {
            //only two parameters code and option

            final int arglength = objects.length;
            final boolean hasArgs = arglength > 0 ;
            final Object code = hasArgs ? objects[0] : UNDEFINED;
      //---- check what happen if really no args.

            Object trimCode = (code instanceof ConsString) ? (ConsString)code :  (String)code;

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

            final ScriptFunction func = context.compileScript(new Source("filename",trimCode.toString()), global);
            //file name must be the filename stored in the js object in the pro('natives');

            if(func == null || errors.getNumberOfErrors() != 0 ){
                //sent javascript error to the output
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
              //check if flush is suitable here;
            }

            return function;
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

    private static Context createContext(){

        final ErrorManager errors = new ErrorManager();
        final Options option = new Options("nashorn");
        final Context context = new Context(option, errors, Thread.currentThread().getContextClassLoader());

        return context;
    }
}
