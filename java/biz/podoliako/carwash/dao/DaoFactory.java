package biz.podoliako.carwash.dao;

import java.sql.Connection;

/**
 * Created by bizon4ik on 27.05.15.
 */
public interface DaoFactory {
    public DaoCarWashOwner getDaoCarWashOwner();
    public CarWashDao getCarWashDao();
    public CategoryDao getCategoryDao();
    public ServiceDao getServiceDao();

    /*Connection connection = null;
    private DaoClient daoClient;
    private DaoOrders daoOrders;
    private DaoCarWash daoCarWash;
    private DaoService daoService;
    private DaoCategoriesCar daoCategoriesCar;

    public DaoClient getDaoClient() {
        if (daoClient == null) {
            daoClient = new DaoClient(connection);
        }
        return daoClient;
    }

    public DaoOrders getDaoOrders() {
        if (daoOrders == null) {
            daoOrders = new DaoOrders(connection);
        }
        return daoOrders;
    }

    public DaoCarWash getDaoCarWash() {
        if (daoCarWash == null) {
            daoCarWash = new DaoCarWash(connection);
        }
        return daoCarWash;
    }

    public DaoService getDaoService() {
        if (daoService == null) {
            daoService = new DaoService(connection);
        }
        return daoService;
    }

    public DaoCategoriesCar getDaoCategoriesCar() {
        if (daoCategoriesCar == null) {
            daoCategoriesCar = new DaoCategoriesCar(connection);
        }
        return  daoCategoriesCar;
    }*/
}
