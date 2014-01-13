/*
module 1k module folder 1n delete karoth cache 1n delte karala danna
foo
*/

//this module loading system works for user write modules only,
        /*there are two module system
            1.load jaggery native moudles
                (when jaggery call var sourced = Pro.binding('core_modules') jaggery send javascript object filled with key value pairs
                 key is moduleName and value is string represntation of the source )

            2.load user wrote modules to the context
                thee are multiple places that user can put there own modules.jaggery go to those loacations and load those files.
        */


var fs = require('fs');
var CoreModule = require('core_module');

function hasOwnProperty(object, property){
    Object.prototype.hasOwnProperty.call(object, property)
}

Module.initPath();// init all paths to search modules

function Module(id, parent){
    this.id = id;
    this.parent = parent;
    this.exports = {};

    if(parent && parent.children){
        parent.children.push(this);
    }

    this.children = [];
    this.loaded = false;
}

module.exports = Module;

Module._pathCache = {}; //eg requestpath:abs_path_of_requestpath
Module.moduleCache = {};
Module.extensions = {};
var modulePaths = [];

Module.wrap = CoreModule.wrap;
Module.wrapper = CoreModule.wrapper;

//find if there are package.json file.if not load index.jag or index.js
var packageCache = {};

/*this function will find package read once and cache it.it is confirm that file will read only once*/
/*before it confirm that there is a package.json file*/

//*************************complete*****************************
function readPackage(requestPath){

    if(hasOwnProperty(this.packageCache, requestPath)){
        return packageCache[requestPath];
    }

    try{
        var packagePath = path.resolvePath(requestPath,'package.json'); // path class will be called
        json_content = fs.read(packagePath, "utf8"); //this is a method from java class.use apache io.utils
    }catch(e){
      return false;
    }

    try{
    // convert json into javascript object(JSON.parse())
        var package = packageCache[requestPath] = JSON.parse(json_content);
    }catch(e){
        e.path = requestPath;
        e.message = 'ERROR PARSING JSON :' + e.path;
        throw e;
    }
    return package;
 }

//****************************completed***********************************
//main can be include js or jag file so we pass extension as a parameter
function tryPackage(requestPath, extension){

    var package = readPackage(requestPath);

    if(!package || !package.main){
        return false;
    }

    var mainModulePath = path.resolve(requestPath, package.main);
    return tryFile(mainModulePath) || tryExtensions(mainModulePath, extension); //hello can be a dir name then what should you do
    //extensions are check because main can be "main":"lib/hello" without extension. hello file can be .js or .jag file.
    //this main module must be a jag or js file.(.json is not allow here)
    //in this extension file don't send .json as an extension.
}

//***************************completed*********************************
  //this current dir need to be absolute.otherwise split won't work
function jaggery_modulePaths(currDir){

    var absPath = path.resolve(currDir);
    var pathsToLook = [];

    var parts = absPath.split('/'/*path.sep*/); /*eg - [home,buddhi,Desktop,foo]  */
    //windows waladi c://kiyala ena nisa eka poddak balanna.

    var path = '';

//loop will stop at when i =1 because i =0 canot be occour. index 0 is home dir.
    for(var i = parts.length-1; i > 0; i--){

        if(parts[i] === 'jaggery_module'){
            continue;
            //don't search inside jaggery_module/jaggery_module folder.
        }

        absPath = parts.slice(0, i+1);
        path = absPath.concat("jaggery_module").join(pathsep);
        pathsToLook.push(path);

    }
    return pathsToLook;
    /*
    sequence is,
    /home/buddhi/Desktop/my/jaggery_module
    /home/buddhi/Desktop/jaggery_module
    /home/buddhi/jaggery_module
    home/jaggery_module
    */
}

//*******************************************************************not completed****************************************************
function tryFile(requestPath){

     if(dir){
        return DIR;
     }

     if(){  //if file 1 exist and not a directory then return
        return absPath     //sometimes it may no need of nio
     }
     return false;

}
//***********************completed************************************
//given path will check with given extension
function tryExtensions(requestPath, extension){
    //all extension need to search will pass to extension param.
    var el = extension.length;
    for(var k= 0 ; k < el; i++ ){
        var file = tryFile(requestPath.concat(extension[k]));
    }

    if(file){
        return file;
    }
    return false;
}


//cashing path so in the compile method i can directly call that path and read file.
//this path is predefined path may be  from node global folder

//*************************completed*********************************************
//take the guessed path if they really exists
Module.findPath = function(request, paths){
/*this request is not a dir namei. if 'module is a module as a folder' you send foo/bar --> bar is a module folder name.you are inside
that module folder name*/

     //paths set 1th cache karanna onada
    
    if(Module._pathCache.hasOwnProperty(request)){  //request can be ./foo path cache has abs path name of that path
        return Module._pathCache[request];
    }

    var dir_path = paths;
    var resolvePath;

/*in posix systems if there is a trailing slash that mean there are sub folders inside that directory.if not that is a file*/
    var trailingSlash = (request.slice(-1) === '/');    //true or false

    var extensions = Object.keys(Module.extensions);

    if(request.charAt(0) == '/'){
        //that mean absolute path in posix
        dir_path =  [''];
    }

    //for each directory search will be taken in following sequence
    /*
    symbolic link
    .js file
    package.json
    index.jag or index.js
    */

    //file is the absolute path to the file.
    var file;
    for(var i = 0 ; i < dir_Path.length-1; i--){  //send every global paths here jaggery_globle
        resolvePath = path.resolve(dri_path[i], request);

        if(!trailingSlash){
            file = tryFile(resolvePath);    //in try file if it is a dir go inside that dir and search until that file find.

            if(!file && !trailingSlash){
                file = tryExtension(resolvePath, extensions);
            }
        }
           
        if(!file){
            file = tryPackage(resolvePath);
        }

        if(!file){
            tryExtensions(path.resolve(resolvePath +
            ,'index'), extensions);  //eg - tryFile(index.jag or index.js)
        }

        if(file){
            Module._pathCache[request] = file;          //--------------------------------do i need to use something like cache key
        }
    }
      return false;
}

