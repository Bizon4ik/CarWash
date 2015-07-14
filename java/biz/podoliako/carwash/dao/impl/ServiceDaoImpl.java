package biz.podoliako.carwash.dao.impl;


import biz.podoliako.carwash.dao.ServiceDao;
import biz.podoliako.carwash.dao.pojo.CarWash;
import biz.podoliako.carwash.dao.pojo.CarWashService;
import biz.podoliako.carwash.dao.pojo.Category;
import biz.podoliako.carwash.dao.pojo.ServiceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Component("ServiceDao")
@Scope(BeanDefinition.SCOPE_SINGLETON )
public class ServiceDaoImpl implements ServiceDao{
    public final static String SERVICE_NAME_TABLE = "service_names";
    public final static String CAR_WASH_SERVICE_NAME_TABLE = "car_wash_services";

    private Connection connection = null;

    @Autowired
    public ServiceDaoImpl(ConnectDB connectDB) throws SQLException, NamingException {
        this.connection = connectDB.getPoolConnection();
    }

    public ServiceDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void addServiceName(ServiceName serviceName) throws SQLException {
        String query = "INSERT INTO " + SERVICE_NAME_TABLE + " " +
                       "(name, date_of_creation, owner_id) VALUES " +
                       "(  ?,           ?,          ?    )";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, serviceName.getName());
        ps.setObject(2, serviceName.getDateOfCreation());
        ps.setInt(3, serviceName.getOwnerId());

