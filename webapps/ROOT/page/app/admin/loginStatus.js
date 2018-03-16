// 登录状态
//console.log('登录状态');
var loginStatus = {
	
		jView : '',
		pagePlugin :'',
		tableTpl :'', //  new Ext.XTemplate($.tpl.control.userManager);
		
		refreshCurrentPage : function(){
			var cp = loginStatus.pagePlugin.pageVal.currentPage;
			loginStatus.pagePlugin.reqPageDatas(cp);
		},
		
		init: function(){
		//	loginStatus.pagePlugin.reqPageDatas(1);
			var g = loginStatus;
			g.tableTpl = new $CTJ.XTemplate(tpl.control.loginStatus );
			g.tableTpl.compile(); 

			g.jView = $('#childDiv').find('div[menu=loginStatus]');
			g.jView.append(tpl.loginStatus.res);

			
			
			loginStatus.pagePlugin = new $CTJ.plugin.Page({
				showSize:'big',   //small  normal  big
				url:APP_CONSTANTS.root + '/common/queryPage.do',
				baseParams:{
					scriptId:'platformdb.customer.queryLoginLog'
				},
				parentDiv : $('#page_loginStatus'),
				currentPage:1,
				pageSize:10,
				listeners:{
					afterLoad : function(data, p){
						var tbody = $('#table_loginStatus').find('tbody[viewMark=tbody]');
						tbody.empty();
						$('#page_loginStatus_count').html('共' + data.totalCount + '条');
						for(var i =0; i< data.datas.length; i++){
							var d = data.datas[i];
							if(d.logType == '1'){
								d.logTypeStr = '登录';
							}else{
								d.logTypeStr = '注销';
							}
							var tr = loginStatus.tableTpl.append(tbody[0], d);
							var jtr = $(tr);
							jtr.attr('mark',d.cusId);
							jtr.data('data',d);
							
						}
					}
				}
			});
			

			$('#btn_loginStatus_res').bind('click',function(){
				var panel = $('#loginStatus_modal_res');
				$zf.util.form.clear(panel);
				panel.modal('show');
			});
			
			
			loginStatus.pagePlugin.reqPageDatas(1);
			
			
		},
		show : function(){
			loginStatus.pagePlugin.reqPageDatas(1);
		}
};

loginStatus.submitRes = function(){
	var panel = $('#loginStatus_modal_res');
	
	var vals = $zf.util.form.getVals(panel);
	var page = loginStatus.pagePlugin;
	page.baseParams ={
		scriptId:'platformdb.customer.queryLoginLog'
	};
	
	for(var name in vals){
		page.baseParams[name] = vals[name];
	}
	
	page.reqPageDatas(1);
	panel.modal('hide');
	
}
