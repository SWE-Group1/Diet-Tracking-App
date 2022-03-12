package sample;

import java.util.ArrayList;

public class FoodObject {
    // Added Foods are stored in this arrayList (Only stores daily consumption)
    public static ArrayList<FoodObject> addedFood = new ArrayList<>();

    // FoodObject Description
    public String foodName;
    public int fdcId;
    public String dataType;
    public String brandName;
    public int servingSize;

    // FoodObject LabelNutrients
    public double calories;
    public double sodium;
    public double fiber;
    public double sugars;
    //...
    //...
    //...



}
