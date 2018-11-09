/**
 * Created by pdl on 2018/9/19.
 */
var token = sessionStorage.getItem('token');
var loginHumanFace = sessionStorage.getItem('loginHumanFace');
var loginHuman =JSON.parse(sessionStorage.getItem('loginHuman'));
var humanType = parseInt(sessionStorage.getItem('humanType'));
var loginID = parseInt(sessionStorage.getItem('loginID'));
initPage(loginID, humanType, token);
var humanTypeList = ['学生', '家长', '老师', '园方管理员', '超级管理员', '其他人员'];
var manageTypeList = ['学生', '超级管理员', '园方管理员', '班主任', '老师', '家长', '家属', '其他人员'];
// var serverPath = 'http://10.0.3.134:8080/deliver/';
var serverPath = '';
var schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
var faceFeatrue = '';
var faceImg = '';
var selectMode = false;
// 测试
// var schoolData = [{
//     "start": 0,
//     "end": 0,
//     "pageCurrent": 0,
//     "pageSize": 0,
//     "pageCount": 0,
//     "orderBy": null,
//     "schoolID": 1,
//     "schoolName": "金霞小学",
//     "regionID": 2,
//     "president": "张三",
//     "createTime": 1537413112000,
//     "updateTime": 1537413207000,
//     "deleteFlag": 0,
//     "remarks": null,
//     "address": "长沙开福区",
//     "gradeInfo": [
//         {
//             "start": 0,
//             "end": 0,
//             "pageCurrent": 0,
//             "pageSize": 0,
//             "pageCount": 0,
//             "orderBy": null,
//             "gradeID": 1,
//             "gradeNum": 1,
//             "gradeName": "1",
//             "schoolID": 1,
//             "createTime": 1537409661000,
//             "updateTime": 1537409681000,
//             "deleteFlag": 0,
//             "remarks": null,
//             "classInfoList": [
//                 {
//                     "start": 0,
//                     "end": 0,
//                     "pageCurrent": 0,
//                     "pageSize": 0,
//                     "pageCount": 0,
//                     "orderBy": null,
//                     "classID": 1,
//                     "classNum": 1,
//                     "className": "1",
//                     "gradeID": 1,
//                     "schoolID": 1,
//                     "createTime": 1537409700000,
//                     "updateTime": 1537409712000,
//                     "deleteFlag": 0,
//                     "remarks": null
//                 },
//                 {
//                     "start": 0,
//                     "end": 0,
//                     "pageCurrent": 0,
//                     "pageSize": 0,
//                     "pageCount": 0,
//                     "orderBy": null,
//                     "classID": 2,
//                     "classNum": 2,
//                     "className": "2",
//                     "gradeID": 1,
//                     "schoolID": 1,
//                     "createTime": 1537415268000,
//                     "updateTime": 1537415277000,
//                     "deleteFlag": 0,
//                     "remarks": null
//                 },
//                 {
//                     "start": 0,
//                     "end": 0,
//                     "pageCurrent": 0,
//                     "pageSize": 0,
//                     "pageCount": 0,
//                     "orderBy": null,
//                     "classID": 3,
//                     "classNum": 3,
//                     "className": "3",
//                     "gradeID": 1,
//                     "schoolID": 1,
//                     "createTime": 1537415268000,
//                     "updateTime": 1537415277000,
//                     "deleteFlag": 0,
//                     "remarks": null
//                 }
//             ]
//         },
//         {
//             "start": 0,
//             "end": 0,
//             "pageCurrent": 0,
//             "pageSize": 0,
//             "pageCount": 0,
//             "orderBy": null,
//             "gradeID": 2,
//             "gradeNum": 2,
//             "gradeName": "2",
//             "schoolID": 1,
//             "createTime": 1537415151000,
//             "updateTime": 1537415163000,
//             "deleteFlag": 0,
//             "remarks": null,
//             "classInfoList": [
//                 {
//                     "start": 0,
//                     "end": 0,
//                     "pageCurrent": 0,
//                     "pageSize": 0,
//                     "pageCount": 0,
//                     "orderBy": null,
//                     "classID": 3,
//                     "classNum": 1,
//                     "className": "1",
//                     "gradeID": 2,
//                     "schoolID": 1,
//                     "createTime": 1537415289000,
//                     "updateTime": 1537415301000,
//                     "deleteFlag": 0,
//                     "remarks": null
//                 },
//                 {
//                     "start": 0,
//                     "end": 0,
//                     "pageCurrent": 0,
//                     "pageSize": 0,
//                     "pageCount": 0,
//                     "orderBy": null,
//                     "classID": 4,
//                     "classNum": 2,
//                     "className": "2",
//                     "gradeID": 2,
//                     "schoolID": 1,
//                     "createTime": 1537415312000,
//                     "updateTime": 1537415320000,
//                     "deleteFlag": 0,
//                     "remarks": null
//                 }
//             ]
//         },
//         {
//             "start": 0,
//             "end": 0,
//             "pageCurrent": 0,
//             "pageSize": 0,
//             "pageCount": 0,
//             "orderBy": null,
//             "gradeID": 3,
//             "gradeNum": 3,
//             "gradeName": "3",
//             "schoolID": 1,
//             "createTime": 1537415171000,
//             "updateTime": 1537415199000,
//             "deleteFlag": 0,
//             "remarks": null,
//             "classInfoList": [
//                 {
//                     "start": 0,
//                     "end": 0,
//                     "pageCurrent": 0,
//                     "pageSize": 0,
//                     "pageCount": 0,
//                     "orderBy": null,
//                     "classID": 5,
//                     "classNum": 1,
//                     "className": "1",
//                     "gradeID": 3,
//                     "schoolID": 1,
//                     "createTime": 1537415336000,
//                     "updateTime": 1537415343000,
//                     "deleteFlag": 0,
//                     "remarks": null
//                 },
//                 {
//                     "start": 0,
//                     "end": 0,
//                     "pageCurrent": 0,
//                     "pageSize": 0,
//                     "pageCount": 0,
//                     "orderBy": null,
//                     "classID": 6,
//                     "classNum": 2,
//                     "className": "2",
//                     "gradeID": 3,
//                     "schoolID": 1,
//                     "createTime": 1537415351000,
//                     "updateTime": 1537415359000,
//                     "deleteFlag": 0,
//                     "remarks": null
//                 }
//             ]
//         }
//     ],
//     "macInfoList":[{
//         "start": 0,
//         "end": 0,
//         "pageCurrent": 0,
//         "pageSize": 0,
//         "pageCount": 0,
//         "orderBy": null,
//         "macID": 1,
//         "macNum": 1,
//         "macName": "1",
//         "schoolID": 1,
//         "createTime": 1537409661000,
//         "updateTime": 1537409681000,
//         }]
// }];


//表格内容分页相关
var currentPageNum = 1;
var pageItemsNum = 8;
var pageContent = [];
var currentManageType = '';
var pageTotalNum = 0;

var selectedItem = new Set();

//文件上传相关
var uploadPic = new FormData();
var uploadAD = new FormData();
var uploadApp = new FormData();

