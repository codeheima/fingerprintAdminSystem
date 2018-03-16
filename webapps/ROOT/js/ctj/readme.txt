1.名词解释：
ctj :  'tj'是太极.


2.引入顺序：(或直接引用build/ctj-all-0.1.js )  ...需要jquery 

core.js

base/String.js
base/Date.js
base/Format.js
base/DomHelper.js


extends/Template.js
extends/json.js


3.编译
  编译器： FileUnion.jar  |　　compile.bat
  合并的文件： ctj_list.js
　编译结果：  build/ctj-all-0.1.js


4.样例: test文件夹


2个参数 : {
	type:
	val:
}

虚拟身份展示页面：
	目前可以处理界面效果,存在问题如下：
	
1.确认类型传入数据类型：身份证 | 手机号 | IMEI | IMSI |
   身份证 --> 手机号 |   IMEI | IMSI |。。
   

2. title部分( 张三 / 18612873456 / NB123456 / IPHONE 6S )：是其他页面传过来，还是我这边再去库里查一次

3.最常用虚拟身份 : 身份证 | 手机号 | IMEI | IMSI | ADSL 的查询方式. ( 是否只展示前8个或前5个 )

4. 电子邮件/个人主页/电子商务/.. : 不同参数的查询方式 （同上）

5.点击电子邮件帐户|微信帐户|qq帐户|等 是否需要跳转相应查询页面？如需要请确认能够调整的类型及跳转目标.
   5.1 微信|qq |..im ----> im详情
   5.2 电子邮件 -----> ??
   5.3 个人主页 -----> ??
   5.4 板/BBS ---->??
   5.5 其他 -----> ??

   