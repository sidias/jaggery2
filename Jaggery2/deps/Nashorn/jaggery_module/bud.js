//var path = require('path');
//exports.resolve = path.resolve('foo/bar', '/tmp/file/', '..', 'a/../subfile');

var foo = require('./foo.js');
print(Object.keys(foo));
print(foo.age);