if(loginHuman){
    $("#loginPic").attr('src',serverPath+loginHumanFace);
    $("#loginHumanInfo").html("登录人员："+loginHuman.humanName+"（"+manageTypeList[parseInt(loginHuman.managerType)]+"）");
}
//左侧导航栏人员管理按钮绑定事件
$("#humanManagementMenuBtn").click(function () {
    currentManageType = 'humanManage';
    var contName = $(this).attr("id");
    initContentView(contName);
    var pageSchool = $("#entityManagementHuman_school");
    var pageGrade = $("#entityManagementHuman_grade");
    $("#entityManagementHuman_class").html("<option value=-1>请选择班级</option>");
    pageSchool.html("<option value=-1>请选择学校</option>");
    pageGrade.html("<option value=-1>请选择年级</option>");
    for (var i = 0; i < schoolData.length; i++) {
        var schoolItem = schoolData[i];
        var schoolResult = "<option value=" + schoolItem.schoolID + ">" + schoolItem.schoolName + "</option>";
        pageSchool.append(schoolResult);
    }
    if (humanType == 1) {

        $("#entityManagementHuman_humantype").html("<option value = '4'>超级管理员</option>" +
            "<option value = '3'>园方管理员</option>" +
            "<option value = '2'>老师</option>" +
            "<option value = '1'>家长</option>" +
            "<option value = '0'>学生</option>" +
            "<option value = '5'>其他人员</option>");
    } else {
        //    不显示学校信息
        var loginGrades = schoolData[0].gradeInfo;
        for (var i = 0; i < loginGrades.length; i++) {
            var gradeItem = loginGrades[i];
            var gradeResult = "<option value=" + gradeItem.gradeID + ">" + gradeItem.gradeName + "</option>";
            pageGrade.append(gradeResult);
        }
        if (humanType == 2) {
            $("#entityManagementHuman_humantype").html(
                "<option value = '2'>老师</option>" +
                "<option value = '1'>家长</option>" +
                "<option value = '0'>学生</option>" +
                "<option value = '5'>其他人员</option>");
        } else {
            $("#entityManagementHuman_humantype").html(
                "<option value = '1'>家长</option>" +
                "<option value = '0'>学生</option>" +
                "<option value = '5'>其他人员</option>");
        }


    }
});
$("#schoolManagementMenuBtn").click(function () {
    console.log("school!!!!");
    currentManageType = 'schoolManage';
    var contName = $(this).attr("id");
    initContentView(contName);
    pageContent = schoolData;
    pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
    $("#entityManagementSchool_searchResult").css('display', '');
    currentPageNum =1;
    changePage(currentManageType, pageContent, 1, pageItemsNum);
    initPagination(pageTotalNum);
});
$("#gradeManagementMenuBtn").click(function () {
    $("#entityManagementGrade_searchResult").css('display', 'none');
    currentManageType = 'gradeManage';
    var contName = $(this).attr("id");
    initContentView(contName);
    var pageSchool = $("#gradeManagement_schoolID");
    pageSchool.html("<option value=-1>请选择学校</option>");
    for (var i = 0; i < schoolData.length; i++) {
        var schoolItem = schoolData[i];
        var schoolResult = "<option value=" + schoolItem.schoolID + ">" + schoolItem.schoolName + "</option>";
        pageSchool.append(schoolResult);
    }
});
$("#classManagementMenuBtn").click(function () {
    $("#entityManagementClass_searchResult").css('display', 'none');

    currentManageType = 'classManage';
    var contName = $(this).attr("id");
    initContentView(contName);
    var pageSchool = $("#classManagement_schoolID");
    pageSchool.html("<option value=-1>请选择学校</option>");
    $("#classManagement_gradeID").html("<option value=0>请选择年级</option>");
    for (var i = 0; i < schoolData.length; i++) {
        var schoolItem = schoolData[i];
        var schoolResult = "<option value=" + schoolItem.schoolID + ">" + schoolItem.schoolName + "</option>";
        pageSchool.append(schoolResult);
    }
    ;

});
$("#facilityManagementMenuBtn").click(function () {
    $("#entityManagementFacility_searchResult").css('display', 'none');
    currentManageType = 'facilityManage';
    var contName = $(this).attr("id");
    initContentView(contName);
    var pageSchool = $("#facilityManagement_schoolID");
    pageSchool.html("<option value=-1>请选择学校</option>");
    for (var i = 0; i < schoolData.length; i++) {
        var schoolItem = schoolData[i];
        var schoolResult = "<option value=" + schoolItem.schoolID + ">" + schoolItem.schoolName + "</option>";
        pageSchool.append(schoolResult);
    }
});
$("#inoutManagement_atSchoolSearchMenuBtn").click(function () {
    currentManageType = 'atSchoolSearch';
    var contName = $(this).attr("id");
    initContentView(contName);
    var pageSchool = $("#atSchoolSearch_school");
    var pageGrade = $("#atSchoolSearch_grade");
    var pageClass = $("#atSchoolSearch_class");
    pageClass.html("<option value=-1>请选择班级</option>");
    pageSchool.html("<option value=-1>请选择学校</option>");
    pageGrade.html("<option value=-1>请选择年级</option>");
    for (var i = 0; i < schoolData.length; i++) {
        var schoolItem = schoolData[i];
        var schoolResult = "<option value=" + schoolItem.schoolID + ">" + schoolItem.schoolName + "</option>";
        pageSchool.append(schoolResult);
    }
    if(loginHuman.schoolID){
        for (var i = 0; i < schoolData.length; i++) {
            var schoolItem = schoolData[i];
            if((''+schoolItem.schoolID) == (''+loginHuman.schoolID)){
                var gradeItem = schoolData[i].gradeInfo;
                for(var j = 0;j<gradeItem.length;j++){
                    var gradeResult = "<option value=" + gradeItem[j].gradeID + ">" + gradeItem[j].gradeName + "</option>";
                    pageGrade.append(gradeResult);
                }
            }
        }
    }
});
$("#inoutManagement_record_historyMenuBtn").click(function () {
    currentManageType = 'record_history';
    var contName = $(this).attr("id");
    initContentView(contName);
});
$("#applyManagementMenuBtn").click(function () {
    currentManageType = 'applyManage';
    var contName = $(this).attr("id");
    initContentView(contName);
});
$("#upLoadManagementMenuBtn").click(function(){
    currentManageType = 'upLoadManage';
    var contName = $(this).attr("id");
    initContentView(contName);
    var pichSchool = $("#pichSchool");
    var adPichSchool = $("#adPichSchool");
    if(loginHuman.managerType =='1'){
        for(var i =0;i<schoolData.length;i++){
            var temp = " <option value='"+schoolData[i].schoolID+"' >"+schoolData[i].schoolName+"</option>";
            pichSchool.append(temp);
        }
        for(var i =0;i<schoolData.length;i++){
            var temp = " <option value='"+schoolData[i].schoolID+"' >"+schoolData[i].schoolName+"</option>";
            adPichSchool.append(temp);
        }
    }else{
        pichSchool.html('');
        adPichSchool.html('');
    }
})

