package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

public class FoodObject {
    // Added Foods are stored in this arrayList (Only stores daily consumption)
    public static ArrayList<FoodObject> addedFood = new ArrayList<>();

    // FoodObject Description
    public String foodName;
    public int fdcId;
    public String dataType;
    public String brandOwner;
    public int servingSize;

    // FoodObject LabelNutrients
    public double calories = 0;
    public double fats = 0;
    public double saturatedFats = 0;
    public double transFat = 0;
    public double sodium = 0;
    public double fiber = 0;
    public double carbs = 0;
    public double sugars = 0;
    public double protein = 0;
    public double potassium = 0;

    public FoodObject(double calories, double fats, double saturatedFats, double transFat, double sodium)
    {

    }
    public FoodObject(JSONObject label){

        // Temp JsonObject to hold Objects
        JSONObject fact;

        // Fats
        if(label.get("fat") != null){
            fact = (JSONObject) label.get("fat");
            fats = Double.parseDouble(String.valueOf(fact.get("value")));
        }
        // Saturated Fats
        if(label.get("saturatedFat") != null){
            fact = (JSONObject) label.get("saturatedFat");
            saturatedFats = Double.parseDouble(String.valueOf(fact.get("value")));
        }
        // Trans Fats
        if(label.get("transFat") != null){
            fact = (JSONObject) label.get("transFat");
            transFat = Double.parseDouble(String.valueOf(fact.get("value")));
        }
        // Sodium
        if(label.get("sodium") != null){
            fact = (JSONObject) label.get("sodium");
            sodium = Double.parseDouble(String.valueOf(fact.get("value")));
        }
        // Carbohydrates
        if(label.get("carbohydrates") != null){
            fact = (JSONObject) label.get("carbohydrates");
            carbs = Double.parseDouble(String.valueOf(fact.get("value")));
        }
        // Fiber
        if(label.get("fiber") != null){
            fact = (JSONObject) label.get("fiber");
            fiber = Double.parseDouble(String.valueOf(fact.get("value")));
        }
        // Sugar
        if(label.get("sugars") != null){
            fact = (JSONObject) label.get("sugars");
            sugars = Double.parseDouble(String.valueOf(fact.get("value")));
        }
        // Protein
        if(label.get("protein") != null){
            fact = (JSONObject) label.get("protein");
            protein = Double.parseDouble(String.valueOf(fact.get("value")));
        }
        // Potassium
        if(label.get("potassium") != null){
            fact = (JSONObject) label.get("potassium");
            potassium = Double.parseDouble(String.valueOf(fact.get("value")));
        }
        // Calories
        if(label.get("calories") != null){
            fact = (JSONObject) label.get("calories");
            calories = Double.parseDouble(String.valueOf(fact.get("value")));
        }

    }

    public static void addFoodToList(FoodObject food){
        addedFood.add(food);
    }

    @Override
    public String toString(){
        return "calories: "+ calories + " fats: " + fats + " saturatedFats: "+ saturatedFats + " transFat: " + transFat +
                "sodium: "+ sodium + " fiber: " + fiber + " carbs: "+ carbs + " sugars: " + sugars + " protein: "+ protein + " potassium: " + potassium;
    }
    /*
    public FoodObject(JSONArray entry) {
        JSONArray test;
    }

    public FoodObject(JSONObject entry){
        JSONObject fact = new JSONObject();

        if(entry.get("fat") != null){

            fact.get("fat");
            fact.get("value");
            fats = Integer.parseInt(String.valueOf(fact.get("value")));

            System.out.println("Fats: " + fats);
        }

    }

     */



}
