package edu.whu.demo.service;


import edu.whu.demo.dao.UserJPARepository;
import edu.whu.demo.entity.BookItem;
import edu.whu.demo.entity.PaperItem;
import edu.whu.demo.entity.UserItem;
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
public class UserService {
    @Autowired
    UserJPARepository userRepository;
    public UserItem addUser(UserItem user) {
        return userRepository.saveAndFlush(user);
    }
    public void deleteUser(long id) {userRepository.deleteById(id);}
    public UserItem getUser(long id) {
        return userRepository.getById(id);
    }
    public void updateUser(long id, UserItem user) {
        userRepository.save(user);
    }
    public List<UserItem> findUsers(String userName,String userIdentity) {
        //动态构造查询条件，name和complete不为null时作为条件
        Specification<UserItem> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (userName != null) {
                predicateList.add(criteriaBuilder.like(root.get("userName"), "%" + userName + "%"));
            }
            if (userIdentity != null) {
                predicateList.add(criteriaBuilder.equal(root.get("userIdentity"), userIdentity));
            }
            Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
            return criteriaBuilder.and(predicates);
        };

        List<UserItem> result = userRepository.findAll(specification);
        return result;
    }
}
