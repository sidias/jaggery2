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

package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.objects.annotations.Attribute;
import jdk.nashorn.internal.objects.annotations.Function;
import jdk.nashorn.internal.objects.annotations.ScriptClass;
import jdk.nashorn.internal.objects.annotations.Where;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;

import static jdk.nashorn.internal.runtime.ScriptRuntime.UNDEFINED;

import java.io.IOException;

/**
 * ECMA 15.8 The Math Object
 *
 */
@ScriptClass("Pro")
public final class NativePro extends ScriptObject {

    // initialized by nasgen
    @SuppressWarnings("unused")
    private static PropertyMap $nasgenmap$;

    static PropertyMap getInitialMap() {
        return $nasgenmap$;
    }

    private NativePro() {
        // don't create me!
        throw new UnsupportedOperationException();
    }

    @Function(attributes = Attribute.NOT_ENUMERABLE, where = Where.CONSTRUCTOR)
    public static Object bind(final Object self, final Object x) throws IOException {

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


    //var foo = Pro.bind('contextify').contextifyScript; implementation;
    private static Object ContextifyScriptImpl(){
        return initConstructors("Contextify");

        /* eg: var foo = Pro.bind('contextify'); ---------> this is a object
        *     var script = foo.contextifyScript; --------> this is a object of that function
        * */
    }

    private static Object nativesImpl(){
        return initConstructors("LoadNatives");
    }


    private static ScriptObject initConstructors(final String name) {
        try {
            //DELETE PROTOTYPE OBJECT IN THIS CONTEXTIFY OBJECT.BECAUSE PROTOTYPE oBJECT IS CREATED AUTOMATICALLY

            final StringBuilder sb = new StringBuilder("Dilan.");
            sb.append(name);

            final Class<?>     funcClass = Class.forName(sb.toString());
            final ScriptObject res       = (ScriptObject)funcClass.newInstance();

            return res;

        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



}
