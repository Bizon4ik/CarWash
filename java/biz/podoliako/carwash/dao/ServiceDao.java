package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.models.entity.CarWashService;
import biz.podoliako.carwash.models.entity.ServiceName;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public interface ServiceDao {
    public final static Integer NAME_MAX_LENGTH = 300;

    void addServiceName(ServiceName serviceName) throws SQLException;

    void deleteServiceName (Integer id) throws SQLException;

    void modifyServiceName (Integer id, ServiceName name);

    List<ServiceName> selectAllServiceName(Integer ownerId) throws SQLException;

    boolean isServiceNameExist(String name) throws SQLException;

    void addCarWashService(CarWashService service)throws SQLException;


    boolean isServiceNameInCarWashExist(Integer carWashId, Integer categoryId, Integer serviceNameId) throws SQLException;

    List<CarWashService> selectAllCarWashServiceByCategory(Integer categoryId, Integer carWashIdInt, Integer carWashOwnerId) throws SQLException;

    void deleteCarWashService(Date dateOfDelete, Integer serviceId, Integer carWashOwnerId) throws SQLException;
}
