package org.upgrad.repositories;


import org.upgrad.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(nativeQuery = true,value="SELECT email FROM USERS WHERE UPPER(email) = UPPER (?1)")
    String findByEmail(String email);

    @Query(nativeQuery = true,value="SELECT userName FROM USERS WHERE UPPER(userName) = UPPER (?1)")
    String findByUserName(String userName);

    @Query(nativeQuery = true,value="SELECT PASSWORD FROM USERS WHERE UPPER(userName) = UPPER (?1)")
    String findUserPassword(String userName);

    @Query(nativeQuery = true,value="SELECT id FROM USERS WHERE UPPER(userName) = UPPER (?1)")
    Integer findUserId(String userName);

    @Query(nativeQuery = true,value="SELECT ROLE FROM USERS WHERE UPPER(userName) = UPPER (?1)")
    String findUserRole(String userName);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO USERS (userName,EMAIL,PASSWORD,role) VALUES (?1,?2,?3,?4)")
    void save(String userName,String email, String password,String role);

}

