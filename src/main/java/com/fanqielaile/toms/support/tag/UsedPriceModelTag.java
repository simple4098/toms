package com.fanqielaile.toms.support.tag;

import com.fanqielaile.toms.enums.UsedPriceModel;
import com.fanqielaile.toms.support.util.Constants;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * DESC : 渠道的价格模式
 * @author : 番茄木-ZLin
 * @data : 2015/9/29
 * @version: v1.0.0
 */
public class UsedPriceModelTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        StringBuilder stringBuilder = new StringBuilder("<select class=\"col-xs-10 col-sm-5 ace usedPriceModel\" style=\" width: 100%; \" id=\"usedPriceModel\" name=\"usedPriceModel\">");
        UsedPriceModel[] values = UsedPriceModel.values();
        stringBuilder.append("<option value=''>").append(Constants.OPTION).append("</option>");
        for (UsedPriceModel c:values){
            stringBuilder.append("<option value='"+c.name()+"'>").append(c.getText()).append("</option>");
        }
        stringBuilder.append("</select>");
        getJspContext().getOut().write(stringBuilder.toString());
    }
}
