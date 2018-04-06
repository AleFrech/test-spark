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
        String sql = "select ID, NAME, EMAIL, Salary, StartingDate, DepartmentId, RoleId\n"
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
        String sql = "select ID, NAME, EMAIL, Salary, StartingDate, DepartmentId, RoleId\n"
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
    
}
