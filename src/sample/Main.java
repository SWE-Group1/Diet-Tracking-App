package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SearchScene.fxml"));
        primaryStage.setTitle("Diet Tracking App");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();

        SceneController.setSceneDetails(primaryStage.getScene());
    }


    public static void main(String[] args) {
        // Read FoodHistoryLog
        System.out.println("Program starting, reading data from FoodHistoryLog");
        Log.read();


        launch(args);
    }

    @Override
    public void stop() {
        System.out.println("Stage is closing, Saving food history to FoodHistoryLog");

        // Save FoodHistoryLog
        Log.save();

    }
}
