package menus;

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
	
	private MenuItem myEndRideItem;
	private MenuItem myFinishDrivingItem;
	
    public FileMenu(Controller control){
        this.setText("File");
        
        myFinishDrivingItem = new MenuItem("Finished Driving");
        myFinishDrivingItem.setDisable(true);
        myFinishDrivingItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	control.finishDriving();
            }
        });   
        
        myEndRideItem = new MenuItem("Ride Completed");
        myEndRideItem.setDisable(true);
        myEndRideItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	control.endRide();
            }
        });    
        
        
        this.getItems().addAll(myFinishDrivingItem, myEndRideItem);        
    }
    
    protected void enableFinishDrivingItem(){
    	myFinishDrivingItem.setDisable(false);
    }
    
    protected void enableEndRideItem(){
    	myEndRideItem.setDisable(false);
    }
    
    
}
