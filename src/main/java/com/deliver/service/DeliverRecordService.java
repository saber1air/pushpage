package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.DeliverRecordDao;
import com.deliver.dao.GeTuiRecordDao;
import com.deliver.dao.HumanInfoDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.DeliverRecord;
import com.deliver.entity.GeTueRecord;
import com.deliver.entity.HumanInfo;
import com.deliver.mapbody.DeliverRecordParam;
import com.deliver.util.AppPushUtils;
import com.deliver.util.ResultInfo;
import com.gexin.rp.sdk.base.IPushResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class DeliverRecordService {

    private static String appId = "jPX0kbnuCc8Og0gmSBnu3";
    private static String appKey = "Og6qT7rLNN9fixE8O7ppR4";
    private static String masterSecret = "x28EanpNuRA1fMjoDS1Tv9";

    @Autowired
    private DeliverRecordDao deliverRecordDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HumanInfoDao humanInfoDao;

    @Autowired
    private GeTuiRecordDao geTuiRecordDao;

    public DeliverRecord findByDeliverID(int id) {
        return deliverRecordDao.findByDeliverID(id);
    }

    public DeliverRecord save(DeliverRecord deliverRecord) {
        deliverRecord.setDeliverTime(new Date());
        deliverRecord.setDeleteFlag(0);
        return deliverRecordDao.save(deliverRecord);
    }

    public DeliverRecord insert(DeliverRecord deliverRecord) {
        deliverRecord.setDeleteFlag(0);
        return deliverRecordDao.save(deliverRecord);
    }

    public int delete(int id) {
        return deliverRecordDao.deleteByDeliverID(id);
    }

    public List<Map<String, Object>> deliverRecordQuery(DeliverRecordParam deliverRecordParam) {
        String sql = "";
        if (deliverRecordParam.getHumanType() == 0) {
            sql = "select y.parent_name,x.deliver_type,x.access_type,x.deliver_time,x.media,x.message,x.checkresult,x.student_name,x.school_name,x.grade_name,x.class_name\n" +
                    "from (select a.deliverid,b.human_name as student_name,a.deliver_type,a.access_type,a.deliver_time,a.media,a.message,a.checkresult,\n" +
                    "c.school_name,d.grade_name,e.class_name from tc_deliver_record a,\n" +
                    "tc_human_info b,tc_school c,tc_grade_info d,tc_class_info e where 1=1 and a.studentid=b.humanid \n" +
                    "and a.schoolid=c.schoolid and b.gradeid=d.gradeid and b.classid=e.classid and b.human_type=0 ";
            if (deliverRecordParam.getHumanName() != null && deliverRecordParam.getHumanName() != "") {
                sql += " and b.human_name='" + deliverRecordParam.getHumanName() + "'";
            }
            if (deliverRecordParam.getAccessType() != null && deliverRecordParam.getAccessType() != -1) {
                sql += " and a.access_type=" + deliverRecordParam.getAccessType();
            }
            if (deliverRecordParam.getDeliverType() != null && deliverRecordParam.getDeliverType() != -1) {
                sql += " and a.deliver_type=" + deliverRecordParam.getDeliverType();
            }
            if (deliverRecordParam.getCheckResult() != null && deliverRecordParam.getCheckResult() != -1) {
                sql += " and a.checkresult=" + deliverRecordParam.getCheckResult();
            }
            if (deliverRecordParam.getSchoolID() != null && deliverRecordParam.getSchoolID() != -1) {
                sql += " and b.schoolid=" + deliverRecordParam.getSchoolID();
            }
            if (deliverRecordParam.getGradeID() != null && deliverRecordParam.getGradeID() != -1) {
                sql += " and b.gradeid=" + deliverRecordParam.getGradeID();
            }
            if (deliverRecordParam.getClassID() != null && deliverRecordParam.getClassID() != -1) {
                sql += " and b.classid=" + deliverRecordParam.getClassID();
            }
            if (deliverRecordParam.getBeginTime() != null && deliverRecordParam.getBeginTime() != "") {
                sql += " and a.deliver_time>=DATE_FORMAT('" + deliverRecordParam.getBeginTime() + "','%Y-%m-%d') ";
            }
            if (deliverRecordParam.getEndTime() != null && deliverRecordParam.getEndTime() != "") {
                sql += " and a.deliver_time<=DATE_FORMAT('" + deliverRecordParam.getEndTime() + "','%Y-%m-%d') ";
            }
            sql += ") x LEFT JOIN (select s.deliverid,h.human_name as parent_name from tc_deliver_record s,tc_human_info h " +
                    "where s.parentid=h.humanid) y on x.deliverid=y.deliverid ORDER BY x.deliver_time DESC";
        } else if (deliverRecordParam.getHumanType() == 1) {
            sql = "select x.parent_name,x.deliver_type,x.access_type,x.deliver_time,x.media,x.message,x.checkresult,y.student_name,x.school_name \n" +
                    "from (select a.deliverid,b.human_name as parent_name,a.deliver_type,a.access_type,a.deliver_time,a.media,a.message,a.checkresult,\n" +
                    "c.school_name from tc_deliver_record a,\n" +
                    "tc_human_info b,tc_school c where 1=1 and a.parentid=b.humanid \n" +
                    "and a.schoolid=c.schoolid and b.human_type=1 ";
            if (deliverRecordParam.getHumanName() != null && deliverRecordParam.getHumanName() != "") {
                sql += " and b.human_name='" + deliverRecordParam.getHumanName() + "'";
            }
            if (deliverRecordParam.getAccessType() != null && deliverRecordParam.getAccessType() != -1) {
                sql += " and a.access_type=" + deliverRecordParam.getAccessType();
            }
            if (deliverRecordParam.getDeliverType() != null && deliverRecordParam.getDeliverType() != -1) {
                sql += " and a.deliver_type=" + deliverRecordParam.getDeliverType();
            }
            if (deliverRecordParam.getCheckResult() != null && deliverRecordParam.getCheckResult() != -1) {
                sql += " and a.checkresult=" + deliverRecordParam.getCheckResult();
            }
            if (deliverRecordParam.getBeginTime() != null && deliverRecordParam.getBeginTime() != "") {
                sql += " and a.deliver_time>=DATE_FORMAT('" + deliverRecordParam.getBeginTime() + "','%Y-%m-%d') ";
            }
            if (deliverRecordParam.getEndTime() != null && deliverRecordParam.getEndTime() != "") {
                sql += " and a.deliver_time<=DATE_FORMAT('" + deliverRecordParam.getEndTime() + "','%Y-%m-%d') ";
            }
            sql += ") x LEFT JOIN (select s.deliverid,h.human_name as student_name from tc_deliver_record s,tc_human_info h " +
                    "where s.studentid=h.humanid) y on x.deliverid=y.deliverid ORDER BY x.deliver_time DESC";
        } else if (deliverRecordParam.getHumanType() > 1) {
            sql = "select x.parent_name,x.deliver_type,x.access_type,x.deliver_time,x.media,x.message,x.checkresult,y.student_name,x.school_name \n" +
                    "from (select a.deliverid,b.human_name as parent_name,a.deliver_type,a.access_type,a.deliver_time,a.media,a.message,a.checkresult,\n" +
                    "c.school_name from tc_deliver_record a,\n" +
                    "tc_human_info b,tc_school c where 1=1 and a.parentid=b.humanid \n" +
                    "and a.schoolid=c.schoolid ";
            if (deliverRecordParam.getHumanName() != null && deliverRecordParam.getHumanName() != "") {
                sql += " and b.human_name='" + deliverRecordParam.getHumanName() + "'";
            }
            if (deliverRecordParam.getHumanType() != null && deliverRecordParam.getHumanType() != -1) {
                sql += " and b.human_type=" + deliverRecordParam.getHumanType();
            }
            if (deliverRecordParam.getAccessType() != null && deliverRecordParam.getAccessType() != -1) {
                sql += " and a.access_type=" + deliverRecordParam.getAccessType();
            }
            if (deliverRecordParam.getDeliverType() != null && deliverRecordParam.getDeliverType() != -1) {
                sql += " and a.deliver_type=" + deliverRecordParam.getDeliverType();
            }
            if (deliverRecordParam.getCheckResult() != null && deliverRecordParam.getCheckResult() != -1) {
                sql += " and a.checkresult=" + deliverRecordParam.getCheckResult();
            }
            if (deliverRecordParam.getBeginTime() != null && deliverRecordParam.getBeginTime() != "") {
                sql += " and a.deliver_time>=DATE_FORMAT('" + deliverRecordParam.getBeginTime() + "','%Y-%m-%d') ";
            }
            if (deliverRecordParam.getEndTime() != null && deliverRecordParam.getEndTime() != "") {
                sql += " and a.deliver_time<=DATE_FORMAT('" + deliverRecordParam.getEndTime() + "','%Y-%m-%d') ";
            }
            sql += ") x LEFT JOIN (select s.deliverid,h.human_name as student_name from tc_deliver_record s,tc_human_info h " +
                    "where s.studentid=h.humanid) y on x.deliverid=y.deliverid  ORDER BY x.deliver_time DESC";
        }
        List<Map<String, Object>> deliverlist = jdbcTemplate.queryForList(sql);
        return deliverlist;
    }

    public ResultInfo deliverGeTuiAM() {//早上九点入园通知
        List<HumanInfo> humanlist = humanInfoDao.findByCheckFlagAndHumanTypeAndDeleteFlag(1, 0, 0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String content = "";
        ResultInfo resultInfo = new ResultInfo(false);
        if (humanlist != null && humanlist.size() > 0) {
            for (HumanInfo human : humanlist) {
                if (human.getAtschoolFlag() == 0) {
                    content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" + date + "未入园";
                } else if(human.getAtschoolFlag() == 1){
                    String sql = "select * from (select t.deliverid,t.checkresult,t.access_type,t.deliver_time," +
                            "t.deliver_type,t.media,t.message,t.operateid,t.schoolid,a.human_name from tc_deliver_record t\n" +
                            "LEFT JOIN tc_human_info a on  t.parentid=a.humanid where t.studentid=" + human.getHumanID() + " and " +
                            "t.access_type=1 and t.deliver_time>=DATE_FORMAT('" + date + "','%Y-%m-%d') " +
                            "ORDER BY deliver_time DESC LIMIT 1) x\n";
                    List<Map<String, Object>> geTuiHumanList = jdbcTemplate.queryForList(sql);

                    if (geTuiHumanList != null && geTuiHumanList.size() > 0) {
                        Integer deliverType = (Integer) geTuiHumanList.get(0).get("deliver_type");
                        if (deliverType == 1) {
                            if (geTuiHumanList.get(0).get("human_name") != null) {
                                if (geTuiHumanList.get(0).get("human_name") == "陌生人") {
                                    content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" +
                                            geTuiHumanList.get(0).get("deliver_time") + "由系统未录入家属接送入园";
                                }else {
                                    content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" +
                                            geTuiHumanList.get(0).get("deliver_time") + "由" + geTuiHumanList.get(0).get("human_name") + "接送入园";
                                }

                            } else {
                                content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" +
                                        geTuiHumanList.get(0).get("deliver_time") + "独自入园";
                            }
                        } else if (deliverType == 2) {
                            content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" + geTuiHumanList.get(0).get("deliver_time") + "由校车接送入园";
                        } else {
                            content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" +
                                    geTuiHumanList.get(0).get("deliver_time") + "独自入园";
                        }
                        /*String ssql = "select t.humanid,t.human_name,t.tel,t.human_type,c.media_path from tc_human_info t,tc_parent_student_rel a,tc_human_media c \n" +
                                "where t.humanid=a.humanid and t.humanid=c.humanid and a.homeid in \n" +
                                "(select DISTINCT b.homeid from tc_parent_student_rel b " +
                                "where b.humanid = "+human.getHumanID()+" or b.homeid = "+human.getHumanID()+") GROUP BY t.humanid\n" +
                                "UNION (select t1.humanid,t1.human_name,t1.tel,t1.human_type,c1.media_path \n" +
                                "from tc_human_info t1,tc_parent_student_rel a1,tc_human_media c1 \n" +
                                "where t1.humanid=a1.homeid and t1.humanid=c1.humanid and a1.homeid in (select DISTINCT b1.homeid \n" +
                                "from tc_parent_student_rel b1 where b1.humanid = "+human.getHumanID()+" or b1.homeid = "+human.getHumanID()+") GROUP BY t1.humanid)";
*/

                    }else{
                        content = "尊敬的家长，您的小孩" + human.getHumanName() + "昨天未出园，今天于" + date + "未入园";
                    }
                }else{
                    content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" + date + "未入园";
                }

                String ssql = "select b.human_name,b.humanid,b.tel,b.clientid from tc_parent_student_rel a,tc_human_info b where a.humanid="+ human.getHumanID() +
                        " and a.homeid=b.humanid and b.delete_flag=0 and a.delete_flag=0 and a.check_flag=1 and b.check_flag=1";

                List<Map<String, Object>> parentList = jdbcTemplate.queryForList(ssql);
                Map<String, String> msg = new HashMap<String, String>();
                msg.put("title", "入园消息！");
                msg.put("titleText", content);
                msg.put("transText", "");


                AppPushUtils pushUtils = new AppPushUtils(appId, appKey, masterSecret);

                if (parentList != null && parentList.size() > 0) {
                    for (Map map : parentList) {
                        System.out.println("正在发送消息...");
                        if (map.get("clientid") != null) {
                            IPushResult ret = pushUtils.pushMsgToSingle(map.get("clientid").toString(), msg);
                            System.out.println(ret.getResponse().toString());
                            GeTueRecord GeTueRecord = new GeTueRecord();
                            GeTueRecord.setGetuiTime(new Date());
                            GeTueRecord.setMessage(content);
                            GeTueRecord.setDeleteFlag(0);
                            GeTueRecord.setParentID((Integer) map.get("humanid"));
                            GeTueRecord.setSchoolID(human.getSchoolID());
                            GeTueRecord.setStudentID(human.getHumanID());
                            GeTueRecord.setAccessType(1);
                            geTuiRecordDao.save(GeTueRecord);
                        }
                    }
                }

                //String[] cids = {"7a419805f2efaab701829dbac68739f7"};
                /*String[] cids = {"3eed2a88629645b825e5a221eddfe1d4"};

                for (String cid : cids) {
                    System.out.println("正在发送消息...");
                    IPushResult ret = pushUtils.pushMsgToSingle(cid, msg);
                    System.out.println(ret.getResponse().toString());
                    GeTueRecord GeTueRecord = new GeTueRecord();
                    GeTueRecord.setGetuiTime(new Date());
                    GeTueRecord.setMessage(content);
                    GeTueRecord.setDeleteFlag(0);
                    GeTueRecord.setAccessType(1);
                    geTuiRecordDao.save(GeTueRecord);
                }*/
            }
        }
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        resultInfo.setMessage("入园通知发送成功！");

        return resultInfo;
    }

    public ResultInfo deliverGeTuiPM() {//离园通知
        List<HumanInfo> humanlist = humanInfoDao.findByCheckFlagAndHumanTypeAndDeleteFlag(1, 0, 0);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String content = "";
        ResultInfo resultInfo = new ResultInfo(false);
        if (humanlist != null && humanlist.size() > 0) {
            for (HumanInfo human : humanlist) {
                if (human.getAtschoolFlag() == 1) {
                    content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" + date + "未离园";
                } else if (human.getAtschoolFlag() == 0) {
                    String sql = "select * from (select t.deliverid,t.checkresult,t.access_type,t.deliver_time," +
                            "t.deliver_type,t.media,t.message,t.operateid,t.schoolid,a.human_name from tc_deliver_record t\n" +
                            "LEFT JOIN tc_human_info a on  t.parentid=a.humanid where t.studentid=" + human.getHumanID() + " and " +
                            "t.access_type=0 and t.deliver_time>=DATE_FORMAT('" + date + "','%Y-%m-%d') " +
                            "ORDER BY deliver_time DESC LIMIT 1) x\n";
                    List<Map<String, Object>> geTuiHumanList = jdbcTemplate.queryForList(sql);

                    if (geTuiHumanList != null && geTuiHumanList.size() > 0) {
                        Integer deliverType = (Integer) geTuiHumanList.get(0).get("deliver_type");
                        if (deliverType == 1) {
                            if (geTuiHumanList.get(0).get("human_name") != null) {
                                if (geTuiHumanList.get(0).get("human_name") == "陌生人") {
                                    content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" +
                                            geTuiHumanList.get(0).get("deliver_time") + "由系统未录入家属接送出园";
                                }else {
                                    content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" +
                                            geTuiHumanList.get(0).get("deliver_time") + "由" + geTuiHumanList.get(0).get("human_name") + "接送出园";
                                }

                            } else {
                                content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" +
                                        geTuiHumanList.get(0).get("deliver_time") + "独自出园";
                            }
                        } else if (deliverType == 2) {
                            content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" + geTuiHumanList.get(0).get("deliver_time") + "由校车接送出园";
                        } else {
                            content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" +
                                    geTuiHumanList.get(0).get("deliver_time") + "独自出园";
                        }
                        /*String ssql = "select t.humanid,t.human_name,t.tel,t.human_type,c.media_path from tc_human_info t,tc_parent_student_rel a,tc_human_media c \n" +
                                "where t.humanid=a.humanid and t.humanid=c.humanid and a.homeid in \n" +
                                "(select DISTINCT b.homeid from tc_parent_student_rel b " +
                                "where b.humanid = "+human.getHumanID()+" or b.homeid = "+human.getHumanID()+") GROUP BY t.humanid\n" +
                                "UNION (select t1.humanid,t1.human_name,t1.tel,t1.human_type,c1.media_path \n" +
                                "from tc_human_info t1,tc_parent_student_rel a1,tc_human_media c1 \n" +
                                "where t1.humanid=a1.homeid and t1.humanid=c1.humanid and a1.homeid in (select DISTINCT b1.homeid \n" +
                                "from tc_parent_student_rel b1 where b1.humanid = "+human.getHumanID()+" or b1.homeid = "+human.getHumanID()+") GROUP BY t1.humanid)";
*/

                    }else{
                        content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" + date + "没有进出园信息。";
                    }
                }else{
                    content = "尊敬的家长，您的小孩" + human.getHumanName() + "于" + date + "没有进出园信息。";
                }
                String ssql = "select b.human_name,b.humanid,b.tel,b.clientid from tc_parent_student_rel a,tc_human_info b where a.humanid="+ human.getHumanID() +
                        " and a.homeid=b.humanid and b.delete_flag=0 and a.delete_flag=0 and a.check_flag=1 and b.check_flag=1";

                List<Map<String, Object>> parentList = jdbcTemplate.queryForList(ssql);
                Map<String, String> msg = new HashMap<String, String>();
                msg.put("title", "出园消息！");
                msg.put("titleText", content);
                msg.put("transText", "");

                /*String appId = "jPX0kbnuCc8Og0gmSBnu3";
                String appKey = "Og6qT7rLNN9fixE8O7ppR4";
                String masterSecret = "x28EanpNuRA1fMjoDS1Tv9";*/
                AppPushUtils pushUtils = new AppPushUtils(appId, appKey, masterSecret);

                if (parentList != null && parentList.size() > 0) {
                    for (Map map : parentList) {
                        System.out.println("正在发送消息...");
                        if (map.get("clientid") != null) {
                            IPushResult ret = pushUtils.pushMsgToSingle(map.get("clientid").toString(), msg);
                            System.out.println(ret.getResponse().toString());
                            GeTueRecord GeTueRecord = new GeTueRecord();
                            GeTueRecord.setGetuiTime(new Date());
                            GeTueRecord.setMessage(content);
                            GeTueRecord.setDeleteFlag(0);
                            GeTueRecord.setParentID((Integer) map.get("humanid"));
                            GeTueRecord.setSchoolID(human.getSchoolID());
                            GeTueRecord.setStudentID(human.getHumanID());
                            GeTueRecord.setAccessType(0);
                            geTuiRecordDao.save(GeTueRecord);
                        }
                    }
                }

                /*String[] cids = {"3eed2a88629645b825e5a221eddfe1d4"};

                for (String cid : cids) {
                    System.out.println("正在发送消息...");
                    IPushResult ret = pushUtils.pushMsgToSingle(cid, msg);
                    System.out.println(ret.getResponse().toString());
                    GeTueRecord GeTueRecord = new GeTueRecord();
                    GeTueRecord.setGetuiTime(new Date());
                    GeTueRecord.setMessage(content);
                    GeTueRecord.setDeleteFlag(0);
                    GeTueRecord.setAccessType(1);
                    geTuiRecordDao.save(GeTueRecord);
                }*/
            }
        }
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        resultInfo.setMessage("出园通知发送成功！");

        return resultInfo;
    }

    public ResultInfo deliverRecordQueryByAdult(int humanid,String humanName,int humanType,String beginTime,String endTime){
        String sql = "";
        String ssql =  "";
        ResultInfo resultInfo = new ResultInfo(false);
        if(humanType==1){
            sql = "select DISTINCT c.humanid,c.human_name from tc_parent_student_rel t,tc_human_info c " +
                    "where t.homeid in (select a.homeid from tc_parent_student_rel a " +
                    "where (a.homeid="+humanid+" or a.humanid="+humanid+")) and \n" +
                    " t.humanid=c.humanid and c.human_type=0";
            List<Map<String,Object>> relhumanlist = jdbcTemplate.queryForList(sql);
            if(humanName!=null && !humanName.equals("")){
                if(relhumanlist!=null && relhumanlist.size()>0){
                    for(Map human:relhumanlist){
                        if(human.get("human_name").equals(humanName)){
                            ssql = "select a.deliverid,a.deliver_time,a.deliver_type,a.access_type,a.checkresult," +
                                    "a.media,a.message,a.studentid,b.human_name as student_name,a.parentid," +
                                    "c.human_name as parent_name\n" +
                                    "from tc_deliver_record a LEFT JOIN tc_human_info c on a.parentid=c.humanid," +
                                    "tc_human_info b where a.studentid="+human.get("humanid")+" and a.studentid=b.humanid ";
                            if(beginTime!=null && !beginTime.equals("")){
                                ssql +=" and a.deliver_time>DATE_FORMAT('"+beginTime+"','%Y-%m-%d %h:%i:%s') ";
                            }

                            if(endTime!=null && !endTime.equals("")){
                                ssql +=" and a.deliver_time<DATE_FORMAT('"+endTime+"','%Y-%m-%d %h:%i:%s') ";
                            }

                            ssql +=" ORDER BY a.deliver_time DESC";


                        }
                    }
                }
            }else{
                ssql = "select d.deliverid,d.deliver_time,d.deliver_type,d.access_type,d.checkresult,d.media,\n" +
                        "d.message,d.studentid,b.human_name as student_name,d.parentid,c.human_name as parent_name \n" +
                        "from tc_parent_student_rel t,tc_human_info b,tc_deliver_record d LEFT JOIN tc_human_info c on c.humanid=d.parentid \n" +
                        "where t.homeid in (select t.homeid from tc_parent_student_rel a \n" +
                        "where (a.homeid="+humanid+" or a.humanid="+humanid+")) " +
                        "and t.humanid=b.humanid and b.human_type=0 and d.studentid=b.humanid ";
                if(beginTime!=null && !beginTime.equals("")){
                    ssql +=" and d.deliver_time>DATE_FORMAT('"+beginTime+"','%Y-%m-%d %h:%i:%s') ";
                }

                if(endTime!=null && !endTime.equals("")){
                    ssql +=" and d.deliver_time<DATE_FORMAT('"+endTime+"','%Y-%m-%d %h:%i:%s') ";
                }

                ssql +=" ORDER BY d.deliver_time DESC";

            }
        }else if(humanType==2){
            HumanInfo humanInfo = humanInfoDao.findByHumanIDAndDeleteFlag(humanid,0);
            if(humanName!=null && !humanName.equals("")){
                ssql = "select a.deliverid,a.deliver_time,a.deliver_type,a.access_type,a.checkresult,a.media," +
                        "a.message,a.studentid,b.human_name as student_name,a.parentid,c.human_name as parent_name \n" +
                        "from tc_deliver_record a LEFT JOIN tc_human_info c on a.parentid=c.humanid,tc_human_info b " +
                        "where b.humanid=a.studentid and b.human_type=0 and b.delete_flag=0 and b.schoolid=" +humanInfo.getSchoolID()+
                        " and b.human_name like '%"+humanName+"%'";
                if(beginTime!=null && !beginTime.equals("")){
                    ssql +=" and a.deliver_time>DATE_FORMAT('"+beginTime+"','%Y-%m-%d %h:%i:%s') ";
                }

                if(endTime!=null && !endTime.equals("")){
                    ssql +=" and a.deliver_time<DATE_FORMAT('"+endTime+"','%Y-%m-%d %h:%i:%s') ";
                }

                ssql +=" ORDER BY a.deliver_time DESC";
            }else{
                resultInfo.setCode(400);
                resultInfo.setSuccess(false);
                resultInfo.setMessage("输入名字为空，请指定人员查询！");
                return  resultInfo;
            }
        }

        System.out.println("humanType:"+humanType);
        System.out.println("ssql:"+ssql);

        List<Map<String,Object>> deliverrecordlist = jdbcTemplate.queryForList(ssql);
        if(ssql!=null && ssql != "" && !ssql.equals("")){
            //List<Map<String,Object>> deliverrecordlist = jdbcTemplate.queryForList(ssql);
            resultInfo.addData("deliverrecordlist",deliverrecordlist);
            resultInfo.setCode(200);
            resultInfo.setSuccess(true);
            resultInfo.setMessage("查询成功！");
            return  resultInfo;
        }

        resultInfo.addData("deliverrecordlist",deliverrecordlist);
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        resultInfo.setMessage("查询成功！");
        return resultInfo;
    }

}
