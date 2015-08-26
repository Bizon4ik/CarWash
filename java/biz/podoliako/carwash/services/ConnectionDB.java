package biz.podoliako.carwash.services;


import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionDB {
    public Connection getConnection() throws NamingException, SQLException;

}
