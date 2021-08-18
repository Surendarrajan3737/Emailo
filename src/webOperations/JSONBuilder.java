package webOperations;

public class JSONBuilder {
	
	public static String CreateJSON(String key,String value) {
		String json="{\""+key+"\":\""+value+"\"}";
		return json;
	}
	
	public static String addJSONPair(String json,String key,String value) {
		json= json.substring(0, json.length()-1);
		String additionPair=",\""+key+"\":\""+value+"\"}";
		return json+additionPair;
	}
	
	public static String addJSONPairobject(String json,String key,String value) {
		json= json.substring(0, json.length()-1);
		String additionPair=",\""+key+"\":"+value+"}";
		return json+additionPair;
	}
	
	public static String getJSONArray(String jsonObject) {
		String jsonArray="["+jsonObject+"]";
		return jsonArray;
	}
	public static String addJSONObject(String jsonArray,String jsonObject) {
		jsonArray=jsonArray.substring(0, jsonArray.length()-1);
		jsonArray+=","+jsonObject+"]";
		return jsonArray;
	}
}
