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


$CTJ.plugin.easyMoreTplTable = function(options){
	var global = this;
	
	//default
	var defaultOptions = {
		url:'',
		mainPanel:'',
		moreControlPanel : '',
		isHideMorePanel : false,
		tpl:'',
		page:{
			hasPage : false,
			pageSize : 10,
		    way : 'local'          // way :  local || ajax   (默认 local)
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
	this.mainPanel = this.options.mainPanel;
	this.morePanel = this.options.moreControlPanel;
	this.isHideMorePanel = this.options.isHideMorePanel;
	this.url = this.options.url;
	this.currentList = []; //当前数据...
	this.showSize = 0; 
	this.page = this.options.page;
	
//	console.log(this.getOptions());
	
	if(this.morePanel != '' ){
		this.morePanel.bind("click", function(e){
	//		console.log('click more...');
			global.loadMoreData();
		});
		this.morePanel.hide();
		this.page.hasPage =  true;
	}else{
		this.page.hasPage =  false;
		
	}

}//end $CTJ.plugin.easyMoreTplTable


//functions
$CTJ.plugin.easyMoreTplTable.prototype ={
	getOptions : function (){
		return this.options;
	},
	setParams : function (params){
		this.params = params;
	},
	clearParams : function (){
		this.params = {};
	},
	getMoreControlPanel : function(){
		return moreControlPanel;
	},
	getMainPanel : function (){
		return this.mainPanel;
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
	loadByUrl : function (url){
		if(url != undefined ){
			this.url = url;
		}
		//TODO 通过url加载   -- 暂不支持
	},
	loadMoreData : function(){
		if(this.options.page.way == 'ajax'){
			//TODO 处理ajax  
			console.log('easyMoreTplTable 暂不支持...ajax');
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
		this.showSize = showSize;
		
		//控制隐藏more_btn
		if(showSize >= currentList.length){
			if(this.page.hasPage == true){
				this.morePanel.hide();
			}
		}else{
			if(this.page.hasPage == true && !this.isHideMorePanel){
				this.morePanel.show();
			}
		}
		
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
		for(var i = 0; i< datas.length; i++){
			var data = datas[i];
			var el = tpl.append(mainPanel[0],data);
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
	
}//end $CTJ.plugin.easyMoreTplTable.prototype