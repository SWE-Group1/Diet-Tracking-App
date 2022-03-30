package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SearchScene {

    @FXML Image headerImage;

    // Display Search Results
    @FXML ListView<String> searchList;
    @FXML TableView<String> tableList;


    @FXML TableColumn<String, String> FoodNameColumn;

    // Search Nodes
    @FXML AnchorPane headerAnchor;
    @FXML TextField searchTextFieldFood;
    @FXML TextField searchTextFieldBrand;
    @FXML JFXButton searchButton;
    @FXML ImageView searchButtonIcon;
    @FXML JFXButton addButton;

    String food, brand = "";
    boolean isBranded = false;
    int page = 1;


    @FXML
    void initialize(){


        /*
        for(SearchObject x : FoodData.searchResult){
            searchList.getItems().add(String.valueOf(x));
        }
        */


    }



    public void searchFood(){
        //FoodData.searchFood("Mango",false,"",1);

        // Checking if food textField is empty
        if(!searchTextFieldFood.getText().isEmpty()) {
            food = searchTextFieldFood.getText();
            brand = "";
            isBranded = false;
        }else {
            return;
        }


        // Checking if a brand was entered
        if(!searchTextFieldBrand.getText().isEmpty()){
            brand = searchTextFieldBrand.getText();
            isBranded = true;
        }else{
            brand = "";
            isBranded = false;
        }

        FoodData.searchFood(food,isBranded,brand,page);
        displaySearchResults();
    }

    public void displaySearchResults(){
        if(!FoodData.searchResult.isEmpty()){

            searchList.getItems().clear();

            for(SearchObject x : FoodData.searchResult){
                searchList.getItems().add(String.valueOf(x));

            }

            /*
            for(SearchObject x : FoodData.searchResult){
                //tableList.getItems().add(String.valueOf(x));
                //FoodNameColumn.getColumns().addAll(x);
            }
            */



        }else{
            // Display in a label -> "No results found"
            return;
        }
    }

    public void addFood(){
        System.out.println("Adding To Daily Foods: " + searchList.getSelectionModel().getSelectedItem());

        int index = searchList.getSelectionModel().getSelectedIndex();  // Get index of search results from ListView
        int fdcID = FoodData.searchResult.get(index).fdcId;             // Get fdcID
        boolean isBranded = false;

        //Make sure to implement a condition that prevents the user from adding the same item twice.
        isBranded = (FoodData.searchResult.get(index).dataType != "Survey (FNDDS)");

        FoodData.getNutrition(fdcID,isBranded);                                   // Creates FoodObject and adds to daily consumption
    }
}
