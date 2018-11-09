package com.deliver.controller;

import com.deliver.entity.HumanInfo;
import com.deliver.entity.HumanMedia;
import com.deliver.entity.MacInfo;
import com.deliver.service.HumanInfoService;
import com.deliver.service.HumanMediaService;
import com.deliver.service.MacInfoService;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by pdl on 2018/9/20.
 */
@Controller
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private HumanInfoService humanInfoService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MacInfoService macInfoService;

    @Autowired
    private HumanMediaService humanMediaService;

    /**
     * 人员登录
     */
    @RequestMapping(value="/login")
    @ResponseBody
    public ResultInfo login(HttpServletRequest request, @RequestBody Map<String,Object> human) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        HttpSession session = request.getSession();
        String tel = (String)human.get("tel");
        String password = (String)human.get("password");
        String macName = (String)human.get("macName");
        String clientID = (String)human.get("clientID");
        Integer phoneFlag = (Integer)human.get("phoneFlag");
        List<HumanInfo> humanlist = humanInfoService.findByTelAndPassword(tel,password);
        HumanInfo humanInfo =null;
        if(humanlist!=null && humanlist.size()>0){
            humanInfo = humanlist.get(0);
        }

        if(phoneFlag!=null && phoneFlag==1){
            MacInfo macInfo= macInfoService.findByMacName(macName);
            if(macInfo==null){
                resultInfo.setCode(400);
                resultInfo.setMessage("登陆失败！该机器未授权！");
                resultInfo.setSuccess(false);
            }
        }

        if(humanInfo!=null){
            if(humanInfo.getHumanType()==0){
                resultInfo.setCode(400);
                resultInfo.setMessage("登陆失败！学生不允许登录！");
                resultInfo.setSuccess(false);
                return resultInfo;
            }
            if(humanInfo.getCheckFlag()==0){
                resultInfo.addData("human",humanInfo);
                resultInfo.setCode(200);
                resultInfo.setMessage("审核等待中！");
                resultInfo.setSuccess(true);
            }else if(humanInfo.getCheckFlag()==2){
                resultInfo.addData("human",humanInfo);
                resultInfo.setCode(200);
                resultInfo.setMessage("审核未通过！");
                resultInfo.setSuccess(true);
            }
            session.setAttribute("humanid",humanInfo.getHumanID());
            humanInfo.setOnline(1);
            if(clientID!=null && !clientID.equals("")){
                humanInfo.setClientID(clientID);
            }


            humanInfoService.save(humanInfo);
            List<HumanMedia> mediaList = humanMediaService.findByHumanIDAndDeleteFlag(humanInfo.getHumanID(),0);

            resultInfo.setCode(200);
            resultInfo.setMessage("登陆成功！");
            resultInfo.setSuccess(true);
            resultInfo.addData("human",humanInfo);
            resultInfo.addData("media",mediaList);
        }else{
            resultInfo.setCode(400);
            resultInfo.setMessage("登陆失败！手机或密码不正确！");
            resultInfo.setSuccess(false);
        }

        return resultInfo;
    }
}
