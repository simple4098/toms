package com.toms.test;

import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
public abstract class BaseUnitTest {
	protected ClassPathXmlApplicationContext context;
	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("conf/spring/spring-content.xml",
				 "classpath:conf/spring/spring-security.xml",
				 "classpath:conf/spring/cxf.xml");
		init();
	}
	protected abstract void init();
	
	/**
	 * 打印结果
	 * @param obj
	 */
	protected void printlnResult(Object obj){
		SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;
		System.out.println(JSON.toJSONString(obj,feature));
	}
}
