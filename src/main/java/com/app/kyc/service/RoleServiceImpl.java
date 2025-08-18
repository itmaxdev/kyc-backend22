package com.app.kyc.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kyc.entity.Role;
import com.app.kyc.entity.User;
import com.app.kyc.repository.RoleRepository;


@Service
public class RoleServiceImpl implements RoleService
{

   @Autowired
   private RoleRepository roleRepository;

   public Role getRoleById(Long id) throws SQLException
   {
      return roleRepository.findById(id).get();
   }

   public Role getRoleByName(String name) throws SQLException
   {
      return roleRepository.findByName(name);
   }

   public Map<String, Object> getRoles(User user)
   {
      List<Role> roles = roleRepository.findAllByParentRoleId(user.getRole().getId());
      Map<String, Object> rolesWithCount = new HashMap<String, Object>();
      rolesWithCount.put("data", roles);
      rolesWithCount.put("count", roles.size());
      return rolesWithCount;
   }

   public void addRole(Role role) throws SQLException
   {
      roleRepository.save(role);
   }

   public Role updateRole(Role role) throws SQLException
   {
      return roleRepository.save(role);
   }

}
