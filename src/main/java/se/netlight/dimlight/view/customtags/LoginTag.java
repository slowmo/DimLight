package se.netlight.dimlight.view.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LoginTag extends SimpleTagSupport {
	
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();   
        JspWriter out = pageContext.getOut();
        out.println("<div class=\"login\"><form method=\"POST\" action=\"login.do\"><div class=\"namerow\">Name: <input name=\"name\" type=\"text\"/></div><div class=\"password\">Password: <input name=\"password\" type=\"password\"/></div><div class=\"submit\"><input type=\"submit\" value=\"Login\"/></div></form></div>");
    }
}
