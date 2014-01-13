//do not use objName.hasOwnProperty
function hasOwnProperty(obj, prop) {
    return Object.prototype.hasOwnProperty.call(obj, prop);
}

//reference to JagNative func in scr/jaggery.js
var JagNative = require('natives');

function JagModule(id) {
    this.id = id;
    this.fileName = [(id +"js"), (id +"jag")];
    this.exports = {};
}

//JagModule can be call as a function wherever it is required;
//use to call runMain through src/jaggery.js
module.exports = JagModule;

JagModule.fullyPathCache = {};
JagModule.module_cache = {};
JagModule.extensions = {};

var packageCache = {};

JagModule.extensions['.js'] = function() {

};

JagModule.extensions['json'] = function() {

};

/*
* read package.json file in given path
* */
function readPackage(pacakagePath) {

};

/*
* find if pacakge.json exists
* */
function tryPackage() {
    if(hasOwnProperty(packageCache, path)){

    }
};

function tryFile() {

};

function tryExtensions() {

};

JagModule.wrap = JagNative.wrap;
JagModule.wrapper = JagNative.wrapper;

JagModule.findPath = function() {

};

/*
* param   request - module;
*         parent - parent module request this module.
* */
JagModule._load = function(request, parent) {

};

JagModule.resoleveFileName = function() {

};

JagModule.prototype.load = function() {

};

JagModule.prototype.compile = function() {

};

JagModule.prototype.require = function() {

};

/*
* starting point
* */
JagModule.Main = function() {
    JagModule._load();
};




