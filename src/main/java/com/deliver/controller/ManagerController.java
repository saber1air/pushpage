package com.deliver.controller;

import com.deliver.entity.*;
import com.deliver.mapbody.AddHumanParam;
import com.deliver.mapbody.CheckHumanParam;
import com.deliver.mapbody.HumanParam;
import com.deliver.service.*;
import com.deliver.util.*;
import net.sf.json.JSONArray;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by pdl on 2018/9/18.
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {


    @Autowired
    private HumanInfoService humanInfoService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PareStudentRelService pareStudentRelService;

    @Autowired
    private HumanRegisterRecordService humanRegisterRecordService;

    @Autowired
    private ManagerRegisterRecordService managerRegisterRecordService;

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private GradeInfoService gradeInfoService;
    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    private HumanMediaService humanMediaService;

    @Autowired
    private MacInfoService macInfoService;

    @Value("${cbs.imagesPath}")
    private String mImagesPath;


    /**
     * * pc端初始化管理页面
     */
    @RequestMapping(value = "/initpc")
    @ResponseBody
    public ResultInfo initPc(HttpServletRequest request, @RequestBody Map<String, Object> jsonMap) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        Integer humanID = (Integer) jsonMap.get("humanid");
        if(humanID==null){
            resultInfo.setSuccess(false);
            resultInfo.setMessage("humanid为空！");
            resultInfo.setCode(400);
            return resultInfo;
        }
        HumanInfo human = humanInfoService.findByHumanID(humanID);
        resultInfo.addData("school", null);
        List<School> schoollist = new ArrayList<School>();
        if (human != null) {
            if (human.getManagerType()!=null && human.getManagerType()!=-1 && (human.getManagerType() == 2
                    || human.getManagerType() == 3 || human.getManagerType() == 4)) {
                School school = schoolService.findBySchoolIDAndDeleteFlag(human.getSchoolID(), 0);
                if (school != null) {
                    List<GradeInfo> gradeInfolist = gradeInfoService.findBySchoolID(school.getSchoolID());
                    List<MacInfo> macInfolist = macInfoService.findlistBySchoolID(school.getSchoolID());

                    if (gradeInfolist != null && gradeInfolist.size() > 0) {
                        for (GradeInfo gradeInfo : gradeInfolist) {
                            List<ClassInfo> classInfoList = classInfoService.findByGradeID(gradeInfo.getGradeID());
                            gradeInfo.setClassInfoList(classInfoList);
                        }
                    }
                    school.setGradeInfo(gradeInfolist);
                    school.setMacInfo(macInfolist);
                    schoollist.add(school);
                    resultInfo.setData("school", schoollist);
                }
            } else if (human.getCheckFlag()==-1 || human.getManagerType()==1
                    || human.getManagerType() == 5 || human.getManagerType() == 6) {
                schoollist = schoolService.findByDeleteFlag(0);
                for (School school : schoollist) {
                    if (school != null) {
                        List<GradeInfo> gradeInfolist = gradeInfoService.findBySchoolID(school.getSchoolID());
                        List<MacInfo> macInfolist = macInfoService.findlistBySchoolID(school.getSchoolID());
                        if (gradeInfolist != null && gradeInfolist.size() > 0) {
                            for (GradeInfo gradeInfo : gradeInfolist) {
                                List<ClassInfo> classInfoList = classInfoService.findByGradeID(gradeInfo.getGradeID());
                                gradeInfo.setClassInfoList(classInfoList);
                            }
                        }
                        school.setGradeInfo(gradeInfolist);
                        school.setMacInfo(macInfolist);
                    }
                }
                resultInfo.setData("school", schoollist);
            }

        }

        resultInfo.setSuccess(true);
        resultInfo.setMessage("初始化成功！");
        resultInfo.setCode(200);
        return resultInfo;
    }

    /**
     * 发送手机验证码
     */
    @RequestMapping(value = "/sendsms")
    @ResponseBody
    public ResultInfo sendSms(HttpServletRequest request, @RequestBody Map<String, Object> telmap) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        String tel = telmap.get("tel").toString();
        String type = telmap.get("type").toString();
        Integer code = humanInfoService.getCode();
        HttpSession session = request.getSession();
        request.getSession().setAttribute("code", code);
        //request.getSession().setMaxInactiveInterval(5 * 60);
        HumanInfo human = humanInfoService.findByTel(tel);
        if(type=="sign-up" || "sign-up".equalsIgnoreCase(type)){
            if (human != null) {
                resultInfo.setMessage("该手机已注册！");
                resultInfo.setCode(400);
                return resultInfo;
            }
            if (tel != null) {
                resultInfo = humanInfoService.sendSms(code, tel);

            }
        }else if(type=="reset-password" || "reset-password".equalsIgnoreCase(type)){
            if (tel != null && !tel.equals("") && tel!="") {
                resultInfo = humanInfoService.sendSms(code, tel);
            }
        }
        return resultInfo;
    }


    /**
     * 电话注册用户
     */
    @RequestMapping(value = "/telregister")
    @ResponseBody
    public ResultInfo telRegister(HttpServletRequest request, @RequestBody Map<String, Object> jsonMap) throws Exception {

        String tel = jsonMap.get("tel").toString();
        Integer code = (Integer) jsonMap.get("code");
        String password = jsonMap.get("password").toString();
        HumanInfo human = new HumanInfo();
        human.setPassword(password);
        human.setTel(tel);
        ResultInfo resultInfo = new ResultInfo(false);
        HttpSession session = request.getSession();
        int codesession = 0;

        //humanInfoService.findByTel()

        if (null == request.getSession(false)) {
            if (true == request.getSession(true).isNew()) {
                codesession = (Integer) request.getSession().getAttribute("code");
            } else {
                System.out.println("session已经过期");
                resultInfo.setMessage("验证码已经过期");
                resultInfo.setCode(400);
                resultInfo.setSuccess(false);
                return resultInfo;
            }
        } else {
            if (request.getSession().getAttribute("code") != null) {
                codesession = (Integer) request.getSession().getAttribute("code");
            }else{
                resultInfo.setMessage("验证码已经过期");
                resultInfo.setCode(400);
                resultInfo.setSuccess(false);
                return resultInfo;
            }
        }

        if (codesession != 0 && codesession == code) {
            human.setCheckFlag(-1);
            resultInfo = humanInfoService.addHuman(human);

            resultInfo.setMessage("注册成功！");
            resultInfo.setCode(200);
            resultInfo.setSuccess(true);
            return resultInfo;
        }

        return resultInfo;
    }

    /**
     * 用户手机号重置密码
     */
    @RequestMapping(value = "/resetpassword")
    @ResponseBody
    public ResultInfo resetPassword(HttpServletRequest request, @RequestBody Map<String, Object> jsonMap) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        String tel = jsonMap.get("tel").toString();
        String password = jsonMap.get("password").toString();
        Integer code = (Integer) jsonMap.get("code");

        int codesession = 0;
        HumanInfo human = humanInfoService.findByTel(tel);
        human.setPassword(password);
        if (null == request.getSession(false)) {
            if (true == request.getSession(true).isNew()) {
                codesession = (Integer) request.getSession().getAttribute("code");
            } else {
                System.out.println("session已经过期");
            }
        } else {
            if (request.getSession().getAttribute("code") != null) {
                codesession = (Integer) request.getSession().getAttribute("code");
            }
        }

        if (codesession != 0 && codesession == code) {
            resultInfo = humanInfoService.editHuman(human);
        }

        return resultInfo;
    }

    /**
     * 人员手机注册实名制，人员修改接口
     */
    @RequestMapping(value = "/modhuman")
    @ResponseBody
    public ResultInfo modHuman(@RequestBody AddHumanParam humanParam) throws Exception {
        //System.out.println(mImagesPath);
        /*******以下是修改人员信息******/
        Integer humanID = humanParam.getHumanID();
        Integer humanType = humanParam.getHumanType();
        String humanName = humanParam.getHumanName();
        String password = humanParam.getPassword();
        Integer gradeID = humanParam.getGradeID();
        Integer classID = humanParam.getClassID();
        Integer schoolID = humanParam.getSchoolID();
        String img = humanParam.getImg();
        String tel = humanParam.getTel();
        Integer newFlag = humanParam.getNewFlag(); //手机注册实名制标识，1为实名制,走审核流程
        float[] feature = humanParam.getFeature();
        //Integer phoneFlag = humanParam.getPhoneFlag();
        Integer managerType = humanParam.getManagerType();
        String remarks = humanParam.getRemarks();
        List<Integer> relHumanlistID = humanParam.getRelHumanlistID();
        /*******以上是修改人员信息******/
        ResultInfo resultInfo = new ResultInfo(false);
        HumanInfo human = new HumanInfo();
        ParenStudentRel parenStudentRel = new ParenStudentRel();
        String message = "修改成功！";
        if(humanID!=null && humanID!=-1 && humanID!=0){
            human = humanInfoService.findByHumanID(humanID);
        }else{
            resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，人员不存在。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }
        if(humanName!=null)
            human.setHumanName(humanName);
        if(humanType!=null)
            human.setHumanType(humanType);
        if(schoolID!=null && schoolID!=-1)
            human.setSchoolID(schoolID);
        if(gradeID!=null && gradeID!=-1)
            human.setGradeID(gradeID);
        if(classID!=null && classID!=-1)
            human.setClassID(classID);
        if(password!=null)
            human.setPassword(password);
        if(tel!=null)
            human.setTel(tel);
        if(remarks!=null && remarks!="" && !remarks.equals(""))
            human.setRemarks(remarks);
        if(managerType!=null && managerType!=-1)
            human.setManagerType(managerType);
        if(newFlag!=null && newFlag==1){
            if(humanType!=null && (humanType==0 || humanType==2 || humanType==3)){
                human.setCheckFlag(0);
            }else{
                human.setCheckFlag(1);
            }
        }else{
            human.setCheckFlag(1);
        }

        humanInfoService.editHuman(human);
        if(relHumanlistID!=null && relHumanlistID.size()>0){
            for(int i=0;i<relHumanlistID.size();i++){
                HumanInfo relHuman = humanInfoService.findByHumanID(relHumanlistID.get(i));
                if(relHuman==null){
                    continue;
                }
                if(human.getManagerType()==5 && relHuman.getHumanType()==0){
                    parenStudentRel.setCheckFlag(0);
                    parenStudentRel.setHomeID(human.getHumanID());
                    parenStudentRel.setHumanID(relHuman.getHumanID());
                    pareStudentRelService.addHumanRel(parenStudentRel);

                }else if(human.getManagerType()==5 && relHuman.getManagerType()==6){
                    parenStudentRel.setCheckFlag(1);
                    parenStudentRel.setHomeID(human.getHumanID());
                    parenStudentRel.setHumanID(relHuman.getHumanID());
                    pareStudentRelService.addHumanRel(parenStudentRel);
                }else if(human.getManagerType()==6 && relHuman.getManagerType()==5){
                    parenStudentRel.setCheckFlag(0);
                    parenStudentRel.setHomeID(relHuman.getHumanID());
                    parenStudentRel.setHumanID(human.getHumanID());
                    pareStudentRelService.addHumanRel(parenStudentRel);
                }else{
                    message="修改成功，但关系绑定部分，两家长或家属不能绑定关系！";
                }


            }

        }

        if(img!=null && img!="" && !img.equals("")){
            Base64Img base64Img = new Base64Img();
            String property = System.getProperty("user.dir");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            File file = new File(mImagesPath+"school/" + schoolID);
            if (!file.exists()) {
                file.mkdir();
            }
            File teacherfile = new File(mImagesPath+"school/" + schoolID + "/teacher");
            if (!teacherfile.exists()) {
                teacherfile.mkdir();
            }
            File parentfile = new File(mImagesPath+"parent");
            if (!parentfile.exists()) {
                parentfile.mkdir();
            }
            File studentfile = new File(mImagesPath+"school/" + schoolID + "/student");
            if (!studentfile.exists()) {
                studentfile.mkdir();
            }
            File otherfile = new File(mImagesPath+"school/" + schoolID + "/other");
            if (!otherfile.exists()) {
                otherfile.mkdir();
            }
            File scenefile = new File(mImagesPath+"school/" + schoolID + "/scene");
            if (!scenefile.exists()) {
                scenefile.mkdir();
            }

            String photoName = "parent/" + human.getHumanID() + date + ".png";
            if (human.getHumanType() == 0) {
                photoName = "school/" + schoolID + "/student/" + human.getHumanID() + date + ".png";
            }else if (human.getHumanType() == 1) {
                photoName = "parent/" + human.getHumanID() + date + ".png";
            } else if (human.getHumanType() == 2 || human.getHumanType() == 3) {
                photoName = "school/" + schoolID + "/teacher/" + human.getHumanID() + date + ".png";
            } else if (human.getHumanType() == 4) {
                photoName = human.getHumanID() + date + ".png";
            } else {
                photoName = "school/" + schoolID + "/other/" + human.getHumanID() + date + ".png";
            }
            String ImgPath = mImagesPath + photoName;
            if(img!=null && img!="" && !img.equals("")){
                Base64Img.base64StrToImage(img, ImgPath);

                JSONArray jsonObject = JSONArray.fromObject(feature);
                String jsonFeature = jsonObject.toString();

                List<HumanMedia> humanMediaList = humanMediaService.findByHumanIDAndDeleteFlagAndCheckFlag(human.getHumanID(),0,1);

                if(humanMediaList!=null && humanMediaList.size()>0){
                    for(HumanMedia media:humanMediaList){
                        media.setDeleteFlag(1);
                        media.setUpdateTime(new Date());
                        humanMediaService.delMedia(media);
                    }
                }
                HumanMedia humanMedia = new HumanMedia();
                humanMedia.setUpdateTime(new Date());
                humanMedia.setMediaPath("images/"+photoName);
                humanMedia.setFeature(jsonFeature);
                humanMedia.setSchoolID(human.getSchoolID());
                humanMedia.setHumanID(human.getHumanID());
                humanMedia.setCheckFlag(1);
                //humanMedia.setCheckFlag(0);
                humanMediaService.addMedia(humanMedia);
                resultInfo.addData("media","images/"+photoName);
            }
        }

        resultInfo.addData("human",human);
        resultInfo.setMessage(message);
        resultInfo.setSuccess(true);
        resultInfo.setCode(200);

        return resultInfo;
    }


    /**
     * 家长或园方管理员新增其他人员调用该接口，人员录入接口
     */
    @RequestMapping(value = "/addhumanbyother")
    @ResponseBody
    public ResultInfo addHumanByOther(@RequestBody AddHumanParam humanParam) throws Exception {
        //System.out.println(mImagesPath);
        Integer humanID = humanParam.getHumanID();//提交申请人员ID

        /*******以下是新增人员信息******/
        Integer humanType = humanParam.getHumanType();
        String humanName = humanParam.getHumanName();
        String password = humanParam.getPassword();
        Integer gradeID = humanParam.getGradeID();
        Integer classID = humanParam.getClassID();
        Integer schoolID = humanParam.getSchoolID();
        String img = humanParam.getImg();
        String tel = humanParam.getTel();
        float[] feature = humanParam.getFeature();
        Integer phoneFlag = humanParam.getPhoneFlag();
        Integer managerType = humanParam.getManagerType();
        List<Integer> relHumanlistID = humanParam.getRelHumanlistID();
        String parentTel = humanParam.getParentTel();
        /*******以上是新增人员信息******/
        ResultInfo resultInfo = new ResultInfo(false);
        /***以下是非空判断***/
        if(humanName==null || humanName.equals("")){
            resultInfo.setSuccess(false);
            resultInfo.setCode(400);
            resultInfo.setMessage("请填写人员姓名！");
            return resultInfo;
        }
        if(humanType!=null && humanType!=-1){
            if(humanType==0){
                if(schoolID==null || schoolID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择学校！");
                    return resultInfo;
                }
                if(gradeID==null || gradeID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择年级！");
                    return resultInfo;
                }
                if(classID==null || classID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择班级！");
                    return resultInfo;
                }
            }else if(humanType==1){
                if(tel==null || tel.equals("")){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请填写电话！");
                    return resultInfo;
                }

            }else if(humanType==2 || humanType==3){
                if(tel==null || tel.equals("")){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请填写电话！");
                    return resultInfo;
                }
                if(schoolID==null || schoolID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择学校！");
                    return resultInfo;
                }

            }else{
                if(tel==null || tel.equals("")){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请填写电话！");
                    return resultInfo;
                }
            }
        }else{
            resultInfo.setSuccess(false);
            resultInfo.setCode(400);
            resultInfo.setMessage("人员类型不允许为空！");

            return resultInfo;
        }

        /****以上是非空判断***/

        HumanInfo applyHuman = new HumanInfo();
        HumanInfo addHuman = new HumanInfo();
        ParenStudentRel parenStudentRel = new ParenStudentRel();
        if(humanID!=null && humanID!=-1 && humanID!=0){
            applyHuman = humanInfoService.findByHumanID(humanID);
        }else{
            resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，申请人不存在。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }

        if(applyHuman==null){
            resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，申请人不存在。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }
        if(humanType==0){
            addHuman = humanInfoService.findByHumanNameAndSchoolIDAndGradeIDAndClassID(humanName,schoolID,gradeID,classID);
        }else if(humanType>0 && humanType<4){
            addHuman = humanInfoService.findByTel(tel);
        }else if(humanType==5){
            addHuman = humanInfoService.findByTel(tel);
        }else if(humanType==4){
            addHuman = humanInfoService.findByTel(tel);
        }
        int humanExist = 0;
        if(addHuman!=null){
            humanExist=1;
            /*resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，该人员已存在。");
            resultInfo.setSuccess(false);
            return resultInfo;*/
        }else{
            addHuman = new HumanInfo();
            addHuman.setHumanName(humanName);
            addHuman.setHumanType(humanType);
            if(schoolID!=null && schoolID!=-1){
                addHuman.setSchoolID(schoolID);
            }else if((applyHuman.getManagerType()==2 || applyHuman.getHumanType()==2) && (humanType==2 || humanType==0)){
                addHuman.setSchoolID(applyHuman.getSchoolID());
            }

            if(gradeID!=null && gradeID!=-1){
                addHuman.setGradeID(gradeID);
            }

            if(classID!=null && classID!=-1)
                addHuman.setClassID(classID);
            if(password!=null && !password.equals(""))
                addHuman.setPassword(password);
            else
                addHuman.setPassword("0000");
            addHuman.setTel(tel);
            if(managerType!=null && managerType!=-1)
                addHuman.setManagerType(managerType);
            
            
            if(applyHuman.getManagerType()==1){
                addHuman.setCheckFlag(1);
                addHuman=humanInfoService.saveHuman(addHuman);
                /*if(humanType==3 || humanType==4){
                    addHuman=humanInfoService.saveHuman(addHuman);
                }else {
                    resultInfo.setCode(400);
                    resultInfo.setMessage("新增失败，超级管理员只能新增园方管理员。");
                    resultInfo.setSuccess(false);
                    return resultInfo;
                }*/
            }else if(applyHuman.getManagerType()==2){
                addHuman.setCheckFlag(1);
                addHuman=humanInfoService.saveHuman(addHuman);
                parenStudentRel.setCheckFlag(1);
            }else if(applyHuman.getManagerType()==3){
                addHuman.setCheckFlag(1);
                addHuman=humanInfoService.saveHuman(addHuman);
                parenStudentRel.setCheckFlag(1);
            }else if(applyHuman.getManagerType()==4){
                addHuman.setCheckFlag(0);
                addHuman=humanInfoService.saveHuman(addHuman);
                parenStudentRel.setCheckFlag(0);
            }else if(applyHuman.getManagerType()==5){//家长主帐号
                if(humanType==0){
                    addHuman.setCheckFlag(0);
                    parenStudentRel.setCheckFlag(0);
                }else if(humanType==1){
                    addHuman.setCheckFlag(1);
                    parenStudentRel.setCheckFlag(1);
                }else{
                    resultInfo.setCode(400);
                    resultInfo.setMessage("新增失败，家长主帐号只能新增学生和家长从帐号。");
                    resultInfo.setSuccess(false);
                    return resultInfo;
                }
                addHuman=humanInfoService.saveHuman(addHuman);
            }


            if(relHumanlistID!=null && relHumanlistID.size()>0){//新增人员关系
                for(int i=0;i<relHumanlistID.size();i++){
                    if(relHumanlistID.get(i)!=null){
                        List<ParenStudentRel> rellist = pareStudentRelService.
                                findByHomeIDAndHumanID(relHumanlistID.get(i),addHuman.getHumanID());
                        if(rellist==null || rellist.size()==0){
                            parenStudentRel.setHomeID(relHumanlistID.get(i));
                            parenStudentRel.setHumanID(addHuman.getHumanID());
                            parenStudentRel.setSchoolID(addHuman.getSchoolID());
                            pareStudentRelService.addHumanRel(parenStudentRel);
                        }
                    }


                }

            }
        }


        

        if(img!=null && img!="" && !img.equals("")){
            Base64Img base64Img = new Base64Img();
            String property = System.getProperty("user.dir");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            File file = new File(mImagesPath+"school/" + schoolID);
            if (!file.exists()) {
                file.mkdir();
            }
            File teacherfile = new File(mImagesPath+"school/" + schoolID + "/teacher");
            if (!teacherfile.exists()) {
                teacherfile.mkdir();
            }
            File parentfile = new File(mImagesPath+"parent");
            if (!parentfile.exists()) {
                parentfile.mkdir();
            }
            File studentfile = new File(mImagesPath+"school/" + schoolID + "/student");
            if (!studentfile.exists()) {
                studentfile.mkdir();
            }
            File otherfile = new File(mImagesPath+"school/" + schoolID + "/other");
            if (!otherfile.exists()) {
                otherfile.mkdir();
            }
            File scenefile = new File(mImagesPath+"school/" + schoolID + "/scene");
            if (!scenefile.exists()) {
                scenefile.mkdir();
            }

            String photoName = "parent/" + addHuman.getHumanID() + date + ".png";
            if (addHuman.getHumanType() == 0) {
                photoName = "school/" + schoolID + "/student/" + addHuman.getHumanID() + date + ".png";
            }else if (addHuman.getHumanType() == 1) {
                photoName = "parent/" + addHuman.getHumanID() + date + ".png";
            } else if (addHuman.getHumanType() == 2 || addHuman.getHumanType() == 3) {
                photoName = "school/" + schoolID + "/teacher/" + addHuman.getHumanID() + date + ".png";
            } else if (addHuman.getHumanType() == 4) {
                photoName = addHuman.getHumanID() + date + ".png";
            } else {
                photoName = "school/" + schoolID + "/other/" + addHuman.getHumanID() + date + ".png";
            }
            String ImgPath = mImagesPath + photoName;
            if(img!=null && img!="" && !img.equals("")){
                Base64Img.base64StrToImage(img, ImgPath);

                JSONArray jsonObject = JSONArray.fromObject(feature);
                String jsonFeature = jsonObject.toString();
                
                List<HumanMedia> humanMediaList = humanMediaService.findByHumanIDAndDeleteFlag(addHuman.getHumanID(),0);
                if(humanMediaList!=null && humanMediaList.size()>0){
                    for(HumanMedia humanMedia:humanMediaList){
                        humanMedia.setDeleteFlag(1);
                        humanMediaService.delMedia(humanMedia);
                    }
                }

                HumanMedia humanMedia = new HumanMedia();
                humanMedia.setUpdateTime(new Date());
                humanMedia.setMediaPath("images/"+photoName);
                humanMedia.setFeature(jsonFeature);
                humanMedia.setSchoolID(addHuman.getSchoolID());
                humanMedia.setHumanID(addHuman.getHumanID());
                if(addHuman.getCheckFlag()==1){
                    humanMedia.setCheckFlag(1);
                }else{
                    humanMedia.setCheckFlag(0);
                }

                humanMediaService.addMedia(humanMedia);
            }
        }

        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("成功！");

        return resultInfo;
    }
    
    /**
     * 家长或园方管理员新增其他人员调用该接口，人员录入接口
     */
    @RequestMapping(value = "/addhumanbylocal")
    @ResponseBody
    public ResultInfo addHumanByLocal(@RequestBody AddHumanParam humanParam) throws Exception {
        //System.out.println(mImagesPath);

        Integer humanID = humanParam.getHumanID();//提交申请人员ID

        /*******以下是新增人员信息******/
        Integer humanType = humanParam.getHumanType();
        String humanName = humanParam.getHumanName();
        String password = humanParam.getPassword();
        Integer gradeID = humanParam.getGradeID();
        Integer classID = humanParam.getClassID();
        Integer schoolID = humanParam.getSchoolID();
        String img = humanParam.getImg();
        String tel = humanParam.getTel();
        float[] feature = humanParam.getFeature();
        Integer phoneFlag = humanParam.getPhoneFlag();
        Integer managerType = humanParam.getManagerType();
        List<Integer> relHumanlistID = humanParam.getRelHumanlistID();
        String parentTel = humanParam.getParentTel();
        /*******以上是新增人员信息******/
        ResultInfo resultInfo = new ResultInfo(false);
        String message ="新增成功！";
        /***以下是非空判断***/
        if(humanName==null || humanName.equals("")){
            resultInfo.setSuccess(false);
            resultInfo.setCode(400);
            resultInfo.setMessage("请填写人员姓名！");
            return resultInfo;
        }
        if(humanType!=null && humanType!=-1){
            if(humanType==0){
                if(schoolID==null || schoolID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择学校！");
                    return resultInfo;
                }
                if(gradeID==null || gradeID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择年级！");
                    return resultInfo;
                }
                if(classID==null || classID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择班级！");
                    return resultInfo;
                }
            }else if(humanType==1){
                if(tel==null || tel.equals("")){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请填写电话！");
                    return resultInfo;
                }

            }else if(humanType==2 || humanType==3){
                if(tel==null || tel.equals("")){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请填写电话！");
                    return resultInfo;
                }
                if(schoolID==null || schoolID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择学校！");
                    return resultInfo;
                }

            }else{
                if(tel==null || tel.equals("")){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请填写电话！");
                    return resultInfo;
                }
            }
        }else{
            resultInfo.setSuccess(false);
            resultInfo.setCode(400);
            resultInfo.setMessage("人员类型不允许为空！");

            return resultInfo;
        }

        /****以上是非空判断***/

        HumanInfo applyHuman = new HumanInfo();
        HumanInfo addHuman = new HumanInfo();
        
        if(humanID!=null && humanID!=-1 && humanID!=0){
            applyHuman = humanInfoService.findByHumanID(humanID);
        }else{
            resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，申请人不存在。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }

        if(applyHuman==null){
            resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，申请人不存在。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }
        if(applyHuman.getManagerType()!=2){
        	resultInfo.setCode(400);
            resultInfo.setMessage("本地端只能园方管理员权限才能录入。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }
        if(humanType==0){
            addHuman = humanInfoService.findByHumanNameAndSchoolIDAndGradeIDAndClassID(humanName,schoolID,gradeID,classID);
        }else if(humanType>0 && humanType<4){
            addHuman = humanInfoService.findByTel(tel);
        }else if(humanType==5){
            addHuman = humanInfoService.findByTel(tel);
        }else if(humanType==4){
            addHuman = humanInfoService.findByTel(tel);
        }
        int humanExist = 0;
        if(addHuman!=null){
            humanExist=1;
            /*resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，该人员已存在。");
            resultInfo.setSuccess(false);
            return resultInfo;*/
            if(addHuman.getCheckFlag()==0){
            	addHuman.setCheckFlag(1);
                humanInfoService.editHuman(addHuman);
            }
            
        }else{
            addHuman = new HumanInfo();
            addHuman.setHumanName(humanName);
            addHuman.setHumanType(humanType);
            if(schoolID!=null && schoolID!=-1){
                addHuman.setSchoolID(schoolID);
            }else if((applyHuman.getManagerType()==2 || applyHuman.getHumanType()==2) && (humanType==2 || humanType==0)){
                addHuman.setSchoolID(applyHuman.getSchoolID());
            }

            if(gradeID!=null && gradeID!=-1){
                addHuman.setGradeID(gradeID);
            }

            if(classID!=null && classID!=-1)
                addHuman.setClassID(classID);
            if(password!=null && !password.equals(""))
                addHuman.setPassword(password);
            else
                addHuman.setPassword("0000");
            addHuman.setTel(tel);
            if(managerType!=null && managerType!=-1)
                addHuman.setManagerType(managerType);
            
            
            if(applyHuman.getManagerType()==1){
                addHuman.setCheckFlag(1);
                addHuman=humanInfoService.saveHuman(addHuman);
                /*if(humanType==3 || humanType==4){
                    addHuman=humanInfoService.saveHuman(addHuman);
                }else {
                    resultInfo.setCode(400);
                    resultInfo.setMessage("新增失败，超级管理员只能新增园方管理员。");
                    resultInfo.setSuccess(false);
                    return resultInfo;
                }*/
            }else if(applyHuman.getManagerType()==2){
                addHuman.setCheckFlag(1);
                addHuman=humanInfoService.saveHuman(addHuman);
            }else if(applyHuman.getManagerType()==3){
                addHuman.setCheckFlag(1);
                addHuman=humanInfoService.saveHuman(addHuman);
            }else if(applyHuman.getManagerType()==4){
                addHuman.setCheckFlag(0);
                addHuman=humanInfoService.saveHuman(addHuman);
            }else if(applyHuman.getManagerType()==5){//家长主帐号
                if(humanType==0){
                    addHuman.setCheckFlag(0);
                }else if(humanType==1){
                    addHuman.setCheckFlag(1);
                }else{
                    resultInfo.setCode(400);
                    resultInfo.setMessage("新增失败，家长主帐号只能新增学生和家长从帐号。");
                    resultInfo.setSuccess(false);
                    return resultInfo;
                }
                addHuman=humanInfoService.saveHuman(addHuman);
            }


            
        }
        
        if(relHumanlistID!=null && relHumanlistID.size()>0){//新增人员关系
            for(int i=0;i<relHumanlistID.size();i++){
                if(relHumanlistID.get(i)!=null){
                    List<ParenStudentRel> rellist = pareStudentRelService.
                            findByHomeIDAndHumanID(relHumanlistID.get(i),addHuman.getHumanID());
                    if(rellist==null || rellist.size()==0){
                    	HumanInfo human = humanInfoService.findByHumanID(relHumanlistID.get(i));
                    	ParenStudentRel parenStudentRel = new ParenStudentRel();
                    	parenStudentRel.setCheckFlag(1);
                    	if(human.getManagerType()==5){
                    		parenStudentRel.setHomeID(relHumanlistID.get(i));
                            parenStudentRel.setHumanID(addHuman.getHumanID());
                            //parenStudentRel.setSchoolID(addHuman.getSchoolID());
                    	}else if(addHuman.getManagerType()==5){
                    		parenStudentRel.setHumanID(relHumanlistID.get(i));
                            parenStudentRel.setHomeID(addHuman.getHumanID());
                    	}else{
                    		message="新增成功，但关系绑定失败，绑定中没有家长主帐号！";
                    	}
                    	
                    	
                        pareStudentRelService.addHumanRel(parenStudentRel);
                    }
                }


            }

        }


        

        if(img!=null && img!="" && !img.equals("")){
            Base64Img base64Img = new Base64Img();
            String property = System.getProperty("user.dir");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            File file = new File(mImagesPath+"school/" + schoolID);
            if (!file.exists()) {
                file.mkdir();
            }
            File teacherfile = new File(mImagesPath+"school/" + schoolID + "/teacher");
            if (!teacherfile.exists()) {
                teacherfile.mkdir();
            }
            File parentfile = new File(mImagesPath+"parent");
            if (!parentfile.exists()) {
                parentfile.mkdir();
            }
            File studentfile = new File(mImagesPath+"school/" + schoolID + "/student");
            if (!studentfile.exists()) {
                studentfile.mkdir();
            }
            File otherfile = new File(mImagesPath+"school/" + schoolID + "/other");
            if (!otherfile.exists()) {
                otherfile.mkdir();
            }
            File scenefile = new File(mImagesPath+"school/" + schoolID + "/scene");
            if (!scenefile.exists()) {
                scenefile.mkdir();
            }

            String photoName = "parent/" + addHuman.getHumanID() + date + ".png";
            if (addHuman.getHumanType() == 0) {
                photoName = "school/" + schoolID + "/student/" + addHuman.getHumanID() + date + ".png";
            }else if (addHuman.getHumanType() == 1) {
                photoName = "parent/" + addHuman.getHumanID() + date + ".png";
            } else if (addHuman.getHumanType() == 2 || addHuman.getHumanType() == 3) {
                photoName = "school/" + schoolID + "/teacher/" + addHuman.getHumanID() + date + ".png";
            } else if (addHuman.getHumanType() == 4) {
                photoName = addHuman.getHumanID() + date + ".png";
            } else {
                photoName = "school/" + schoolID + "/other/" + addHuman.getHumanID() + date + ".png";
            }
            String ImgPath = mImagesPath + photoName;
            if(img!=null && img!="" && !img.equals("")){
                Base64Img.base64StrToImage(img, ImgPath);

                JSONArray jsonObject = JSONArray.fromObject(feature);
                String jsonFeature = jsonObject.toString();
                
                List<HumanMedia> humanMediaList = humanMediaService.findByHumanIDAndDeleteFlag(addHuman.getHumanID(),0);
                if(humanMediaList!=null && humanMediaList.size()>0){
                    for(HumanMedia humanMedia:humanMediaList){
                        humanMedia.setDeleteFlag(1);
                        humanMediaService.delMedia(humanMedia);
                    }
                }

                HumanMedia humanMedia = new HumanMedia();
                humanMedia.setUpdateTime(new Date());
                humanMedia.setMediaPath("images/"+photoName);
                humanMedia.setFeature(jsonFeature);
                humanMedia.setSchoolID(addHuman.getSchoolID());
                humanMedia.setHumanID(addHuman.getHumanID());
                if(addHuman.getCheckFlag()==1){
                    humanMedia.setCheckFlag(1);
                }else{
                    humanMedia.setCheckFlag(0);
                }

                humanMediaService.addMedia(humanMedia);
            }
        }

        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        resultInfo.setMessage(message);

        return resultInfo;
    }

    /**
     * 老师app新增家长和学生接口，人员录入接口
     */
    @RequestMapping(value = "/addhumanbyteacher")
    @ResponseBody
    public ResultInfo addHumanByTeacher(@RequestBody AddHumanParam humanParam) throws Exception {
        //System.out.println(mImagesPath);
        Integer humanID = humanParam.getHumanID();//提交申请人员ID

        /*******以下是新增人员信息******/
        Integer humanType = humanParam.getHumanType();
        String humanName = humanParam.getHumanName();
        String password = humanParam.getPassword();
        Integer gradeID = humanParam.getGradeID();
        Integer classID = humanParam.getClassID();
        Integer schoolID = humanParam.getSchoolID();
        String img = humanParam.getImg();
        String tel = humanParam.getTel();
        float[] feature = humanParam.getFeature();
        Integer phoneFlag = humanParam.getPhoneFlag();//手机端标识,1为手机端，0为本地端。
        Integer managerType = humanParam.getManagerType();
        List<Integer> relHumanlistID = humanParam.getRelHumanlistID();
        String parentTel = humanParam.getParentTel();
        String parentName = humanParam.getParentName();
        /*******以上是新增人员信息******/
        ResultInfo resultInfo = new ResultInfo(false);
        /***以下是非空判断***/
        if(humanName==null || humanName.equals("")){
            resultInfo.setSuccess(false);
            resultInfo.setCode(400);
            resultInfo.setMessage("请填写人员姓名！");
            return resultInfo;
        }
        if(humanType!=null && humanType!=-1){
            if(humanType==0){
                if(schoolID==null || schoolID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择学校！");
                    return resultInfo;
                }
                if(gradeID==null || gradeID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择年级！");
                    return resultInfo;
                }
                if(classID==null || classID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择班级！");
                    return resultInfo;
                }
            }else if(humanType==1){
                if(tel==null || tel.equals("")){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请填写电话！");
                    return resultInfo;
                }

            }else if(humanType==2 || humanType==3){
                if(tel==null || tel.equals("")){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请填写电话！");
                    return resultInfo;
                }
                if(schoolID==null || schoolID==-1){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请选择学校！");
                    return resultInfo;
                }

            }else{
                if(tel==null || tel.equals("")){
                    resultInfo.setSuccess(false);
                    resultInfo.setCode(400);
                    resultInfo.setMessage("请填写电话！");
                    return resultInfo;
                }
            }
        }else{
            resultInfo.setSuccess(false);
            resultInfo.setCode(400);
            resultInfo.setMessage("人员类型不允许为空！");

            return resultInfo;
        }

        /****以上是非空判断***/



        if(parentName!=null && parentTel!=null){
            HumanInfo parentHuman = humanInfoService.findByHumanNameAndTel(parentName,parentTel);
            if(parentHuman!=null){
                if(relHumanlistID==null){
                    relHumanlistID = new ArrayList<Integer>();
                }
                relHumanlistID.add(parentHuman.getHumanID());
            }else{
                resultInfo.setSuccess(false);
                resultInfo.setCode(400);
                resultInfo.setMessage("家长不存在，请核对家长信息！");

                return resultInfo;
            }
        }


        HumanInfo applyHuman = new HumanInfo();
        HumanInfo addHuman = new HumanInfo();
        ParenStudentRel parenStudentRel = new ParenStudentRel();
        if(humanID!=null && humanID!=-1 && humanID!=0){
            applyHuman = humanInfoService.findByHumanID(humanID);
        }else{
            resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，操作人不存在。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }

        if(applyHuman==null){
            resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，操作人不存在。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }

        if(humanType==0){
            addHuman = humanInfoService.findByHumanNameAndSchoolIDAndGradeIDAndClassID(humanName,schoolID,gradeID,classID);
        }else if(humanType==1){
            addHuman = humanInfoService.findByTel(tel);
        }
        int humanExist = 0;
        if(addHuman!=null){
            humanExist=1;
            resultInfo.setCode(400);
            resultInfo.setMessage("新增失败，该人员已存在。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }else{
            addHuman = new HumanInfo();
            addHuman.setHumanName(humanName);
            addHuman.setHumanType(humanType);
            if(schoolID!=null && schoolID!=-1){
                addHuman.setSchoolID(schoolID);
            }else if(humanType==0){
                addHuman.setSchoolID(applyHuman.getSchoolID());
            }

            if(gradeID!=null && gradeID!=-1){
                addHuman.setGradeID(gradeID);
            }

            if(classID!=null && classID!=-1)
                addHuman.setClassID(classID);
            if(password!=null && !password.equals(""))
                addHuman.setPassword(password);
            else
                addHuman.setPassword("0000");
            addHuman.setTel(tel);
            if(managerType!=null && managerType!=-1)
                addHuman.setManagerType(managerType);
        }


        if(applyHuman.getManagerType()==3){
            addHuman.setCheckFlag(1);
            addHuman=humanInfoService.saveHuman(addHuman);
            parenStudentRel.setCheckFlag(1);
        }else if(applyHuman.getManagerType()==4){
            addHuman.setCheckFlag(0);
            addHuman=humanInfoService.saveHuman(addHuman);
            parenStudentRel.setCheckFlag(0);
        }



        if(relHumanlistID!=null && relHumanlistID.size()>0){//新增人员关系
            for(int i=0;i<relHumanlistID.size();i++){
                if(relHumanlistID.get(i)!=null){
                    List<ParenStudentRel> rellist = pareStudentRelService.
                            findByHomeIDAndHumanID(relHumanlistID.get(i),addHuman.getHumanID());
                    if(rellist==null || rellist.size()==0){
                        parenStudentRel.setHomeID(relHumanlistID.get(i));
                        parenStudentRel.setHumanID(addHuman.getHumanID());
                        parenStudentRel.setSchoolID(applyHuman.getSchoolID());
                        pareStudentRelService.addHumanRel(parenStudentRel);
                    }
                }
            }

        }

        if(humanExist==0 && img!=null && img!="" && !img.equals("")){
            Base64Img base64Img = new Base64Img();
            String property = System.getProperty("user.dir");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            File file = new File(mImagesPath+"school/" + schoolID);
            if (!file.exists()) {
                file.mkdir();
            }
            File teacherfile = new File(mImagesPath+"school/" + schoolID + "/teacher");
            if (!teacherfile.exists()) {
                teacherfile.mkdir();
            }
            File parentfile = new File(mImagesPath+"parent");
            if (!parentfile.exists()) {
                parentfile.mkdir();
            }
            File studentfile = new File(mImagesPath+"school/" + schoolID + "/student");
            if (!studentfile.exists()) {
                studentfile.mkdir();
            }
            File otherfile = new File(mImagesPath+"school/" + schoolID + "/other");
            if (!otherfile.exists()) {
                otherfile.mkdir();
            }
            File scenefile = new File(mImagesPath+"school/" + schoolID + "/scene");
            if (!scenefile.exists()) {
                scenefile.mkdir();
            }

            String photoName = "parent/" + addHuman.getHumanID() + date + ".png";
            if (addHuman.getHumanType() == 0) {
                photoName = "school/" + schoolID + "/student/" + addHuman.getHumanID() + date + ".png";
            }else if (addHuman.getHumanType() == 1) {
                photoName = "parent/" + addHuman.getHumanID() + date + ".png";
            } else if (addHuman.getHumanType() == 2 || addHuman.getHumanType() == 3) {
                photoName = "school/" + schoolID + "/teacher/" + addHuman.getHumanID() + date + ".png";
            } else if (addHuman.getHumanType() == 4) {
                photoName = addHuman.getHumanID() + date + ".png";
            } else {
                photoName = "school/" + schoolID + "/other/" + addHuman.getHumanID() + date + ".png";
            }
            String ImgPath = mImagesPath + photoName;
            if(img!=null && img!="" && !img.equals("")){
                Base64Img.base64StrToImage(img, ImgPath);

                JSONArray jsonObject = JSONArray.fromObject(feature);
                String jsonFeature = jsonObject.toString();

                HumanMedia humanMedia = new HumanMedia();
                humanMedia.setUpdateTime(new Date());
                humanMedia.setMediaPath("images/"+photoName);
                humanMedia.setFeature(jsonFeature);
                humanMedia.setSchoolID(addHuman.getSchoolID());
                humanMedia.setHumanID(addHuman.getHumanID());
                if(addHuman.getCheckFlag()==1){
                    humanMedia.setCheckFlag(1);
                }else{
                    humanMedia.setCheckFlag(0);
                }

                humanMediaService.addMedia(humanMedia);
            }
        }

        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("成功！");

        return resultInfo;
    }

    /**
     * 修改人员信息,实名制信息。
     */
    @RequestMapping(value = "/addhuman")
    @ResponseBody
    public ResultInfo addHuman(@RequestBody AddHumanParam jsonMap) throws Exception {
        //System.out.println(mImagesPath);
        Integer humanID = jsonMap.getHumanID();
        Integer humanType =  jsonMap.getHumanType();
        String humanName = jsonMap.getHumanName();
        String password = jsonMap.getPassword();
        Integer gradeID =  jsonMap.getGradeID();
        Integer classID =  jsonMap.getClassID();
        Integer schoolID =  jsonMap.getSchoolID();
        String img = jsonMap.getImg();
        String tel = jsonMap.getTel();
        float[] feature =  jsonMap.getFeature();
        Integer phoneFlag =  jsonMap.getPhoneFlag();
        Integer managerType =  jsonMap.getManagerType();
        List<Integer> relHumanlistID = jsonMap.getRelHumanlistID();
        ResultInfo resultInfo = new ResultInfo(false);
        HumanInfo human = new HumanInfo();
        if (phoneFlag == 1 && humanType >= 1) { //手机端人员新增
            if (humanID == null) {
                resultInfo.setCode(400);
                resultInfo.setSuccess(false);
                resultInfo.setMessage("手机端，请先手机注册用户。");
                return resultInfo;
            }
            human = humanInfoService.findByHumanID(humanID);
            human.setHumanName(humanName);
            human.setHumanType(humanType);
            human.setSchoolID(schoolID);
            human.setManagerType(managerType);
            human.setGradeID(gradeID);
            human.setClassID(classID);
            human.setCheckFlag(0);
            resultInfo = humanInfoService.editHuman(human);
            HumanRegisterRecord humanRegisterRecord = new HumanRegisterRecord();
            humanRegisterRecord.setCheckFlag(0);
            humanRegisterRecord.setOperateHumID(human.getHumanID());
            humanRegisterRecord.setRegHumID(human.getHumanID());
            humanRegisterRecord.setClassID(classID);
            humanRegisterRecord.setSchoolID(schoolID);
            humanRegisterRecordService.save(humanRegisterRecord);
            ParenStudentRel parenStudentRel = new ParenStudentRel();
            if(relHumanlistID!=null && relHumanlistID.size()>0){
                parenStudentRel.setHomeID(relHumanlistID.get(0));
                parenStudentRel.setHumanID(human.getHumanID());
                //parenStudentRel.setSchoolID();
                pareStudentRelService.addHumanRel(parenStudentRel);
            }
        } else if (phoneFlag == 0) {  //本地端人员新增
            if(humanID!=null && humanID!=-1){
                human = humanInfoService.findByHumanID(humanID);
            }else if(humanType==0){
                human = humanInfoService.findByHumanNameAndSchoolIDAndGradeIDAndClassID(humanName,schoolID,gradeID,classID);
            }else if(humanType>0 && humanType<4 || humanType==5){
                human = humanInfoService.findByTel(tel);
            }else if(humanType==4){
                human = humanInfoService.findByTel(tel);
            }
            if(human==null){
                human = new HumanInfo();
                human.setHumanName(humanName);
                human.setHumanType(humanType);
                human.setSchoolID(schoolID);
                human.setGradeID(gradeID);
                human.setClassID(classID);
                human.setPassword(password);
                human.setTel(tel);
                human.setManagerType(managerType);
                human.setCheckFlag(1);
                human = humanInfoService.saveHuman(human);
            }else{
                human.setManagerType(managerType);
                human.setHumanName(humanName);
                human.setHumanType(humanType);
                human.setSchoolID(schoolID);
                human.setGradeID(gradeID);
                human.setClassID(classID);
                human.setPassword(password);
                human.setTel(tel);
                human.setCheckFlag(1);
               humanInfoService.editHuman(human);
            }

            ParenStudentRel parenStudentRel = new ParenStudentRel();
            if(relHumanlistID!=null && relHumanlistID.size()>0){
                parenStudentRel.setHomeID(relHumanlistID.get(0));
                parenStudentRel.setHumanID(human.getHumanID());
                pareStudentRelService.addHumanRel(parenStudentRel);
            }

        }

        Base64Img base64Img = new Base64Img();
        //img = base64Img.imageToBase64Str("D:\\tomcat\\apache-tomcat-7.0.34\\webapps\\images\\adult\\20180817103257.png");
        //String classpath = this.getClass().getClassLoader().getResource("/").getPath().replaceFirst("/", "");
        //String childImgPath = classpath.replaceAll("WEB-INF/classes/", "WEB-INF/classes/static/");
        String property = System.getProperty("user.dir");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        File file = new File(mImagesPath+"school/" + schoolID);
        if (!file.exists()) {
            file.mkdir();
        }
        File teacherfile = new File(mImagesPath+"school/" + schoolID + "/teacher");
        if (!teacherfile.exists()) {
            teacherfile.mkdir();
        }
        File parentfile = new File(mImagesPath+"parent");
        if (!parentfile.exists()) {
            parentfile.mkdir();
        }
        File studentfile = new File(mImagesPath+"school/" + schoolID + "/student");
        if (!studentfile.exists()) {
            studentfile.mkdir();
        }
        File otherfile = new File(mImagesPath+"school/" + schoolID + "/other");
        if (!otherfile.exists()) {
            otherfile.mkdir();
        }
        File scenefile = new File(mImagesPath+"school/" + schoolID + "/scene");
        if (!scenefile.exists()) {
            scenefile.mkdir();
        }

        String photoName = "parent/" + human.getHumanID() + date + ".png";
        if (human.getHumanType() == 0) {
            photoName = "school/" + schoolID + "/student/" + human.getHumanID() + date + ".png";
        }else if (human.getHumanType() == 1) {
            photoName = "parent/" + human.getHumanID() + date + ".png";
        } else if (human.getHumanType() == 2 || human.getHumanType() == 3) {
            photoName = "school/" + schoolID + "/teacher/" + human.getHumanID() + date + ".png";
        } else if (human.getHumanType() == 4) {
            photoName = human.getHumanID() + date + ".png";
        } else {
            photoName = "school/" + schoolID + "/other/" + human.getHumanID() + date + ".png";
        }
        String ImgPath = mImagesPath + photoName;
        if(img!=null && img!="" && !img.equals("")){
            Base64Img.base64StrToImage(img, ImgPath);

            JSONArray jsonObject = JSONArray.fromObject(feature);
            String jsonFeature = jsonObject.toString();

            HumanMedia humanMedia = new HumanMedia();
            humanMedia.setUpdateTime(new Date());
            humanMedia.setMediaPath("images/"+photoName);
            humanMedia.setFeature(jsonFeature);
            humanMedia.setSchoolID(human.getSchoolID());
            humanMedia.setHumanID(human.getHumanID());
            humanMedia.setCheckFlag(0);
            humanMediaService.addMedia(humanMedia);
        }


        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("成功！");

        return resultInfo;
    }

    /**
     * 查询待审核的人员信息
     */
    @RequestMapping(value = "/findtocheckhuman")
    @ResponseBody
    public ResultInfo findToCheckHuman(@RequestBody Map<String, Object> jsonMap) throws Exception {

        Integer humanID = (Integer) jsonMap.get("humanID");
        Integer applyType = (Integer) jsonMap.get("applyType");
        HumanInfo human = humanInfoService.findByHumanID(humanID);
        ResultInfo resultInfo = new ResultInfo(false);
        List<Map<String,Object>> humanlist = new ArrayList<Map<String,Object>>();
        if(human.getManagerType()==3){
            humanlist = humanInfoService.findByClassIDAndCheckFlagAndHumanType(human.getClassID(),applyType);
        }else if(human.getManagerType()==2){
            humanlist = humanInfoService.findBySchoolIDAndCheckFlagAndHumanType(human.getSchoolID(),applyType);
        }else if(human.getManagerType()==1){
            humanlist = humanInfoService.findByCheckFlagAndHumanType(applyType);
        }else if(human.getManagerType()==5){
            humanlist = humanInfoService.findHumanByCheckFlag(human.getHumanID(),applyType);
        }
        resultInfo.addData("humanlist",humanlist);
        resultInfo.setCode(200);
        resultInfo.setMessage("成功！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }


    /**
     * 审核修改人员信息,实名制信息。
     */
    @RequestMapping(value = "/checkhuman")
    @ResponseBody
    public ResultInfo checkHuman(@RequestBody CheckHumanParam checkHumanParam) throws Exception {

        ResultInfo resultInfo = new ResultInfo(false);
        //String message = "";
        if(checkHumanParam!=null && checkHumanParam.getHumanID()!=null && checkHumanParam.getHumanID().size()>0){
            for(int i=0;i<checkHumanParam.getHumanID().size();i++){
                Integer humanID = checkHumanParam.getHumanID().get(i);
                Integer checkFlag = checkHumanParam.getCheckFlag();
                String remarks=null;
                if(checkHumanParam.getRemarks()!=null && checkHumanParam.getRemarks().size()>0){
                    remarks = checkHumanParam.getRemarks().get(i);
                }
                HumanInfo human = humanInfoService.findByHumanID(humanID);

                if (human!=null && human.getHumanID() != null) {
                    if (checkFlag == 1) {
                        human.setCheckFlag(1);
                        human.setRemarks(remarks);
                        resultInfo = humanInfoService.editHuman(human);

                    } else if (checkFlag == 2){
                        human.setRemarks(remarks);
                        human.setCheckFlag(2);
                        resultInfo = humanInfoService.editHuman(human);
                    }

                    List<ParenStudentRel> parenStudentRellist = pareStudentRelService.findByHumanIDAndCheckFlagAndDeleteFlag(human.getHumanID(),0,0);
                    for(ParenStudentRel parenStudentRel:parenStudentRellist){
                        parenStudentRel.setCheckFlag(1);
                        pareStudentRelService.editHumanRel(parenStudentRel);
                    }

                    List<HumanMedia> humanMediaList = humanMediaService.
                            findByHumanIDAndDeleteFlagAndCheckFlag(human.getHumanID(),0,0);
                    if(humanMediaList!=null && humanMediaList.size()>0){
                        for(HumanMedia humanMedia:humanMediaList){
                            humanMedia.setCheckFlag(1);
                            humanMedia.setUpdateTime(new Date());
                            humanMediaService.editMedia(humanMedia);
                        }
                    }

                }
            }

        }
        resultInfo.setMessage("审核成功！");
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 审核修改人员信息,实名制信息。
     */
    @RequestMapping(value = "/checkhumanrel")
    @ResponseBody
    public ResultInfo checkHumanRel(@RequestBody CheckHumanParam checkHumanParam) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        return resultInfo;
    }

    /**
     * 审查学生注册信息。
     */
    @RequestMapping(value = "/checkstudent")
    @ResponseBody
    public ResultInfo checkStudent(@RequestBody Map<String, Object> jsonMap) throws Exception {
        Integer humanID = (Integer) jsonMap.get("humanID");
        Integer checkFlag = (Integer) jsonMap.get("checkFlag");
        HumanInfo human = humanInfoService.findByHumanID(humanID);
        ResultInfo resultInfo = new ResultInfo(false);
        if (human.getHumanID() != null) {
            if (checkFlag == 1) {
                human.setCheckFlag(1);
                resultInfo = humanInfoService.editHuman(human);
                resultInfo.setMessage("审核学生成功！");
            } else {
                resultInfo.setCode(400);
                resultInfo.setMessage("审核学生失败！");
                resultInfo.setSuccess(false);
            }

        } else {
            resultInfo.setCode(400);
            resultInfo.setMessage("审核家长失败！");
            resultInfo.setSuccess(false);
        }
        return resultInfo;
    }

    /**
     * 删除人员信息
     */
    @RequestMapping(value = "/delhuman")
    @ResponseBody
    public ResultInfo delHuman(@RequestBody Map<String, Object> jsonMap) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<Integer> humanID = (List) jsonMap.get("humanID");
        for(Integer humanid:humanID){
            //Integer humanID = (Integer) jsonMap.get("humanID");
            HumanInfo human = humanInfoService.findByHumanID(humanid);
            human.setDeleteFlag(1);
            humanInfoService.editHuman(human);
            List<HumanMedia> humanMediaList = humanMediaService.findByHumanIDAndDeleteFlag(humanid,0);
            if(humanMediaList!=null && humanMediaList.size()>0){
                for(HumanMedia humanMedia:humanMediaList){
                    humanMedia.setDeleteFlag(1);
                    humanMediaService.delMedia(humanMedia);
                }
            }
            List<ParenStudentRel> relList1 = pareStudentRelService.findByHomeIDAndDeleteFlag(humanid,0);
            if(relList1!=null && relList1.size()>0){
                for(ParenStudentRel parenStudentRel:relList1){
                    parenStudentRel.setDeleteFlag(1);
                    pareStudentRelService.editHumanRel(parenStudentRel);
                }
            }

            List<ParenStudentRel> relList2 = pareStudentRelService.findByHumanIDAndDeleteFlag(humanid,0);
            if(relList2!=null && relList2.size()>0){
                for(ParenStudentRel parenStudentRel:relList2){
                    parenStudentRel.setDeleteFlag(1);
                    pareStudentRelService.editHumanRel(parenStudentRel);
                }
            }


        }

        resultInfo.setMessage("删除成功！");
        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        return resultInfo;
    }

    /**
     * 增加人员和关系
     */
    @RequestMapping(value = "/addhumanrel")
    @ResponseBody
    public ResultInfo addHumanRel(@RequestBody Map<String, Object> jsonMap) throws Exception {
        HumanInfo human = new HumanInfo();
        human.setHumanType((Integer) jsonMap.get("humanType"));
        human.setHumanName(jsonMap.get("humanName").toString());
        human.setSchoolID((Integer) jsonMap.get("schoolID"));
        human.setGradeID((Integer) jsonMap.get("gradeID"));
        human.setClassID((Integer) jsonMap.get("classID"));
        human.setTel(jsonMap.get("tel").toString());
        Integer schoolID = human.getSchoolID();

        Integer parentID = (Integer) jsonMap.get("parentID");
        String img = jsonMap.get("img").toString();
        String feature = jsonMap.get("feature").toString();
        Integer phoneFlag = 0;

        ResultInfo resultInfo = new ResultInfo(false);

        if (human.getHumanType() == 0) {
            if (humanInfoService.findByHumanNameAndSchoolIDAndGradeIDAndClassID(human.getHumanName(), human.getSchoolID(), human.getGradeID(), human.getClassID()) != null) {
                human = humanInfoService.findByHumanNameAndSchoolIDAndGradeIDAndClassID(human.getHumanName(), human.getSchoolID(), human.getGradeID(), human.getClassID());
                /*resultInfo.setSuccess(false);
                resultInfo.setCode(400);
                resultInfo.setMessage("该学生已录入！");
                return resultInfo;*/
                if (phoneFlag == 1)
                    human.setCheckFlag(0);
                else if (phoneFlag == 0) {
                    human.setCheckFlag(1);
                }

                resultInfo = humanInfoService.editHuman(human);
            } else {
                human.setCheckFlag(0);
                resultInfo = humanInfoService.addHuman(human);
                if (resultInfo.isSuccess() == true) {
                    human = humanInfoService.findByHumanNameAndSchoolIDAndGradeIDAndClassID(human.getHumanName(), human.getSchoolID(), human.getGradeID(), human.getClassID());
                } else {
                    resultInfo.setMessage("人员新增失败！");
                    resultInfo.setCode(400);
                    resultInfo.setSuccess(false);
                    return resultInfo;
                }
            }
            ParenStudentRel parenStudentRel = new ParenStudentRel();
            parenStudentRel.setHomeID(parentID);
            parenStudentRel.setSchoolID(human.getSchoolID());
            parenStudentRel.setHumanID(human.getHumanID());
            if (pareStudentRelService.findByHomeIDAndHumanID(parentID, human.getHumanID()) != null) {
                resultInfo.setMessage("该关系已绑定！");
                resultInfo.setCode(400);
                resultInfo.setSuccess(false);
                return resultInfo;
            } else {
                pareStudentRelService.addHumanRel(parenStudentRel);
            }

            Base64Img base64Img = new Base64Img();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            File file = new File(mImagesPath+"school/" + schoolID);
            if (!file.exists()) {
                file.mkdir();
                File teacherfile = new File(mImagesPath+"school/" + schoolID + "/teacher");
                if (!teacherfile.exists()) {
                    teacherfile.mkdir();
                }
                File parentfile = new File(mImagesPath+"parent");
                if (!parentfile.exists()) {
                    parentfile.mkdir();
                }
                File studentfile = new File(mImagesPath+"school/" + schoolID + "/student");
                if (!studentfile.exists()) {
                    studentfile.mkdir();
                }
                File otherfile = new File(mImagesPath+"school/" + schoolID + "/other");
                if (!otherfile.exists()) {
                    otherfile.mkdir();
                }
                File scenefile = new File(mImagesPath+"school/" + schoolID + "/scene");
                if (!scenefile.exists()) {
                    scenefile.mkdir();
                }
            }
            String photoName = "school/" + schoolID + "/student/" + human.getHumanID() + date + ".png";
            String ImgPath = mImagesPath + photoName;
            Base64Img.base64StrToImage(img, ImgPath);
            HumanMedia humanMedia = new HumanMedia();
            humanMedia.setUpdateTime(new Date());
            humanMedia.setMediaPath("images/"+photoName);
            humanMedia.setFeature(feature);
            humanMedia.setSchoolID(human.getSchoolID());
            humanMedia.setHumanID(human.getHumanID());
            humanMedia.setCheckFlag(0);
            humanMediaService.addMedia(humanMedia);

        } else if (human.getHumanType() > 0) {

            human.setCheckFlag(1);
            if (humanInfoService.findByTel(human.getTel()) != null) {
                human = humanInfoService.findByTel(human.getTel());
            } else {
                resultInfo = humanInfoService.addHuman(human);
                if (resultInfo.isSuccess() == true) {
                    human = humanInfoService.findByTel(human.getTel());
                } else {
                    resultInfo.setMessage("人员新增失败！");
                    resultInfo.setCode(400);
                    resultInfo.setSuccess(false);
                    return resultInfo;
                }
            }

            ParenStudentRel parenStudentRel = new ParenStudentRel();
            parenStudentRel.setHomeID(parentID);
            parenStudentRel.setSchoolID(human.getSchoolID());
            parenStudentRel.setHumanID(human.getHumanID());
            if (pareStudentRelService.findByHomeIDAndHumanID(parentID, human.getHumanID()) != null) {
                resultInfo.setMessage("该关系已绑定！");
                resultInfo.setCode(400);
                resultInfo.setSuccess(false);
                return resultInfo;
            } else {
                pareStudentRelService.addHumanRel(parenStudentRel);
            }

            Base64Img base64Img = new Base64Img();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            File file = new File(mImagesPath+"school/" + schoolID);
            if (!file.exists()) {
                file.mkdir();
                File teacherfile = new File(mImagesPath+"school/" + schoolID + "/teacher");
                if (!teacherfile.exists()) {
                    teacherfile.mkdir();
                }
                File parentfile = new File(mImagesPath+"parent");
                if (!parentfile.exists()) {
                    parentfile.mkdir();
                }
                File studentfile = new File(mImagesPath+"school/" + schoolID + "/student");
                if (!studentfile.exists()) {
                    studentfile.mkdir();
                }
                File otherfile = new File(mImagesPath+"school/" + schoolID + "/other");
                if (!otherfile.exists()) {
                    otherfile.mkdir();
                }
                File scenefile = new File(mImagesPath+"school/" + schoolID + "/scene");
                if (!scenefile.exists()) {
                    scenefile.mkdir();
                }
            }
            String photoName = "parent/" + human.getHumanID() + date + ".png";
            String ImgPath = mImagesPath+ photoName;
            Base64Img.base64StrToImage(img, ImgPath);
            HumanMedia humanMedia = new HumanMedia();
            humanMedia.setUpdateTime(new Date());
            humanMedia.setMediaPath("images/"+photoName);
            humanMedia.setFeature(feature);
            humanMedia.setHumanID(human.getHumanID());
            humanMedia.setSchoolID(human.getSchoolID());
            humanMedia.setCheckFlag(0);
            humanMediaService.addMedia(humanMedia);
        }

        //resultInfo = humanInfoService.findHuman(humanName,schoolID,gradeID,classID,tel,humanType,atSchoolFlag);
        resultInfo.setMessage("新增关系成功！");
        return resultInfo;
    }

    @RequestMapping(value = "/findhumanrel")
    @ResponseBody
    public ResultInfo findHumanRel(@RequestBody HumanInfo human) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<Map<String, Object>> humanlist = humanInfoService.findHumanRel(human);
        resultInfo.addData("humanlist", humanlist);
        resultInfo.setMessage("查询成功！");
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        return resultInfo;
    }


    /**
     * 查询人员信息
     */
    @RequestMapping(value = "/findhuman")
    @ResponseBody
    public ResultInfo findHuman(@RequestBody HumanParam human) throws Exception {

        ResultInfo resultInfo = new ResultInfo(false);
        resultInfo = humanInfoService.findHuman(human.getHumanName(), human.getSchoolID(), human.getGradeID(), human.getClassID(),
                human.getTel(), human.getHumanType(), human.getAtschoolFlag(), human.getCheckFlag(), human.getManagerType());
        return resultInfo;
    }

    /**
     * 申请管理员权限
     */
    @RequestMapping(value = "/applaymanager")
    @ResponseBody
    public ResultInfo applayManager(@RequestBody Map<String, Object> jsonMap) throws Exception {
        Integer humanID = (Integer) jsonMap.get("humanID");
        Integer applayAuth = (Integer) jsonMap.get("applayAuth");
        Integer classID = (Integer) jsonMap.get("classID");
        ResultInfo resultInfo = new ResultInfo(false);
        HumanInfo human = humanInfoService.findByHumanID(humanID);
        if (human != null) {
            human.setApplayAuth(applayAuth);
            human.setManagerFlag(0);
            resultInfo = humanInfoService.editHuman(human);
            ManagerRegisterRecord managerRegisterRecord = managerRegisterRecordService.findByRegManagerIDAndCheckFlag(humanID, 0);
            if (managerRegisterRecord != null) {

            } else {
                managerRegisterRecord = new ManagerRegisterRecord();
            }
            managerRegisterRecord.setApplayAuth(applayAuth);
            managerRegisterRecord.setCheckFlag(0);
            managerRegisterRecord.setOperateHumID(humanID);
            managerRegisterRecord.setRegManagerID(humanID);
            managerRegisterRecord.setClassID(classID);
            managerRegisterRecordService.save(managerRegisterRecord);
            resultInfo.setMessage("申请成功！请等待审核！");
        }
        return resultInfo;
    }

    /**
     * 查询待审核权限的管理员
     */
    @RequestMapping(value = "/findapplaymanager")
    @ResponseBody
    public ResultInfo findApplayManager(@RequestBody Map<String, Object> jsonMap) throws Exception {
        Integer classID = (Integer) jsonMap.get("classID");
        ResultInfo resultInfo = new ResultInfo(false);
        resultInfo = humanInfoService.findApplayManager(classID);
        return resultInfo;
    }

    /**
     * 审核管理员权限
     * checkflag=0审核不通这，1审核通过
     */
    @RequestMapping(value = "/checkmanager")
    @ResponseBody
    public ResultInfo checkManager(@RequestBody Map<String, Object> jsonMap) throws Exception {
        Integer humanID = (Integer) jsonMap.get("humanID");
        Integer checkflag = (Integer) jsonMap.get("checkFlag");
        ResultInfo resultInfo = new ResultInfo(false);
        HumanInfo human = humanInfoService.findByHumanID(humanID);
        if (human != null && checkflag == 1) {

            ManagerRegisterRecord managerRegisterRecord = managerRegisterRecordService.findByRegManagerIDAndCheckFlag(humanID, 0);
            if (managerRegisterRecord != null) {
                managerRegisterRecord.setCheckFlag(1);
                managerRegisterRecord.setCheckTime(new Date());
                managerRegisterRecordService.save(managerRegisterRecord);
                human.setManagerType(human.getApplayAuth());
                human.setManagerFlag(1);
                resultInfo = humanInfoService.editHuman(human);
            }
            resultInfo.setMessage("审核成功！");
        }
        return resultInfo;
    }

}
