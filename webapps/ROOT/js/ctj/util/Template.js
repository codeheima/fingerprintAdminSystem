

/**
 * @class $CTJ.Template
 * 
Represents an HTML fragment template. Templates may be {@link #compile precompiled}
 * for greater performance.


 * 
For example usage {@link #Template see the constructor}.


 *
 * @constructor
 * An instance of this class may be created by passing to the constructor either
 * a single argument, or multiple arguments:
 * 

 * 
single argument : String/Array
 * 

 * The single argument may be either a String or an Array:

 * 
String : 

var t = new $CTJ.Template("<div>Hello {0}.</div>");
t.{@link #append}('some-element', ['foo']);
 * 

 * 
Array : 

 * An Array will be combined with join('').

var t = new $CTJ.Template([
    '<div name="{id}">',
        '<span class="{cls}">{name:trim} {value:ellipsis(10)}</span>',
    '</div>',
]);
t.{@link #compile}();
t.{@link #append}('some-element', {id: 'myid', cls: 'myclass', name: 'foo', value: 'bar'});

 * 

 * 
multiple arguments : String, Object, Array, ...
 * 

 * Multiple arguments will be combined with join('').
 * 

var t = new $CTJ.Template(
    '<div name="{id}">',
        '<span class="{cls}">{name} {value}</span>',
    '</div>',
    // a configuration object:
    {
        compiled: true,      // {@link #compile} immediately
        disableFormats: true // See Notes below.
    }
);
 * 

 * 
Notes:


 * 

 * 
Formatting and disableFormats are not applicable for $CTJ Core.

 * 
For a list of available format functions, see {@link $CTJ.util.Format}.

 * 
disableFormats reduces {@link #apply} time
 * when no formatting is required.

 * 

 * 

 * 

 * @param {Mixed} config
 */
