//function process(jaggeryObj){

    function start(){

        var jags = jag.caches;

        /*
        * only .js files are accepted;
        * */
        function JagNative(id){
            this.id = id;
            this.fileName = id + 'js';
            this.exports = {};
        };

        //source codes wrapped as strings
        JagNative._source = jags.bind('natives');
        JagNative._cache = {};

        JagNative.require = function(id) {
            if(id == 'natives'){
                return JagNative;
            }

            var hasCache = JagNative.cache_exists(id);

            if(hasCache) {
                return JagNative.getCache(id);
            }

            var jagModule = new JagNative(id);
            jagModule.compile();

            return jagModule.exports;
        }

        JagNative.prototype.compile = function(){

            var script = jags.bind('contextify').contextifyScript;
            var fn = new script(this.fileName, JagNative.wrap(this.id));
            fn(this.exports, JagNative.require, this, this.fileName);

            //cache the compiled object
            this.cache();
        };

        JagNative.wrapper = ['function(exports, require, module, fileName) {\n', '\n}'];

        JagNative.wrap = function(id){
            return JagNative.wrapper[0] + JagNative.getSource(id) + JagNative.wrapper[1];
        };

        JagNative.getSource = function(id) {
            if(JagNative.source_exists(id)) {
                return JagNative._source[id];
            }
            return undefined; //correct or not
        };

        JagNative.getCache = function(id) {
            return JagNative._cache[id];
        }

        JagNative.source_exists = function(id) {
            return Object.prototype.hasOwnProperty.call(JagNative._source, id);
        };

        JagNative.cache_exists = function(id) {
            return Object.prototype.hasOwnProperty.call(JagNative._cache, id);
        };

        JagNative.prototype.cache = function(){
            JagNative._cache[this.id] = this;
        };

        var foo = JagNative.require('path');
        print(foo.name);
    }
    start();
    //
//}