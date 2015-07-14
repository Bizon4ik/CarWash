package biz.podoliako.carwash.models.impl;

import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.dao.ServiceDao;
import biz.podoliako.carwash.dao.pojo.CarWash;
import biz.podoliako.carwash.dao.pojo.CarWashService;
import biz.podoliako.carwash.dao.pojo.Category;
import biz.podoliako.carwash.dao.pojo.ServiceName;
import biz.podoliako.carwash.models.ServiceModel;
import biz.podoliako.carwash.models.pojo.CarWashAddServiceForm;
import biz.podoliako.carwash.models.pojo.ServiceFormError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.Service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;


@Component("ServiceModel")
public class ServiceModelImpl implements ServiceModel {

    private DaoFactory daoFactory;

    @Autowired
    public ServiceModelImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addServiceName(ServiceName serviceName) throws SQLException {
        ServiceName newServiceName = new ServiceName();
        newServiceName.setName(serviceName.getName().trim().toLowerCase());
        newServiceName.setOwnerId(serviceName.getOwnerId());
        newServiceName.setDateOfCreation(new Date());

        daoFactory.getServiceDao().addServiceName(newServiceName);

    }

    @Override
    public void addCarWashService(CarWashAddServiceForm form, Integer ownerId) throws SQLException {
        CarWash carWash = new CarWash();
        carWash.setId(Integer.valueOf(form.getCarwashid()));
        Category category = new Category();
        category.setId(Integer.valueOf(form.getCategoryid()));

        ArrayList<CarWashService> carWashServicesList = new ArrayList<CarWashService>();

        for (int i=0; i<form.getPriceList().length; i++){
            CarWashService carWashService = new CarWashService();
            carWashService.setCarWash(carWash);
            carWashService.setCategory(category);

            ServiceName serviceName = new ServiceName();
            serviceName.setId(Integer.valueOf(form.getServiceNameIdList()[i]));

            carWashService.setServiceName(serviceName);
            carWashService.setPrice(new BigDecimal(form.getPriceList()[i]));
            carWashService.setCommisionDay(Integer.valueOf(form.getDayCommissionList()[i]));
            carWashService.setCommisionNight(Integer.valueOf(form.getNightCommissionList()[i]));
            carWashService.setDateOfCreation(new Date());
            carWashService.setDateOfDelate(null);
            carWashService.setOwnerId(ownerId);

            carWashServicesList.add(carWashService);
        }

        putCarWashServiceListIntoDB(carWashServicesList);

    }

    private void putCarWashServiceListIntoDB(ArrayList<CarWashService> carWashServicesList) throws SQLException {
            for (CarWashService service : carWashServicesList) {
                daoFactory.getServiceDao().addCarWashService(service);
            }
    }

    @Override
    public void deleteServiceName(String id) throws SQLException {
        Integer serviceNameId = Integer.valueOf(id);
        daoFactory.getServiceDao().deleteServiceName(serviceNameId);

    }

    @Override
    public void modifyServiceName(Integer id, String newName) {

    }

    @Override
    public List<ServiceName> selectAllServiceName(Integer ownerId) throws SQLException {
        return daoFactory.getServiceDao().selectAllServiceName(ownerId);
    }

    @Override
    public String validateServiceName(String name) throws SQLException {
        if (name == null || "".equals(name.trim())) return "Укажите название услуги";
        if (name.trim().length() > ServiceDao.NAME_MAX_LENGTH) return  "Название услуги слишком длинное";
        if (daoFactory.getServiceDao().isServiceNameExist(name.trim().toLowerCase())) {
            return "Услуга с таким именем уже существует";
        }

        return null;
    }

    @Override
    public ServiceFormError validateCarWashService(CarWashAddServiceForm form) throws SQLException {
        ServiceFormError serviceFormError = new ServiceFormError();
        String[] priceErrorList = validatePrice(form);
        String[] serviceNaneAndCategoryErrorList = validateServiceNameAndCategory(form);

        if (priceErrorList != null) serviceFormError.setPriceErrors(priceErrorList);
        if (serviceNaneAndCategoryErrorList != null) serviceFormError.setNameExistErros(serviceNaneAndCategoryErrorList);

        return serviceFormError;
    }

    @Override
    public Map<String, List<CarWashService>> selectAllCarWashServices(String carWashId, Integer carWashOwnerId) throws SQLException {
        Map<String, List<CarWashService>> carWashServiceList = new LinkedHashMap<>();
        Integer carWashIdInt = Integer.valueOf(carWashId);
        List<Category> categoryList = daoFactory.getCategoryDao().selectAllCategories(carWashOwnerId);

        for (Category c : categoryList) {
            List<CarWashService> carWashServices =
                    daoFactory.getServiceDao().selectAllCarWashServiceByCategory(c.getId(), carWashIdInt, carWashOwnerId);
            carWashServiceList.put(c.getName(), carWashServices);
        }

        return carWashServiceList;
    }

    @Override
    public void deleteCarWashService(String[] listCarWashServiceNameId, Integer carWashOwnerId) throws SQLException {
        Date date = new Date();
        for(String s : listCarWashServiceNameId) {
            Integer serviceId = Integer.valueOf(s);
            daoFactory.getServiceDao().deleteCarWashService(date, serviceId, carWashOwnerId);
        }

    }

    private String[] validateServiceNameAndCategory(CarWashAddServiceForm form) throws SQLException {
        String serviceIsExistInThisCategory = "Услуга уже существует в данной категории";
        String sameName = "Вы пытаетесь добавить эту услугу несколько раз";

        Integer categoryId = Integer.valueOf(form.getCategoryid());
        Integer carWashId = Integer.valueOf(form.getCarwashid());
        String[] serviceNameId = new String[form.getServiceNameIdList().length];
        System.arraycopy(form.getServiceNameIdList(), 0, serviceNameId, 0, form.getServiceNameIdList().length );

        String[] serviceNameErrors = new String[serviceNameId.length];
        for (int i = 0; i < serviceNameId.length; i++) {
            String nameId = serviceNameId[i];
            serviceNameId[i] = null;
            if (Arrays.asList(serviceNameId).contains(nameId)) {
                serviceNameErrors[i] = sameName;
            }else if(daoFactory.getServiceDao().isServiceNameInCarWashExist(carWashId, categoryId, Integer.valueOf(nameId))) {
                    serviceNameErrors[i] = serviceIsExistInThisCategory;
            }

        }

        if (Arrays.asList(serviceNameErrors).contains(serviceIsExistInThisCategory)) return  serviceNameErrors;
        return null;
    }

    private String[] validatePrice(CarWashAddServiceForm form){
        String priceIsEmpty = "Укажите цену";
        String incorrectPrice = "Неправильный формат цены";


        String[] priceList = form.getPriceList();
        if (priceList.length == 0 ) {
            String[] priceErrorList = new String[1];
            priceErrorList[0] = priceIsEmpty;
            return priceErrorList;
        }else {
            String[] priceErrorList = new String[priceList.length];

            for (int i=0; i<priceList.length; i++) {
                if (priceList[i] == null) {
                    priceErrorList[i] = priceIsEmpty;
                    continue;
                }else {
                    try {
                        Double price = Double.parseDouble(priceList[i].trim());
                    }catch (NumberFormatException e) {
                        priceErrorList[i] = incorrectPrice;
                    }
                }
            }

            if (Arrays.asList(priceErrorList).contains(priceIsEmpty) || Arrays.asList(priceErrorList).contains(incorrectPrice) ) {
                return priceErrorList;
            }

        }

        return null;
    }


}
