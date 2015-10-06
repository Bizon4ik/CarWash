package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.OrderDao;
import biz.podoliako.carwash.dao.ServiceDao;
import biz.podoliako.carwash.models.PaymentMethod;
import biz.podoliako.carwash.models.entity.Order;
import biz.podoliako.carwash.models.entity.WasherManInBox;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.entity.ServiceInOrder;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
import biz.podoliako.carwash.view.OrderedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Date;

@Component("OrderDao")
public class OrderDaoImpl implements OrderDao{

    private ConnectionDB connectionDB;

    @Autowired
    public OrderDaoImpl(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }


    @Override
    public List<Integer> selectAvailableBox(Integer carWashId) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<Integer> availableBoxes = new ArrayList<>();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT DISTINCT box_number " +
                           "FROM " + UserDaoImpl.WASHER_MAN_IN_BOX_TABLE +
                           " WHERE finish IS NULL " +
                           "AND car_wash_id = ? " +
                           " AND box_number NOT IN " +
                                                "(SELECT DISTINCT box " +
                                                 "FROM " + OrderDaoImpl.ORDER_TABLE +
                                                 " WHERE car_wash_id = ? " +
                                                 " AND date_of_close IS NULL " +
                                                 " AND date_of_delete IS NULL " +
                                                 ")" +
                           "ORDER BY box_number";

            ps = connection.prepareStatement(query);
            ps.setInt(1, carWashId);
            ps.setInt(2, carWashId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                availableBoxes.add(rs.getInt("box_number"));
            }

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectAvailableBox (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectAvailableBox (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectAvailableBox (OrderDaoImpl) " + e);
            }
        }

