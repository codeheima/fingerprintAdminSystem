$CTJ.ns('$zf.util.form',window);

$zf.util.form.getVals =  function(panel){
	
	var values = {};
	$.each( panel.find('input[type=hidden][name]'),function(i,n){
		 if(undefined != n.name && '' != n.name){
		 	values[n.name]= n.value;
		 }
	});
	$.each( panel.find('input[type=text][name]'),function(i,n){
		 if(undefined != n.name && '' != n.name){
		 	values[n.name]= n.value;
		 }
	});
	$.each( panel.find('select[name]'),function(i,n){
		if(undefined != n.name && '' != n.name){
		 	values[n.name]= n.value;
		 }
	});
	
	$.each( panel.find('input[type=checkbox][name]'),function(i,n){
		 if(undefined != n.name && '' != n.name && undefined != n.value && '' != n.value ){
		 	if(n.checked == false){
		 		return;
		 	}
		 	if(values[n.name] == undefined){
		 		values[n.name] = [n.value];
		 	}else{
		 		values[n.name].push(n.value);
		 	}
		 }
	});
	
	return values;
}

$zf.util.form.clear = function(panel){
	$.each( panel.find('input[name]'),function(i,n){
		 $(n).val('');
	});
	$.each( panel.find('select[name]'),function(i,n){
		$(n).val('');
	});
};
$zf.util.form.setVals =  function(panel,vals){
	for(name in vals){
		var input = panel.find('input[name=' + name +']');
		if(input.length > 0){
			input.val(vals[name]);
		}
	}
}