//**************************partially completed***********************
//guess the paths
Module.lookupPaths = function(request, parent){

    if(NativeModule.exists(request)){
        return request;
    }

    var begin = request.substring(0,2);
    if(begin !== '..' || begin !== './'){

    //this can be /home/dd/gd/afaf of foo + parent samaga kalla(e kiaynne meyawa illannee thawa module 1kin)

//foo kiyala awoth resolve karanna ona methanain.find path 1 ta  yanne just paths tika vitharai.ewa jaggery_module da nathda kiyala wadak na
        var paths = modulePaths; // module path contain place to look from home dir
        if(parent){
            if(!parent.paths){
                parent.paths = [];
            }
                paths = parent.paths.concat(paths);
        /*modules that are require by parent must be in paths relavent to parent's path.so her
        what we do is we add jaggery_module paths relevant to the parent and add global paths if
        to search if require path not found from jaggery_module folder.*/
        }
        return paths;
    }

    //situation can be require('./foo') of (../foo)
    if(parent || parent.id || parent.filename){
        //var _dir = parent.id = parent.filename;
        var basepath = path.basename(parent.filename);
        paths.put(path.resolve(path.dirname(parent.filename), request));

        //go and search in global folder set by init path moduel
        //if module cannot be found in jaggery_module folder it will look for global module folder.
        return paths;
    }

    /*follow scenario can be happen when we require from console
    in command line we cannot mention parent we just type var k = require(fs)
    there is no parent module require this fs module*/

//(parent rahitha kella,meyawa illane command line 1n)

//**************not completed************************
    paths = Module.jaggery_modulePaths('.')
    return paths;
}

//this func is responsible for load all module.load module and keep the js text file in a javascript object
//module can be  a single file. but above func load them all.

//command line argument is not yet implemented.
//**************************completed*****************************
Module.load = function(request, parent){

    var filename = lookupPaths(request, parent);//if not return null else return abs path
    //file name is the abs path
                                                // no file name nam exception 1k gahala udinma nawathwanawa
    var cacheModule = Module.moduleCache[filename]; //garantee that module name is get
    if(cacheModule){
        return cacheModule.exports;
    }

    //then load if in the native module.cache is faster than loading native modules.native modules also cache in module.cache
    //then how we read this native modules
    if(NativeModule.exists(filename)){
        return NativeModule.getSource(filename).exports; //or this can be nativemodule.require(id); //id is fully resolved file name
                                                           //if no native module exist it return error
    }

    //if not in native modules or if not in cache then the module is loading in first time load module in regular way
    var module = new Module(request, parent);

    //ismain module is skipped**********************

    try{
        module.newLoad(filename);   //error can happen while loading
        module.loaded = true;

        if(module.loaded){
            Module.moduleCache[filename] = module;
        }
    }catch(e){
        if(!module.loaded){
            throw new Error(e.message);
        }
    }
    return module.exports;
}
 //***********************completed*********************
//this function is used to load a module from starting point
Module.prototype.newLoad = function(filename){
///gurantee that filename is a abs path

    this.filename = filename;    //because this is the abs path of this module
    this.paths = Module.jaggery_modulePaths(path.dirname(filename));

    //exte only can be .js or .jag. .json cannot be module type
    var exte = path.extname(filename);
    if(!Module.extension[exte]){
        //try to load module with added extensions(jag or js)(eg:require(/home/buddhi/Desktop/foo/bar) like in common js)
        exte = ['.js', '.jag'];
        filename = tryExtensions(filename, exte);
        exte = path.extename(path.basepath(filename));
        this.loaded = true;
    }

    Module.extension[exte](this, filename);   ///this function will read and execute the file

}

//********************completed********************
Module.resolveFileName = function(request, parent){

    if(NativeModule.exist(request)){
        return request;
    }

    var paths = lookupPaths(request, parent);
    var file = findPath(request, paths);

    if(file){
        return file;
    }
    throw new Error('Requested module not found ' + request);
}

//read file with relavent extensions.
Module.extensions['.js'] = function(filename, module){
    var content = NativeModule.require('fs').readFile(filename, 'utf8'); //read file using apache commaon io
    module.compile();
}

Module.extensions['.json'] = function(filename, module){
    var content = NativeModule.require('fs').readFile(filename, 'utf8');
    module.compile();
}

Module.extensions['.jag'] = function(filename, module){
    var content = NativeModule.require('fs').readFile(filename, 'utf8');
    module.compile();
}

//this method help to compile any kind of extension type
Module.compile = function(){


}



//mulin balanna ona paths tika unshift kara kara issarahata yawanna.

//in this function set all the paths you want to search for module including global paths.
Module.initPath = function(requestPath){
    //get user currdir based on windows or ubuntu. you can get those from process.getenv.home
    var home = Process.getenv.HOME;

    var paths = [path.resolve(home, )];    //put global path where all modules are in

    paths.unshift(path.resolve(home,'jaggery_module'));

    modulePaths = paths;
}

//check weather in common io utils bom is needed.if need use it
Module.resolveBOM = function(content){
//use this method to understand which endian is using(unicode)
}

