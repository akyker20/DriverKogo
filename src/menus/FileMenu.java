package menus;

import javax.xml.transform.TransformerException;

import control.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Class will be used to load files such as previously saved commands or
 * grid configurations.
 * @author Austin Kyker
 *
 */
public class FileMenu extends Menu {
	
    public FileMenu(Controller control){
        this.setText("File");
        
        MenuItem finishDriving = new MenuItem("Finished Driving");
        finishDriving.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	control.finishDriving();
            }
        });     
        this.getItems().add(finishDriving);        
    }
}
