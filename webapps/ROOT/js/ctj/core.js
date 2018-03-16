var $CTJ = {
	_verison : '0.2',
	_writter : 'Archmage',
	ns :function(pac,parent){
		var arr = pac.split('.');
		w = $CTJ;
		if(parent != undefined){
			w = parent;
		}
		var temp = w;
		for(var i = 0 ;i < arr.length; i++){
			var  name = arr[i];
			if(temp[name] == undefined){
				temp[name] = {};
			}
			temp =temp[name]; 
		}
		return temp;
	}
};

//扩展命名空间
$CTJ.ns('core');
$CTJ.ns('util');
$CTJ.ns('plugin');

$CTJ.apply = function(o, c, defaults){
    // no "this" reference for friendly out of scope calls
    if(defaults){
        $CTJ.apply(o, defaults);
    }
    if(o && c && typeof c == 'object'){
        for(var p in c){
            o[p] = c[p];
        }
    }
    return o;
};


/**
 * Copies all the properties of config to obj if they don't already exist.
 * @param {Object} obj The receiver of the properties
 * @param {Object} config The source of the properties
 * @return {Object} returns obj
 */
$CTJ.applyIf = function(o, c){
    if(o){
        for(var p in c){
            if(!$CTJ.isDefined(o[p])){
                o[p] = c[p];
            }
        }
    }
    return o;
};


$CTJ.escapeRe = function(s) {
    return s.replace(/([-.*+?^${}()|[\]\/\\])/g, "\\$1");
};




(function(){
	var idSeed = 0,
        toString = Object.prototype.toString,
        ua = navigator.userAgent.toLowerCase(),
		DOC = document,
		check = function(r){
            return r.test(ua);
        },
        docMode = DOC.documentMode,
        isStrict = DOC.compatMode == "CSS1Compat",
        isOpera = check(/opera/),
        isChrome = check(/\bchrome\b/),
        isWebKit = check(/webkit/),
        isSafari = !isChrome && check(/safari/),
        isSafari2 = isSafari && check(/applewebkit\/4/), // unique to Safari 2
        isSafari3 = isSafari && check(/version\/3/),
        isSafari4 = isSafari && check(/version\/4/),
        isIE = !isOpera && check(/msie/),
        isIE7 = isIE && (check(/msie 7/) || docMode == 7),
        isIE8 = isIE && (check(/msie 8/) && docMode != 7),
        isIE6 = isIE && check(/msie 6/),
        //isIE6 = isIE && !isIE7 && !isIE8,
        isGecko = !isWebKit && check(/gecko/),
        isGecko2 = isGecko && check(/rv:1\.8/),
        isGecko3 = isGecko && check(/rv:1\.9/),
        isBorderBox = isIE && !isStrict,
        isWindows = check(/windows|win32/),
        isMac = check(/macintosh|mac os x/),
        isAir = check(/adobeair/),
        isLinux = check(/linux/),
        isSecure = /^https/i.test(window.location.protocol);

	/**
		browser
	*/
	$CTJ.isStrict=isStrict;
	$CTJ.isOpera=isOpera;
	$CTJ.isChrome=isChrome;
	$CTJ.isWebKit=isWebKit;
	$CTJ.isSafari=isSafari;
	$CTJ.isSafari2=isSafari2;
	$CTJ.isSafari3=isSafari3;
	$CTJ.isSafari4=isSafari4;
	$CTJ.isIE=isIE;
	$CTJ.isIE7=isIE7;
	$CTJ.isIE8=isIE8;
	$CTJ.isIE6=isIE6;
	$CTJ.isGecko=isGecko;
	$CTJ.isGecko2=isGecko2;
	$CTJ.isGecko3=isGecko3;
	$CTJ.isBorderBox=isBorderBox;
	$CTJ.isWindows=isWindows;
	$CTJ.isMac=isMac;
	$CTJ.isAir=isAir;
	$CTJ.isLinux=isLinux;
	$CTJ.isSecure=isSecure;

	$CTJ.isEmpty = function(v, allowBlank){
		return v === null || v === undefined || (($CTJ.isArray(v) && !v.length)) || (!allowBlank ? v === '' : false);
	},

       
	$CTJ.isDefined = function(v){
		return typeof v !== 'undefined';
	};

	$CTJ.isArray = function(v){
		return Object.prototype.toString.call(v) === '[object Array]';
	};

	$CTJ.isDate = function(v){
		return Object.prototype.toString.call(v) === '[object Date]';
	};

	$CTJ.isObject = function(v){
		return !!v && Object.prototype.toString.call(v) === '[object Object]';
	};

	$CTJ.isPrimitive = function(v){
		return $CTJ.isString(v) || $CTJ.isNumber(v) || $CTJ.isBoolean(v);
	};


	$CTJ.isFunction = function(v){
		return typeof v == "function";
	};


	$CTJ.isNumber = function(v){
		return typeof v === 'number' && isFinite(v);
	}

	 
	$CTJ.isString = function(v){
		return typeof v === 'string';
	};


	$CTJ.isBoolean = function(v){
		return typeof v === 'boolean';
	};
	
	
	$CTJ.num =function(v, defaultValue){
		v = Number($CTJ.isEmpty(v) || $CTJ.isArray(v) || typeof v == 'boolean' || (typeof v == 'string' && v.length == 0) ? NaN : v);
		return isNaN(v) ? defaultValue : v;
     };
	
	
	$CTJ.getDom =function(el, strict){
		if(!el || !DOC){
			return null;
		}
		if (el.dom){
			return el.dom;
		} else {
			if (typeof el == 'string') {
				var e = DOC.getElementById(el);
				// IE returns elements with the 'name' and 'id' attribute.
				// we do a strict check to return the element with only the id attribute
				if (e && isIE && strict) {
					if (el == e.getAttribute('id')) {
						return e;
					} else {
						return null;
					}
				}
				return e;
			} else {
				return el;
			}
		}
	};
	
	var El={};
	
	El._flyweights = {};
	
	$CTJ.fly = function(el, named){
		var ret = null;
		named = named || '_global';

		if (el = $CTJ.getDom(el)) {
			(El._flyweights[named] = El._flyweights[named] || new El.Flyweight()).dom = el;
			ret = El._flyweights[named];
		}
		return ret;
	};
	
	$CTJ.override = function(origclass, overrides){
         if(overrides){
             var p = origclass.prototype;
             $CTJ.apply(p, overrides);
             if($CTJ.isIE && overrides.hasOwnProperty('toString')){
                 p.toString = overrides.toString;
             }
         }
    };
	$CTJ.extend = function(){
        // inline overrides
        var io = function(o){
            for(var m in o){
                this[m] = o[m];
            }
        };
        var oc = Object.prototype.constructor;

        return function(sb, sp, overrides){
            if(typeof sp == 'object'){
                overrides = sp;
                sp = sb;
                sb = overrides.constructor != oc ? overrides.constructor : function(){sp.apply(this, arguments);};
            }
            var F = function(){},
                sbp,
                spp = sp.prototype;

            F.prototype = spp;
            sbp = sb.prototype = new F();
            sbp.constructor=sb;
            sb.superclass=spp;
            if(spp.constructor == oc){
                spp.constructor=sp;
            }
            sb.override = function(o){
                $CTJ.override(sb, o);
            };
            sbp.superclass = sbp.supr = (function(){
                return spp;
            });
            sbp.override = io;
            $CTJ.override(sb, overrides);
            sb.extend = function(o){return $CTJ.extend(sb, o);};
            return sb;
        };
    }();

})();

//console.log($CTJ);