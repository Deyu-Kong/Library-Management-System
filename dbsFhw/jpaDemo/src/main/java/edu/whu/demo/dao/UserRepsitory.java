package edu.whu.demo.dao;

import edu.whu.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jiaxy
 * 用户的JpaRepository接口
 */
public interface UserRepsitory extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    /**
     *  利用方法名表示查询条件的示例。
     *  根据Name查询
     */
    List<User> findByName(String name);

    /**
     *  利用方法名表示查询条件的示例。
     *  根据Name进行模糊查询
     */
    List<User> findByNameContaining(String name);

    /**
     *  利用方法名表示查询条件的示例。
     *  根据年龄范围查询
     */
    List<User> findByAgeBetween(int age1,int age2);


    /**
     * 使用JPQL查询。
     * 注意：按照实体类名称、字段写查询条件
     */
    @Query("select u from User u where name= ?1")
    List<User> findWithJPQLByName(String name);


    /**
     * 根据多个条件组合动态查询。参数为null时不做查询条件
     * @param name
     * @param age
     * @return
     */
    @Query("select u from User u where (?1 = null or name like %?1%) and  (?2 = null or age > ?2)")
    List<User> findWithJPQLByNameAndAge(String name,Integer age);


    /**
     * 使用SQL查询
     * 注意：按照实际的数据库表名、字段写查询条件
     */
    @Query(value = "select * from user where name like ?1",nativeQuery = true)
    List<User> findWithSQLByName(String name);


    /**
     * 根据name模糊查询，并分页
     * @param name 用户名
     * @param pageable
     * @return
     */
    Page<User> findByNameContaining(String name, Pageable pageable);

}
