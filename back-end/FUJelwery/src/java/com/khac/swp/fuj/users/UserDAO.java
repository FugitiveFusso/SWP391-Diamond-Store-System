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

/**
 *
 * @author phucu
 */
public class UserDAO {

    public UserDTO login(String username, String password, String rolename) {
        UserDTO user = null;
        try {

            Connection con = DBUtils.getConnection();
            String sql = " select userID, userName, firstName, lastName, email, phoneNumber, point, roleName from [User] u full join [Role] r on u.roleID = r.roleID ";
            sql += " WHERE roleName = ? AND password = ? AND userName = ?";

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
            String sql = " select userID, userName, firstName, lastName, email, address from [User] u full join [Role] r on u.roleID = r.roleID where roleName like ? ";
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

                    UserDTO user = new UserDTO();
                    user.setUserid(customerid);
                    user.setUsername(username);
                    user.setFirstname(firstname);
                    user.setLastname(lastname);
                    user.setEmail(email);
                    user.setAddress(address);
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

        String sql = "select userID, userName, firstName, lastName, email, phoneNumber, address, point from [User] u full join [Role] r on u.roleID = r.roleID where userID = ? and roleName like ?";

        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setString(2, roleName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                int customerid = rs.getInt("userID");
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
        String sql = "INSERT INTO [User] (userID, userName, password, firstName, lastName, phoneNumber, email, address, point, roleID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, user.getUserid());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFirstname());
            ps.setString(5, user.getLastname());
            ps.setString(6, user.getPhonenumber());
            ps.setString(7, user.getEmail());
            ps.setString(8, user.getAddress());
            ps.setInt(9, user.getPoint());
            ps.setInt(10, user.getRoleid());
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
        String sql = "UPDATE [User] SET userName = ?, firstName = ?, lastName = ?, phoneNumber = ?, email = ?, address = ?, point = ? WHERE userID = ? ";
        try {

            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(8, user.getUserid());
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());
            ps.setString(4, user.getPhonenumber());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getAddress());
            ps.setInt(7, user.getPoint());

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
        String sql = "DELETE [User] WHERE userID = ? ";
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
}