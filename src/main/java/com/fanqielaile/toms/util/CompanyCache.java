package com.fanqielaile.toms.util;

import java.util.HashMap;
import java.util.Map;

import com.fanqielaile.toms.model.Company;

public class CompanyCache {
	private static Map<String, Company> map = new HashMap<>();
	static{
		Company company = new Company();
		company.setUserAccount("XCMS");
		company.setOtaId(936);
		company.setUserPassword("XCMS654");
		map.put(company.getOtaId()+"", company);
	}
	
	public static Company get(String key){
		return map.get(key);
	}
}

