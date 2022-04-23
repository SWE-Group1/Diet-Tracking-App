package sample;

import org.json.simple.JSONObject;

public class SearchObject {

    // FoodObject Description
    public String foodName;
    public int fdcId;
    public String dataType;
    public String brandOwner;
    public double servingSize;

    SearchObject(JSONObject entry) {
        if (entry.get("description") != null)
            foodName = String.valueOf(entry.get("description"));

        if (entry.get("fdcId") != null)
            fdcId = Integer.parseInt(String.valueOf(entry.get("fdcId")));

        if (entry.get("dataType") != null)
            dataType = String.valueOf(entry.get("dataType"));

        if (entry.get("brandName") != null)
            brandOwner = String.valueOf(entry.get("brandName"));

        if (entry.get("servingSize") != null)
            servingSize = Double.parseDouble(String.valueOf(entry.get("servingSize")));

    }

    @Override
    public String toString() {
        return "FoodName: " + foodName + "\t fdcId: " + fdcId + "\t dataType: " + dataType + "\tbrandName: " + brandOwner + "\t servingSize: " + servingSize;

    }

    public String getFoodName() {
        return foodName;
    }

    public int getFdcId() {
        return fdcId;
    }

    public String getDataType() {
        return dataType;
    }

    public String getBrandOwner() {
        return brandOwner;
    }

    public double getServingSize() {
        return servingSize;
    }


}
