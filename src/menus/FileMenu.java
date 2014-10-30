package menus;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import control.Controller;

/**
 * FileMenu provides two options:
 * 1) Finish Driving - when the driver is done with the driving session.
 * 2) Ride Completed - when the driver completes a ride.
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
        myFinishDrivingItem.setOnAction(event -> control.finishDriving());
        
        myEndRideItem = new MenuItem("Ride Completed");
        myEndRideItem.setDisable(true);
        myEndRideItem.setOnAction(event -> control.endRide()); 
        
        this.getItems().addAll(myFinishDrivingItem, myEndRideItem);        
    }
    
    /**
     * Enables the FinishDriving item.
     */
    protected void enableFinishDrivingItem(){
    	myFinishDrivingItem.setDisable(false);
    }
    
    /**
     * Enables the EndRide item.
     */
    protected void enableEndRideItem(){
    	myEndRideItem.setDisable(false);
    }    
}