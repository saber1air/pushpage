package com.deliver.util;


import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by pdl on 2018/10/15.
 */
public class PushAppUtil {
    //private static Logger log = LoggerFactory.getLogger(NoticeUtil.class);

    // 由IGetui管理页面生成，是您的应用与SDK通信的标识之一，每个应用都对应一个唯一的AppID
    private static String appId = "";
    // 预先分配的第三方应用对应的Key，是您的应用与SDK通信的标识之一。
    private static String appKey = "";
    // 个推服务端API鉴权码，用于验证调用方合法性。在调用个推服务端API时需要提供。（请妥善保管，避免通道被盗用）
    private static String masterSecret = "";

    private static String url = "https://api.getui.com/apiex.htm";

    private static final String IOS = "iOS";
    private static final String ANDROID = "Android";

    public static void main(String[] args) throws Exception {
        Map<String, String> msg = new HashMap<>();
        msg.put("title", "peerslee, 新年快乐ads！");
        msg.put("titleText", "sdsfsd,");
        msg.put("transText", "");

        String appId = "BpMp5LGylp7abrsVsJvkKA";
        String appKey = "7N6ijFjjAB76HZWrBh4Vq8";
        String masterSecret = "1rEtLTwU7mAdjQ8Kjx4mc2";
        String[] cids = {"7a419805f2efaab701829dbac68739f7"};

        for(String cid : cids) {
            System.out.println("正在发送消息...");
            /*IPushResult ret =  pushMsgToAll(msg);
            System.out.println(ret.getResponse().toString());*/
            test1();
        }
    }

    public static void test1() {
        IGtPush push = new IGtPush(url, appKey, masterSecret);

        // 新建消息类型 单独推送给用户采用SingleMessage
        SingleMessage singleMessage = new SingleMessage();

        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle("重要通知！！！！");
        template.setText("测试消息");
        template.setUrl("http://getui.com");

        // 设置接收信息的cid集合
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        // 设置接收信息的cid
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId("--");
        // 推送接口
        // pushMessageToSingle==单个用户/ pushMessageToList==一批用户/
        // pushMessageToApp==应用中的全部用户。
        singleMessage.setData(template);
        IPushResult result = push.pushMessageToSingle(singleMessage, target);
        String response = result.getResponse().toString();
        System.out.println("返回值：" + response);
    }


    /**
     * 发送消息至所有安装app用户
     * 通过clientid 发送
     */
    public static IPushResult pushMsgToAll( Map<String, String> appMsgInfo){
        System.setProperty("gexin_pushList_needDetails", "true");
        IGtPush push = new IGtPush(url,appKey, masterSecret);
        AppMessage message = new AppMessage();
        // 通知透传模板
        TransmissionTemplate template = getTransmissionTemplate(appMsgInfo);
        message.setData(template);
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        //推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions();

        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);
        message.setConditions(cdt);
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
        return ret;
    }

    /**
     * 分平台发送消息至app用户 Android、iOS
     * 通过clientid 发送
     */
    public static IPushResult pushMsgByType(Map<String, String> appMsgInfo,String type){
        System.setProperty("gexin_pushList_needDetails", "true");
        IGtPush push = new IGtPush(appKey, masterSecret);
        AppMessage message = new AppMessage();

        TransmissionTemplate template = getTransmissionTemplate(appMsgInfo);
        message.setData(template);
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        //推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions();
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);
        //手机类型
        List<String> phoneTypeList = new ArrayList<String>();
        phoneTypeList.add(type.toUpperCase());// Android/iOS
        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
        message.setConditions(cdt);
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
        return ret;
    }

    public static TransmissionTemplate getTransmissionTemplate( Map<String, String> appMsgInfo){
        TransmissionTemplate  template = new TransmissionTemplate ();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(appMsgInfo.get("transText"));
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("$由客户端定义");

        //简单模式APNPayload.SimpleMsg
        //  payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
        //自定义类型参数
        payload.addCustomMsg("type", 1);//推送类型1、全部，2android，3iOS，4用户Id
        //payload.addCustomMsg("link", appMsgInfo.getLink());//推送url路径

        //字典模式使用APNPayload.DictionaryAlertMsg
        payload.setAlertMsg(getDictionaryAlertMsg());

        // 添加多媒体资源
   /* payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.video)
            .setResUrl("http://ol5mrj259.bkt.clouddn.com/test2.mp4")
            .setOnlyWifi(true));*/

        template.setAPNInfo(payload);
        return template;
    }

    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setTitle("peerslee, 新年快乐ads！");//推送标题
        alertMsg.setBody("sdsdsd");//推送内容
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey("LocKey");
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image"); // iOS8.2以上版本支持 alertMsg.setTitle("Title");
        alertMsg.setTitleLocKey("TitleLocKey");
        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
    }


}
