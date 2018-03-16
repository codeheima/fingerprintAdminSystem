package org.ma.taglib.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TagRoot extends BodyTagSupport {
	
	public int doStartTag() throws JspException {

        return EVAL_BODY_BUFFERED;

    }
	
	public int doAfterBody() throws JspException {

	    return SKIP_BODY;	
	}
    
 
	
	public int doEndTag() throws JspException{
    	
		print(((HttpServletRequest)this.pageContext.getRequest()).getContextPath());
    	
    	return EVAL_PAGE; 
    
	}	
	
    public void println(String html) throws JspException{
    	
    	try{
    		
    		pageContext.getOut().println(html);
    		
    	}catch(Exception e){
    		
    		throw new JspException(e);
    		
    	}
    	
    }
    
    public void print(String html) throws JspException{
    	try{
    		
    		pageContext.getOut().print(html);
    		
		}catch(Exception e){
			
			throw new JspException(e);
		
		}   
    	
    } 
}
