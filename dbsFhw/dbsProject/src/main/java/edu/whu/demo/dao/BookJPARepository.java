package edu.whu.demo.dao;

import edu.whu.demo.entity.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 孔德昱
 * book的JpaRepository接口
 */
public interface BookJPARepository extends JpaRepository<BookItem, Long>,JpaSpecificationExecutor<BookItem> {

}
