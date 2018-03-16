
/**
 * @class $CTJ.DomHelper
 * 
The DomHelper class provides a layer of abstraction from DOM and transparently supports creating
 * elements via DOM or using HTML fragments. It also has the ability to create HTML fragment templates
 * from your DOM building code.


 *
 * 
DomHelper element specification object


 * 
A specification object is used when creating elements. Attributes of this object
 * are assumed to be element attributes, except for 4 special attributes:
 * 


 * 
tag : 
The tag name of the element

 * 
children : or cn
An array of the
 * same kind of element definition objects to be created and appended. These can be nested
 * as deep as you want.

 * 
cls : 
The class attribute of the element.
 * This will end up being either the "class" attribute on a HTML fragment or className
 * for a DOM node, depending on whether DomHelper is using fragments or DOM.

 * 
html : 
The innerHTML for the element

 * 

 *
 * 
Insertion methods


 * 
Commonly used insertion methods:
 * 


 * 
{@link #append} : 

 * 
{@link #insertBefore} : 

 * 
{@link #insertAfter} : 

 * 
{@link #overwrite} : 

 * 
{@link #createTemplate} : 

 * 
{@link #insertHtml} : 

 * 

 *
 * 
Example


 * 
This is an example, where an unordered list with 3 children items is appended to an existing
 * element with id 'my-div':

 


var dh = $CTJ.DomHelper; // create shorthand alias
// specification object
var spec = {
    id: 'my-ul',
    tag: 'ul',
    cls: 'my-list',
    // append children after creating
    children: [     // may also specify 'cn' instead of 'children'
        {tag: 'li', id: 'item0', html: 'List Item 0'},
        {tag: 'li', id: 'item1', html: 'List Item 1'},
        {tag: 'li', id: 'item2', html: 'List Item 2'}
    ]
};
var list = dh.append(
    'my-div', // the context element 'my-div' can either be the id or the actual node
    spec      // the specification object
);
 

 * 
Element creation specification parameters in this class may also be passed as an Array of
 * specification objects. This can be used to insert multiple sibling nodes into an existing
 * container very efficiently. For example, to add more list items to the example above:


dh.append('my-ul', [
    {tag: 'li', id: 'item3', html: 'List Item 3'},
    {tag: 'li', id: 'item4', html: 'List Item 4'}
]);
 * 

 *
 * 
Templating


 * 
The real power is in the built-in templating. Instead of creating or appending any elements,
 * {@link #createTemplate} returns a Template object which can be used over and over to
 * insert new elements. Revisiting the example above, we could utilize templating this time:
 * 


// create the node
var list = dh.append('my-div', {tag: 'ul', cls: 'my-list'});
// get template
var tpl = dh.createTemplate({tag: 'li', id: 'item{0}', html: 'List Item {0}'});

for(var i = 0; i < 5, i++){
    tpl.append(list, [i]); // use template to append to the actual node
}
 * 

 * 
An example using a template:


var html = '{2}';

var tpl = new $CTJ.DomHelper.createTemplate(html);
tpl.append('blog-roll', ['link1', 'http://www.jackslocum.com/', "Jack's Site"]);
tpl.append('blog-roll', ['link2', 'http://www.dustindiaz.com/', "Dustin's Site"]);
 * 

 *
 * 
The same example using named parameters:


var html = '{text}';

var tpl = new $CTJ.DomHelper.createTemplate(html);
tpl.append('blog-roll', {
    id: 'link1',
    url: 'http://www.jackslocum.com/',
    text: "Jack's Site"
});
tpl.append('blog-roll', {
    id: 'link2',
    url: 'http://www.dustindiaz.com/',
    text: "Dustin's Site"
});
 * 

 *
 * 
Compiling Templates


 * 
Templates are applied using regular expressions. The performance is great, but if
 * you are adding a bunch of DOM elements using the same template, you can increase
 * performance even further by {@link $CTJ.Template#compile "compiling"} the template.
 * The way "{@link $CTJ.Template#compile compile()}" works is the template is parsed and
 * broken up at the different variable points and a dynamic function is created and eval'ed.
 * The generated function performs string concatenation of these parts and the passed
 * variables instead of using regular expressions.
 * 


var html = '{text}';

var tpl = new $CTJ.DomHelper.createTemplate(html);
tpl.compile();

//... use template like normal
 * 

 *
 * 
Performance Boost


 * 
DomHelper will transparently create HTML fragments when it can. Using HTML fragments instead
 * of DOM can significantly boost performance.


 * 
Element creation specification parameters may also be strings. If {@link #useDom} is false,
 * then the string is used as innerHTML. If {@link #useDom} is true, a string specification
 * results in the creation of a text node. Usage:


 * 

$CTJ.DomHelper.useDom = true; // force it to use DOM; reduces performance
 * 

 * @singleton
 */
