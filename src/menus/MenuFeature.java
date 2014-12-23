package menus;

import control.Controller;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * The MenuBar which right now only contains the FileMenu.
 * @author Austin Kyker
 *
 */
public class MenuFeature extends MenuBar {
	
	private static final String FINISHED_DRIVING = "Finished Driving";
	
	private MenuItem myEndRideItem;
	private MenuItem myFinishDrivingItem;
	
	public MenuFeature(Controller control){
		
		Menu fileMenu = new Menu("File");
        
        myFinishDrivingItem = new MenuItem(FINISHED_DRIVING);
        myFinishDrivingItem.setOnAction(event -> control.finishDriving());
        
        fileMenu.getItems().addAll(myFinishDrivingItem, myEndRideItem);   
		this.getMenus().add(fileMenu);
		
	}
    
    public void configureFinishDrivingMenu(){
    	myFinishDrivingItem.setDisable(false);
    	myEndRideItem.setDisable(true);
    }
}