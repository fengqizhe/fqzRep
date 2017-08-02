package com.itheima.mybatis;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class mybatisTest {
	
@Test
public void testGetUserById() throws Exception{
	SqlSessionFactoryBuilder fb = new SqlSessionFactoryBuilder();
	SqlSessionFactory sf = fb.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
	SqlSession session = sf.openSession();
	Object one = session.selectOne("getUserByName", "��");
	
	System.out.println(one);
	// 9���ر�����
	session.close();
}

}
