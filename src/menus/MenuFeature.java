package menus;

import control.Controller;
import javafx.scene.control.MenuBar;

public class MenuFeature extends MenuBar {
	
	private FileMenu myFileMenu;
	
	public MenuFeature(Controller controller){
		myFileMenu = new FileMenu(controller);
		this.getMenus().add(myFileMenu);
	}
	
    public void enableFinishDrivingItem(){
    	myFileMenu.enableFinishDrivingItem();
    }
    
    public void enableEndRideItem(){
    	myFileMenu.enableEndRideItem();
    }
}
