/**
 * URL-参数封装工具类
 */
var UrlUtils;
(function() {
	UrlUtils = function() {
	}

	// 创建script
	UrlUtils.creatScript = function(src) {
		console.log();
		var script = document.createElement('script');
		script.language = 'javascript';
		script.src = getRootPath() + src;
		$('head')[0].appendChild(script);
		script = null;
	};
	// UrlUtils.creatScript('/themes/cjda/public/js/base64/jquery.base64.js');

	/**
	 * 
	 */
	UrlUtils.encodeAll = function() {
		var as = $("a[href]");
		$.each(as, function(i, a) {
			var href = $(a).attr("href");
			if (href.indexOf("?") > 0) {
				console.log("<-原url：" + href);
				var hrefs = href.split("?");
				var params = encodeBase64(hrefs[1]);
				console.log(hrefs[0] + "?params=" + params);
				console.log(String.fromCharCode(params));
			}
		})
	}

	UrlUtils.decodeAll = function() {

	}

	UrlUtils.encode = function(url) {

	}

	UrlUtils.decode = function(url) {

	}

	// 创建link
	UrlUtils.creatlink = function(lib) {
		var link = document.createElement('link');
		link.type = 'text/css';
		link.rel = 'stylesheet';
		link.href = getRootPath() + '/skin/' + lib + '.css';
		QD('head')[0].appendChild(link);
		link = null;
	};

}());

/**
 * 获取项目根路径
 * 
 * @returns
 */
function getRootPath() {
	return $(rootpath).val();
}