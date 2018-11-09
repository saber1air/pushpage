package com.deliver.controller;

import com.deliver.Post;
import com.deliver.service.PostService;
import com.deliver.util.ResultInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by pdl on 2018/9/13.
 */
@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/query/{id}")
    @ResponseBody
    public Object query(@PathVariable int id) {
        return postService.findById(id);
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(@ModelAttribute Post post) {
        return postService.save(post);
    }

    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable int id) {
        return postService.delete(id);
    }

    @RequestMapping("/queryPage")
    public Object query(String name, int pageNum, int count) {
        //根据weight倒序分页查询
//        Pageable pageable = new PageRequest(pageNum, count, Sort.Direction.DESC, "weight");
//        return userRepository.findByName(name, pageable);
        return null;
    }

    @RequestMapping("/findall")
    @ResponseBody
    public ResultInfo findall(String content){
        ResultInfo resultInfo =  new ResultInfo(false);
        String sql = "select * from tc_post where content="+content;
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        resultInfo.addData("list",list);
        resultInfo.addData("dd","dd");
        return resultInfo;
    }



}
