package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author "zhouhai"
 * @Date2019/1/920:11
 **/
@RestController
@CrossOrigin
@RequestMapping("/label")
@RefreshScope
public class LableController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private HttpServletRequest request;

    @Value("${hehe}")
    private String hehe;

    @GetMapping
    public Result findAll() {
        //获取头信息
        String authorization = request.getHeader("Authorization");


        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());

    }

    @GetMapping(value="/{labelId}")
    public Result findById(@PathVariable("labelId")String lableId) {

        return new Result(true, StatusCode.OK,"查询成功",labelService.findById(lableId));
    }

    @PostMapping
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @DeleteMapping("/{lableId}")
    public Result deleteById(@PathVariable String lableId) {
        labelService.deleteById(lableId);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @PutMapping("/{lableId}")
    public Result update(@PathVariable String lableId,@RequestBody Label label) {
        label.setId(lableId);
        labelService.update(label);
        return new Result(true, StatusCode.OK,"更新成功");
    }

    @PostMapping("search")
    public Result findSearch(@RequestBody Label label) {

        List<Label> list = labelService.findSearch(label);

        return new Result(true, StatusCode.OK, "查询成功");
    }

    @PostMapping("/search/{page}/{size}")
    public Result pageQuery(@RequestBody Label label,@PathVariable int page,@PathVariable int size) {
        Page<Label> pageDate = labelService.pageQuery(label,page,size);
        return new Result(true, StatusCode.OK, "查询成功",new PageResult<Label>(pageDate.getTotalElements(),pageDate.getContent()));
    }

}