//下拉框绑定变更事件
$("#addEntityHuman_school").bind("change", function () {
    var schoolIDValue = $(this).val();
    if (parseInt(schoolIDValue) == -1) {
        return;
    }
    var pageGrade = $("#addEntityHuman_grade");
    var grades = [];
    for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            grades = schoolData[i].gradeInfo;
            break;
        }
    }
    pageGrade.html("<option value=-1>请选择年级</option>");
    for (var i = 0; i < grades.length; i++) {
        var gradeItem = grades[i];
        var gradeResult = "<option value=" + gradeItem.gradeID + ">" + gradeItem.gradeName + "</option>";
        pageGrade.append(gradeResult);
    }
});
$("#addEntityHuman_grade").bind("change", function () {
    var gradeIDValue = $(this).val();
    if (parseInt(gradeIDValue) == -1) {
        return;
    }
    var classInfo = [];
    var schoolIDValue = $("#addEntityHuman_school").val();
    outer:   for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            for (var j = 0; j < schoolData[i].gradeInfo.length; j++) {
                if (gradeIDValue == schoolData[i].gradeInfo[j].gradeID) {
                    classInfo = schoolData[i].gradeInfo[j].classInfoList;
                    break outer;
                }
            }
        }
    }
    var pageClass = $("#addEntityHuman_class");
    pageClass.html('');
    for (var i = 0; i < classInfo.length; i++) {
        var classItem = classInfo[i];
        var classResult = "<option value=" + classItem.classID + ">" + classItem.className + "</option>";
        pageClass.append(classResult);
    }

});
$("#editEntityHuman_school").bind("change", function () {
    var schoolIDValue = $(this).val();
    if (parseInt(schoolIDValue) == -1) {
        return;
    }
    var pageGrade = $("#editEntityHuman_grade");
    var grades = [];
    for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            grades = schoolData[i].gradeInfo;
            break;
        }
    }
    pageGrade.html("<option value=-1>请选择年级</option>");
    for (var i = 0; i < grades.length; i++) {
        var gradeItem = grades[i];
        var gradeResult = "<option value=" + gradeItem.gradeID + ">" + gradeItem.gradeName + "</option>";
        pageGrade.append(gradeResult);
    }
});
$("#editEntityHuman_grade").bind("change", function () {
    var gradeIDValue = $(this).val();
    if (parseInt(gradeIDValue) == -1) {
        return;
    }
    var classInfo = [];
    var schoolIDValue = $("#editEntityHuman_school").val();
    outer:   for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            for (var j = 0; j < schoolData[i].gradeInfo.length; j++) {
                if (gradeIDValue == schoolData[i].gradeInfo[j].gradeID) {
                    classInfo = schoolData[i].gradeInfo[j].classInfoList;
                    break outer;
                }
            }
        }
    }
    var pageClass = $("#editEntityHuman_class");
    pageClass.html('');
    for (var i = 0; i < classInfo.length; i++) {
        var classItem = classInfo[i];
        var classResult = "<option value=" + classItem.classID + ">" + classItem.className + "</option>";
        pageClass.append(classResult);
    }

});
$("#gradeManagement_schoolID").bind("change", function () {
    var schoolIDValue = $(this).val();
    for (var i = 0; i < schoolData.length; i++) {
        if (parseInt(schoolIDValue) == schoolData[i].schoolID) {
            pageContent = schoolData[i].gradeInfo;
            currentPageNum =1;
            changePage(currentManageType, pageContent, 1, pageItemsNum);
            pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
            initPagination(pageTotalNum);
            $("#entityManagementGrade_searchResult").css('display', '');
            break;
        }
    }
});
$("#classManagement_schoolID").bind("change", function () {
    var schoolIDValue = $(this).val();
    if (parseInt(schoolIDValue) == -1) {
        return;
    }
    var pageGrade = $("#classManagement_gradeID");
    var grades = [];
    for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            grades = schoolData[i].gradeInfo;
            break;
        }
    }
    pageGrade.html("<option value=-1>请选择年级</option>");
    for (var i = 0; i < grades.length; i++) {
        var gradeItem = grades[i];
        var gradeResult = "<option value=" + gradeItem.gradeID + ">" + gradeItem.gradeName + "</option>";
        pageGrade.append(gradeResult);
    }
});
$("#classManagement_gradeID").bind("change", function () {
    var gradeIDValue = $(this).val();
    if (parseInt(gradeIDValue) == -1) {
        return;
    }
    var schoolIDValue = $("#classManagement_schoolID").val();
    outer:   for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            for (var j = 0; j < schoolData[i].gradeInfo.length; j++) {
                if (gradeIDValue == schoolData[i].gradeInfo[j].gradeID) {
                    pageContent = schoolData[i].gradeInfo[j].classInfoList;
                    break outer;
                }
            }
        }
    }
    currentPageNum =1;
    changePage(currentManageType, pageContent, 1, pageItemsNum);
    pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
    initPagination(pageTotalNum);
    $("#entityManagementClass_searchResult").css('display', '');

});
$("#facilityManagement_schoolID").bind("change", function () {
    $("#entityManagementFacility_searchResult").css('display', '');

    var schoolIDValue = $(this).val();
    for (var i = 0; i < schoolData.length; i++) {
        if (schoolIDValue == schoolData[i].schoolID) {
            pageContent = schoolData[i].macInfo;
            currentPageNum =1;
            changePage(currentManageType, pageContent, 1, pageItemsNum);
            pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
            initPagination(pageTotalNum);
            break;
        }
    }
});
$("#entityManagementHuman_school").bind("change", function () {
    var schoolIDValue = $(this).val();
    var pageGrade = $("#entityManagementHuman_grade");
    $("#entityManagementHuman_class").html("<option value=-1>请选择班级</option>");
    var grades = '';
    for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            grades = schoolData[i].gradeInfo;
            break;
        }
    }
    pageGrade.html("<option value=-1>请选择年级</option>");
    for (var i = 0; i < grades.length; i++) {
        var gradeItem = grades[i];
        var gradeResult = "<option value=" + gradeItem.gradeID + ">" + gradeItem.gradeName + "</option>";
        pageGrade.append(gradeResult);
    }
});
$("#entityManagementHuman_grade").bind("change", function () {
    var gradeIDValue = $(this).val();
    var schoolIDValue = $("#entityManagementHuman_school").val();
    var pageClass = $("#entityManagementHuman_class");
    $("#entityManagementHuman_class").html("<option value=-1>请选择班级</option>");
    var classes = '';
    outer:   for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            for (var j = 0; j < schoolData[i].gradeInfo.length; j++) {
                if (gradeIDValue == schoolData[i].gradeInfo[j].gradeID) {
                    classes = schoolData[i].gradeInfo[j].classInfoList;
                    break outer;
                }
            }
        }
    }
    for (var i = 0; i < classes.length; i++) {
        var classItem = classes[i];
        var classResult = "<option value=" + classItem.classID + ">" + classItem.className + "</option>";
        pageClass.append(classResult);
    }
    ;
});
$("#atSchoolSearch_school").bind("change", function () {
    var schoolIDValue = $(this).val();
    var pageGrade = $("#atSchoolSearch_grade");
    $("#atSchoolSearch_class").html("<option value=-1>请选择班级</option>");
    var grades = '';
    for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            grades = schoolData[i].gradeInfo;
            break;
        }
    }
    pageGrade.html("<option value=-1>请选择年级</option>");
    for (var i = 0; i < grades.length; i++) {
        var gradeItem = grades[i];
        var gradeResult = "<option value=" + gradeItem.gradeID + ">" + gradeItem.gradeName + "</option>";
        pageGrade.append(gradeResult);
    }
});
$("#atSchoolSearch_grade").bind("change", function () {
    var gradeIDValue = $(this).val();
    if(loginHuman.schoolID){
        var schoolIDValue = loginHuman.schoolID;
    }else{
        var schoolIDValue = $("#atSchoolSearch_school").val();
    }
    var pageClass = $("#atSchoolSearch_class");
    pageClass.html("<option value=-1>请选择班级</option>");
    var classes = '';
    outer:   for (var i = 0; i < schoolData.length; i++) {
        if (schoolData[i].schoolID == schoolIDValue) {
            for (var j = 0; j < schoolData[i].gradeInfo.length; j++) {
                if (gradeIDValue == schoolData[i].gradeInfo[j].gradeID) {
                    classes = schoolData[i].gradeInfo[j].classInfoList;
                    break outer;
                }
            }
        }
    }
    for (var i = 0; i < classes.length; i++) {
        var classItem = classes[i];
        var classResult = "<option value=" + classItem.classID + ">" + classItem.className + "</option>";
        pageClass.append(classResult);
    }
    ;
});
$("#addEntityHuman_manageType").bind('change',function () {
    var currentManagerType = $(this).val();
    switch (parseInt(currentManagerType)){
        case 0:
            $("#addEntityHuman_schoolDiv").css('display','');
            $("#addEntityHuman_gradeDiv").css('display','');
            $("#addEntityHuman_classDiv").css('display','');
            $("#addEntityHuman_parentNameDiv").css('display','');
            $("#addEntityHuman_parentTelDiv").css('display','');
            $("#addEntityHuman_telDiv").css('display','none');
            $("#addEntityHuman_passwordDiv").css('display','none');
            break;
        case 1:
            $("#addEntityHuman_schoolDiv").css('display','none');
            $("#addEntityHuman_gradeDiv").css('display','none');
            $("#addEntityHuman_classDiv").css('display','none');
            $("#addEntityHuman_parentNameDiv").css('display','none');
            $("#addEntityHuman_parentTelDiv").css('display','none');
            $("#addEntityHuman_telDiv").css('display','');
            $("#addEntityHuman_passwordDiv").css('display','');
            break;
        case 2:
            $("#addEntityHuman_schoolDiv").css('display','');
            $("#addEntityHuman_gradeDiv").css('display','none');
            $("#addEntityHuman_classDiv").css('display','none');
            $("#addEntityHuman_parentNameDiv").css('display','none');
            $("#addEntityHuman_parentTelDiv").css('display','none');
            $("#addEntityHuman_telDiv").css('display','');
            $("#addEntityHuman_passwordDiv").css('display','');
            break;
        case 3:
            $("#addEntityHuman_schoolDiv").css('display','');
            $("#addEntityHuman_gradeDiv").css('display','');
            $("#addEntityHuman_classDiv").css('display','');
            $("#addEntityHuman_parentNameDiv").css('display','none');
            $("#addEntityHuman_parentTelDiv").css('display','none');
            $("#addEntityHuman_telDiv").css('display','');
            $("#addEntityHuman_passwordDiv").css('display','');
            break;
        case 4:
            $("#addEntityHuman_schoolDiv").css('display','');
            $("#addEntityHuman_gradeDiv").css('display','none');
            $("#addEntityHuman_classDiv").css('display','none');
            $("#addEntityHuman_parentNameDiv").css('display','none');
            $("#addEntityHuman_parentTelDiv").css('display','none');
            $("#addEntityHuman_telDiv").css('display','');
            $("#addEntityHuman_passwordDiv").css('display','');
            break;
        case 5:
            $("#addEntityHuman_schoolDiv").css('display','none');
            $("#addEntityHuman_gradeDiv").css('display','none');
            $("#addEntityHuman_classDiv").css('display','none');
            $("#addEntityHuman_parentNameDiv").css('display','none');
            $("#addEntityHuman_parentTelDiv").css('display','none');
            $("#addEntityHuman_telDiv").css('display','');
            $("#addEntityHuman_passwordDiv").css('display','');
            break;
        case 6:
            $("#addEntityHuman_schoolDiv").css('display','none');
            $("#addEntityHuman_gradeDiv").css('display','none');
            $("#addEntityHuman_classDiv").css('display','none');
            $("#addEntityHuman_parentNameDiv").css('display','');
            $("#addEntityHuman_parentTelDiv").css('display','');
            $("#addEntityHuman_telDiv").css('display','');
            $("#addEntityHuman_passwordDiv").css('display','');
            break;
        default:
            break;
    }
})

