////////////////////////////////////////////////////////////////////////////////
// SearchFormController.java
// ============
//
//
// AUTHOR: Brian Chiu
// CREATED: 2018-04-05
// UPDATED: 2018-04-27
////////////////////////////////////////////////////////////////////////////////
package chiubri;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class SearchFormController implements Initializable {
    
    
    //member vars
    private CourseInventoryModel model;
    private Stage stage;
    
    @FXML
    private TextField textSearch;
    @FXML
    private TableView<Course> tableCourses;
    @FXML
    private TableColumn<Course, String> colId;
    @FXML
    private TableColumn<Course, String> colTitle;
    @FXML
    private TableColumn<Course, Integer> colCredit;
    @FXML
    private TableColumn<Course, String> colCategory;

    /**
     @FXML
    
    * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        //customize tableview
        colId.setCellValueFactory(new PropertyValueFactory<Course, String> ("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Course, String> ("title"));
        colCredit.setCellValueFactory(new PropertyValueFactory<Course, Integer> ("credit"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Course, String> ("category"));
 
    }    

    @FXML
    private void handleSearch(ActionEvent event) {
        //shows the Courses that match the user's search
        tableCourses.setItems(FXCollections.observableList(model.findCoursesById(textSearch.getText())));
    }

    @FXML
    private void handleSelect(ActionEvent event) {
        
        //remember the selected course
        model.setSelectedCourse(tableCourses.getSelectionModel().getSelectedItem());
        if (stage != null)
            stage.close();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        //returns to the main window
        model.setSelectedCourse(null);
        tableCourses.getItems().clear();
        //clsoe window
        stage.close();
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setModel(CourseInventoryModel m) {
        
        this.model = m;
    }
}
