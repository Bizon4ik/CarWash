package biz.podoliako.carwash.services;

import biz.podoliako.carwash.models.entity.CarWashService;
import biz.podoliako.carwash.models.entity.ServiceName;
import biz.podoliako.carwash.models.pojo.CarWashAddServiceForm;
import biz.podoliako.carwash.models.pojo.ServiceFormError;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ServiceService {

    void addServiceName(ServiceName serviceName) throws SQLException;

    void addCarWashService(CarWashAddServiceForm carWashAddServiceForm, Integer ownerId) throws SQLException;

    void deleteServiceName(String id) throws SQLException;

    void modifyServiceName(Integer id, String newName);

    List<ServiceName> selectAllServiceName(Integer ownerId) throws SQLException;

    ServiceFormError validateCarWashService(CarWashAddServiceForm form) throws SQLException;

    Map<String,List<CarWashService>> selectAllCarWashServices(String carWashId, Integer carWashOwnerId) throws SQLException;

    void deleteCarWashService(String[] listCarWashServiceNameId, Integer carWashOwnerId) throws SQLException;
}
