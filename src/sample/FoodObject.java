package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

public class FoodObject {
    // Added Foods are stored in this arrayList (Only stores daily consumption)
    public static ArrayList<String> dailyFood = new ArrayList<>();
    public static double[] dailyNutrients = new double[10];

    // FoodObject Description
    public String foodName;
    public int fdcId;
    public String dataType;
    public String brandOwner;
    public double servingSize;

    // FoodObject LabelNutrients
    public double calories = 0;         // id = 1008
    public double fats = 0;             // id = 1004
    public double saturatedFats = 0;    // id = 1258
    public double transFat = 0;         // id = 1257
    public double sodium = 0;           // id = 1093
    public double fiber = 0;            // id = 1079
    public double carbs = 0;            // id = 1005
    public double sugars = 0;           // id = 2000
    public double protein = 0;          // id = 1003
    public double cholesterol = 0;      // id = 1253

    public FoodObject(double[] labelNutrients)
    {
        calories = labelNutrients[0]; fats = labelNutrients[1]; saturatedFats = labelNutrients[2];
        transFat = labelNutrients[3]; sodium = labelNutrients[4]; fiber = labelNutrients[5];
        carbs = labelNutrients[6]; sugars = labelNutrients[7]; protein = labelNutrients[8];
        cholesterol = labelNutrients[9];

    }

    public static void addFoodToList(FoodObject food){
        dailyFood.add(food.foodName);
    }
    public static void removeFoodFromList(int index){dailyFood.remove(index);}

    public void addNutrientsToList(FoodObject food){
        dailyNutrients[0] += food.calories;
        dailyNutrients[1] += food.fats;
        dailyNutrients[2] += food.saturatedFats;
        dailyNutrients[3] += food.transFat;
        dailyNutrients[4] += food.sodium;
        dailyNutrients[5] += food.fiber;
        dailyNutrients[6] += food.carbs;
        dailyNutrients[7] += food.sugars;
        dailyNutrients[8] += food.protein;
        dailyNutrients[9] += food.cholesterol;
    }

    public void removeNutrientsFromList(FoodObject food){
        dailyNutrients[0] -= food.calories;
        dailyNutrients[1] -= food.fats;
        dailyNutrients[2] -= food.saturatedFats;
        dailyNutrients[3] -= food.transFat;
        dailyNutrients[4] -= food.sodium;
        dailyNutrients[5] -= food.fiber;
        dailyNutrients[6] -= food.carbs;
        dailyNutrients[7] -= food.sugars;
        dailyNutrients[8] -= food.protein;
        dailyNutrients[9] -= food.cholesterol;
    }

    @Override
    public String toString() {
        return ("▬").repeat(10) + "\ncalories: "+ calories + "\nfats: " + fats + "\nsaturatedFats: "+ saturatedFats + "\ntransFat: " + transFat +
                "\nsodium: "+ sodium + "\nfiber: " + fiber + "\ncarbs: "+ carbs + "\nsugars: " + sugars + "\nprotein: "+ protein + "\ncholesterol: " + cholesterol +
                "\n"+ ("▬").repeat(10);
    }

    public void setDescription(SearchObject food){
        foodName = food.getFoodName();
        fdcId = food.getFdcId();
        dataType = food.getDataType();
        brandOwner = food.getBrandOwner();
        servingSize = food.getServingSize();
    }





}
