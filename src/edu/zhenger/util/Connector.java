/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.util;

import java.sql.*;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/16
 */
public final class Connector
{
    private static Connector ourInstance = new Connector();

    public static Connector getInstance()
    {
        return ourInstance;
    }

    private Connector()
    {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://administrator:3306/globalgis";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "system";
        //遍历查询结果集
        try
        {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from parent";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);

            String id = null;
            while (rs.next())
            {
                //获取id这列数据
                id = rs.getString("id");

                //输出结果
                System.out.println(id + "\t" + id);
            }
            rs.close();
            con.close();
        }
        catch (ClassNotFoundException e)
        {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            //数据库连接失败异常处理
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally
        {
            System.out.println("数据库数据成功获取！！");
        }
    }
}
