package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.UserDao;
import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.models.entity.UserCompensation;
import biz.podoliako.carwash.models.entity.WasherManInBox;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.entity.AddUserForm;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
import biz.podoliako.carwash.services.impl.ConnectDB;
import biz.podoliako.carwash.view.WasherManInBoxWithName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component("UserDao")
@Scope(BeanDefinition.SCOPE_SINGLETON )
public class UserDaoImpl implements UserDao {
    public final static String USER_TABLE = "users";
    public final static String AUTHORIZATION_TABLE = "authorization";
    public final static String PERMISSION_FOR_CARWASH = "permission_for_car_wash";
    public final static String WASHER_MAN_IN_BOX_TABLE = "washer_man_in_box";

    private Connection conn = null;

    private ConnectionDB connectionDB;

    @Autowired
    public UserDaoImpl(ConnectionDB connectionDB) throws SQLException, NamingException {
        this.conn = connectionDB.getConnection();
        this.connectionDB = connectionDB;
    }

    @Override
    public User getUserbyId(Integer id) throws SQLException {
        User user = null;

        String query = "SELECT  u.id as id, " +
                               "u.name as name, " +
                               "u.surname as surname, " +
                               "u.phoneNumber as phoneNumber, " +
                               "u.role as role, " +
                               "u.date_of_creation as date_of_creation, " +
                               "u.date_of_delete as date_of_delete, " +
                               "u.owner_id as owner_id, " +
                               "u.created_by as created_by, " +
                               "u.salary as salary, " +
                               "u.day_commission as day_commission, " +
                               "u.night_commission as night_commission, " +
                               "a.login as login " +
                        "FROM " + USER_TABLE + " AS u " +
                        "JOIN " + AUTHORIZATION_TABLE + " AS a  " +
                        "ON u.id=a.user_id  " +
                        "WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setRole(Role.valueOf(rs.getString("role")));
            user.setDateOfCreation(rs.getDate("date_of_creation"));
            user.setDateOfDelete(rs.getDate("date_of_delete"));
            user.setOwnerId(rs.getInt("owner_id"));
            user.setCreatedBy(rs.getInt("created_by"));
            user.setLogin(rs.getString("login"));

            UserCompensation userCompensation = null;

            if (rs.getBigDecimal("salary") != null
                || new Integer(rs.getInt("day_commission")) != 0
                || new Integer(rs.getInt("night_commission")) != 0) {

                userCompensation = new UserCompensation();
                userCompensation.setSalary(rs.getBigDecimal("salary"));
                userCompensation.setDayCommission(rs.getInt("day_commission"));
                userCompensation.setNightCommission(rs.getInt("night_commission"));
            }
            user.setCompensation(userCompensation);
            user.setCarWashPermissionSet(getUserCarWashes(id));


        }

