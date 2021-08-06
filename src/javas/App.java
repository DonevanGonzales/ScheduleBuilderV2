package javas;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// javascript engine
import javax.script.*;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SLAC Schedule Builder");

        Parent root = FXMLLoader.load(getClass().getResource("MainContainer.fxml"));
        Scene scene = new Scene(root,1000,800);
        scene.getStylesheets().add("slac/components/stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");
        try {
            engine.eval("print('hello there');");
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    

    

}