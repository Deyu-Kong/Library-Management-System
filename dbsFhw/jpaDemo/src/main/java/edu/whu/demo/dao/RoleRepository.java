package edu.whu.demo.dao;

import edu.whu.demo.entity.Role;
import edu.whu.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role,Long> {

    @Modifying
    @Query(value = "INSERT INTO user_roles (user_id,roles_id) VALUES (?1,?2)",nativeQuery = true)
    public void addUserRole(long userId,long roleId);

}
