// 用户管理
//console.log('用户管理');
var userManagerView = {
	jView : '',
	pagePlugin :'',
	tableTpl :'', //  new Ext.XTemplate($.tpl.control.userManager);
	
	getData : function(btn){
		var id = $(btn).attr('bindId');
		var jtr = $('#table_userManager').find('tr[mark='+ id + ']');
		return jtr.data('data');

	},
	refreshCurrentPage : function(){
		
		var cp = userManagerView.pagePlugin.pageVal.currentPage;
		userManagerView.pagePlugin.reqPageDatas(cp);
		
	},
	/**
	 * 指纹定时刷新任务
	 */
	refreshZfTask : {
	    run: function(){
	    	var p = $('#user_modal_zf');
	    	if(p.css('display') == 'none'){
	    		return;
	    	}
	    	
	    	var vals = $zf.util.form.getVals(p);
	    	if(vals.cusId == undefined || '' == vals.cusId){
	    		return;
	    	}
	    	
	    	if(deviceMonitor.isOpen == null || deviceMonitor.isOpen == undefined ||deviceMonitor.isOpen == false ){
	    		console.log('conn not open ');
	    		return;
	    	}
	    	
	    	console.log('test to ajax');
	    	$.ajax({
    		   type: 'POST',
    		   async: false,
    		   url: APP_CONSTANTS.root + '/common/querySql.do',
    		   data : {
    			   dbName : 'zfdb',
    			   sql: "select * from CUSTOMER_CURRENT where CUS_ID = '"+ vals.cusId + " '"
    		   },
    		   success: function(text,status,response){
    			   var obj = $CTJ.JSON.decode(text);
    			   if(obj.count ==  0){
    				   return;
    			   }
    			   var data = obj.datas[0];
    			   $zf.util.form.setVals( $('#user_modal_zf'),data);
    			   
    		   }
    		});    	
	    },
	    interval: 1000 //1 second
	},
	
	init: function(){
	//	userManagerView.pagePlugin.reqPageDatas(1);
		var g = userManagerView;
		g.tableTpl = new $CTJ.XTemplate(tpl.control.userManager );
		g.tableTpl.compile(); 

		g.jView = $('#childDiv').find('div[menu=userManager]');
		g.jView.append(tpl.userManager.res);
		g.jView.append(tpl.userManager.add);
		g.jView.append(tpl.userManager.update);
		g.jView.append(tpl.userManager.zf);
		
		
		
		userManagerView.pagePlugin = new $CTJ.plugin.Page({
			showSize:'big',   //small  normal  big
			url:APP_CONSTANTS.root + '/common/queryPage.do',
			baseParams:{
				scriptId:'platformdb.customer.query'
			},
			parentDiv : $('#page_customer'),
			currentPage:1,
			pageSize:10,
			listeners:{
				afterLoad : function(data, p){
					var tbody = $('#table_userManager').find('tbody[viewMark=tbody]');
					tbody.empty();
					$('#page_customer_count').html('共' + data.totalCount + '条');
					for(var i =0; i< data.datas.length; i++){
						var d = data.datas[i];
						var tr = userManagerView.tableTpl.append(tbody[0], d);
						var jtr = $(tr);
						jtr.attr('mark',d.cusId);
						jtr.data('data',d);
						
					}
				}
			}
		});
		

		$('#btn_user_res').bind('click',function(){
			var panel = $('#user_modal_res');
			$zf.util.form.clear(panel);
			panel.modal('show');
		});
		
		$('#btn_user_add').bind('click',function(){
			userManagerView.add();
		});
		
		userManagerView.pagePlugin.reqPageDatas(1);
		
		
		//指纹模态窗口事件：
		$('#user_modal_zf').on('hide.bs.modal', function () {
			$zf.util.form.clear($('#user_modal_zf'));
			
		});
		
		var task = {
			
			}
		$CTJ.TaskManager.start(userManagerView.refreshZfTask);
	},
	show : function(){
		userManagerView.pagePlugin.reqPageDatas(1);
	}
};

userManagerView.submitRes = function(){
	var panel = $('#user_modal_res');
	
	var vals = $zf.util.form.getVals(panel);
	var page = userManagerView.pagePlugin;
	page.baseParams ={
		scriptId:'platformdb.customer.query'
	};
	
	for(var name in vals){
		page.baseParams[name] = vals[name];
	}
	
	page.reqPageDatas(1);
	panel.modal('hide');
	
};

