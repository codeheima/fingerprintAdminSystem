ns[tpl.control]

//用户管理  tpl.control.userManager
DEF[userManager]
	
<tr>
	<td>{cusName}</td>
	<td>{cusCompany}</td>
	<td>{cusCode}</td>
	<td>{cusSex}</td>
	<td>{zfCount}</td>
	<td>
		<button type="button" bindId="{cusId}" onclick="userManagerView.hand(this)" class="btn btn-info btn-xs  fa fa-hand-stop-o">&nbsp;指纹</button>
		<button type="button" bindId="{cusId}" onclick="userManagerView.update(this)" class="btn btn-info btn-xs fa fa-edit">&nbsp;修改</button>
		<button type="button" bindId="{cusId}" onclick="userManagerView.del(this)" class="btn btn-danger btn-xs fa fa-trash-o">&nbsp;删除</button>
		<button type="button" bindId="{cusId}" onclick="userManagerView.delRelationZf(this)" class="btn btn-danger btn-xs fa fa-trash-o">&nbsp;删除指纹</button>
	</td>
</tr>
	
DEF_END[userManager]




//登录状态  tpl.control.loginStatus
DEF[loginStatus]
	
<tr>
	<td>{cusCode}</td>
	<td>{cusName}</td>
	<td>{logTypeStr}</td>
	<td>{logTime}</td>
	<td>{cpu}</td>
	<td>{mac}</td>
	<td>{ip}</td>
	<td>{logDes}</td>
</tr>

DEF_END[loginStatus]