$CTJ.DomHelper = function(){
    var tempTableEl = null,
        emptyTags = /^(?:br|frame|hr|img|input|link|meta|range|spacer|wbr|area|param|col)$/i,
        tableRe = /^table|tbody|tr|td$/i,
        confRe = /tag|children|cn|html$/i,
        tableElRe = /td|tr|tbody/i,
        cssRe = /([a-z0-9-]+)\s*:\s*([^;\s]+(?:\s*[^;\s]+)*);?/gi,
        endRe = /end/i,
        pub,
        // kill repeat to save bytes
        afterbegin = 'afterbegin',
        afterend = 'afterend',
        beforebegin = 'beforebegin',
        beforeend = 'beforeend',
        ts = '<table>',
        te = '</table>',
        tbs = ts+'<tbody>',
        tbe = '</tbody>'+te,
        trs = tbs + '<tr>',
        tre = '</tr>'+tbe;

    // private
    function doInsert(el, o, pos, sibling, append){
        var newNode = pub.insertHtml(pos, $CTJ.getDom(el), createHtml(o));
        return  newNode;
    }

    // build as innerHTML where available
    function createHtml(o){
        var b = '',
            attr,
            val,
            key,
            cn;

        if(typeof o == "string"){
            b = o;
        } else if ($CTJ.isArray(o)) {
            for (var i=0; i < o.length; i++) {
                if(o[i]) {
                    b += createHtml(o[i]);
                }
            };
        } else {
            b += '<' + (o.tag = o.tag || 'div');
            for (attr in o) {
                val = o[attr];
                if(!confRe.test(attr)){
                    if (typeof val == "object") {
                        b += ' ' + attr + '="';
                        for (key in val) {
                            b += key + ':' + val[key] + ';';
                        };
                        b += '"';
                    }else{
                        b += ' ' + ({cls : 'class', htmlFor : 'for'}[attr] || attr) + '="' + val + '"';
                    }
                }
            };
            // Now either just close the tag or try to add children and close the tag.
            if (emptyTags.test(o.tag)) {
                b += '/>';
            } else {
                b += '>';
                if ((cn = o.children || o.cn)) {
                    b += createHtml(cn);
                } else if(o.html){
                    b += o.html;
                }
                b += '';
            }
        }
        return b;
    }

    function ieTable(depth, s, h, e){
        tempTableEl.innerHTML = [s, h, e].join('');
        var i = -1,
            el = tempTableEl,
            ns;
        while(++i < depth){
            el = el.firstChild;
        }
//      If the result is multiple siblings, then encapsulate them into one fragment.
        if(ns = el.nextSibling){
            var df = document.createDocumentFragment();
            while(el){
                ns = el.nextSibling;
                df.appendChild(el);
                el = ns;
            }
            el = df;
        }
        return el;
    }

    /**
     * @ignore
     * Nasty code for IE's broken table implementation
     */
    function insertIntoTable(tag, where, el, html) {
        var node,
            before;

        tempTableEl = tempTableEl || document.createElement('div');

        if(tag == 'td' && (where == afterbegin || where == beforeend) ||
           !tableElRe.test(tag) && (where == beforebegin || where == afterend)) {
            return;
        }
        before = where == beforebegin ? el :
                 where == afterend ? el.nextSibling :
                 where == afterbegin ? el.firstChild : null;

        if (where == beforebegin || where == afterend) {
            el = el.parentNode;
        }

        if (tag == 'td' || (tag == 'tr' && (where == beforeend || where == afterbegin))) {
             node = ieTable(4, trs, html, tre);
        } else if ((tag == 'tbody' && (where == beforeend || where == afterbegin)) ||
                   (tag == 'tr' && (where == beforebegin || where == afterend))) {
              node = ieTable(3, tbs, html, tbe);
        } else {
            node = ieTable(2, ts, html, te);
        }
        el.insertBefore(node, before);
        return node;
    }


    pub = {
        
/**
         * Returns the markup for the passed Element(s) config.
         * @param {Object} o The DOM object spec (and children)
         * @return {String}
         */
        markup : function(o){
            return createHtml(o);
        },

        


        
/**
         * Inserts an HTML fragment into the DOM.
         * @param {String} where Where to insert the html in relation to el - beforeBegin, afterBegin, beforeEnd, afterEnd.
         * @param {HTMLElement} el The context element
         * @param {String} html The HTML fragment
         * @return {HTMLElement} The new node
         */
        insertHtml : function(where, el, html){
            var hash = {},
                hashVal,
                setStart,
                range,
                frag,
                rangeEl,
                rs;

            where = where.toLowerCase();
            // add these here because they are used in both branches of the condition.
            hash[beforebegin] = ['BeforeBegin', 'previousSibling'];
            hash[afterend] = ['AfterEnd', 'nextSibling'];

            if (el.insertAdjacentHTML) {
                if(tableRe.test(el.tagName) && (rs = insertIntoTable(el.tagName.toLowerCase(), where, el, html))){
                    return rs;
                }
                // add these two to the hash.
                hash[afterbegin] = ['AfterBegin', 'firstChild'];
                hash[beforeend] = ['BeforeEnd', 'lastChild'];
                if ((hashVal = hash[where])) {
                    el.insertAdjacentHTML(hashVal[0], html);
                    return el[hashVal[1]];
                }
            } else {
                range = el.ownerDocument.createRange();
                setStart = 'setStart' + (endRe.test(where) ? 'After' : 'Before');
                if (hash[where]) {
                    range[setStart](el);
                    frag = range.createContextualFragment(html);
                    el.parentNode.insertBefore(frag, where == beforebegin ? el : el.nextSibling);
                    return el[(where == beforebegin ? 'previous' : 'next') + 'Sibling'];
                } else {
                    rangeEl = (where == afterbegin ? 'first' : 'last') + 'Child';
                    if (el.firstChild) {
                        range[setStart](el[rangeEl]);
                        frag = range.createContextualFragment(html);
                        if(where == afterbegin){
                            el.insertBefore(frag, el.firstChild);
                        }else{
                            el.appendChild(frag);
                        }
                    } else {
                        el.innerHTML = html;
                    }
                    return el[rangeEl];
                }
            }
            throw 'Illegal insertion point -> "' + where + '"';
        },

        
/**
         * Creates new DOM element(s) and inserts them before el.
         * @param {Mixed} el The context element
         * @param {Object/String} o The DOM object spec (and children) or raw HTML blob
         * @param {Boolean} returnElement (optional) true to return a $CTJ.Element
         * @return {HTMLElement/$CTJ.Element} The new node
         */
        insertBefore : function(el, o){
            return doInsert(el, o, beforebegin);
        },

        
/**
         * Creates new DOM element(s) and inserts them after el.
         * @param {Mixed} el The context element
         * @param {Object} o The DOM object spec (and children)
         * @param {Boolean} returnElement (optional) true to return a $CTJ.Element
         * @return {HTMLElement/$CTJ.Element} The new node
         */
        insertAfter : function(el, o){
            return doInsert(el, o, afterend, 'nextSibling');
        },

        
/**
         * Creates new DOM element(s) and inserts them as the first child of el.
         * @param {Mixed} el The context element
         * @param {Object/String} o The DOM object spec (and children) or raw HTML blob
         * @param {Boolean} returnElement (optional) true to return a $CTJ.Element
         * @return {HTMLElement/$CTJ.Element} The new node
         */
        insertFirst : function(el, o){
            return doInsert(el, o,  afterbegin, 'firstChild');
        },

        
/**
         * Creates new DOM element(s) and appends them to el.
         * @param {Mixed} el The context element
         * @param {Object/String} o The DOM object spec (and children) or raw HTML blob
         * @param {Boolean} returnElement (optional) true to return a $CTJ.Element
         * @return {HTMLElement/$CTJ.Element} The new node
         */
        append : function(el, o){
            return doInsert(el, o, beforeend, '', true);
        },

        
/**
         * Creates new DOM element(s) and overwrites the contents of el with them.
         * @param {Mixed} el The context element
         * @param {Object/String} o The DOM object spec (and children) or raw HTML blob
         * @param {Boolean} returnElement (optional) true to return a $CTJ.Element
         * @return {HTMLElement/$CTJ.Element} The new node
        
        overwrite : function(el, o, returnElement){
            el = $CTJ.getDom(el);
            el.innerHTML = createHtml(o);
            return returnElement ? $CTJ.get(el.firstChild) : el.firstChild;
        },
 */
        createHtml : createHtml
        
        
    };
    return pub;
}();