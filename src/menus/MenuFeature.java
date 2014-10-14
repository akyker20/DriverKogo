package menus;

import control.Controller;
import javafx.scene.control.MenuBar;

public class MenuFeature extends MenuBar {
	public MenuFeature(Controller controller){
		this.getMenus().add(new FileMenu(controller));
	}
}
