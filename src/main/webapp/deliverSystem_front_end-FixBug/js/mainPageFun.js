/**
 * Created by pdl on 2018/9/19.
 */
// var serverPath = 'http://10.0.3.134:8080/deliver/';
// var facePath = 'http://10.0.3.134:5000/rest/2.0/face/v2/get_features';
var serverPath = '';
var facePath = 'http://106.12.125.175:5000/rest/2.0/face/v2/get_features';
/**
 * 根据人员级别初始化学校-年级-班级信息
 * @param humanID humanType
 */
function initPage(humanID,humanType,token) {
    zeroModal.loading(6);
    $.ajax(
        {
            url: serverPath+"manager/initpc",
            async: false,
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify({
                humanid: humanID,
                token:token
            }),
            dataType: "json",
            success: function (data) {
                zeroModal.closeAll();
                if(data.success){
                    console.log(data);
                    if(data.data.school){
                        sessionStorage.removeItem('schoolData');
                        sessionStorage.setItem('schoolData',JSON.stringify(data.data.school)) ;
                    }else{
                        sessionStorage.setItem('schoolData',[]) ;
                    }

                    $("[access]").each(function (i) {
                        var itemAccess = $(this).attr('access');
                        if (parseInt(itemAccess) < parseInt(humanType)) {
                            $(this).css('display','none');
                        }else{
                            $(this).css('display','');
                        }
                    });
                }else{
                    showMessage(data.message, 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                };
            },
            error: function (xhr) {
                zeroModal.closeAll();
                showMessage('初始化失败！', 2000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        }
    )
}

/**
 * 根据菜单按钮控制页面内容隐藏和展示
 * @param menuBtnName
 */
function initContentView(menuBtnName){
    selectedItem = new Set();
    var contentList = ['humanManagement','schoolManagement','gradeManagement','classManagement','facilityManagement',
        'inoutManagement_atSchoolSearch','inoutManagement_record_history','applyManagement','applyManagement_searchResult',
        'entityManagementResult','inoutManagement_searchResult','entityManagementHuman_searchResult','upLoadManagement'];
    var index = menuBtnName.lastIndexOf("MenuBtn");
    var contentName = menuBtnName.substring(0,index);
    for(var i=0;i<contentList.length;i++){
        if(contentName == contentList[i]){
            $("#"+contentList[i]).css('display','');
            // console.log(contentList[i]+'  '+contentName+'true');
        }else{
            $("#"+contentList[i]).css('display','none');
            // console.log(contentList[i]+'  '+contentName+'false');
        }
    }
}

/**
 * 时间戳转时间
 * @param timestamp
 * @returns {*}
 */
function timestampToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    D = date.getDate() + ' ';
    h = ((date.getHours() < 10)?('0'+date.getHours()):(date.getHours())) + ':';
    m = (date.getMinutes() < 10)?('0'+date.getMinutes()):(date.getMinutes()) ;
    s = date.getSeconds();
    return Y + M + D + h + m;
}

/**
 * 分页初始化
 */
function initPagination(pageTotalNum) {
    $("#entityManagementResult").css('display','');
    $("#paginationPrevious").addClass('disabled');
    if(pageContent.length > pageItemsNum){
        $("#paginationNext").removeClass('disabled');
    }else{
        $("#paginationNext").addClass('disabled');
    }
    $('#paginationSelectNum').html("");
    for(var i=0;i<pageTotalNum;i++){
        $('#paginationSelectNum').append("<option value='"+(i+1)+"'>"+(i+1)+"</option>");
    }
}

/**
 * 分页切换页面显示函数
 * @param currentManageType 当前业务类型
 * @param pageContent 页面数据内容
 * @param pageNum 当前页面页数
 * @param pageItemsNum  每页数据条数
 */
function changePage(currentManageType,pageContent, pageNum,pageItemsNum) {
    if(pageContent.length != 0){
        var pageTotalNum  = Math.ceil(pageContent.length / pageItemsNum);
        if(currentPageNum > pageTotalNum || currentPageNum <1 ){
            return;
        }
    }

     if( pageNum == pageTotalNum){
         if( currentManageType == 'applyManage'){
             $("#applyPaginationNext").addClass('disabled');
             $("#applyPaginationPrevious").removeClass('disabled');
         }else{
             $("#paginationPrevious").removeClass('disabled');
             $("#paginationNext").addClass('disabled');
         }
     }
     if(pageNum == 1){
         if( currentManageType == 'applyManage'){
             $("#applyPaginationPrevious").addClass('disabled');
             $("#applyPaginationNext").removeClass('disabled');
         }else{
             $("#paginationPrevious").addClass('disabled');
             $("#paginationNext").removeClass('disabled');
         }
     }
    switch (currentManageType) {
        case "humanManage":
            $("#entityManagementHuman_searchResult_body").html('');
            for (var i = (pageNum - 1) * pageItemsNum; i < pageContent.length; i++) {
                if (i > (pageNum * pageItemsNum - 1)) {
                    break;
                }
                if(parseInt($("#entityManagementHuman_humantype").val()) == 1){
                    var item = "<tr type='' id='" + pageContent[i].humanid + "tr' manageType='humanManage'> <td> <div class='flex-child-shrink'>" +
                        " <img style='margin-bottom: 0rem;height: 120px;width: 100px' class='thumbnail' src='" + serverPath+pageContent[i].media_path + "'>" +
                        " </div> </td> <td>" +
                        pageContent[i].human_name + "</td><td>" + pageContent[i].tel + "</td>" +
                        "<td>" + timestampToTime(pageContent[i].create_time) + "</td>" +
                        "<td >" + humanTypeList[parseInt(pageContent[i].human_type)] + "</td>" +
                        "<td>" + manageTypeList[parseInt(pageContent[i].manager_type)] + "</td>" +
                        "<td type='pickItem' style='margin-top: 1rem;text-align: center'><input id='" + pageContent[i].humanid + "' name='entityManagementHuman_checkbox' style='margin-top: 1rem;' type='checkbox'></td> </tr>";
                }else{
                    var item = "<tr type='open' id='" + pageContent[i].humanid + "tr' manageType='humanManage'> <td> <div class='flex-child-shrink'>" +
                        " <img style='margin-bottom: 0rem;height: 120px;width: 100px' class='thumbnail' src='" + serverPath+pageContent[i].media_path + "'>" +
                        " </div> </td> <td>" +
                        pageContent[i].human_name + "</td><td>" + pageContent[i].tel + "</td>" +
                        "<td>" + timestampToTime(pageContent[i].create_time) + "</td>" +
                        "<td >" + humanTypeList[parseInt(pageContent[i].human_type)] + "</td>" +
                        "<td>" + manageTypeList[parseInt(pageContent[i].manager_type)] + "</td>" +
                        "<td type='pickItem' style='margin-top: 1rem;text-align: center'><input id='" + pageContent[i].humanid + "' name='entityManagementHuman_checkbox' style='margin-top: 1rem;' type='checkbox'></td> </tr>";
                }

                $("#entityManagementHuman_searchResult_body").append(item);
            }
            break;
        case "schoolManage":
            $("#entityManagementSchool_searchResult_body").html('');
            for (var i = (pageNum - 1) * pageItemsNum; i < pageContent.length; i++) {
                if (i > (pageNum * pageItemsNum - 1)) {
                    break;
                }
                var item = "<tr type='open' id='" + pageContent[i].schoolID + "tr' manageType='schoolManage'> <td> </td> <td> </td><td>" + pageContent[i].address + "</td>" +
                    "<td>" + pageContent[i].schoolName + "</td>" +
                    "<td >" +  "" + "</td>" +
                    "<td>" + " "+ "</td>" +
                    "<td>" + " "+ "</td>" +
                    "<td type='pickItem' style='margin-top: 1rem;text-align: center'><input id='" + pageContent[i].schoolID + "' name='entityManagementSchool_checkbox' style='margin-top: 1rem;' type='checkbox'></td> </tr>";
                $("#entityManagementSchool_searchResult_body").append(item);
            }
            break;
        case "gradeManage":
            $("#entityManagementGrade_searchResult_body").html('');
            for (var i = (pageNum - 1) * pageItemsNum; i < pageContent.length; i++) {
                if (i > (pageNum * pageItemsNum - 1)) {
                    break;
                }
                var classNum = (pageContent[i].classInfoList)? (pageContent[i].classInfoList.length ):(0);
                var item = "<tr type='open' id='" + pageContent[i].gradeID + "tr' manageType='gradeManage'> <td>"+ $("#gradeManagement_schoolID").find("option:selected").text() +"</td> <td> "+pageContent[i].gradeName+"</td><td>" + timestampToTime(pageContent[i].createTime) + "</td>" +
                    "<td>" + pageContent[i].gradeNum + "</td>" +
                    "<td >" + classNum + "</td>" +
                    "<td type='pickItem' style='margin-top: 1rem;text-align: center'><input id='" + pageContent[i].gradeID + "' name='entityManagementGrade_checkbox' style='margin-top: 1rem;' type='checkbox'></td> </tr>";
                $("#entityManagementGrade_searchResult_body").append(item);
            }
            break;
        case "classManage":
            $("#entityManagementClass_searchResult_body").html('');
            for (var i = (pageNum - 1) * pageItemsNum; i < pageContent.length; i++) {
                if (i > (pageNum * pageItemsNum - 1)) {
                    break;
                }
                var item = "<tr type='open' id='" + pageContent[i].classID + "tr' manageType='classManage'> <td> "+pageContent[i].classNum+"</td> <td> "+pageContent[i].className+"</td><td>" + timestampToTime(pageContent[i].createTime) + "</td>" +
                    "<td>" + "" + "</td>" +
                    "<td >" +  "" + "</td>" +
                    "<td type='pickItem' style='margin-top: 1rem;text-align: center'><input id='" + pageContent[i].classID + "' name='entityManagementClass_checkbox' style='margin-top: 1rem;' type='checkbox'></td> </tr>";
                $("#entityManagementClass_searchResult_body").append(item);
            }
            break;
        case "facilityManage":
            $("#entityManagementFacility_searchResult_body").html('');
            for (var i = (pageNum - 1) * pageItemsNum; i < pageContent.length; i++) {
                if (i > (pageNum * pageItemsNum - 1)) {
                    break;
                }
                var item = "<tr type='open' id='" + pageContent[i].macID + "tr' manageType='facilityManage'> <td> "+$("#facilityManagement_schoolID").find("option:selected").text()+"</td> <td> "+pageContent[i].macName+"</td><td>" + timestampToTime(pageContent[i].createTime) + "</td>" +
                    "<td>" + "" + "</td>" +
                    "<td >" + timestampToTime(pageContent[i].updateTime) + "</td>" +
                    "<td type='pickItem' style='margin-top: 1rem;text-align: center'><input id='" + pageContent[i].macID + "' name='entityManagementFacility_checkbox' style='margin-top: 1rem;' type='checkbox'></td> </tr>";

                $("#entityManagementFacility_searchResult_body").append(item);
            }
            break;
        case "applyManage":
            $("#applyManagement_searchResult_body").html('');
            for (var i = (pageNum - 1) * pageItemsNum; i < pageContent.length; i++) {
                if (i > (pageNum * pageItemsNum - 1)) {
                    break;
                }
                var applyMessage = ($("#applyManagement_applyType").val() == 1)?('申请成为:'+manageTypeList[parseInt(pageContent[i].applay_auth)]):("");
                var item = "<tr> <td> "+timestampToTime(pageContent[i].create_time)+"</td> <td><div class=\"flex-child-shrink\"><img style='width: 100px;height: 120px' class=\"dashboard-table-image\" src='"+serverPath+
                    pageContent[i].media_path+"'></div></td><td>" + pageContent[i].human_name + "</td>" +
                    "<td>" + manageTypeList[parseInt(pageContent[i].manager_type)] + "</td>" +
                    "<td >" + pageContent[i].tel + "</td>" +
                    "<td >" + applyMessage + "</td>" +
                    "<td style='margin-top: 1rem;text-align: center'><input id='" + pageContent[i].humanid + "' name='applyManage_checkbox' style='margin-top: 1rem;' type='checkbox'></td> </tr>";

                $("#applyManagement_searchResult_body").append(item);
            }
        default:
            console.log('error');
            break;
    }
    $("tr[type='open']").click(function () {
        console.log('clicked');
        fillInfo(parseInt(this.id.split('tr')),this.getAttribute('manageType'));
    });
    $(':checkbox').click(function(evt){
        var is = $(this).attr('checked');
        if ( is )
        {
            $(this).removeAttr('checked');

        }
        else
        {
            $(this).attr('checked','checked');
        }
        // 阻止冒泡
        evt.stopPropagation();
    });
    //  var child = $("td[type='pickItem']").children("input");
    // $("td[type='pickItem']").click(function (evt) {
    //     // console.log('picked');
    //     var child = $(this).children("input");
    //     if(child.attr("checked")){
    //         child.removeAttr('checked');
    //
    //     }else{
    //         child.attr('checked','checked');
    //     }
    //     evt.stopPropagation();
    // });
}
function switchMan2Hum(manageType) {
    var humanType = -1;
    switch (manageType){
        case 0:
            humanType = 0;
            break;
        case 1:
            humanType = 4;
            break;
        case 2:
            humanType = 3;
            break;
        case 3:
        case 4:
            humanType = 2;
            break;
        case 5:
        case 6:
            humanType = 1;
        default:
            break;
    }
    return humanType;
}

function fillInfo(itemID,itemManageType) {
    switch  (itemManageType) {
        case "humanManage":
            for(var i =0;i<pageContent.length;i++){
                if(itemID == pageContent[i].humanid){
                    //根据不同managerType确定不同的显示内容
                    $("#editEntityHuman_humanID").val(itemID);
                    $("#editEntityHuman").foundation('open');
                    $("#editEntityHuman_humanName").val(pageContent[i].human_name);
                    $("#editEntityHuman_facePic").attr('src',serverPath+pageContent[i].media_path);
                    $("#editFaceChangeFlag").val('false');
                    $("#editEntityHuman_manageType").val(pageContent[i].manager_type);
                    var pageSchool = $("#editEntityHuman_school");
                    var pageGrade =  $("#editEntityHuman_grade");
                    var pageClass =  $("#editEntityHuman_class");
                    pageSchool.html("<option value='-1'>请选择学校</option>");
                    pageGrade.html('');
                    pageClass.html('');
                    for (var j = 0; j < schoolData.length; j++) {
                        var schoolItem = schoolData[j];
                        var schoolResult = "<option value=" + schoolItem.schoolID + ">" + schoolItem.schoolName + "</option>";
                        pageSchool.append(schoolResult);
                    };
                    if(pageContent[i].school_name){
                        pageSchool.val(pageContent[i].schoolid);
                        for (var j = 0; j < schoolData.length; j++) {
                            var schoolItem = schoolData[j];
                            if(pageContent[i].schoolid == schoolItem.schoolID){
                                var gradeList = schoolItem.gradeInfo;
                                for(var m=0; m < gradeList.length;m++){
                                    var gradeItem = gradeList[m];
                                    var gradeResult = "<option value=" + gradeItem.gradeID + ">" + gradeItem.gradeName + "</option>";
                                    pageGrade.append(gradeResult);
                                    if(gradeItem.gradeID == pageContent[i].gradeid){
                                        var classList = gradeItem.classInfoList;
                                        for(var n=0; n < classList.length;n++){
                                            var classItem = classList[n];
                                            var classResult = "<option value=" + classItem.classID + ">" + classItem.className + "</option>";
                                            pageClass.append(classResult);
                                        }
                                    }
                                }
                                pageGrade.val(pageContent[i].gradeid);
                                pageClass.val(pageContent[i].classid);
                            }
                        };
                    }
                    switch (parseInt(pageContent[i].manager_type)){
                        case 0:
                            $("#editEntityHuman_schoolDiv").css('display','');
                            $("#editEntityHuman_gradeDiv").css('display','');
                            $("#editEntityHuman_classDiv").css('display','');
                            // $("#editEntityHuman_parentNameDiv").css('display','');
                            // $("#editEntityHuman_parentTelDiv").css('display','');
                            $("#editEntityHuman_telDiv").css('display','none');
                            $("#editEntityHuman_passwordDiv").css('display','none');
                            break;
                        case 1:
                        case 5:
                        case 6:
                            $("#editEntityHuman_schoolDiv").css('display','none');
                            $("#editEntityHuman_gradeDiv").css('display','none');
                            $("#editEntityHuman_classDiv").css('display','none');
                            // $("#editEntityHuman_parentNameDiv").css('display','none');
                            // $("#editEntityHuman_parentTelDiv").css('display','none');
                            $("#editEntityHuman_telDiv").css('display','');
                            $("#editEntityHuman_passwordDiv").css('display','');
                            $("#editEntityHuman_tel").val(pageContent[i].tel);
                            $("#editEntityHuman_password").val(pageContent[i].password);
                            break;
                        case 2:
                        case 4:
                            $("#editEntityHuman_schoolDiv").css('display','');
                            $("#editEntityHuman_gradeDiv").css('display','none');
                            $("#editEntityHuman_classDiv").css('display','none');
                            // $("#editEntityHuman_parentNameDiv").css('display','none');
                            // $("#editEntityHuman_parentTelDiv").css('display','none');
                            $("#editEntityHuman_telDiv").css('display','');
                            $("#editEntityHuman_passwordDiv").css('display','');
                            $("#editEntityHuman_tel").val(pageContent[i].tel);
                            $("#editEntityHuman_password").val(pageContent[i].password);
                            break;
                        case 3:
                            $("#editEntityHuman_schoolDiv").css('display','');
                            $("#editEntityHuman_gradeDiv").css('display','');
                            $("#editEntityHuman_classDiv").css('display','');
                            // $("#editEntityHuman_parentNameDiv").css('display','none');
                            // $("#editEntityHuman_parentTelDiv").css('display','none');
                            $("#editEntityHuman_telDiv").css('display','');
                            $("#editEntityHuman_passwordDiv").css('display','');
                            $("#editEntityHuman_tel").val(pageContent[i].tel);
                            $("#editEntityHuman_password").val(pageContent[i].password);
                            break;
                        default:
                            console.log('error');
                            break;
                    }
                    break;
                }
            }
            break;
        case "schoolManage":
            for(var i =0;i<pageContent.length;i++){
                if(itemID == pageContent[i].schoolID){
                    $("#editEntitySchool").foundation('open');
                    $("#editEntitySchool_schoolID").val(itemID);
                    $("#editEntitySchool_schoolName").val(pageContent[i].schoolName);
                    $("#editEntitySchool_president").val(pageContent[i].president);
                    $("#editEntitySchool_address").val(pageContent[i].address);
                    break;
                }
            }
            break;
        case "gradeManage":
            for(var i =0;i<pageContent.length;i++){
                if(itemID == pageContent[i].gradeID){
                    $("#editEntityGrade").foundation('open');
                    $("#editEntitySchool_gradeID").val(itemID);
                    $("#editEntityGrade_gradeNum").val(pageContent[i].gradeNum);
                    $("#editEntityGrade_gradeName").val(pageContent[i].gradeName);
                    break;
                }
            }
            break;
        case "classManage":
            for(var i =0;i<pageContent.length;i++){
                if(itemID == pageContent[i].classID){
                    $("#editEntityClass").foundation('open');
                    $("#editEntitySchool_classID").val(itemID);
                    $("#editEntityClass_classNum").val(pageContent[i].classNum);
                    $("#editEntityClass_className").val(pageContent[i].className);
                    break;
                }
            }
            break;
        case "facilityManage":
            for(var i =0;i<pageContent.length;i++){
                if(itemID == pageContent[i].macID){
                    $("#editEntityFacility").foundation('open');
                    $("#editEntitySchool_facilityID").val(itemID);
                    $("#editEntityFacility_macName").val(pageContent[i].macName);
                    break;
                }
            }
            break;
    }
}

function imgPreview(fileDom,type) { //判断是否支持FileReader

    if(window.FileReader) {
        var reader = new FileReader();
    } else {
        alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
    }
    //获取文件
    var file = fileDom.files[0];
    var imageType = /^image\//;
    //是否是图片
    if(!imageType.test(file.type)) {
        alert("请选择图片！");
        return;
    }
    //读取完成
    reader.onload = function(e) {

        var temp = e.target.result.split(',')[1];
        var imgPre = e.target.result.split(',')[0]+",";
        zeroModal.loading(6);
        $.ajax({
            url: facePath,
            async: false,
            type: 'POST',
            data: JSON.stringify({
                image: temp,
                max_face_num: '1',
                token: 'token'
            }),
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function(data) {
                zeroModal.closeAll();
                if(data.result) {

                    if(data.result.length > 0) {
                        $("#editFaceChangeFlag").val('true');
                        //data:image/jepg;base64,
                        faceImg = '' + data.result[0].crop_img;
                        if(type =='add'){
                            $("#addEntityHuman_facePic").attr('src',imgPre+faceImg);
                            $("#addEntityHuman_facePic").css('display','');
                        }else{
                            $("#editEntityHuman_facePic").attr('src',imgPre+faceImg);
                        }
                        faceFeatrue = data.result[0].features.split(',');
                        console.log(faceFeatrue);

                    } else {

                        console.log("返回的人脸数量为0");
                        showMessage("检测出人脸数量为0 ", 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                    }
                } else {

                    console.log("算法引擎系统故障...");
                    showMessage("算法引擎系统故障... ", 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
                }
            },
            error: function(data) {
                zeroModal.closeAll();
                console.log("算法引擎系统故障... ");
                showMessage("算法引擎系统故障...", 3000, true, 'bounceInUp-hastrans', 'bounceOutDown-hastrans');
            }
        })


    };
    reader.readAsDataURL(file);
}
