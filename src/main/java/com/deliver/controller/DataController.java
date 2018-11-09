package com.deliver.controller;

import com.deliver.entity.AtSchoolRecord;
import com.deliver.entity.DeliverRecord;
import com.deliver.entity.HumanInfo;
import com.deliver.mapbody.DeliverData;
import com.deliver.service.AtSchoolRecordService;
import com.deliver.service.DeliverRecordService;
import com.deliver.service.HumanInfoService;
import com.deliver.util.Base64Img;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据同步
 * Created by pdl on 2018/9/25.
 */

@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DeliverRecordService deliverRecordService;

    @Autowired
    private HumanInfoService humanInfoService;

    @Autowired
    private AtSchoolRecordService atSchoolRecordService;

    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    /**
     * 上传数据同步
     */
    @RequestMapping(value="/deliverdata")
    @ResponseBody
    public ResultInfo deliverData(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        List<Map<String,Object>> deliverDatalist = (List) jsonMap.get("deliverDatalist");
        ResultInfo resultInfo = new ResultInfo(false);
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳

        if(deliverDatalist!=null && deliverDatalist.size()>0){
            for(int i=0;i<deliverDatalist.size();i++){
                Map<String,Object> dataMap = deliverDatalist.get(i);
                if(dataMap.get("parentID")!=null && (Integer)dataMap.get("parentID")!=-1 && (Integer)dataMap.get("parentID")!=0){
                    HumanInfo human = humanInfoService.findByHumanID((Integer) dataMap.get("parentID"));
                    Integer accessType = (Integer) dataMap.get("accessType");
                    if(human!=null){
                        human.setAtschoolFlag((Integer) dataMap.get("accessType"));
                        humanInfoService.save(human);

                        AtSchoolRecord atSchoolRecord = new AtSchoolRecord();
                        atSchoolRecord.setAccessTime(new Date());
                        if(dataMap.get("accessType")!=null)
                            atSchoolRecord.setAccessType((Integer) dataMap.get("accessType"));
                        if(dataMap.get("checkResult")!=null)
                            atSchoolRecord.setCheckresult((Integer) dataMap.get("checkResult"));
                        atSchoolRecord.setDeleteFlag(0);
                        if(dataMap.get("operateID")!=null){
                            atSchoolRecord.setOperateID((Integer) dataMap.get("operateID"));
                        }
                        atSchoolRecord.setMessage(dataMap.get("message").toString());
                        if(dataMap.get("parentID")!=null)
                            atSchoolRecord.setHumanID((Integer) dataMap.get("parentID"));
                        if(dataMap.get("schoolID")!=null)
                            atSchoolRecord.setSchoolID((Integer) dataMap.get("schoolID"));
                        atSchoolRecordService.save(atSchoolRecord);
                    }

                }

                if((Integer) dataMap.get("studentID")!=null && (Integer) dataMap.get("studentID")!=-1 && (Integer) dataMap.get("studentID")!=0){
                    HumanInfo human = humanInfoService.findByHumanID((Integer) dataMap.get("studentID"));
                    if(human!=null){
                        human.setAtschoolFlag((Integer) dataMap.get("accessType"));
                        humanInfoService.save(human);

                        AtSchoolRecord atSchoolRecord = new AtSchoolRecord();
                        atSchoolRecord.setAccessTime(new Date());
                        if(dataMap.get("accessType")!=null)
                            atSchoolRecord.setAccessType((Integer) dataMap.get("accessType"));
                        if(dataMap.get("checkResult")!=null)
                            atSchoolRecord.setCheckresult((Integer) dataMap.get("checkResult"));
                        atSchoolRecord.setDeleteFlag(0);
                        if(dataMap.get("operateID")!=null)
                            atSchoolRecord.setOperateID((Integer) dataMap.get("operateID"));
                        if(dataMap.get("message")!=null)
                            atSchoolRecord.setMessage((String) dataMap.get("message"));
                        if(dataMap.get("studentID")!=null)
                            atSchoolRecord.setHumanID((Integer) dataMap.get("studentID"));
                        if(dataMap.get("schoolID")!=null)
                            atSchoolRecord.setSchoolID((Integer) dataMap.get("schoolID"));
                        atSchoolRecordService.save(atSchoolRecord);
                    }

                }
                String photoName = "school/"+(Integer) dataMap.get("schoolID")+"/scene/";
                if((Integer) dataMap.get("studentID")!=null && (Integer) dataMap.get("studentID")!=-1
                        && (Integer) dataMap.get("studentID")!=-2){
                    photoName+=(Integer) dataMap.get("studentID") + date + ".png";
                }else if(dataMap.get("parentID")!=null && (Integer) dataMap.get("parentID")!=-1
                        && (Integer) dataMap.get("parentID")!=-2){
                    photoName+=(Integer) dataMap.get("parentID") + date + ".png";
                }else{
                    photoName+=(Integer) dataMap.get("parentID") + date + ".png";
                }

                DeliverRecord deliverRecord = new DeliverRecord();
                if(dataMap.get("img")!=null){
                    Base64Img.base64StrToImage(dataMap.get("img").toString(),mImagesPath + photoName);
                    deliverRecord.setMedia("images/"+photoName);
                }

                deliverRecord.setSchoolID((Integer) dataMap.get("schoolID"));
                deliverRecord.setCheckresult((Integer) dataMap.get("checkResult"));
                if((Integer) dataMap.get("studentID")!=-2){
                    deliverRecord.setStudentID((Integer) dataMap.get("studentID"));
                }else{
                    HumanInfo mo =humanInfoService.findByHumanNameAndTel("陌生人",null);
                    deliverRecord.setStudentID(mo.getHumanID());
                }

                if(dataMap.get("deliverTime")!=null) {
                    Date deliverTime = new Date(new Long(dataMap.get("deliverTime").toString()));
                    //Date deliverTime = sDateFormat.parse(dataMap.get("deliverTime").toString());
                    deliverRecord.setDeliverTime(deliverTime);
                }

                if((Integer) dataMap.get("parentID")!=-2){
                    deliverRecord.setParentID((Integer) dataMap.get("parentID"));
                }else{
                    HumanInfo mo =humanInfoService.findByHumanNameAndTel("陌生人",null);
                    deliverRecord.setParentID(mo.getHumanID());
                }
                deliverRecord.setAccessType((Integer) dataMap.get("accessType"));
                deliverRecord.setDeliverType((Integer) dataMap.get("deliverType"));
                if(dataMap.get("message")!=null)
                    deliverRecord.setMessage(dataMap.get("message").toString());
                deliverRecord.setOperateID((Integer) dataMap.get("operateID"));
                if(dataMap.get("remarks")!=null)
                    deliverRecord.setRemarks(dataMap.get("remarks").toString());
                deliverRecordService.insert(deliverRecord);
            }
        }

        resultInfo.setCode(200);
        resultInfo.setMessage("同步完毕！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    @RequestMapping(value="/client")
    @ResponseBody
    public ResultInfo client(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        String cliendid =  (String) jsonMap.get("clientid");
        ResultInfo resultInfo = new ResultInfo(true);
        System.out.println("client:"+cliendid);
        return resultInfo;
    }


}
