package com.zrscsoft.api.baidu.address;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class AddressDistinguish {
	 public static void  main(String[] args) {			 
//	        String[] strs={"dailingyang001�����׷���13800000000������ʡ���޳������ųǽֵ�������1��¥1000����"
//	        ,"tb508969_2013����΢��18900000000������ʡ��ɳ�У���´������ɳ��ֵ� ����԰С�����Է2��2��Ԫ1000��"
//	        ,"��֮�ѣ������꣬13600000000���㽭ʡ�����У���Ϫ�У��ֶ��� �������������ͳǴ��Է5��2000"
//	        ,"maruiлsir��л�庣��13900000000������ʡ�Ͼ��У��ػ���������·1��ˮ�γ�5¥1��"
//	        ,"�к�����19��Ԫ����15000000000������ʡ�����У�����������·�ֵ� ����ʡ ������ ���� ����·�ж�"
//	        ,"��ΰ��13120000000������ ������ ������ �����ֵ� ������԰����208�ţ�000000"
//	        ,"������13000000000������ʡ֣���и��������Ҵ�ѧ�Ƽ�԰����1��¥"};
	        String[] strs={"������������ɽ�в�����"
	        ,"��������������ʡ֣���и��������Ҵ�ѧ�Ƽ�԰����1��¥"};
	        String splitStr="��";
	        for(int i=0;i<strs.length;i++){
	            String str=strs[i];
	            try {
	                Map<String, String> info=distinguishInfo(str,splitStr);
	                System.out.println(str + "//" +info.get("name")+"//"+info.get("mobile")+"//" +info);
	            }catch (Exception e){
	            	System.out.println(str);
	                e.printStackTrace();
	            }
	        }
	    }
	    public static Map<String, String> distinguishInfo(String userinfo,String splitStr) {
	        Map<String, String> rstMap = new HashMap<String, String>();
	        try {
	            Map<String, String> userInfo = getUserInfo(userinfo,splitStr);
	            String buyer_address = userInfo.get("address");
	            if(buyer_address.indexOf("�ֵ�") > 0){
	                String subString = buyer_address.substring(0,buyer_address.indexOf("�ֵ�")+2);
	                rstMap = BaiduMapUtil.getAddress(subString);
	            }else if(buyer_address.indexOf("·") > 0){
	                String subString = buyer_address.substring(0,buyer_address.indexOf("·")+1);
	                rstMap = BaiduMapUtil.getAddress(subString);
	            }else{
	                rstMap = BaiduMapUtil.getAddress(buyer_address);
	            }

	            if ("".equals(rstMap.get("province")) || "".equals(rstMap.get("city")) || "".equals(rstMap.get("area"))) {
	                rstMap.put("result", "false");
	                return rstMap;
	            }
	            String province = rstMap.get("province");
	            String city = rstMap.get("city");
	            String area = rstMap.get("area");
	            String street = rstMap.get("street");
	            rstMap.put("street", street + "�ֵ�");
	            buyer_address = buyer_address.replaceFirst(province, "").replaceFirst(city, "").replaceFirst(area, "").replaceFirst(street, " ").trim();
	           
	            rstMap.put("detailAddress", userInfo.get("address"));
	            rstMap.put("mobile", userInfo.get("mobile"));
	            rstMap.put("name", userInfo.get("name"));
	            rstMap.put("result", "succ");
	        } catch (MalformedURLException e) {
	            rstMap.put("result", "fail");
	            e.printStackTrace();
	        }
	        return rstMap;
	    }

	    //�ų�ʡ����������ݲ�ѯ������Ϣ
	    public static  Map getUserInfo(String info,String splitStr) {
	        Map<String, String> resultMap = new HashMap<String, String>();

	        try {
	            //ƥ�人��
	            resultMap.put("name", getName(info,splitStr));
	            //ƥ������
	            resultMap.put("mobile", getMobile(info));

	            if(resultMap.get("name") != null){
	                info = info.replace(resultMap.get("name")+splitStr,"");
	            }
	            if(resultMap.get("mobile") != null){
	                info = info.replace(resultMap.get("mobile")+splitStr,"");
	            }
	            info = info.replace(" "+splitStr,"");
	            info = info.replace(" ","");
	            resultMap.put("address", info);
	        } catch (Exception e) {
	           e.printStackTrace();
	        }
	        return resultMap;
	    }
	    private static String getName(String addressInfo,String splitStr) {
	        //*ʶ������*/
	    	String[] firstNames = {"��","Ǯ","��","��","��","��","֣","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","ʩ","��","��","��","��","��","��","κ","��","��","��","л","��","��","��","ˮ","�","��","��","��","��","��","��","��","��","��","³","Τ","��","��","��","��","��","��","��","��","Ԭ","��","ۺ","��","ʷ","��","��","��","�","Ѧ","��","��","��","��","��","��","��","��","��","��","��","��","��","��","ʱ","��","Ƥ","��","��","��","��","��","Ԫ","��","��","��","ƽ","��","��","��","��","��","Ҧ","��","տ","��","��","ë","��","��","��","��","��","�","��","��","��","��","̸","��","é","��","��","��","��","��","��","ף","��","��","��","��","��","��","ϯ","��","��","ǿ","��","·","¦","Σ","��","ͯ","��","��","÷","ʢ","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","֧","��","��","��","¬","Ī","��","��","��","��","��","��","Ӧ","��","��","��","��","��","��","��","��","��","��","��","��","ʯ","��","��","ť","��","��","��","��","��","��","½","��","��","��","��","�","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","ɽ","��","��","��","�","��","ȫ","ۭ","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","ղ","��","��","Ҷ","��","˾","��","۬","��","��","��","ӡ","��","��","��","��","ۢ","��","��","��","��","��","��","׿","��","��","��","��","��","��","��","��","��","��","˫","��","ݷ","��","��","̷","��","��","��","��","��","��","��","Ƚ","��","۪","Ӻ","��","�","ɣ","��","�","ţ","��","ͨ","��","��","��","��","ۣ","��","��","ũ","��","��","ׯ","��","��","��","��","��","Ľ","��","��","ϰ","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","»","��","��","Ź","�","��","��","ε","Խ","��","¡","ʦ","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","ɳ","ؿ","��","��","��","��","��","��","��","��","��","��","��","��","��","��","Ȩ","��","��","��","��","��","��ٹ","˾��","�Ϲ�","ŷ��","�ĺ�","���","����","����","����","�ʸ�","ξ��","����","�̨","��ұ","����","���","����","����","̫��","����","����","����","��ԯ","���","����","����","����","Ľ��","����","����","˾ͽ","˾��","����","˾��","��","��","�ӳ�","���","��ľ","����","����","���","����","����","����","�ذ�","�й�","�׸�","����","��","��","��","��","��","۳","Ϳ","��","�θ�","����","����","����","����","��","��","����","΢��","��","˧","��","��","��","��","��","��","����","����","����","����","��","Ĳ","��","٦","��","��","�Ϲ�","ī","��","��","��","��","��","��","١"};
	        String[] names=addressInfo.split(splitStr);
	        String name="";
	        for(int i=0;i<names.length;i++){
	        	String nametmp=names[i];
	        	//System.out.println(names.length+"//"+nametmp);
	        	if(nametmp==null||nametmp.length()<=1||nametmp.length()>4){
	        		continue;
	        	}
	        	for(int j=0;j<firstNames.length;j++){
	        		if(nametmp.startsWith(firstNames[j])){
	        			name=nametmp;
	        			break;
	        		}
	        	}
	        	if(!"".equals(name)){
	        		break;
	        	}
	        }
	        return name;
	    }
	    private static String getMobile(String addressInfo) {
	        //*ʶ��绰����*/
	        Pattern pattern = Pattern.compile("([1][3-9][\\d]{9})|(0\\d{2,4}-\\d{7,8})");
	        Matcher matcher = pattern.matcher(addressInfo);
	        StringBuffer bf = new StringBuffer(64);
	        while (matcher.find()) {
	            bf.append(matcher.group());
	        }
	        return bf.toString();
	    }
}
