package com.fanqielaile.toms.service.impl;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanqielaile.toms.util.PassWordUtil;

public class SignaatureTest {
	public static void main(String[] args) {
		Long time =System.currentTimeMillis();
		System.out.println(time);
		String key ="936"+time+"XCMS"+"XCMS654";
		String signature = PassWordUtil.getMd5Pwd(key);
		System.out.println(signature);
	}
	@Test
	public void testJson(){
		A a = new A();
		System.out.println(JSON.toJSON(a));
		System.out.println(JSONObject.toJSONString(a,false));
		System.out.println(JSONObject.toJSONString(a,true));
	}
	@Test
	public void testB(){
		int i =2000000;
		a(i);
		System.out.println(i);
	}
	void a(Integer i){
		i=300000000;
	}
	class A {
		int a = 3;
		String b ="23";
		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
		public String getB() {
			return b;
		}
		public void setB(String b) {
			this.b = b;
		}
		
	}
}

