package menus;

import control.Controller;
import javafx.scene.control.MenuBar;

/**
 * The MenuBar which right now only contains the FileMenu.
 * @author Austin Kyker
 *
 */
public class MenuFeature extends MenuBar {
	
	private FileMenu myFileMenu;
	
	public MenuFeature(Controller controller){
		myFileMenu = new FileMenu(controller);
		this.getMenus().add(myFileMenu);
	}
	
	/**
	 * Enables the FinishDrivingItem in the final menu. This item
	 * is enabled in the StartRide and Not Enough Videos scenes
	 */
    public void enableFinishDrivingItem(){
    	myFileMenu.enableFinishDrivingItem();
    }
    
	/**
	 * Enables the EndRideItem in the final menu. This item is
	 * enabled in the VideoScene.
	 */
    public void enableEndRideItem(){
    	myFileMenu.enableEndRideItem();
    }
}
