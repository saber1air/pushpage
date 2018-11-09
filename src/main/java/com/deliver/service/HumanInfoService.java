package com.deliver.service;

import com.aliyuncs.exceptions.ClientException;
import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.HumanInfoDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.HumanInfo;
import com.deliver.util.FlatSessionStore;
import com.deliver.util.RedisFlatSessionStore;
import com.deliver.util.ResultInfo;
import com.deliver.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class HumanInfoService {
    @Autowired
    private HumanInfoDao humanInfoDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public HumanInfo findByHumanID(int id) {
        return humanInfoDao.findByHumanIDAndDeleteFlag(id, 0);
    }

    public List<HumanInfo> findBySchoolID(int id) {
        return humanInfoDao.findBySchoolIDAndCheckFlag(id, 1);
    }

    public List<HumanInfo> findBySchoolIDAndUpdateTimeAfter(int id, Date date) {

        return humanInfoDao.findBySchoolIDAndUpdateTimeAfterAndCheckFlag(id, date, 1);
    }

    public HumanInfo save(HumanInfo humanInfo) {
        return humanInfoDao.save(humanInfo);
    }

    public HumanInfo findByHumanNameAndTel(String humanName, String tel) {
        return humanInfoDao.findByHumanNameAndTelAndDeleteFlag(humanName, tel, 0);
    }

    public HumanInfo findByHumanNameAndTelAndSchoolID(String humanName,String tel){
        return humanInfoDao.findByHumanNameAndTelAndDeleteFlag(humanName, tel,0);
    }

    public List<HumanInfo> findByTelAndPassword(String tel, String password) {
        return humanInfoDao.findByTelAndPasswordAndDeleteFlag(tel, password,0);
    }

    public List<Map<String,Object>> findByClassIDAndCheckFlagAndHumanType(int classID,int applyType){
        String sql = "";
        if(applyType==0){  //注册申请
            sql = "select a.humanid,a.human_name,a.human_type,a.manager_type,a.tel,a.create_time,b.mediaid,b.media_path \n" +
                    "from tc_human_info a,tc_human_media b where a.humanid=b.humanid and a.check_flag=0 and a.delete_flag=0 " +
                    "and a.human_type in (0,1) and a.classid="+classID;
        }else if(applyType==1){ //权限申请
            sql = "select a.humanid,a.human_name,a.human_type,a.manager_type,a.applay_auth,a.tel,a.create_time,b.mediaid,b.media_path \n" +
                    "from tc_human_info a,tc_human_media b where a.humanid=b.humanid and a.check_flag=0 and a.delete_flag=0 " +
                    "and a.human_type in (0,1) and a.classid="+classID;
        }
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String,Object>> findHumanByCheckFlag(int humanID,int applyType){
        String sql = "";
        if(applyType==0){  //注册申请
            sql = "select a.humanid,a.human_name,a.human_type,a.manager_type,a.tel,a.schoolid,a.create_time,b.mediaid,b.media_path \n" +
                    "from tc_human_info a,tc_human_media b,tc_parent_student_rel c where a.check_flag=0 and a.delete_flag=0  " +
                    "and c.homeid="+humanID+" and a.humanid=c.humanid and a.humanid=b.humanid and a.human_type=1";
        }else if(applyType==1){ //权限申请
            sql = "select a.humanid,a.human_name,a.human_type,a.applay_auth,a.manager_type,a.tel,a.schoolid,a.create_time,b.mediaid,b.media_path \n" +
                    "from tc_human_info a,tc_human_media b,tc_parent_student_rel c where a.check_flag=0 and a.delete_flag=0  " +
                    "and c.homeid="+humanID+" and a.humanid=c.humanid and a.humanid=b.humanid and a.human_type=1";
        }

        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String,Object>> findBySchoolIDAndCheckFlagAndHumanType(int schoolID,int applyType){
        String sql = "";
        if(applyType==0){
            sql = "select a.humanid,a.human_name,a.human_type,a.manager_type,a.tel,a.create_time,b.mediaid,b.media_path \n" +
                    "from tc_human_info a,tc_human_media b where a.humanid=b.humanid and a.check_flag=0 and a.delete_flag=0  " +
                    "and a.human_type=2 and a.schoolid="+schoolID;
        }else if(applyType==1){
            sql = "select a.humanid,a.human_name,a.human_type,a.manager_type,a.applay_auth,a.tel,a.create_time,b.mediaid,b.media_path \n" +
                    "from tc_human_info a,tc_human_media b where a.humanid=b.humanid and a.check_flag=0 and a.delete_flag=0  " +
                    "and a.human_type=2 and a.schoolid="+schoolID;
        }
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String,Object>> findByCheckFlagAndHumanType(int applyType){//
        String sql = "";
        if(applyType==0){//人员注册录入
            sql = "select a.humanid,a.human_name,a.human_type,a.manager_type,a.tel,a.create_time,b.mediaid,b.media_path \n" +
                    "from tc_human_info a,tc_human_media b where a.humanid=b.humanid and a.check_flag=0 and a.delete_flag=0  " +
                    "and a.human_type=3";
            return jdbcTemplate.queryForList(sql);
        }else if(applyType==1){//人员权限申请
            sql = "select a.humanid,a.human_name,a.human_type,a.manager_type,a.applay_auth,a.tel,a.create_time,b.mediaid,b.media_path \n" +
                    "from tc_human_info a,tc_human_media b where a.humanid=b.humanid and a.check_flag=0 and a.delete_flag=0  " +
                    "and a.human_type=3";
            return jdbcTemplate.queryForList(sql);
        }
        return null;
    }

    public int delete(int id) {
        return humanInfoDao.deleteByHumanID(id);
    }

    public HumanInfo findByTel(String tel) {
        if(humanInfoDao.findByTelAndDeleteFlag(tel,0)!=null && humanInfoDao.findByTelAndDeleteFlag(tel,0).size()>0){
            return humanInfoDao.findByTelAndDeleteFlag(tel,0).get(0);
        }
        return null;
    }

    public HumanInfo findByHumanNameAndSchoolIDAndGradeIDAndClassID(String humanName, int schoolID, int gradeID, int classID) {
        return humanInfoDao.findByHumanNameAndSchoolIDAndGradeIDAndClassIDAndDeleteFlag(humanName, schoolID, gradeID, classID,0);
    }

    public ResultInfo sendSms(int code, String tel) throws ClientException {
        ResultInfo resultInfo = new ResultInfo(false);
        SmsUtils smsUtils = new SmsUtils();

        boolean sendsms = smsUtils.sendsms(tel, code);
        if (sendsms) {
            resultInfo.setSuccess(true);
            resultInfo.setCode(200);
            resultInfo.setMessage("验证码发送成功！");
        } else {
            resultInfo.setSuccess(false);
            resultInfo.setCode(400);
            resultInfo.setMessage("验证码发送失败！");
        }
        return resultInfo;
    }

    public ResultInfo addHuman(HumanInfo human) {
        ResultInfo resultInfo = new ResultInfo(false);
        human.setAtschoolFlag(0);
        human.setCreateTime(new Date());
        human.setUpdateTime(new Date());
        human = humanInfoDao.save(human);
        resultInfo.addData("human",human);
        resultInfo.setCode(200);
        resultInfo.setMessage("新增人员成功！");
        resultInfo.setSuccess(true);

        return resultInfo;
    }

    public HumanInfo saveHuman(HumanInfo human) {
        human.setAtschoolFlag(0);
        human.setCreateTime(new Date());
        human.setUpdateTime(new Date());
        human = humanInfoDao.save(human);

        return human;
    }

    public ResultInfo editHuman(HumanInfo human) {
        ResultInfo resultInfo = new ResultInfo(false);
        if (human.getHumanID() != null) {
            human.setUpdateTime(new Date());
            humanInfoDao.save(human);
            resultInfo.setCode(200);
            resultInfo.setMessage("修改人员成功！");
            resultInfo.setSuccess(true);
        }
        return resultInfo;
    }

    public ResultInfo delHuman(int humanID) {
        ResultInfo resultInfo = new ResultInfo(false);
        humanInfoDao.deleteByHumanID(humanID);
        resultInfo.setCode(200);
        resultInfo.setMessage("删除人员成功！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    public ResultInfo findHuman(String humanName, Integer schoolID, Integer gradeID, Integer classID, String tel, Integer humanType,
                                Integer atSchoolFlag, Integer checkFlag, Integer managerType) {
        ResultInfo resultInfo = new ResultInfo(false);
        String sql = "";
        /*if (humanType == 0) {
            sql = "select t.humanid,t.human_name,t.password,t.human_type,t.tel,t.create_time,t.manager_type,t.atschool_flag,a.mediaid," +
                    "a.media_path,b.gradeid,b.grade_name,b.grade_num,c.classid,c.class_name,c.class_num,d.schoolid,d.school_name  \n" +
                    "from tc_human_info t,tc_human_media a,tc_grade_info b,tc_class_info c,tc_school d where 1=1 and t.humanid=a.humanid and \n" +
                    "t.schoolid=d.schoolid and t.gradeid=b.gradeid and t.classid=c.classid and t.delete_flag=0 and a.delete_flag=0 and t.check_flag " +
                    " and a.check_flag=1 ";




            if (schoolID != null && schoolID != -1) {
                sql += " and t.schoolid =" + schoolID;
            }
            if (gradeID != null && humanType == 0 && gradeID != -1) {
                sql += " and b.grade_num =" + gradeID;
            }
            if (classID != null && humanType == 0 && classID != -1) {
                sql += " and c.class_num =" + classID;
            }


        } else if (humanType > 0) {
            if (humanType == 4) {

                sql = "select t.humanid,t.human_name,t.human_type,t.password,t.tel,t.create_time,t.manager_type,a.mediaid,a.media_path" +
                        ",null as schoolid,null as school_name,null as gradeid,null as grade_name,null as grade_num,null as classid" +
                        ",null as class_name,null as class_num \n" +
                        "from tc_human_info t,tc_human_media a where 1=1 and t.humanid=a.humanid and t.delete_flag=0 ";


            }else if(humanType==3||humanType==2){
                sql = "select t.humanid,t.human_name,t.password,t.human_type,t.tel,t.create_time,t.manager_type,a.mediaid," +
                        "a.media_path,d.schoolid,d.school_name,null as gradeid,null as grade_name,null as grade_num,null as classid" +
                        ",null as class_name,null as class_num,t.atschool_flag \n" +
                        "from tc_human_info t,tc_human_media a,tc_school d where 1=1 and t.humanid=a.humanid and \n" +
                        "t.schoolid=d.schoolid and t.delete_flag=0 ";

                if (schoolID != null && schoolID != -1) {
                    sql += " and t.schoolid =" + schoolID;
                }
                if (gradeID != null && humanType == 0 && gradeID != -1) {
                    sql += " and b.grade_num =" + gradeID;
                }
                if (classID != null && humanType == 0 && classID != -1) {
                    sql += " and c.class_num =" + classID;
                }
            }else if(humanType==1){
                sql = "select t.humanid,t.human_name,t.password,t.human_type,t.tel,t.create_time,t.manager_type,t.atschool_flag,a.mediaid," +
                        "a.media_path,null as gradeid,null as grade_name,null as grade_num,null as classid" +
                        ",null as class_name,null as class_num,d.schoolid,d.school_name  \n" +
                        "from tc_human_info t,tc_human_media a,tc_school d where 1=1 and t.humanid=a.humanid and \n" +
                        "t.schoolid=d.schoolid and t.delete_flag=0 ";


                if (schoolID != null && schoolID != -1) {
                    sql += " and t.schoolid =" + schoolID;
                }
                if (gradeID != null && humanType == 0 && gradeID != -1) {
                    sql += " and b.grade_num =" + gradeID;
                }
                if (classID != null && humanType == 0 && classID != -1) {
                    sql += " and c.class_num =" + classID;
                }
            }else{
                sql = "select t.humanid,t.human_name,t.human_type,t.password,t.tel,t.create_time,t.manager_type,a.mediaid,a.media_path,t.atschool_flag \n" +
                        "from tc_human_info t,tc_human_media a where 1=1 and t.humanid=a.humanid and t.delete_flag=0 ";

            }
        }
*/
        sql = "select t.humanid,t.human_name,t.password,t.human_type,t.tel,t.create_time,t.manager_type,t.atschool_flag,\n" +
                "a.mediaid,a.media_path,b.gradeid,b.grade_name,b.grade_num,c.classid,c.class_name,c.class_num,d.schoolid,\n" +
                "d.school_name from tc_human_media a,tc_human_info t LEFT JOIN tc_school d on t.schoolid=d.schoolid \n" +
                "LEFT JOIN tc_grade_info b on t.gradeid=b.gradeid LEFT JOIN tc_class_info c on t.classid=c.classid \n" +
                "where t.check_flag=1 and a.check_flag=1 and t.delete_flag=0 and t.humanid=a.humanid and a.delete_flag=0 ";
        if(humanType==0 || humanType==2 || humanType==3){
            if (schoolID != null && schoolID != -1) {
                sql += " and t.schoolid =" + schoolID;
            }
        }

        if(humanType==0 || humanType==2){
            if (gradeID != null && gradeID != -1) {
                sql += " and b.gradeID =" + gradeID;
            }
        }

        if(humanType==0 || humanType==2){
            if (classID != null && classID != -1) {
                sql += " and c.classID =" + classID;
            }
        }

        if (checkFlag != null && humanType > 0 && checkFlag != -1) {
            sql += " and t.check_flag =" + checkFlag;
        }
        if (humanName != null && humanName != "") {
            sql += " and t.human_name like '%" + humanName + "%'";
        }

        if (tel != null && humanType > 0 && tel != "") {
            sql += " and t.tel ='" + tel + "'";
        }
        if (humanType != null && humanType != -1) {
            sql += " and t.human_type =" + humanType;
        }
        if (atSchoolFlag != null && atSchoolFlag != -1) {
            sql += " and t.atschool_flag =" + atSchoolFlag;
        }
        if (managerType != null && managerType != -1) {
            sql += " and t.manager_type =" + managerType;
        }

        sql += " ORDER BY t.humanid";
        List<Map<String, Object>> humanlist = new ArrayList<Map<String,Object>>();
        if(humanType>=0){
            List<Map<String, Object>> humanlist1 = jdbcTemplate.queryForList(sql);
            if(humanlist1!=null && humanlist1.size()>0){
                int humanid = 0;
                humanlist = new ArrayList<Map<String,Object>>();
                for(Map<String, Object> map : humanlist1){
                    if((Integer)map.get("humanid")!=humanid){
                        humanlist.add(map);
                        humanid= (Integer)map.get("humanid");
                    }

                }
            }

            resultInfo.addData("humanlist", humanlist);
        }


        resultInfo.setCode(200);
        resultInfo.setMessage("查询成功！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    public ResultInfo findApplayManager(Integer classID) {
        ResultInfo resultInfo = new ResultInfo(false);
        String sql = "select a.humanid,a.human_name,a.human_type,a.applay_auth,a.check_flag,a.tel,b.mediaid," +
                "b.media_path,c.operate_humid from tc_human_info a,tc_human_media b,tc_manager_register_record c " +
                "where a.delete_flag=0 and b.delete_flag=0 and c.delete_flag=0 and a.check_flag=0 and " +
                "a.humanid=b.humanid and a.humanid=c.reg_managerid and c.check_flag=0 and c.classid=" + classID;
        List<Map<String, Object>> humanlist = jdbcTemplate.queryForList(sql);
        resultInfo.addData("humanlist", humanlist);
        resultInfo.setCode(200);
        resultInfo.setMessage("查询成功！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    public List<Map<String, Object>> findHumanRel(HumanInfo human){
        /*String sql = "select t.humanid,t.human_name,t.tel,t.human_type,c.media_path,t.check_flag from tc_human_info t,tc_parent_student_rel a,tc_human_media c \n" +
                "where t.humanid=a.humanid and t.humanid=c.humanid and t.check_flag=1 and t.delete_flag=0" +
                " and a.check_flag=1 and a.delete_flag=0 and c.check_flag=1 and c.delete_flag=0 and a.homeid in \n" +
                "(select DISTINCT b.homeid from tc_parent_student_rel b " +
                "where b.delete_flag=0 and b.check_flag=1 and b.humanid = "+human.getHumanID()+" or b.homeid = "+human.getHumanID()+") GROUP BY t.humanid\n" +
                "UNION (select t1.humanid,t1.human_name,t1.tel,t1.human_type,c1.media_path,t1.check_flag \n" +
                "from tc_human_info t1,tc_parent_student_rel a1,tc_human_media c1 \n" +
                "where t1.check_flag=1 and t1.delete_flag=0 and t1.humanid=a1.homeid" +
                " and a1.check_flag=1 and a1.delete_flag=0 " +
                " and c1.check_flag=1 and c1.delete_flag=0 " +
                " and t1.humanid=c1.humanid and a1.homeid in (select DISTINCT b1.homeid \n" +
                "from tc_parent_student_rel b1 where b1.check_flag=1 and b1.delete_flag=0 " +
                " and b1.humanid = "+human.getHumanID()+" or b1.homeid = "+human.getHumanID()+") GROUP BY t1.humanid)";*/
        String sql = "select DISTINCT t.humanid,t.human_name,t.human_type,t.manager_type,t.create_time,t.applay_auth,t.atschool_flag,t.tel,t.`password` ,b.mediaid,b.media_path,\n" +
                " c.schoolid,c.school_name,d.gradeid,d.grade_name,e.classid,e.class_name\n" +
                " from tc_parent_student_rel a,tc_human_info t LEFT JOIN tc_human_media b on t.humanid=b.humanid LEFT JOIN tc_school c on t.schoolid=c.schoolid \n" +
                " LEFT JOIN tc_grade_info d on t.gradeid=d.gradeid LEFT JOIN tc_class_info e on t.classid=e.classid where a.homeid in \n" +
                " (select DISTINCT homeid from tc_parent_student_rel where (homeid="+human.getHumanID()+" or humanid="+human.getHumanID()+")) and t.humanid<>"+human.getHumanID()+" \n" +
                " and (t.humanid=a.humanid or t.humanid=a.homeid) and t.humanid=b.humanid and t.check_flag=1 and t.delete_flag=0 and a.check_flag=1 \n" +
                " and a.delete_flag=0 and (b.check_flag=1 or b.check_flag=null) and (b.delete_flag=0 or b.delete_flag=null)";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> teacherFindNoAtSchool(HumanInfo human){
        String sql = "select a.humanid,a.human_name,a.human_type,b.mediaid,b.media_path," +
                " d.humanid as parentid,d.human_name as parent_name,d.tel from " +
                " tc_human_info a,tc_human_media b,tc_parent_student_rel c,tc_human_info d \n" +
                " where a.atschool_flag=0 and a.check_flag=1 and a.delete_flag=0 " +
                " and a.human_type=0 and a.classid=" +human.getClassID()+
                " and a.humanid=b.humanid and a.humanid=c.humanid and c.homeid=d.humanid ORDER BY a.humanid";
        List<Map<String,Object>> humanlist1 = jdbcTemplate.queryForList(sql);
        List<Map<String,Object>> humanlist = new ArrayList<Map<String,Object>>();
        if(humanlist1!=null && humanlist1.size()>0){
            humanlist = new ArrayList<Map<String,Object>>();
            int humanid =0;
            for(Map<String,Object> map : humanlist1){
                if((Integer) map.get("humanid")!=humanid){
                    humanlist.add(map);
                }
            }
        }
        return humanlist;
    }

    public List<Map<String, Object>> teacherFindAtSchool(HumanInfo human){
        String sql = "select a.humanid,a.human_name,a.human_type,b.mediaid,b.media_path," +
                " d.humanid as parentid,d.human_name as parent_name,d.tel " +
                " from tc_human_info a,tc_human_media b,tc_parent_student_rel c,tc_human_info d \n" +
                " where a.atschool_flag=1 and a.check_flag=1 and a.delete_flag=0 " +
                " and a.human_type=0 and a.classid=" +human.getClassID()+
                " and a.humanid=b.humanid and a.humanid=c.humanid and c.homeid=d.humanid ORDER BY a.humanid";
        List<Map<String,Object>> humanlist1 = jdbcTemplate.queryForList(sql);
        List<Map<String,Object>> humanlist = new ArrayList<Map<String,Object>>();
        if(humanlist1!=null && humanlist1.size()>0){
            humanlist = new ArrayList<Map<String,Object>>();
            int humanid =0;
            for(Map<String,Object> map : humanlist1){
                if((Integer) map.get("humanid")!=humanid){
                    humanlist.add(map);
                }
            }
        }
        return humanlist;
    }




    public int getCode() {
        int code = (int) (Math.random() * 9999) + 1000;  //每次调用生成一次四位数的随机数
        return code;
    }

}
