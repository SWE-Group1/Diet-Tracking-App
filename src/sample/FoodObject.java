package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

public class FoodObject {
    // Added Foods are stored in this arrayList (Only stores daily consumption)
    public static ArrayList<String> dailyFood = new ArrayList<>();
    public static ArrayList<Integer> dailyFoodIds = new ArrayList<>();
    public static double[] dailyNutrients = new double[10];

    // FoodObject Description
    public String foodName;
    public int fdcId;
    public String dataType;
    public String brandOwner;
    public double servingSize;

    // FoodObject LabelNutrients
    public double calories = 0;         // id = 1008    unit = kcal    max = 2500 kcal
    public double fats = 0;             // id = 1004    unit = g       max = 100g
    public double saturatedFats = 0;    // id = 1258    unit = g       max = 20g
    public double transFat = 0;         // id = 1257    unit = g       max = 3g
    public double sodium = 0;           // id = 1093    unit = mg      max = 2500mg
    public double fiber = 0;            // id = 1079    unit = g       max = 80
    public double carbs = 0;            // id = 1005    unit = g       max = 500g
    public double sugars = 0;           // id = 2000    unit = g       max = 80g
    public double protein = 0;          // id = 1003    unit = g       max = 100g
    public double cholesterol = 0;      // id = 1253    unit = mg      max = 400mg

    public FoodObject(double[] labelNutrients) {
        calories = labelNutrients[0];
        fats = labelNutrients[1];
        saturatedFats = labelNutrients[2];
        transFat = labelNutrients[3];
        sodium = labelNutrients[4];
        fiber = labelNutrients[5];
        carbs = labelNutrients[6];
        sugars = labelNutrients[7];
        protein = labelNutrients[8];
        cholesterol = labelNutrients[9];

    }

    public static void addFoodToList(FoodObject food) {
        dailyFood.add(food.foodName);
    }

    public static void addFoodId(int id) {
        dailyFoodIds.add(id);
    }

    public static void removeFoodFromList(int index) {
        dailyFood.remove(index);
    }

    public void addNutrientsToList(FoodObject food) {
        dailyNutrients[0] += Math.round(food.calories * 10) / 10;
        dailyNutrients[1] += Math.round(food.fats * 10) / 10;
        dailyNutrients[2] += Math.round(food.saturatedFats * 10) / 10;
        dailyNutrients[3] += Math.round(food.transFat * 10) / 10;
        dailyNutrients[4] += Math.round(food.sodium * 10) / 10;
        dailyNutrients[5] += Math.round(food.fiber * 10) / 10;
        dailyNutrients[6] += Math.round(food.carbs * 10) / 10;
        dailyNutrients[7] += Math.round(food.sugars * 10) / 10;
        dailyNutrients[8] += Math.round(food.protein * 10) / 10;
        dailyNutrients[9] += Math.round(food.cholesterol * 10) / 10;
    }

    public void removeNutrientsFromList(FoodObject food) {
        dailyNutrients[0] -= Math.round(food.calories * 10) / 10;
        dailyNutrients[1] -= Math.round(food.fats * 10) / 10;
        dailyNutrients[2] -= Math.round(food.saturatedFats * 10) / 10;
        dailyNutrients[3] -= Math.round(food.transFat * 10) / 10;
        dailyNutrients[4] -= Math.round(food.sodium * 10) / 10;
        dailyNutrients[5] -= Math.round(food.fiber * 10) / 10;
        dailyNutrients[6] -= Math.round(food.carbs * 10) / 10;
        dailyNutrients[7] -= Math.round(food.sugars * 10) / 10;
        dailyNutrients[8] -= Math.round(food.protein * 10) / 10;
        dailyNutrients[9] -= Math.round(food.cholesterol * 10) / 10;

    }

    @Override
    public String toString() {
        return ("▬").repeat(10) + "\ncalories: " + calories + "\nfats: " + fats + "\nsaturatedFats: " + saturatedFats + "\ntransFat: " + transFat +
                "\nsodium: " + sodium + "\nfiber: " + fiber + "\ncarbs: " + carbs + "\nsugars: " + sugars + "\nprotein: " + protein + "\ncholesterol: " + cholesterol +
                "\n" + ("▬").repeat(10);
    }

    public void setDescription(SearchObject food) {
        foodName = food.getFoodName();
        fdcId = food.getFdcId();
        dataType = food.getDataType();
        brandOwner = food.getBrandOwner();
        servingSize = food.getServingSize();
    }


}
