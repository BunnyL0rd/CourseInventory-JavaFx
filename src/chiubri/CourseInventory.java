////////////////////////////////////////////////////////////////////////////////
// CourseInevntory.java
// ============
// 
//
// AUTHOR: Brian Chiu
// CREATED: 2018-04-05
// UPDATED: 2018-04-27
////////////////////////////////////////////////////////////////////////////////
package chiubri;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CourseInventory extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        //create an image 
        Image appIcon = new Image(getClass().getResourceAsStream("book-icon.png"));

        //set image to window
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.setTitle("Course Inventory by: Brian Chiu v1.0.0");
        
        
        //assgin stage to the controller
        MainFormController controller = (MainFormController) loader.getController();
        controller.setStage(stage);
        
        //show window
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
