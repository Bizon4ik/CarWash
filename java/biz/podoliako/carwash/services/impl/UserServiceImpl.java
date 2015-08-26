package biz.podoliako.carwash.services.impl;


import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.services.CarWashService;
import biz.podoliako.carwash.models.entity.*;
import biz.podoliako.carwash.services.MD5;
import biz.podoliako.carwash.services.UserService;
import biz.podoliako.carwash.services.entity.AddUserForm;
import biz.podoliako.carwash.view.AddUserFormView;
import biz.podoliako.carwash.view.WasherManInBoxWithName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.*;

@Service("UserService")
public class UserServiceImpl implements UserService{

    private DaoFactory daoFactory;
    private CarWashService carWashService;

    @Autowired
    public UserServiceImpl(DaoFactory daoFactory,
                           CarWashService carWashService){
         this.daoFactory = daoFactory;
        this.carWashService = carWashService;
    }

    @Override
    public boolean isLoginExist(String login) throws SQLException {
        return daoFactory.getUserDao().isLoginExist(login.trim());
    }

    @Override
    public AddUserFormView getAddAdminForm(Integer ownerId) throws SQLException {
        AddUserFormView addUserFormView = new AddUserFormView();
        addUserFormView.setTitle("Добавить администратора");
        addUserFormView.setCarWashList(carWashService.getAllCarWashes(ownerId));
        addUserFormView.setLogPassPart(true);
        addUserFormView.setUrl("addAdmin");

        return addUserFormView;
    }

    @Override
    public void addUser(AddUserForm addUserForm) throws SQLException {
            addUserForm = prepareAddUserForm(addUserForm);
        if (addUserForm.getLogin() != null) {
            daoFactory.getUserDao().addUserWithLogin(addUserForm);
        }else {
            daoFactory.getUserDao().addUserWithOutLogin(addUserForm);
        }

    }

    @Override
    public AddUserFormView getAddWasherManForm(Integer ownerId) throws SQLException {
        AddUserFormView addUserFormView = new AddUserFormView();
        addUserFormView.setTitle("Добавить мойщика");
        addUserFormView.setCarWashList(carWashService.getAllCarWashes(ownerId));
        addUserFormView.setLogPassPart(false);
        addUserFormView.setUrl("addWasherMan");

        return addUserFormView;
    }

    @Override
    public Map<CarWash, List<User>> getAllUsers(Integer ownerId) throws SQLException {
        List<CarWash> carWashList = carWashService.getAllCarWashes(ownerId);
        Map<CarWash, List<User>> allUsers = new LinkedHashMap<>();

        for(CarWash carWash : carWashList) {
            List<User> userList = getAllUserInCarWash(carWash.getId());

            allUsers.put(carWash, userList);
        }

        return allUsers;
    }

    public List<User> getAllUserInCarWash(Integer carWashid) throws SQLException {
        return daoFactory.getUserDao().selectAllUserInCarWash(carWashid);

    }

    @Override
    public List<Set<WasherManInBoxWithName>> getWasherMansWorkingInBoxesNow(Integer carWashId) throws SQLException, NamingException {
        CarWash carWash = daoFactory.getCarWashDao().selectCarWash(carWashId);

        List<Set<WasherManInBoxWithName>> washerManList = new ArrayList<>();

        for (int box = 1; box <= carWash.getBoxAmount(); box++) {
            Set<WasherManInBoxWithName> washerManSet = daoFactory.getUserDao().selectWasherManInBoxWithName(carWashId, box);

            if (washerManSet.size() == 0) {
                washerManList.add(null);
            }else {
                washerManList.add(washerManSet);
            }
        }
        return washerManList;
    }

    @Override
    public Set<User> getAllWasherManInCarWash(Integer carWashId) {
        return daoFactory.getUserDao().selectAllWasherManInCarWash(carWashId);

    }

