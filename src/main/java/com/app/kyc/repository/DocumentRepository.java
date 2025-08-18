package com.app.kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.kyc.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>
{
   @Query(value = "SELECT * FROM documents WHERE name = ?", nativeQuery = true)
   Document findByName(@Param("name") String name);

}
