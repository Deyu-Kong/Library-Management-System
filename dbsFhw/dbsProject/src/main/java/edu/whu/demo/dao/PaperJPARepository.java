package edu.whu.demo.dao;

import edu.whu.demo.entity.PaperItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 孔德昱
 * @date 2022/12/8 11:29 星期四
 */
public interface PaperJPARepository extends JpaRepository<PaperItem, Long>, JpaSpecificationExecutor<PaperItem> {

}
