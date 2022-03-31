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

    public FoodObject(double[] labelNutrients)
    {

        calories = labelNutrients[0]; fats = labelNutrients[1]; saturatedFats = labelNutrients[2];
        transFat = labelNutrients[3]; sodium = labelNutrients[4]; fiber = labelNutrients[5];
        carbs = labelNutrients[6]; sugars = labelNutrients[7]; protein = labelNutrients[8];
        potassium = labelNutrients[9];
    }

    public static void addFoodToList(FoodObject food){
        addedFood.add(food);
    }

    @Override
    public String toString() {
        return ("▬").repeat(10) + "\ncalories: "+ calories + "\nfats: " + fats + "\nsaturatedFats: "+ saturatedFats + "\ntransFat: " + transFat +
                "\nsodium: "+ sodium + "\nfiber: " + fiber + "\ncarbs: "+ carbs + "\nsugars: " + sugars + "\nprotein: "+ protein + "\npotassium: " + potassium +
                "\n"+ ("▬").repeat(10);
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
