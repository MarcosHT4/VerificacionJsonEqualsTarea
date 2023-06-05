package ejercicioJson;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonEquals {
    public boolean equalJSON(String expectedJSON, String actualJSON) {
        try {
            JSONObject json1 = new JSONObject(expectedJSON);
            JSONObject json2 = new JSONObject(actualJSON);
            return compareJSONObjects(json1, json2);
        } catch (JSONException e) {
            System.out.println("El formato de uno de los JSONS es incorrecto");
        }
        return false;
    }

    private boolean compareJSONObjects(JSONObject json1, JSONObject json2) throws JSONException {

        if (json1.length() != json2.length()) {
            return false;
        }


        for (String key : json1.keySet()) {

            if (!json2.has(key)) {
                System.out.println("El actualJSON, no posee la llave: " + key);
                return false;
            }


            Object value1 = json1.get(key);
            Object value2 = json2.get(key);


            if (value1.equals("IGNORE")) {
                continue;
            }

            if (value1 instanceof JSONObject && value2 instanceof JSONObject) {
                if (!compareJSONObjects((JSONObject) value1, (JSONObject) value2)) {
                    return false;
                }
            } else {
                if (!value1.equals(value2)) {
                    System.out.println("Para la llave: " + key + " | " + value1 + " no es igual a " + value2 + " | ");
                    return false;
                }
            }
        }
        return true;
    }


}
