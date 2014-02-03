/*
* jaggery.js source file is executed by src/Dilan?Jaggery2
* */

//function process(jaggeryObj){

function start() {

	function hasOwnProperty(obj, prop) {
		return Object.prototype.hasOwnProperty.call(obj, prop);
	}
	/*
	 * only .js files are accepted;
	 * */
	function Native(id) {
		this.id = id;
		this.fileName = id + '.js';
		this.exports = {};
	};

	//source codes wrapped as strings
	Native._source = jaggery.bind('natives');
	Native._cache = {};

	Native.require = function (id) {

		if (id == 'natives') {
			return Native;
		}

		var hasCache = Native.cache_exists(id);
		if (hasCache) {
			var cached = Native.getCache(id);
			return cached.exports;
		}

		var jagModule = new Native(id);

		jagModule.compile();

		//cache the compiled object
		jagModule.cache();

		return jagModule.exports;
	}

	//compile wrapped content of core .js files
	Native.prototype.compile = function () {

		var script = jaggery.bind('contextify').contextifyScript;
		var fn = new script(this.fileName, Native.wrap(this.id));
		fn(this.exports, Native.require, this, this.fileName);
	};

	Native.wrapper = ['function(exports, require, module, fileName) {\n',
		'\n}'];

	Native.wrap = function (id) {
		return Native.wrapper[0] + Native.getSource(id) + Native.wrapper[1];
	};

	Native.getSource = function (id) {
		if (Native.source_exists(id)) {
			return Native._source[id];
		}
	};

	Native.getCache = function (id) {
		return Native._cache[id];
	}

	Native.source_exists = function (id) {
		return hasOwnProperty(Native._source, id);
	};

	//cache existences check
	Native.cache_exists = function (id) {
		return hasOwnProperty(Native._cache, id);
	};

	//cache the module
	Native.prototype.cache = function () {
		Native._cache[this.id] = this;
	};


	//test
	var module = Native.require('module');
	//calling static method Module.Main inside module.js
	module.Main();
}
start();
//
//}
