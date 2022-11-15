package edu.whu.demo.dao;

import edu.whu.demo.DemoApplication;
import edu.whu.demo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = DemoApplication.class )
@Transactional
public class UserJAPRepositoryTest {
    @Autowired
    UserRepsitory userRepsitory;

    @BeforeEach
    void init(){
        userRepsitory.deleteAll();
        User user = new User();
        user.setName("黑马程序员");
        user.setPassword("itheima");
        user.setAge(12);
        user.setTel("4006184000");
        userRepsitory.save(user);

        User user2 = new User();
        user2.setName("黑马程序员2");
        user2.setPassword("itheima");
        user2.setAge(14);
        user2.setTel("4006184001");
        userRepsitory.save(user2);

        User user3 = new User();
        user3.setName("黑马程序员3");
        user3.setPassword("itheima");
        user3.setAge(14);
        user3.setTel("4006184002");
        userRepsitory.save(user3);

        User user4 = new User();
        user4.setName("黑马程序员4");
        user4.setPassword("itheima");
        user4.setAge(15);
        user4.setTel("4006184003");
        userRepsitory.save(user4);
//        List<User> result1 = userRepsitory.findAll(
//                (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%黑马%"));
    }

    /**
     * 测试利用方法名构造查询条件功能
     */
    @Test
    public void testFindWithMethodName(){
        List<User> result = userRepsitory.findByName("黑马程序员3");
        assertNotNull(result);
        assertEquals("黑马程序员3",result.get(0).getName());

        result = userRepsitory.findByNameContaining("黑马程序员");
        assertNotNull(result);
        assertEquals(4,result.size());

        result = userRepsitory.findByAgeBetween(14,15);
        assertNotNull(result);
        assertEquals(3,result.size());
    }


    /**
     * 使用JPQL进行查询
     * 关于JPQL更多资料: https://www.yiibai.com/jpa/jpa_jpql.html
     */
    @Test
    public void testJPQL(){
        List<User> result = userRepsitory.findWithJPQLByName("黑马程序员3");
        assertNotNull(result);
        assertEquals("黑马程序员3",result.get(0).getName());

        //根据name和age动态查询。age为null时候，只查name
        result=userRepsitory.findWithJPQLByNameAndAge("黑马",null);
        assertNotNull(result);
        assertEquals(4,result.size());
        //根据name和age动态查询。age不为null时候，作为查询条件
        result=userRepsitory.findWithJPQLByNameAndAge("黑马",14);
        assertNotNull(result);
        assertEquals(1,result.size());

    }

    /**
     * 使用SQL进行查询
     */
    @Test
    public void testSQL(){
        List<User> result = userRepsitory.findWithSQLByName("黑马程序员3");
        assertNotNull(result);
        assertEquals("黑马程序员3",result.get(0).getName());
    }

    /**
     * 分页查询与排序
     */
    @Test
    public void testPagingAndSorting(){
        //查询第1页，每页3条
        Pageable pageable = PageRequest.of(0,3);
        Page<User> result = userRepsitory.findByNameContaining("黑马", pageable);
        assertEquals(4,result.getTotalElements()); //总数量
        assertEquals(2,result.getTotalPages()); //总页数
        List<User> users = result.getContent(); //查询返回的结果
        assertEquals(3,users.size());

        //查询第2页，每页3条（最多返回3条，不够3条按照实际数返回）
        result = userRepsitory.findByNameContaining("黑马", PageRequest.of(1,3));
        assertEquals(1,result.getContent().size());

        //按年龄升序
        pageable = PageRequest.of(0,3,Sort.by("age"));
        result = userRepsitory.findByNameContaining("黑马", pageable);
        users = result.getContent();
        assertEquals(3,users.size());
        assertTrue(users.get(0).getAge()<=users.get(1).getAge()
                &&users.get(1).getAge()<=users.get(2).getAge() );

        //按年龄降序
        pageable = PageRequest.of(0,3,Sort.by(Sort.Direction.DESC,"age"));
        result = userRepsitory.findByNameContaining("黑马", pageable);
        users = result.getContent();
        assertEquals(3,users.size());
        assertTrue(users.get(0).getAge()>=users.get(1).getAge()
                &&users.get(1).getAge()>=users.get(2).getAge() );

    }

    /**
     * 使用Specification查询
     */
    @Test
    public void testQueryWithSpecification(){//String name,Integer age
        //使用Specification进行查询
        List<User> result1 = userRepsitory.findAll(
                (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%黑马%"));
        assertEquals(4,result1.size());

        //组合查询
        List<User> result2 = userRepsitory.findAll( (root, query, cb) ->
               cb.and(cb.like(root.get("name"), "%黑马%"),cb.ge(root.get("age"),14)));
        assertEquals(3,result2.size());

        //使用Specification进行分页查询
        PageRequest pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "age"));
        Page<User> result4 = userRepsitory.findAll(
                (root, q, cb) -> cb.like(root.get("name"), "%黑马%")
                ,pageable);
        assertEquals(3,result4.getContent().size());



    }


    /**
     * 使用Specification构建动态查询语句。
     * 该测试用例使用了参数化测试，可以为测试传入不同的参数来启动多次测试。
     */
    @ParameterizedTest
    @CsvSource({
            "'黑马',14, 3",
            "'黑马',,4",
            ",14,3"
    })
    public void testQueryWithSpecification2(String name,Integer age, int count){
        //动态组合查询，如果某个属性不为null，则作为查询条件
        List<User> result3 = userRepsitory.findAll( (root, query, cb) ->{
            List<Predicate> pList=new ArrayList<>();
            if(name != null) {pList.add(cb.like(root.get("name"), "%"+name+"%"));}
            if(age != null) {pList.add( cb.ge(root.get("age"),14));}
            return cb.and(pList.toArray(new Predicate[pList.size()]));
        });
        assertEquals(count,result3.size());
    }







}
