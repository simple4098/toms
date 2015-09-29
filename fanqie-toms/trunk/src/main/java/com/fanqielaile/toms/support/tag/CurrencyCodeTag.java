package com.fanqielaile.toms.support.tag;

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
public class CurrencyCodeTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {

        StringBuilder stringBuilder = new StringBuilder("<select name=\"currency\" id=\"currencyId\">");
        CurrencyCode[] values = CurrencyCode.values();
        for (CurrencyCode c:values){
            stringBuilder.append("<option value='"+c.name()+"'>").append(c.getValue()).append("</option>");
        }
        stringBuilder.append("</select>");
        getJspContext().getOut().write(stringBuilder.toString());
    }
}
