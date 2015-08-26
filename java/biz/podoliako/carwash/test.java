package biz.podoliako.carwash;

import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.dao.UserDao;
import biz.podoliako.carwash.dao.impl.*;
import biz.podoliako.carwash.models.entity.CarBrand;
import biz.podoliako.carwash.models.entity.ServiceName;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.impl.ConnectDBTest;


import javax.naming.NamingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class test {

    public static void main(String[] args) throws SQLException, NamingException {
        Pattern p = Pattern.compile("[\\p{IsCyrillic}0-9]{0,14}");
        Matcher m = p.matcher("фыв123фъ");

        if (m.matches()){
            System.out.println("ok");
        }else {
            System.out.println("No");
        }







    }


}

