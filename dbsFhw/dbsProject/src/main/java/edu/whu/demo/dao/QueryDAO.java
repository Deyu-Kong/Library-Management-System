package edu.whu.demo.dao;


import edu.whu.demo.entity.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 孔德昱
 * @date 2023/1/8 16:00 星期日
 */
@Repository
public interface QueryDAO extends JpaRepository<BookItem, Long>, JpaSpecificationExecutor<BookItem> {
    @Query(value = "SELECT COUNT(*) FROM buyer_item " +
            "WHERE user_id IN " +
            "(SELECT user_id FROM user_item WHERE user_name=:userName); ",
            nativeQuery = true)
    Integer getBuyCountByUserName(String userName);

    @Query(value = "SELECT COUNT(*) FROM paper_item " +
            "WHERE paper_uploader_id IN " +
            "(SELECT user_id FROM user_item WHERE user_name=:userName); ",
            nativeQuery = true)
    Integer getUploadCountByUserName(String userName);

    @Query(value="SELECT COUNT(*) AS counts,FLOOR(rating) AS rates " +
            "FROM book_item " +
            "GROUP BY FLOOR(rating) " +
            "ORDER BY FLOOR(rating) DESC;",nativeQuery = true)
    List<Object[]> getScoreDist();

    @Query(value="SELECT COUNT(*) AS counts, user_identity " +
            "FROM user_item " +
            "GROUP BY user_identity " +
            "ORDER BY counts DESC;",nativeQuery = true)
    List<Object[]> getIdentityPie();

    @Query(value="SELECT u.user_name,id,mcnt FROM (SELECT user_id as id,count(buyer_id) as mcnt FROM buyer_item " +
            "GROUP BY user_id HAVING mcnt >=ALL(SELECT count(buyer_id) FROM buyer_item GROUP BY user_id)) AS a, " +
            "user_item AS u WHERE u.user_id=id;"
            ,nativeQuery=true)
    List<Object[]> getMaxBook();
}
