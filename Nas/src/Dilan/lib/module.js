/* <mindful>
 	1.) warning
             do not use objName.hasOwnProperty
    2.) get all the known core modules using JagNative
 */

//reference to Native func in scr/jaggery.js
var JagNative = require('natives');
var path = JagNative.require('path');
//var util = require('util');

var platform = jaggery.platform;
var pathSep = (platform == 'Linux') ? '/' : '';

//wrapper use when compiling.
Module.wrapper = JagNative.wrapper;

function hasOwnProperty(obj, prop) {
	return Object.prototype.hasOwnProperty.call(obj, prop);
}

//path status
function statsPath(path) {
	var fs = JagNative.require('fs');
	//file relate task may throw error;
	try {
		return fs.statPath(path); //{return object}
	} catch(err) {
		throw err;
	}
}

function Module(id, parent) {
	this.id = id;
	this.fileName = null;
	this.exports = {};
	this.parent = parent;
	this.loaded = false;
}

//Module can be call as a function wherever is required;
//use to call runMain through src/jaggery.js
module.exports = Module;

//fullyPathCache is stored in a object user requirePath as a key against absolutePath of that user requirePath
Module.fullyPathCache = {};
Module.module_cache = {};
Module.extensions = {};

var packageCache = {};

Module.extensions = ['.js','json']; //insert .jag

/*Module.extensions['jag'] = function() {

} */

/*
 * read package.json file in given path
 * param -> package - package.json content;
 * */
function readPackage(absPath) {

	var mainPath = '';
	var package = tryPackage(absPath);

	if (package) {
		try {
			var pack = JSON.parse(package);

			//cache parsed package.
			packageCache[absPath] = package;
			var main = pack.main /*'./index'*/;

			if (main) {
				var mainModulePath = path.resolve(absPath, main);

				//baseName of the mainModlePath
				var mainModule_base = path.baseName(mainModulePath);

				//check if (package.json).main has valid extension
				if(path.extName(mainModule_base)) {
					mainPath = tryFile(mainModulePath, null);     // if package.json mention main not available returns false
				} else {
					mainPath = tryExtensions(mainModulePath);
				}

				if(!mainPath) {
					throw new Error('file mention in (pacakge.json).main could not found');
				}
				return mainPath;

			} else {
				throw new Error('no valid main module mention in (pacakge.json).main')
			}
		} catch (error) {
			throw error;
		}
	}

	//search for index.jag or index.js
	var index = tryExtensions([absPath, 'index'].join(pathSep));
	return index;   //may be false
};

/*
 * find if package.json exists
 * */
function tryPackage(absPath) {

	if (hasOwnProperty(packageCache, absPath)) {
		return packageCache[absPath];
	}

	var packagePath = [absPath, 'package.json'].join(pathSep);
	//inside dir
	//check if package.json exists before read
	var has_pkg = tryFile(packagePath, null);;

	if(has_pkg) {
		var fs = require('fs');
		var pkg = fs.readFile(packagePath);
		if (pkg) {
			return pkg;
		}
	}
	return false;   //if this func return false try to read index.jag or index.js   */
};

//read the file from given path
/*
* if path is a js file statpath will return path with extension name
* if path is a dir statpath will return path without extension
* */
function tryFile(filePath, realRequest) {

	var absPath;
    if(realRequest  != null) {
		var pathToJoin = [filePath, realRequest];
		absPath = pathToJoin.join(pathSep);
	} else {
		absPath = filePath;
	}

	var statpath = statsPath(absPath);
	return statpath.isPathExists;
};

//try given filepath with all extensions
//wrong here. check which try extension is using there are two try extensions
function tryExtensions(absPath) {

	var ext = ['.js'];//Object.keys(Module.extensions);
	var extLength = ext.length;

	for (var i = 0; i < extLength; i++) {
		var file = tryFile(absPath + ext[i], null);
		if (file) {
			return file;
		}
	}
	return false;
};

Module.findPath = function (realRequest, paths) {

	/*check user gives fileName extension or not
	 if extension is not given we have to check for .js and .jag
	 */
	var fileName = path.baseName(realRequest);
	if(!Array.isArray(paths)) {

		var extension = path.extName(fileName);

		//require('foo') foo don't have extension. so this if will be skipped if passed as 'foo'.because foo has no extension.
		if (extension) {
			return tryFile(paths, null);
		} else {
			//if no extension first search for single.js file. if not found looks for folderModule
			var ext = tryExtensions(paths);

			if(ext) {
				return ext;
			}

			var folderModule = tryFile(paths, null);
			if(folderModule) {
				var mainSource = readPackage(folderModule);
				return mainSource;
			}
			return false;
		}
	} else {  //fill this if not normal files will do the same stuff.
		//search in jaggery_module folders
		var jaggeryModulePaths = paths;
		var file = '', fileParts = '';

		//change this method firs .js file then folder
		for (var i = 0; i < jaggeryModulePaths.length; i++) { 					//real request --> eg : foo

			//first we try to get fileName.js file.if not then looks for folder as module
			fileParts = [jaggeryModulePaths[i], fileName]
			file = tryExtensions(fileParts.join(pathSep));

			if(file) {
				return file;
				break;    //--------------------------no break need here
			}

			//file is a dir(without extension).read package.json and get mainModule/index path
			file = tryFile(jaggeryModulePaths[i], fileName);
			if(file) {
			   	var mainSource = readPackage(file);
				return mainSource;
			}
		}
		return false;
	}
};

