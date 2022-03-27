package com.zrscsoft.api.baidu;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;
/**
 * ʶ�����֤������
 * 
 * @author admin
 *
 */
public class IDCardRecognitionDemo {
	//����APPID/AK/SK
    public static final String APP_ID = "15603863";
    public static final String API_KEY = "8KWnL5Go5OqRyubfNUDbrUyi";
    public static final String SECRET_KEY = "Fb8BSsH0mSSoM67twvOKVu2P2UNxrkH5";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // ��ʼ��һ��AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // ��ѡ�������������Ӳ���
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // �����ѡ�������ýӿ�
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("detect_direction", "true");
	    options.put("detect_risk", "false");
	    // ʶ�����֤���棨����ͼƬΪ����ͼƬ����D:\1.png��
	    JSONObject frontres = client.idcard("D:\\1.png", "front", options);
	    System.out.println(frontres.toString(2));
	    // ʶ�����֤���棨����ͼƬΪ����ͼƬ����D:\2.png��
	    JSONObject backres = client.idcard("D:\\2.png", "back", options);
	    System.out.println(backres.toString(2));
	    
	}
	
}
