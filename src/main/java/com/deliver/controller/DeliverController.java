package com.deliver.controller;

import com.deliver.bean.ArrRequest;
import com.deliver.entity.AtSchoolRecord;
import com.deliver.entity.DeliverRecord;
import com.deliver.entity.HumanInfo;
import com.deliver.entity.ParenStudentRel;
import com.deliver.mapbody.ConfirmParam;
import com.deliver.mapbody.DeliverParam;
import com.deliver.mapbody.DeliverRecordParam;
import com.deliver.service.*;
import com.deliver.util.Base64Img;
import com.deliver.util.CosDistance;
import com.deliver.util.ResultInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pdl on 2018/9/17.
 */
@Controller
@RequestMapping("/access")
public class DeliverController {

    @Autowired
    private DeliverRecordService deliverRecordService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PareStudentRelService pareStudentRelService;

    @Autowired
    private HumanInfoService humanInfoService;

    @Autowired
    private AtSchoolRecordService atSchoolRecordService;

    @Autowired
    private GeTuiRecordService geTuiRecordService;

    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    /**
     * 接送检测
     */
    @RequestMapping(value="/deliverdetect")
    @ResponseBody
    public ResultInfo deliverDetect(@RequestBody DeliverParam deliverParam ) throws Exception{
        ResultInfo resultInfo = new ResultInfo(false);
        Integer delivertype = deliverParam.getDelivertype();

        double[] feature1 = deliverParam.getFeature1();
        double[] feature2 = deliverParam.getFeature2();

        Integer schoolID = deliverParam.getSchoolID();


        String sql = "select a.humanid,a.human_name,a.human_type,b.mediaid,b.media_path,b.feature from tc_human_info a,tc_human_media b " +
                "where a.humanid=b.humanid and a.delete_flag=0 and b.delete_flag=0 and a.schoolid="+schoolID;
        List<Map<String,Object>> humanFeaturelist = jdbcTemplate.queryForList(sql);
        CosDistance cosDistance = new CosDistance();
        Map<String,Object> humanMap1 = new HashMap<String,Object>();
        Map<String,Object> humanMap2 = new HashMap<String,Object>();

        if(feature1!=null && feature1.length>0){
            humanMap1.put("HUMANNAME","陌生人");
            humanMap1.put("HUMANTYPE",-1);
            humanMap1.put("HUMANID",0);
            humanMap1.put("SIMILAR",0);
            humanMap1.put("MEDIAID",-1);
            humanMap1.put("MEDIANAME","");
            resultInfo.setData("HUMAN1", humanMap1);
        }
        if(feature2!=null && feature2.length>0){
            humanMap2.put("HUMANNAME","陌生人");
            humanMap2.put("HUMANTYPE",-1);
            humanMap2.put("HUMANID",0);
            humanMap2.put("SIMILAR",0);
            humanMap1.put("MEDIAID",-1);
            humanMap1.put("MEDIANAME","");
            resultInfo.setData("HUMAN2", humanMap2);
        }

        double max1=0.68;
        double max2=0.68;
        if(humanFeaturelist!=null && humanFeaturelist.size()>0){
            for(int i=0;i<humanFeaturelist.size();i++){
                String[] featuresArr = humanFeaturelist.get(i).get("FEATURE").toString().split(",");

                if(featuresArr!=null){
                    double[] featuredArr = new double[featuresArr.length];
                    for(int j=0;j<featuresArr.length;j++){
                        featuredArr[j]=Double.valueOf(featuresArr[j]);
                    }
                    double similarity1 = 0;
                    double similarity2 = 0;
                    if(featuredArr.length==128){
                        if(feature1!=null && feature1.length>0){
                            similarity1=cosDistance.sim(feature1, featuredArr);
                            if(similarity1>max1){
                                max1 = similarity1;
                                humanFeaturelist.get(i).put("SIMILAR",max1);
                                humanMap1 = humanFeaturelist.get(i);
                                resultInfo.setData("HUMAN1", humanMap1);
                            }
                        }

                        if(feature2!=null && feature2.length>0){
                            similarity2=cosDistance.sim(feature2, featuredArr);
                            if(similarity2>max2){
                                max2 = similarity2;
                                humanFeaturelist.get(i).put("SIMILAR",max2);
                                humanMap2 = humanFeaturelist.get(i);
                                resultInfo.setData("HUMAN2", humanMap2);
                            }
                        }

                    }


                }

            }
        }

        if(feature1!=null && feature1.length>0 && feature2!=null && feature2.length>0){
            if(Integer.parseInt(humanMap1.get("HUMANT_TYPE").toString())==-1 || Integer.parseInt(humanMap2.get("HUMAN_TYPE").toString())==-1){
                resultInfo.setCode(400);
                resultInfo.addData("CHECKRESULT", 2);
                resultInfo.setMessage("检测异常！陌生人员，请先录入登记！");
                resultInfo.setSuccess(true);
                return resultInfo;
            }else if(Integer.parseInt(humanMap1.get("HUMANT_TYPE").toString())>0 && Integer.parseInt(humanMap2.get("HUMANT_TYPE").toString())>0){
                resultInfo.setCode(400);
                resultInfo.setMessage("检测异常！两非学生人员，请排队进出！");
                resultInfo.addData("CHECKRESULT", 0);
                resultInfo.setSuccess(true);
                return resultInfo;
            }else if((Integer.parseInt(humanMap1.get("HUMANT_TYPE").toString())>0 && Integer.parseInt(humanMap2.get("HUMANT_TYPE").toString())==0)){

                String ssql = "select a.homeid from tc_parent_student_rel a where a.humanid="+(Integer)humanMap2.get("HUMANID")+" and a.homeid in " +
                        "(select a.homeid from tc_parent_student_rel a where a.humanid="+(Integer)humanMap1.get("HUMANID")+")";
                List<Map<String,Object>> relatlist = jdbcTemplate.queryForList(ssql);

                if(relatlist!=null && relatlist.size()>0){
                    resultInfo.setCode(200);
                    resultInfo.addData("CHECKRESULT", 1);
                    resultInfo.setMessage("检测成功！"+humanMap1.get("HUMAN_NAME")+"为"+humanMap2.get("HUMAN_NAME")+"的家长,欢迎进出。");
                    resultInfo.setSuccess(true);
                    return resultInfo;
                }else{
                    resultInfo.setCode(400);
                    resultInfo.addData("CHECKRESULT", 0);
                    resultInfo.setMessage("检测异常！"+humanMap1.get("HUMAN_NAME")+"与"+humanMap2.get("HUMAN_NAME")+"没有记录家庭关系。");
                    resultInfo.setSuccess(true);
                    return resultInfo;
                }
            }else if(Integer.parseInt(humanMap1.get("HUMANT_TYPE").toString())==0 && Integer.parseInt(humanMap2.get("HUMANT_TYPE").toString())>0){
                String ssql = "select a.homeid from tc_parent_student_rel a where a.humanid="+(Integer)humanMap1.get("HUMANID")+" and a.homeid in " +
                        "(select a.homeid from tc_parent_student_rel a where a.humanid="+(Integer)humanMap2.get("HUMANID")+")";
                List<Map<String,Object>> relatlist = jdbcTemplate.queryForList(ssql);

                if(relatlist!=null && relatlist.size()>0){
                    resultInfo.setCode(200);
                    resultInfo.addData("CHECKRESULT", 1);
                    resultInfo.setMessage("检测成功！"+humanMap2.get("HUMAN_NAME")+"为"+humanMap1.get("HUMAN_NAME")+"的家长,欢迎进出。");
                    resultInfo.setSuccess(true);
                    return resultInfo;
                }else{
                    resultInfo.setCode(400);
                    resultInfo.addData("CHECKRESULT", 0);
                    resultInfo.setMessage("检测异常！"+humanMap1.get("HUMAN_NAME")+"与"+humanMap2.get("HUMAN_NAME")+"没有记录家庭关系。");
                    resultInfo.setSuccess(true);
                    return resultInfo;
                }
            }else if(Integer.parseInt(humanMap1.get("HUMANT_TYPE").toString())==0 && Integer.parseInt(humanMap2.get("HUMANT_TYPE").toString())==0){
                resultInfo.setCode(400);
                resultInfo.addData("CHECKRESULT", 0);
                resultInfo.setMessage("检测异常！两名学生，请排队进出！");
                resultInfo.setSuccess(true);
                return resultInfo;
            }
        }else if(feature1!=null && feature1.length>0 && feature2==null  && Integer.parseInt(humanMap1.get("HUMANT_TYPE").toString())>0){
            resultInfo.setCode(200);
            //resultInfo.addData("CHECKRESULT", 0);
            //resultInfo.setMessage("检测异常！家长、老师以及其他非学生人员，单独进出时，请在人员打卡系统进行检测！");
            resultInfo.addData("CHECKRESULT", 1);
            resultInfo.setMessage("检测成功！欢迎"+humanMap1.get("HUMAN_NAME")+"进出。");
            resultInfo.setSuccess(true);
            return resultInfo;
        }else if(feature1!=null && feature1.length>0 && feature2==null  && Integer.parseInt(humanMap1.get("HUMANT_TYPE").toString())==-1){
            resultInfo.setCode(200);
            resultInfo.addData("CHECKRESULT", 2);
            resultInfo.setMessage("检测异常！陌生人员，请先录入登记！");
            resultInfo.setSuccess(true);
            return resultInfo;
        }else if(feature1!=null && feature1.length>0 && feature2==null  && Integer.parseInt(humanMap1.get("HUMANT_TYPE").toString())==0){
            if(delivertype==0){
                resultInfo.setCode(200);
                resultInfo.addData("CHECKRESULT", 1);
                resultInfo.setMessage("检测成功！欢迎"+humanMap1.get("HUMAN_NAME")+"同学进出。");
                resultInfo.setSuccess(true);
                return resultInfo;
            }else if(delivertype==1){
                resultInfo.setCode(200);
                resultInfo.addData("CHECKRESULT", 0);
                resultInfo.setMessage("检测异常！学生不能从人员打卡系统进出！");
                resultInfo.setSuccess(true);
                return resultInfo;
            }

        }

        resultInfo.addData("CHECKRESULT", 0);
        resultInfo.setCode(200);
        resultInfo.setMessage("检测异常！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 接送检测确认
     */
    @RequestMapping(value="/deliverconfirm")
    @ResponseBody
    public ResultInfo deliverConfirm(@RequestBody ConfirmParam confirmParam) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        Base64Img base64Img = new Base64Img();
        String classpath = this.getClass().getClassLoader().getResource("/").getPath().replaceFirst("/", "");
        String childImgPath = classpath.replaceAll("WEB-INF/classes/", "");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String photoName = "school/"+confirmParam.getSchoolID()+"/scene/";
        HumanInfo human1 = humanInfoService.findByHumanID(confirmParam.getHumanID1());
        HumanInfo human2 = humanInfoService.findByHumanID(confirmParam.getHumanID2());
        human1.setAtschoolFlag(confirmParam.getAccessType());
        human2.setAtschoolFlag(confirmParam.getAccessType());
        humanInfoService.save(human1);
        humanInfoService.save(human2);

        if(confirmParam.getHumanType1()==0){
            DeliverRecord deliverRecord = new DeliverRecord();
            deliverRecord.setStudentID(confirmParam.getHumanID1());
            deliverRecord.setMessage(confirmParam.getMessage());
            deliverRecord.setAccessType(confirmParam.getAccessType());
            deliverRecord.setDeliverType(confirmParam.getDeliverType());
            deliverRecord.setOperateID(confirmParam.getOperaterID());
            deliverRecord.setCheckresult(confirmParam.getCheckResult());
            deliverRecord.setSchoolID(confirmParam.getSchoolID());
            photoName = photoName+confirmParam.getHumanID1()+date+".png";

            deliverRecord.setMedia("images/"+photoName);
            if(confirmParam.getHumanType2()>0){
                deliverRecord.setParentID(confirmParam.getHumanID2());
                deliverRecordService.save(deliverRecord);

            }else if(confirmParam.getHumanType2()==0){
                DeliverRecord deliverRecord2 = new DeliverRecord();
                deliverRecord2.setStudentID(confirmParam.getHumanID2());
                deliverRecord2.setMessage(confirmParam.getMessage());
                deliverRecord2.setAccessType(confirmParam.getAccessType());
                deliverRecord2.setDeliverType(confirmParam.getDeliverType());
                deliverRecord2.setOperateID(confirmParam.getOperaterID());
                deliverRecord2.setCheckresult(confirmParam.getCheckResult());
                deliverRecord2.setSchoolID(confirmParam.getSchoolID());
                photoName = photoName+confirmParam.getHumanID2()+date+".png";

                deliverRecord.setMedia("images/"+photoName);
                deliverRecordService.save(deliverRecord);
                deliverRecordService.save(deliverRecord2);
            }

        }else if(confirmParam.getHumanType1()>0){
            DeliverRecord deliverRecord = new DeliverRecord();
            deliverRecord.setParentID(confirmParam.getHumanID1());
            deliverRecord.setMessage(confirmParam.getMessage());
            deliverRecord.setAccessType(confirmParam.getAccessType());
            deliverRecord.setDeliverType(confirmParam.getDeliverType());
            deliverRecord.setCheckresult(confirmParam.getCheckResult());
            deliverRecord.setOperateID(confirmParam.getOperaterID());
            deliverRecord.setSchoolID(confirmParam.getSchoolID());
            photoName = photoName+confirmParam.getHumanID1()+date+".png";

            deliverRecord.setMedia("images/"+photoName);
            if(confirmParam.getHumanType2()==0){
                deliverRecord.setStudentID(confirmParam.getHumanID2());
                deliverRecordService.save(deliverRecord);

            }else if(confirmParam.getHumanType2()>0){
                DeliverRecord deliverRecord2 = new DeliverRecord();
                deliverRecord2.setParentID(confirmParam.getHumanID2());
                deliverRecord2.setMessage(confirmParam.getMessage());
                deliverRecord2.setAccessType(confirmParam.getAccessType());
                deliverRecord2.setDeliverType(confirmParam.getDeliverType());
                deliverRecord2.setOperateID(confirmParam.getOperaterID());
                deliverRecord2.setCheckresult(confirmParam.getCheckResult());
                deliverRecord2.setSchoolID(confirmParam.getSchoolID());
                photoName = photoName+confirmParam.getHumanID2()+date+".png";
                deliverRecord.setMedia("images/"+photoName);
                deliverRecordService.save(deliverRecord);
                deliverRecordService.save(deliverRecord2);
            }
        }
        Base64Img.base64StrToImage(confirmParam.getOrgPhotoCode(),mImagesPath+photoName);
        human1.setAtschoolFlag(1);
        human2.setAtschoolFlag(1);
        if(confirmParam.getHumanType1()!=null){
            AtSchoolRecord atSchoolRecord = new AtSchoolRecord();
            atSchoolRecord.setAccessTime(new Date());
            atSchoolRecord.setAccessType(confirmParam.getAccessType());
            atSchoolRecord.setCheckresult(confirmParam.getCheckResult());
            atSchoolRecord.setDeleteFlag(0);
            atSchoolRecord.setOperateID(confirmParam.getOperaterID());
            atSchoolRecord.setMessage(confirmParam.getMessage());
            atSchoolRecord.setHumanID(confirmParam.getHumanID1());
            atSchoolRecord.setSchoolID(confirmParam.getSchoolID());
            atSchoolRecordService.save(atSchoolRecord);
        }

        if(confirmParam.getHumanType2()!=null){
            AtSchoolRecord atSchoolRecord = new AtSchoolRecord();
            atSchoolRecord.setAccessTime(new Date());
            atSchoolRecord.setAccessType(confirmParam.getAccessType());
            atSchoolRecord.setCheckresult(confirmParam.getCheckResult());
            atSchoolRecord.setDeleteFlag(0);
            atSchoolRecord.setOperateID(confirmParam.getOperaterID());
            atSchoolRecord.setMessage(confirmParam.getMessage());
            atSchoolRecord.setHumanID(confirmParam.getHumanID2());
            atSchoolRecord.setSchoolID(confirmParam.getSchoolID());
            atSchoolRecordService.save(atSchoolRecord);
        }

        resultInfo.setCode(200);
        resultInfo.setMessage("成功！");
        resultInfo.setSuccess(true);

        return resultInfo;
    }

    /**
     * 进出记录查询
     */
    @RequestMapping(value="/deliverrecordquery")
    @ResponseBody
    public ResultInfo deliverRecordQuery(@RequestBody DeliverRecordParam deliverRecordParam) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<Map<String,Object>> humanlist = deliverRecordService.deliverRecordQuery(deliverRecordParam);
        resultInfo.addData("humanlist",humanlist);
        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("查询成功！");
        return resultInfo;
    }

