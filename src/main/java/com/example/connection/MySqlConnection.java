package com.example.connection;

import java.sql.*;
import java.util.List;

public class MySqlConnection {

    private String driver="com.mysql.cj.jdbc.Driver";
    private String url="jdbc:mysql://0.0.0.0:33060/watcher?autoReconnect=true&useSSL=false&user=root&password=secret";
    private Connection conn;

    public MySqlConnection(){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet execute(String query) {
        ResultSet rs = null;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
             rs = statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet execute(String query, List<String> params) {
        ResultSet rs = null;
        try {
            PreparedStatement statement = conn.prepareStatement(query);

            params.forEach(p -> {
                try {
                    statement.setString(params.indexOf(p)+1, p);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            rs = statement.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String query, List<String> params) {
        int rs = 0;
        try {
            PreparedStatement statement = conn.prepareStatement(query);

            params.forEach(p -> {
                try {
                    statement.setString(params.indexOf(p)+1, p);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            rs = statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }

}
