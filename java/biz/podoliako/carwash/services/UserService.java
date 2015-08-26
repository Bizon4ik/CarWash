package biz.podoliako.carwash.services;

import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.models.entity.WasherManInBox;
import biz.podoliako.carwash.services.entity.AddUserForm;
import biz.podoliako.carwash.view.AddUserFormView;
import biz.podoliako.carwash.view.WasherManInBoxWithName;


import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    boolean isLoginExist(String login) throws SQLException;

    AddUserFormView getAddAdminForm(Integer ownerId) throws SQLException;

    void addUser(AddUserForm addUserForm) throws SQLException;

    AddUserFormView getAddWasherManForm(Integer ownerid) throws SQLException;

    Map<CarWash,List<User>> getAllUsers(Integer ownerId) throws SQLException;

    List<User> getAllUserInCarWash(Integer carWashid) throws SQLException;

    List<Set<WasherManInBoxWithName>> getWasherMansWorkingInBoxesNow(Integer carWashId) throws SQLException, NamingException;

    Set<User> getAllWasherManInCarWash(Integer carWashId) throws SQLException, NamingException;

    void setWasherMansInBox(Integer washId, Integer boxNum, String[] washerManIds, Integer userIdHowSetWasherMan) throws SQLException, NamingException;

    Set<WasherManInBoxWithName> getAvailableAndCurrentWasherManInBox(Integer washId, Integer boxNum) throws SQLException, NamingException;

    Set<WasherManInBox> getAllWasherManInBox(Integer washId, Integer boxNum) throws SQLException, NamingException;


    void modifyWasherManTeamInBox(Integer washId, Integer boxNum, String[] washerManIds, Integer currentUserId) throws SQLException, NamingException;
}
