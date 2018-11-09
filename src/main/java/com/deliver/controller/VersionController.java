package com.deliver.controller;

import com.deliver.entity.HumanInfo;
import com.deliver.entity.HumanMedia;
import com.deliver.entity.MacInfo;
import com.deliver.entity.VersionInfo;
import com.deliver.service.HumanInfoService;
import com.deliver.service.HumanMediaService;
import com.deliver.service.MacInfoService;
import com.deliver.service.VersionInfoService;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by pdl on 2018/9/20.
 */
@Controller
@RequestMapping("/version")
public class VersionController {
    @Autowired
    private VersionInfoService versionInfoService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 检测版本更新接口
     */
    @RequestMapping(value="/queryupdate")
    @ResponseBody
    public ResultInfo queryUpdate(HttpServletRequest request, @RequestBody VersionInfo versionInfo) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        resultInfo = versionInfoService.queryUpdate(versionInfo);
        return resultInfo;
    }

    /**
     * 版本更新接口
     */
    @RequestMapping(value="/addversion")
    @ResponseBody
    public ResultInfo addVersion(@RequestBody VersionInfo versionInfo) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        versionInfoService.save(versionInfo);
        resultInfo.setCode(200);
        resultInfo.setMessage("上传成功！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }
}
