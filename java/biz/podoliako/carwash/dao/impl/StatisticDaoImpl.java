package biz.podoliako.carwash.dao.impl;


import biz.podoliako.carwash.dao.OrderDao;
import biz.podoliako.carwash.dao.ServiceDao;
import biz.podoliako.carwash.dao.StatisticDao;
import biz.podoliako.carwash.models.PaymentMethod;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
import biz.podoliako.carwash.view.GeneralStatInBox;
import biz.podoliako.carwash.view.statistic.OrderForDetailStatisticInBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("StatisticDao")
public class StatisticDaoImpl implements StatisticDao{

    private ConnectionDB connectionDB;

    @Autowired
    public StatisticDaoImpl(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;

    }


    @Override
    public GeneralStatInBox selectGeneralStatisticForBox(Date from, Date to, Integer washId, int box) {
        Connection connection = null;
        PreparedStatement ps = null;
        GeneralStatInBox generalStatInBox = new GeneralStatInBox();

        try {
            connection = connectionDB.getConnection();

            String query = "select count(DISTINCT o.id) as id_amount, " +
                                "sum(c.price*ordered.quantity+addition_price) as total, " +
                                "sum(case " +
                                        "when o.payment_method = 'Cash' " +
                                        "then c.price*ordered.quantity+addition_price " +
                                        "else 0 " +
                                    "end" +
                                ") as cash, " +
                                "sum(case " +
                                        "when o.payment_method <> 'Cash' " +
                                        "then c.price*ordered.quantity+addition_price " +
                                        "else 0 " +
                                        "end" +
                                    ") as beznal " +


                           "from orders as o " +
                           "join ordered_services as ordered on o.id=ordered.order_id " +
                           "join car_wash_services as c on ordered.car_wash_services_id = c.id " +
                           "where o.car_wash_id =? and box=? and date_of_close IS NOT NULL " +
                           "AND o.date_of_creation between ? and ? ";

            ps = connection.prepareStatement(query);

            ps.setInt(1, washId);
            ps.setInt(2, box);
            ps.setObject(3, from);
            ps.setObject(4, to);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                generalStatInBox.setBox(box);
                generalStatInBox.setFrom(from);
                generalStatInBox.setTo(to);

                generalStatInBox.setOrderAmount(rs.getInt("id_amount"));

                generalStatInBox.setMoneyTotal(rs.getBigDecimal("total"));
                generalStatInBox.setMoneyInCash(rs.getBigDecimal("cash"));
                generalStatInBox.setMoneyBeznal(rs.getBigDecimal("beznal"));
            }

            return generalStatInBox;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectGeneralStatisticForBox (StatisticDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectGeneralStatisticForBox (StatisticDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectGeneralStatisticForBox (StatisticDaoImpl) " + e);
            }
        }
    }

    @Override
    public List<OrderForDetailStatisticInBox> selectDetailStatisticForBox(Integer washId, Integer boxNumber, Date from, Date to) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<OrderForDetailStatisticInBox> detailStatInBox = new ArrayList<>();
        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                                " o.id as id, " +
                                " o.car_wash_id as car_wash_id," +
                                " o.box as box," +
                                " o.car_id as car_id, " +
                                " o.payment_method as payment_method, " +
                                " o.date_of_creation as date_of_creation, " +
                                " o.date_of_close as date_of_close, " +
                                " o.date_of_delete as date_of_delete, " +
                                " o.created_by_id as created_by_id, " +
                                " o.close_by_id as close_by_id, " +
                                " o.delete_by_id  as delete_by_id," +
                                " (SELECT SUM(salary) FROM washer_man_in_order as w WHERE w.order_id = o.id) as salary , " +
                                " (SELECT SUM(cws.price*os.quantity+os.addition_price)  " +
                                  "FROM ordered_services as os " +
                                  "JOIN car_wash_services as cws ON os.car_wash_services_id = cws.id " +
                                  "WHERE os.order_id = o.id) as order_cost "+
                    "FROM " + OrderDao.ORDER_TABLE + " as o " +
                    " WHERE o.car_wash_id = ? " +
                    " AND o.box = ? " +
                    " AND o.date_of_close IS NOT NULL " +
                    " AND o.date_of_creation BETWEEN ? AND ? " +
                    " GROUP BY o.id";

            ps = connection.prepareStatement(query);

            ps.setInt(1, washId);
            ps.setInt(2, boxNumber);
            ps.setObject(3, from);
            ps.setObject(4, to);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                OrderForDetailStatisticInBox order = new OrderForDetailStatisticInBox();
                order.setId(rs.getInt("id"));
                order.setCarWashId(rs.getInt("car_wash_id"));
                order.setBox(rs.getInt("box"));
                order.setCarId(rs.getInt("car_id"));
                order.setPaymentMethod(PaymentMethod.valueOf(rs.getString("payment_method")));
                order.setDateOfCreation(rs.getTimestamp("date_of_creation"));
                order.setDateOfClose(rs.getTimestamp("date_of_close"));
                order.setDateOfDelete(rs.getTimestamp("date_of_delete"));
                order.setCreatedById(rs.getInt("created_by_id"));
                order.setCloseById(rs.getInt("close_by_id"));
                order.setDeleteById(rs.getInt("delete_by_id"));
                order.setSalary(rs.getBigDecimal("salary"));
                order.setTotaCostOfOrder(rs.getBigDecimal("order_cost"));

                detailStatInBox.add(order);
            }

            return detailStatInBox;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectDetailStatisticForBox (StatisticDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectDetailStatisticForBox (StatisticDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectDetailStatisticForBox (StatisticDaoImpl) " + e);
            }
        }
    }

    @Override
    public BigDecimal selectGeneralSalaryForBox(Date from, Date to, Integer washId, int box) {
        Connection connection = null;
        PreparedStatement ps = null;
        BigDecimal salary = null;

        try {
            connection = connectionDB.getConnection();

            String query = "select " +
                    "sum(w.salary) as salary " +

                    "from orders as o " +

                    "join washer_man_in_order as w on o.id = w.order_id " +
                    "where o.car_wash_id =? and box=? and date_of_close IS NOT NULL " +
                    "AND o.date_of_creation between ? and ? ";

            ps = connection.prepareStatement(query);

            ps.setInt(1, washId);
            ps.setInt(2, box);
            ps.setObject(3, from);
            ps.setObject(4, to);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                salary = rs.getBigDecimal("salary");
            }

            return salary;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectGeneralSalaryForBox (StatisticDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectGeneralSalaryForBox (StatisticDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectGeneralSalaryForBox (StatisticDaoImpl) " + e);
            }
        }
    }
}
