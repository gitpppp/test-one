package com.zrscsoft.api.baidu;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;
/**
 * 图片验证码识别
 * 
 * @author admin
 *
 */
public class VerificationCodeRecognitionDemo {
	//设置APPID/AK/SK
    public static final String APP_ID = "15603863";
    public static final String API_KEY = "8KWnL5Go5OqRyubfNUDbrUyi";
    public static final String SECRET_KEY = "Fb8BSsH0mSSoM67twvOKVu2P2UNxrkH5";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        String path = System.getProperty("user.dir");
        String imgPath=path+"/images/VerificationCodeRecognition1.png";//D:\\1.png
	    // 识别图片验证码中的数字
	    HashMap<String, String> options2 = new HashMap<String, String>();
	    options2.put("detect_direction", "true");
	    options2.put("detect_language", "true");
	    JSONObject res = client.webImage(imgPath, options2);
	    System.out.println("识别返回的字符串为："+res.toString(2));
	    System.out.println("识别出来的验证码为："+res.getJSONArray("words_result").getJSONObject(0).get("words"));
	}

}
