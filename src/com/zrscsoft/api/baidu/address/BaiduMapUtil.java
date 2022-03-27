package com.zrscsoft.api.baidu.address;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
public class BaiduMapUtil {

	public static String ak = "cnOqGWBsMTo7em70PP5sj2kpS2HZUYa1";
	/**
	 * 根据市和详细地址获取地址经纬度
	 * @param address
	 * @param city
	 * @return uYr0vs7KOcs8I5y6e4Y479CyW6xOC3GV
	 */
	public static String getGeocoderLatitude(String address, String city) {
		BufferedReader in = null;
		String lng = "";
		String lat = "";
		try {
			address = URLEncoder.encode(address, "UTF-8");
			URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?address="
					+ address
					+ "&output=json&ak=" + ak + "&city="
					+ city);
			in = new BufferedReader(new InputStreamReader(tirc.openStream(),
					"UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			String str = sb.toString();
			if (StringUtils.isNotEmpty(str)) {
				int lngStart = str.indexOf("lng\":");
				int lngEnd = str.indexOf(",\"lat");
				int latEnd = str.indexOf("},\"precise");
				if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
					lng = str.substring(lngStart + 5, lngEnd);
					lat = str.substring(lngEnd + 7, latEnd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lat + "," + lng;

	}

	/**
	 * 根据经纬度查询地址信息
	 * @param
	 * @throws MalformedURLException
	 */
	public static JsonNode getposition(String LatitudeAndLongitude)
			throws MalformedURLException {
		BufferedReader in = null;
		JsonNode locationNode = null;
		/*URL tirc = new URL("http://api.map.baidu.com/geocoder?location="
				+ LatitudeAndLongitude + "&output=json&key="
				+ "E4805d16520de693a3fe707cdc962045");*/
		URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?location="
				+ LatitudeAndLongitude + "&output=json&ak="
				+ ak);
		try {
			in = new BufferedReader(new InputStreamReader(tirc.openStream(),
					"UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			String str = sb.toString();
			ObjectMapper mapper = new ObjectMapper();
			if (StringUtils.isNotEmpty(str)) {
				JsonNode jsonNode = mapper.readTree(str);
				jsonNode.findValue("status").toString();
				JsonNode resultNode = jsonNode.findValue("result");
				/*JsonNode locationNode = resultNode
						.findValue("formatted_address");*/
				locationNode = resultNode
						.findValue("addressComponent");
				/*JsonNode area = locationNode.findValue("district");
				System.out.println(area);*/
			}

		} catch (Exception e) {

		}
		return locationNode;

	}
	
	

	public static void main(String[] args) throws MalformedURLException {
		String LatitudeAndLongitude = getGeocoderLatitude("公园时代小区1号楼二单元3301", "");
		System.out.println(LatitudeAndLongitude);
		JsonNode node = getposition(LatitudeAndLongitude);
		String re = node.findValue("district").toString().replace("\"","");
		System.out.println(re);
	}
	
	/**
	 * 根据市和详细地址信息获取区域信息
	 * @param address
	 * @param city
	 * @return
	 * @throws MalformedURLException
	 */
	public static String getArea(String address,String city) throws MalformedURLException{
		//根据市和详细地址获取地址经纬度
		String LatitudeAndLongitude = getGeocoderLatitude(address, city);
		JsonNode jsonNode = getposition(LatitudeAndLongitude);
		String area = jsonNode.findValue("district").toString().replace("\"","");
		return area;
		
	}
	/**
	 * 根据市和详细地址信息获省市区信息
	 * @param address
	 * @return
	 * @throws MalformedURLException
	 */
	public static Map<String,String> getAddress(String address) throws MalformedURLException{
		//根据市和详细地址获取地址经纬度
		String LatitudeAndLongitude = getGeocoderLatitude(address, "");
		JsonNode jsonNode = getposition(LatitudeAndLongitude);
		String province = jsonNode.findValue("province").toString().replace("\"","");
		String city = jsonNode.findValue("city").toString().replace("\"","");
		String area = jsonNode.findValue("district").toString().replace("\"","");
		String street=jsonNode.findValue("street").toString().replace("\"","");
		Map<String,String> addMap = new HashMap<String,String>();
		addMap.put("province", province);
		addMap.put("city", city);
		addMap.put("area", area);
		addMap.put("street", street);
		return addMap;
		
	}
}
