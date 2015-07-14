package biz.podoliako.carwash.models.impl;

import biz.podoliako.carwash.dao.DaoCarWashOwner;
import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.dao.pojo.CarWashOwner;
import biz.podoliako.carwash.models.AuthorizationModel;
import biz.podoliako.carwash.models.pojo.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by bizon4ik on 31.05.15.
 */
@Component("AuthorizationModel")
public class AuthorizationModelImpl implements AuthorizationModel {

    @Autowired
    private DaoFactory daoFactory;

    private HttpSession session = null;

    @Autowired
    private MD5 md5;

    @Override
    public String getUserRoleAndSetSessions(String login, String password, HttpServletRequest request) throws SQLException {
        DaoCarWashOwner daoCarWashOwner = daoFactory.getDaoCarWashOwner();
        this.session = request.getSession();

        password = md5.hashing(password.trim());
        CarWashOwner carWashOwner = daoCarWashOwner.selectCarWashOwner(login.trim(), password);
        if (carWashOwner != null){
            setCarWashOwnerSession(carWashOwner);
            return "owner";
        }

        return null;
    }

    @Override
    public Integer getCarWashOwnerId(String login, String password) {
        return null;
    }

    @Override
    public Integer getBusinessPartnerId(String login, String password) {
        return null;
    }

    @Override
    public Integer getManagerId(String login, String password) {
        return null;
    }

    @Override
    public void setCarWashOwnerSession(CarWashOwner carWashOwner) {
        Authorization authorization = new Authorization();
        authorization.setCarWashOwnerId(carWashOwner.getId());
        authorization.setOwnerid(carWashOwner.getId());
        authorization.setUserName(carWashOwner.getLogin());
        session.setAttribute("authorization", authorization);
       /* session.setAttribute(AuthorizationModel.SESSION_CAR_WASH_OWNER_ID, carWashOwner.getId());
        session.setAttribute("ownerId", carWashOwner.getId());
        session.setAttribute("userName", carWashOwner.getLogin());*/
    }

    @Override
    public void setBusinessPartnerSession(Integer businessPartnerId, Integer ownerId) {

    }

    @Override
    public void setManagerSession(Integer managerId, Integer ownerId) {

    }
}
