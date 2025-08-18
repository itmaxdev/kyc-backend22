package com.app.kyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.kyc.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
   @Query(value = "SELECT * FROM roles WHERE name = ?", nativeQuery = true)
   Role findByName(@Param("name") String name);

   List<Role> findAllByParentRoleId(Long id);

}
