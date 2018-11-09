package com.deliver.controller;

import com.deliver.entity.*;
import com.deliver.service.*;
import com.deliver.util.Base64Img;
import com.deliver.util.ResultInfo;
import com.deliver.util.ToInterface;
import com.deliver.util.UnZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pdl on 2018/9/29.
 */

@RestController
@RequestMapping("/uploadDownload")
public class UploadDownloadController {
    //private static final Logger logger = LoggerFactory.getLogger(UploadDownloadController.class);
    //@Value("${uploadDir}")
    private String uploadDir;

    @Autowired
    private AdvertService advertService;

    @Autowired
    private HumanInfoService humanInfoService;

    @Autowired
    private HumanMediaService humanMediaService;

    @Autowired
    private PareStudentRelService parenStudentRelService;

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private GradeInfoService gradeInfoService;
    @Autowired
    private ClassInfoService classInfoService;

    @Autowired
    private VersionInfoService versionInfoService;

    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public ResultInfo uploadImage(@RequestParam(value = "file") MultipartFile file) throws RuntimeException {
        ResultInfo resultInfo = new ResultInfo(false);
        if (file.isEmpty()) {
            resultInfo.setMessage("文件不能为空");
            resultInfo.setCode(400);
            resultInfo.setSuccess(true);
            return resultInfo;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = uploadDir;
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            //logger.info("上传成功后的文件路径未：" + filePath + fileName);
            resultInfo.setMessage("文件不能为空");
            resultInfo.setCode(400);
            resultInfo.setSuccess(true);
            resultInfo.addData("filename", fileName);
            return resultInfo;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultInfo.setMessage("文件上传失败");
        resultInfo.setCode(400);
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    //文件下载相关代码
    @RequestMapping(value = "/downloadImage", method = RequestMethod.GET)
    public String downloadImage(String imageName, HttpServletRequest request, HttpServletResponse response) {
        //String fileName = "123.JPG";
        //logger.debug("the imageName is : "+imageName);
        String fileUrl = "D:\\images\\school\\1\\teacher\\" + imageName;
        if (fileUrl != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
           /* String realPath = request.getServletContext().getRealPath(
                    "//WEB-INF//");*/
            /*File file = new File(realPath, fileName);*/
            File file = new File(fileUrl);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + imageName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 文件上传到服务器
     **/
    @RequestMapping(value = "/uploadadvert")
    @ResponseBody
    public ResultInfo uploadAdvert(HttpServletRequest request,
                                   @RequestParam("file") MultipartFile file, @RequestParam(value = "schoolID", defaultValue = "0") Integer schoolID) throws Exception {

        //System.out.println(description);
        ResultInfo resultInfo = new ResultInfo(false);
        //如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            //上传文件路径
            //String path = request.getServletPath();

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            String path = mImagesPath + "advert/" + date;
            //上传文件名
            String filename = file.getOriginalFilename();
            if (filename.split("\\.")[1] != "jpeg" && filename.split("\\.")[1] != "jpg" && filename.split("\\.")[1] != "png"
                    && !filename.split("\\.")[1].equals("jpeg") && !filename.split("\\.")[1].equals("jpg") && !filename.split("\\.")[1].equals("png")) {

                resultInfo.setCode(400);
                resultInfo.setMessage("上传失败！只支持jpeg,jpg,png格式的文件！");
                resultInfo.setSuccess(false);
                return resultInfo;
            }
            File filepath = new File(path, filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
            String pathSrc = path + File.separator + filename;


            String advertPath = "images/advert/" + date + "/" + filename;
            Advert advert = new Advert();

            advert.setDeleteFlag(0);
            advert.setAdvertName(filename);
            advert.setAdvertPath(advertPath);
            advert.setSchoolID(schoolID);

            advertService.save(advert);

            resultInfo.setCode(200);
            resultInfo.setMessage("上传成功！");
            resultInfo.setSuccess(true);
            return resultInfo;
        } else {
            resultInfo.setCode(400);
            resultInfo.setMessage("上传失败！无上传文件！");
            resultInfo.setSuccess(false);
            return resultInfo;
        }

    }


    /**
     * 批量上传 2018-09-06 cwy
     */
    @RequestMapping(value = "/batchupload")
    public @ResponseBody
    ResultInfo batchUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam(value = "schoolID", defaultValue = "0") Integer schoolID) throws Exception {
        ResultInfo resultInfo = new ResultInfo(false);
        ToInterface toInterface = new ToInterface();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        if (!file.isEmpty()) {
            //上传文件路径
            //String path = request.getServletPath();

            String path = mImagesPath + "uploadzip" + File.separator + date;
            //上传文件名
            String filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
            String pathSrc = path + File.separator + filename;
            if (filename.split("\\.")[1] == "zip" || filename.split("\\.")[1].equals("zip")) {
                UnZipUtil unZipUtil = new UnZipUtil();
                if (!unZipUtil.unZipFolder(pathSrc, mImagesPath + "uploadimg" + File.separator + date)) {
                    File upload = new File(path + File.separator + filename);
                    if (upload.exists()) {
                        //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
                        SecurityManager securityManager = new SecurityManager();
                        securityManager.checkDelete(path + File.separator + filename);
                        //删除已存在的目标文件
                        upload.delete();
                    }
                    resultInfo.setCode(400);
                    resultInfo.setMessage("上传失败!请检查压缩文件格式，只支持zip压缩文件");
                    resultInfo.setSuccess(false);
                    return resultInfo;
                }
            }else{
                resultInfo.setCode(400);
                resultInfo.setMessage("上传失败!请检查压缩文件格式，只支持zip压缩文件");
                resultInfo.setSuccess(false);
                return resultInfo;
            }


        }
        String imgFolderPath = mImagesPath + "uploadimg" + File.separator + date;
        List<String> formaterrorlist = new ArrayList<String>();
        List<String> imgerrorlist = new ArrayList<String>();
        Pattern pattern = Pattern.compile("(?!_)(?!.*?_$)[s]{1}[\\-][a-zA-Z0-9\u4e00-\u9fa5]");

        Pattern pattern1 = Pattern.compile("(?!_)(?!.*?_$)[p]{1}[\\-][a-zA-Z0-9\u4e00-\u9fa5]+" +
                "([\\-][((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$]{11}$)");
        Pattern pattern2 = Pattern.compile("(?!_)(?!.*?_$)[t]{1}[\\-][a-zA-Z0-9\u4e00-\u9fa5]+" +
                "([\\-][((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$]{11}$)");
        File tarfile = new File(imgFolderPath);
        File[] tarfiles = tarfile.listFiles();
        String gradeNum = "";
        String classNum = "";
        
        List<String> fileerrorlist = new ArrayList<String>();

        if (tarfiles != null && tarfiles.length > 0) {
            for (int i = 0; i < tarfiles.length; i++) {
                if (tarfiles[i].isDirectory() == true) {
                    String fileName = tarfiles[i].getName();
                    File schoolfile = new File(imgFolderPath + "/" + fileName);
                    if (schoolfile.isDirectory()) {
                        File[] gradefiles = schoolfile.listFiles();
                        if (gradefiles != null && gradefiles.length > 0) {
                            for (int j = 0; j < gradefiles.length; j++) {
                                if (gradefiles[j].getName().matches("[0-3]{1}")) {
                                    //gradeID=Integer.parseInt(gradefiles[j].getName());
                                    if (gradefiles[j].isDirectory()) {
                                        File[] classfiles = gradefiles[j].listFiles();
                                        if (classfiles != null && classfiles.length > 0) {
                                            for (int k = 0; k < classfiles.length; k++) {
                                                if (classfiles[k].getName().matches("[0-9]{1,2}")) {
                                                    //classID = Integer.parseInt(classfiles[k].getName());
                                                    if (classfiles[k].isDirectory()) {
                                                        File[] studentfiles = classfiles[k].listFiles();
                                                        if (studentfiles != null && studentfiles.length > 0) {
                                                            for (int h = 0; h < studentfiles.length; h++) {
                                                                if (studentfiles[h].isDirectory()) {
                                                                    File[] imgfiles = studentfiles[h].listFiles();
                                                                    if (imgfiles != null && imgfiles.length > 0) {
                                                                        int p = 0;
                                                                        int s = 0;
                                                                        for (int g = 0; g < imgfiles.length; g++) {
                                                                            if (imgfiles[g].isFile()) {
                                                                                String imgName = imgfiles[g].getName();
                                                                                String[] imgNameArr = imgName.split("\\.");
                                                                                if (imgName.split("\\.")[1] != "jpg" && imgName.split("\\.")[1] != "png"
                                                                                        && !imgName.split("\\.")[1].equalsIgnoreCase("jpg") && !imgName.split("\\.")[1].equalsIgnoreCase("png")) {
                                                                                    formaterrorlist.add(imgName);
                                                                                    continue;
                                                                                }
                                                                                Matcher matcher = pattern.matcher(imgNameArr[0]);
                                                                                Matcher matcher1 = pattern1.matcher(imgNameArr[0]);
                                                                                if (matcher.find()) {
                                                                                    //s+=1;
                                                                                } else if (matcher1.find()) {
                                                                                    //p+=1;
                                                                                } else {
                                                                                    formaterrorlist.add(imgName);
                                                                                }
                                                                                if (imgName.split("-")[0] == "s" || imgName.split("-")[0].equals("s")) {
                                                                                    s++;
                                                                                } else if (imgName.split("-")[0] == "p" || imgName.split("-")[0].equals("p")) {
                                                                                    p++;
                                                                                }

                                                                            }
                                                                        }
                                                                        if (s == 0) {
                                                                        	fileerrorlist.add(studentfiles[h].getName());
                                                                            /*resultInfo.setCode(400);
                                                                            resultInfo.setMessage("校验失败！" + studentfiles[h].getName() + "文件夹下没有学生，或图片无法识别，请确认所有学生文件夹下有至少一张学生图片。");
                                                                            resultInfo.setSuccess(false);
                                                                            return resultInfo;*/
                                                                        } else if (p == 0) {
                                                                        	fileerrorlist.add(studentfiles[h].getName());
                                                                            /*resultInfo.setCode(400);
                                                                            resultInfo.setMessage("校验失败！" + studentfiles[h].getName() + "文件夹下没有家长，或图片无法识别，请确认所有学生文件夹下有至少一张家长图片。");
                                                                            resultInfo.setSuccess(false);
                                                                            return resultInfo;*/
                                                                        }
                                                                    } else {
                                                                    	fileerrorlist.add(studentfiles[h].getName());
                                                                        /*resultInfo.setCode(400);
                                                                        resultInfo.setMessage("校验失败！" + studentfiles[h].getName() + "为空文件夹，请放入至少一和学生照和一张家长照再上传，若该学生不存在，请删除。");
                                                                        resultInfo.setSuccess(false);
                                                                        return resultInfo;*/
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    resultInfo.setCode(400);
                                                    resultInfo.setMessage("校验失败！" + classfiles[k].getName() + "文件夹命名错误，班级文件夹只能命名为0-20之间的数字");
                                                    resultInfo.setSuccess(false);
                                                    return resultInfo;
                                                }

                                            }

                                        }
                                    }

                                } else {
                                    int t = 0;
                                    if (gradefiles[j].isDirectory()) {
                                        File[] teacherfiles = gradefiles[j].listFiles();

                                        if (teacherfiles != null && teacherfiles.length > 0) {
                                            for (int l = 0; l < teacherfiles.length; l++) {
                                                if (teacherfiles[l].isFile()) {
                                                    String imgName = teacherfiles[l].getName();
                                                    String[] imgNameArr = imgName.split("\\.");
                                                    if (imgName.split("\\.")[1] != "jpeg" && imgName.split("\\.")[1] != "jpg" && imgName.split("\\.")[1] != "png"
                                                            && !imgName.split("\\.")[1].equals("jpeg") && !imgName.split("\\.")[1].equals("jpg") && !imgName.split("\\.")[1].equals("png")) {
                                                        formaterrorlist.add(imgName);
                                                        continue;
                                                    }
                                                    Matcher matcher2 = pattern2.matcher(imgNameArr[0]);
                                                    if (!matcher2.find()) {
                                                        formaterrorlist.add(imgName);
                                                    }
                                                    t++;
                                                }
                                            }
                                        } else {
                                            resultInfo.setCode(400);
                                            resultInfo.setMessage("校验失败！" + gradefiles[j].getName() + "为空文件夹，请删除或修改。");
                                            resultInfo.setSuccess(false);
                                            return resultInfo;
                                        }
                                    }


                                    if (t == 0) {
                                        resultInfo.setCode(400);
                                        resultInfo.setMessage("校验失败！" + gradefiles[j].getName() + "文件夹命名错误，年级文件夹只能命名为0-3的数字" +
                                                "，如大班为3，中班为2，小班为1，小小班为0。若是老师文件，就在老师文件夹下放置图片，请严格按照命名格式。");
                                        resultInfo.setSuccess(false);
                                        return resultInfo;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        resultInfo.addData("imgfolderpath", imgFolderPath);
        resultInfo.addData("formaterrorlist", formaterrorlist);
        if (formaterrorlist != null && formaterrorlist.size() > 0) {
            resultInfo.setCode(400);
            resultInfo.setMessage("校验失败！部分图片命名错误，请按照命名规则准确命名,学生前缀为s,家长为p,老师为t。");
            resultInfo.setSuccess(false);
            return resultInfo;
        }

        /******以上是图片名称校验******/


        /**********以下是图片导入数据库********/

        Base64Img base64Img = new Base64Img();
        int gradeID=0;
        int classID=0;
        if (tarfiles != null && tarfiles.length > 0) {
            for (int i = 0; i < tarfiles.length; i++) {
                if (tarfiles[i].isDirectory() == true) {
                    String fileName = tarfiles[i].getName();

                    File schoolfile = new File(imgFolderPath + "/" + fileName);
                    if (schoolfile.isDirectory()) {
                        File[] gradefiles = schoolfile.listFiles();
                        if (gradefiles != null && gradefiles.length > 0) {
                            for (int j = 0; j < gradefiles.length; j++) {
                                if (gradefiles[j].getName().matches("[0-3]{1}")) {
                                    gradeNum = gradefiles[j].getName();
                                    List<GradeInfo> gradelist = gradeInfoService.findBySchoolIDAndGradeNumAndDeleteFlag(schoolID,Integer.parseInt(gradeNum),0);
                                    if(gradelist!=null && gradelist.size()>0){
                                        gradeID=gradelist.get(0).getGradeID();
                                    }else{
                                    	resultInfo.setCode(400);
                                        resultInfo.setMessage("校验失败！不存在该年级，请新增该年级后再录入。");
                                        resultInfo.setSuccess(false);
                                        return resultInfo;
                                    }
                                    if (gradefiles[j].isDirectory()) {
                                        File[] classfiles = gradefiles[j].listFiles();
                                        if (classfiles != null && classfiles.length > 0) {
                                            for (int k = 0; k < classfiles.length; k++) {
                                                if (classfiles[k].getName().matches("[0-9]{1,2}")) {
                                                    classNum = classfiles[k].getName();
                                                    List<ClassInfo> classlist = classInfoService.
                                                            findBySchoolIDAndGradeIDAndClassNumAndDeleteFlag(schoolID,gradeID,Integer.parseInt(classNum),0);
                                                    if(classlist!=null && classlist.size()>0){
                                                        classID=classlist.get(0).getClassID();
                                                    }else{
                                                    	resultInfo.setCode(400);
                                                        resultInfo.setMessage("校验失败！不存在该班级，请新增该班级后再录入。");
                                                        resultInfo.setSuccess(false);
                                                        return resultInfo;
                                                    }
                                                    if (classfiles[k].isDirectory()) {
                                                        File[] studentfiles = classfiles[k].listFiles();
                                                        if (studentfiles != null && studentfiles.length > 0) {
                                                            for (int h = 0; h < studentfiles.length; h++) {
                                                                if (studentfiles[h].isDirectory()) {
                                                                    File[] imgfiles = studentfiles[h].listFiles();
                                                                    if (imgfiles != null && imgfiles.length > 0) {
                                                                        List<Integer> humanrellist = new ArrayList<Integer>();
                                                                        //List<Integer> parentIDlist = new ArrayList<Integer>();
                                                                        int father=0;
                                                                        int mother=0;
                                                                        int others=0;
                                                                        for (int g = 0; g < imgfiles.length; g++) {
                                                                            if (imgfiles[g].isFile()) {
                                                                                String imgName = imgfiles[g].getName();
                                                                                String[] imgNameInfo = imgName.split("\\.")[0].split("-");
                                                                                if (imgNameInfo[0] == "s" || imgNameInfo[0].equals("s")) {
                                                                                    HumanInfo childInfo = new HumanInfo();
                                                                                    HumanInfo childlist = humanInfoService.findByHumanNameAndSchoolIDAndGradeIDAndClassID(imgNameInfo[1].trim(), schoolID, gradeID, classID);
                                                                                    if (childlist == null) {
                                                                                        childInfo.setAtschoolFlag(0);
                                                                                        childInfo.setHumanName(imgNameInfo[1]);
                                                                                        childInfo.setSchoolID(schoolID);
                                                                                        if(gradelist!=null && gradelist.size()>0){
                                                                                            childInfo.setGradeID(gradelist.get(0).getGradeID());
                                                                                        }
                                                                                        if(classlist!=null && classlist.size()>0){
                                                                                            childInfo.setClassID(classlist.get(0).getClassID());
                                                                                        }

                                                                                        childInfo.setDeleteFlag(0);
                                                                                        childInfo.setCheckFlag(1);
                                                                                        childInfo.setHumanType(0);
                                                                                        childInfo.setManagerType(0);
                                                                                        childInfo.setManagerFlag(1);
                                                                                        childInfo = (HumanInfo) humanInfoService.addHuman(childInfo).getData("human");

                                                                                    }else{
                                                                                        childInfo=childlist;
                                                                                        if(childInfo.getDeleteFlag()==1){
                                                                                        	childInfo.setDeleteFlag(0);
                                                                                        	humanInfoService.editHuman(childInfo);
                                                                                        }
                                                                                    }
                                                                                    int childID = -1;
                                                                                    if (childInfo != null) {
                                                                                        childID = childInfo.getHumanID();
                                                                                        if(childID!=-1){
                                                                                            humanrellist.add(childID);
                                                                                        }

                                                                                        String imgpath = imgFolderPath + "/" + fileName + "/" + gradeNum + "/" + classNum + "/" + studentfiles[h].getName() + "/" + imgName;
                                                                                        Map<String, Object> map = toInterface.pythonMap(imgpath);
                                                                                        if (!map.isEmpty()) {
                                                                                            File fileschool = new File(mImagesPath + "school/" + schoolID);
                                                                                            if (!fileschool.exists()) {
                                                                                                fileschool.mkdir();
                                                                                                File fileHuman = new File(mImagesPath + "school/" + schoolID + "/student");
                                                                                                if (!fileHuman.exists()) {
                                                                                                    fileHuman.mkdir();

                                                                                                }
                                                                                            }

                                                                                            String photoName = "school/" + schoolID + "/student/" + childID + date + ".png";
                                                                                            String childImgPath = mImagesPath + photoName;
                                                                                            Base64Img.base64StrToImage(map.get("crop_img").toString(), childImgPath);

                                                                                            List<HumanMedia> humanMediaList = humanMediaService.findByHumanIDAndDeleteFlagAndCheckFlag(childID, 0, 1);
                                                                                            if (humanMediaList != null && humanMediaList.size() > 0) {
                                                                                                for (HumanMedia humanMedia1 : humanMediaList) {
                                                                                                    humanMedia1.setDeleteFlag(1);
                                                                                                    humanMediaService.delMedia(humanMedia1);
                                                                                                }


                                                                                            }
                                                                                            HumanMedia humanMedia2 = new HumanMedia();
                                                                                            humanMedia2.setDeleteFlag(0);
                                                                                            humanMedia2.setCheckFlag(1);
                                                                                            humanMedia2.setSchoolID(schoolID);
                                                                                            humanMedia2.setHumanID(childID);
                                                                                            humanMedia2.setMediaPath("images/" + photoName);
                                                                                            humanMedia2.setFeature(map.get("features").toString());
                                                                                            humanMediaService.addMedia(humanMedia2);


                                                                                        } else {
                                                                                            imgerrorlist.add(imgName);
                                                                                            //childInfo.setDeleteFlag(1);
                                                                                            humanInfoService.delete(childInfo.getHumanID());
                                                                                        }


                                                                                    }
                                                                                } else if (imgNameInfo[0] == "p" || imgNameInfo[0].equals("p")) {
                                                                                    HumanInfo adultInfo = humanInfoService.findByHumanNameAndTel(imgNameInfo[1], imgNameInfo[2]);
                                                                                    if (adultInfo != null) {
                                                                                    	if(adultInfo.getDeleteFlag()==1){
                                                                                    		adultInfo.setDeleteFlag(0);
                                                                                    		humanInfoService.editHuman(adultInfo);
                                                                                    	}

                                                                                    } else {
                                                                                        adultInfo = new HumanInfo();
                                                                                        adultInfo.setHumanType(1);
                                                                                        adultInfo.setPassword("0000");
                                                                                        adultInfo.setHumanName(imgNameInfo[1]);
                                                                                        adultInfo.setTel(imgNameInfo[2]);
                                                                                        adultInfo.setAtschoolFlag(0);
                                                                                        adultInfo.setDeleteFlag(0);
                                                                                        adultInfo.setManagerType(6);
                                                                                        adultInfo.setManagerFlag(1);
                                                                                        adultInfo.setCheckFlag(1);
                                                                                        adultInfo = (HumanInfo) humanInfoService.addHuman(adultInfo).getData("human");
                                                                                    }
                                                                                    String imgpath = imgFolderPath + "/" + fileName + "/" + gradeNum + "/" +
                                                                                            classNum + "/" + studentfiles[h].getName() + "/" + imgName;
                                                                                    Map<String, Object> map = toInterface
                                                                                            .pythonMap(imgpath);
                                                                                    if (!map.isEmpty()) {
                                                                                        File fileschool = new File(mImagesPath + "school/" + schoolID);
                                                                                        if (!fileschool.exists()) {
                                                                                            fileschool.mkdir();
                                                                                            File fileHuman = new File(mImagesPath + "parent");
                                                                                            if (!fileHuman.exists()) {
                                                                                                fileHuman.mkdir();

                                                                                            }
                                                                                        }

                                                                                        int adultID = 0;
                                                                                        if (adultInfo != null) {
                                                                                            adultID = adultInfo.getHumanID();
                                                                                            if(adultInfo.getHumanName().indexOf("爸爸")!=-1){
                                                                                                father = adultInfo.getHumanID();
                                                                                            }else if(adultInfo.getHumanName().indexOf("妈妈")!=-1){
                                                                                                mother = adultInfo.getHumanID();
                                                                                            }else{
                                                                                                others = adultInfo.getHumanID();
                                                                                            }

                                                                                            if(adultID!=0){
                                                                                                humanrellist.add(adultID);
                                                                                            }

                                                                                            String photoName = "parent/" + adultID + date + ".png";
                                                                                            String adultImgPath = mImagesPath + photoName;
                                                                                            Base64Img.base64StrToImage(map.get("crop_img")
                                                                                                    .toString(), adultImgPath);

                                                                                            List<HumanMedia> humanMediaList = humanMediaService.findByHumanIDAndDeleteFlagAndCheckFlag(adultID, 0, 1);
                                                                                            if (humanMediaList != null && humanMediaList.size() > 0) {
                                                                                                for (HumanMedia humanMedia1 : humanMediaList) {
                                                                                                    humanMedia1.setDeleteFlag(1);
                                                                                                    humanMediaService.delMedia(humanMedia1);
                                                                                                }


                                                                                            }
                                                                                            HumanMedia humanMedia2 = new HumanMedia();
                                                                                            humanMedia2.setDeleteFlag(0);
                                                                                            humanMedia2.setCheckFlag(1);
                                                                                            humanMedia2.setHumanID(adultID);
                                                                                            humanMedia2.setMediaPath("images/" + photoName);
                                                                                            humanMedia2.setFeature(map.get("features").toString());
                                                                                            humanMediaService.addMedia(humanMedia2);
                                                                                        }
                                                                                    } else {
                                                                                        imgerrorlist.add(imgName);
                                                                                        humanInfoService.delete(adultInfo.getHumanID());
                                                                                    }
                                                                                }

                                                                            }
                                                                        }
                                                                        if (humanrellist != null && humanrellist.size() > 0 ) {
                                                                            int homeid = 0;
                                                                            if(father!=0){
                                                                                homeid=father;
                                                                            }else if(mother!=0){
                                                                                homeid=mother;
                                                                            }else{
                                                                                homeid=others;
                                                                            }
                                                                            father=0;
                                                                            mother=0;
                                                                            others=0;
                                                                            if(homeid!=0){
                                                                                HumanInfo homeHuman = humanInfoService.findByHumanID(homeid);
                                                                                homeHuman.setManagerFlag(1);
                                                                                homeHuman.setManagerType(5);
                                                                                humanInfoService.editHuman(homeHuman);
                                                                                for (int humanid : humanrellist) {
                                                                                    ParenStudentRel parenStudentRel = new ParenStudentRel();

                                                                                    List<ParenStudentRel> adultChildRelationlist = parenStudentRelService.findByHomeIDAndHumanID(homeid, humanid);
                                                                                    if (adultChildRelationlist == null || adultChildRelationlist.size() == 0) {
                                                                                        parenStudentRel.setHomeID(homeid);
                                                                                        parenStudentRel.setHumanID(humanid);
                                                                                        parenStudentRel.setCheckFlag(1);
                                                                                        parenStudentRel.setDeleteFlag(0);
                                                                                        parenStudentRel.setSchoolID(schoolID);
                                                                                        parenStudentRel.setDeleteFlag(0);
                                                                                        parenStudentRelService.addHumanRel(parenStudentRel);
                                                                                    }

                                                                                }
                                                                            }

                                                                            
                                                                        }


                                                                    } else {
                                                                        resultInfo.setCode(400);
                                                                        resultInfo.setMessage("校验失败！" + studentfiles[h].getName() + "为空文件夹，请放入至少一和学生照和一张家长照再上传，若该学生不存在，请删除。");
                                                                        resultInfo.setSuccess(false);
                                                                        return resultInfo;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    resultInfo.setCode(400);
                                                    resultInfo.setMessage("校验失败！" + classfiles[k].getName() + "文件夹命名错误，班级文件夹只能命名为0-20之间的数字");
                                                    resultInfo.setSuccess(false);
                                                    return resultInfo;
                                                }

                                            }

                                        }
                                    }

                                } else {

                                    if (gradefiles[j].isDirectory()) {
                                        File[] teacherfiles = gradefiles[j].listFiles();

                                        if (teacherfiles != null && teacherfiles.length > 0) {
                                            for (int l = 0; l < teacherfiles.length; l++) {
                                                if (teacherfiles[l].isFile()) {
                                                    String imgName = teacherfiles[l].getName();
                                                    String[] imgNameInfo = imgName.split("\\.")[0].split("-");
                                                    if (imgNameInfo[0] == "t" || imgNameInfo[0].equals("t")) {
                                                        HumanInfo adultInfo = humanInfoService.findByHumanNameAndTel(imgNameInfo[1], imgNameInfo[2]);
                                                        if (adultInfo != null) {
                                                        	if(adultInfo.getDeleteFlag()==1){
                                                        		adultInfo.setDeleteFlag(0);
                                                        		humanInfoService.editHuman(adultInfo);
                                                        	}
                                                        } else {
                                                            adultInfo = new HumanInfo();
                                                            adultInfo.setHumanType(2);
                                                            adultInfo.setPassword("0000");
                                                            adultInfo.setHumanName(imgNameInfo[1]);
                                                            adultInfo.setTel(imgNameInfo[2]);
                                                            adultInfo.setAtschoolFlag(0);
                                                            adultInfo.setDeleteFlag(0);
                                                            adultInfo.setManagerType(4);
                                                            adultInfo.setManagerFlag(1);
                                                            adultInfo.setCheckFlag(1);
                                                            adultInfo.setSchoolID(schoolID);
                                                            adultInfo = (HumanInfo) humanInfoService.addHuman(adultInfo).getData("human");
                                                        }
                                                        String imgpath = imgFolderPath + "/" + fileName + "/"  + gradefiles[j].getName() + "/" + imgName;
                                                        Map<String, Object> map = toInterface
                                                                .pythonMap(imgpath);
                                                        if (!map.isEmpty()) {
                                                            File fileschool = new File(mImagesPath + "school/" + schoolID);
                                                            if (!fileschool.exists()) {
                                                                fileschool.mkdir();
                                                                File fileHuman = new File(mImagesPath + "school/" + schoolID + "/teacher");
                                                                if (!fileHuman.exists()) {
                                                                    fileHuman.mkdir();

                                                                }
                                                            }

                                                            int adultID = 0;
                                                            if (adultInfo != null) {
                                                                adultID = adultInfo.getHumanID();
                                                                String photoName = "school/" + schoolID + "/teacher/" + adultID + date + ".png";
                                                                String adultImgPath = mImagesPath + photoName;
                                                                Base64Img.base64StrToImage(map.get("crop_img")
                                                                        .toString(), adultImgPath);

                                                                List<HumanMedia> humanMediaList = humanMediaService.findByHumanIDAndDeleteFlagAndCheckFlag(adultID, 0, 1);
                                                                if (humanMediaList != null && humanMediaList.size() > 0) {
                                                                    for (HumanMedia humanMedia1 : humanMediaList) {
                                                                        humanMedia1.setDeleteFlag(1);
                                                                        humanMediaService.delMedia(humanMedia1);
                                                                    }


                                                                }
                                                                HumanMedia humanMedia2 = new HumanMedia();
                                                                humanMedia2.setDeleteFlag(0);
                                                                humanMedia2.setCheckFlag(1);
                                                                humanMedia2.setHumanID(adultID);
                                                                humanMedia2.setMediaPath("images/" + photoName);
                                                                humanMedia2.setFeature(map.get("features").toString());
                                                                humanMediaService.addMedia(humanMedia2);
                                                            }
                                                        } else {
                                                            imgerrorlist.add(imgName);
                                                            humanInfoService.delete(adultInfo.getHumanID());
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            resultInfo.setCode(400);
                                            resultInfo.setMessage("校验失败！" + gradefiles[j].getName() + "为空文件夹，请删除或修改。");
                                            resultInfo.setSuccess(false);
                                            return resultInfo;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        if (imgerrorlist != null && imgerrorlist.size() > 0) {
            resultInfo.addData("imgerrorlist", imgerrorlist);
            resultInfo.setCode(400);
            resultInfo.setMessage("批量导入部分成功！有部分成人和学生图片无法识别！请更换图片，但保持与先前一样的图片名称！");
            resultInfo.setSuccess(false);
            return resultInfo;
        }


        resultInfo.addData("fileerrorlist", fileerrorlist);
        resultInfo.addData("imgerrorlist", imgerrorlist);
        resultInfo.setCode(200);
        resultInfo.setMessage("批量导入信息成功！");
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 文件上传到服务器
     **/
    @RequestMapping(value = "/uploadversion")
    @ResponseBody
    public ResultInfo uploadVersion(HttpServletRequest request,
                                   @RequestParam("file") MultipartFile file,
                                    @RequestParam(value = "appID", defaultValue = "") String appID,
                                    @RequestParam(value = "Os", defaultValue = "") String Os,
                                    @RequestParam(value = "versionNum", defaultValue = "") String versionNum) throws Exception {

        //System.out.println(description);
        ResultInfo resultInfo = new ResultInfo(false);
        //如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            //上传文件路径
            //String path = request.getServletPath();

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            String path = mImagesPath + "version/" + date;
            //上传文件名
            String filename = file.getOriginalFilename();
            File filepath = new File(path, filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
            String pathSrc = path + File.separator + filename;


            String versionPath = "images/version/" + date + "/" + filename;

            List<VersionInfo> versionInfoList = versionInfoService.findByUpdateFlagAndDeleteFlag(1,0);
            if(versionInfoList!=null && versionInfoList.size()>0){
                for(VersionInfo versionInfo1:versionInfoList){
                    versionInfo1.setUpdateFlag(0);
                    versionInfoService.save(versionInfo1);
                }
            }

            VersionInfo versionInfo = new VersionInfo();
            versionInfo.setDeleteFlag(0);
            versionInfo.setAppID(appID);
            versionInfo.setOs(Os);
            versionInfo.setVersionNum(versionNum);
            versionInfo.setVersionUrl(versionPath);
            versionInfo.setUpdateFlag(1);

            versionInfoService.save(versionInfo);

            resultInfo.setCode(200);
            resultInfo.setMessage("上传成功！");
            resultInfo.setSuccess(true);
            return resultInfo;
        } else {
            resultInfo.setCode(400);
            resultInfo.setMessage("上传失败！无上传文件！");
            resultInfo.setSuccess(false);
            return resultInfo;
        }

    }

}
