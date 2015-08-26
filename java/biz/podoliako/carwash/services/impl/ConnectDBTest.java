package biz.podoliako.carwash.services.impl;


import biz.podoliako.carwash.services.ConnectionDB;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectDBTest implements ConnectionDB{
    private final String LOGIN = "carwash_core";
    private final String PASSWORD = "5700876";

    private Properties getProperties() {
        Properties conProperties = new Properties();
        conProperties.setProperty("user", LOGIN);
        conProperties.setProperty("password", PASSWORD);
        return conProperties;
    }

    @Override
    public Connection getConnection() throws NamingException, SQLException {
        Properties conProperties = getProperties();

        return  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/CarWash?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", conProperties);

    }
}
