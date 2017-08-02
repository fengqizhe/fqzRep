package com.itheima.lucene;

import org.junit.Test;

public class staticTest {
public static  int staticVar=0;
public int instanceVar=0;
@Test
public void varianTest(){

	instanceVar++;
	staticTest s1 = new staticTest();
	System.out.println(s1.instanceVar+"    "+instanceVar);
}
}
