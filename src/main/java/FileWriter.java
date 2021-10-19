import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileWriter {
    JSONObject output;
    JSONObject actualOutput;

    public void createCsv(JSONObject response) {
        try {
//            output = new JSONObject(response);
//            actualOutput = (JSONObject) output.get("data");
//            System.out.println(actualOutput);

            JSONArray docs = actualOutput.getJSONArray("alpaca_nft");
            File file = new File("./src/resources/fromJSON.csv");
            String csv = CDL.toString(docs);
            FileUtils.writeStringToFile(file, csv, Charset.defaultCharset());
        } catch (
                JSONException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
