/*
var opt={
	showSize:'big',   //small  normal  big
	url:'xxx.do',
	baseParams:{},
	currentPage:1,
	pageSize:10,
	parentDiv : '',
	listeners:{
		afterLoad :  function(datas,page){}
	}
};
	currentPage : 2
	totalPage  : 3
	pageSize   : 10
	totalNum  :  25
*/
$CTJ.plugin.Page = function(options){
	var g = this;

	this.url = '';
	this.showSize = 'big';
	this.AjaxType = 'POST';
	this.pageSize = 10;
	this.currentPage = 1;
	
	this.parentDiv = '';
	this.listeners = {
		afterLoad : ''  //  function(datas,page){}
	};
	
	$CTJ.apply(this, options);
	
	if(g.baseParams == undefined){
		g.baseParams = {};
	}
	this.pageVal = {
		currentPage : this.currentPage,
		pageSize : this.pageSize,
		totalPage : 5,
		totalNum : 0
	}
	
	if(this.parentDiv != '' && undefined != this.parentDiv){
		this.renderTo();
	}
	
	
};//end $CTJ.plugin.Page


//functions
$CTJ.plugin.Page.prototype ={
		
	reqPageDatas : function(page){
		var g = this;
		var params = {};
		for(var name in g.baseParams){
			params[name] = g.baseParams[name];
		}
		
		g._addPageParams(params,page);
		$.ajax({
		   type: g.AjaxType,
		   url: g.url,
		   data : params,
		   success: function(text,status,response){
			   
			   var obj = $CTJ.JSON.decode(text);
		//	   console.log(obj);
			   g.pageVal.currentPage = obj.currentPage;
			   g.pageVal.pageSize = obj.pageSize;
			   g.pageVal.totalCount = obj.totalCount;
	//		   g.pageVal.totalPage = obj.totalPage;
			   g.pageVal.totalPage =(obj.totalCount % obj.pageSize == 0)? obj.totalCount / obj.pageSize: Math.floor(obj.totalCount / obj.pageSize) + 1;
			   
		//	   console.log(g.pageVal.totalPage);
			   if(g.parentDiv != ''){
				   g.renderTo();
			   }
			   if($CTJ.isFunction(g.listeners.afterLoad)){
				   g.listeners.afterLoad(obj,g);
			   }
		   },
		   error : function(XMLHttpRequest, textStatus, errorThrown){
			   console.log(XMLHttpRequest);
			   console.log(textStatus);
			   console.log(errorThrown);
		   }
		});
		
	},
	setBaseParams : function(params){
		if(null == params || undefined == params){
			params = {};
		}
		this.baseParams = params;
	},
	renderTo : function(jDiv){
		if(jDiv != undefined){
			this.parentDiv = jDiv;
		}
		this.parentDiv.empty();
		this.parentDiv.append( this.getView());
	},
	/**
	 * 
	 <ul class="pagination pagination-lg">
		<li class="disabled"><a href="javaScript:void(0)">&laquo;</a></li>
		<li><a href="#">1</a></li>
		<li><a href="#">2</a></li>
		<li class="active"><a href="#" >3</a></li>
		<li class="disabled"><a href="#">4</a></li>
		<li><a href="#">5</a></li>
		<li><a href="#">&raquo;</a></li>
	</ul>
	currentPage : 2
	totalPage  : 3
	pageSize   : 10
	totalNum  :  25
	 * 
	 * 
	 */
	getView:function(){
		var g = this;
		var sizeClass = '';
		if(g.showSize== 'small'){
			sizeClass = 'pagination-sm';
		}else if(g.showSize== 'big'){
			sizeClass = 'pagination-lg';
		}
		
		var pageView = $('<ul style="margin:10px 0" class="pagination ' + sizeClass + '"></ul>'); 
		
		var min = g.pageVal.currentPage -1;
		if(min <= 1){
			min = 1;
		}
		for( ;min >1 && g.pageVal.currentPage -  min < 2;min-- ){
		}
		
		if(min == 1){
			pageView.append('<li viewMark="0" ><a href="javaScript:void(0)">&laquo;</a></li>');
		}else{
			pageView.append('<li viewMark="' +(min - 1 ) + '" ><a href="javaScript:void(0)">&laquo;</a></li>');
		}
		var i = min 
		for(; i< min + 5; i++){
			if(i > g.pageVal.totalPage){
				break;
			}
			pageView.append('<li viewMark="' + i + '" ><a href="javaScript:void(0)">' + i + '</a></li>');
		}
		
		pageView.append('<li  viewMark="' + i + '"  ><a href="javaScript:void(0)">&raquo;</a></li>');
		
		pageView.find('[viewMark]').each(function (index, domEle) { 
			var jli = $(domEle);
			var viewMark = parseInt(jli.attr('viewMark'));
			if(viewMark == g.pageVal.currentPage){
				jli.addClass('active');
			}else if(viewMark <=0 || viewMark > g.pageVal.totalPage  ){
				jli.addClass('disabled');
			}else{
				jli.on("click", function(){
					var page = parseInt($(this).attr('viewMark'));
					g.reqPageDatas(page);
				});
			}
		});
		
		
		return pageView;
	},
	/**
	 * 添加页数信息
	 */
	_addPageParams : function(params,page){
		var g = this;
		params.pageSize = g.pageVal.pageSize;
		params.currentPage = page;
	}
	
	
}//end $CTJ.plugin.Page.prototype