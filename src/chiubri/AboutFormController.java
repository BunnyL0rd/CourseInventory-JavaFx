////////////////////////////////////////////////////////////////////////////////
// AboutFormController.java
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class AboutFormController implements Initializable {

    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonClose(ActionEvent event) {
        
     if (stage != null)
         stage.close();
    }
    public void setStage (Stage stage){
        
        this.stage = stage;
    }
    
}
