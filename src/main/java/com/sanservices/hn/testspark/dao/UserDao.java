/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanservices.hn.testspark.dao;

import com.sanservices.hn.testspark.dto.UserDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author afrech
 */
public class UserDao {
    
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }
    
    public ArrayList<UserDto> getUsers() throws SQLException {
        String sql = "select ID, Name, Email, Salary, StartingDate, DepartmentId, RoleId\n"
                + "from crud.Users";
        ArrayList<UserDto> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    UserDto user = new UserDto();
                    user.setId(result.getInt("ID"));
                    user.setName(result.getString("NAME"));
                    user.setEmail(result.getString("EMAIL"));
                    user.setSalary(result.getBigDecimal("Salary"));
                    user.setStartingDate(result.getDate("StartingDate"));
                    user.setDepartmentId(result.getInt("DepartmentId"));
                    user.setRoleId(result.getInt("RoleId"));
                    users.add(user);
                }
                return users;
            }
        }
    }
    
    
    public UserDto getUser(int userId) throws SQLException {
        String sql = "select ID, Name, Email, Password, Salary, StartingDate, DepartmentId, RoleId\n"
                + "from crud.Users\n"
                + "where ID =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    UserDto user = new UserDto();
                    user.setId(result.getInt("ID"));
                    user.setName(result.getString("NAME"));
                    user.setEmail(result.getString("EMAIL"));
                    user.setPassword(result.getString("Password"));
                    user.setSalary(result.getBigDecimal("Salary"));
                    user.setStartingDate(result.getDate("StartingDate"));
                    user.setDepartmentId(result.getInt("DepartmentId"));
                    user.setRoleId(result.getInt("RoleId"));
                    return user;
                }
                return null;
            }
        }
    }
    
    public UserDto getUser(String email) throws SQLException {
        String sql = "select ID, Name, Email, Password,  Salary, StartingDate, DepartmentId, RoleId, Salt\n"
                + "from crud.Users\n"
                + "where EMAIL = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    UserDto user = new UserDto();
                    user.setId(result.getInt("ID"));
                    user.setName(result.getString("NAME"));
                    user.setEmail(result.getString("EMAIL"));
                    user.setPassword(result.getString("Password"));
                    user.setSalary(result.getBigDecimal("Salary"));
                    user.setStartingDate(result.getDate("StartingDate"));
                    user.setDepartmentId(result.getInt("DepartmentId"));
                    user.setRoleId(result.getInt("RoleId"));
                    user.setSalt(result.getString("Salt"));
                    return user;
                }
                return null;
            }
        }
    }
    
    public boolean insertUser(UserDto user) throws SQLException {
        String sql = "INSERT INTO crud.Users (Name, Salary, StartingDate, Email, Password, "
                + "DepartmentId, RoleId, Salt)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setBigDecimal(2, user.getSalary());
            statement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setInt(6, user.getDepartmentId());
            statement.setInt(7, user.getRoleId());
            statement.setString(8, user.getSalt());
            return statement.executeUpdate() > 0;
        }
    }
    
    public boolean updateUser(UserDto user) throws SQLException {
        String sql = "UPDATE crud.Users "
                + "SET Name = ?, "
                + "Salary = ?, "
                + "Email = ?, "
                + "DepartmentId = ?, "
                + "RoleId = ? "
                + "WHERE ID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setBigDecimal(2, user.getSalary());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getDepartmentId());
            statement.setInt(5, user.getRoleId());
            statement.setInt(6, user.getId());
            return statement.executeUpdate() > 0;
        }
    }
    
    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM crud.Users "
                + "WHERE ID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id);
            return statement.executeUpdate() > 0;
        }
    }
    
    
}