    @Override
    public void setWasherMansInBox(Integer carWashId, Integer boxNum, String[] userIds, Integer userIdWhoSetWasherMan) throws SQLException, NamingException {
        Set<Integer> userId = transferStringIdIntoIntergerSet(userIds);

        for(Integer id : userId){
            WasherManInBox washerManInBox = createNewWasherManInBox(carWashId, boxNum, id, userIdWhoSetWasherMan);
            daoFactory.getUserDao().addWasherManInBox(washerManInBox);
        }
    }

    @Override
    public Set<WasherManInBoxWithName> getAvailableAndCurrentWasherManInBox(Integer carWashId, Integer boxNum) throws SQLException, NamingException {
        return daoFactory.getUserDao().selectAvailableAndCurrentWasherManInBox(carWashId, boxNum);
    }

    @Override
    public Set<WasherManInBox> getAllWasherManInBox(Integer carWashId, Integer boxNum) throws SQLException, NamingException {
        return daoFactory.getUserDao().selectAllWasherManInBox(carWashId, boxNum);
    }

    @Override
    public void modifyWasherManTeamInBox(Integer carWashId, Integer boxNum, String[] washerManIds, Integer currentUserId) {
        Set<Integer> pickedUserWasherManIdsInt = transferStringIdIntoIntergerSet(washerManIds);
        Set<Integer> userIdsWasherManInBoxNow = daoFactory.getUserDao().selectUserIdsWasherManInBox(carWashId, boxNum);

        for (Integer id : pickedUserWasherManIdsInt){
            if (id == -1){
                /* DO Nothing, it's empty select */
            }else if(userIdsWasherManInBoxNow.contains(id)){
                userIdsWasherManInBoxNow.remove(id);
            }else{
                WasherManInBox washerManInBox = createNewWasherManInBox(carWashId, boxNum, id, currentUserId);
                daoFactory.getUserDao().addWasherManInBox(washerManInBox);
            }
        }

        for(Integer id : userIdsWasherManInBoxNow){
            daoFactory.getUserDao().insertFinishTimeForWasherManInBox(id, new Date());
        }

    }


    private Set<Integer> transferStringIdIntoIntergerSet(String[] userIds) {
        Set<Integer> userId = new HashSet<>();

        for(String s: userIds){
            userId.add(Integer.valueOf(s));
        }

        return  userId;
    }

    private WasherManInBox createNewWasherManInBox(Integer carWashId, Integer boxNumber, Integer userId, Integer userIdHowSetWasherMan) {
          WasherManInBox washerManInBox = new WasherManInBox();
          washerManInBox.setCarWashId(carWashId);
          washerManInBox.setBoxNumber(boxNumber);
          washerManInBox.setUserId(userId);
          washerManInBox.setStartInBox(new Date());
          washerManInBox.setFinishInBox(null);
          washerManInBox.setSetInBoxBy(userIdHowSetWasherMan);

           return washerManInBox;
    }

    private AddUserForm prepareAddUserForm(AddUserForm addUserForm) {
        AddUserForm form = new AddUserForm();
        form.setName(addUserForm.getName().trim().toLowerCase());
        form.setSurname(addUserForm.getSurname().trim().toLowerCase());
        form.setPhoneNumber(addUserForm.getPhoneNumber().trim().toLowerCase());
        form.setSalary(addUserForm.getSalary().trim());
        form.setCarWashId(addUserForm.getCarWashId());
        form.setDayCommission(addUserForm.getDayCommission());
        form.setNightCommission(addUserForm.getNightCommission());
        form.setOwnerId(addUserForm.getOwnerId());
        form.setCreatedBy(addUserForm.getCreatedBy());

        String login = addUserForm.getLogin() == null ? null : addUserForm.getLogin().trim();
        form.setLogin(login);
        String password = addUserForm.getPassword() == null ? null : MD5.hashing(addUserForm.getPassword().trim());
        form.setPassword(password);
        form.setRole(addUserForm.getRole());

        return form;
    }




}
