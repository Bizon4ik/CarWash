package biz.podoliako.carwash.dao.impl;


import org.springframework.stereotype.Component;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by bizon4ik on 21.03.15.
 */
@Component
public class ConnectDB  {
    private final String LOGIN = "carwash_core";
    private final String PASSWORD = "5700876";

    public static Connection getOneConnection() throws SQLException {
        Properties conProperties = new ConnectDB().getProperties();

        Connection con =
                DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/CarWash?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8", conProperties);
        return con;
    }

    private Properties getProperties() {
        Properties conProperties = new Properties();
        conProperties.setProperty("user", LOGIN);
        conProperties.setProperty("password", PASSWORD);
        return conProperties;
    }

    public static Connection getPoolConnection () throws NamingException, SQLException {
        InitialContext initContext= new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/CarWash");
        return ds.getConnection();
    }

}