        ps.execute();

    }

    @Override
    public void deleteServiceName(Integer id) throws SQLException {
        String query = "DELETE FROM " + SERVICE_NAME_TABLE + " WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.execute();

    }

    @Override
    public void modifyServiceName(Integer id, ServiceName name) {

    }

    @Override
    public List<ServiceName> selectAllServiceName(Integer ownerId) throws SQLException {
        List<ServiceName> serviceNameList = new ArrayList<ServiceName>();

        String query = "SELECT * FROM " + SERVICE_NAME_TABLE + " " +
                       "WHERE owner_id = " + ownerId + " AND  date_of_delete IS NULL ORDER BY name";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            ServiceName serviceName = new ServiceName();
            serviceName.setId(rs.getInt("id"));
            serviceName.setName(rs.getString("name"));
            serviceName.setDateOfCreation(rs.getDate("date_of_creation"));
            serviceName.setDateOfDelete(rs.getDate("date_of_delete"));
            serviceName.setOwnerId(rs.getInt("owner_id"));

            serviceNameList.add(serviceName);
        }

        return serviceNameList;
    }

    @Override
    public boolean isServiceNameExist(String name) throws SQLException {
        String query = "SELECT * FROM " + SERVICE_NAME_TABLE + " WHERE name = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    @Override
    public void addCarWashService(CarWashService service) throws SQLException {
        String query = "INSERT INTO " + CAR_WASH_SERVICE_NAME_TABLE +
                       "(car_wash_id, category_id, service_name_id, price, commision_day, commision_night, date_of_creation, date_of_delete, owner_id) VALUES " +
                       "(    ?,             ?,            ?,          ?,        ?,               ?,                ?,               ?,           ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, service.getCarWash().getId());
        ps.setInt(2, service.getCategory().getId());
        ps.setInt(3, service.getServiceName().getId());
        ps.setBigDecimal(4, service.getPrice());
        ps.setInt(5, service.getCommisionDay());
        ps.setInt(6, service.getCommisionNight());
        ps.setObject(7, service.getDateOfCreation());
        ps.setObject(8, service.getDateOfDelate());
        ps.setInt(9, service.getOwnerId());

        ps.execute();
    }

    @Override
    public boolean isServiceNameInCarWashExist(Integer carWashId, Integer categoryId, Integer serviceNameId) throws SQLException {
        String query = "SELECT * FROM " + CAR_WASH_SERVICE_NAME_TABLE + " WHERE car_wash_id = ? AND category_id = ? AND service_name_id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, carWashId);
        ps.setInt(2, categoryId);
        ps.setInt(3, serviceNameId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) return true;

        return false;
    }

    @Override
    public List<CarWashService> selectAllCarWashServiceByCategory(Integer categoryId, Integer carWashIdInt, Integer carWashOwnerId) throws SQLException {
        List<CarWashService> carWashServiceList = new ArrayList<>();

        String query = "SELECT " +
                               "cws.id as cwsId, " +
                                    "cw.id as cwId, " +
                                    "cw.name as cwName, " +
                                    "cw.address as cwAddress, " +
                                    "cw.box_amount as cwBoxAmount, " +
                                    "cw.phone_number as cwPhoneNumber, " +
                                    "cw.date_of_creation as cwDateOfCreation, " +
                                    "cw.owner_id as cwOwnerId, " +
                                    "cw.start_shift as cwStartShift, " +
                                    "cw.finish_shift as cwFinishShift, " +
                "" +
                                    "ca.id as caId, " +
                                    "ca.name as caName, " +
                                    "ca.description as caDescription, " +
                                    "ca.date_of_creation as caDateOfCreation, " +
                                    "ca.owner_id as caOwnerId, " +
                " " +
                                    "s.id as sId, " +
                                    "s.name as sName, " +
                                    "s.date_of_creation as sDateOfCreation, " +
                                    "s.date_of_delete as sdate_of_delete, " +
                                    "s.owner_id as sowner_id, " +
                "" +
                               "cws.price as cwsprice, " +
                               "cws.commision_day as cwscommision_day, " +
                               "cws.commision_night as cwscommision_night, " +
                               "cws.date_of_creation as cwsdate_of_creation, " +
                               "cws.date_of_delete as cwsdate_of_delete, " +
                               "cws.owner_id as cwsowner_id " +

                "       FROM " + CAR_WASH_SERVICE_NAME_TABLE + " AS cws " +
                                "JOIN " + CarWashDaoImpl.CAR_WASH_TABLE + " AS cw  ON cws.car_wash_id = cw.id " +
                                "JOIN " + CategoryDaoImpl.CATEGORY_TABLE_NAME + " AS ca ON cws.category_id = ca.id " +
                                "JOIN " + SERVICE_NAME_TABLE + " AS s ON cws.service_name_id = s.id " +
                       "WHERE cws.category_id = ? AND " +
                              "cws.car_wash_id = ? AND " +
                             "cws.owner_id = ?  AND " +
                            "cws.date_of_delete IS NULL " +
                       "ORDER BY s.name";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, categoryId);
        ps.setInt(2, carWashIdInt);
        ps.setInt(3, carWashOwnerId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            CarWashService carWashService = new CarWashService();
            carWashService.setId(rs.getInt("cwsId"));
                CarWash carWash = new CarWash();
                carWash.setId(rs.getInt("cwId"));
                carWash.setName(rs.getString("cwName"));
                carWash.setAddress(rs.getString("cwAddress"));
                carWash.setBoxAmount(rs.getInt("cwBoxAmount"));
                carWash.setPhoneNumber(rs.getString("cwPhoneNumber"));
                carWash.setDateOfCreation(rs.getTimestamp("cwDateOfCreation"));
                carWash.setOwnerId(rs.getInt("cwOwnerId"));
                carWash.setStartDayShift(rs.getTime("cwStartShift"));
                carWash.setFinishDayShift(rs.getTime("cwFinishShift"));
            carWashService.setCarWash(carWash);
                Category category = new Category();
                category.setId(rs.getInt("caId"));
                category.setName(rs.getString("caName"));
                category.setDescription(rs.getString("caDescription"));
                category.setDateOfCreation(rs.getTimestamp("caDateOfCreation"));
                category.setOwnerId(rs.getInt("caOwnerId"));
            carWashService.setCategory(category);
                ServiceName serviceName = new ServiceName();
                serviceName.setId(rs.getInt("sId"));
                serviceName.setName(rs.getString("sName"));
                serviceName.setDateOfCreation(rs.getDate("sDateOfCreation"));
                serviceName.setDateOfDelete(rs.getDate("sdate_of_delete"));
                serviceName.setOwnerId(rs.getInt("sowner_id"));
            carWashService.setServiceName(serviceName);
            carWashService.setPrice(rs.getBigDecimal("cwsprice"));
            carWashService.setCommisionDay(rs.getInt("cwscommision_day"));
            carWashService.setCommisionNight(rs.getInt("cwscommision_night"));
            carWashService.setDateOfCreation(rs.getTimestamp("cwsdate_of_creation"));
            carWashService.setDateOfDelate(rs.getTimestamp("cwsdate_of_delete"));
            carWashService.setOwnerId(rs.getInt("cwsowner_id"));

            carWashServiceList.add(carWashService);
        }

        return carWashServiceList;
    }

    @Override
    public void deleteCarWashService(java.util.Date dateOfDelete, Integer serviceId, Integer carWashOwnerId) throws SQLException {
        String query = "UPDATE " + CAR_WASH_SERVICE_NAME_TABLE + " SET date_of_delete = ? WHERE id = ? AND owner_id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setObject(1, dateOfDelete);
        ps.setInt(2, serviceId);
        ps.setInt(3, carWashOwnerId);

        ps.execute();

    }


}
