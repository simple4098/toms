package com.fanqielaile.toms.support.tag;

import com.fanqielaile.toms.enums.BedType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * DESC :床型显示
 * @author : 番茄木-ZLin
 * @data : 2015/9/29
 * @version: v1.0.0
 */
public class BedTypeTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        BedType[] bedTypes = BedType.values();
        StringBuilder sb = new StringBuilder("<select name=\"bedType\" id=\"bedTypeId\">");
        for (BedType b:bedTypes){
            sb.append("<option value='"+b.name()+"'>").append(b.getDesc()).append("</option>");
        }
        sb.append("</select>");
        getJspContext().getOut().write(sb.toString());
    }
}
