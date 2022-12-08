package edu.whu.demo.dao;

import edu.whu.demo.entity.BuyerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author dzf
 * @date 2022/12/8 15:55
 */

public interface BuyerJPARepository extends JpaRepository<BuyerItem, Long>, JpaSpecificationExecutor<BuyerItem> {

}
