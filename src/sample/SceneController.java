package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    // CONTROLLING VARIABLES & METHODS ========================================
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int sceneNum = 1;

    public void switchToSearchScene(ActionEvent event) throws IOException {
        sceneNum = 1;
         root = FXMLLoader.load(getClass().getResource("SearchScene.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }
    public void switchToHistoryScene(ActionEvent event) throws IOException {
        sceneNum = 2;
        root = FXMLLoader.load(getClass().getResource("HistoryScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void switchToSummaryScene(ActionEvent event) throws IOException {
        sceneNum = 3;
        root = FXMLLoader.load(getClass().getResource("SummaryScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //==========================================================================


    // SEARCH SCENE VARIABLES ========================================

    // Display Search Results
    @FXML TableView<SearchObject> tableList;

    @FXML TableColumn<SearchObject, String> FoodNameCol;
    @FXML TableColumn<SearchObject, Integer> FDCID;
    @FXML TableColumn<SearchObject, String> BrandOwner;
    @FXML TableColumn<SearchObject, String> DataType;
    @FXML TableColumn<SearchObject, String> ServingSize;

    // Searching Objects
    @FXML AnchorPane headerAnchor;
    @FXML TextField searchTextFieldFood;
    @FXML TextField searchTextFieldBrand;
    @FXML JFXButton searchButton;
    @FXML ImageView searchButtonIcon;
    @FXML JFXButton addButton;

    String food, brand = "";
    boolean isBranded = false;
    int page = 1;
    //=================================================================

    // HISTORY SCENE VARIABLES ========================================



    //=================================================================

    // SUMMARY SCENE VARIABLES ========================================



    //================================================================



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
            FoodNameCol.setCellValueFactory(new PropertyValueFactory<SearchObject, String>("foodName"));
            FDCID.setCellValueFactory(new PropertyValueFactory<SearchObject, Integer>("fdcId"));
            BrandOwner.setCellValueFactory(new PropertyValueFactory<SearchObject, String>("brandOwner"));
            DataType.setCellValueFactory(new PropertyValueFactory<SearchObject, String>("dataType"));
            ServingSize.setCellValueFactory(new PropertyValueFactory<SearchObject, String>("servingSize"));

            tableList.getItems().clear();

            ObservableList<SearchObject> displaySearchList = FXCollections.observableArrayList();
            displaySearchList.addAll(FoodData.searchResult);
            tableList.setItems(displaySearchList);

        }else{
            // Display in a label -> "No results found"
            tableList.getItems().clear();
            tableList.setPlaceholder(new Label("No Results Found"));

            return;
        }
    }

    public void addFood(){

        System.out.println("Adding To Daily Foods: " + tableList.getSelectionModel().getSelectedItem());

        int index = tableList.getSelectionModel().getSelectedIndex();   // Get index of search results from TableView
        int fdcID = FoodData.searchResult.get(index).fdcId;             // Get fdcID
        boolean isBranded = false;

        //Make sure to implement a condition that prevents the user from adding the same item twice.
        isBranded = (FoodData.searchResult.get(index).dataType != "Survey (FNDDS)");


        FoodData.getNutrition(fdcID,isBranded,FoodData.searchResult.get(index));                                   // Creates FoodObject and adds to daily consumption
    }
}
