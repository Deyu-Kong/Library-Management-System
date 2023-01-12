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

    @Query(value="SELECT t1.counts, t2.user_name FROM(SELECT COUNT(*) AS counts," +
            "paper_uploader_id AS uploader " +
            "FROM paper_item GROUP BY uploader ORDER BY counts DESC " +
            "LIMIT 10) AS t1, user_item AS t2 " +
            "WHERE t1.uploader=t2.user_id ORDER BY t1.counts DESC;",nativeQuery=true)
    List<Object[]> getUploaderRank();

    @Query(value="SELECT t1.counts, t2.user_name FROM(SELECT count(*) AS counts, " +
            "user_id FROM buyer_item GROUP BY user_id " +
            "ORDER BY counts DESC LIMIT 10) AS t1, user_item AS t2 " +
            "WHERE t1.user_id=t2.user_id ORDER BY t1.counts DESC ",nativeQuery=true)
    List<Object[]> getUserBuyBar();

    @Query(value="SELECT COUNT(*) AS counts, publisher_name " +
            "FROM book_item " +
            "GROUP BY publisher_name " +
            "ORDER BY counts DESC " +
            "LIMIT 20;",nativeQuery=true)
    List<Object[]> getPublisherBar();

    @Query(value="SELECT COUNT(*) AS counts, YEAR(publication_date) AS `year` " +
            "FROM book_item " +
            "GROUP BY year " +
            "HAVING year>=1970 " +
            "ORDER BY year ", nativeQuery=true)
    List<Object[]> getPublishYear();

    @Query(value = "SELECT book_name,cnt FROM" +
            "(SELECT COUNT(*) AS cnt ,book_id FROM buyer_item GROUP BY book_id)AS a" +
            ",book_item WHERE a.book_id=book_item.id ORDER BY cnt DESC",nativeQuery=true )
//    @Query(value = "SELECT COUNT  book_name FROM book_item,buyer_item WHERE book_item.id=buyer_item.book_id", nativeQuery=true)
    List<Object[]>getBuyerBooks();
}
