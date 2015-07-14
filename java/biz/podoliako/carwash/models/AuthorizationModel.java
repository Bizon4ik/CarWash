package biz.podoliako.carwash.models;

import biz.podoliako.carwash.dao.pojo.CarWashOwner;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface AuthorizationModel {
    static final String SESSION_CAR_WASH_OWNER_ID   = "carWashOwnerId";     /* var identify Car Wash Owner  */
    static final String SESSION_BUSINESS_PARTNER_ID = "businessPartnerId";
    static final String SESSION_MANAGER_ID          = "managerId";
    static final String SESSION_OWNER_ID            = "ownerId";   /* this var is using in DAO
                                                                    (all tables have ownerId which is equal to CAR_WASH_OWNER_ID
                                                                    who created all date in table) */

    String getUserRoleAndSetSessions(String login, String password, HttpServletRequest request) throws SQLException;

    Integer getCarWashOwnerId (String login, String password);

    Integer getBusinessPartnerId (String login, String password);

    Integer getManagerId (String login, String password);

    void setCarWashOwnerSession(CarWashOwner carWashOwner);

    void setBusinessPartnerSession(Integer businessPartnerId, Integer ownerId);

    void setManagerSession(Integer managerId, Integer ownerId);



}
