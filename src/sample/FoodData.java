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

    // using FDC ID
    //https://api.nal.usda.gov/fdc/v1/food/######?api_key=DEMO_KEY

    // https://api.nal.usda.gov/fdc/v1/food/1102670?api_key=KwfuFLiBA0GH55zdagUsasX7RFseFJJoSGbPEFYj <<--- Generic fdcid Search
    // https://api.nal.usda.gov/fdc/v1/food/1901367?api_key=KwfuFLiBA0GH55zdagUsasX7RFseFJJoSGbPEFYj <<--- Branded fdcid Search

    public static ArrayList<SearchObject> searchResult = new ArrayList<>();

    private static final String API_KEY = "KwfuFLiBA0GH55zdagUsasX7RFseFJJoSGbPEFYj"; // Default rate of 3,600 requests per hour per IP address for this API Key
    public static int pageSize = 50;
    public static int pageNumber = 1;
    public static int totalHits = 0;

    // Search for food to select (end goal: return fdcId)
    public static void searchFood(String foodName, boolean isBranded, String brandOwner, int page){
        String data = "";
        pageSize = 50;
        pageNumber = 1;
        totalHits = 0;
        try {
            String searchURL = makeSearchURL(foodName, isBranded, brandOwner);
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

    }

    // Get Nutritional Facts from desired fdcId (end goal: create new FoodObject with stored information)
    public static void getNutrition(int fdcID, boolean isBranded, SearchObject foodResult){
        try{
            String data;
            URL url = new URL("https://api.nal.usda.gov/fdc/v1/food/" + fdcID + "?api_key=" + API_KEY);

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

                addFoodObject(dataObject,isBranded,foodResult,fdcID);

            }

        }catch(Exception e){
            // Generic exception handling put more specific catch blocks above
            System.out.println("Error: " + e.getMessage());

            e.printStackTrace();// Remove later, only here for debugging
        }


    }

    // Parse Nutritional Data and creates food objects -- Method called by FoodData->getNutrition
    public static void addFoodObject(JSONObject data, boolean isBranded, SearchObject foodResult, int fdcID){

        // Parse JSONObject data and add nutritional facts to new FoodObject

        JSONObject labelNutrients = (JSONObject) data.get("labelNutrients"); //We create a JSONObject for labels by default it is null if it cannot be initialized

        // If "labelNutrients" is not empty (null), use labelNutrients to create FoodObject
        if(isBranded && labelNutrients != null){

                FoodObject food = new FoodObject( new double[] //These labels are all based on the order of the FoodObject attributes
                        {
                                (((JSONObject) labelNutrients.get("calories")) != null) ? Double.parseDouble(((JSONObject) labelNutrients.get("calories")).get("value").toString()) : 0,
                                (((JSONObject) labelNutrients.get("fat")) != null) ? Double.parseDouble(((JSONObject) labelNutrients.get("fat")).get("value").toString()) : 0,
                                (((JSONObject) labelNutrients.get("saturatedFat")) != null) ? Double.parseDouble(((JSONObject) labelNutrients.get("saturatedFat")).get("value").toString()) : 0,
                                (((JSONObject) labelNutrients.get("transFat")) != null) ? Double.parseDouble(((JSONObject) labelNutrients.get("transFat")).get("value").toString()) : 0,
                                (((JSONObject) labelNutrients.get("sodium")) != null) ? Double.parseDouble(((JSONObject) labelNutrients.get("sodium")).get("value").toString()) : 0,
                                (((JSONObject) labelNutrients.get("fiber")) != null) ? Double.parseDouble(((JSONObject) labelNutrients.get("fiber")).get("value").toString()) : 0,
                                (((JSONObject) labelNutrients.get("carbohydrates")) != null) ? Double.parseDouble((((JSONObject) labelNutrients.get("carbohydrates")).get("value")).toString()) : 0,
                                (((JSONObject) labelNutrients.get("sugars")) != null) ? Double.parseDouble(((JSONObject) labelNutrients.get("sugars")).get("value").toString()) : 0,
                                (((JSONObject) labelNutrients.get("protein")) != null) ? Double.parseDouble(((JSONObject) labelNutrients.get("protein")).get("value").toString()) : 0,
                                (((JSONObject) labelNutrients.get("cholesterol")) != null) ? Double.parseDouble(((JSONObject) labelNutrients.get("cholesterol")).get("value").toString()) : 0
                        }
                );
                food.setDescription(foodResult);    // Sets food description
                FoodObject.addFoodToList(food);     // Add food to daily consumed foods
                FoodObject.addFoodId(fdcID);        // Add food id of daily consumed food
                food.addNutrientsToList(food);      // Adds food's nutrients to daily foods

                System.out.println(food); // Prints FoodObject label contents to the terminal window

        }else{
        // If "labelNutrients" is empty (null), use foodNutrients for FoodObject

            System.out.println("labelNutrients does not exist.\nUsing foodNutrients data\n"); // Notifying us about the nutrition facts

            JSONArray foodNutrients = (JSONArray) data.get("foodNutrients");    // Gets foodNutrients JSON object array

            JSONObject entry;
            double[] foodNutrientsArray = new double[10];

            for(int i = 0; i < foodNutrients.size(); i++){

                entry = (JSONObject) foodNutrients.get(i);
                // Selects from the foodNutrients JsonArray the needed nutrients based off their corresponding id
                switch ( Integer.parseInt(((JSONObject) entry.get("nutrient")).get("id").toString())){

                    case 1008: foodNutrientsArray[0] = Double.parseDouble(entry.get("amount").toString());// calories
                        break;

                    case 1004: foodNutrientsArray[1] = Double.parseDouble(entry.get("amount").toString());// fats
                        break;

                    case 1258: foodNutrientsArray[2] = Double.parseDouble(entry.get("amount").toString());// saturatedFats
                        break;

                    case 1257: foodNutrientsArray[3] = Double.parseDouble(entry.get("amount").toString());// transFat
                        break;

                    case 1093: foodNutrientsArray[4] = Double.parseDouble(entry.get("amount").toString());// sodium
                        break;

                    case 1079: foodNutrientsArray[5] = Double.parseDouble(entry.get("amount").toString());// fiber
                        break;

                    case 1005: foodNutrientsArray[6] = Double.parseDouble(entry.get("amount").toString());// carbs
                        break;

                    case 2000: foodNutrientsArray[7] = Double.parseDouble(entry.get("amount").toString());// sugars
                        break;

                    case 1003: foodNutrientsArray[8] = Double.parseDouble(entry.get("amount").toString());// protein
                        break;

                    case 1253: foodNutrientsArray[9] = Double.parseDouble(entry.get("amount").toString());// cholesterol
                        break;

                }
            }
            FoodObject food = new FoodObject(foodNutrientsArray);
            food.setDescription(foodResult);    // Sets food description
            FoodObject.addFoodToList(food);     // Add food to daily consumed foods
            FoodObject.addFoodId(fdcID);        // Add food id of daily consumed food
            food.addNutrientsToList(food);      // Adds food's nutrients to daily foods

            System.out.println(food);

        }

        // Prints List of added/consumed foods & nutrients to terminal window
        for(int i = 0; i < FoodObject.dailyFood.size(); i++){
            System.out.println((i+1) + ": " + FoodObject.dailyFood.get(i));
        }

        System.out.println(("▬").repeat(10) + "\ncalories: "+ FoodObject.dailyNutrients[0] + "\nfats: " + FoodObject.dailyNutrients[1] + "\nsaturatedFats: "+ FoodObject.dailyNutrients[2] + "\ntransFat: " + FoodObject.dailyNutrients[3] +
                "\nsodium: "+ FoodObject.dailyNutrients[4] + "\nfiber: " + FoodObject.dailyNutrients[5] + "\ncarbs: "+ FoodObject.dailyNutrients[6] + "\nsugars: " + FoodObject.dailyNutrients[7] +
                "\nprotein: "+ FoodObject.dailyNutrients[8] + "\ncholesterol: " + FoodObject.dailyNutrients[9] + "\n"+ ("▬").repeat(10));






    }

    public static String makeSearchURL(String foodName, boolean isBranded, String brandOwner){
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
            System.out.println(url);
            return url;

        }else{
            // Search for brand
            String[] brand = brandOwner.split(" ");
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
            brandOwner = tempBrand;
            dataType = "Branded";

            url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key="+API_KEY+"&query="+foodName+"&dataType="+dataType+"&pageSize="+pageSize+"&pageNumber="+pageNumber+"&brandOwner="+brandOwner;

            // Print URL (Debugging - Remove Later)
            System.out.println(url);

            return url;
        }

    }



}
