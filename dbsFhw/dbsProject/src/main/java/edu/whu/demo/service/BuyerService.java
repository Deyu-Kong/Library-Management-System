package edu.whu.demo.service;

import edu.whu.demo.dao.BuyerJPARepository;
import edu.whu.demo.entity.BuyerItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dzf
 * @date 2022/12/8 15:55
 */

@Service
public class BuyerService {
    @Autowired
    BuyerJPARepository buyerRepository;

    public BuyerItem addBuyer(BuyerItem buyer) {
        return buyerRepository.saveAndFlush(buyer);
    }

    public BuyerItem getBuyer(long id) {
        return buyerRepository.getById(id);
    }


    public void updateBuyer(long id, BuyerItem buyer) {
        buyerRepository.save(buyer);
    }

    public void deleteBuyer(long id) {
        buyerRepository.deleteById(id);
    }

    public List<BuyerItem> findBuyers(Long bookId, Long userId) {
        Specification<BuyerItem> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (bookId != null) {
                predicateList.add(criteriaBuilder.equal(root.get("bookId"), bookId));
            }
            if (userId != null) {
                predicateList.add(criteriaBuilder.equal(root.get("userId"), userId));
            }

            Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
            return criteriaBuilder.and(predicates);
        };

        List<BuyerItem> result = buyerRepository.findAll(specification);
        return result;
    }

}
