package com.deliver.controller;

import com.deliver.entity.HumanInfo;
import com.deliver.service.HumanInfoService;
import com.deliver.service.PareStudentRelService;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by pdl on 2018/10/11.
 */
@Controller
@RequestMapping("/atschool")
public class AtSchoolController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PareStudentRelService pareStudentRelService;

    @Autowired
    private HumanInfoService humanInfoService;

    /**
     * * 老师初始化在位人员信息
     */
    @RequestMapping(value = "/teacherfindatschool")
    @ResponseBody
    public ResultInfo teacherFindAtSchool(HttpServletRequest request, @RequestBody Map<String, Object> jsonMap) throws Exception {
        int humanid = (Integer) jsonMap.get("humanID");
        HumanInfo human = humanInfoService.findByHumanID(humanid);
        List<Map<String, Object>> atSchoolHumanlist=null;
        List<Map<String, Object>> noatSchoolHumanlist=null;
        if(human.getManagerType()==3){
            atSchoolHumanlist= humanInfoService.teacherFindAtSchool(human);
            noatSchoolHumanlist= humanInfoService.teacherFindNoAtSchool(human);
        }
        ResultInfo resultInfo = new ResultInfo(false);
        resultInfo.addData("atSchoolHumanlist",atSchoolHumanlist);
        resultInfo.addData("noatSchoolHumanlist",noatSchoolHumanlist);
        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("查询成功");
        return resultInfo;
    }
}
