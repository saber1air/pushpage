package com.deliver.service;

import com.deliver.dao.AccessRecordDao;
import com.deliver.dao.VersionInfoDao;
import com.deliver.entity.AccessRecord;
import com.deliver.entity.VersionInfo;
import com.deliver.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pdl on 2018/9/13.
 */

@Service
public class VersionInfoService {
    @Autowired
    private VersionInfoDao versionInfoDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public VersionInfo findByAccessID(int id) {
        return versionInfoDao.findByVersionID(id);
    }

    public VersionInfo save(VersionInfo versionInfo) {
        versionInfo.setCreateTime(new Date());
        return versionInfoDao.save(versionInfo);
    }

    public int delete(int id) {
        return versionInfoDao.deleteByVersionID(id);
    }

    public List<VersionInfo> findByVersionNumAndAppID(String versionNum, String appID){
        return versionInfoDao.findByVersionNumAndAppID(versionNum,appID);
    }

    public List<VersionInfo> findByUpdateFlagAndDeleteFlag(int updateFlag, int deleteFlag){
        return versionInfoDao.findByUpdateFlagAndDeleteFlag(updateFlag,deleteFlag);
    }

    public ResultInfo queryUpdate(VersionInfo versionInfo){
        ResultInfo resultInfo = new ResultInfo(false);
        String sql = "select * from tc_version_info t where t.update_flag=1 ORDER BY t.create_time DESC LIMIT 1";
        List<Map<String,Object>> versionlist = jdbcTemplate.queryForList(sql);
        if(versionlist!=null && versionlist.size()>0){
            if((versionInfo.getOs()==versionlist.get(0).get("os") || versionInfo.getOs().equals(versionlist.get(0).get("os")))
                    && versionInfo.getVersionNum()==versionlist.get(0).get("version_num") || versionInfo.getOs().equals(versionlist.get(0).get("version_num"))){
                resultInfo.setCode(400);
                resultInfo.setMessage("已是最新版本！");
                resultInfo.setSuccess(false);
                return resultInfo;
            }else{
                resultInfo.setCode(200);
                resultInfo.setMessage("版本需要更新！");
                resultInfo.setSuccess(true);
                resultInfo.addData("versionpath",versionlist.get(0).get("version_url"));
                return resultInfo;
            }
        }else{
            resultInfo.setCode(400);
            resultInfo.setMessage("已是最新版本！");
            resultInfo.setSuccess(false);
            return resultInfo;
        }

    }

}
