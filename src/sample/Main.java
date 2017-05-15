package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.util.DBUtil;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        connect();

        Parent root = FXMLLoader.load(getClass().getResource("view/root.fxml"));
        primaryStage.setTitle("AliExpress");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    private void connect() {
        DBUtil.connect();
        try {
            DBUtil.deleteSchema();
            DBUtil.createSchema();
            DBUtil.insertDefault();
        }
        catch (Exception e) {
            System.out.println("База данных уже существует!");
        }
    }
}
