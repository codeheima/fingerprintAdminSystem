package org.apache.jsp.page;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(5);
    _jspx_dependants.add("/page/include/ez.jsp");
    _jspx_dependants.add("/page/include/ConstantsPage.jsp");
    _jspx_dependants.add("/WEB-INF/tlds/ma-web-tag.tld");
    _jspx_dependants.add("/page/include/css_js.jsp");
    _jspx_dependants.add("/page/include/css.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fw_005froot_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fw_005froot_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = JspFactory.getDefaultFactory().getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fw_005froot_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("\t<title>登录</title>\r\n");

	request.getSession().setAttribute("loginType", "user");

	String loginStatusStr = request.getParameter("loginStatus");
	String loginStatus = "1";
	if("fff".equals(loginStatusStr)){
		loginStatus = "2";
	}

      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t ");
      out.write("\r\n");
      out.write("\r\n");
      out.write(" ");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	String rootPath = request.getContextPath();

      out.write("\r\n");
      out.write("<!-- 加载各种常量  -->\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar APP_CONSTANTS = {\r\n");
      out.write("\t\troot :'");
      if (_jspx_meth_w_005froot_005f0(_jspx_page_context))
        return;
      out.write("' , //根路径...\t\r\n");
      out.write("\t\tUpperLetterArr : ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z']\r\n");
      out.write("\t};\r\n");
      out.write("\r\n");
      out.write("</script>");
      out.write('\r');
      out.write('\n');
      out.write(' ');
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!--  jquery -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/jquery/jquery-3.2.1.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- font-awesome -->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/css/font-awesome/css/font-awesome.min.css\"></link>  \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- bootstrap -->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/bootstarp/bootstrap.css\"></link>  \r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/bootstarp/bootstrap.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!--  ctj -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/ctj/build/ctj-all-0.1.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!--  ctj -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/js/util/util.form.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("  \r\n");
      out.write(" \r\n");
      out.write("  ");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("\t.bg-div{\r\n");
      out.write("\t\tbackground:#031126;\r\n");
      out.write("\t\tposition:fixed;\r\n");
      out.write("\t\tleft:0;\r\n");
      out.write("\t\ttop:-59px;\r\n");
      out.write("\t\tright:0;\r\n");
      out.write("\t\tbottom:0;\r\n");
      out.write("\t\tz-index:-1;\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\t\r\n");
      out.write("\r\n");
      out.write("</style>");
      out.write("\r\n");
      out.write("\t \r\n");
      out.write("\t <script language=\"javaScript\">\r\n");
      out.write("\t\t function loginAdmin(){\r\n");
      out.write("\t\t\t $('#adminForm').submit();\t \r\n");
      out.write("\t\t }\r\n");
      out.write("\t\t $(function(){\r\n");
      out.write("\t\t\t var loginStatus = '");
      out.print(loginStatus);
      out.write("';\r\n");
      out.write("\t\t\t if(loginStatus == 2){\r\n");
      out.write("\t\t\t\t $('#errorDiv').html('密码错误');\r\n");
      out.write("\t\t\t\t \r\n");
      out.write("\t\t\t }\r\n");
      out.write("\t\t });\r\n");
      out.write("\t\t \r\n");
      out.write("\t </script>\r\n");
      out.write("\t \r\n");
      out.write("</head> \r\n");
      out.write("<body>\r\n");
      out.write("\t<div style=\"width:80%;\"><h2 style=\"text-align:center;\" >登录</h2> </div>\r\n");
      out.write("\t<form id=\"adminForm\" class=\"form-horizontal\" method=\"post\" style=\"width:70%\" role=\"form\"  action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/page/admin.jsp\">\r\n");
      out.write("\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t<label class=\"col-sm-2 control-label\">用户名</label>\r\n");
      out.write("\t\t\t<div class=\"col-sm-10\">\r\n");
      out.write("\t\t\t\t<p class=\"form-control-static\">admin</p>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t<label for=\"inputPassword\" class=\"col-sm-2 control-label\">密码</label>\r\n");
      out.write("\t\t\t<div class=\"col-sm-10\"> \r\n");
      out.write("\t\t\t\t<input type=\"password\" class=\"form-control\" id=\"inputPassword\"  name=\"inputPassword\"\r\n");
      out.write("\t\t\t\t\tplaceholder=\"请输入密码\">\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"form-group\" >\r\n");
      out.write("\t\t\t<label for=\"\" class=\"col-sm-2 control-label\">&nbsp;</label>\r\n");
      out.write("\t\t\t<button style=\"margin-left:10px;\" onclick=\"loginAdmin()\" type=\"button\" class=\"btn btn-primary btn-lg \">登录</button>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t   ");
      out.write("\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</form>\r\n");
      out.write("\t<div id=\"errorDiv\" style=\"color: red;font-size:14px;padding-left: 160px;\"></div>\r\n");
      out.write("\t\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_w_005froot_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  w:root
    org.ma.taglib.web.TagRoot _jspx_th_w_005froot_005f0 = (org.ma.taglib.web.TagRoot) _005fjspx_005ftagPool_005fw_005froot_005fnobody.get(org.ma.taglib.web.TagRoot.class);
    _jspx_th_w_005froot_005f0.setPageContext(_jspx_page_context);
    _jspx_th_w_005froot_005f0.setParent(null);
    int _jspx_eval_w_005froot_005f0 = _jspx_th_w_005froot_005f0.doStartTag();
    if (_jspx_th_w_005froot_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fw_005froot_005fnobody.reuse(_jspx_th_w_005froot_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fw_005froot_005fnobody.reuse(_jspx_th_w_005froot_005f0);
    return false;
  }
}
