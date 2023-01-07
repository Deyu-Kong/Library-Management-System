package edu.whu.demo.dao;
import edu.whu.demo.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface UserJPARepository extends JpaRepository<UserItem, Long>, JpaSpecificationExecutor<UserItem> {
    @Query(value="select count(user_id) from user_item",nativeQuery=true)
    public Integer getUserCount();
}
