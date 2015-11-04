package com.fanqielaile.toms.support.decorator;

import com.fanqie.support.IParameterMatcher;
import com.fanqie.support.PageDecorator;
import com.fanqie.support.PageNode;
import com.fanqie.util.Pagination;

import java.util.List;


/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/10/27
 * @version: v1.0.0
 */
public class FrontendPagerDecorator extends PageDecorator {
    private String anchor;


    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }


    public FrontendPagerDecorator(Pagination pagination) {
        super(pagination);
    }

    public FrontendPagerDecorator(IParameterMatcher matcher, Pagination pagination) {
        super(matcher, pagination);
    }

    public FrontendPagerDecorator() {
    }

    @Override
    protected StringBuilder factoryCurrentPageTag(int i) {
        return new StringBuilder(" <li class=\"active\">  <a>").append(i).append("</a> </li>");
    }

    private boolean containPageNo(int pageNo) {
        List<PageNode> pageNodes = this.generatePages();
        for (PageNode pageNode : pageNodes) {
            if (pageNode.getNo() == pageNo) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected StringBuilder generatePre(boolean hasPre) {
        StringBuilder code = new StringBuilder();
        if (hasPre) {
            code.append("<li> <a onclick=\"").append("page(").append(getCurrentPage() - 1).append(")").append("\">")
                    .append("<i class=\"icon-double-angle-left\"></i> </a></li>");
            if (!containPageNo(1)) {
                code.append("<li> <a onclick=\"").append("page(").append(1).append(")").append("\">1</a> </li>");
                code.append("<li><a>...</a></li>");
            }
        } else {
            code.append("");
        }
        return code;

    }

    @Override
    protected StringBuilder generateNext(boolean hasNext) {
        StringBuilder code = new StringBuilder();
        if (hasNext) {
            if (!containPageNo(getTotalPages())) {
                code.append("<li><a>...</a></li>");
                code.append("<li><a onclick=\"").append("page("+getTotalPages()+")").append("\">").append(getTotalPages()).append("</a></li>");
            }
            code.append("<li> <a onclick=\"").append("page(").append(getCurrentPage() + 1).append(")").append("\">")
                    .append("<i class=\"icon-double-angle-right\"></i> </a> </li>");
        } else {
            code.append("");
        }
        return code;
    }

    @Override
    protected String buildUrl(int page) {
        String link = super.buildUrl(page);
        int linkLen = link.length() - 1;
        Character lastChar = link.charAt(linkLen);
        if (lastChar.equals('?')) {
            link = link.substring(0, linkLen);
        }
        return anchor == null ? link : link.concat("#" + anchor);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public int getTotalCount() {
        return this.getPagination().getRows();
    }

    @Override
    protected int obtainPageNumberOffset() {
        return 7;
    }

    @Override
    protected StringBuilder buildContainer(StringBuilder body) {
        return new StringBuilder("<div class=\"text-center\"><div class=\"text-center\"><ul class=\"pagination\">").append(body).append("</ul></div></div>");
    }

    @Override
    protected StringBuilder generateNodes(List<PageNode> pages)
    {
        StringBuilder code = new StringBuilder();
        for (PageNode page : pages) {
            code.append(page.isCurrent() ? factoryCurrentPageTag(page.getNo()) : factoryLinkTag(buildUrl(page.getNo()), page.getNo()));
        }
        return code;
    }
    @Override
    protected StringBuilder factoryLinkTag(String linkUrl, int pageNumber) {
        return new StringBuilder("<li>  <a onclick=\"page("+pageNumber+")\">"+pageNumber+"</a></li>");
    }
    @Override
    protected StringBuilder factoryTag(String tag, String linkAttr, String link, String tagText) {
        return new StringBuilder("<").append(tag).append(" ").append(linkAttr).append("=").append("\"").append(link).append("\"").append(">").append(tagText).append("</").append(tag).append(">");
    }

    public static void main(String... s) {
        Pagination pagination = new Pagination(2, 10);
        pagination.setRows(100);
        pagination.setPageCount(10);
        FrontendPagerDecorator frontendPagerDecorator = new FrontendPagerDecorator(pagination);
        System.out.print(frontendPagerDecorator.build("#", "#"));
    }
}