        return availableBoxes;
    }

    @Override
    public Integer insertOrder(Order order) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

             String query = "INSERT INTO " + ORDER_TABLE +
                          " (car_wash_id, box, car_id, date_of_creation, date_of_close, date_of_delete, created_by_id, close_by_id, delete_by_id, payment_method) " +
                          "VALUES " +
                          "(      ?,       ?,     ?,         ?,              ?,              ?,             ?,              ?,            ?,          ?       )";

            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, order.getCarWashId());
            ps.setInt(2, order.getBox());
            ps.setInt(3, order.getCarId());
            ps.setObject(4, order.getDateOfCreation());
            ps.setObject(5, order.getDateOfClose());
            ps.setObject(6, order.getDateOfDelete());
            ps.setInt(7, order.getCreatedById());
            ps.setObject(8, order.getDateOfClose());
            ps.setObject(9, order.getDateOfDelete());
            ps.setString(10, order.getPaymentMethod().toString());

            ps.execute();

            ResultSet generatedKey = ps.getGeneratedKeys();
            if (generatedKey.next()) {
                return (int) generatedKey.getLong(1);
            }
            else {
                throw new SQLException("Creating car failed, can not obtain added car ID");
            }

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе insertOrder (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе insertOrder (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе insertOrder (OrderDaoImpl) " + e);
            }
        }

    }

    @Override
    public void insetOrderedService(ServiceInOrder serviceInOrder, Integer orderId) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

            String query = "INSERT INTO " + ORDERED_SERVICES_TABLE +
                           " (order_id, car_wash_services_id, quantity, addition_price) " +
                           "VALUES " +
                           " (?,                 ?,              ?,         ?)";

            ps = connection.prepareStatement(query);

            ps.setInt(1, orderId);
            ps.setInt(2, serviceInOrder.getCarWashServiceId());
            ps.setInt(3, serviceInOrder.getCount());
            ps.setObject(4, serviceInOrder.getAdditionPrice());

            ps.execute();

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе insetOrderedService (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе insetOrderedService (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе insetOrderedService (OrderDaoImpl) " + e);
            }
        }

    }

    @Override
    public List<ServiceInOrder> selectAllServiceInOrder(Integer orderId) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<ServiceInOrder> serviceInOrderList = new ArrayList<>();

        try {
            connection = connectionDB.getConnection();


            String query = "SELECT order_id, car_wash_services_id, quantity, addition_price " +
                           "FROM " + ORDERED_SERVICES_TABLE +
                           " WHERE order_id = ?";

            ps = connection.prepareStatement(query);

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                ServiceInOrder serviceInOrder = new ServiceInOrder();
                serviceInOrder.setOrderId(rs.getInt("order_id"));
                serviceInOrder.setCarWashServiceId(rs.getInt("car_wash_services_id"));
                serviceInOrder.setCount(rs.getInt("quantity"));
                serviceInOrder.setAdditionPrice(rs.getInt("addition_price"));
                serviceInOrderList.add(serviceInOrder);

            }

            return serviceInOrderList;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectAllServiceInOrder (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectAllServiceInOrder (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectAllServiceInOrder (OrderDaoImpl) " + e);
            }
        }
    }

    @Override
    public void insertWasherManInOrder(WasherManInBox washerManInBox, Integer orderId) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

            String query = "INSERT INTO " + WASHER_MAN_IN_ORDER_TABLE +
                    " (order_id, user_id) " +
                    "VALUES " +
                    " (?,           ?)";

            ps = connection.prepareStatement(query);

            ps.setInt(1, orderId);
            ps.setInt(2, washerManInBox.getUserId());

            ps.execute();

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе insertWasherManInOrder (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе insertWasherManInOrder (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе insertWasherManInOrder (OrderDaoImpl) " + e);
            }
        }

    }

    @Override
    public Order selectOrder(Integer carWashId, Integer box) {
        Connection connection = null;
        PreparedStatement ps = null;
        Order order = new Order();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                                 "  id, car_wash_id, box, car_id, payment_method, date_of_creation, " +
                                 "  date_of_close, date_of_delete, created_by_id, close_by_id, delete_by_id  " +
                           "FROM " + ORDER_TABLE +
                           " WHERE car_wash_id = ? " +
                           " AND box = ? " +
                           " AND date_of_close IS NULL " +
                           " AND date_of_delete IS NULL ";

            ps = connection.prepareStatement(query);

            ps.setInt(1, carWashId);
            ps.setInt(2, box);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
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

            }

            return order;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectOrder (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectOrder (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectOrder (OrderDaoImpl) " + e);
            }
        }

    }

    @Override
    public List<OrderedService> selectOrderedService(Integer orderId) {
        Connection connection = null;
        PreparedStatement ps = null;
        List<OrderedService> orderedServices = new ArrayList<>();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT sn.name as sn_name, cs.price as cs_price, os.quantity as os_quantity, os.addition_price as os_addition_price " +
                           "FROM " + ORDERED_SERVICES_TABLE + " as os " +
                           " JOIN " + ServiceDao.CAR_WASH_SERVICE_NAME_TABLE + " as cs" +
                                    " ON os.car_wash_services_id = cs.id " +
                           " JOIN " + ServiceDao.SERVICE_NAME_TABLE + " as sn" +
                                    " ON cs.service_name_id = sn.id " +
                           "WHERE  order_id = ?";

            ps = connection.prepareStatement(query);

            ps.setInt(1, orderId);


            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                OrderedService orderedService = new OrderedService();
                orderedService.setName(rs.getString("sn_name"));
                orderedService.setPrice(rs.getBigDecimal("cs_price"));
                orderedService.setCount(rs.getInt("os_quantity"));
                orderedService.setAdditionPrice(rs.getBigDecimal("os_addition_price"));
                orderedServices.add(orderedService);
            }

            return orderedServices;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectOrderedService (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectOrderedService (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectOrderedService (OrderDaoImpl) " + e);
            }
        }
    }

    @Override
    public Order selectOrderById(Integer id) {
        Connection connection = null;
        PreparedStatement ps = null;
        Order order = new Order();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                    "  id, car_wash_id, box, car_id, payment_method, date_of_creation, " +
                    "  date_of_close, date_of_delete, created_by_id, close_by_id, delete_by_id  " +
                    "FROM " + ORDER_TABLE +
                    " WHERE id = ? ";

            ps = connection.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
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

            }

            return order;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectOrderById (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectOrderById (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectOrderById (OrderDaoImpl) " + e);
            }
        }
    }

    @Override
    public void updateBoxInOrder(Integer orderId, Integer boxNumber) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

            String query = "UPDATE " + ORDER_TABLE +
                           " SET box = ? " +
                           "WHERE id=?";

            ps = connection.prepareStatement(query);

            ps.setInt(1, boxNumber);
            ps.setInt(2, orderId);

            ps.execute();


        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе updateBoxInOrder (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе updateBoxInOrder (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе updateBoxInOrder (OrderDaoImpl) " + e);
            }
        }

    }

    @Override
    public void deleteServicesInOrder(Integer orderId) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

            String query = "DELETE  FROM " + ORDERED_SERVICES_TABLE +
                           " WHERE order_id=?";

            ps = connection.prepareStatement(query);

            ps.setInt(1, orderId);

            ps.execute();


        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе deleteServicesInOrder (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе deleteServicesInOrder (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе deleteServicesInOrder (OrderDaoImpl) " + e);
            }
        }
    }

    @Override
    public void closeOrder(Integer id, Integer carWashId, Integer userId, java.util.Date dateOfClose) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

            String query = "UPDATE " + ORDER_TABLE + " SET date_of_close = ?, close_by_id = ? WHERE id = ? AND car_wash_id = ?";

            ps = connection.prepareStatement(query);

            ps.setObject(1, dateOfClose);
            ps.setInt(2, userId);
            ps.setInt(3, id);
            ps.setInt(4, carWashId);

            ps.execute();


        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе closeOrder (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе closeOrder (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе closeOrder (OrderDaoImpl) " + e);
            }
        }

    }

    @Override
    public void deleteOrder(Integer id, Integer carWashId, Integer userID, Date dateOfDelete) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();

            String query = "UPDATE " + ORDER_TABLE + " SET date_of_delete = ?, delete_by_id = ?  WHERE id = ? AND car_wash_id = ?";

            ps = connection.prepareStatement(query);

            ps.setObject(1, dateOfDelete);
            ps.setInt(2, userID);
            ps.setInt(3, id);
            ps.setInt(4, carWashId);

            ps.execute();


        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе deleteOrder (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе deleteOrder (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            } catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе deleteOrder (OrderDaoImpl) " + e);
            }
        }
    }

    @Override
    public Boolean isOrderOpen(Integer id) {
        Connection connection = null;
        PreparedStatement ps = null;
        Boolean result = new Boolean(false);

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT * " +
                    "FROM " + ORDER_TABLE +
                    " WHERE id = ? " +
                    " AND date_of_delete IS NULL  " +
                    " AND date_of_close IS NULL ";

            ps = connection.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                result = false;

            }

            return result;

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе isOrderOpen (OrderDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе isOrderOpen (OrderDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе isOrderOpen (OrderDaoImpl) " + e);
            }
        }
    }
}
