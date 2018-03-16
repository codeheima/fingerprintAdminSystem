// 设备管理
//console.log('用户管理');
var deviceMonitor = {
	
		
	isOpen : false,
	init: function(){
	},
	show: function(){
		$.ajax({
		   type: 'POST',
		   url: APP_CONSTANTS.root + '/device/isOpen.do',
		   success: function(text,status,response){
			   var obj = $CTJ.JSON.decode(text);
			   deviceMonitor.changeStatus(obj);  
		   }
		});
	},
	changeStatus : function(obj){
		if('true' == '' + obj.msg){
			$('#panel_device_status').css('background-color','green');
			deviceMonitor.isOpen = true;
		}else{
			$('#panel_device_status').css('background-color','red');
			deviceMonitor.isOpen = false;
		}
	}
};

deviceMonitor.open = function(){
	$.ajax({
	   type: 'POST',
	   url: APP_CONSTANTS.root + '/device/open.do',
	   success: function(text,status,response){
		   var obj = $CTJ.JSON.decode(text);
		   deviceMonitor.changeStatus(obj);  
	   }
	});
	
};

deviceMonitor.close = function(){
	$.ajax({
	   type: 'POST',
	   url: APP_CONSTANTS.root + '/device/close.do',
	   success: function(text,status,response){
		   var obj = $CTJ.JSON.decode(text);
		   deviceMonitor.changeStatus(obj);  
	   }
	});
	
};