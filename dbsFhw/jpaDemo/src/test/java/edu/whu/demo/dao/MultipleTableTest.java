package edu.whu.demo.dao;

import edu.whu.demo.DemoApplication;
import edu.whu.demo.entity.Role;
import edu.whu.demo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@SpringBootTest(classes = DemoApplication.class )

public class MultipleTableTest {


    @Autowired
    UserRepsitory userRepsitory;

    @Autowired
    RoleRepository roleRepository;


    @BeforeEach
    void init(){
        userRepsitory.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void testAddandQuery(){
        //添加角色到数据库
        Role role1=new Role();
        role1.setName("管理员");
        role1.setRemark("系统管理员");
        role1 = roleRepository.save(role1);
        Role role2=new Role();
        role2.setName("普通用户");
        role2.setRemark("普通用户");
        role2 = roleRepository.save(role2);

        //添加一个用户及其角色
        User user = new User();
        user.setName("黑马程序员");
        user.setPassword("itheima");
        user.setAge(12);
        user.setTel("4006184000");
        List<Role> roleList=new ArrayList<>();
        roleList.add(role1);
        user.setRoles(roleList);
        User userInDB = userRepsitory.save(user);
//
        //查询用户，可以自动包含角色信息
        userInDB=userRepsitory.getById(userInDB.getId());
        assertNotNull(userInDB.getRoles());
        assertEquals(1,(userInDB.getRoles().size()));
//
//        //修改用户角色
//        roleList=new ArrayList<>();
//        roleList.add(role1);
//        roleList.add(role2);
//        userInDB.setRoles(roleList);//现在两个角色
//        userRepsitory.save(userInDB);
//
//        //查询用户，可以自动包含角色信息
//        userInDB=userRepsitory.getById(userInDB.getId());
//        assertNotNull(userInDB.getRoles());
//        assertEquals(2,userInDB.getRoles().size());

        userRepsitory.deleteById(userInDB.getId());

    }




}
