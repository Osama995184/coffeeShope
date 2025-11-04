package CafeShopSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Osama
 */
public class CafeShopSystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("CafeShop POS System");
        stage.setMinHeight(450);
        stage.setMinWidth(618);
        stage.setScene(scene);
        stage.show();

//        startServices();
//        startAutoBackup();
    }

    @Override
    public void stop() {
//        stopServices();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
