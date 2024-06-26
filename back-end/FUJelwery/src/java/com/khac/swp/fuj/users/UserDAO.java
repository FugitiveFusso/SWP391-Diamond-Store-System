/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khac.swp.fuj.users;

import com.khac.swp.fuj.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public UserDTO login(String username, String password, String rolename) {
        UserDTO user = null;
        try {

            Connection con = DBUtils.getConnection();
            String sql = " select userID, userName, firstName, lastName, email, phoneNumber, address, point, status, roleName from [User] u full join [Role] r on u.roleID = r.roleID ";
            sql += " WHERE roleName = ? AND password = ? AND userName = ? and isDeleted = 'active' ";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rolename);
            stmt.setString(2, password);
            stmt.setString(3, username);

            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                if (rs.next()) {
                    user = new UserDTO();
                    user.setUsername(rs.getString("userName"));
                    user.setFirstname(rs.getString("firstName"));
                    user.setUserid(rs.getInt("userID"));
                    user.setLastname(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setPhonenumber(rs.getString("phoneNumber"));
                    user.setAddress(rs.getString("address"));
                    user.setPoint(rs.getInt("point"));
                    user.setRolename(rs.getString("roleName"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();

        }
        return user;
    }

    public UserDTO loginActive(String username, String password, String rolename) {
        UserDTO user = null;
        try {

            Connection con = DBUtils.getConnection();
            String sql = " select userID, userName, firstName, lastName, email, phoneNumber, address, point, status, roleName from [User] u full join [Role] r on u.roleID = r.roleID ";
            sql += " WHERE roleName = ? AND password = ? AND userName = ? AND STATUS = 'active'";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, rolename);
            stmt.setString(2, password);
            stmt.setString(3, username);

            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                if (rs.next()) {
                    user = new UserDTO();
                    user.setUsername(rs.getString("userName"));
                    user.setFirstname(rs.getString("firstName"));
                    user.setUserid(rs.getInt("userID"));
                    user.setLastname(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setPhonenumber(rs.getString("phoneNumber"));
                    user.setAddress(rs.getString("address"));
                    user.setPoint(rs.getInt("point"));
                    user.setRolename(rs.getString("roleName"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();

        }
        return user;
    }

    public List<UserDTO> list(String keyword, String sortCol, String roleName) {
        List<UserDTO> list = new ArrayList<UserDTO>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = " select userID, userName, firstName, lastName, email, address, status, r.roleID, roleName  from [User] u full join [Role] r on u.roleID = r.roleID where roleName like ? AND isDeleted = 'active' ";
            if (keyword != null && !keyword.isEmpty()) {
                sql += " and (userName like ? or firstName like ? or lastName like ? or email like ?)";
            }

            if (sortCol != null && !sortCol.isEmpty()) {
                sql += " ORDER BY " + sortCol + " ASC ";
            }

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, "%" + roleName + "%");

            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(2, "%" + keyword + "%");
                stmt.setString(3, "%" + keyword + "%");
                stmt.setString(4, "%" + keyword + "%");
                stmt.setString(5, "%" + keyword + "%");

            }
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {

                    int customerid = rs.getInt("userID");
                    String username = rs.getString("userName");
                    String firstname = rs.getString("firstName");
                    String lastname = rs.getString("lastName");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String status = rs.getString("status");
                    int roleid = rs.getInt("roleID");
                    String rolename = rs.getString("roleName");

                    UserDTO user = new UserDTO();
                    user.setUserid(customerid);
                    user.setUsername(username);
                    user.setFirstname(firstname);
                    user.setLastname(lastname);
                    user.setEmail(email);
                    user.setAddress(address);
                    user.setStatus(status);
                    user.setRoleid(roleid);
                    user.setRolename(rolename);
                    list.add(user);
                }
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public UserDTO load(int userID, String roleName) {

        String sql = "select userID, userName, password, firstName, lastName, email, phoneNumber, address, point from [User] u full join [Role] r on u.roleID = r.roleID where userID = ? and roleName like ? and isDeleted = 'active'";

        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setString(2, roleName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                int customerid = rs.getInt("userID");
                String password = rs.getString("password");
                String customername = rs.getString("userName");
                String firstname = rs.getString("firstName");
                String lastname = rs.getString("lastName");
                String email = rs.getString("email");
                String phonenumber = rs.getString("phoneNumber");
                String address = rs.getString("address");
                int point = rs.getInt("point");

                UserDTO user = new UserDTO();
                user.setUserid(customerid);
                user.setUsername(customername);
                user.setPassword(password);
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setEmail(email);
                user.setPhonenumber(phonenumber);
                user.setAddress(address);
                user.setPoint(point);
                return user;
            }
        } catch (SQLException ex) {
            System.out.println("Query User error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public Integer insert(UserDTO user) {
        String sql = "INSERT INTO [User] (userName, password, firstName, lastName, phoneNumber, email, address, point, status, roleID, isDeleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'active', ?, 'active' )";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstname());
            ps.setString(4, user.getLastname());
            ps.setString(5, user.getPhonenumber());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getAddress());
            ps.setInt(8, user.getPoint());
            ps.setInt(9, user.getRoleid());
            ps.executeUpdate();
            conn.close();
            return user.getUserid();
        } catch (SQLException ex) {
            System.out.println("Insert User error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public boolean update(UserDTO user) {
        String sql = "UPDATE [User] SET userName = ?, password = ?, firstName = ?, lastName = ?, phoneNumber = ?, email = ?, address = ?, point = ? WHERE userID = ? ";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(9, user.getUserid());
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstname());
            ps.setString(4, user.getLastname());
            ps.setString(5, user.getPhonenumber());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getAddress());
            ps.setInt(8, user.getPoint());

            ps.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Update User error!" + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    /*
    Delete student 
     */
    public boolean delete(int id) {
        String sql = "UPDATE [User] set isDeleted = 'deleted' where userID = ? ";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Delete User error!" + ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }

    public void signup(String username, String password, String firstname, String lastname, String phonenumber, String email, String address, int point, int roleid) {
        String sql = "INSERT INTO [User] (userName, password, firstName, lastName, phoneNumber, email, address, point, status, roleID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'active', ?)";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, firstname);
            ps.setString(4, lastname);
            ps.setString(5, phonenumber);
            ps.setString(6, email);
            ps.setString(7, address);
            ps.setInt(8, point);
            ps.setInt(9, roleid);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Insert User error!" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public UserDTO checkAccountExist(String username) {
        UserDTO user = null;
        try {

            Connection con = DBUtils.getConnection();
            String sql = " select userName, firstName, lastName, email, phoneNumber, point, roleID from [User] u ";
            sql += " WHERE userName = ? and status = 'active' and isDeleted = 'active' ";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                if (rs.next()) {
                    user = new UserDTO();
                    user.setUsername(rs.getString("userName"));
                    user.setFirstname(rs.getString("firstName"));
                    user.setUserid(rs.getInt("userID"));
                    user.setLastname(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setPhonenumber(rs.getString("phoneNumber"));
                    user.setPoint(rs.getInt("point"));
                    user.setRoleid(rs.getInt("roleID"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();

        }
        return user;
    }

    public UserDTO checkAccountExistByGoogle(String email) {
        UserDTO user = null;
        try {

            Connection con = DBUtils.getConnection();
            String sql = " select userName, firstName, lastName, email, phoneNumber, point, roleID from [User] u ";
            sql += " WHERE email = ? AND isDeleted = 'active'";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                if (rs.next()) {
                    user = new UserDTO();
                    user.setUsername(rs.getString("userName"));
                    user.setFirstname(rs.getString("firstName"));
                    user.setUserid(rs.getInt("userID"));
                    user.setLastname(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setPhonenumber(rs.getString("phoneNumber"));
                    user.setPoint(rs.getInt("point"));
                    user.setRoleid(rs.getInt("roleID"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();

        }
        return user;
    }

    public UserDTO checkAccountExistByGoogleActive(String email) {
        UserDTO user = null;
        try {

            Connection con = DBUtils.getConnection();
            String sql = " select userName, firstName, lastName, email, phoneNumber, point, roleID from [User] u ";
            sql += " WHERE email = ? and status = 'active' ";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                if (rs.next()) {
                    user = new UserDTO();
                    user.setUsername(rs.getString("userName"));
                    user.setFirstname(rs.getString("firstName"));
                    user.setUserid(rs.getInt("userID"));
                    user.setLastname(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setPhonenumber(rs.getString("phoneNumber"));
                    user.setPoint(rs.getInt("point"));
                    user.setRoleid(rs.getInt("roleID"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();

        }
        return user;
    }

    public boolean active(int id) {
        String sql = "UPDATE [User] set status = 'active' where userID = ? ";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Active User error!" + ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }

    public boolean banned(int id) {
        String sql = "UPDATE [User] set status = 'banned' where userID = ? ";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Banned User error!" + ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }

    public List<UserDTO> listStatistics() {
        List<UserDTO> list = new ArrayList<UserDTO>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT r.roleName,\n"
                    + "       COUNT(u.userID) AS TotalUsers,\n"
                    + "       SUM(CASE WHEN u.[status] = 'active' THEN 1 ELSE 0 END) AS ActiveUserCount,\n"
                    + "       SUM(CASE WHEN u.[status] = 'banned' THEN 1 ELSE 0 END) AS BannedUserCount\n"
                    + "FROM [Role] r\n"
                    + "LEFT JOIN [User] u ON r.roleID = u.roleID\n"
                    + "GROUP BY r.roleName, u.isDeleted\n"
                    + "HAVING u.isDeleted = 'active'\n"
                    + "ORDER BY r.roleName;";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {

                    int totalUsers = rs.getInt("TotalUsers");
                    int activeUserCount = rs.getInt("ActiveUserCount");
                    int bannedUserCount = rs.getInt("BannedUserCount");
                    String rolename = rs.getString("roleName");

                    UserDTO user = new UserDTO();
                    user.setTotalUsers(totalUsers);
                    user.setTotalActiveUserCount(activeUserCount);
                    user.setTotalBannedUSerCount(bannedUserCount);
                    user.setRolename(rolename);
                    list.add(user);
                }
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public List<UserDTO> listStaff() {
        List<UserDTO> list = new ArrayList<UserDTO>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT u.firstName + ' ' + u.lastName as fullName, r.roleName\n"
                    + "FROM [User] u\n"
                    + "JOIN [Role] r ON u.roleID = r.roleID\n"
                    + "WHERE r.roleName != 'Customer' and u.isDeleted = 'active';";
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {

                    String rolename = rs.getString("roleName");
                    String fullName = rs.getString("fullName");
                    UserDTO user = new UserDTO();
                    user.setFullName(fullName);
                    user.setRolename(rolename);
                    list.add(user);
                }
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }
}
