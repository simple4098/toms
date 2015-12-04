package com.fanqielaile.toms.support.tag;

import com.fanqielaile.toms.enums.CompanyType;
import com.fanqielaile.toms.enums.CurrencyCode;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * DESC : 页面显示货币集合
 * @author : 番茄木-ZLin
 * @data : 2015/9/29
 * @version: v1.0.0
 */
public class CompanyTypeTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        StringBuilder stringBuilder = new StringBuilder("<select class=\"col-xs-10 col-sm-5 ace companyType\" name=\"companyType\">");
        CompanyType[] values = CompanyType.values();
        for (CompanyType c:values){
            stringBuilder.append("<option value='"+c.name()+"'>").append(c.getDesc()).append("</option>");
        }
        stringBuilder.append("</select>");
        getJspContext().getOut().write(stringBuilder.toString());
    }
}
