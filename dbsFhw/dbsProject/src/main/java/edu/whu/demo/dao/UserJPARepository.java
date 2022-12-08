package edu.whu.demo.dao;
import edu.whu.demo.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserJPARepository extends JpaRepository<UserItem, Long>, JpaSpecificationExecutor<UserItem> {

}
