package com.itheima.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

public class jdbcTest {
	@Test
public void testJdbc() throws Exception{
	Class.forName("com.mysql.jdbc.Driver");
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql1", "root", "123");
	String sql="select * from user";
	PreparedStatement prepareStatement = con.prepareStatement(sql);
	ResultSet rs = prepareStatement.executeQuery();
	while(rs.next()){
		System.out.println(rs.getString(1));
		System.out.println(rs.getString(2));
	}
	rs.close();
	prepareStatement.close();
	con.close();
	}
}
