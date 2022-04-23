package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class Log {

    Log() {
    }

    private static JSONObject logFileObject;

    public static JSONObject currentEntryDate;
    public static JSONObject nutritionalFacts;
    public static JSONArray foods;
    public static JSONArray foodIds;


    public static String getDate() {
        Date rawDate = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd-yyyy");
        return formatDate.format(rawDate);
    }

    // Reads data from FoodHistoryLog.json and stores it in JSONObject to be parsed
    public static void read() {
        // JSONParser parser = new JSONParser();
        try {
            String logFileName = "src/sample/FoodHistoryLog.json";
            File logFile = new File(logFileName);
            Scanner sc = new Scanner(logFile);
            String data = "";

            while (sc.hasNextLine()) {
                data += sc.nextLine();
            }
            sc.close();

            if (data.isEmpty()) {
                System.out.println("LogFile is empty inputting bare minimum into file");
                data = "{}";
            }
            logFileObject = (JSONObject) JSONValue.parse(data);

            parseFile();

            initializeDailyValues();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Parses logFile to obtain the nutritionalFacts and foods JSONObjects for the current day
    public static void parseFile() {
        if (!logFileObject.isEmpty()) {
            currentEntryDate = (JSONObject) logFileObject.get(getDate());
        } else {
            currentEntryDate = new JSONObject();
        }


        if (currentEntryDate != null) {
            foods = (JSONArray) currentEntryDate.get("foods");
            foodIds = (JSONArray) currentEntryDate.get("foodIds");
            nutritionalFacts = (JSONObject) currentEntryDate.get("nutritionalFacts");
        } else {
            currentEntryDate = new JSONObject();
            foods = new JSONArray();
            foodIds = new JSONArray();
            nutritionalFacts = new JSONObject();
        }

    }

    // Initializes daily food list and nutrients list
    public static void initializeDailyValues() {

        // Initialize foods
        if (foods != null) {
            for (int i = 0; i < foods.size(); i++) {
                FoodObject.dailyFood.add(foods.get(i).toString());
            }
        } else {
            foods = new JSONArray();
        }

        // Initialize food ids
        if (foodIds != null) {
            for (int i = 0; i < foodIds.size(); i++) {
                FoodObject.dailyFoodIds.add(Integer.parseInt(foodIds.get(i).toString()));
            }
        } else {
            foodIds = new JSONArray();
        }

        // Initialize foods  nutritional facts

        if (nutritionalFacts == null) {
            nutritionalFacts = new JSONObject();
        }

        if (nutritionalFacts.get("calories") != null) {
            FoodObject.dailyNutrients[0] = Double.parseDouble(nutritionalFacts.get("calories").toString());
        } else {
            FoodObject.dailyNutrients[0] = 0;
            nutritionalFacts.put("calories", 0);
        }

        if (nutritionalFacts.get("fats") != null) {
            FoodObject.dailyNutrients[1] = Double.parseDouble(nutritionalFacts.get("fats").toString());
        } else {
            FoodObject.dailyNutrients[1] = 0;
            nutritionalFacts.put("fats", 0);
        }

        if (nutritionalFacts.get("saturatedFats") != null) {
            FoodObject.dailyNutrients[2] = Double.parseDouble(nutritionalFacts.get("saturatedFats").toString());
        } else {
            FoodObject.dailyNutrients[2] = 0;
            nutritionalFacts.put("saturatedFats", 0);
        }

        if (nutritionalFacts.get("transFat") != null) {
            FoodObject.dailyNutrients[3] = Double.parseDouble(nutritionalFacts.get("transFat").toString());
        } else {
            FoodObject.dailyNutrients[3] = 0;
            nutritionalFacts.put("transFat", 0);
        }

        if (nutritionalFacts.get("sodium") != null) {
            FoodObject.dailyNutrients[4] = Double.parseDouble(nutritionalFacts.get("sodium").toString());
        } else {
            FoodObject.dailyNutrients[4] = 0;
            nutritionalFacts.put("sodium", 0);
        }

        if (nutritionalFacts.get("fiber") != null) {
            FoodObject.dailyNutrients[5] = Double.parseDouble(nutritionalFacts.get("fiber").toString());
        } else {
            FoodObject.dailyNutrients[5] = 0;
            nutritionalFacts.put("fiber", 0);
        }

        if (nutritionalFacts.get("carbs") != null) {
            FoodObject.dailyNutrients[6] = Double.parseDouble(nutritionalFacts.get("carbs").toString());
        } else {
            FoodObject.dailyNutrients[6] = 0;
            nutritionalFacts.put("carbs", 0);
        }

        if (nutritionalFacts.get("sugars") != null) {
            FoodObject.dailyNutrients[7] = Double.parseDouble(nutritionalFacts.get("sugars").toString());
        } else {
            FoodObject.dailyNutrients[7] = 0;
            nutritionalFacts.put("sugars", 0);
        }

        if (nutritionalFacts.get("protein") != null) {
            FoodObject.dailyNutrients[8] = Double.parseDouble(nutritionalFacts.get("protein").toString());
        } else {
            FoodObject.dailyNutrients[8] = 0;
            nutritionalFacts.put("protein", 0);
        }

        if (nutritionalFacts.get("cholesterol") != null) {
            FoodObject.dailyNutrients[9] = Double.parseDouble(nutritionalFacts.get("cholesterol").toString());
        } else {
            FoodObject.dailyNutrients[9] = 0;
            nutritionalFacts.put("cholesterol", 0);
        }


    }

    public static void save() {
        try {
            String logFileName = "src/sample/FoodHistoryLog.json";

            File logFile = new File(logFileName);
            FileWriter fileWriter = new FileWriter(logFile);


            if (!foods.isEmpty()) {
                foods.clear();
            }
            foods.addAll(FoodObject.dailyFood);

            if (!foodIds.isEmpty()) {
                foodIds.clear();
            }
            foodIds.addAll(FoodObject.dailyFoodIds);

            nutritionalFacts.put("calories", FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("fats", FoodObject.dailyNutrients[1]);
            nutritionalFacts.put("saturatedFats", FoodObject.dailyNutrients[2]);
            nutritionalFacts.put("transFat", FoodObject.dailyNutrients[3]);
            nutritionalFacts.put("sodium", FoodObject.dailyNutrients[4]);
            nutritionalFacts.put("fiber", FoodObject.dailyNutrients[5]);
            nutritionalFacts.put("carbs", FoodObject.dailyNutrients[6]);
            nutritionalFacts.put("sugars", FoodObject.dailyNutrients[7]);
            nutritionalFacts.put("protein", FoodObject.dailyNutrients[8]);
            nutritionalFacts.put("cholesterol", FoodObject.dailyNutrients[9]);

            currentEntryDate.put("nutritionalFacts", nutritionalFacts);
            currentEntryDate.put("foods", foods);
            currentEntryDate.put("foodIds", foodIds);

            logFileObject.put(getDate(), currentEntryDate);

            Iterator logEntries = logFileObject.keySet().iterator();    // Gets a set of all they keys/entries from the FoodHistoryLog

            //Write to file
            fileWriter.write("{\n");

            // Formatting the FoodHistoryLog File for better readability
            while (logEntries.hasNext()) {
                String key = (String) logEntries.next();
                if (logEntries.hasNext()) {
                    fileWriter.write("\"" + key + "\":" + logFileObject.get(key) + ",\n");
                } else {
                    fileWriter.write("\"" + key + "\":" + logFileObject.get(key) + "\n}");
                }

            }

            fileWriter.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static JSONObject searchFoodHistoryLog(String key) {
        // Handle case if key value return null
        return (JSONObject) logFileObject.get(key);
    }
}
