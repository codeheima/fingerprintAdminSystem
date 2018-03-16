/**
 * 
	$CTJ.Ajax.request({
		method:'POST',  //'POST' , 'GET'
		url :'',
		params : {},
		callback : function(text,status,response){
			//status == success 
			//response ==  Object {readyState: 4, responseText: "{"123":"555","666":"777","name":"7买买提"}", status: 200, statusText: "OK"}
			
		},
		listeners : {
			beforeSend: '',
			afterSend : ''
		}
  
	});
 * 
 * 
 * 
 */

$CTJ.Ajax = {
	request : function(opt){
		var de = {
			method:'POST',  //'POST' , 'GET'
			url :'',
			params : {},
			callback : function(){
				
			},
			listeners : {
				beforeSend: '',
				afterSend : ''
			}
		};
		var opt = $CTJ.apply(de,opt);
		
		
//		var isPost = opt.method  == 'GET'?false: true;
		var sendType = opt.method  == 'GET'?'GET': 'POST';
		
		$.ajax({
			
		   type: sendType,
		   url: opt.url,
		   data : opt.params,  // {a:'a1',b:'b2'}
		   success: function(text,status,response){
			   
			   if($CTJ.isFunction(opt.callback)){
				   opt.callback(text,status,response);
			   }
		  
		   },
		   error : function(response, status, errorThrown){
			   
			   if($CTJ.isFunction(opt.callback)){
				   opt.callback(errorThrown,status,response);
				   
			   }
		   }
		});
	
	},
	//opt.url : 'xx/xx.js'
	loadScript: function(opt){
		$.ajax({
		  type: "GET",
		  url: opt.url,
		  dataType: "script"
		});
	}
};

