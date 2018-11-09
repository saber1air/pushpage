package com.deliver.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class ToInterface {
 
	public static final String POST_URL = " http://localhost:5000/rest/2.0/face/v2/get_features";
//	public static final String POST_URL = " http://localhost:5000/rest/2.0/face/v2/get_features";

	public static StringBuilder httpURLConnectionPOST(String parm) {//parm 你要post的数据  
		PrintWriter out = null;
		StringBuilder sb = new StringBuilder(); // 用来存储响应数据
		try {
			URL url = new URL(POST_URL);
			// 将url以open方法返回的urlConnection 连接强转为HttpURLConnection连接
			// (标识一个url所引用的远程对象连接)
			// 此时cnnection只是为一个连接对象,待连接中
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置连接输出流为true,默认false (post请求是以流的方式隐式的传递参数)
			connection.setDoOutput(true);
			// 设置连接输入流为true
			connection.setDoInput(true);
			// 设置请求方式为post
			connection.setRequestMethod("POST");
			// post请求缓存设为false
			connection.setUseCaches(false);
			// 设置该HttpURLConnection实例是否自动执行重定向
			connection.setInstanceFollowRedirects(true);
			// 设置请求头里面的各个属性 (以下为设置内容的类型)
			// application/x-javascript text/xml->xml数据
			// application/x-javascript->json对象  测试后发现这么设置不行，必须是json
			// application/x-www-form-urlencoded->表单数据
			// ;charset=utf-8 必须要，不然会出现乱码
			//该样例使用json对象进行请求
//			connection.setRequestProperty("Content-Type", "application/x-javascript;charset=utf-8");
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

			// 建立连接
			connection.connect();
			// 创建输入输出流,用于往连接里面输出携带的参数,记得设置参数编码格式
			OutputStreamWriter outWriter = 
				new OutputStreamWriter(connection.getOutputStream(), "utf-8");
			out = new PrintWriter(outWriter);
			// 将参数输出到连接
			out.print(parm);
			// 输出完成后刷新并关闭流
			out.flush();
			out.close(); 
			//System.out.println(connection.getResponseCode());// 不是200的请跳楼
			// 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
			BufferedReader bf = 
				new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			// 循环读取流,若不到结尾处
			while ((line = bf.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			}
			bf.close(); // 日常关流
			connection.disconnect(); // 销毁连接	        
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(out);
		return sb;

	}
	
	
	// 创建JSONObject对象  
    public static JSONObject createJSONObject(String imageStr) throws JSONException {
//      "image": im,
//      "max_face_num": 20,
//      "token": "token"
    	
        JSONObject result = new JSONObject();  
        result.put("image", imageStr);  
        result.put("max_face_num", "20");  
        result.put("token", "token");            
          
        return result;  
    } 
    
  public Map<String,Object> pythonMap(String path) throws JSONException {
	  Base64Img base64Img = new Base64Img();
	  String strImg =Base64Img.imageToBase64Str(path);
	  JSONObject para = createJSONObject(strImg);
      StringBuilder result = httpURLConnectionPOST(para.toString());
      JSONObject myJsonObject = new JSONObject(result.toString());
      String value =  myJsonObject.get("result").toString();
      //System.out.println(value);
      String crop_img =  "";
      String face_probability = "";
      String features ;
      Map<String, Object> map = new HashMap<String,Object>();
      if(value!=null && value !="[]" && value.length()>2){
    	  JSONObject myJsonObject1 = new JSONObject(value.substring(1, value.length()-1));
    	  crop_img =  myJsonObject1.get("crop_img").toString();
          face_probability =  myJsonObject1.get("face_probability").toString();
          //System.out.println(face_probability);
          features =  myJsonObject1.get("features").toString();
          String[] featureArr = features.split(",");
          /*float[] featurefloat = new float[128];
          if(featureArr!=null && featureArr.length>0){
			  for(int i = 0 ;i<featureArr.length;i++){
				  featurefloat[i]=Float.valueOf(featureArr[i]);
			  }
		  }*/

          net.sf.json.JSONArray jsonObject = net.sf.json.JSONArray.fromObject(featureArr);
		  String jsonFeature = jsonObject.toString();
          map.put("crop_img", crop_img);
          map.put("face_probability", face_probability);
          map.put("features", jsonFeature);
          //System.out.println(features);
      }
      
      
      return map;
  }
	
  public static void main(String[] args) throws JSONException {
//	  String strImg = ImageDetection.getImageStr("C:/Users/lon/Documents/slowlon/dataset/HollywoodHeads/JPEGImages/mov_001_007591.jpeg");
	  //String strImg = ImageDetection.getImageStr("C:\\Users\\lon\\Documents\\slowlon\\face_rec_api\\test\\im_facedetect.jpg");
	  //String strImg = ImageDetection.getImageStr("D:\\JavaCode\\emp\\WebRoot\\images\\targetpicture\\yuan\\1.jpg");
	  String strImg = "";
	  Base64Img base64Img = new Base64Img();
	  Base64Img.base64StrToImage(strImg,"D:\\JavaCode\\emp\\WebRoot\\images\\targetpicture\\yuan\\222222.jpg");
	  JSONObject para = createJSONObject(strImg);
      
//      String para  = "{\"image\":" + strImg + ",\"max_face_num\":\"20\", \"token\":\"token\"}";

      //System.out.println(para.toString());

      StringBuilder result = httpURLConnectionPOST(para.toString());
      System.out.println(result);
      JSONObject myJsonObject = new JSONObject(result.toString());
      //获取对应的值
      
      
      String value1 =  myJsonObject.get("result").toString();
      System.out.println(value1);
      
      JSONObject myJsonObject1 = new JSONObject(value1.substring(1, value1.length()-1));
      String crop_img =  myJsonObject1.get("crop_img").toString();
      System.out.println(crop_img);
      String face_probability =  myJsonObject1.get("face_probability").toString();
      System.out.println(face_probability);
      String location =  myJsonObject1.get("location").toString();
      System.out.println(location);
      String features =  myJsonObject1.get("features").toString();
      System.out.println(features);
      
     /* String[] str = value1.split("\"features\":");
      String value2 = str[str.length-1];
      value2 = value2.replace("[", "");
      value2 = value2.replace("]", "");
      value2 = value2.replace("{", "");
      value2 = value2.replace("}", "");*/
      //System.out.println(value2);
      
  }
	
}
