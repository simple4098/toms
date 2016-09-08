package com.fanqielaile.toms.util;

import java.util.HashMap;
import java.util.Map;

import com.fanqielaile.toms.model.Company;

public class CompanyCache {
	private static Map<String, Company> map = new HashMap<>();
	static{
		Company company = new Company();
		company.setUserAccount("minSuTest");
		company.setOtaId(111);
		company.setUserPassword("123456");
		map.put(company.getOtaId()+"", company);
	}
	
	public static Company get(String key){
		return map.get(key);
	}
}

