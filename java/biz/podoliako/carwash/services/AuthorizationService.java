package biz.podoliako.carwash.services;

import biz.podoliako.carwash.models.entity.CarWashOwner;
import biz.podoliako.carwash.models.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public interface AuthorizationService {

    User getUser(String login, String password) throws SQLException;

    Integer getCarWashOwnerId (String login, String password);

    Integer getBusinessPartnerId (String login, String password);

    Integer getManagerId (String login, String password);

    void setBusinessPartnerSession(Integer businessPartnerId, Integer ownerId);

    void setManagerSession(Integer managerId, Integer ownerId);


    void setUserSession(User user, HttpSession session);


    void setChoosenCarWashSession(Integer carWashId, HttpSession session);
}
