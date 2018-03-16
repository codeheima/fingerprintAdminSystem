$(document).ready(function(){
	var p = new $CTJ.plugin.Page({
		showSize:'big',   //small  normal  big
		url:APP_CONSTANTS.root + '/common/queryPage.do',
		baseParams:{
			a:'bc',
			bbb:'c122',
			scriptId:'platformdb.customer.query'
		},
		parentDiv : $('#testPage'),
		currentPage:1,
		pageSize:10,
		listeners:{
			afterLoad : function(datas, p){
				console.log('ddddddd');
				console.log(datas);
			}
		}
	});

	$('#btnRes').bind('click',function(){
		
		p.renderTo();
		
	});
	
	$('#btnP1').bind('click',function(){
		p.reqPageDatas(1);
	});
	
});