$CTJ.Template = function(html){
    var me = this,
        a = arguments,
        buf = [],
        v;

    if ($CTJ.isArray(html)) {
        html = html.join("");
    } else if (a.length > 1) {
        for(var i = 0, len = a.length; i < len; i++){
            v = a[i];
            if(typeof v == 'object'){
                $CTJ.apply(me, v);
            } else {
                buf.push(v);
            }
        };
        html = buf.join('');
    }

    /**@private*/
    me.html = html;
    
/**
     * @cfg {Boolean} compiled Specify true to compile the template
     * immediately (see {@link #compile}).
     * Defaults to false.
     */
    if (me.compiled) {
        me.compile();
    }
};
$CTJ.Template.prototype = {
    
/**
     * @cfg {RegExp} re The regular expression used to match template variables.
     * Defaults to:

     * re : /\{([\w-]+)\}/g                                     // for $CTJ Core
     * re : /\{([\w-]+)(?:\:([\w\.]*)(?:\((.*?)?\))?)?\}/g      // for $CTJ JS
     * 

     */
    re : /\{([\w-]+)\}/g,
    
/**
     * See {@link #re}.
     * @type RegExp
     * @property re
     */

    
/**
     * Returns an HTML fragment of this template with the specified values applied.
     * @param {Object/Array} values
     * The template values. Can be an array if the params are numeric (i.e. {0})
     * or an object (i.e. {foo: 'bar'}).
     * @return {String} The HTML fragment
     */
    applyTemplate : function(values){
        var me = this;

        return me.compiled ?
                me.compiled(values) :
                me.html.replace(me.re, function(m, name){
                    return values[name] !== undefined ? values[name] : "";
                });
    },

    
/**
     * Sets the HTML used as the template and optionally compiles it.
     * @param {String} html
     * @param {Boolean} compile (optional) True to compile the template (defaults to undefined)
     * @return {$CTJ.Template} this
     */
    set : function(html, compile){
        var me = this;
        me.html = html;
        me.compiled = null;
        return compile ? me.compile() : me;
    },

    
/**
     * Compiles the template into an internal function, eliminating the RegEx overhead.
     * @return {$CTJ.Template} this
     */
    compile : function(){
        var me = this,
            sep = $CTJ.isGecko ? "+" : ",";

        function fn(m, name){
            name = "values['" + name + "']";
            return "'"+ sep + '(' + name + " == undefined ? '' : " + name + ')' + sep + "'";
        }

        eval("this.compiled = function(values){ return " + ($CTJ.isGecko ? "'" : "['") +
             me.html.replace(/\\/g, '\\\\').replace(/(\r\n|\n)/g, '\\n').replace(/'/g, "\\'").replace(this.re, fn) +
             ($CTJ.isGecko ?  "';};" : "'].join('');};"));
        return me;
    },

    
/**
     * Applies the supplied values to the template and inserts the new node(s) as the first child of el.
     * @param {Mixed} el The context element
     * @param {Object/Array} values The template values. Can be an array if your params are numeric (i.e. {0}) or an object (i.e. {foo: 'bar'})
     * @param {Boolean} returnElement (optional) true to return a $CTJ.Element (defaults to undefined)
     * @return {HTMLElement/$CTJ.Element} The new node or Element
     */
    insertFirst: function(el, values, returnElement){
        return this.doInsert('afterBegin', el, values, returnElement);
    },

    
/**
     * Applies the supplied values to the template and inserts the new node(s) before el.
     * @param {Mixed} el The context element
     * @param {Object/Array} values The template values. Can be an array if your params are numeric (i.e. {0}) or an object (i.e. {foo: 'bar'})
     * @param {Boolean} returnElement (optional) true to return a $CTJ.Element (defaults to undefined)
     * @return {HTMLElement/$CTJ.Element} The new node or Element
     */
    insertBefore: function(el, values, returnElement){
        return this.doInsert('beforeBegin', el, values, returnElement);
    },

    
/**
     * Applies the supplied values to the template and inserts the new node(s) after el.
     * @param {Mixed} el The context element
     * @param {Object/Array} values The template values. Can be an array if your params are numeric (i.e. {0}) or an object (i.e. {foo: 'bar'})
     * @param {Boolean} returnElement (optional) true to return a $CTJ.Element (defaults to undefined)
     * @return {HTMLElement/$CTJ.Element} The new node or Element
     */
    insertAfter : function(el, values, returnElement){
        return this.doInsert('afterEnd', el, values, returnElement);
    },

    
/**
     * Applies the supplied values to the template and appends
     * the new node(s) to the specified el.
     * 
For example usage {@link #Template see the constructor}.


     * @param {Mixed} el The context element
     * @param {Object/Array} values
     * The template values. Can be an array if the params are numeric (i.e. {0})
     * or an object (i.e. {foo: 'bar'}).
     * @param {Boolean} returnElement (optional) true to return an $CTJ.Element (defaults to undefined)
     * @return {HTMLElement/$CTJ.Element} The new node or Element
     */
    append : function(el, values){
        return this.doInsert('beforeEnd', el, values);
    },

    doInsert : function(where, el, values){
        el = $CTJ.getDom(el);
        var newNode = $CTJ.DomHelper.insertHtml(where, el, this.applyTemplate(values));
        return  newNode;
    },

    
/**
     * Applies the supplied values to the template and overwrites the content of el with the new node(s).
     * @param {Mixed} el The context element
     * @param {Object/Array} values The template values. Can be an array if your params are numeric (i.e. {0}) or an object (i.e. {foo: 'bar'})
     * @param {Boolean} returnElement (optional) true to return a $CTJ.Element (defaults to undefined)
     * @return {HTMLElement/$CTJ.Element} The new node or Element
 */   
    overwrite : function(el, values, returnElement){
        el = $CTJ.getDom(el);
        el.innerHTML = this.applyTemplate(values);
        return returnElement ? $CTJ.get(el.firstChild, true) : el.firstChild;
    }
 
};
/**
 * Alias for {@link #applyTemplate}
 * Returns an HTML fragment of this template with the specified values applied.
 * @param {Object/Array} values
 * The template values. Can be an array if the params are numeric (i.e. {0})
 * or an object (i.e. {foo: 'bar'}).
 * @return {String} The HTML fragment
 * @member $CTJ.Template
 * @method apply
 */
$CTJ.Template.prototype.apply = $CTJ.Template.prototype.applyTemplate;

/**
 * Creates a template from the passed element's value (display:none textarea, preferred) or innerHTML.
 * @param {String/HTMLElement} el A DOM element or its id
 * @param {Object} config A configuration object
 * @return {$CTJ.Template} The created template
 * @static
 */
$CTJ.Template.from = function(el, config){
    el = $CTJ.getDom(el);
    return new $CTJ.Template(el.value || el.innerHTML, config || '');
};



$CTJ.apply($CTJ.Template.prototype, {
    /**
     * @cfg {Boolean} disableFormats Specify <tt>true</tt> to disable format
     * functions in the template. If the template does not contain
     * {@link $CTJ.util.Format format functions}, setting <code>disableFormats</code>
     * to true will reduce <code>{@link #apply}</code> time. Defaults to <tt>false</tt>.
     * <pre><code>
var t = new $CTJ.Template(
    '&lt;div name="{id}"&gt;',
        '&lt;span class="{cls}"&gt;{name} {value}&lt;/span&gt;',
    '&lt;/div&gt;',
    {
        compiled: true,      // {@link #compile} immediately
        disableFormats: true // reduce <code>{@link #apply}</code> time since no formatting
    }
);
     * </code></pre>
     * For a list of available format functions, see {@link $CTJ.util.Format}.
     */
    disableFormats : false,
    /**
     * See <code>{@link #disableFormats}</code>.
     * @type Boolean
     * @property disableFormats
     */

    /**
     * The regular expression used to match template variables
     * @type RegExp
     * @property
     * @hide repeat doc
     */
    re : /\{([\w-]+)(?:\:([\w\.]*)(?:\((.*?)?\))?)?\}/g,
    argsRe : /^\s*['"](.*)["']\s*$/,
    compileARe : /\\/g,
    compileBRe : /(\r\n|\n)/g,
    compileCRe : /'/g,

    /**
     * Returns an HTML fragment of this template with the specified values applied.
     * @param {Object/Array} values The template values. Can be an array if your params are numeric (i.e. {0}) or an object (i.e. {foo: 'bar'})
     * @return {String} The HTML fragment
     * @hide repeat doc
     */
    applyTemplate : function(values){
        var me = this,
            useF = me.disableFormats !== true,
            fm = $CTJ.util.Format,
            tpl = me;

        if(me.compiled){
            return me.compiled(values);
        }
        function fn(m, name, format, args){
            if (format && useF) {
                if (format.substr(0, 5) == "this.") {
                    return tpl.call(format.substr(5), values[name], values);
                } else {
                    if (args) {
                        // quoted values are required for strings in compiled templates,
                        // but for non compiled we need to strip them
                        // quoted reversed for jsmin
                        var re = me.argsRe;
                        args = args.split(',');
                        for(var i = 0, len = args.length; i < len; i++){
                            args[i] = args[i].replace(re, "$1");
                        }
                        args = [values[name]].concat(args);
                    } else {
                        args = [values[name]];
                    }
                    return fm[format].apply(fm, args);
                }
            } else {
                return values[name] !== undefined ? values[name] : "";
            }
        }
        return me.html.replace(me.re, fn);
    },

    /**
     * Compiles the template into an internal function, eliminating the RegEx overhead.
     * @return {$CTJ.Template} this
     * @hide repeat doc
     */
    compile : function(){
        var me = this,
            fm = $CTJ.util.Format,
            useF = me.disableFormats !== true,
            sep = $CTJ.isGecko ? "+" : ",",
            body;

        function fn(m, name, format, args){
            if(format && useF){
                args = args ? ',' + args : "";
                if(format.substr(0, 5) != "this."){
                    format = "fm." + format + '(';
                }else{
                    format = 'this.call("'+ format.substr(5) + '", ';
                 //   args = ", values";
                    args = ",[ values" + args + "]";
                }
            }else{
                args= ''; format = "(values['" + name + "'] == undefined ? '' : ";
            }
            return "'"+ sep + format + "values['" + name + "']" + args + ")"+sep+"'";
        }

        // branched to use + in gecko and [].join() in others
        if($CTJ.isGecko){
            body = "this.compiled = function(values){ return '" +
                   me.html.replace(me.compileARe, '\\\\').replace(me.compileBRe, '\\n').replace(me.compileCRe, "\\'").replace(me.re, fn) +
                    "';};";
        }else{
            body = ["this.compiled = function(values){ return ['"];
            body.push(me.html.replace(me.compileARe, '\\\\').replace(me.compileBRe, '\\n').replace(me.compileCRe, "\\'").replace(me.re, fn));
            body.push("'].join('');};");
            body = body.join('');
        }
        eval(body);
        return me;
    },

    // private function used to call members
    call : function(fnName, value, allValues){
        return this[fnName](value, allValues);
    }
});
$CTJ.Template.prototype.apply = $CTJ.Template.prototype.applyTemplate;
