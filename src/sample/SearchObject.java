package sample;

import org.json.simple.JSONObject;

public class SearchObject {

    // FoodObject Description
    public String foodName;
    public int fdcId;
    public String dataType;
    public String brandOwner;
    public double servingSize;

    SearchObject(JSONObject entry){
        if(entry.get("description") != null)
            foodName = String.valueOf(entry.get("description"));

        if(entry.get("fdcId") != null)
            fdcId = Integer.parseInt(String.valueOf(entry.get("fdcId")));

        if(entry.get("dataType") != null)
            dataType = String.valueOf(entry.get("dataType"));

        if(entry.get("brandName") != null)
            brandOwner = String.valueOf(entry.get("brandName"));

        if(entry.get("servingSize") != null)
            servingSize = Double.parseDouble(String.valueOf(entry.get("servingSize")));




        if(foodName != null && foodName.length() >= 30){
            foodName = foodName.substring(0,30);
        }
        else{

            foodName = String.format("%-30s",foodName).replace(' ','*');
            //foodName = foodName.replace('*',' ');

        }



    }

    @Override
    public String toString(){
        return "FoodName: " + foodName + "\t\t\t\t\t\t fdcId: " + fdcId + "\t\t\t\t\t\t dataType: " + dataType + "\t\t\t\t\t\t brandName: " + brandOwner + "\t\t\t\t\t\t servingSize: " + servingSize;
        //return "FoodName: " + foodName + "                    " + " fdcId: " + fdcId + "                    " + " dataType: " + dataType + "                    " + " brandName: " + brandOwner + "                    " + " servingSize: " + servingSize;
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
