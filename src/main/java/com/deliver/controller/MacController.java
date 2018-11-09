package com.deliver.controller;


import com.deliver.entity.*;
import com.deliver.service.*;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
 * Created by lon on 2018/9/18.
 * 包括新增mac地址 ； 删除mac地址
 */
@Controller
public class MacController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MacInfoService macInfoService;


    /**
     * 根据学校id 查询 mac 地址
     * http://localhost:8080/deliver/findMacDeviceBySchoolID
     * schoolID=1
     * {
     "schoolID":1
     }
     */
    @RequestMapping("/findMacDeviceBySchoolID")
    @ResponseBody
    public ResultInfo findMacDevice(@RequestBody MacInfo macInfo){
        System.out.println(macInfo);

        ResultInfo resultInfo = macInfoService.findBySchoolID(macInfo.getSchoolID());
        System.out.println(resultInfo);
        return resultInfo;
    }

    /**
     * 查询所有存在的 mac 地址
     * http://localhost:8080/deliver/findallMacDevice
     */
    @RequestMapping("/findallMacDevice")
    @ResponseBody
    public ResultInfo findallMacDevice(){
        ResultInfo resultInfo = macInfoService.findallMacDevice();
        return resultInfo;
    }

    /**
     * 根据学校id, mac 地址，新增 mac 地址
     * http://localhost:8080/deliver/addMacDevice
     * {
     "macName":"60-45-CB-A7-7A-B9",
     "schoolID":10
     }
     */
    @RequestMapping("/addMacDevice")
    @ResponseBody
    public ResultInfo addMacDevice(@RequestBody MacInfo macInfo){
        System.out.println(macInfo.getMacName());

        MacInfo findedmac = macInfoService.findByMacName(macInfo.getMacName());
        if (findedmac != null){  // 如果已经存在mac就不在加入
            ResultInfo resultInfo = new ResultInfo(false);
            resultInfo.setMessage("400 已经存在mac address");
            return resultInfo;
        }
        macInfo.setDeleteFlag(0);
        macInfo.setCreateTime(new Date());
        macInfo.setUpdateTime(new Date());
        ResultInfo resultInfo = new ResultInfo(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("新增 mac 设备成功");

        macInfoService.save(macInfo);
        return resultInfo;
    }

    /**
     * 根据学校id 删除mac 地址,实际删除而不是逻辑删除
     * http://localhost:8080/deliver/delMacDevice?schoolID=1
     */
    @RequestMapping("/delMacDevice")
    @ResponseBody
    public ResultInfo delMacDevice(@RequestBody MacInfo macInfo){
        System.out.println(macInfo.getSchoolID());
        macInfoService.deleteBySchool(macInfo.getSchoolID());

        ResultInfo resultInfo = new ResultInfo(true);
        resultInfo.setCode(200);
        return resultInfo;
    }

    /**
     * 批量删除mac 信息
     * 根据macid 删除 mac 地址,实际删除而不是逻辑删除
     * http://localhost:8080/deliver/delMacDeviceByMacID
     * {
     "macID":[11,16]
     }
     macID 而不是学校ID
     */
    @RequestMapping("/delMacDeviceByMacID")
    @ResponseBody
    public ResultInfo delMacDeviceByMacID(@RequestBody Map<String,Object> jsonMap){
        List<Integer> macIDs = (List) jsonMap.get("macID");
        if(macIDs!=null && macIDs.size()>0){
            for(int i = 0; i<macIDs.size();i++){
                macInfoService.delete(macIDs.get(i));
            }
        }
        //System.out.println(macInfo.getMacID());

        ResultInfo resultInfo = new ResultInfo(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("批量删除 mac 设备成功");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /** 编辑 mac 记录
     * 根据MacID获取记录， 然后编辑 mac 地址和schoolID
     * http://localhost:8080/deliver/editMacDevice
     * macID=1&macName=60-45-CB-A7-7A-B9&schoolID=10
     * {
     "macID":17,
     "macName":"60-45-CB-A7-7A-B9",
     "schoolID":1000
     }
     */
    @RequestMapping("/editMacDevice")
    @ResponseBody
    public ResultInfo editMacDevice(@RequestBody MacInfo macInfo){
        MacInfo macInfoFull = macInfoService.findByMacID(macInfo.getMacID());
        if (macInfoFull == null){  // 如果不存在mac ，需要先将该mac 绑定学校
            ResultInfo resultInfo = new ResultInfo(false);
            resultInfo.setMessage("不存在mac address ");
            resultInfo.setCode(400);
            return resultInfo;
        }
        macInfoFull.setMacName(macInfo.getMacName());
        macInfoFull.setSchoolID(macInfo.getSchoolID());
        macInfoFull.setUpdateTime(new Date());
        macInfoService.save(macInfoFull);

        ResultInfo resultInfo = new ResultInfo(true);
        resultInfo.setCode(200);
        resultInfo.setMessage("修改成功！ ");
        return resultInfo;
    }





    /**
     * 云端数据同步到本地
     * 根据mac 地址找到学校id，然后根据学校id 找到相应的班级信息，年级信息，学校信息，
     * 人员信息，人员图片，人员关系信息，区域信息 发送给该终端
     * 同步的时间节点根据mac_info 表里面的更新时间为准，将其他表里面该时间节点之后的数据
     * 同步给终端。
     * 请求使用 get方式，数据使用 json 格式
     * http://localhost:8080/deliver/synData?macName=1c:ca:e3:3c:c2:43&init=1
     * 同步完成之后，需要对mac info 的更新时间进行更新
     */

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    private HumanInfoService humanInfoService;

    @Autowired
    private HumanMediaService humanMediaService;

    @Autowired
    private PareStudentRelService pareStudentRelService;

    @Autowired
    private GradeInfoService gradeInfoService;

    @Autowired
    private RegionInfoService regionInfoService;

    @Autowired
    private HumanTypeService  humanTypeService;

    @Autowired
    private ManagerTypeService  managerTypeService;

    @Autowired
    private AdvertService advertService;

    @RequestMapping("/synData")
    @ResponseBody
    public ResultInfo synData( String macName, String init){
//        System.out.println(macInfo.getMacName());
        System.out.println(macName);
        System.out.println(init);
        Date dateNow = new Date();
        System.out.println(dateNow);

//        MacInfo findedMac = macInfoService.findByMacName(macInfo.getMacName());
        MacInfo findedMac = macInfoService.findByMacName(macName);
        if (findedMac == null){  // 如果不存在mac ，需要先将该mac 绑定学校
            ResultInfo resultInfo = new ResultInfo(false);
            resultInfo.setMessage("400 不存在mac address,先绑定该mac ");
            return resultInfo;
        }
        Integer schoolID = findedMac.getSchoolID();
        Date startDate = findedMac.getUpdateTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(startDate);

        if (startDate == null || init.equals("1") ){  // 如果不存在更新日期，返回所有班级信息

            ResultInfo resultInfo = new ResultInfo(true);
            resultInfo.setMessage(" 不存在更新日期，返回所有信息");

            School school = schoolService.findBySchoolID(schoolID);
            List<ClassInfo> classInfoList = classInfoService.findBySchoolID(schoolID);
            //List<HumanInfo> humanInfoList = humanInfoService.findBySchoolID(schoolID);
            String sql = "select t.humanid as humanID,t.applay_auth as applayAuth,t.atschool_flag as atschoolFlag,\n" +
                    "t.check_flag as checkFlag,t.classid as classID,t.clientid as clientID,\n" +
                    "t.create_time as createTime,t.delete_flag as deleteFlag,t.gradeid as gradeID,t.human_name as humanName,\n" +
                    "t.human_type as humanType,t.idnum,t.manager_flag as managerFlag,t.manager_type as managerType,\n" +
                    "t.`online`,t.`password`,t.remarks,t.schoolid as schoolID,t.tel,t.token,t.update_time as updateTime \n" +
                    "from tc_human_info t where t.humanid in \n" +
                    "(select DISTINCT b.humanid as humanid  from tc_human_info t,tc_parent_student_rel a,tc_parent_student_rel b " +
                    "where t.human_type=0 and t.schoolid="+schoolID+" and a.humanid=t.humanid \n" +
                    "and b.homeid=a.homeid \n" +
                    "UNION \n" +
                    "select DISTINCT a.homeid as humanid  from tc_human_info t,tc_parent_student_rel a " +
                    "where t.human_type=0 and t.schoolid="+schoolID+" and a.humanid=t.humanid ) " +
                    "UNION select DISTINCT t.humanid as humanID,t.applay_auth as applayAuth,t.atschool_flag as atschoolFlag,\n" +
                    "t.check_flag as checkFlag,t.classid as classID,t.clientid as clientID,\n" +
                    "t.create_time as createTime,t.delete_flag as deleteFlag,t.gradeid as gradeID,t.human_name as humanName,\n" +
                    "t.human_type as humanType,t.idnum,t.manager_flag as managerFlag,t.manager_type as managerType,\n" +
                    "t.`online`,t.`password`,t.remarks,t.schoolid as schoolID,t.tel,t.token,t.update_time as updateTime  " +
                    " from tc_human_info t where t.human_type<>1 and t.schoolid="+schoolID ;
            List<Map<String,Object>> humanInfoList = jdbcTemplate.queryForList(sql);
            sql = "select DISTINCT t.mediaid as mediaID,t.create_time as createTime,t.delete_flag as deleteFlag,\n" +
                    "t.feature,t.humanid as humanID,t.media_path as mediaPath,t.remarks,t.update_time as updateTime,\n" +
                    "t.schoolid as schoolID,t.check_flag as checkFlag \n" +
                    "from tc_human_media t where t.humanid in \n" +
                    "(select DISTINCT b.humanid as humanid  from tc_human_info t,tc_parent_student_rel a,tc_parent_student_rel b " +
                    "where t.human_type=0 and t.schoolid="+schoolID+" and a.humanid=t.humanid \n" +
                    "and b.homeid=a.homeid \n" +
                    "UNION \n" +
                    "select DISTINCT a.homeid as humanid  from tc_human_info t,tc_parent_student_rel a " +
                    "where t.human_type=0 and t.schoolid="+schoolID+" and a.humanid=t.humanid ) " +
                    "UNION select DISTINCT t.mediaid as mediaID,t.create_time as createTime,t.delete_flag as deleteFlag,\n" +
                    " t.feature,t.humanid as humanID,t.media_path as mediaPath,t.remarks,t.update_time as updateTime,"  +
                    " t.schoolid as schoolID,t.check_flag as checkFlag from tc_human_info a,tc_human_media t " +
                    " where t.humanid=a.humanid and a.human_type<>1 and a.schoolid="+schoolID ;
            List<Map<String,Object>> humanMediaList = jdbcTemplate.queryForList(sql);
                    //List<HumanMedia> humanMediaList = humanMediaService.findBySchoolID(schoolID);
            sql = "select DISTINCT b.relid as relid,b.check_flag as checkFlag,b.create_time as createTime,\n" +
                    "b.delete_flag as deleteFlag,b.homeid as homeID,b.humanid as humanID,b.remarks,\n" +
                    "b.update_time as updateTime,b.schoolID from tc_human_info t,tc_parent_student_rel a,\n" +
                    "tc_parent_student_rel b where t.human_type=0 and t.schoolid="+schoolID+" and t.humanid=a.humanid and a.homeid=b.homeid";
            List<Map<String,Object>> parenStudentRel = jdbcTemplate.queryForList(sql);
            //List<ParenStudentRel> parenStudentRel = pareStudentRelService.findBySchoolID(schoolID);
            List<GradeInfo> gradeInfoList = gradeInfoService.findBySchoolIDonly(schoolID);
            List<RegionInfo> regionInfoList = regionInfoService.findByDeleteFlag(0);
            List<HumanType> humanTypeList = humanTypeService.findByDeleteFlag(0);
            List<ManagerType> managerTypeList = managerTypeService.findByDeleteFlag(0);

            List<Advert> advertList = advertService.findBySchoolIDAndDeleteFlag(schoolID,0);


            resultInfo.addData("school",school);
            resultInfo.addData("classInfoList",classInfoList);
            resultInfo.addData("humanInfoList",humanInfoList);
            resultInfo.addData("humanMediaList",humanMediaList);
            resultInfo.addData("parenStudentRelList",parenStudentRel);
            resultInfo.addData("gradeInfoList",gradeInfoList);
            resultInfo.addData("regionInfoList",regionInfoList);
            resultInfo.addData("humanTypeList",humanTypeList);
            resultInfo.addData("managerTypeList",managerTypeList);
            resultInfo.addData("advertList",advertList);
            resultInfo.setCode(200);

            findedMac.setUpdateTime(new Date()); // 重置更新时间
            macInfoService.save(findedMac);

            return resultInfo;

        }else {

            School school = schoolService.findBySchoolIDAndUpdateTimeAfter(schoolID, startDate);
            List<ClassInfo> classInfoList = classInfoService.findBySchoolIDAndUpdateTimeAfter(schoolID, startDate);
            //List<HumanInfo> humanInfoList = humanInfoService.findBySchoolIDAndUpdateTimeAfter(schoolID, startDate);
            String sql = "select t.humanid as humanID,t.applay_auth as applayAuth,t.atschool_flag as atschoolFlag,\n" +
                    "t.check_flag as checkFlag,t.classid as classID,t.clientid as clientID,\n" +
                    "t.create_time as createTime,t.delete_flag as deleteFlag,t.gradeid as gradeID,t.human_name as humanName,\n" +
                    "t.human_type as humanType,t.idnum,t.manager_flag as managerFlag,t.manager_type as managerType,\n" +
                    "t.`online`,t.`password`,t.remarks,t.schoolid as schoolID,t.tel,t.token,t.update_time as updateTime \n" +
                    "from tc_human_info t where t.humanid in \n" +
                    "(select DISTINCT b.humanid as humanid  from tc_human_info t,tc_parent_student_rel a,tc_parent_student_rel b " +
                    "where t.human_type=0 and t.schoolid="+schoolID+" and a.humanid=t.humanid \n" +
                    " and b.homeid=a.homeid \n" +
                    "UNION \n" +
                    "select DISTINCT a.homeid as humanid  from tc_human_info t,tc_parent_student_rel a " +
                    "where t.human_type=0 and t.schoolid="+schoolID+" and a.humanid=t.humanid ) "  +
                    "and t.update_time>DATE_FORMAT('"+date+"','%Y-%m-%d %H:%i:%s')" +
                    "UNION select DISTINCT t.humanid as humanID,t.applay_auth as applayAuth,t.atschool_flag as atschoolFlag,\n" +
                    "t.check_flag as checkFlag,t.classid as classID,t.clientid as clientID,\n" +
                    "t.create_time as createTime,t.delete_flag as deleteFlag,t.gradeid as gradeID,t.human_name as humanName,\n" +
                    "t.human_type as humanType,t.idnum,t.manager_flag as managerFlag,t.manager_type as managerType,\n" +
                    "t.`online`,t.`password`,t.remarks,t.schoolid as schoolID,t.tel,t.token,t.update_time as updateTime  " +
                    " from tc_human_info t where t.human_type<>1 and t.schoolid="+schoolID +
                    " and t.update_time>DATE_FORMAT('"+date+"','%Y-%m-%d %H:%i:%s') " +
                    " UNION select t.humanid as humanID,t.applay_auth as applayAuth,t.atschool_flag as atschoolFlag,\n" +
                    " t.check_flag as checkFlag,t.classid as classID,t.clientid as clientID," +
                    " t.create_time as createTime,t.delete_flag as deleteFlag,t.gradeid as gradeID,t.human_name as humanName," +
                    " t.human_type as humanType,t.idnum,t.manager_flag as managerFlag,t.manager_type as managerType," +
                    " t.`online`,t.`password`,t.remarks,t.schoolid as schoolID,t.tel,t.token,t.update_time as updateTime from tc_human_info d,tc_parent_student_rel a,tc_parent_student_rel b,tc_human_info t   \n" +
                    "where d.create_time>DATE_FORMAT('"+date+"','%Y-%m-%d %H:%i:%s') \n" +
                    "and d.schoolid="+schoolID+" and d.human_type=0 and a.humanid=d.humanid and a.homeid=b.homeid and (t.humanid=b.humanid or t.humanid=b.homeid)" ;
            List<Map<String,Object>> humanInfoList = jdbcTemplate.queryForList(sql);

            //List<HumanMedia> humanMediaList = humanMediaService.findBySchoolIDAndUpdateTimeAfter(schoolID, startDate);
            sql = "select t.mediaid as mediaID,t.create_time as createTime,t.delete_flag as deleteFlag,\n" +
                    "t.feature,t.humanid as humanID,t.media_path as mediaPath,t.remarks,t.update_time as updateTime,\n" +
                    "t.schoolid as schoolID,t.check_flag as checkFlag \n" +
                    "from tc_human_media t where t.humanid in \n" +
                    "(select DISTINCT b.humanid as humanid  from tc_human_info t,tc_parent_student_rel a,tc_parent_student_rel b " +
                    "where t.human_type=0 and t.schoolid="+schoolID+" and a.humanid=t.humanid \n" +
                    " and b.homeid=a.homeid \n" +
                    "UNION \n" +
                    "select DISTINCT a.homeid as humanid  from tc_human_info t,tc_parent_student_rel a " +
                    "where t.human_type=0 and t.schoolid="+schoolID+" and a.humanid=t.humanid ) "  +
                    "and t.update_time>DATE_FORMAT('"+date+"','%Y-%m-%d %H:%i:%s')"+
                    "UNION select DISTINCT t.mediaid as mediaID,t.create_time as createTime,t.delete_flag as deleteFlag,\n" +
                    " t.feature,t.humanid as humanID,t.media_path as mediaPath,t.remarks,t.update_time as updateTime,"  +
                    " t.schoolid as schoolID,t.check_flag as checkFlag from tc_human_info a,tc_human_media t " +
                    " where t.humanid=a.humanid and a.human_type<>1 and a.schoolid="+schoolID+
                    " and t.update_time>DATE_FORMAT('"+date+"','%Y-%m-%d %H:%i:%s') " +
                    " UNION select DISTINCT t.mediaid as mediaID,t.create_time as createTime,t.delete_flag as deleteFlag,\n" +
                    " t.feature,t.humanid as humanID,t.media_path as mediaPath,t.remarks,t.update_time as updateTime," +
                    " t.schoolid as schoolID,t.check_flag as checkFlag " +
                    "from tc_human_info d,tc_parent_student_rel a,tc_parent_student_rel b,tc_human_media t " +
                    "where d.create_time>DATE_FORMAT('"+date+"','%Y-%m-%d %H:%i:%s') \n" +
                    "and d.schoolid="+schoolID+" and d.human_type=0 and a.humanid=d.humanid and a.homeid=b.homeid " +
                    "and (t.humanid=b.homeid or t.humanid=b.humanid)";
            List<Map<String,Object>> humanMediaList = jdbcTemplate.queryForList(sql);

            sql = "select DISTINCT b.relid as relid,b.check_flag as checkFlag,b.create_time as createTime,\n" +
                    "b.delete_flag as deleteFlag,b.homeid as homeID,b.humanid as humanID,b.remarks,\n" +
                    "b.update_time as updateTime,b.schoolID from tc_human_info t,tc_parent_student_rel a,\n" +
                    "tc_parent_student_rel b where t.human_type=0 and t.schoolid="+schoolID+" " +
                    "and t.humanid=a.humanid and a.homeid=b.homeid "+
                    " and t.update_time>DATE_FORMAT('"+date+"','%Y-%m-%d %H:%i:%s')";;
            List<Map<String,Object>> parenStudentRel = jdbcTemplate.queryForList(sql);
            //List<ParenStudentRel> parenStudentRel = pareStudentRelService.findBySchoolIDAndUpdateTimeAfter(schoolID, startDate);
            List<GradeInfo> gradeInfoList = gradeInfoService.findBySchoolIDAndUpdateTimeAfter(schoolID, startDate);
            List<RegionInfo> regionInfoList = regionInfoService.findByDeleteFlag(0);
            List<HumanType> humanTypeList = humanTypeService.findByDeleteFlag(0);
            List<ManagerType> managerTypeList = managerTypeService.findByDeleteFlag(0);

            List<Advert> advertList = advertService.findBySchoolIDAndUpdateTimeAfter(schoolID,startDate);


            // 给图片表和关系表也添加了学校字段，不用再去循环取每个人的信息了
            // 接送记录表也添加了学校字段；


            ResultInfo resultInfo = new ResultInfo(true);

            resultInfo.addData("school",school);
            resultInfo.addData("classInfoList",classInfoList);
            resultInfo.addData("humanInfoList",humanInfoList);
            resultInfo.addData("humanMediaList",humanMediaList);
            resultInfo.addData("parenStudentRelList",parenStudentRel);
            resultInfo.addData("gradeInfoList",gradeInfoList);
            resultInfo.addData("regionInfoList",regionInfoList);
            resultInfo.addData("humanTypeList",humanTypeList);
            resultInfo.addData("managerTypeList",managerTypeList);
            resultInfo.addData("advertList",advertList);
            resultInfo.setCode(200);
            resultInfo.setMessage("存在更新日期，成功查询到相关信息");

            findedMac.setUpdateTime(new Date()); // 重置更新时间
            macInfoService.save(findedMac);

            return resultInfo;
        }

    }

    /**
     * 检测mac 地址的合法性
     * @param
     * @return
     */
    @RequestMapping("/checkmac")
    @ResponseBody
    public ResultInfo checkMac(String macName){
        ResultInfo resultInfo = new ResultInfo(false);
        MacInfo mac = macInfoService.findByMacName(macName);
        if(mac!=null){
            resultInfo.addData("mac",mac);
            resultInfo.setSuccess(true);
            resultInfo.setCode(200);
            resultInfo.setMessage("该机器已授权！");
            return resultInfo;
        }
        resultInfo.setSuccess(false);
        resultInfo.setCode(400);
        resultInfo.setMessage("该机器未授权！");
        return resultInfo;
    }



}
