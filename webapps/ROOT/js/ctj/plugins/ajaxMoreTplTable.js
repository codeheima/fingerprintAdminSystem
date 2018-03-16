/**
 * options:
       {
			url:'',
			mainPanel:'',             //mainPanel
			moreControlPanel : '',    //morePanel  如果想有翻页效果，这个值必须传入,不然全部显示
			isHideMorePanel : false,  //用来手动处理加载数据事件的方式..
			tpl:'',
			page:{
				pageSize : 10,
				way : 'local'          // way :  local || ajax   (默认 local)
			},
			listeners:{
				onRowClick:'',         //单击事件     e,data,tr,mainDiv
				rowContextmenu: '',     // 右键菜单   e,data,tr,mainDiv
				itemClick:{},			//目标itemClick  e,data,attrVal,jqItemTemp,mainDiv
				afterLoadByPages:''   //查询数据完成时   webPagination.list,mainDiv
			}
		};
 *     
 *  method:
 *     setParams({a:1,b:2});
 *     getParams();     
 *     clearParams();   
 *     loadDatas(url);   //url : 如果填写，将会替换原有的url
 *     
 *     getMainPanel();
 *     getOptions();
 *     
 *     showMore();
 */ 

/**
 * 
  	var panel = new $CTJ.plugin.ajaxMoreTplTable({
        url : '/';
		mainPanel:$('#targetListPanel'),             //mainPanel
		tpl:tpl,
		page:{
			pageSize : 20,
			way : 'local'          // way :  local || ajax   (默认 local)
		}
	});
 * 
 */

$CTJ.plugin.ajaxMoreTplTable = function(options){
	var global = this;
	
	//default
	var defaultOptions = {
		url:'',
		mainPanel:'',
		tpl:'',
		downDecHeight:200,
		params: {},
		page:{
			pageSize : 15,
		    way : 'ajax'          // way :  local || ajax   (默认 local)
	    },
		listeners:{
			onRowClick:'',         //单击事件     e,data,tr,mainDiv
			rowContextmenu: '',     // 右键菜单   e,data,tr,mainDiv
			itemClick:{},			//目标itemClick  e,data,attrVal,jqItemTemp,mainDiv  需要配合[clickMark 标签]
			afterLoadByPages:''   //查询数据完成时   webPagination.list,mainDiv
		}
	};
	
	this.options = $CTJ.apply(defaultOptions,options);
	this.params = {};
	this.isAjax = false;
	this.mainPanel = this.options.mainPanel;
	this.downDecHeight = this.options.downDecHeight;
//	this.morePanel = this.options.moreControlPanel;
//	this.isHideMorePanel = this.options.isHideMorePanel;
	this.url = this.options.url;
	this.currentList = []; //当前数据...
	this.showSize = 0; 
	this.page = this.options.page;
	
	this.initPanel();
	
	
	$(window).scroll(function(){ 
		// 拿到滚动位置
		var scrollY = window.pageYOffset || window.scrollY
				|| document.documentElement.scrollTop;
		var contextH = $(document).height();
//		var windowH = $(window).height();
		var windowH = window.innerHeight;
		if(scrollY >= (contextH-windowH)) {
	//	if((scrollY + global.downDecHeight - contextH) > 0) {
			//触发滚动条事件
			console.log('contextH-windowH:[' + (contextH-windowH) +'] ,scrollY: ' + scrollY);
			global.doAjaxMoreData();
		}
	});

//	console.log(this.getOptions());
	
/*	if(this.morePanel != '' ){
		this.morePanel.bind("click", function(e){
	//		console.log('click more...');
			global.loadMoreData();
		});
		this.morePanel.hide();
		this.page.hasPage =  true;
	}else{
		this.page.hasPage =  false;
		
	}
*/

}//end $CTJ.plugin.ajaxMoreTplTable