userManagerView.add = function(){
	var panel = $('#user_modal_add');
	$zf.util.form.clear(panel);
	panel.modal('show');
};

userManagerView.submitAdd = function(){
	var vals = $zf.util.form.getVals($('#user_modal_add'));
	vals.tableId  = 'zfdb.customer';
	$.ajax({
	   type: 'POST',
	   url: APP_CONSTANTS.root + '/common/insert.do',
	   data : vals,
	   success: function(text,status,response){
		   var obj = $CTJ.JSON.decode(text);
		   alert(obj.msg);
		   userManagerView.refreshCurrentPage();
		   $('#user_modal_add').modal('hide');
	   }
	});
};
//指纹 -- 显示窗口
userManagerView.hand = function(btn){
	var datas = userManagerView.getData(btn);
	
	var panel = $('#user_modal_zf');
	$zf.util.form.clear(panel);
	
	$zf.util.form.setVals(panel,datas);
	panel.modal('show');
	panel.find('label[cusName=cusName]').html('' + datas.cusName);
	
	$.ajax({
	   type: 'POST',
	   url: APP_CONSTANTS.root + '/cus/currentZf.do',
	   data : {cusId : datas.cusId},
	   success: function(text,status,response){
		   
	   }
	});
};
//指纹 - 刷新 
userManagerView.refreshzf = function(){
	console.log('刷新');
	
};
//指纹 - 合并
userManagerView.zfhb = function(){
   	var p = $('#user_modal_zf');
	if(p.css('display') == 'none'){
		return;
	}
	var vals = $zf.util.form.getVals(p);
	$.ajax({
	   type: 'POST',
	   url: APP_CONSTANTS.root + '/cus/unionZf.do',
	   success: function(text,status,response){
		   var obj = $CTJ.JSON.decode(text);
		   alert(obj.msg);
		   userManagerView.refreshCurrentPage();
		   if('true' == ('' + obj.isSuccess)){
			   var panel = $('#user_modal_zf');
				$zf.util.form.clear(panel);
				panel.modal('hide');
		   }
	   }
	});
};

//修改 
userManagerView.update = function(btn){
	var datas = userManagerView.getData(btn);
//	console.log('update');
	var panel = $('#user_modal_update');
	
	panel.modal({
		keyboard: true
	});
	
	$zf.util.form.clear(panel);
	$zf.util.form.setVals(panel,datas);
	
};

userManagerView.submitUpdate = function(){
	var vals = $zf.util.form.getVals($('#user_modal_update'));
	vals.tableId  = 'zfdb.customer';
	
	$.ajax({
	   type: 'POST',
	   url: APP_CONSTANTS.root + '/common/update.do',
	   data : vals,
	   success: function(text,status,response){
		   var obj = $CTJ.JSON.decode(text);
		   alert(obj.msg);
		   userManagerView.refreshCurrentPage();
		   $('#user_modal_update').modal('hide');
	   }
	
	});
	
};

//删除
userManagerView.del = function(btn){
	var msg = "您真的确定要删除吗？\n\n请确认！";
	if (confirm(msg)==true){
		var datas = userManagerView.getData(btn);
		var param = {
			tableId : 'zfdb.customer',	
			cusId : datas.cusId
		};
		$.ajax({
			   type: 'POST',
			   url: APP_CONSTANTS.root + '/common/del.do',
			   data : param,
			   success: function(text,status,response){
				   var obj = $CTJ.JSON.decode(text);
				   alert(obj.msg);
				   userManagerView.refreshCurrentPage();
			   }
		});
	}
};


userManagerView.delRelationZf = function(btn){
	var msg = "您真的确定要删除吗？\n\n请确认！";
	if (confirm(msg)==true){
		var datas = userManagerView.getData(btn);
		$.ajax({
			   type: 'POST',
			   url: APP_CONSTANTS.root + '/cus/delZf.do',
			   data : {
					cusId : datas.cusId
			   },
			   success: function(text,status,response){
				   var obj = $CTJ.JSON.decode(text);
				   alert(obj.msg);
				   userManagerView.refreshCurrentPage();
			   }
		});
	}
};

