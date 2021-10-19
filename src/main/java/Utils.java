import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    /**
     * Printing Map values
     *
     * @param map
     */
    public void printMap(Map<String, List<String>> map) {
        map.forEach((key, value) -> System.out.println("\"" + key + "\" -> " + value));
    }

    /**
     * Convert 18+digits values to pretty form
     *
     * @param list
     * @return
     */
    public List<String> convertPrices(List<String> list) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String value;
            int valueLength = list.get(i).length();
            if (valueLength > 18) {
                String first = list.get(i).substring(0, valueLength - 18);
                String second = list.get(i).substring(valueLength - 18, valueLength - 18 + 4);
                value = first + "." + second;
            } else if (valueLength == 18) {
                String second = list.get(i).substring(0, 4);
                value = "0." + second;
            } else {
                String first;
                int lastIndex = 4 - (18 - valueLength);
                switch (valueLength) {
                    case (17):
                        first = "0.0";
                        break;
                    case (16):
                        first = "0.00";
                        break;
                    case (15):
                        first = "0.000";
                        break;
                    default:
                        System.out.println("Количество символов в 'price' менее 15");
                        throw new IllegalStateException("Unexpected value: " + valueLength);
                }
                String second = list.get(i).substring(0, lastIndex);
                value = first + second;
            }

//            System.out.println("i=" + i + ", value=" + value);
            result.add(value);
        }
        return result;
    }

    public HashMap<String, List<String>> filteringResponse(HashMap<String, List<String>> map) {
        HashMap<String, List<String>> filteredResponse = new HashMap<>();
        filteredResponse.put("id", map.get("list_token_id"));
        filteredResponse.put("name", map.get("list_name"));
        filteredResponse.put("price", convertPrices(map.get("list_price")));
        filteredResponse.put("symbol", map.get("list_symbol"));
        filteredResponse.put("dataCount", map.get("dataCount"));
        filteredResponse.put("pageSize", map.get("pageSize"));

        return filteredResponse;
    }

    private List<String> getList(HashMap<String, List<String>> map, String name) {
        return map.get(name);
    }

    public HashMap<String, List<String>> showNftInfo(HashMap<String, List<String>> map) {
        List<String> uniqueNFTs = getList(map, "name").stream().
                sorted().
                distinct().
                collect(Collectors.toList());

        List<String> names = getList(map, "name");
        List<String> prices = getList(map, "price");
        List<String> symbols = getList(map, "symbol");

        int nftCounts = getList(map, "name").size();

        // формируем hashmap с ключами по названиям nft
        HashMap<String, List<String>> result = new HashMap<>();
        for (int j = 0; j < uniqueNFTs.size(); j++) {
            result.put(uniqueNFTs.get(j), new ArrayList<>());
        }

        // наполняем мапу
        for (int i = 0; i < nftCounts; i++) {
            List<String> currentList = result.get(names.get(i));
            currentList.add(prices.get(i) + " " + symbols.get(i));
            result.put(names.get(i), currentList);
        }

//        System.out.println("   All NFT = " + getList(map, "dataCount").get(0));
//        System.out.println("Parsed NFT = " + getList(map, "pageSize").get(0));
//        System.out.println("Unique NFT = " + uniqueNFTs.size());
//        printMap(result);
        return result;
    }

}
