package com.fanqielaile.toms.support;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.model.Company;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/10/8
 * @version: v1.0.0
 */
public class CallableBean {

    private Company company;
    private OtaInfoRefDto o;
    private TBParam tbParam;

    public Company getCompany() {
        return company;
    }

    public CallableBean(Company company, OtaInfoRefDto o, TBParam tbParam) {
        this.company = company;
        this.o = o;
        this.tbParam = tbParam;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public OtaInfoRefDto getO() {
        return o;
    }

    public void setO(OtaInfoRefDto o) {
        this.o = o;
    }

    public TBParam getTbParam() {
        return tbParam;
    }

    public void setTbParam(TBParam tbParam) {
        this.tbParam = tbParam;
    }
}
