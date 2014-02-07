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

package Dilan;

//we must set a prototype to this function
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.PrototypeObject;
import jdk.nashorn.internal.objects.annotations.Attribute;
import jdk.nashorn.internal.runtime.*;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.options.Options;

import javax.naming.*;
import javax.naming.event.ObjectChangeListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;


public final class JaggeryContextifyImpl {

    public static Object getMethod(final String method){
        Object methodObject = null;
        switch (method){
            case "isContext":
                methodObject =  new IsContext();
                break;

            case "runInNewContext" :
                methodObject = new RunInNewContext();
                break;

            case "contextifyScript" :
                methodObject = new ContextifyScript();
                break;

            case "compileScript" :
                methodObject = new CompileScript();
                break;

            case "runScript" :
                methodObject = new RunScript();
                break;
        }
        return methodObject;
    }

    private static final class RunInNewContext extends JSObject{

        private static String CLASSNAME = "RunInNewContext";

        @Override
        public Object call(String s, Object... objects) {
            //throw ecma error
            return UNDEFINED;
        }

        @Override
        public Object newObject(String s, Object... objects) {

            Object function = UNDEFINED;
            final int argLength = objects.length;
            final boolean hasArgs = (argLength <= 2);

            if(!hasArgs){
                //handle exeception here
            }

            final Object code = (hasArgs) ? ((objects[1] instanceof ConsString) ? (ConsString)objects[1] :
                    (String)objects[1]) : UNDEFINED;

            final Object fileName = (hasArgs) ? ((objects[0] instanceof ConsString) ? (ConsString)objects[0] :
                    (String)objects[0]) : UNDEFINED;

            final Context context = Context.getContext();
            final ScriptObject newGlobal = context.createGlobal();
            final ErrorManager errorManager = context.getErrorManager();

            final ScriptFunction func = context.compileScript(new Source(fileName.toString(),code.toString()), newGlobal);

            if(func == null || errorManager.getNumberOfErrors() != 0 ){
                //sent javascript error to the output
            }

            try{
                function = ScriptRuntime.apply(func, newGlobal);
            } catch (final NashornException e){
                errorManager.error(e.toString());
                if(context.getEnv()._dump_on_error){
                    e.printStackTrace(context.getErr());
                }
            } finally {
                context.getOut().flush();
                context.getErr().flush();
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

        private final String CLASSNAME = "IsContext";

        @Override
        public Object call(String s, Object... objects) {
            final int argLength = objects.length;
            final boolean hasArgs = (argLength == 2);
            return null;
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

    private static final class ContextifyScript extends JSObject{

        private final String CLASSNAME = "ContextifyScript";

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
            final boolean hasArgs = arglength <= 2 ;
            if(!hasArgs) {
               //handle exeception here
            }

            final Object code = hasArgs ? objects[1] : UNDEFINED;
            //check what happen if really no args.

            Object trimCode = (code instanceof ConsString) ? (ConsString)code :  (String)code;

            //remove end and begin ( );
            //trimCode = trimCode.substring(1,trimCode.length()-1);

            // code.toString == '(function (exports, require, module, __filename, __dirname) { });  */
            //remember to use ; end of this function otherwise it will throw an error.
            Object function = UNDEFINED;

            //get current global and context to execute js file
            Context context = Context.getContext();
            final ScriptObject global = Context.getGlobal();
            final ErrorManager errors = context.getErrorManager();

            final ScriptFunction func = context.compileScript(new Source("",trimCode.toString()), global);
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

    private static final class CompileScript extends JSObject {

        @Override
        public Object call(String s, Object... objects) {

            final int arglength = objects.length;
            final boolean hasArgs = arglength >= 3 ;
            if(!hasArgs) {
                //handle exeception here
            }

            Object code = (hasArgs) ? ((objects[2] instanceof ConsString) ? (ConsString)objects[2] :
                    (String)objects[2]) : UNDEFINED ;

            Context context = Context.getContext();
            final ScriptObject global = Context.getGlobal();
            final ScriptFunction func = context.compileScript(new Source("",code.toString()), global);

            return func;
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

    private static final class RunScript extends JSObject {

        @Override
        public Object call(String s, Object... objects) {

            final int argLength = objects.length;
            final boolean hasArgs = (argLength >= 2);

            if(!hasArgs) {
                //handle error
            }
            Object param[] = objects;
            final Object func = (objects[1] instanceof ScriptFunction) ? param[1] : UNDEFINED;

            final Object[] args = (argLength > 2) ? Arrays.copyOfRange(param,2,argLength) : null;

            Object function = UNDEFINED;
            final Context context = Context.getContext();
            final ScriptObject global = Context.getGlobal();
            final ErrorManager eManager = context.getErrorManager();

            try{
                function = ScriptRuntime.apply((ScriptFunction)func, global, args);
            } catch (final NashornException e){
                eManager.error(e.toString());
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
