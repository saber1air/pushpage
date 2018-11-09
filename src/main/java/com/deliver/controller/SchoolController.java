package com.deliver.controller;

import com.deliver.entity.ClassInfo;
import com.deliver.entity.GradeInfo;
import com.deliver.entity.MacInfo;
import com.deliver.entity.School;
import com.deliver.mapbody.DeliverParam;
import com.deliver.service.ClassInfoService;
import com.deliver.service.GradeInfoService;
import com.deliver.service.MacInfoService;
import com.deliver.service.SchoolService;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 学校管理
 * Created by pdl on 2018/9/25.
 */
@Controller
@RequestMapping(value="/schoolmgr")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private GradeInfoService gradeInfoService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MacInfoService macInfoService;


    /**
     * 新增学校
     */
    @RequestMapping(value="/addschool")
    @ResponseBody
    public ResultInfo addSchool(@RequestBody School school ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<School> schoolList = schoolService.findBySchoolNameAndDeleteFlag(school.getSchoolName(),0);
        if(schoolList!=null && schoolList.size()>0){
            resultInfo.setMessage("新增失败！该学校已注册！");
            resultInfo.setCode(400);
            resultInfo.setSuccess(false);
        }else{
            schoolService.save(school);
            resultInfo.setMessage("新增成功！");
            resultInfo.setCode(200);
            resultInfo.setSuccess(true);
        }

        return resultInfo;
    }

    /**
     * 学校修改
     */
    @RequestMapping(value="/editschool")
    @ResponseBody
    public ResultInfo editSchool(@RequestBody School school) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        School schoolinfo = schoolService.findBySchoolIDAndDeleteFlag(school.getSchoolID(),0);
        if(schoolinfo==null){
            resultInfo.setMessage("修改失败！学校不存在！");
            resultInfo.setCode(400);
            resultInfo.setSuccess(false);
            return resultInfo;
        }
        if(school.getSchoolName()!=null)
            schoolinfo.setSchoolName(school.getSchoolName());
        if(school.getAddress()!=null)
            schoolinfo.setAddress(school.getAddress());
        if(school.getPresident()!=null)
            schoolinfo.setPresident(school.getPresident());
        schoolService.update(schoolinfo);
        resultInfo.setMessage("修改成功！");
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 学校删除
     */
    @RequestMapping(value="/delschool")
    @ResponseBody
    public ResultInfo delSchool(@RequestBody Map<String,Object> jsonMap) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<Integer> schoolIDs = (List) jsonMap.get("schoolID");
        if(schoolIDs!=null && schoolIDs.size()>0){
            for(int i=0;i<schoolIDs.size();i++){
                School school = schoolService.findBySchoolIDAndDeleteFlag(schoolIDs.get(i),0);
                school.setDeleteFlag(1);
                schoolService.update(school);
                String sql = "update tc_grade_info t set t.delete_flag=1,t.update_time=NOW() where t.schoolid="+schoolIDs.get(i);
                try {
                    jdbcTemplate.update(sql);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
                sql = "update tc_class_info t set t.delete_flag=1,t.update_time=NOW() where t.schoolid="+schoolIDs.get(i);
                try {
                    jdbcTemplate.update(sql);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
                macInfoService.deleteBySchool(schoolIDs.get(i));
            }
        }
        resultInfo.setMessage("删除成功！");
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        return resultInfo;
    }



    /**
     * 新增年级
     */
    @RequestMapping(value="/addgrade")
    @ResponseBody
    public ResultInfo addGrade(@RequestBody GradeInfo gradeInfo ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<GradeInfo> gradeInfoList = gradeInfoService.findBySchoolIDAndGradeNumAndDeleteFlag(gradeInfo.getSchoolID(),gradeInfo.getGradeNum(),0);
        if(gradeInfoList!=null && gradeInfoList.size()>0){
            resultInfo.setMessage("新增失败！该年级已新增！");
            resultInfo.setCode(400);
            resultInfo.setSuccess(false);
        }else{
            gradeInfoService.save(gradeInfo);
            resultInfo.setMessage("新增成功！");
            resultInfo.setCode(200);
            resultInfo.setSuccess(true);
        }

        return resultInfo;

    }

    /**
     * 年级修改
     */
    @RequestMapping(value="/editgrade")
    @ResponseBody
    public ResultInfo editGrade(@RequestBody GradeInfo gradeInfo ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        GradeInfo grade = gradeInfoService.findByGradeID(gradeInfo.getGradeID());
        if(grade==null){
            resultInfo.setMessage("修改失败！学校不存在或已被删除！");
            resultInfo.setCode(400);
            resultInfo.setSuccess(false);
            return resultInfo;
        }
        if(gradeInfo.getGradeNum()!=null)
            grade.setGradeNum(gradeInfo.getGradeNum());
        if(gradeInfo.getGradeName()!=null)
            grade.setGradeName(gradeInfo.getGradeName());
        gradeInfoService.update(grade);
        resultInfo.setMessage("修改成功！");
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        return resultInfo;

    }

    /**
     * 年级删除
     */
    @RequestMapping(value="/delgrade")
    @ResponseBody
    public ResultInfo delGrade(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<Integer> gradeIDs = (List) jsonMap.get("gradeID");
        if(gradeIDs!=null && gradeIDs.size()>0){
            for(int i=0;i<gradeIDs.size();i++){
                GradeInfo gradeInfo = gradeInfoService.findByGradeID(gradeIDs.get(i));
                gradeInfo.setDeleteFlag(1);
                gradeInfoService.update(gradeInfo);
                String sql = "update tc_class_info t set t.delete_flag=1,t.update_time=NOW() where t.gradeid="+gradeIDs.get(i);
                try {
                    jdbcTemplate.update(sql);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //gradeInfoService.delete(gradeInfo.getGradeID());
        resultInfo.setMessage("删除成功！");
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        return resultInfo;

    }

    /**
     * 新增班级
     */
    @RequestMapping(value="/addclass")
    @ResponseBody
    public ResultInfo addClass(@RequestBody ClassInfo classInfo ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<ClassInfo> classInfoList = classInfoService.
                findBySchoolIDAndGradeIDAndClassNumAndDeleteFlag(classInfo.getSchoolID(),classInfo.getGradeID(),classInfo.getClassNum(),0);
        if(classInfoList!=null && classInfoList.size()>0){
            resultInfo.setMessage("新增失败！该班级已新增！");
            resultInfo.setCode(400);
            resultInfo.setSuccess(false);
        }else{
            classInfoService.save(classInfo);
            resultInfo.setMessage("新增成功！");
            resultInfo.setCode(200);
            resultInfo.setSuccess(true);
        }

        return resultInfo;
    }

    /**
     * 班级修改
     */
    @RequestMapping(value="/editclass")
    @ResponseBody
    public ResultInfo editClass(@RequestBody ClassInfo classInfo ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        ClassInfo classs = classInfoService.findByClassID(classInfo.getClassID());
        if(classs==null){
            resultInfo.setMessage("修改失败！班在不存在或已被删除！");
            resultInfo.setCode(400);
            resultInfo.setSuccess(false);
            return resultInfo;
        }
        if(classInfo.getClassName()!=null)
            classs.setClassName(classInfo.getClassName());
        if(classInfo.getClassNum()!=null)
            classs.setClassNum(classInfo.getClassNum());
        classInfoService.update(classs);
        resultInfo.setMessage("修改成功！");
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 班级删除
     */
    @RequestMapping(value="/delclass")
    @ResponseBody
    public ResultInfo delClass(@RequestBody Map<String,Object> jsonMap ) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        List<Integer> classIDs = (List) jsonMap.get("classID");
        if(classIDs!=null && classIDs.size()>0){
            for(int i=0;i<classIDs.size();i++){
                ClassInfo classInfo = classInfoService.findByClassID(classIDs.get(i));
                classInfo.setDeleteFlag(1);
                classInfoService.update(classInfo);
            }
        }
        //classInfoService.delete(classInfo.getClassID());
        resultInfo.setMessage("删除成功！");
        resultInfo.setCode(200);
        resultInfo.setSuccess(true);
        return resultInfo;
    }

}
