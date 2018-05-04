////////////////////////////////////////////////////////////////////////////////
// AddFormController.java
// ============
//
//
// AUTHOR: Brian Chiu
// CREATED: 2018-04-05
// UPDATED: 2018-04-27
////////////////////////////////////////////////////////////////////////////////
package chiubri;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddFormController implements Initializable {
    //member var
    private Stage stage;
    private CourseInventoryModel model;
    @FXML
    private TextField textId;
    @FXML
    private TextField textTitle;
    @FXML
    private TextField textCredit;
    @FXML
    private ChoiceBox<String> comboCat;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
        public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setModel(CourseInventoryModel m) {
        
        this.model = m;
        populateCategories();
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) {
        Alert alert = new Alert(AlertType.ERROR);
        
        //information validation before adding to ArrayList
        //validate ID
        String id = textId.getText().toUpperCase();
        if (model.validateId(id) == false) {
            alert.setContentText("ID must be 4 alphabets followed by 5 digit");
            alert.show(); return; 
        }
        //validate Title
        String title = textTitle.getText();
        if (title == null || title.length() == 0) {
            alert.setContentText("Title cannot be empty");
            alert.show(); return;
        }
        
        //validate Credit
        int credit =0;
        //checks if textCredit is a numeric
        try {
            credit = Integer.parseInt(textCredit.getText());
            
        } catch (NumberFormatException e) {
            alert.setContentText("Credit must be numeric");
            alert.show(); return;
        }
        if (credit <= 0) {
            alert.setContentText("Credit must be greater than 0");
            alert.show(); return;
        }
        //Checks if a course has been selected
        String cat = comboCat.getValue();
        if (cat == null) {
            alert.setContentText("Please select a Category!");
            alert.show(); return;
        }
        
        //adds the course
        model.addCourse(id, title, credit, cat);
        model.setNewCourseId(id);
        if(stage != null)
            stage.close();
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        
        if(stage != null)
            stage.close();
    }
    
    private void populateCategories() {
        
        ArrayList <String> list = model.getCategories();
        comboCat.setItems(FXCollections.observableList(list));
    }
    
}
