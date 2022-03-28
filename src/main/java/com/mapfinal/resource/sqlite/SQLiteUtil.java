package com.mapfinal.resource.sqlite;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.mapfinal.kit.StringKit;

public class SQLiteUtil {
	
	private static Connection connection = null;
	private static Statement statement = null;

	/**
	 * @describe 设置连接
	 * @param path sqlite文件地址
	 * @param username
	 * @param password
	 */
	public static void setConnection(String path, String username, String password) {
		try {
			// 声明驱动类型
			Class.forName("org.sqlite.JDBC");
			// 设置 sqlite文件路径，等同于mysql连接地址(jdbc:mysql://127.0.0.1:3306/test)
			String url = "jdbc:sqlite:" + path;
			// 获取连接
			if(StringKit.isBlank(username) || StringKit.isBlank(password)) {
				connection = DriverManager.getConnection(url);
			} else {
				connection = DriverManager.getConnection(url, username, password);
			}
			// 声明
			statement = connection.createStatement();
		} catch (Exception e) {
			throw new RuntimeException("建立Sqlite连接失败");
		}
	}

	/**
	 * @describe: 创建表
	 * @params: tableName: 要创建的表的名称 className：项目中Pojo的类名(需要注意的是该类名需要加上包名 如
	 *          com.xxx.xxx.pojo.xxx)
	 */
	public synchronized static void create(String sql) {
		try {
			statement.executeUpdate(sql);
		} catch (Exception e) {
			throw new RuntimeException("sql执行失败");
		}
	}

	/**
	 * @describe: 关闭链接
	 */
	public static void endConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
