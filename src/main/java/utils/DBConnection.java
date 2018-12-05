package utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private static DBConnection instance = null;
	private static DruidDataSource dataSource = null;

	private DBConnection() {
		// 操作Ipm数据库连接
		dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mstydb?serverTimezone=Asia/Shanghai&characterEncoding=utf-8&useSSL=false");
		dataSource.setInitialSize(5); 
		dataSource.setMinIdle(1); 
		dataSource.setMaxActive(20);
        dataSource.setValidationQuery("select 1");
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setTestWhileIdle(true);
		// 启用监控统计功能 dataSource.setFilters("stat");// for mysql
		dataSource.setPoolPreparedStatements(false);
	}

	public static synchronized DBConnection getInstance() {
		if (null == instance) {
			instance = new DBConnection();
		}
		return instance;
	}

	public DruidPooledConnection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

    public static void close(Object o){

        try {
            if(o instanceof ResultSet){
                ((ResultSet)o).close();
            }else if(o instanceof Statement){
                ((Statement)o).close();
            }else if(o instanceof Connection){
                ((Connection)o).close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
