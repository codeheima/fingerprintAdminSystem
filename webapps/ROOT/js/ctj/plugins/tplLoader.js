$CTJ.plugin.tplLoader = function (urls){
	
	/*var scripts = document.getElementsByTagName('script');
	
	// Array.prototype.slice cannot be used on NodeList on IE8
	var jsxScripts = [];
	for (var i = 0; i < scripts.length; i++) {
		if (/^text\/cjtTpl(;|$)/.test(scripts.item(i).type)) {
			jsxScripts.push(scripts.item(i));
		}
	}

	if (jsxScripts.length < 1) {
		return;
	}

	loadScripts(jsxScripts);
	*/
	loadScripts(urls);
	function includeTpl(content, url, options){
		var temp = content;
		var nsStart = temp.indexOf('ns['),
		nsEnd = 0;
		var tempObj= '';
		if(nsStart >= 0 && nsStart<5){
			nsStart = nsStart + 3;
			nsEnd = temp.indexOf(']',nsStart);
			if(nsEnd >0 && (nsEnd - nsStart < 100)){  //100个字符够长了
				var nsStr = temp.substring(nsStart,nsEnd);
				tempObj = $CTJ.ns(nsStr,window);
		//		console.log(tempObj);
			}else{
				nsEnd = 0;
			}	
		}
		var defStart =  temp.indexOf('DEF[',nsEnd),defEnd = 1;
		while(defStart > 0 && defEnd > 0){
			var s = defStart;
			var e = temp.indexOf(']',s);
			var defStr = temp.substring(s + 4, e );
			var endStr = 'DEF_END['+ defStr +']';
			
			defEnd = temp.indexOf(endStr,e);
			
			var defContent = temp.substring(e + 1,defEnd).trim(); 
			if(defContent != undefined && defContent != ''){
		//		console.log(defStr);
		//		console.log(defContent);
				tempObj[defStr] = defContent;
			}
			
			if(defEnd > 0){
				defStart = defEnd + endStr.length;
				defStart = temp.indexOf('DEF[',defStart);
			}
		}
	}

	function loadScripts(scripts) {
		var result = [];
		var count = scripts.length;

		
		function checkScript(script){
			if (script.loaded && !script.executed) {
				script.executed = true;
				includeTpl(script.content, script.url, script.options);
			} else if (!script.loaded && !script.error && !script.async) {
				
			}
		}

		for(var i = 0; i<count ; i++ ){
			var script = scripts[i];
			var options = {
				sourceMap : true
			};
			if (/;harmony=true(;|$)/.test(script.type)) {
				options.harmony = true;
			}
			if (/;stripTypes=true(;|$)/.test(script.type)) {
				options.stripTypes = true;
			}

			// script.async is always true for non-javascript script tags
		//	var async = script.hasAttribute('async');
			if (script) {
				result[i] = {
				//	async : async,
					error : false,
					executed : false,
					content : null,
					loaded : false,
					url : script.src,
					options : options
				};

				load(script, function (content) {
					result[i].loaded = true;
					result[i].content = content;
					checkScript(result[i]);
				}, function () {
					result[i].error = true;
					checkScript(result[i]);
				});
			} else {
				result[i] = {
				//	async : async,
					error : false,
					executed : false,
					content : script.innerHTML,
					loaded : true,
					url : null,
					options : options
				};
				checkScript(result[i]);
			}
		};
	}//end loadScripts(scripts)

	function load(url, successCallback, errorCallback) {
		var xhr;
		xhr = window.ActiveXObject ? new window.ActiveXObject('Microsoft.XMLHTTP')
			 : new XMLHttpRequest();


		// DOM to mirror normal script loading.
		xhr.open('GET', url, false);  //true  async  ||  false sync
		if ('overrideMimeType' in xhr) {
			xhr.overrideMimeType('text/plain');
		}
		xhr.onreadystatechange = function () {
			if (xhr.readyState === 4) {
				if (xhr.status === 0 || xhr.status === 200) {
					successCallback(xhr.responseText);
				} else {
					errorCallback();
					throw new Error('Could not load ' + url);
				}
			}
		};
		return xhr.send(null);
	}	
}//end $CTJ.plugin.tplLoader