    /**
            * 个推消息记录查询
     */
    @RequestMapping(value="/getuirecordquery")
    @ResponseBody
    public ResultInfo geTuiRecordQuery(@RequestBody Map<String,Object> jsonMap) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        Integer humanID = (Integer) jsonMap.get("humanID");
        String beginTime = (String) jsonMap.get("beginTime");
        String endTime = (String) jsonMap.get("endTime");
        List<Map<String,Object>> geTuilist = geTuiRecordService.geTuiRecordQuery(humanID,beginTime,endTime);
        resultInfo.addData("geTuilist",geTuilist);
        resultInfo.setSuccess(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("查询成功！");
        return resultInfo;
    }

    /**
     * 家长或老师接送记录查询
     *
     */
    @RequestMapping(value="/deliverrecordquerybyadult")
    @ResponseBody
    public ResultInfo deliverRecordQueryByAdult(@RequestBody Map<String,Object> jsonMap) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        Integer humanID = (Integer) jsonMap.get("humanID");//家长或老师的ID
        Integer humanType = (Integer) jsonMap.get("humanType");
        String humanName = (String) jsonMap.get("humanName");//小孩的名字
        String beginTime = (String) jsonMap.get("beginTime");
        String endTime = (String) jsonMap.get("endTime");
        resultInfo = deliverRecordService.deliverRecordQueryByAdult(humanID,humanName,humanType,beginTime,endTime);
        return resultInfo;
    }

}