/*
 * param   request - module;
 *         parent - parent module request this module.
 *         (param parent use to resolve the relative path)
 *
 *         if module is core module it will not be cached into Module.module_cache obj
 *         it will be cached into Native._cache obj in jaggery.js
 * */
Module._load = function (request, parent) {

	//in this point module path is resolved to it's absolute path;
	//check file exist in given dir
	var filePath = Module.resolveFileName(request, parent);
			print('filepath is '+ filePath);

	//if request module is a jaggery core module resolveFileName func return coreModuleName;
	var cacheModule = hasOwnProperty(Module.module_cache, filePath);
	if (cacheModule) {
		var module = Module.module_cache[filePath];
		return module.exports;
	}

	//if module exists cache it
	if (filePath) {
		//cache fullPath if not cached
		//guarantee to cache full path at the first time if module was not cached
		if(!hasOwnProperty(Module.fullyPathCache, request)) {
			Module.fullyPathCache[request] = filePath;
		}

		var f_letter = filePath.charAt(0);
		//return to check if core modules(resolveFileName return module name only if module is a core,otherwise return
		// string path beginning with / regarding to module);
		if( f_letter !== '/') {

			//no need to cache require will return cache module if exists;

			//Module.resolveFileName returns single string if required module is a core module
			//guarantee to call if module is a cored module.
			return JagNative.require(filePath);
		}

		//found the targeted file, main is a absolute path to the main js file
		//create a module and compile it and send module.exports
		var module = new Module(filePath, parent);
		module.load(filePath);

		//read the file and compile it and return exports
		return module.exports;		//create a module and compile it and send module.exports

	} else {
		var error = new Error('requested module ' + request + ' not found ');
		throw error;
	}
};

//send resolve filename
//module can be as following
/*can be  	1.require(foo) - foo can be core module or module inside jaggery_module folder
 			3../foo(with,without extension)
 			4.../../foo(with,without extension)
 			5.abs path(with,without extension)
 */
Module.resolveFileName = function (request, parent) {

	//check path cache
	if (hasOwnProperty(Module.fullyPathCache, request)) {
		return Module.fullyPathCache[request];
	}

	//check for core modules in src/lib
	if (JagNative.source_exists(request)) {
		return request;
	}

	//set ./ of ../ or ../jag_module/moduleName paths to check
	var resolvedPaths = Module.resolvePaths(request, parent);
	var requests = resolvedPaths[0];
	var pathsToSearch = resolvedPaths[1];

	//use findPath method to see if this file exists
	var filePath = Module.findPath(requests, pathsToSearch);
	if (filePath) {
		return filePath;
	}
	return false;
};

//set all possible paths to check.
Module.resolvePaths = function (request, parent) {

	//if request is abs
	if(path.isAbsolutePath(request)) {
		return [request, request];
	}

	//guarantee request like ./ or ../ only reach here(require('foo') will be skipped)
	var start = request.slice(0, 2);
	if (start === '..' || start === './') {

	//at the start up parent is null user need to send abs path to work jaggery
	//assume parent path is a abs path because parent module is a already created module.
		if (parent != null) {

			var parentPaths = parent.fileName;
			var childPath = path.resolve(parentPaths, request);

			return [request, childPath];
		}
		//if parent is null
		return [request, path.resolve(jaggery.cwd, request)];
	}

	//if require('foo') is not a core module and lay inside jaggery_module folder
	//assume parent is a fully resolved path; cwd may null with first startup;(runMain pass null);
	var jagmod_paths = Module.jagModulePaths(childPath);
	//jagmod_paths is an array

	return [request, jagmod_paths];
};

/*jaggery_module folder paths
 * param -> absPath - absolute path of the module
*/
Module.jagModulePaths = function (request) {

	//guarantee that path is normalized absolute path
	//var absPath = path.resolve(jaggery.cwd,request);
	var absPath = jaggery.cwd
	var modulePaths = [];
	var splitPath = absPath.split(pathSep);
	splitPath.shift();

	var p = '';
	for (var i = 0; i < splitPath.length; i++) {  //use pathsep instead
		p = p + '/' + splitPath[i];
		modulePaths.unshift(p + '/jaggery_module');
	}
	return modulePaths;
};

//add more stuff.
Module.prototype.load = function (absPath) {

	//fill newly created module object with data
	this.fileName = absPath;
	//read the content of file;
	var content = require('fs').readFile(this.fileName);
	    print('content of this file is ))))))\n'+content);

	//compiled the source and fill module.exports object;
	this.compile(content, this.fileName);
};

Module.prototype.compile = function(content, fileName) {
	var self = this;

	//param - required path
	function require(path) {
		self.require(path);
	}

	/*if developer want to expose more functionalists to the scriptEnvironment. bind them as static methods to
	'require' function*/

	//wrapped content ;
	var wrapped_content = Module.wrapper[0] + content + Module.wrapper[1];

	//function(exports, require, module, fileName)

	var Script = jaggery.bind('contextify').contextifyScript;
 	var fn = new Script(this.fileName, wrapped_content);
 	fn(this.exports, require, this, this.fileName);

 //cache module after compiled
 	Module.module_cache[fileName] = this;
 };

//instance property of Module object.use to load module to this module
Module.prototype.require = function (request) {

	Module._load(request, this);
	return this.exports;
};

function stripBOM(content) {

	if (content.charCodeAt(0) === 0xFEFF) {
		content = content.slice(1);
	}
	return content;
}

 /* starting point
 * */
Module.Main = function () {
	print('main call');
	var fs = Module._load(/*jaggery.files[0]*/'buddhi', null);   //return {};
	print(fs.isAbsolutePath('./foo/buddhi.js'));
};





