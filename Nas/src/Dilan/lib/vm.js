/*
* we can get out as javascript function and we can get out as a executer,
* */

var binding = jaggery.bind('contextify');

/*execute in the current context global context
 @param	  fileName  name of the file to execute
 @param	  content   content of a file as a string
*/
exports.runInThisContext = function(fileName, content) {
	var Script = binding.contextifyScript;
	var fn = new Script(fileName, content);
	return fn;
}

/* run Script in a new global Context
*  @param	fileName  name of the file to execute
*  @param	content   content of a file as a string
* */
exports.runInNewContext = function(fileName, content) {
	var runInNewcContext = binding.runInNewContext;
	return new runInNewcContext(fileName, content);
}

exports.isContext = function() {
}

/* compile code but does not run;
 @param	  fileName  name of the file to execute
 @param	  content   content of a file as a string
 * */
exports.compileScript = function(fileName, content) {
	var compileScript = binding.compileScript;
	return compileScript(fileName, content);
}


exports.runScripts = function(compiledScript, args) {
	var runScripts = binding.runScripts;
	return runScripts(compiledScript,args);
}