        return user;
    }

    private Set<Integer> getUserCarWashes(Integer usedId) throws SQLException {
        String query = "SELECT * FROM " + PERMISSION_FOR_CARWASH + " WHERE user_id = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, usedId);
        ResultSet rs = ps.executeQuery();

        Set<Integer> carWashListId = new HashSet<>();
        while (rs.next()){
            carWashListId.add(rs.getInt("car_wash_id"));
        }

        return carWashListId;
    }

    @Override
    public User authorizeUser(String login, String password) {
        return null;
    }

    @Override
    public boolean isLoginExist(String login) throws SQLException {
        String query = "SELECT * FROM " + AUTHORIZATION_TABLE + " WHERE login = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    @Override
    public void addUserWithLogin(AddUserForm userForm) throws SQLException {
        Date dateOfCreation = new Date();

        Integer newUserId = setUserEntity(userForm, dateOfCreation);
        setUserLogPass(userForm, newUserId, dateOfCreation);

        for(Integer i : userForm.getCarWashId()) {
            setPermissionForCarwash(i, newUserId, dateOfCreation, userForm.getCreatedBy());
        }

    }

    @Override
    public void addUserWithOutLogin(AddUserForm userForm) throws SQLException {
        Date dateOfCreation = new Date();

        Integer newUserId = setUserEntity(userForm, dateOfCreation);

        for(Integer i : userForm.getCarWashId()) {
            setPermissionForCarwash(i, newUserId, dateOfCreation, userForm.getCreatedBy());
        }
    }

    @Override
    public List<User> selectAllUserInCarWash(Integer carWashid) throws SQLException {
        String query = "SELECT  " +
                                "u.id as id, " +
                                "u.name as name, " +
                                "u.surname as surname, " +
                                "u.phoneNumber as phoneNumber, " +
                                "u.role as role, " +
                                "u.date_of_creation as date_of_creation, " +
                                "u.date_of_delete as date_of_delete, " +
                                "u.owner_id as owner_id," +
                                "u.created_by as created_by " +
                       "FROM " + PERMISSION_FOR_CARWASH + " AS p  " +
                       "JOIN " + USER_TABLE + " AS u " +
                       "ON p.user_id = u.id " +
                       "WHERE p.car_wash_id = ? AND " +
                             "p.date_of_delete IS NULL AND " +
                             "u.date_of_delete is NULL " +
                       "ORDER by u.role, u.surname";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, carWashid);

        ResultSet rs = ps.executeQuery();

        List<User> userList = new ArrayList<>();

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setRole(Role.valueOf(rs.getString("role")));
            user.setDateOfCreation(rs.getDate("date_of_creation"));
            user.setDateOfDelete(null);
            user.setOwnerId(rs.getInt("owner_id"));
            user.setCreatedBy(rs.getInt("created_by"));
            userList.add(user);
        }

        return userList;
    }

    @Override
    public Set<Integer> getUserPermission(Integer userId) throws SQLException {
        Set<Integer> permissionSet = new HashSet<>();

        String query = "SELECT * FROM " + PERMISSION_FOR_CARWASH + " WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            permissionSet.add(rs.getInt("car_wash_id"));
        }

        return permissionSet;
    }

    @Override
    public Set<User> selectAllWasherManInCarWash(Integer carWashId) {
        Connection connection = null;
        PreparedStatement ps = null;
        Set<User> washermans = new LinkedHashSet<>();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                                "u.id as id, " +
                                "u.name as name, " +
                                "u.surname as surname, " +
                                "u.phoneNumber as phoneNumber, " +
                                "u.role as role, " +
                                "u.date_of_creation as date_of_creation, " +
                                "u.date_of_delete as date_of_delete, " +
                                "u.owner_id as owner_id," +
                                "u.created_by as created_by " +
                           "FROM " + PERMISSION_FOR_CARWASH + " as p " +
                           "JOIN " + USER_TABLE + " as u " +
                           "ON p.user_id = u.id " +
                           "WHERE p.date_of_delete IS NULL " +
                           "AND u.role = '" + Role.washerMan + "' "+
                           "AND p.car_wash_id = ? " +
                           "ORDER BY u.surname, u.name";

            ps = connection.prepareStatement(query);
            ps.setInt(1, carWashId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setDateOfCreation(rs.getTimestamp("date_of_creation"));
                user.setDateOfDelete(rs.getTimestamp("date_of_delete"));
                user.setOwnerId(rs.getInt("owner_id"));
                user.setCreatedBy(rs.getInt("created_by"));
                washermans.add(user);

            }

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectAllWasherManInCarWash (UserDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectAllWasherManInCarWash (UserDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectAllWasherManInCarWash (UserDaoImpl) " + e);
            }
        }

        return washermans;
    }

    @Override
    public void addWasherManInBox(WasherManInBox washerManInBox) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
                connection = connectionDB.getConnection();

                String query = "INSERT INTO " + WASHER_MAN_IN_BOX_TABLE +
                               " (user_id, car_wash_id, box_number, start, finish, set_by) " +
                               "VALUES (?,     ?,          ?,          ?,     ?,      ?  ) ";

                ps = connection.prepareStatement(query);
                ps.setInt(1, washerManInBox.getUserId());
                ps.setInt(2, washerManInBox.getCarWashId());
                ps.setInt(3, washerManInBox.getBoxNumber());
                ps.setObject(4, washerManInBox.getStartInBox());
                ps.setObject(5, washerManInBox.getFinishInBox());
                ps.setInt(6, washerManInBox.getSetInBoxBy());

                ps.execute();

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе addWasherManInBox (UserDaoImpl) " + e);
        } catch (NamingException e) {
           throw new NamingRuntimeException("Naming exception в методе addWasherManInBox (UserDaoImpl) " + e);
        } finally {
                try {
                    if (ps !=null){
                        ps.close();
                    }
                    if (connection !=null ){
                        connection.close();
                    }

                } catch (SQLException e) {
                    throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе addWasherManInBox (UserDaoImpl) " + e);
                }
        }


    }

    @Override
    public Set<WasherManInBoxWithName> selectWasherManInBoxWithName(Integer carWashId, int box)  {
        Connection connection = null;
        PreparedStatement ps = null;
        Set<WasherManInBoxWithName> washerManList = null;

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                                   "w.id as id, " +
                                   "w.user_id as user_id, " +
                                   "w.car_wash_id as car_wash_id, " +
                                   "w.box_number as box_number, " +
                                   "w.start as start, " +
                                   "w.finish as finish, " +
                                   "w.set_by as set_by, " +
                                   "u.name as name, " +
                                   "u.surname as surname " +
                           "FROM " + WASHER_MAN_IN_BOX_TABLE + " as w " +
                           "JOIN " + USER_TABLE + " as u " +
                           "ON w.user_id = u.id " +
                           "WHERE w.car_wash_id = ? " +
                           "AND w.box_number = ? " +
                           "AND w.finish IS NULL " +
                           "ORDER BY u.surname";

            ps = connection.prepareStatement(query);
            ps.setInt(1, carWashId);
            ps.setInt(2, box);

            ResultSet rs = ps.executeQuery();

            washerManList = new HashSet<>();

            while (rs.next()) {
                WasherManInBoxWithName washerManWithName = new WasherManInBoxWithName();
                washerManWithName.setId(rs.getInt("id"));
                washerManWithName.setUserId(rs.getInt("user_id"));
                washerManWithName.setCarWashId(rs.getInt("car_wash_id"));
                washerManWithName.setBoxNumber(rs.getInt("box_number"));
                washerManWithName.setStartInBox(rs.getTimestamp("start"));
                washerManWithName.setFinishInBox(rs.getTimestamp("finish"));
                washerManWithName.setSetInBoxBy(rs.getInt("set_by"));
                washerManWithName.setName(rs.getString("name"));
                washerManWithName.setSurname(rs.getString("surname"));

                washerManList.add(washerManWithName);
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectWasherManInBoxWithName (UserDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectWasherManInBoxWithName (UserDaoImpl) " + e);
        }finally {
            try {
                if (ps !=null){
                    ps.close();
                }
                if (connection !=null ){
                    connection.close();
                }

            } catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectWasherManInBoxWithName (UserDaoImpl) " + e);
            }

        }


        return washerManList;
    }

    @Override
    public Set<WasherManInBoxWithName> selectAvailableAndCurrentWasherManInBox(Integer carWashId, Integer boxNum) {
        Connection connection = null;
        PreparedStatement ps = null;
        Set<WasherManInBoxWithName> washerManInBoxWithNameSet = new LinkedHashSet<>();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT DISTINCT " +
                                    "w.id as id, " +
                                    "w.user_id as user_id, " +
                                    "w.car_wash_id as car_wash_id, " +
                                    "w.box_number as box_number, " +
                                    "w.start as start, " +
                                    "w.finish as finish, " +
                                    "w.set_by as set_by, " +
                                    "u.name as name, " +
                                    "u.surname as surname " +
                           "FROM " + PERMISSION_FOR_CARWASH + " as p " +
                           "JOIN " + USER_TABLE + " as u " +
                           "ON p.user_id = u.id " +
                           "LEFT JOIN " + WASHER_MAN_IN_BOX_TABLE + " as w " +
                           "ON p.user_id = w.user_id " +

                           "WHERE p.car_wash_id = ? " +
                           "AND p.date_of_delete IS NULL " +
                           "AND u.role = '" +Role.washerMan +"' " +
                           "AND p.user_id NOT IN " +
                                                 "(SELECT wa.user_id " +
                                                 "FROM "+ WASHER_MAN_IN_BOX_TABLE + " as wa " +
                                                 "WHERE  finish is NULL " +
                                                 "AND box_number <> ? " +
                                                 "AND car_wash_id = ? ) " +
                           "ORDER BY u.surname, u.name";

            ps = connection.prepareStatement(query);
            ps.setInt(1, carWashId);
            ps.setInt(2, boxNum);
            ps.setInt(3, carWashId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                WasherManInBoxWithName washerManWithName = new WasherManInBoxWithName();
                washerManWithName.setId(rs.getInt("id"));
                washerManWithName.setUserId(rs.getInt("user_id"));
                washerManWithName.setCarWashId(rs.getInt("car_wash_id"));
                washerManWithName.setBoxNumber(rs.getInt("box_number"));
                washerManWithName.setStartInBox(rs.getTimestamp("start"));
                washerManWithName.setFinishInBox(rs.getTimestamp("finish"));
                washerManWithName.setSetInBoxBy(rs.getInt("set_by"));
                washerManWithName.setName(rs.getString("name"));
                washerManWithName.setSurname(rs.getString("surname"));

                washerManInBoxWithNameSet.add(washerManWithName);
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectAvailableAndCurrentWasherManInBox (UserDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectAvailableAndCurrentWasherManInBox (UserDaoImpl) " + e);
        } finally {
            try {
                if (ps !=null){
                    ps.close();
                }
                if (connection !=null ){
                    connection.close();
                }

            } catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception, Cannot close connection or PreparedStatement in selectAvailableAndCurrentWasherManInBox (UserDaoImpl) " + e);
            }

        }

        return washerManInBoxWithNameSet;
    }

    @Override
    public Set<WasherManInBox> selectAllWasherManInBox(Integer carWashId, Integer boxNum) {
        Connection connection = null;
        PreparedStatement ps = null;
        Set<WasherManInBox> washerManInBoxSet = new HashSet<>();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                                    "w.id as id, " +
                                    "w.user_id as user_id, " +
                                    "w.car_wash_id as car_wash_id, " +
                                    "w.box_number as box_number, " +
                                    "w.start as start, " +
                                    "w.finish as finish, " +
                                    "w.set_by as set_by " +

                            "FROM "  + WASHER_MAN_IN_BOX_TABLE + " as w " +

                            "WHERE w.car_wash_id = ? " +
                            "AND w.finish IS NULL " +
                            "AND w.box_number = ? ";

            ps = connection.prepareStatement(query);
            ps.setInt(1, carWashId);
            ps.setInt(2, boxNum);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                WasherManInBox washerMan  = new WasherManInBox();
                washerMan.setId(rs.getInt("id"));
                washerMan.setUserId(rs.getInt("user_id"));
                washerMan.setCarWashId(rs.getInt("car_wash_id"));
                washerMan.setBoxNumber(rs.getInt("box_number"));
                washerMan.setStartInBox(rs.getTimestamp("start"));
                washerMan.setFinishInBox(rs.getTimestamp("finish"));
                washerMan.setSetInBoxBy(rs.getInt("set_by"));

                washerManInBoxSet.add(washerMan);
            }
        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectAllWasherManInBox (UserDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectAllWasherManInBox (UserDaoImpl) " + e);
        }finally {
            try {
                if (ps !=null){
                    ps.close();
                }
                if (connection !=null ){
                    connection.close();
                }

            } catch (SQLException e) {
                System.out.println("Cannot close connection or PreparedStatement in selectAllWasherManInBox (UserDaoImpl) : " + e);
            }
        }

        return washerManInBoxSet;
    }

    @Override
    public Set<Integer> selectUserIdsWasherManInBox(Integer carWashId, Integer boxNum) {
        Connection connection = null;
        PreparedStatement ps = null;
        Set<Integer> washerManIds = new HashSet<>();

        try {
            connection = connectionDB.getConnection();

            String query = "SELECT " +
                    "w.user_id as user_id " +

                    "FROM "  + WASHER_MAN_IN_BOX_TABLE + " as w " +

                    "WHERE w.car_wash_id = ? " +
                    "AND w.finish IS NULL " +
                    "AND w.box_number = ? ";

            ps = connection.prepareStatement(query);
            ps.setInt(1, carWashId);
            ps.setInt(2, boxNum);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                washerManIds.add(rs.getInt("user_id"));
            }

        } catch (SQLException e) {
           throw new SQLRuntimeException("SQL exception в методе selectIdsWasherManInBox (UserDaoImpl) " + e);
        } catch (NamingException e) {
           throw new NamingRuntimeException("Naming exception в методе selectIdsWasherManInBox (UserDaoImpl) " + e);
        } finally {
            try {
                if (ps !=null){
                    ps.close();
                }
                if (connection !=null ){
                    connection.close();
                }

            } catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception, Cannot close connection or PreparedStatement in selectIdsWasherManInBox (UserDaoImpl) " + e);
            }

        }

        return washerManIds;

    }

    @Override
    public void insertFinishTimeForWasherManInBox(Integer userId, Date date) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionDB.getConnection();
            String query = "UPDATE " + WASHER_MAN_IN_BOX_TABLE + " SET finish = ? WHERE user_id = ? AND finish IS NULL ";
            ps = connection.prepareStatement(query);
            ps.setObject(1, date);
            ps.setInt(2, userId);
            ps.execute();

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе insertFinishTimeForWasherManInBox (UserDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе insertFinishTimeForWasherManInBox (UserDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception, Cannot close connection or PreparedStatement in insertFinishTimeForWasherManInBox (UserDaoImpl) " + e);
            }

        }
    }

    @Override
    public Integer getUserIdByLogPass(String login, String password) throws SQLException {
        Integer userId = null;
        String query = "SELECT * FROM " + AUTHORIZATION_TABLE + " WHERE login = ? AND password = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, login);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            userId = rs.getInt("user_id");
        }

        return userId;
    }

    private Integer setUserEntity(AddUserForm userForm, Date dateOfCreation) throws SQLException {
        String query = "INSERT INTO " + USER_TABLE + " " +
                "(name, surname, phoneNumber, role, salary, day_commission, night_commission, date_of_creation, date_of_delete, owner_id, created_by) " +
                "VALUES " +
                "(?,      ?,         ?,         ?,     ?,        ?,              ?,                 ?,                NULL,         ?,        ?      )";

        PreparedStatement ps = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, userForm.getName());
        ps.setString(2, userForm.getSurname());
        ps.setString(3, userForm.getPhoneNumber());
        ps.setString(4, userForm.getRole().toString());
        ps.setBigDecimal(5, new BigDecimal(userForm.getSalary()));
        ps.setInt(6, userForm.getDayCommission());
        ps.setInt(7, userForm.getNightCommission());
        ps.setObject(8, dateOfCreation);
        ps.setInt(9, userForm.getOwnerId());
        ps.setInt(10, userForm.getCreatedBy());

        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return  rs.getInt(1);
    }

    private boolean setUserLogPass(AddUserForm userForm, Integer userId, Date dateOfCreation) throws SQLException {
        String query = "INSERT INTO " + AUTHORIZATION_TABLE + " (login, password, user_id, date_of_creation, owner_id, date_of_delete) VALUES (?, ?, ?, ?, ?, NULL )";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, userForm.getLogin());
        ps.setString(2, userForm.getPassword());
        ps.setInt(3, userId);
        ps.setObject(4, dateOfCreation);
        ps.setInt(5, userForm.getOwnerId());

        return  ps.execute();
    }

    private boolean setPermissionForCarwash(Integer carWashId, Integer userId, Date dateOfCreation, Integer careatedBy) throws SQLException {
        String query = "INSERT INTO " + PERMISSION_FOR_CARWASH + "(car_wash_id, user_id, date_of_creation, created_by, date_of_delete) " +
                       "VALUES (?, ?, ?, ?, NULL )";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, carWashId);
        ps.setInt(2, userId);
        ps.setObject(3, dateOfCreation);
        ps.setInt(4, careatedBy);

        return ps.execute();

    }


}
