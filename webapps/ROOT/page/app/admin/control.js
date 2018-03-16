console.log('control');
var topMenu;

$(function(){
	$CTJ.plugin.tplLoader([
	    APP_CONSTANTS.root+'/page/app/admin/control_tpl.js',
	    APP_CONSTANTS.root+'/page/app/admin/userManager_tpl.js',
	    APP_CONSTANTS.root+'/page/app/admin/loginStatus_tpl.js'
	]);
	
	//menu...
	topMenu = new TopMenu('#topMenuDiv');
	
	
	//用户管理
	userManagerView.init();
	topMenu.userManager = function(jli){
		var menu = jli.attr('menu');
		userManagerView.show();
	};
	
	
	//登录状态
	loginStatus.init();
	topMenu.loginStatus = function(jli){
//		console.log('登录状态');
		loginStatus.show();
	};
	
	//设备监控
	deviceMonitor.init();
	topMenu.deviceMonitor = function(jli){
//		console.log('设备监控');
		deviceMonitor.show();
		
	};
	
	
	var childDiv = new ChildDiv('#childDiv');
	
	childDiv.resize();
	
	
});

function ChildDiv(s){
	var jDiv = $(s);
	
	this.resize = function(){
		
		var mo = topMenu.jMenu.offset();		
		var h = window.innerHeight -  topMenu.jMenu.outerHeight(true);
		jDiv.height(h);
		
	}
}

function TopMenu(s){
	var jMenu = $(s);
	this.jMenu = jMenu;
	var global = this;
	this.height = jMenu.height();
	
	var jBtns = jMenu.find('li[viewMark=menu]');
	for(var i = 0;i< jBtns.length; i++){
		var jli = $(jBtns[i]);
		jli.bind("click", function(){
			jBtns.removeClass('active');
			$(this).addClass('active');
			var fuStr = $(this).attr('menu');
			
			var childDiv = $('#childDiv');
			
			childDiv.find('div[viewMark=child]').each(function (index, domEle) { 
				var jdom = $(this);
				var m = jdom.attr('menu');
				if(m == fuStr){
					jdom.show();
				}else{
					jdom.hide();
				}
			});

			if(global[fuStr] != undefined){
				global[fuStr]($(this))
			}
		});
	}
	
	

}

