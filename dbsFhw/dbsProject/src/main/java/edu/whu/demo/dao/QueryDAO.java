package edu.whu.demo.dao;


import edu.whu.demo.entity.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author 孔德昱
 * @date 2023/1/8 16:00 星期日
 */
@Repository
public interface QueryDAO extends JpaRepository<BookItem, Long>, JpaSpecificationExecutor<BookItem> {
    @Query(value = "SELECT COUNT(*) FROM buyer_item WHERE user_id IN (SELECT user_id FROM user_item WHERE user_name=:userName); ",nativeQuery = true)
    Integer getBuyCountByUserName(String userName);

    @Query(value = "SELECT COUNT(*) FROM paper_item WHERE paper_uploader_id IN (SELECT user_id FROM user_item WHERE user_name=:userName); ",nativeQuery = true)
    Integer getUploadCountByUserName(String userName);
}
