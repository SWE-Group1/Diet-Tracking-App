package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class FoodData {

    //Paste into url to play around with search results
    //https://api.nal.usda.gov/fdc/v1/foods/search?api_key=KwfuFLiBA0GH55zdagUsasX7RFseFJJoSGbPEFYj&query=Apple&dataType=Survey%20%28FNDDS%29&pageSize=20&pageNumber=1

    public static ArrayList<SearchObject> searchResult = new ArrayList<>();

    private static final String API_KEY = "KwfuFLiBA0GH55zdagUsasX7RFseFJJoSGbPEFYj";
    public static int pageSize = 50;
    public static int pageNumber = 1;
    public static int totalHits = 0;

    // Search for food to select (end goal: return fdcId)
    public static void searchFood(String foodName, boolean isBranded, String brandName, int page){
        String data = "";
        pageSize = 50;
        pageNumber = 1;
        totalHits = 0;
        try {
            String searchURL = makeURL(foodName, isBranded, brandName);
            URL url = new URL(searchURL);

            // Attempt to connect
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connection is made
            int responseCode = conn.getResponseCode();

            // 200 = Good Connection
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }

                scanner.close();                                                // Close the scanner

                data = informationString.toString();                            // Copy data to string

                JSONObject dataObject = (JSONObject) JSONValue.parse(data);     // Parse data as string to JSONObject

                String hitResults = String.valueOf(dataObject.get("totalHits"));// Get total number of results
                totalHits = Integer.parseInt(hitResults);

                JSONArray foods = (JSONArray) dataObject.get("foods");          // Parse "foods" JSONObject as JSONArray

                // Parse food entries into searchResults ArrayList<SearchObject>
                JSONObject entry;
                int searchAmountDisplayed = Math.min(totalHits, pageSize); // if totalHits is greater than pageSize then we can use pageSize as search display limit but if other way around use totalHits
                searchResult.clear();

                for (int i = 0; i < searchAmountDisplayed; i++) {
                    // Get and add entry JSONObject
                    entry = (JSONObject) foods.get(i);
                    searchResult.add(new SearchObject(entry));
                }

            }

        }catch(Exception e){
            // Generic exception handling put more specific catch blocks above
            System.out.println("Error: " + e.getMessage());

            e.printStackTrace();// Remove later, only here for debugging
        }


        /*
        for(int i = 0; i < searchResult.size(); i++){
            System.out.println(i + ": " + searchResult.get(i));
        }
        */


    }

    // Get Nutritional Facts from desired fdcId (end goal: create new FoodObject with stored information)
    public static void getNutrition(){

    }

    public static String makeURL(String foodName, boolean isBranded, String brandName){
        String url = "";
        String dataType = "";
        String[] food = foodName.split(" ");
        String tempFood = "";

        if(!isBranded){
            // General Search

            // Parse foodName to be query
            for(int i = 0; i < food.length; i++){
                if(food[i].isEmpty()){continue;}

                if(i == food.length - 1){
                    tempFood += food[i];
                }else{
                    tempFood += food[i] + "%20";
                }
            }
            foodName = tempFood;
            dataType = "Survey%20%28FNDDS%29"; // "Survey (FNDDS)"

            url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key="+API_KEY+"&query="+foodName+"&dataType="+dataType+"&pageSize="+pageSize+"&pageNumber="+pageNumber;
            return url;

        }else{
            // Search for brand
            String[] brand = brandName.split(" ");
            String tempBrand = "";

            // Parse foodName to be query
            for(int i = 0; i < food.length; i++){
                if(food[i].isEmpty()){continue;}

                if(i == food.length - 1){
                    tempFood += food[i];
                }else{
                    tempFood += food[i] + "%20";
                }
            }

            // Parse brandName to be query
            for(int i = 0; i < brand.length; i++){
                if(brand[i].isEmpty()){continue;}

                if(i == brand.length - 1){
                    tempBrand += brand[i];
                }else{
                    tempBrand += brand[i] + "%20";
                }
            }
            foodName = tempFood;
            brandName = tempBrand;
            dataType = "Branded";

            url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key="+API_KEY+"&query="+foodName+"&dataType="+dataType+"&pageSize="+pageSize+"&pageNumber="+pageNumber+"&brandOwner="+brandName;
            return url;
        }

    }
}
