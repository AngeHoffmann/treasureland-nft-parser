import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Utils utils = new Utils();
        HttpSender sender = new HttpSender();
        String endpoint = "https://api.treasureland.market/v2/v1/nft/items";
        int pageSize = 100;
        int pageCount = 3;
        String alpacaContract = "0xe85d7b8f4c0c13806e158a1c9d7dcb33140cdc46";
        HashMap<String, List<String>> finalMap = null;
        JsonParser parser = new JsonParser();

        for (int i = 1; i <= pageCount; i++) {
            String url = String.format("%s?chain_id=56&page_no=%d&page_size=%d&contract=%s", endpoint, i, pageSize, alpacaContract);
            String response = sender.run(url);

            // Собираем мапу из полного ответа
            HashMap<String, List<String>> fullResponse = parser.parseJsonToMap(response);
            // Фильтруем мапу, оставляя только необходимые поля
            HashMap<String, List<String>> filteredResponse = utils.filteringResponse(fullResponse);

            if (i == 1) {
                System.out.println("ALL NFT = " + filteredResponse.get("dataCount").get(0));
            }
            System.out.println("Parsing " + i + " page...");

            // Преобразуем Мапу в удобный для просмотра формат и выводим его на экран
            HashMap<String, List<String>> result = utils.showNftInfo(filteredResponse);

            // Находим минимальную стоимость
            for (List<String> lists : result.values()) {
                String minValue = lists.stream().min(String::compareTo).get();
                lists.clear();
                lists.add(minValue);
            }

//            System.out.println("\n=-=-=-= ONLY MINIMUM PRICE =-=-=-=\n");
//            utils.printMap(result);

            // Соединяем мапы со всех страниц в единую
            if (finalMap == null) {
                finalMap = new HashMap<>(result);
            } else {
                HashMap<String, List<String>> finalMap1 = finalMap;
                result.forEach((k, v) -> finalMap1.merge(k, v, (v1, v2) -> {
                    List<String> temp = new ArrayList<>();
                    temp.add(v1.get(0));
                    temp.add(v2.get(0));
                    String minValue = temp.stream().min(String::compareTo).get();
                    temp.clear();
                    temp.add(minValue);
                    return temp;
                }));
            }
        }

        System.out.println("\nRESULT: ");
        System.out.println("Parsed NFT = " + pageCount * pageSize);
        System.out.println("Unique NFT = " + finalMap.size());
        Map<String, List<String>> treeMap = new TreeMap<>(finalMap);
        utils.printMap(treeMap);
    }
}