package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SearchScene {
    @FXML
    Image headerImage;
    @FXML
    ListView<String> searchList;

    // Search Nodes
    @FXML AnchorPane headerAnchor;
    @FXML TextField searchTextFieldFood;
    @FXML TextField searchTextFieldBrand;
    @FXML JFXButton searchButton;
    @FXML ImageView searchButtonIcon;

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

        }else{
            // Display in a label -> "No results found"
            return;
        }
    }
}
