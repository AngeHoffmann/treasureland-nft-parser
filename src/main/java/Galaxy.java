import java.io.IOException;

public class Galaxy {
    public void getAllStatForAlpacaNft() throws IOException {
        // GRAPHQL
        HttpSender sender = new HttpSender();
        String url = "https://api.binance.com/api/v3/ticker/price?symbol=BNBUSDT";
        JsonParser parser = new JsonParser();

        String response = sender.run(url);
        System.out.println(response);
    }
}
