package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log {

    Log(){
        // Read From FoodHistoryLog.json and initialize FoodObject dailyFoods & dailyNutrients
        // Account for no information/date being there
    }
    private static JSONObject logFileObject;

    public static JSONObject currentEntryDate;
    public static JSONObject nutritionalFacts;
    public static JSONArray foods;



    public static String getDate(){
        Date rawDate = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd-yyyy");
        return formatDate.format(rawDate);
    }
    // Reads data from FoodHistoryLog.json and stores it in JSONObject to be parsed
    public static void read(){
        JSONParser parser = new JSONParser();
        try{
            String logFile = "src/sample/FoodHistoryLog.json";
/*
            BufferedReader br = new BufferedReader(new FileReader(logFile));
            String data;
            StringBuilder informationString = new StringBuilder();

            while(br.readLine() != null){
                //Read from file
                informationString.append(br.readLine());
            }

            br.close();

            data = informationString.toString();                            // Copy data to string
            System.out.println(data);
            logFileObject = (JSONObject) JSONValue.parse(data);             // Parse data to JSONObject

            System.out.println("Log File empty?" + logFileObject.isEmpty());
*/
            Object obj = parser.parse(new FileReader(logFile));
            logFileObject = (JSONObject) obj;


            parseFile();

            initializeDailyValues();

        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Parses logFile to obtain the nutritionalFacts and foods JSONObjects for the current day
    public static void parseFile(){
        if(!logFileObject.isEmpty()) {
            //currentEntryDate = (JSONObject) logFileObject.get(getDate());
            currentEntryDate = new JSONObject();
        }
        else
        {
            currentEntryDate = new JSONObject();
        }

        if(currentEntryDate == null){
            foods = (JSONArray) currentEntryDate.get("foods");
            nutritionalFacts = (JSONObject) currentEntryDate.get("nutritionalFacts");
        }
        else
        {
             foods = new JSONArray();
             nutritionalFacts = new JSONObject();
        }
    }

    // Initializes daily food list and nutrients list
    public static void initializeDailyValues(){

        for(int i = 0; i < foods.size(); i++){
            FoodObject.dailyFood.add(foods.get(i).toString());
        }

      if(  nutritionalFacts.get("calories") != null ){FoodObject.dailyNutrients[0] = Double.parseDouble(nutritionalFacts.get("calories").toString());}
      else{FoodObject.dailyNutrients[0] = 0;}

      if(  nutritionalFacts.get("fats") != null ){FoodObject.dailyNutrients[1] = Double.parseDouble(nutritionalFacts.get("fats").toString());}
      else{FoodObject.dailyNutrients[1] = 0;}

      if(  nutritionalFacts.get("saturatedFats") != null ){FoodObject.dailyNutrients[2] = Double.parseDouble(nutritionalFacts.get("saturatedFats").toString());}
      else{FoodObject.dailyNutrients[2] = 0;}

      if(  nutritionalFacts.get("transFat") != null ){FoodObject.dailyNutrients[3] = Double.parseDouble(nutritionalFacts.get("transFat").toString());}
      else{FoodObject.dailyNutrients[3] = 0;}

      if(  nutritionalFacts.get("sodium") != null ){FoodObject.dailyNutrients[4] = Double.parseDouble(nutritionalFacts.get("sodium").toString());}
      else{FoodObject.dailyNutrients[4] = 0;}

      if(  nutritionalFacts.get("fiber") != null ){FoodObject.dailyNutrients[5] = Double.parseDouble(nutritionalFacts.get("fiber").toString());}
      else{FoodObject.dailyNutrients[5] = 0;}

      if(  nutritionalFacts.get("carbs") != null ){FoodObject.dailyNutrients[6] = Double.parseDouble(nutritionalFacts.get("carbs").toString());}
      else{FoodObject.dailyNutrients[6] = 0;}

      if(  nutritionalFacts.get("sugars") != null ){FoodObject.dailyNutrients[7] = Double.parseDouble(nutritionalFacts.get("sugars").toString());}
      else{FoodObject.dailyNutrients[7] = 0;}

      if(  nutritionalFacts.get("protein") != null ){FoodObject.dailyNutrients[8] = Double.parseDouble(nutritionalFacts.get("protein").toString());}
      else{FoodObject.dailyNutrients[8] = 0;}

      if(  nutritionalFacts.get("cholesterol") != null ){FoodObject.dailyNutrients[9] = Double.parseDouble(nutritionalFacts.get("cholesterol").toString());}
      else{FoodObject.dailyNutrients[9] = 0;}


    }

    public static void save(){
        try{
            String logFile = "src/sample/FoodHistoryLog.json";

            BufferedWriter bw = new BufferedWriter(new FileWriter(logFile));

            //Write to file
            if(!foods.isEmpty()){foods.clear();}
            foods.addAll(FoodObject.dailyFood);

            nutritionalFacts.put("calories",FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("fats",FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("saturatedFats",FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("transFat",FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("sodium",FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("fiber",FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("carbs",FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("sugars",FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("protein",FoodObject.dailyNutrients[0]);
            nutritionalFacts.put("cholesterol",FoodObject.dailyNutrients[0]);

            currentEntryDate.put("nutritionalFacts",nutritionalFacts);
            currentEntryDate.put("foods",foods);

            logFileObject.put(getDate(),currentEntryDate);

            bw.flush();
            bw.close();

        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
