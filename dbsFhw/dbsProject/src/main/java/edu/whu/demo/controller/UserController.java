package edu.whu.demo.controller;

import edu.whu.demo.entity.UserItem;
import edu.whu.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
@Api(description = "用户管理器")
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    // get: localhost:8088/users/1
    @ApiOperation("根据Id查询用户")
    @GetMapping("/{id}")
    public ResponseEntity<UserItem> getUser(@ApiParam("用户Id")@PathVariable long id){
        UserItem result = userService.getUser(id);
        if(result==null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    // get: localhost:8088/users
    // get: localhost:8088/users?name=user1
    // get: localhost:8088/users?name=user1&&identity=学生
    @ApiOperation("根据条件查询用户")
    @GetMapping("")
    public ResponseEntity<List<UserItem>> findUsers(@ApiParam("用户名称")String userName, @ApiParam("用户身份") String userIdentity){
        List<UserItem> result = userService.findUsers(userName, userIdentity);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("添加用户")
    @PostMapping("")
    public ResponseEntity<String> adduser(@RequestBody UserItem user){
        try {
            UserItem result = userService.addUser(user);
            return ResponseEntity.ok(""+result.getUserId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @ApiOperation("修改用户")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateuser(@PathVariable long id,@RequestBody UserItem user){
        userService.updateUser(id,user);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteuser(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
