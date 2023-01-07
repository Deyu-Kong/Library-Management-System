package edu.whu.demo.controller;

import edu.whu.demo.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 孔德昱
 * @date 2023/1/7 19:43 星期六
 */
@Api(description = "统计信息")
@RestController
@RequestMapping("info")
public class InfoController {

    @Autowired
    InfoService infoService;

    @ApiOperation("根据Id查询图书")
    @GetMapping("/global")
    public ResponseEntity<Map<String, Integer>> getGlobalInfo(){
        Map<String,Integer>map=new HashMap<>();
        map.put("bookCount",infoService.getBookCount());
        map.put("paperCount",infoService.getPaperCount());
        map.put("userCount", infoService.getUserCount());
        return ResponseEntity.ok(map);
    }

}
