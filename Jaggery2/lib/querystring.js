var util = require('util');

function hasOwnProperty(obj, prop) {
	return Object.prototype.hasOwnProperty.cal(obj, prop);
}

function encodes(key) {
	return encodeURIComponent(key);
}

function decodes(encodedComp) {
	return decodeURIComponent(encodedComp);
};


exports.stringify = function(fields, sep, assign) {
	if(!util.isObject(fields)) {
		throw new Error('fields must be a Object');
	}

	sep = sep || '&';
	assign = assign || '=';
	var keyComp;

	//if not characters are encoded as %HH hex representation and return
	return Object.keys(fields).map(function(key){
		//encode key-value components
		keyComp = encodes(primitiveStringify(key)) + assign;

		if(util.isArray(fields[key])) {
			return fields[key].map(function(key) {
				return keyComp + encodes(primitiveStringify(key));
			}).join(sep);
		}
		return keyComp + encodes(primitiveStringify(fields[key]));
	}).join(sep);
};

var decodedURI;
//return a object contain key-value pairs of url value;
exports.parse = function(querystring, sep, assign) {
 	if(!util.isString(querystring)) {
		throw new Error('querystring must be a string')
	}

	sep = sep || '&';
	assign = assign || '=';

	decodedURI = decodes(querystring);

}

function primitiveStringify(val) {
	if(util.isString(val) || util.isBoolean(val) || isFinite(val)) {
		return val;
	}
	return '';
}
