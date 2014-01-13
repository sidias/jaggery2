function isNull(arg) {
  return arg === null;
}

function isString(arg) {
  return typeof arg === 'string';
}

function isObject(arg) {
  return typeof arg === 'object' && arg !== null;
}

var regExp = /%[sdj%]/g;
function format(){
    var args =  Array.prototype.slice.call(arguments);
    var str = String(args[0]);

      if (!isString(str)) {
        var objects = [];
        for (var i = 0; i < arguments.length; i++) {
          objects.push(inspect(arguments[i]));
        }
        return objects.join(' ');

      }

    var len = args.length;
    var k = 0;

    str = str.replace(regExp,function(val){
        if(val === '%%'){return '%'}
        //if(k>=len){return val}   eg{'hello my name is %s.i am %d years old','buddhi',22,'hi'}

        switch(val){
            case '%s':
                k++;
                if(args[k]){
                    return String(args[k]);
                    break;
                }
                throw Error('Invalid number of arguments');

            case '%d':
                k++;
                if(args[k]){
                    return Number(args[k]);
                    break;
                }
                throw Error('Invalid number of arguments');

            case '%j':
                k++;
                if(args[k]){
                    try{
                        return JSON.stringify(args[k]);
                    }catch(e){
                        return '[faild]';          //correct this
                    }
                    break;
                }
                throw Error('Invalid number of arguments');
        }
    });
    return str;
}



print(format(1,2,3));