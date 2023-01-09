package edu.whu.demo.controller;

import edu.whu.demo.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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

    @ApiOperation("全局信息")
    @GetMapping("/global")
    public ResponseEntity<Map<String, Integer>> getGlobalInfo(){
        Map<String,Integer>map=new HashMap<>();
        map.put("bookCount",infoService.getBookCount());
        map.put("paperCount",infoService.getPaperCount());
        map.put("userCount", infoService.getUserCount());
        return ResponseEntity.ok(map);
    }

    @ApiOperation("查询特定用户的统计信息")
    @GetMapping("/user/{userName}")
    public ResponseEntity<Map<String, Integer>> getUserInfoByName(@PathVariable("userName") String userName){
        Map<String,Integer>map=new HashMap<>();
        map.put("buyCount",infoService.getBuyCount(userName));
        map.put("uploadCount", infoService.getUploadCount(userName));
        return ResponseEntity.ok(map);
    }

    @ApiOperation("图书统计信息")
    @GetMapping("/book")
    public ResponseEntity<Map<String, Object>> getBookInfo(){
        Map<String,Object>map=new HashMap<>();
        Map<String, List<String>> scoreDist = infoService.getScoreDist();
        map.put("xAxisData",scoreDist.get("xAxisData"));
        map.put("seriesData", scoreDist.get("seriesData"));
        return ResponseEntity.ok(map);
    }


    @ApiOperation("论文统计信息")
    @GetMapping("/paper")
    public ResponseEntity<Map<String, Object>> getPaperInfo(){
        Map<String,Object>map=new HashMap<>();
        Map<String, List<String>> uploaderRank = infoService.getUploaderRank();
        map.put("xAxisData",uploaderRank.get("xAxisData"));
        map.put("seriesData", uploaderRank.get("seriesData"));
        return ResponseEntity.ok(map);
    }

    @ApiOperation("用户统计信息")
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUserInfo(){
        Map<String,Object>map=new HashMap<>();
        List<Map<String, String>> list = infoService.getIdentityPie();
        map.put("identityPie",list);
        map.put("mname",infoService.getMaxBook().get("mname"));
        map.put("mid",infoService.getMaxBook().get("mid"));
        map.put("mcnt",infoService.getMaxBook().get("mcnt"));
        return ResponseEntity.ok(map);
    }

}