//查询按钮事件
$("#entityManagementHuman_searchBtn").click(function () {
    console.log(currentManageType);
    zeroModal.loading(6);
    //初始化新增框
    $("#addEntityHuman_manageType").val(-1);
    $("#addEntityHuman_schoolDiv").css('display','none');
    $("#addEntityHuman_gradeDiv").css('display','none');
    $("#addEntityHuman_classDiv").css('display','none');
    $("#addEntityHuman_parentNameDiv").css('display','none');
    $("#addEntityHuman_parentTelDiv").css('display','none');
    $("#addEntityHuman_telDiv").css('display','none');
    $("#addEntityHuman_passwordDiv").css('display','none');
    $("#addEntityHuman_checkDiv").css('display','none');
    // $("#addEntityHuman_manageType").val(-1);
    var _school='';
    if(loginHuman.schoolID){
        _school = parseInt(loginHuman.schoolID);
    }else{
        _school = $("#entityManagementHuman_school").val();
    }
    $.ajax(
        {
            url: serverPath + 'manager/findhuman',
            async: true,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                humanName: $("#entityManagementHuman_humanname").val(),
                tel: $("#entityManagementHuman_phonenum").val(),
                humanType: $("#entityManagementHuman_humantype").val(),
                schoolID: _school,
                gradeID: $("#entityManagementHuman_grade").val(),
                classID: $("#entityManagementHuman_class").val(),
                checkFlag: 1,
                atSchoolFlag: -1
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                if (data.success && data.data.humanlist) {
                    pageContent = data.data.humanlist;
                    currentPageNum =1;
                    changePage(currentManageType, pageContent, 1, pageItemsNum);
                    //初始化分页
                    pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                    initPagination(pageTotalNum);
                    $("#entityManagementHuman_searchResult").css('display', '');

                } else {
                    showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                }

            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});
$("#inoutManagement_record_history_searchBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + "access/deliverrecordquery",
            async: false,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                humanName: $('#inoutManagement_record_history_humanname').val(),
                accessType: $('#inoutManagement_record_history_accesstype').val(),
                // gradeID: $('#atSchoolSearch_grade').val(),
                // classID: $('#atSchoolSearch_class').val(),
                // schoolID: $('#atSchoolSearch_school').val(),
                humanType: $('#inoutManagement_record_history_humantype').val(),
                deliverType: $('#inoutManagement_record_history_delivertype').val(),
                beginTime: $('#inoutManagement_record_history_starttime').val(),
                endTime: $('#inoutManagement_record_history_endtime').val(),
                checkResult: $('#inoutManagement_record_history_check_result').val()
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                if (data.success) {
                    zeroModal.closeAll();
                    showMessage(data.message, 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                    if (data.success) {
                        var fatherResult = $("#inoutManagement_searchResult");
                        fatherResult.html("<legend>查询结果</legend>\n" +
                            "\n" +
                            "\n" +
                            "<table id=\"human_manage_record_history_pickTable\" class=\"dashboard-table\">\n" +
                            "    <colgroup>\n" +
                            "        <col width=\"160\">\n" +
                            "        <col width=\"150\">\n" +
                            "        <col width=\"130\">\n" +
                            "        <col width=\"130\">\n" +
                            "        <col width=\"150\">\n" +
                            "        <col width=\"250\">\n" +
                            "    </colgroup>\n" +
                            "    <thead>\n" +
                            "    <tr>\n" +
                            "        <th><a href=\"javascript:void(0);\">记录时间 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "        <th><a href=\"javascript:void(0);\">记录照 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "        <th><a href=\"javascript:void(0);\">儿童姓名 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "        <th><a href=\"javascript:void(0);\">家长姓名 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "        <th><a href=\"javascript:void(0);\">进出类别 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "        <th><a href=\"javascript:void(0);\">记录信息 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "    </tr>\n" +
                            "    </thead>\n" +
                            "    <tbody>\n" +
                            "    </tbody>\n" +
                            "</table>\n" +
                            "\n" +
                            "<table id=\"human_manage_record_history_inoutTable\" class=\"dashboard-table\">\n" +
                            "    <colgroup>\n" +
                            "        <col width=\"160\">\n" +
                            "        <col width=\"150\">\n" +
                            "        <col width=\"130\">\n" +
                            "        <col width=\"150\">\n" +
                            "        <col width=\"250\">\n" +
                            "    </colgroup>\n" +
                            "    <thead>\n" +
                            "    <tr>\n" +
                            "        <th><a href=\"javascript:void(0);\">记录时间 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "        <th><a href=\"javascript:void(0);\">记录照 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "        <th><a href=\"javascript:void(0);\">人员姓名 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "        <th><a href=\"javascript:void(0);\">进出类别 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "        <th><a href=\"javascript:void(0);\">记录信息 <i class=\"fa fa-caret-down\"></i></a></th>\n" +
                            "    </tr>\n" +
                            "    </thead>\n" +
                            "    <tbody>\n" +
                            "    </tbody>\n" +
                            "</table>");

                        var inoutTablebody = $("#human_manage_record_history_inoutTable").find("tbody");
                        inoutTablebody.html('');
                        var pickTablebody = $("#human_manage_record_history_pickTable").find("tbody");
                        pickTablebody.html('');
                        if (1 == $('#inoutManagement_record_history_delivertype').val()) {
                            $("#human_manage_record_history_inoutTable").css("display", "");
                            $("#human_manage_record_history_pickTable").css("display", "none");
                        } else {
                            $("#human_manage_record_history_inoutTable").css("display", "none");
                            $("#human_manage_record_history_pickTable").css("display", "");
                        }
                        if (data.data.humanlist.length) {
                            $("#inoutManagement_searchResult").css('display', '');
                            for (var i = 0; i < data.data.humanlist.length; i++) {
                                var listitem = data.data.humanlist[i];
                                var entersign = (1 == listitem.access_type) ? ("进") : ("出");
                                //     var entermesg = (1 == listitem.CHECKRESULT)?("正常"):("异常");
                                var entermesg = listitem.message;
                                if (1 == listitem.deliver_type) {
                                    //    单独进出
                                    var tbody = $("#human_manage_record_history_inoutTable").find("tbody");
                                    var item = "<tr>\n" +
                                        "                            <td>" + timestampToTime(listitem.deliver_time) + "</td>\n" +
                                        "                            <td> <div class=\"flex-child-shrink\">\n" +
                                        "                                <img style='height: 100px;width: 120px' class=\"dashboard-table-image\" src='" + serverPath + listitem.media + "'>\n" +
                                        "                            </div></td>\n" +
                                        "                            <td>" + listitem.parent_name + "</td>\n" +
                                        "                            <td>" + entersign + "\n" +
                                        "                            </td>\n" +
                                        "                            <td >" + entermesg + "\n" +
                                        "                            </td>\n" +
                                        "                        </tr>";
                                    inoutTablebody.append(item);
                                } else {
                                    //    家长接送

                                    var item = "<tr>\n" +
                                        "                            <td>" + timestampToTime(listitem.deliver_time) + "</td>\n" +
                                        "                            <td> <div class=\"flex-child-shrink\">\n" +
                                        "                                <img style='height: 100px;width: 120px' class=\"dashboard-table-image\" src='" + serverPath + listitem.media + "'>\n" +
                                        "                            </div></td>\n" +
                                        "                            <td>" + listitem.student_name + "</td>\n" +
                                        "                            <td>" + listitem.parent_name + "</td>\n" +
                                        "                            <td>" + entersign + "\n" +
                                        "                            </td>\n" +
                                        "                            <td >" + entermesg + "\n" +
                                        "                            </td>\n" +
                                        "                        </tr>";
                                    pickTablebody.append(item);

                                }

                            }
                        }

                    }
                }
            },
            error: function (xhr) {
                zeroModal.closeAll();
                console.log("错误提示： " + xhr.status + " " + xhr.statusText);
            }
        }
    )
});
$("#atSchoolSearch_searchBtn").click(function () {
    var _school = '';
    if(loginHuman.schoolID){
        _school = parseInt(loginHuman.schoolID);
    }else{
        _school = $("#atSchoolSearch_school").val();
    }
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + "manager/findhuman",
            async: false,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                humanName: $('#atSchoolSearch_humanname').val(),
                humanType: $('#atSchoolSearch_humantype').val(),
                gradeID: $('#atSchoolSearch_grade').val(),
                classID: $('#atSchoolSearch_class').val(),
                schoolID: _school,
                tel: $('#atSchoolSearch_tel').val(),
                atschoolFlag: $('#atSchoolSearch_atschool').val()
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                if (data.success) {
                    $('#inoutManagement_searchResult').css("display", "");
                    $("#inoutManagement_searchResult").html("<legend>查询结果</legend>");

                    if (data.data.humanlist.length) {
                        for (var j = 0; j < data.data.humanlist.length; j++) {
                            humanitem = data.data.humanlist[j];
                            var picpath = serverPath + humanitem.media_path;

                            var atschoolInfo = (humanitem.atschool_flag == 1) ? ("在校") : ("离校");
                            var atschoolInfoStyle = (humanitem.atschool_flag == 1) ? (" ") : ("style='background-color:#d7d4d4'");

                            if (0 == humanitem.human_type) {
                                var resultItem = "<div class='card-profile-stats medium-4 cell'>\n" +
                                    "                    <div class='card-profile-stats-intro'" + atschoolInfoStyle + ">\n" +
                                    "                        <img class='thumbnail' src='" + picpath + "' alt='profile-image' style='width: 70px;height:80px;margin-right:7%'/>\n" +
                                    "                        <div class='card-profile-stats-intro-content'>\n" +
                                    "                            <h5>" + humanitem.human_name + "</h5>\n" +
                                    "                            <p>" + humanitem.grade_name + " " + humanitem.class_name + "</p>\n" +
                                    "                        <p>是否在校：" + atschoolInfo + "</p>\n" +
                                    "                        </div>\n" +
                                    "                    </div>\n" +
                                    "                </div>";
                            } else {
                                var resultItem = "<div class='card-profile-stats medium-4 cell'>\n" +
                                    "                    <div class='card-profile-stats-intro'" + atschoolInfoStyle + ">\n" +
                                    "                        <img class='thumbnail' src='" + picpath + "' alt='profile-image' style='width: 70px;height:80px;margin-right:7%'/>\n" +
                                    "                        <div class='card-profile-stats-intro-content'>\n" +
                                    "                            <h5>" + humanitem.human_name + "</h5>\n" +
                                    "                            <p>电话：" + humanitem.tel + "</p>\n" +
                                    "                        <p>是否在校：" + atschoolInfo + "</p>\n" +
                                    "                        </div>\n" +
                                    "                    </div>\n" +
                                    "                </div>";
                            }

                            $("#inoutManagement_searchResult").append(resultItem);
                        }
                    }
                }
            },
            error: function (xhr) {
                zeroModal.closeAll();
                console.log("错误提示： " + xhr.status + " " + xhr.statusText);
            }
        }
    )
});
$("#applyManagement_searchBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + "manager/findtocheckhuman",
            async: true,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                humanID: loginID,
                applyType: parseInt($("#applyManagement_applyType").val())
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                if (data.success) {
                    pageContent = data.data.humanlist;
                    currentPageNum =1;
                    changePage(currentManageType, pageContent, 1, pageItemsNum);
                    //初始化分页
                    pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                    currentPageNum = 1;
                    //TODO 审核记录分页
                    $("#applyManagement_manipulateBtns").css('display', '');
                    $("#applyPagination").css('display', '');
                    $("#applyPaginationPrevious").addClass('disabled');
                    if (pageContent.length > pageItemsNum) {
                        $("#applyPaginationNext").removeClass('disabled');
                    } else {
                        $("#applyPaginationNext").addClass('disabled');
                    }
                    $('#applyPaginationSelectNum').html("");
                    for (var i = 0; i < pageTotalNum; i++) {
                        $('#applyPaginationSelectNum').append("<option value='" + (i + 1) + "'>" + (i + 1) + "</option>");
                    }

                    //初始化结果
                    $("#applyManagement_searchResult").css('display', '');

                } else {
                    showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                }
                ;
            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});

