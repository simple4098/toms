package com.fanqielaile.toms.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fanqielaile.toms.util.PassWordUtil;

public class MinsuRoomInfoServiceTest {

	@Test
	public void testFetchRoom() {
		fail("Not yet implemented");
	}
	public static void main(String[] args) {
		Long time =System.currentTimeMillis();
		System.out.println(time);
		String key ="111"+time+"minSuTest"+"123456";
		String signature = PassWordUtil.getMd5Pwd(key);
		System.out.println(signature);
	}

}

