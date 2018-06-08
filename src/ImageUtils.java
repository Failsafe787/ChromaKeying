import java.awt.image.BufferedImage;

public class ImageUtils {

	public static double clamp(double value) {
		if (value > 255) {
			return 255;
		} else if (value < 0) {
			return 0;
		} else
			return value;
	}

	public static double clampalpha(double value) {
		if (value > 1) {
			return 1;
		} else if (value < 0) {
			return 0;
		} else
			return value;
	}

	public static BufferedImage toRGB(BufferedImage img) {
		BufferedImage convertedImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		convertedImg.getGraphics().drawImage(img, 0, 0, null);
		return convertedImg;
	}
}