//分页按钮事件
$("#paginationNext").click(function () {
    if (currentPageNum == pageTotalNum) {
        return;
    }
    currentPageNum++;
    $("#paginationSelectNum").val(currentPageNum);
    changePage(currentManageType, pageContent, currentPageNum, pageItemsNum);
    selectMode = false;

});
$("#applyPaginationNext").click(function () {
    if (currentPageNum == pageTotalNum) {
        return;
    }
    currentPageNum++;
    $("#applyPaginationChangePageBtn").val(currentPageNum);
    changePage(currentManageType, pageContent, currentPageNum, pageItemsNum);
    selectMode = false;

});
$("#paginationPrevious").click(function () {
    if (currentPageNum == 1) {
        return;
    }
    currentPageNum--;
    $("#paginationSelectNum").val(currentPageNum);
    changePage(currentManageType, pageContent, currentPageNum, pageItemsNum);
    selectMode = false;
});
$("#applyPaginationPrevious").click(function () {
    if (currentPageNum == 1) {
        return;
    }
    currentPageNum--;
    $("#applyPaginationChangePageBtn").val(currentPageNum);
    changePage(currentManageType, pageContent, currentPageNum, pageItemsNum);
    selectMode = false;
});
$("#paginationChangePageBtn").click(function () {
    var paginationSelectedNum = $("#paginationSelectNum").val();
    if (paginationSelectedNum != currentPageNum) {
        currentPageNum = paginationSelectedNum;
        changePage(currentManageType, pageContent, currentPageNum, pageItemsNum);
        selectMode = false;
    } else {

    }
});
$("#applyPaginationChangePageBtn").click(function () {
    var paginationSelectedNum = $("#applyPaginationSelectNum").val();
    if (paginationSelectedNum != currentPageNum) {
        currentPageNum = paginationSelectedNum;
        changePage(currentManageType, pageContent, currentPageNum, pageItemsNum);
        selectMode = false;
    } else {
    }
});
$("#selectAll").click(function () {

    if (!selectMode) {
        $(":checkbox").each(function (i) {
            $(this).attr('checked', 'checked');
        });
        selectMode = true;
    } else {
        $(":checkbox").each(function (i) {
            $(this).removeAttr('checked');
        });
        selectMode = false;
    }

});
$("#applySelectAll").click(function () {

    if (!selectMode) {
        $(":checkbox").each(function (i) {
            $(this).attr('checked', 'checked');
        });
        selectMode = true;
    } else {
        $(":checkbox").each(function (i) {
            $(this).removeAttr('checked');
        });
        selectMode = false;
    }

});

