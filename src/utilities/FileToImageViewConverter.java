package utilities;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The purpose of this class is to allow the user to get an image view from a string. This
 * will be used throughout AuthoringEnvironment and may also appear in the GameEngine and 
 * front-end Player
 * @author Austin Kyker
 *
 */
public class FileToImageViewConverter {

	public static ImageView getImageView(double width, double height, InputStream stream) {
		ImageView imgView = new ImageView();
		Image image;
		image = new Image(stream, width, height, false, false);
		imgView.setImage(image);
		imgView.setFitWidth(width);
		imgView.setFitHeight(height);
		return imgView;
	}
}
