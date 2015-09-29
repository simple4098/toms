package com.fanqielaile.toms.support.tag;

import com.fanqielaile.toms.enums.PayMethod;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * DESC : 页面显示支付集合
 * @author : 番茄木-ZLin
 * @data : 2015/9/29
 * @version: v1.0.0
 */
public class PayMethodTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        PayMethod[] payMethods = PayMethod.values();
        StringBuilder sb = new StringBuilder("<select name=\"payMethod\" id=\"payMethodId\">");
        for (PayMethod p:payMethods){
            sb.append("<option value='"+p.name()+"'>").append(p.getDesc()).append("</option>");
        }
        sb.append("</select>");
        getJspContext().getOut().write(sb.toString());
    }
}
