package edu.whu.demo.controller;



import edu.whu.demo.entity.BookItem;
import edu.whu.demo.entity.BuyerItem;
import edu.whu.demo.entity.UserItem;
import edu.whu.demo.service.BuyerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * @author dzf
 * @date 2022/12/8 15:55
 */
@Api(description = "买书管理器")
@RestController
@RequestMapping("buyers")
public class BuyerController {
    @Autowired
    BuyerService buyerService;
    // get: localhost:8088/books/1
    @ApiOperation("根据Id查询图书")
    @GetMapping("/{id}")
    public ResponseEntity<BuyerItem> getBuyer(@ApiParam("买书id")@PathVariable long id){
        BuyerItem result = buyerService.getBuyer(id);
        if(result==null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }
    @ApiOperation("根据条件查询买书")
    @GetMapping("")
    public ResponseEntity<List<BuyerItem>> findbuyers(@ApiParam("图书id")Long bookID, @ApiParam("买书人id") Long userID){
        List<BuyerItem> result = buyerService.findBuyers(bookID, userID);
        return ResponseEntity.ok(result);
    }
    @ApiOperation("添加买书")
    @PostMapping("")
    public ResponseEntity<String> addbuyer(@RequestBody BuyerItem buyer){
        try {
            BuyerItem result = buyerService.addBuyer(buyer);
            return ResponseEntity.ok(""+result.getBuyerId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @ApiOperation("修改买书")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatebuyer(@PathVariable long id,@RequestBody BuyerItem buyer){
        buyerService.updateBuyer(id,buyer);
        return ResponseEntity.ok().build();
    }
    @ApiOperation("删除买书")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletebuyer(@PathVariable long id){
        buyerService.deleteBuyer(id);
        return ResponseEntity.ok().build();
    }
}