//实体添加事件
$("#addEntityItem").click(function () {

    switch (currentManageType) {
        case "humanManage":
            var addHumanSchool = $("#addEntityHuman_school");
            addHumanSchool.html("<option value=-1>请选择学校</option>");
            for (var i = 0; i < schoolData.length; i++) {
                var schoolItem = schoolData[i];
                var schoolResult = "<option value=" + schoolItem.schoolID + ">" + schoolItem.schoolName + "</option>";
                addHumanSchool.append(schoolResult);
            }
            // $("#addEntityHuman_manageType").val(-1);
            $("#addEntityHuman").foundation('open');

            break;
        case 'schoolManage':
            $("#addEntitySchool").foundation('open');
            break;
        case 'gradeManage':
            $("#addEntityGrade").foundation('open');
            break;
        case 'classManage':
            $("#addEntityClass").foundation('open');
            break;
        case 'facilityManage':
            $("#addEntityFacility").foundation('open');
            break;
        default:
            console.log("error");
            break;
    }
    ;

});
$("#addEntityHuman_addBtn").click(function () {
    switch (parseInt($("#addEntityHuman_manageType").val())){
        case 0 :
            if($("#addEntityHuman_school").val() && $("#addEntityHuman_grade").val() && $("#addEntityHuman_class").val()
            && $("#addEntityHuman_humanName").val() && faceImg && $("#addEntityHuman_parentName").val()
            && $("#addEntityHuman_parentTel").val()){
                out = JSON.stringify({
                    humanID: loginID,
                    humanName: $("#addEntityHuman_humanName").val(),
                    humanType: 0,
                    managerType: 0,
                    schoolID: parseInt($("#addEntityHuman_school").val()),
                    gradeID: parseInt($("#addEntityHuman_grade").val()),
                    classID: parseInt($("#addEntityHuman_class").val()),
                    img: faceImg,
                    feature: faceFeatrue,
                    parentTel: $("#addEntityHuman_parentTel").val(),
                    parentName: $("#addEntityHuman_parentName").val()
                });
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'manager/addhumanbyteacher',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: out,
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                $("#entityManagementHuman_searchBtn").click();
                                $("#addEntityHuman").foundation('close');
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            }else{
                showMessage('信息录入不全！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
            break;
        case 1:
            if(faceImg && $("#addEntityHuman_humanName").val() && $("#addEntityHuman_tel").val() && $("#addEntityHuman_password").val()){
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'manager/addhumanbyother',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            humanID: loginID,
                            tel: $("#addEntityHuman_tel").val(),
                            password: $("#addEntityHuman_password").val(),
                            humanName: $("#addEntityHuman_humanName").val(),
                            managerType: 1,
                            humanType: 4,
                            img: faceImg,
                            feature: faceFeatrue
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                $("#entityManagementHuman_searchBtn").click();
                                $("#addEntityHuman").foundation('close');
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            }else{
                showMessage('信息录入不全！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
            break;
        case 2:
            if(faceImg && $("#addEntityHuman_humanName").val() && $("#addEntityHuman_tel").val() && $("#addEntityHuman_password").val()
            && $("#addEntityHuman_school").val()){
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'manager/addhumanbyother',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            humanID: loginID,
                            tel: $("#addEntityHuman_tel").val(),
                            schoolID: parseInt($("#addEntityHuman_school").val()),
                            password: $("#addEntityHuman_password").val(),
                            humanName: $("#addEntityHuman_humanName").val(),
                            managerType: 2,
                            humanType: 3,
                            img: faceImg,
                            feature: faceFeatrue
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                $("#entityManagementHuman_searchBtn").click();
                                $("#addEntityHuman").foundation('close');
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            }else{
                showMessage('信息录入不全！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
            break;
        case 3:
            if(faceImg && $("#addEntityHuman_humanName").val() && $("#addEntityHuman_tel").val() && $("#addEntityHuman_password").val()
                && $("#addEntityHuman_school").val()  && $("#addEntityHuman_grade").val() && $("#addEntityHuman_class").val()){
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'manager/addhumanbyother',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            humanID: loginID,
                            tel: $("#addEntityHuman_tel").val(),
                            schoolID: parseInt($("#addEntityHuman_school").val()),
                            gradeID: parseInt($("#addEntityHuman_grade").val()),
                            classID: parseInt($("#addEntityHuman_class").val()),
                            password: $("#addEntityHuman_password").val(),
                            humanName: $("#addEntityHuman_humanName").val(),
                            managerType: 3,
                            humanType: 2,
                            img: faceImg,
                            feature: faceFeatrue
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                $("#entityManagementHuman_searchBtn").click();
                                $("#addEntityHuman").foundation('close');
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            }else{
                showMessage('信息录入不全！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
            break;
        case 4:
            if(faceImg && $("#addEntityHuman_humanName").val() && $("#addEntityHuman_tel").val() && $("#addEntityHuman_password").val()
                && $("#addEntityHuman_school").val()){
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'manager/addhumanbyother',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            humanID: loginID,
                            tel: $("#addEntityHuman_tel").val(),
                            schoolID: parseInt($("#addEntityHuman_school").val()),
                            password: $("#addEntityHuman_password").val(),
                            humanName: $("#addEntityHuman_humanName").val(),
                            managerType: 4,
                            humanType: 2,
                            img: faceImg,
                            feature: faceFeatrue
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                $("#entityManagementHuman_searchBtn").click();
                                $("#addEntityHuman").foundation('close');
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            }else{
                showMessage('信息录入不全！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
            break;
        case 5:
            if(faceImg && $("#addEntityHuman_humanName").val() && $("#addEntityHuman_tel").val() && $("#addEntityHuman_password").val()){
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'manager/addhumanbyother',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            humanID: loginID,
                            tel: $("#addEntityHuman_tel").val(),
                            password: $("#addEntityHuman_password").val(),
                            humanName: $("#addEntityHuman_humanName").val(),
                            managerType: 5,
                            humanType: 1,
                            img: faceImg,
                            feature: faceFeatrue
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                $("#entityManagementHuman_searchBtn").click();
                                $("#addEntityHuman").foundation('close');
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            }else{
                showMessage('信息录入不全！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
            break;
        case 6:
            if($("#addEntityHuman_humanName").val() && faceImg && $("#addEntityHuman_parentName").val()
                && $("#addEntityHuman_parentTel").val() && $("#addEntityHuman_tel").val() && $("#addEntityHuman_password").val() ){
                out = JSON.stringify({
                    humanID: loginID,
                    humanName: $("#addEntityHuman_humanName").val(),
                    humanType: 1,
                    managerType: 6,
                    password: $("#addEntityHuman_password").val(),
                    tel: $("#addEntityHuman_tel").val(),
                    img: faceImg,
                    feature: faceFeatrue,
                    parentTel: $("#addEntityHuman_parentTel").val(),
                    parentName: $("#addEntityHuman_parentName").val()
                });
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'manager/addhumanbyteacher',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: out,
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                $("#entityManagementHuman_searchBtn").click();
                                $("#addEntityHuman").foundation('close');
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            }else{
                showMessage('信息录入不全！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
            break;
        default:
            console.log('add error!');
            break;
    }
});
$("#addEntitySchool_addBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + 'schoolmgr/addschool',
            async: true,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                schoolName: $("#addEntitySchool_schoolName").val(),
                president: $("#addEntitySchool_president").val(),
                address: $("#addEntitySchool_address").val()
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                if (data.success) {
                    // window.location.reload();
                    initPage(loginID, humanType, token);
                    schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                    $("#schoolManagementMenuBtn").click();
                    $("#addEntitySchool").foundation('close');
                }

            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});
$("#addEntityGrade_addBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + 'schoolmgr/addgrade',
            async: true,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                gradeName: $("#addEntityGrade_gradeName").val(),
                gradeNum: parseInt($("#addEntityGrade_gradeNum").val()),
                schoolID: parseInt($("#gradeManagement_schoolID").val())
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');

                if (data.success) {
                    initPage(loginID, humanType, token);
                    schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                    var schoolIDValue = parseInt($("#gradeManagement_schoolID").val());
                    for (var i = 0; i < schoolData.length; i++) {
                        if (schoolIDValue == schoolData[i].schoolID) {
                            pageContent = schoolData[i].gradeInfo;
                            currentPageNum =1;
                            changePage(currentManageType, pageContent, 1, pageItemsNum);
                            pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                            initPagination(pageTotalNum);
                            $("#entityManagementGrade_searchResult").css('display', '');
                            break;
                        }
                    }
                    $("#addEntityGrade").foundation('close');
                }

            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});
$("#addEntityClass_addBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + 'schoolmgr/addclass',
            async: true,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                className: $("#addEntityClass_className").val(),
                classNum: $("#addEntityClass_classNum").val(),
                schoolID: $("#classManagement_schoolID").val(),
                gradeID: $("#classManagement_gradeID").val()
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                if (data.success) {
                    initPage(loginID, humanType, token);
                    schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                    var schoolIDValue = parseInt($("#classManagement_schoolID").val());
                    var gradeIDValue = parseInt($("#classManagement_gradeID").val());
                    outer:   for (var i = 0; i < schoolData.length; i++) {
                        if (schoolIDValue == schoolData[i].schoolID) {
                            var gradeInfo = schoolData[i].gradeInfo;
                            for (var j = 0; j < gradeInfo.length; j++) {
                                if (gradeIDValue == gradeInfo[j].gradeID) {
                                    pageContent = gradeInfo[j].classInfoList;
                                    currentPageNum =1;
                                    changePage(currentManageType, pageContent, 1, pageItemsNum);
                                    pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                                    initPagination(pageTotalNum);
                                    $("#entityManagementClass_searchResult").css('display', '');
                                    break outer;
                                }
                            }
                        }
                    }
                    $("#addEntityClass").foundation('close');
                }
            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});
$("#addEntityFacility_addBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + 'addMacDevice',
            async: true,
            type: 'post',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                macName: $("#addEntityFacility_macName").val(),
                schoolID: parseInt($("#facilityManagement_schoolID").val())
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                if (data.success) {
                    initPage(loginID, humanType, token);
                    schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                    var schoolIDValue = parseInt($("#facilityManagement_schoolID").val());
                    for (var i = 0; i < schoolData.length; i++) {
                        if (schoolIDValue == schoolData[i].schoolID) {
                            pageContent = schoolData[i].macInfo;
                            currentPageNum =1;
                            changePage(currentManageType, pageContent, 1, pageItemsNum);
                            pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                            initPagination(pageTotalNum);
                            $("#entityManagementFacility_searchResult").css('display', '');
                            break;
                        }
                    }
                    $("#addEntityFacility").foundation('close');
                }
            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('新增失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});

//实体管理修改信息保存按钮事件
$("#editEntityHuman_saveBtn").click(function () {
    var addhumanType = -1;
    addhumanType = switchMan2Hum(parseInt($("#editEntityHuman_manageType").val()));
    zeroModal.loading(6);
    var data = '';
    if ($("#editFaceChangeFlag").val() == "true") {

        switch (parseInt($("#editEntityHuman_manageType").val())){
            case 0:
                data = JSON.stringify({
                    humanID: $("#editEntityHuman_humanID").val(),
                    phoneFlag: 0,
                    humanName: $("#editEntityHuman_humanName").val(),
                    managerType: parseInt($("#editEntityHuman_manageType").val()),
                    schoolID: parseInt($("#editEntityHuman_school").val()),
                    gradeID: parseInt($("#editEntityHuman_grade").val()),
                    classID: parseInt($("#editEntityHuman_class").val()),
                    humanType: addhumanType,
                    img: faceImg,
                    feature: faceFeatrue
                });
                break;
            case 1:
            case 5:
            case 6:
                data = JSON.stringify({
                    humanID: $("#editEntityHuman_humanID").val(),
                    phoneFlag: 0,
                    humanName: $("#editEntityHuman_humanName").val(),
                    managerType: parseInt($("#editEntityHuman_manageType").val()),
                    tel: $("#editEntityHuman_tel").val(),
                    password: $("#editEntityHuman_password").val(),
                    humanType: addhumanType,
                    img: faceImg,
                    feature: faceFeatrue
                });
                break;
            case 4:
            case 2:
                data = JSON.stringify({
                    humanID: $("#editEntityHuman_humanID").val(),
                    phoneFlag: 0,
                    humanName: $("#editEntityHuman_humanName").val(),
                    managerType: parseInt($("#editEntityHuman_manageType").val()),
                    tel: $("#editEntityHuman_tel").val(),
                    schoolID: parseInt($("#editEntityHuman_school").val()),
                    password: $("#editEntityHuman_password").val(),
                    humanType: addhumanType,
                    img: faceImg,
                    feature: faceFeatrue
                });
                break;
            case 3:
                data = JSON.stringify({
                    humanID: $("#editEntityHuman_humanID").val(),
                    phoneFlag: 0,
                    humanName: $("#editEntityHuman_humanName").val(),
                    managerType: parseInt($("#editEntityHuman_manageType").val()),
                    tel: $("#editEntityHuman_tel").val(),
                    schoolID: parseInt($("#editEntityHuman_school").val()),
                    gradeID: parseInt($("#editEntityHuman_grade").val()),
                    classID: parseInt($("#editEntityHuman_class").val()),
                    password: $("#editEntityHuman_password").val(),
                    humanType: addhumanType,
                    img: faceImg,
                    feature: faceFeatrue
                });
                break;
            default:
                console.log('edit Error');
                break;
        }
    } else {
        switch (parseInt($("#editEntityHuman_manageType").val())){
            case 0:
                data = JSON.stringify({
                    humanID: $("#editEntityHuman_humanID").val(),
                    phoneFlag: 0,
                    humanName: $("#editEntityHuman_humanName").val(),
                    managerType: parseInt($("#editEntityHuman_manageType").val()),
                    schoolID: parseInt($("#editEntityHuman_school").val()),
                    gradeID: parseInt($("#editEntityHuman_grade").val()),
                    classID: parseInt($("#editEntityHuman_class").val()),
                    humanType: addhumanType
                });
                break;
            case 1:
            case 5:
            case 6:
                data = JSON.stringify({
                    humanID: $("#editEntityHuman_humanID").val(),
                    phoneFlag: 0,
                    humanName: $("#editEntityHuman_humanName").val(),
                    managerType: parseInt($("#editEntityHuman_manageType").val()),
                    tel: $("#editEntityHuman_tel").val(),
                    password: $("#editEntityHuman_password").val(),
                    humanType: addhumanType
                });
                break;
            case 4:
            case 2:
                data = JSON.stringify({
                    humanID: $("#editEntityHuman_humanID").val(),
                    phoneFlag: 0,
                    humanName: $("#editEntityHuman_humanName").val(),
                    managerType: parseInt($("#editEntityHuman_manageType").val()),
                    tel: $("#editEntityHuman_tel").val(),
                    schoolID: parseInt($("#editEntityHuman_school").val()),
                    password: $("#editEntityHuman_password").val(),
                    humanType: addhumanType
                });
                break;
            case 3:
                data = JSON.stringify({
                    humanID: $("#editEntityHuman_humanID").val(),
                    phoneFlag: 0,
                    humanName: $("#editEntityHuman_humanName").val(),
                    managerType: parseInt($("#editEntityHuman_manageType").val()),
                    tel: $("#editEntityHuman_tel").val(),
                    schoolID: parseInt($("#editEntityHuman_school").val()),
                    gradeID: parseInt($("#editEntityHuman_grade").val()),
                    classID: parseInt($("#editEntityHuman_class").val()),
                    password: $("#editEntityHuman_password").val(),
                    humanType: addhumanType
                });
                break;
            default:
                console.log('edit Error');
                break;
        }
    }
    $.ajax(
        {
            url: serverPath + 'manager/modhuman',
            async: true,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: data,
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                if (data.success) {
                    $("#entityManagementHuman_searchBtn").click();
                    $("#editEntityHuman").foundation('close');
                }
            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )

});
$("#editEntitySchool_addBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + 'schoolmgr/editschool',
            async: true,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                schoolID: $("#editEntitySchool_schoolID").val(),
                schoolName: $("#editEntitySchool_schoolName").val(),
                president: $("#editEntitySchool_president").val(),
                address: $("#editEntitySchool_address").val()
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                if (data.success) {
                    // window.location.reload();
                    initPage(loginID, humanType, token);
                    schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                    $("#schoolManagementMenuBtn").click();
                    $("#editEntitySchool").foundation('close');
                }

            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});
$("#editEntityGrade_saveBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + 'schoolmgr/editgrade',
            async: true,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                gradeID: $("#editEntitySchool_gradeID").val(),
                gradeName: $("#editEntityGrade_gradeName").val(),
                gradeNum: parseInt($("#editEntityGrade_gradeNum").val()),
                schoolID: parseInt($("#gradeManagement_schoolID").val())
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');

                if (data.success) {
                    initPage(loginID, humanType, token);
                    schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                    var schoolIDValue = parseInt($("#gradeManagement_schoolID").val());
                    for (var i = 0; i < schoolData.length; i++) {
                        if (schoolIDValue == schoolData[i].schoolID) {
                            pageContent = schoolData[i].gradeInfo;
                            currentPageNum =1;
                            changePage(currentManageType, pageContent, 1, pageItemsNum);
                            pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                            initPagination(pageTotalNum);
                            $("#entityManagementGrade_searchResult").css('display', '');
                            break;
                        }
                    }
                    $("#editEntityGrade").foundation('close');
                }

            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});
$("#editEntityClass_saveBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + 'schoolmgr/editclass',
            async: true,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                classID: $("#editEntitySchool_classID").val(),
                className: $("#editEntityClass_className").val(),
                classNum: $("#editEntityClass_classNum").val(),
                schoolID: $("#classManagement_schoolID").val(),
                gradeID: $("#classManagement_gradeID").val()
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                if (data.success) {
                    initPage(loginID, humanType, token);
                    schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                    var schoolIDValue = parseInt($("#classManagement_schoolID").val());
                    var gradeIDValue = parseInt($("#classManagement_gradeID").val());
                    outer:   for (var i = 0; i < schoolData.length; i++) {
                        if (schoolIDValue == schoolData[i].schoolID) {
                            var gradeInfo = schoolData[i].gradeInfo;
                            for (var j = 0; j < gradeInfo.length; j++) {
                                if (gradeIDValue == gradeInfo[j].gradeID) {
                                    pageContent = gradeInfo[j].classInfoList;
                                    currentPageNum =1;
                                    changePage(currentManageType, pageContent, 1, pageItemsNum);
                                    pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                                    initPagination(pageTotalNum);
                                    $("#entityManagementClass_searchResult").css('display', '');
                                    break outer;
                                }
                            }
                        }
                    }
                    $("#editEntityClass").foundation('close');
                }
            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});
$("#editEntityFacility_addBtn").click(function () {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath + 'editMacDevice',
            async: true,
            type: 'post',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                macID: $("#editEntitySchool_facilityID").val(),
                macName: $("#editEntityFacility_macName").val(),
                schoolID: parseInt($("#facilityManagement_schoolID").val())
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                if (data.success) {
                    initPage(loginID, humanType, token);
                    schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                    var schoolIDValue = parseInt($("#facilityManagement_schoolID").val());
                    for (var i = 0; i < schoolData.length; i++) {
                        if (schoolIDValue == schoolData[i].schoolID) {
                            pageContent = schoolData[i].macInfo;
                            currentPageNum =1;
                            changePage(currentManageType, pageContent, 1, pageItemsNum);
                            pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                            initPagination(pageTotalNum);
                            $("#entityManagementFacility_searchResult").css('display', '');
                            break;
                        }
                    }
                    $("#editEntityFacility").foundation('close');
                }
            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('新增失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
});

$("#deleteEntityItem").click(function () {
    // if (!(selectedItem.size && selectedItem.size >0)) {
    //     showMessage('请勾选需要删除的内容！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
    //     return;
    // }
    switch (currentManageType) {
        case "humanManage":
            $(":checkbox[name='entityManagementHuman_checkbox']").each(function (i) {
                if ($(this).prop('checked')) {
                    selectedItem.add(parseInt($(this).attr('id')));
                }
            });
            if (selectedItem.dataStore.length>0) {
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'manager/delhuman',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            humanID: selectedItem.dataStore
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                $("#entityManagementHuman_searchBtn").click();
                                selectedItem = new Set();
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            } else {
                showMessage('请勾选需要删除的内容！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');

            }
            break;
        case 'schoolManage':
            $(":checkbox[name='entityManagementSchool_checkbox']").each(function (i) {
                if ($(this).prop('checked')) {
                    selectedItem.add(parseInt($(this).attr('id')));
                }
            });
            if (selectedItem.dataStore.length >0) {
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'schoolmgr/delschool',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            schoolID: selectedItem.dataStore
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                initPage(loginID, humanType, token);
                                schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                                $("#schoolManagementMenuBtn").click();
                                selectedItem = new Set();
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            } else {
                showMessage('请勾选需要删除的内容！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');

            }
            break;
        case 'gradeManage':
            $(":checkbox[name='entityManagementGrade_checkbox']").each(function (i) {
                if ($(this).prop('checked')) {
                    selectedItem.add(parseInt($(this).attr('id')));
                }
            });
            if (selectedItem.dataStore.length >0) {
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'schoolmgr/delgrade',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            gradeID: selectedItem.dataStore
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                initPage(loginID, humanType, token);
                                schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                                var schoolIDValue = parseInt($("#gradeManagement_schoolID").val());
                                for (var i = 0; i < schoolData.length; i++) {
                                    if (schoolIDValue == schoolData[i].schoolID) {
                                        pageContent = schoolData[i].gradeInfo;
                                        currentPageNum =1;
                                        changePage(currentManageType, pageContent, 1, pageItemsNum);
                                        pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                                        initPagination(pageTotalNum);
                                        $("#entityManagementGrade_searchResult").css('display', '');
                                        break;
                                    }
                                }
                                selectedItem = new Set();
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            } else {
                showMessage('请勾选需要删除的内容！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');

            }
            break;
        case 'classManage':
            $(":checkbox[name='entityManagementClass_checkbox']").each(function (i) {
                if ($(this).prop('checked')) {
                    selectedItem.add(parseInt($(this).attr('id')));
                }
            });
            if (selectedItem.dataStore.length >0) {
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'schoolmgr/delclass',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            classID: selectedItem.dataStore
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                initPage(loginID, humanType, token);
                                schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                                var schoolIDValue = parseInt($("#classManagement_schoolID").val());
                                var gradeIDValue = parseInt($("#classManagement_gradeID").val());
                                outer:   for (var i = 0; i < schoolData.length; i++) {
                                    if (schoolIDValue == schoolData[i].schoolID) {
                                        var gradeInfo = schoolData[i].gradeInfo;
                                        for (var j = 0; j < gradeInfo.length; j++) {
                                            if (gradeIDValue == gradeInfo[j].gradeID) {
                                                pageContent = gradeInfo[j].classInfoList;
                                                currentPageNum =1;
                                                changePage(currentManageType, pageContent, 1, pageItemsNum);
                                                pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                                                initPagination(pageTotalNum);
                                                $("#entityManagementClass_searchResult").css('display', '');
                                                break outer;
                                            }
                                        }
                                    }
                                }
                                selectedItem = new Set();
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('查询失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            } else {
                showMessage('请勾选需要删除的内容！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');

            }
            break;
        case 'facilityManage':
            $(":checkbox[name='entityManagementFacility_checkbox']").each(function (i) {
                if ($(this).prop('checked')) {
                    selectedItem.add(parseInt($(this).attr('id')));
                }
            });
            if (selectedItem.dataStore.length >0) {
                zeroModal.loading(6);
                $.ajax(
                    {
                        url: serverPath + 'delMacDeviceByMacID',
                        async: true,
                        type: 'POST',
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify({
                            macID: selectedItem.dataStore
                        }),
                        dataType: "json",
                        success: function (data) {
                            zeroModal.closeAll();
                            showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                            if (data.success) {
                                initPage(loginID, humanType, token);
                                schoolData = JSON.parse(sessionStorage.getItem('schoolData'));
                                var schoolIDValue = parseInt($("#facilityManagement_schoolID").val());
                                for (var i = 0; i < schoolData.length; i++) {
                                    if (schoolIDValue == schoolData[i].schoolID) {
                                        pageContent = schoolData[i].macInfo;
                                        currentPageNum =1;
                                        changePage(currentManageType, pageContent, 1, pageItemsNum);
                                        pageTotalNum = Math.ceil(pageContent.length / pageItemsNum);
                                        initPagination(pageTotalNum);
                                        $("#entityManagementFacility_searchResult").css('display', '');
                                        break;
                                    }
                                }
                                selectedItem = new Set();
                            }
                        },
                        error: function (xhr) {
                            zeroModal.closeAll();
                            showMessage('删除失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                        }
                    }
                )
            } else {
                showMessage('请勾选需要删除的内容！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');

            }
            break;
        default:
            console.log("error");
            break;
    }
    ;
});

$("#applyApproval").click(function () {
    $(":checkbox[name='applyManage_checkbox']").each(function (i) {
        if ($(this).prop('checked')) {
            selectedItem.add(parseInt($(this).attr('id')));
        }
    });
    if (selectedItem.dataStore.length >0) {
        zeroModal.loading(6);
        $.ajax(
            {
                url: serverPath + 'manager/checkhuman',
                async: true,
                type: 'POST',
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify({
                    humanID: selectedItem.dataStore,
                    checkFlag: 1
                }),
                dataType: "json",
                success: function (data) {
                    zeroModal.closeAll();
                    showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                    if (data.success) {
                        selectedItem = new Set();
                        $("#applyManagement_searchBtn").click();
                    }
                },
                error: function (xhr) {
                    zeroModal.closeAll();
                    showMessage('操作失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                }
            }
        )
    } else {
        showMessage('请勾选后执行操作！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');

    }

});
$("#applyReject").click(function () {
    $(":checkbox[name='applyManage_checkbox']").each(function (i) {
        if ($(this).prop('checked')) {
            selectedItem.add(parseInt($(this).attr('id')));
        }
    });
    if (selectedItem.dataStore.length >0) {
        zeroModal.loading(6);
        $.ajax(
            {
                url: serverPath + 'manager/checkhuman',
                async: true,
                type: 'POST',
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify({
                    humanID: selectedItem.dataStore,
                    checkFlag: 2
                }),
                dataType: "json",
                success: function (data) {
                    zeroModal.closeAll();
                    showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                    if (data.success) {
                        selectedItem = new Set();
                        $("#applyManagement_searchBtn").click();
                    }
                },
                error: function (xhr) {
                    zeroModal.closeAll();
                    showMessage('操作失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                }
            }
        )
    } else {
        showMessage('请勾选后执行操作！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');

    }

});

//文件上传事件
$("#batchImportFileUpload").on('change',function (e) {
    var fileName = $('#batchImportFileUpload')[0].files[0].name;
    $("#picFileName").html(fileName);
    uploadPic.append('file',$('#batchImportFileUpload')[0].files[0]);
});
$("#batchImportADUpload").on('change',function (e) {
    var fileName = $('#batchImportADUpload')[0].files[0].name;
    $("#adFileName").html(fileName);
    uploadAD.append('file',$('#batchImportADUpload')[0].files[0]);
});
$("#uploadApp").on('change',function (e) {
    var fileName = $('#uploadApp')[0].files[0].name;
    $("#appFileName").html(fileName);
    uploadApp.append('file',$('#uploadApp')[0].files[0]);
});

$("#batchImportFileUploadBtn").click(function (){
    if(uploadPic.get("file")){
        if($("#pichSchool").val()){
            uploadPic.append('schoolID',parseInt($("#pichSchool").val()));
        }else{
            uploadPic.append('schoolID',parseInt(loginHuman.schoolID));
        }
        console.log(uploadPic.get("schoolID"));
        zeroModal.loading(6);
        $.ajax({
            type:"post",
            url:serverPath+"uploadDownload/batchupload",
            data:uploadPic,
            processData : false,
            contentType : false,
            success:function(data){
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                uploadPic = new FormData();
                $("#picFileName").html('');
            },
            error:function(){
                zeroModal.closeAll();
                showMessage('操作失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        });
    }else{
        showMessage('请选择要上传的文件！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
    }

});

$("#batchImportADUploadBtn").click(function (){
    if(uploadAD.get("file")){
        if($("#adPichSchool").val()){
            uploadAD.append('schoolID',parseInt($("#adPichSchool").val()));
        }else{
            uploadAD.append('schoolID',parseInt(loginHuman.schoolID));
        }
        console.log(uploadAD.get("schoolID"));
        zeroModal.loading(6);
        $.ajax({
            type:"post",
            url:serverPath+"uploadDownload/uploadadvert",
            data:uploadAD,
            processData : false,
            contentType : false,
            success:function(data){
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                uploadAD = new FormData();
                $("#adFileName").html('');
            },
            error:function(){
                zeroModal.closeAll();
                showMessage('操作失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        });
    }else{
        showMessage('请选择要上传的文件！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
    }
});

$("#uploadAppBtn").click(function (){
    if(uploadApp.get("file")){
        if($("#appVersion").val() && $("#appOS").val()){
            uploadApp.append('versionNum',$("#appVersion").val());
            uploadApp.append('Os',$("#appOS").val());
        }else{
            showMessage('信息不全！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            return;
        }
        console.log(uploadApp.get("appVersion"));
        zeroModal.loading(6);
        $.ajax({
            type:"post",
            url:serverPath+"uploadDownload/uploadversion",
            data:uploadApp,
            processData : false,
            contentType : false,
            success:function(data){
                zeroModal.closeAll();
                showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                uploadApp = new FormData();
                $("#appFileName").html('');
            },
            error:function(){
                zeroModal.closeAll();
                showMessage('操作失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        });
    }else{
        showMessage('请选择要上传的文件！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
    }

});
//mtree init
(function ($, window, document, undefined) {

    // Only apply if mtree list exists
    if ($('ul.mtree').length) {


        // Settings
        var collapsed = true; // Start with collapsed menu (only level 1 items visible)
        var close_same_level = false; // Close elements on same level when opening new node.
        var duration = 400; // Animation duration should be tweaked according to easing.
        var listAnim = true; // Animate separate list items on open/close element (velocity.js only).
        var easing = 'easeOutQuart'; // Velocity.js only, defaults to 'swing' with jquery animation.


        // Set initial styles
        $('.mtree ul').css({
            'overflow': 'hidden',
            'height': (collapsed) ? 0 : 'auto',
            'display': (collapsed) ? 'none' : 'block'
        });

        // Get node elements, and add classes for styling
        var node = $('.mtree li:has(ul)');
        node.each(function (index, val) {
            $(this).children(':first-child').css('cursor', 'pointer')
            $(this).addClass('mtree-node mtree-' + ((collapsed) ? 'closed' : 'open'));
            $(this).children('ul').addClass('mtree-level-' + ($(this).parentsUntil($('ul.mtree'), 'ul').length + 1));
        });

        // Set mtree-active class on list items for last opened element
        $('.mtree li > *:first-child').on('click.mtree-active', function (e) {
            if ($(this).parent().hasClass('mtree-closed')) {
                $('.mtree-active').not($(this).parent()).removeClass('mtree-active');
                $(this).parent().addClass('mtree-active');
            } else if ($(this).parent().hasClass('mtree-open')) {
                $(this).parent().removeClass('mtree-active');
            } else {
                $('.mtree-active').not($(this).parent()).removeClass('mtree-active');
                $(this).parent().toggleClass('mtree-active');
            }
        });

        // Set node click elements, preferably <a> but node links can be <span> also
        node.children(':first-child').on('click.mtree', function (e) {

            // element vars
            var el = $(this).parent().children('ul').first();
            var isOpen = $(this).parent().hasClass('mtree-open');

            // close other elements on same level if opening
            if ((close_same_level || $('.csl').hasClass('active')) && !isOpen) {
                var close_items = $(this).closest('ul').children('.mtree-open').not($(this).parent()).children('ul');

                // Velocity.js
                if ($.Velocity) {
                    close_items.velocity({
                        height: 0
                    }, {
                        duration: duration,
                        easing: easing,
                        display: 'none',
                        delay: 100,
                        complete: function () {
                            setNodeClass($(this).parent(), true)
                        }
                    });

                    // jQuery fallback
                } else {
                    close_items.delay(100).slideToggle(duration, function () {
                        setNodeClass($(this).parent(), true);
                    });
                }
            }

            // force auto height of element so actual height can be extracted
            el.css({'height': 'auto'});

            // listAnim: animate child elements when opening
            if (!isOpen && $.Velocity && listAnim) el.find(' > li, li.mtree-open > ul > li').css({'opacity': 0}).velocity('stop').velocity('list');

            // Velocity.js animate element
            if ($.Velocity) {
                el.velocity('stop').velocity({
                    //translateZ: 0, // optional hardware-acceleration is automatic on mobile
                    height: isOpen ? [0, el.outerHeight()] : [el.outerHeight(), 0]
                }, {
                    queue: false,
                    duration: duration,
                    easing: easing,
                    display: isOpen ? 'none' : 'block',
                    begin: setNodeClass($(this).parent(), isOpen),
                    complete: function () {
                        if (!isOpen) $(this).css('height', 'auto');
                    }
                });

                // jQuery fallback animate element
            } else {
                setNodeClass($(this).parent(), isOpen);
                el.slideToggle(duration);
            }

            // We can't have nodes as links unfortunately
            e.preventDefault();
        });

        // Function for updating node class
        function setNodeClass(el, isOpen) {
            if (isOpen) {
                el.removeClass('mtree-open').addClass('mtree-closed');
            } else {
                el.removeClass('mtree-closed').addClass('mtree-open');
            }
        }

        // List animation sequence
        if ($.Velocity && listAnim) {
            $.Velocity.Sequences.list = function (element, options, index, size) {
                $.Velocity.animate(element, {
                    opacity: [1, 0],
                    translateY: [0, -(MainPage + 1)]
                }, {
                    delay: MainPage * (duration / size / 2),
                    duration: duration,
                    easing: easing
                });
            };
        }

        // Fade in mtree after classes are added.
        // Useful if you have set collapsed = true or applied styles that change the structure so the menu doesn't jump between states after the function executes.
        if ($('.mtree').css('opacity') == 0) {
            if ($.Velocity) {
                $('.mtree').css('opacity', 1).children().css('opacity', 0).velocity('list');
            } else {
                $('.mtree').show(200);
            }
        }
    }
}(jQuery, this, this.document));