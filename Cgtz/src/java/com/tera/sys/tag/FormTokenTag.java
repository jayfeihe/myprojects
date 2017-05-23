/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.sys.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.tera.util.RequestUtils;
import com.tera.util.StringUtils;
/**
 * @author wy
 * 在jsp页面中form添加<ta:formToken/>
 */
public class FormTokenTag extends TagSupport {

	/**
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doEndTag() throws JspException {
		String token = StringUtils.createToken();
		StringBuffer sb = new StringBuffer();
		String uri = RequestUtils.getURLPart(((HttpServletRequest) this.pageContext.getRequest()).getRequestURI());
		//System.out.println("FormTokenTag key:"+"formToken" + uri);
		//System.out.println("FormTokenTag value:" +token);
		this.pageContext.getSession().setAttribute("formToken", token);
		sb.append("<input id=\"formToken\" name=\"formToken\" type=\"hidden\" value=\"" + token + "\"/>");

		JspWriter out = pageContext.getOut();

		try {
			out.print(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return super.doEndTag();
	}


}
