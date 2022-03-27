package com.zrscsoft.api.baidu.address;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class AddressDistinguish {
	 public static void  main(String[] args) {			 
//	        String[] strs={"dailingyang001，杨兆芳，13800000000，江苏省，宿城区，古城街道便民方舟1号楼1000房间"
//	        ,"tb508969_2013，蒋微，18900000000，湖南省长沙市，岳麓区，观沙岭街道 德润园小区润恒苑2栋2单元1000号"
//	        ,"橱之友，包卫贞，13600000000，浙江省宁波市，慈溪市，庵东镇 杭州湾新区世纪城翠湖苑5栋2000"
//	        ,"marui谢sir，谢义海，13900000000，江苏省南京市，秦淮区，健康路1号水游城5楼1号"
//	        ,"男孩不哭19，元亮，15000000000，河南省安阳市，殷都区，铁西路街道 河南省 安阳市 殷都区 铁西路中段"
//	        ,"刘伟，13120000000，北京 北京市 朝阳区 东湖街道 利泽中园二区208号，000000"
//	        ,"张三，13000000000，河南省郑州市高新区国家大学科技园东区1号楼"};
	        String[] strs={"，，，，，中山市不可能"
	        ,"，，，，，河南省郑州市高新区国家大学科技园东区1号楼"};
	        String splitStr="，";
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
	            if(buyer_address.indexOf("街道") > 0){
	                String subString = buyer_address.substring(0,buyer_address.indexOf("街道")+2);
	                rstMap = BaiduMapUtil.getAddress(subString);
	            }else if(buyer_address.indexOf("路") > 0){
	                String subString = buyer_address.substring(0,buyer_address.indexOf("路")+1);
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
	            rstMap.put("street", street + "街道");
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

	    //排除省市区后的数据查询其他信息
	    public static  Map getUserInfo(String info,String splitStr) {
	        Map<String, String> resultMap = new HashMap<String, String>();

	        try {
	            //匹配汉字
	            resultMap.put("name", getName(info,splitStr));
	            //匹配数字
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
	        //*识别姓名*/
	    	String[] firstNames = {"赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨","朱","秦","尤","许","何","吕","施","张","孔","曹","严","华","金","魏","陶","姜","戚","谢","邹","喻","柏","水","窦","章","云","苏","潘","葛","奚","范","彭","郎","鲁","韦","昌","马","苗","凤","花","方","俞","任","袁","柳","酆","鲍","史","唐","费","廉","岑","薛","雷","贺","倪","汤","滕","殷","罗","毕","郝","邬","安","常","乐","于","时","傅","皮","卞","齐","康","伍","余","元","卜","顾","孟","平","黄","和","穆","萧","尹","姚","邵","湛","汪","祁","毛","禹","狄","米","贝","明","臧","计","伏","成","戴","谈","宋","茅","庞","熊","纪","舒","屈","项","祝","董","粱","杜","阮","蓝","闵","席","季","麻","强","贾","路","娄","危","江","童","颜","郭","梅","盛","林","刁","钟","徐","邱","骆","高","夏","蔡","田","樊","胡","凌","霍","虞","万","支","柯","昝","管","卢","莫","经","房","裘","缪","干","解","应","宗","丁","宣","贲","邓","郁","单","杭","洪","包","诸","左","石","崔","吉","钮","龚","程","嵇","邢","滑","裴","陆","荣","翁","荀","羊","於","惠","甄","麴","家","封","芮","羿","储","靳","汲","邴","糜","松","井","段","富","巫","乌","焦","巴","弓","牧","隗","山","谷","车","侯","宓","蓬","全","郗","班","仰","秋","仲","伊","宫","宁","仇","栾","暴","甘","钭","厉","戎","祖","武","符","刘","景","詹","束","龙","叶","幸","司","韶","郜","黎","蓟","薄","印","宿","白","怀","蒲","邰","从","鄂","索","咸","籍","赖","卓","蔺","屠","蒙","池","乔","阴","欎","胥","能","苍","双","闻","莘","党","翟","谭","贡","劳","逄","姬","申","扶","堵","冉","宰","郦","雍","舄","璩","桑","桂","濮","牛","寿","通","边","扈","燕","冀","郏","浦","尚","农","温","别","庄","晏","柴","瞿","阎","充","慕","连","茹","习","宦","艾","鱼","容","向","古","易","慎","戈","廖","庾","终","暨","居","衡","步","都","耿","满","弘","匡","国","文","寇","广","禄","阙","东","殴","殳","沃","利","蔚","越","夔","隆","师","巩","厍","聂","晁","勾","敖","融","冷","訾","辛","阚","那","简","饶","空","曾","毋","沙","乜","养","鞠","须","丰","巢","关","蒯","相","查","後","荆","红","游","竺","权","逯","盖","益","桓","公","万俟","司马","上官","欧阳","夏侯","诸葛","闻人","东方","赫连","皇甫","尉迟","公羊","澹台","公冶","宗政","濮阳","淳于","单于","太叔","申屠","公孙","仲孙","轩辕","令狐","钟离","宇文","长孙","慕容","鲜于","闾丘","司徒","司空","亓官","司寇","仉","督","子车","颛孙","端木","巫马","公西","漆雕","乐正","壤驷","公良","拓跋","夹谷","宰父","谷梁","晋","楚","闫","法","汝","鄢","涂","钦","段干","百里","东郭","南门","呼延","归","海","羊舌","微生","岳","帅","缑","亢","况","后","有","琴","梁丘","左丘","东门","西门","商","牟","佘","佴","伯","赏","南宫","墨","哈","谯","笪","年","爱","阳","佟"};
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
	        //*识别电话号码*/
	        Pattern pattern = Pattern.compile("([1][3-9][\\d]{9})|(0\\d{2,4}-\\d{7,8})");
	        Matcher matcher = pattern.matcher(addressInfo);
	        StringBuffer bf = new StringBuffer(64);
	        while (matcher.find()) {
	            bf.append(matcher.group());
	        }
	        return bf.toString();
	    }
}
