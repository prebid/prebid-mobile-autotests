package global;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Global_for_JSON {

    public static String generateJsonFromString(String name, String[] value, String sortingOrder) throws IOException {
        //Function to generate Json similar like: {"dataSourcePriority":["druid.pie-hd"]} useful for "override" or {"platformId":["revenue"]} useful for "sort"
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        for (int i = 0; i < value.length; i++) {
            if (sortingOrder != null && sortingOrder.equals("descending") == true) {
                list.add("-" + value[i]);
            } else if (sortingOrder != null && sortingOrder.equals("ascending") == true) {
                list.add(value[i]);
            } else {
                list.add(value[i]);
            }
        }

        obj.put(name, list);

        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        String jsonText = out.toString();
        return jsonText;
    }

    public static List<String> findJsonElementBySearchOnValue(String json_input, String jsonPath,
                                                              String search_on, String search_on_value, String out_value_of) {

        List<String> output = JsonPath.read(json_input, jsonPath + "[?(@." + search_on + "== '" + search_on_value + "')]." + out_value_of + "");


        return output;

    }

    public static List<String> findJsonElementBySearchOn(String json_input, String jsonPath,
                                                         String search_on, String out_value_of) {
        List<String> output_final = new ArrayList<String>();
        List<String> output = JsonPath.read(json_input, jsonPath + "[?(@." + search_on + ")]." + out_value_of);
        for (int i = 0; i < output.size(); i++) {

            String temp = output.get(i);
            output_final.add(temp);
        }

        return output_final;

    }

    /**
     * This Takes a key pair json input and converts it into an array list
     *
     * @param entry Json object of key pairs
     * @return ArrayList of Key pair values
     */
    public static String[] getJsonPairToArrayList(JsonElement entry) {
        String csv_split = ":";
        String[] pair_entry = entry.toString().split(csv_split);
        ArrayList<String> out_put = new ArrayList<String>();
        for (String entry_val : pair_entry) {
            out_put.add(entry_val.replaceAll("[{}]", ""));
        }
        String[] ret_output = new String[out_put.size()];
        ret_output = out_put.toArray(ret_output);
        return ret_output;
    }

    public static List<String> cropJson(String json_input, String jsonPath) {

        List<String> output = JsonPath.read(json_input, jsonPath);

        return output;

    }

    public static JSONArray cropJson2Array(String json_input, String jsonPath) {

        JSONArray output = JsonPath.read(json_input, jsonPath);

        return output;

    }

    public static String appendTrailingZeros(int no_of_zeros) {

        StringBuilder s = new StringBuilder();
        s.append("0.");
        for (int k = 0; k < no_of_zeros; k++) {
            s.append('0');
        }
        return s.toString();
    }

    public static String getJsonValue(String json_input, String jsonElementPath) {

        String output = "";

        if (json_input == null || json_input.length() == 0) {
            return output;
        }

        try {
            output = convertJSONObjectToString(JsonPath.read(json_input, jsonElementPath));
        } catch (ClassCastException cce) {
            //return if it is a single object
            cce.printStackTrace();
            return "";
        } catch (com.jayway.jsonpath.PathNotFoundException pnfe) {
            //path was not found
            pnfe.printStackTrace();
            return "";
        }

        return output;

    }

    /**
     * This verifies that an element exists in the Array of Mondemand Objects.
     * This requires the correct JSONPath and element value
     * It returns true if element does exist and false if entry does not exist.
     *
     * @param json_entry    This is a JsonArray of Mondemand Entries(JsonObject).
     * @param key_path      This is JSONPath of the key
     * @param element_value This is the element value to be verified
     * @return Boolean - true / false
     */
    public static boolean elementExistsJsonArray(JsonArray json_entry, String key_path, String element_value) {
        boolean result = false;
        int exist_count = 0;
        for (JsonElement entry : json_entry) {
            JsonObject trace_entry = (JsonObject) entry;
            String element_val;
            //This cleans up the entry
            key_path = key_path.replace("\"", "");
            element_val = Global_for_JSON.getJsonValue(trace_entry.toString(), key_path);
            if (element_val.contains(element_value.replace("\"", ""))) {
                exist_count = exist_count + 1;
            }
        }
        if (exist_count >= 1) {
            result = true;
        }
        return result;
    }

    public static String getDepth(String jsonPath) {
        String depth = jsonPath.split("\\.").length - 2 + "";
        return depth;
    }

    public static String[] getJsonArray(String json_input, String jsonElementPath) {

        JSONArray jOutput;
        try {
            jOutput = JsonPath.read(json_input, jsonElementPath);
        } catch (ClassCastException cce) {
            //return if it is a single object
            return new String[]{convertJSONObjectToString(JsonPath.read(json_input, jsonElementPath))};
        } catch (com.jayway.jsonpath.PathNotFoundException pnfe) {
            //path was not found
            pnfe.printStackTrace();
            return null;
        }

        if (jOutput == null)
            return new String[]{};
        String[] output = new String[jOutput.size()];

        for (int i = 0; i < output.length; ++i) {
            output[i] = convertJSONObjectToString(jOutput.get(i));
        }
        return output;

    }

    public static String[] getJsonArray(Object jsonInput, String jsonElementPath) {

        JSONArray jOutput;
        try {
            jOutput = JsonPath.read(jsonInput, jsonElementPath);
        } catch (ClassCastException cce) {
            //return if it is a single object
            return new String[]{convertJSONObjectToString(JsonPath.read(jsonInput, jsonElementPath))};
        } catch (com.jayway.jsonpath.PathNotFoundException pnfe) {
            //path was not found
            pnfe.printStackTrace();
            return null;
        }

        if (jOutput == null)
            return new String[]{};
        String[] output = new String[jOutput.size()];

        for (int i = 0; i < output.length; ++i) {
            output[i] = convertJSONObjectToString(jOutput.get(i));
        }
        return output;

    }

    public static boolean isJSONValid(String strJSON) {
        try {
            JsonPath.parse(strJSON);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static String convertJSONObjectToString(Object fromJSON) {
        String output = "";

        if (fromJSON != null) {
            if (fromJSON instanceof Long) {
                output += ((Number) fromJSON).longValue();
            } else if (fromJSON instanceof Double) {
                //remove scientific notation
                output += new BigDecimal(fromJSON.toString()).stripTrailingZeros().toPlainString();
            } else if (fromJSON instanceof Integer) {
                output += ((Number) fromJSON).intValue();
            } else if (fromJSON instanceof Float) {
                output += ((Number) fromJSON).floatValue();
            } else {
                output += fromJSON.toString();
            }
        }
        return output;
    }

    public static void main(String[] args) throws IOException {

        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader("sample.json"));
        try {

            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }

            String json_input = sb.toString();

            System.out.println("sample input :   " + json_input);

            /* Input for Sample 1: To query the json string to get the order id where account id :537238557123 */
            String jsonPath1 = "$[*].";
            String search_on1 = "account_id";
            String search_on_value1 = "537238557123";
            String value_of1 = "order_id";

            /* Call for Sample 1 */
            List<String> order = Global_for_JSON.findJsonElementBySearchOnValue(json_input, jsonPath1, search_on1, search_on_value1, value_of1);

            System.out.println(order);


            /* Input for Sample 2: To query the json string to get value for operator under adunit_max_duration under targeting where operator: <= */
            String jsonPath2 = "$[*].targeting.content.adunit_max_duration";
            String search_on2 = "op";
            String search_on_value2 = "<=";
            String value_of2 = "val";

            /* Calljk,78 for Sample 2 */
            List<String> op_value = Global_for_JSON.findJsonElementBySearchOnValue(json_input, jsonPath2, search_on2, search_on_value2, value_of2);

            System.out.println(op_value);

            /* Input for Sample 3: To query the json string to get value for operator under adunit_max_duration under targeting where operator: <= */
            String jsonSrc = "{\"uid\": \"6004ce9d-accf-fff1-8123-944942\", \"acl_override\":true, \"id\":\"12346\"}";
            String jsonPath3a = "$.uid";
            String jsonPath3b = "$.id";
            /* Call for Sample 3 */
            System.out.println("uid Value: " + Global_for_JSON.getJsonValue(jsonSrc, jsonPath3a));
            System.out.println("id Value: " + Global_for_JSON.getJsonValue(jsonSrc, jsonPath3b));

            /* Input for Sample 3: To query the json string to get value for operator under adunit_max_duration under targeting where operator: <= */
            String jsonSrc2 = "{\"content\":{\"189278\":{\"a\":[\"77591778\",\"77591778\",\"0\",\"0\"],\"g\":\"1411657200\",\"d\":[],\"t\":\"1411657200\"},\"188836\":{\"a\":[\"75283551\",\"75283551\",\"1\",\"0\"],\"g\":\"1411657200\",\"d\":[],\"t\":\"1411657200\"}},\"header\":{\"omit_nt\":null,\"platform\":\"67e5208910ee6e4a14d866d360a7f0df1c4b2dfb\",\"simulate\":null,\"since\":\"0\",\"v\":\"3\",\"status\":\"OK\",\"fmt\":\"json\",\"format\":\"json\",\"omit_last_nt\":null}}";
            String jsonPath4a = "$.content";
            String jsonPath4b = "$.header";
            /* Call for Sample 3 */
            System.out.println("content Value: " + Global_for_JSON.getJsonValue(jsonSrc2, jsonPath4a));
            System.out.println("header Value: " + Global_for_JSON.getJsonValue(jsonSrc2, jsonPath4b));

            String jsonSrc3 = "[{\"timestamp\":\"2016-05-15T00:00:00.000Z\",\"result\":{\"impressions\":2713.0,\"spend\":5.426000025123358}}]";
            String jsonPath5a = "$[0].result";

            System.out.println("json source : " + jsonSrc3);
            System.out.println("Result Value: " + Global_for_JSON.getJsonArray(jsonSrc3, jsonPath5a)[0]);

            System.out.println(Global_for_JSON.isJSONValid("{\"sfklsjkfljsl\"=}"));
            System.out.println(Global_for_JSON.isJSONValid(jsonSrc3));

        } finally {
            br.close();

        }


    }

//	public static <T> T getObjectFromJson(String url, Class<T> cls) throws IOException{
//		String json = Global_for_API.readContentFromUrl(url);
//		return new Gson().fromJson(json , cls);
//	}

}
