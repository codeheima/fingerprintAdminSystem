ns[tpl.userManager]

//查询
DEF[res]
	<!-- 模态框（Modal）dfsdfdf -->
	<div class="modal fade" id="user_modal_res" tabindex="-1" role="dialog"  aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" 
							aria-hidden="true">×
					</button>
					<h4 class="modal-title" id="myModalLabel">
						查询
					</h4>
				</div>
				<div class="modal-body" >
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label  class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="cusName"
									   placeholder="请输入姓名" />
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">公司</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="cusCompany"
									   placeholder="请输公司" />
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">员工编号</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="cusCode"
									   placeholder="请输员工编号" />
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">性别</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="cusSex"
									   placeholder="请输性别" />
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" 
							data-dismiss="modal">关闭
					</button>
					<button type="button" onclick="userManagerView.submitRes()" class="btn btn-primary">
						提交更改
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
DEF_END[res]

//新增
DEF[add]
	
<!-- 模态框（Modal）dfsdfdf -->
<div class="modal fade" id="user_modal_add" tabindex="-1" role="dialog"  aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					新增用户
				</h4>
			</div>
			<div class="modal-body" >
				<form class="form-horizontal" role="form">
					<input type="hidden" name="cusId" />
					<div class="form-group">
						<label  class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="cusName"
								   placeholder="请输入姓名" />
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">公司</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="cusCompany"
								   placeholder="请输公司" />
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">员工编号</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="cusCode"
								   placeholder="请输员工编号" />
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="cusSex"
								   placeholder="请输性别" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" 
						data-dismiss="modal">关闭
				</button>
				<button type="button" onclick="userManagerView.submitAdd()" class="btn btn-primary">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
	
DEF_END[add]


//update
DEF[update]
	
<!-- 模态框（Modal） -->
<div class="modal fade" id="user_modal_update" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					修改用户
				</h4>
			</div>
			<div class="modal-body" >
				<form class="form-horizontal" role="form">
					<input type="hidden" name="cusId" />
					<div class="form-group">
						<label  class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="cusName"
								   placeholder="请输入姓名" />
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">公司</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="cusCompany"
								   placeholder="请输公司" />
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">员工编号</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="cusCode"
								   placeholder="请输员工编号" />
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="cusSex"
								   placeholder="请输性别" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" 
						data-dismiss="modal">关闭
				</button>
				<button type="button" onclick="userManagerView.submitUpdate()" class="btn btn-primary">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
	
DEF_END[update]

DEF[zf]
<!-- 模态框（Modal） 指纹 -->
<div class="modal fade" id="user_modal_zf" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					指纹处理
				</h4>
			</div>
			<div class="modal-body" >
				<form class="form-horizontal" role="form">
					<input type="hidden" name="cusId" /> 
					<div class="form-group">
						<label  class="col-sm-2 control-label" cusName="cusName"></label>

					</div>
					<div class="form-group">
						<label  class="col-sm-2 control-label">指纹1</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="zf1"
								   placeholder="指纹1" />
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">指纹2</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="zf2"
								   placeholder="指纹2" />
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">指纹3</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="zf3"
								   placeholder="指纹3" />
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">合并指纹</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="zfhb"
								   placeholder="合并指纹" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" 
						data-dismiss="modal">关闭
				</button>
				<button type="button"  onclick="userManagerView.zfhb()" class="btn btn-info">
					合并
				</button>
				<button type="button" onclick="userManagerView.submitZf()" class="btn btn-primary">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
	
DEF_END[zf]
