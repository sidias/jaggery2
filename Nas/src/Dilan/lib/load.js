var Context 		= Java.type("jdk.nashorn.internal.runtime.Context");
var ScriptRuntime 	= Java.type("jdk.nashorn.internal.runtime.ScriptRuntime");
var Source 			= Java.type("jdk.nashorn.internal.runtime.Source");
var File            = Java.type("java.io.File");
var IOException     = Java.type("java.io.IOException");
var Files           = Java.type("java.nio.file.Files");
var Path            = Java.type("java.nio.file.Path");
var Paths           = Java.type("java.nio.file.Paths");
//delete later
var HashMap			= Java.type("java.util.HashMap");
var StringBuilder	= Java.type("java.lang.StringBuilder");

var property = new HashMap();
//------------- delete later


var jaggeryjs = ""




function hasOwnProperty(obj, prop) {
	return Object.prototype.hasOwnProperty.call(obj, prop);
}

function readCore() {
	var libPath = new StringBuilder("/home/buddhi/IdeaProjects/Nas/src/Dilan/lib");

	var libDir = new File(libPath.toString());
	if( !libDir.exists() || !libDir.isDirectory()){
		//throw an error.
		return;
	}

	var jsFile = Java.from(libDir.listFiles());

	for(var i=0; i< jsFile.length;i++){

		if( !jsFile[i].isFile() &&  !jsFile[i].getName().endsWith(".js")){
			continue;
		}
		var jsSource = new Source(jsFile[i].getName(), jsFile[i]);
		var fileName = jsFile[i].getName().replace( ".js","");
		property.put(fileName ,jsSource.getString());
	}
}

readCore();
//-------------

var jagCore_cache = {};
function readJaggeryCore() {
	var cached = hasOwnProperty(jagCore_cache, 'jaggery_core');

	if(cached) {
		return cached.jaggery_core;
	}

	var jagDir = "/home/buddhi/IdeaProjects/Nas/src/Dilan/Jaggery.js";
	var jagCore = fs.readFile(jagDir);

	if(!jagCore) {
		throw new Error("Jaggery js file cannot found @ " + jagDir);
	}

	//cache jaggerycore - read all these files @ build
	if(!cached) {
		jagCore_cache['jaggery_core'] = jagCore;
	}

	return jagCore;
}

function runJaggeryCore() {
	var content = /*readJaggeryCore();*/
	contextify.contextifyScript(content, "Jaggery.js");

}

function files() {
	var scriptEnviron = Context.getContext().getEnv();
	var files = scriptEnviron.getFiles();
	if(!files.isEmpty){
		return files;
	}
};

function systemProperty(property) {
	return java.lang.System.getProperty(property)
};

//delete -----------
function getModule(moduleName) {
	return property.containsKey(moduleName) ? property.get(moduleName):"";
}
//------------------

var contextify = {
	runInNewContext:function(content, fileName) {
	 	if(!content || typeof content !== 'string') {
			throw new Error("empty arguments")
		}

		var functions;

		var context = Context.getContext();
		var newGlobal = context.createGlobal();
		var errorManager = context.getErrorManager();

		var func = context.compileScript(new Source("",content.toString()), newGlobal);

		if(func == null || errorManager.getNumberOfErrors() != 0 ){
			throw new Error("execution failed ");
		}

		try{
			functions = ScriptRuntime.apply(func, newGlobal);
		} catch (e){
			errorManager.error(e.toString());
			if(context.getEnv()._dump_on_error){
				e.printStackTrace(context.getErr());
			}
		} finally {
			context.getOut().flush();
			context.getErr().flush();
		}
		return functions;
	},

	isContext:function() {

	},

	contextifyScript:function(content, fileName) {
		if(!content || typeof content !== 'string') {
			throw new Error("empty arguments");
		}
		var functions;

		var context = Context.getContext();
		var global = Context.getGlobal();
		var errorManager = context.getErrorManager();

		var func = context.compileScript(new Source("",content.toString()), global);
		if(func == null || errorManager.getNumberOfErrors() != 0 ){
			//sent javascript error to the output
		}

		try{
			var functions = ScriptRuntime.apply(func, global);
		}catch(e) {
			errorManager.error(e.toString());
			if(context.getEnv()._dump_on_error){
				e.printStackTrace(context.getErr());
			}
		}finally {
			context.getOut().flush();
			context.getErr().flush();
		}
		return functions;
	},

	compileScript:function(content,fileName) {
		if(!content || typeof content !== 'string') {
			throw new Error("empty arguments")
		}

		var context = Context.getContext();
		var global = Context.getGlobal();
		var func = context.compileScript(new Source("",content.toString()), global);
		return func;
	},

	runScript:function(compiledScript) {
	 	if(!compiledScript || typeof compiledScript !== 'function') {
			throw new Error("empty parameters");
		}

		var func;
		var context = Context.getContext();
		var global = Context.getGlobal();
		var errorManager = context.getErrorManager();

		try{
			func = ScriptRuntime.apply(compiledScript, global);
		}catch(e) {
			errorManager.error(e.toString());
			if(context.getEnv()._dump_on_error){
				e.printStackTrace(context.getErr());
			}
		}finally {
			context.getOut().flush();
			context.getErr().flush();
		}
		return func;
	}
};

var fs = {
	statPath: function(path) {
		if(!path || typeof path !== 'string') {
			throw new Error("empty arguments");
		}

		var fileName = Paths.get(path.toString());
		var isRegularReadableFile;

		try {
			isRegularReadableFile = Files.isRegularFile(fileName) & Files.isReadable(fileName);

			if(isRegularReadableFile) {
				var pathType = Files.isRegularFile(fileName) && (path.toString().contains(".js") ||
					path.toString().contains(".jag"));

				if(pathType) {
					return file;
				}
			} else {
				var pathType = Files.isDirectory(fileName);
				if(pathType) {
					return file;
				}
			}
		}catch(e) {
			//throw error to script env
		}
		return false;
	},

	readFile: function(path) {
		if(!path || typeof path !== 'string') {
			throw new Error('empty arguments');
		}

		var fileName = Paths.get(path.toString());
		var filePath = fileName.toFile();
		var source = null;
		try {
			source = new Source(fileName.toString(), filePath);
		} catch (e) {
			e.printStackTrace();
		}
		return source.getString();
	}
};

var natives = {
	cache		: getModule('cache'),
	debugger	: getModule('debugger'),
	fs			: getModule('fs'),
	module		: getModule('module'),
	path		: getModule('path'),
	querystring	: getModule('querystring'),
	uri			: getModule('uri'),
	util		: getModule('util'),
	vm			: getModule('vm')
}

var load = {
	process:{
		bind:function(property) {
			switch (property) {
				case 'contextify':
					return contextify;
					break;
				case 'natives':
					return natives;
					break;
				case 'fs':
					return fs;
					break;
			}
		},
		version	:	"Jaggery 2",
	    arch	:	systemProperty("os.arch"),
		argv	: 	systemProperty(),
		platform: 	systemProperty("os.name"),
		cwd		:   systemProperty("user.dir"),
		env		:   java.lang.System.getenv(),
		files	:   files()
	}

};

Object.bindProperties(this,load);
runJaggeryCore();



