import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class Binance {
    HttpSender sender = new HttpSender();
    String url = "https://api.binance.com/api/v3/ticker/price?symbol=BNBUSDT";
    JsonParser parser = new JsonParser();
    private static String bnbPrice;

    public Binance() throws IOException, ParseException {
        String response = sender.run(url);
        HashMap<String, List<String>> responseMap = parser.parseJsonToMap(response);
        bnbPrice = responseMap.get("price").get(0);
        System.out.printf("BNB = %.2f $\n", Double.valueOf(responseMap.get("price").get(0)));
    }

    public String getBnbPrice() {
        return bnbPrice;
    }
}
