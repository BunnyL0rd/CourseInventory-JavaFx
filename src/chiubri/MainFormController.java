////////////////////////////////////////////////////////////////////////////////
// MainFormController.java
// ============
//
//
// AUTHOR: Brian Chiu
// CREATED: 2018-04-05
// UPDATED: 2018-04-27
////////////////////////////////////////////////////////////////////////////////
package chiubri;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainFormController implements Initializable {
    
    //member vars
    private Stage stage; //remember its stage reference here
    private CourseInventoryModel model; //model part of MVC
    private File file;
    
    @FXML
    private ListView<Course> listviewCourses;
    @FXML
    private TextField textTitle;
    @FXML
    private TextField textCredit;
    @FXML
    private GridPane paneEdit;
    @FXML
    private ChoiceBox<String> comboCat;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //create model object 
        model = new CourseInventoryModel();
        
        paneEdit.setDisable(true);
        populateCategories();
        //register changelistener for listview
        listviewCourses.getSelectionModel().selectedItemProperty()
                .addListener((ob, oldV, newV)->{
        
                    showCourseInfo(newV);    
        });
        
        //customize listview
        listviewCourses.setCellFactory((listview) -> {
            //create a custom ListCell<>
            ListCell<Course> cell = new ListCell<Course>(){
                
                //override updateItem()
                public void updateItem(Course c, boolean empty){
                    super.updateItem(c, empty);
                    //this.setText("abc");
                    if(empty || c == null) {
                        
                        this.setText(null);
                        this.setGraphic(null);
                    } else
                        this.setText(c.getId());
                }
            };
            return cell;
        });
    } ////end of initialize   
    
    //remember stage reference 
    public void setStage (Stage stage) {
        //pops up and comfirmation page to confirm if they want to quit
        this.stage = stage;
        stage.setOnCloseRequest(e -> {
            Alert alert = new Alert (AlertType.CONFIRMATION, 
                    "Do you want to Quit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                //user click OK here, exit program
                Platform.exit(); //not needed
            else
                e.consume();
        });
    }
    //ABOUT WINDOW
    @FXML
    private void handleMenuAbout(ActionEvent event) throws Exception{
        
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("AboutForm.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
     
        //icon
        Image appIcon = new Image(getClass()
                .getResourceAsStream("book-icon.png"));

        //set image to window
        stage.getIcons().add(appIcon);
        stage.setTitle("About");
        stage.setScene(scene);
        stage.show();
        
        AboutFormController ctrlAbout = loader.getController();
        ctrlAbout.setStage(stage);
    }
    //OPENS A DAT FILE
    @FXML
    private void handleMenuOpen(ActionEvent event) {
        
        //create fhileChooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Course List File...");
        
         //displays files with these extensions 
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("All Files", "*.*"),
                new ExtensionFilter("DAT", "*.dat"), 
                new ExtensionFilter("TXT", ".txt") );
        
        File file = fileChooser.showOpenDialog(stage);
        
        //open file
        model.readCourseFile(file);
        this.file = file;
        //display courses to the list view
        if(model.getCourseCount() > 0) {
            //populate courses here
            poplulateCourses(null);  
        } 
    }
    
    private void poplulateCourses(String cat) {
       listviewCourses.setItems(FXCollections
               .observableList(model.getCourses()));
    }
    
    @FXML
    private void handleMenuSave(ActionEvent event) {
        
        //save file to the file that is loaded
        model.saveCourseFile(file);
    }

    //saves as a new file
    @FXML
    private void handleMenuSaveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Course List File As...");
        
        //displays files with these extensions
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("All Files", "*.*"),
                new ExtensionFilter("DAT", "*.dat"), 
                new ExtensionFilter("TXT", ".txt") );
        File file = fileChooser.showSaveDialog(stage);
        if (file == null)
            return;
        model.saveCourseFile(file);
    }
    //closes window
    @FXML
    private void handleMenuClose(ActionEvent event) {
        
            Alert alert = new Alert (AlertType.CONFIRMATION, 
                    "Do you want to Quit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                //user click OK here, exit program
                Platform.exit(); 
    }
    
    //deletes a course
    @FXML
    private void handleButtonDelete(ActionEvent event) {
        
        Course course = listviewCourses.getSelectionModel().getSelectedItem();
        if (course == null)
            return;
        Alert alert = new Alert (AlertType.CONFIRMATION,
        "Do you want to delete " + course.getId() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            model.removeCourse(course);
            model.saveCourseFile(file);
            
            //reload and update the listview
            poplulateCourses(null);
            listviewCourses.getSelectionModel().clearSelection();
        }
        
    }
    //OPENS A SEARCH WINDOW
    @FXML
    private void handleButtonSearch(ActionEvent event) throws IOException {
        
        //create SearchForm stage and open
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("SearchForm.fxml"));
        Parent root = (Parent) loader.load();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Search");
        
        //icon
        Image appIcon = new Image(getClass()
                .getResourceAsStream("book-icon.png"));

        //set image to window
        stage.getIcons().add(appIcon);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        
        SearchFormController controller = loader.getController();
        controller.setStage(stage);
        controller.setModel(model);
        stage.showAndWait();
         
        Course c = model.getSelectedCourse();
        int index = model.getSelectedCourseIndex();
        if (c != null) { // selected
            //programmatically select an item in the listview
            listviewCourses.getSelectionModel().select(c);
            //make if focus
            listviewCourses.getFocusModel().focus(index);
            listviewCourses.scrollTo(index);
            
        } else {    // no selection
            listviewCourses.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("AddForm.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Course");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        
        //icon
        Image appIcon = new Image(getClass()
                .getResourceAsStream("book-icon.png"));

        //set image to window
        stage.getIcons().add(appIcon);
        AddFormController controller = loader.getController();
        controller.setStage(stage);
        controller.setModel(model);
        stage.showAndWait();
        model.saveCourseFile(file);
        
        String id = model.getNewCourseId();
        int index = model.getCourseIndex(id);
        
        if(id == null) return;
             listviewCourses.setItems(FXCollections
                     .observableArrayList(model.getCourses()));
            listviewCourses.getSelectionModel().clearSelection();
       
       //selects the newly added course and brings information 
       //to the user in main window
       listviewCourses.getSelectionModel().select(index);
       listviewCourses.getFocusModel().focus(index);
       listviewCourses.scrollTo(index);  
    }
    
    //saves updated info
    @FXML
    private void handleButtonSave(ActionEvent event) {
        
        Course c = listviewCourses.getSelectionModel().getSelectedItem();
        if (c == null)
            return;
        Alert alert1 = new Alert(AlertType.CONFIRMATION, 
                "Do you want to save the changes?");
        
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() != ButtonType.OK) {
            return;
        }
        //ERROR CHECK IF ANY INPUTS IS INVALID 
        Alert alert2 = new Alert (AlertType.ERROR);
        //TITLE
        String title = textTitle.getText();
        if (title == null || title.length() == 0) {
            alert2.setContentText("Title cannot be empty!");
            alert2.show();return;
        }
        //CREDIT
        int credit;
        try {
            credit = Integer.parseInt(textCredit.getText());
        } catch (NumberFormatException e) {
            
            alert2.setContentText("Credit must be an integer!");
            alert2.show(); return;
        }
        if(credit < 0) {
           alert2.setContentText("Credit must be an integer!");
           alert2.show(); return;
        }
        //CATEGORY
        String cat = comboCat.getValue();
        if (cat == null) {
            alert2.setContentText("Category must be selected!");
            alert2.show(); return;
        }
        //updates arraylist in model and saves the file
        model.updateCourse(c.getId(), title, credit, cat);
        model.saveCourseFile(file);
        paneEdit.setDisable(true);
    }
    
    @FXML
    private void handleButtonEdit(ActionEvent event) {
        //ENABLES THE FEILDS
        paneEdit.setDisable(false);
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        //DISABLES THE FEILDS
        Course id = listviewCourses.getSelectionModel().getSelectedItem();
        listviewCourses.getSelectionModel().select(id);
        paneEdit.setDisable(true);
        
    }
    
    //adds all categories to the combobox
    private void populateCategories() {
        //populate listview
        ArrayList <String> list = model.getCategories();
        comboCat.setItems(FXCollections.observableList(list));
    }
    
    //displays course info when selected on the listview 
    private void showCourseInfo(Course course){
    
        if(course == null) return;
        textTitle.setText(course.getTitle());
        textCredit.setText(String.valueOf(course.getCredit()));
        comboCat.getSelectionModel().select(course.getCategory());
    }

   
    
}
