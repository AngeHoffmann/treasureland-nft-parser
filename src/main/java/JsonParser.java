import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {

    public HashMap<String, List<String>> parseJsonToMap(String input) throws ParseException {
        JSONObject jsonObject = new JSONObject(input);
        HashMap<String, List<String>> map = new HashMap();
        parseJson(jsonObject, null, map);

        map.forEach((key, value) -> {
            List<String> listA = value;
            for (int i = 0; i < listA.size(); i++) {
                String temp = listA.get(i);
                listA.set(i, temp);
            }
        });

//        utils.printMap(map);
        return map;
    }

    private void parseJson(JSONObject jsonObject, String name, HashMap<String, List<String>> map) throws ParseException {
        while (jsonObject.keySet().iterator().hasNext()) {
            Object obj = jsonObject.keySet().iterator().next();

            if (jsonObject.get(obj.toString()) instanceof JSONObject) {
                if (jsonObject.keySet().size() > 1) {
                    parseJsonObject(jsonObject, name, map);
                    break;
                } else {
                    jsonObject = jsonObject.getJSONObject(obj.toString());
                }
            } else {
                parseJsonObject(jsonObject, name, map);
                break;
            }
        }
    }

    private void parseJsonObject(JSONObject jsonObject, String name0, HashMap<String, List<String>> map) throws ParseException {
        for (int i = 0; i < jsonObject.names().length(); i++) {
            String name = jsonObject.names().get(i).toString();
            String listName;
            if (name0 == null || name0.equals("data")) {
                listName = name;
            } else {
                listName = name0 + "_" + name;
            }
            if (jsonObject.get(name) instanceof JSONObject) {
                parseJson((JSONObject) jsonObject.get(name), listName, map);
            } else if (jsonObject.get(name) instanceof JSONArray) {
                parseJsonArray((JSONArray) jsonObject.get(name), listName, map);
            } else {
                String value = jsonObject.get(name).toString();
                if (map.containsKey(listName)) {
                    ArrayList<String> list = (ArrayList) map.get(listName);
                    list.add(value);
                    map.put(listName, list);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(value);
                    map.put(listName, list);
                }

            }
        }
    }

    private void parseJsonArray(JSONArray jsonArr, String listName, HashMap<String, List<String>> map) throws ParseException {
        for (int k = 0; k < jsonArr.length(); k++) {
            if (jsonArr.get(k) instanceof JSONObject) {
                parseJson((JSONObject) jsonArr.get(k), listName, map);
            } else {
                String value = jsonArr.get(k).toString();
                if (map.containsKey(listName)) {
                    ArrayList<String> list = (ArrayList) map.get(listName);
                    list.add(value);
                    map.put(listName, list);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(value);
                    map.put(listName, list);
                }
            }
        }
    }

}
