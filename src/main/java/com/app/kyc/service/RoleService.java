package com.app.kyc.service;

import java.sql.SQLException;
import java.util.Map;

import com.app.kyc.entity.Role;
import com.app.kyc.entity.User;

public interface RoleService
{

   public Role getRoleById(Long id) throws SQLException;

   public Role getRoleByName(String name) throws SQLException;

   public Map<String, Object> getRoles(User user);

   public void addRole(Role role) throws SQLException;

   public Role updateRole(Role role) throws SQLException;

}