//functions
$CTJ.plugin.ajaxMoreTplTable.prototype ={
	getOptions : function (){
		return this.options;
	},
	setParams : function (params){
		this.params = params;
	},
	clearParams : function (){
		this.params = {};
	},
	clearData : function (){
		var dataPanel = this.mainPanel.find('div[viewMark=data]');
		dataPanel.empty();
	},
	getMoreControlPanel : function(){
		return moreControlPanel;
	},
	playTpl:function(tpl){
		if(tpl != undefined){
			this.options.tpl = tpl;
		}
		return this.options.tpl;
	},
	getMainPanel : function (){
		return this.mainPanel;
	},
	initPanel : function(){
		this.mainPanel.append('<div viewMark="data"></div>');
		this.mainPanel.append('<img viewMark="loadingMore" alt="" src="/cjda/themes/cjda/app_timeLine/images/loading-0.gif" style="width: 40px; margin-left: 48%; display: none;"></img>');
		this.mainPanel.append('<div viewMark="loadingMoreNoneData" class="data-no-more" style="display:none;">无更多数据</div>');
	},
	/**
		currentPage: 1
		hasNext: true
		hasPrevious: false
		list: Array[15]
		mark: "toPage"
		maxDisplayRecords: 0
		nextPage: 2
		pageSize: 15
		previousPage: 0
		totalPage: 3
		totalRecords: 31
	 * 
	 */
	loadByAjax : function (options){
		var global = this;
		global.isAjax = true;
		var sendParam = {};
		for(var name in this.params){
			sendParam[name] = this.params[name];
		}
		if(options == undefined){
			this.page.currentPage = 1;
			this.page.hasNext = true;
			this.page.hasPrevious = false;
			
			sendParam.currentPage = 1;
	//		sendParam.pageSize =this.page.pageSize;
		}else{
			sendParam.currentPage = options.nextPage;
		}
		//开始加载数据
		
		
		$.ajax({
			type: "POST",
			url: this.url,
			data: sendParam,
			success: function(msg){
				global.isAjax = false;
				global.currentData = msg;
				//加载结束
				//msg.totalRecords;
				global.addMoreData(msg.list);
			
				var mainPanel = global.mainPanel;
				var loadingMore = mainPanel.find('img[viewMark=loadingMore]');
				var loadingMoreNoneData = mainPanel.find('div[viewMark=loadingMoreNoneData]');
				if(msg.hasNext){
					//存在更多
					loadingMoreNoneData.hide();
					loadingMore.show();
				}else{
					//不存在更多
					loadingMoreNoneData.show();
					loadingMore.hide();
				}
			}
		});
	},
	doAjaxMoreData : function(){
		console.log('doAjaxMoreData');
		var global = this;
		
		if(global.currentData == undefined){
			//数据尚未准备完毕
			return;
		}
		if(this.isAjax){
			//前一次ajax尚未完成
			return;
		}
		if(global.currentData.hasNext){
			global.loadByAjax(global.currentData);
		}
	},
	//更新当前数据
	loadByData : function (datas){
		this.currentList = datas != undefined ? datas : []; //当前数据...
		this.showSize = 0; 
		if(this.page.hasPage == false){
			this.page.pageSize = datas.length;
		}
		this.mainPanel.empty();
		this.loadMoreData();
	},
	
	loadMoreData : function(){
		if(this.options.page.way != 'ajax'){
			//TODO 处理ajax  
			console.log('仅支持ajax');
			return;
		}
		
		
		var currentList = this.currentList; //当前数据...
		var showSize = this.showSize; 
		
		//处理当前页数据
		var tempDatas = [];
		for(var i = 0; i< this.page.pageSize && showSize < currentList.length; i++){
			var d = currentList[showSize];
			tempDatas.push(d);
			showSize++;
		}
//		this.showSize = showSize;
		
		//控制隐藏more_btn
		/*if(showSize >= currentList.length){
			if(this.page.hasPage == true){
				this.morePanel.hide();
			}
		}else{
		
		}*/
		
		this.addMoreData(tempDatas);
		
	}, //end loadMoreData
	//datas : 需要新增的数据
	addMoreData : function(datas){   
		//TODO 显示新增数据  -- OK
		if(datas == undefined || !$CTJ.isArray(datas) || datas.length == 0){
			return;
		}
		var mainPanel = this.mainPanel;
		var tpl = this.options.tpl;
		var morePanel = this.morePanel;
		var listeners = this.options.listeners;
		var dataPanel = mainPanel.find('div[viewMark=data]');
		
		for(var i = 0; i< datas.length; i++){
			var data = datas[i];
			var el = tpl.append(dataPanel[0],data);
			var jqEl = $(el);
			
			//addlisteners
			this.addListeners(data,jqEl,mainPanel);
		}
		
		if($CTJ.isFunction(listeners.afterLoadByPages)){
			listeners.afterLoadByPages(datas,mainPanel);
		}
	},
	addListeners : function (data,jqEl,mainPanel){
		var listeners = this.options.listeners;
		var options = this.options;
		//加入单击事件
		if($CTJ.isFunction(listeners.onRowClick)){
			jqEl.bind("click", function(e){
				e.preventDefault();
				listeners.onRowClick(e,data,jqEl,mainPanel);
			});
		}
		
		//item单击事件
		var clickMarks = jqEl.find('[clickMark]');

		clickMarks.each(function(n,d){
			var jqItem = $(clickMarks[n]);
			var attrVal = jqItem.attr("clickMark");
			
			if($CTJ.isFunction(options.listeners.itemClick[attrVal])){
				jqItem.css('cursor','pointer');
				jqItem.bind("click", function(e){
					e.preventDefault();
					var jqItemTemp =$(e.currentTarget);
					options.listeners.itemClick[attrVal](e,data,attrVal,jqItemTemp,mainPanel);	
				});
			}
		});
		
		//contextmenu
		if($CTJ.isFunction(listeners.rowContextmenu)){
			jqEl.bind('contextmenu',function(e){
				e.preventDefault();
				listeners.rowContextmenu(e,data,jqEl,mainPanel);
			});
		}
	}//end addListeners
	
}//end $CTJ.plugin.ajaxMoreTplTable.prototype