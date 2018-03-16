ns[tpl.loginStatus]

//查询
DEF[res]
	<!-- 模态框（Modal）dfsdfdf -->
	<div class="modal fade" id="loginStatus_modal_res" tabindex="-1" role="dialog"  aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" 
							aria-hidden="true">×
					</button>
					<h4 class="modal-title" >
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
							<label for="lastname" class="col-sm-2 control-label">员工编号</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="cusCode"
									   placeholder="请输员工编号" />
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" 
							data-dismiss="modal">关闭
					</button>
					<button type="button" onclick="loginStatus.submitRes()" class="btn btn-primary">
						提交更改
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
DEF_END[res]
