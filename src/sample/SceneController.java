package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SceneController {
    // CONTROLLING VARIABLES & METHODS ========================================
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int sceneNum = 1;
    private static Scene window;
    private static double width;
    private static double height;

    public void switchToSearchScene(ActionEvent event) throws IOException {
        setSceneDimensions();
        sceneNum = 1;
        root = FXMLLoader.load(getClass().getResource("SearchScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();

        setSceneDetails(scene);
    }

    public void switchToHistoryScene(ActionEvent event) throws IOException {
        setSceneDimensions();
        sceneNum = 2;
        root = FXMLLoader.load(getClass().getResource("HistoryScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();

        setSceneDetails(scene);

    }

    public void switchToSummaryScene(ActionEvent event) throws IOException {
        setSceneDimensions();
        sceneNum = 3;
        root = FXMLLoader.load(getClass().getResource("SummaryScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();

        setSceneDetails(scene);

        // Set Summary Values
    }

    public void setSceneDimensions() {
        width = window.getWidth();
        height = window.getHeight();
    }

    public static void setSceneDetails(Scene currentScene) {
        window = currentScene;
    }


    //==========================================================================


    // SEARCH SCENE VARIABLES & METHODS ========================================

    // Display Search Results
    @FXML
    TableView<SearchObject> tableList;

    @FXML
    TableColumn<SearchObject, String> FoodNameCol;
    @FXML
    TableColumn<SearchObject, Integer> FDCID;
    @FXML
    TableColumn<SearchObject, String> BrandOwner;
    @FXML
    TableColumn<SearchObject, String> DataType;
    @FXML
    TableColumn<SearchObject, String> ServingSize;

    // Searching Objects
    @FXML
    AnchorPane headerAnchor;
    @FXML
    TextField searchTextFieldFood;
    @FXML
    TextField searchTextFieldBrand;
    @FXML
    JFXButton searchButton;
    @FXML
    ImageView searchButtonIcon;
    @FXML
    JFXButton addButton;

    String food, brand = "";
    boolean isBranded = false;
    int page = 1;

    public void searchFood() {
        //FoodData.searchFood("Mango",false,"",1);

        // Checking if food textField is empty
        if (!searchTextFieldFood.getText().isEmpty()) {
            food = searchTextFieldFood.getText();
            brand = "";
            isBranded = false;
        } else {
            return;
        }


        // Checking if a brand was entered
        if (!searchTextFieldBrand.getText().isEmpty()) {
            brand = searchTextFieldBrand.getText();
            isBranded = true;
        } else {
            brand = "";
            isBranded = false;
        }

        FoodData.searchFood(food, isBranded, brand, page);
        displaySearchResults();
    }

    public void displaySearchResults() {
        if (!FoodData.searchResult.isEmpty()) {
            FoodNameCol.setCellValueFactory(new PropertyValueFactory<SearchObject, String>("foodName"));
            FDCID.setCellValueFactory(new PropertyValueFactory<SearchObject, Integer>("fdcId"));
            BrandOwner.setCellValueFactory(new PropertyValueFactory<SearchObject, String>("brandOwner"));
            DataType.setCellValueFactory(new PropertyValueFactory<SearchObject, String>("dataType"));
            ServingSize.setCellValueFactory(new PropertyValueFactory<SearchObject, String>("servingSize"));

            tableList.getItems().clear();

            ObservableList<SearchObject> displaySearchList = FXCollections.observableArrayList();
            displaySearchList.addAll(FoodData.searchResult);
            tableList.setItems(displaySearchList);

        } else {
            // Display in a label -> "No results found"
            tableList.getItems().clear();
            tableList.setPlaceholder(new Label("No Results Found"));

            return;
        }
    }

    public void addFood() {
        System.out.println("Adding To Daily Foods: " + tableList.getSelectionModel().getSelectedItem());
        try {
            int index = tableList.getSelectionModel().getSelectedIndex();   // Get index of search results from TableView
            int fdcID = FoodData.searchResult.get(index).fdcId;             // Get fdcID
            boolean isBranded = false;

            //Make sure to implement a condition that prevents the user from adding the same item twice.
            isBranded = (FoodData.searchResult.get(index).dataType != "Survey (FNDDS)");

            FoodData.getNutrition(fdcID, isBranded, FoodData.searchResult.get(index));    // Creates FoodObject and adds to daily consumption
        } catch (Exception e) {
            System.out.println(" \u001b[91mError\u001b[0m → \u001b[91mUser did not select a food item to add to the list!\u001b[0m");
        } // Error msg
    }
    //=================================================================

    // HISTORY SCENE VARIABLES ========================================

    @FXML
    DatePicker datePicker;
    @FXML
    JFXButton searchFoodHistoryButton;
    @FXML
    Label historyLabel;
    @FXML
    JFXListView<String> historyFoodList;
    @FXML
    JFXButton removeButton;

    public void searchFoodHistory() {

        if (datePicker.getValue() == null) {
            historyLabel.setText("Select a date before searching");
            return;
        }

        String date = datePicker.getValue().toString();

        String month = date.substring(5, 7);
        String day = date.substring(8, 10);
        String year = date.substring(0, 4);

        String key = month + '-' + day + '-' + year;

        JSONObject foodHistoryLog = Log.searchFoodHistoryLog(key);
        historyFoodList.getItems().clear();
        removeButton.setVisible(false);

        // Search For Current Day Food
        if (key.equals(Log.getDate())) {

            if (!FoodObject.dailyFood.isEmpty()) {

                for (int i = 0; i < FoodObject.dailyFood.size(); i++) {
                    historyFoodList.getItems().add((i + 1) + ": " + FoodObject.dailyFood.get(i));
                }

                historyLabel.setText("Food History Log : " + key);
                removeButton.setVisible(true);
                return;

            } else {
                // Daily Foods consumed is empty
                historyLabel.setText("No food history yet today (" + key + "). Search and add food");
                return;
            }
        }

        // Search For Past Food History
        if (foodHistoryLog != null) {

            JSONArray foods = (JSONArray) foodHistoryLog.get("foods");

            if (!foods.isEmpty()) {

                for (int i = 0; i < foods.size(); i++) {
                    historyFoodList.getItems().add((i + 1) + ": " + foods.get(i).toString());
                }
                historyLabel.setText("Food History Log : " + key);

            } else {
                // Valid foodHistoryLog JSONObject but no foods listed in the foods array
                historyLabel.setText("No food history log for the day: " + key);
            }

        } else {
            // Null if no foodHistoryLog JSONObject was found
            historyLabel.setText("No food history log for the day: " + key);
        }


    }

    // Only removes food from the current day not days from the past
    public void removeFood() {

        // Clicked remove but haven't selected a food
        if (historyFoodList.getSelectionModel().isEmpty()) {
            historyLabel.setText("Select a food from the list before removing");
            return;
        }

        int index = historyFoodList.getSelectionModel().getSelectedIndex();

        // Search food from fdcID (might have to make new search method)
        // Remove nutritional values from daily values
        int foodID = FoodObject.dailyFoodIds.get(index);
        FoodData.getNutrition(foodID);

        // Remove food and id from both list
        FoodObject.dailyFood.remove(index);
        FoodObject.dailyFoodIds.remove(index);

        // refresh historyFoodList
        historyFoodList.getItems().clear();
        removeButton.setVisible(false);

        if (!FoodObject.dailyFood.isEmpty()) {

            for (int i = 0; i < FoodObject.dailyFood.size(); i++) {
                historyFoodList.getItems().add((i + 1) + ": " + FoodObject.dailyFood.get(i));
            }

            removeButton.setVisible(true);
            return;

        } else {
            // Daily Foods consumed is empty
            historyLabel.setText("");
            return;
        }

    }

    //=================================================================

    // SUMMARY SCENE VARIABLES ========================================

    // Display Contents of FoodObject.dailyNutrients array on summary scene

    @FXML
    ProgressBar caloriesBar;
    @FXML
    ProgressBar fatsBar;
    @FXML
    ProgressBar saturatedFatsBar;
    @FXML
    ProgressBar transFatBar;
    @FXML
    ProgressBar sodiumBar;
    @FXML
    ProgressBar fiberBar;
    @FXML
    ProgressBar carbsBar;
    @FXML
    ProgressBar sugarsBar;
    @FXML
    ProgressBar proteinBar;
    @FXML
    ProgressBar cholesterolBar;

    @FXML
    Label caloriesLabel;
    @FXML
    Label fatsLabel;
    @FXML
    Label saturatedFatsLabel;
    @FXML
    Label transFatsLabel;
    @FXML
    Label sodiumLabel;
    @FXML
    Label fiberLabel;
    @FXML
    Label carbsLabel;
    @FXML
    Label sugarsLabel;
    @FXML
    Label proteinLabel;
    @FXML
    Label cholesterolLabel;

    @FXML
    Button test;

    public void testfunction() {
        setSummaryValues();
    }

    public void setSummaryValues() {

        caloriesBar.setProgress(FoodObject.dailyNutrients[0] / 2500);
        caloriesLabel.setText(String.valueOf(FoodObject.dailyNutrients[0])+" kcal");

        fatsBar.setProgress(FoodObject.dailyNutrients[1] / 80);
        fatsLabel.setText(String.valueOf(FoodObject.dailyNutrients[1])+"g");

        saturatedFatsBar.setProgress(FoodObject.dailyNutrients[2] / 20);
        saturatedFatsLabel.setText(String.valueOf(FoodObject.dailyNutrients[2])+"g");

        transFatBar.setProgress(FoodObject.dailyNutrients[3] / 3);
        transFatsLabel.setText(String.valueOf(FoodObject.dailyNutrients[3])+"g");

        sodiumBar.setProgress(FoodObject.dailyNutrients[4] / 2500 / 2500);
        sodiumLabel.setText(String.valueOf(FoodObject.dailyNutrients[4])+"mg");

        fiberBar.setProgress(FoodObject.dailyNutrients[5] / 80);
        fiberLabel.setText(String.valueOf(FoodObject.dailyNutrients[5])+"g");

        carbsBar.setProgress(FoodObject.dailyNutrients[6] / 500);
        carbsLabel.setText(String.valueOf(FoodObject.dailyNutrients[6])+"g");

        sugarsBar.setProgress(FoodObject.dailyNutrients[7] / 80);
        sugarsLabel.setText(String.valueOf(FoodObject.dailyNutrients[7])+"g");

        proteinBar.setProgress(FoodObject.dailyNutrients[8] / 100);
        proteinLabel.setText(String.valueOf(FoodObject.dailyNutrients[8])+"g");

        cholesterolBar.setProgress(FoodObject.dailyNutrients[9] / 400);
        cholesterolLabel.setText(String.valueOf(FoodObject.dailyNutrients[9])+"mg");
    }


    //================================================================

}
