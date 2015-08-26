package biz.podoliako.carwash.dao;

public interface DaoFactory {

    public CarWashDao getCarWashDao();
    public CategoryDao getCategoryDao();
    public ServiceDao getServiceDao();
    public UserDao getUserDao();
    public CarBrandDao getCarBrandDao();



}
