package edu.whu.demo.controller;

import edu.whu.demo.entity.PaperItem;
import edu.whu.demo.service.PaperService;
import edu.whu.demo.service.PaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * @author 孔德昱
 * @date 2022/12/8 11:34 星期四
 */
@Api(description = "论文管理器")
@RestController
@RequestMapping("papers")
public class PaperController {
    @Autowired
    PaperService paperService;

    // get: localhost:8088/papers/1
    @ApiOperation("根据Id查询论文")
    @GetMapping("/{id}")
    public ResponseEntity<PaperItem> getpaper(@ApiParam("论文Id")@PathVariable long id){
        PaperItem result = paperService.getPaper(id);
        if(result==null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    // get: localhost:8088/papers
    // get: localhost:8088/papers?name=作业
    // get: localhost:8088/papers?name=作业&&complete=true
    @ApiOperation("根据条件查询论文")
    @GetMapping("")
    public ResponseEntity<List<PaperItem>> findpapers(@ApiParam("论文名称")String name, @ApiParam("发表日期") Date paperDate, @ApiParam("作者")String author,
                                                      @ApiParam("上传者")String uploader, @ApiParam("上传日期")Date uploadDate){
        List<PaperItem> result = paperService.findPapers(name, paperDate, author, uploader, uploadDate);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("添加论文")
    @PostMapping("")
    public ResponseEntity<String> addpaper(@RequestBody PaperItem paper){
        try {
            PaperItem result = paperService.addPaper(paper);
            return ResponseEntity.ok(""+result.getPaperId());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @ApiOperation("修改论文")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatepaper(@PathVariable long id,@RequestBody PaperItem paper){
        paperService.updatePaper(id,paper);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("删除论文")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletepaper(@PathVariable long id){
        paperService.deletePaper(id);
        return ResponseEntity.ok().build();
    }
}
