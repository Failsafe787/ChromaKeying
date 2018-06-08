import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import jbook.util.Input;

public class _ChromaKeyingVlahosTest {
	public static void main(String[] args) {
		BufferedImage originalImage = null, originalImage2 = null, processedImage = null;
		boolean loaded = false;
		while(!loaded){
			try {
				String path1 = Input.readString("Path of the first image: ");
				originalImage = ImageIO.read(new File(path1));
				String path2 = Input.readString("Path of the second image: ");
				originalImage2 = ImageIO.read(new File(path2));
				loaded = true;
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		ChromaKeyingVlahos myChromaKeyingVlaho = null;
		try {
			myChromaKeyingVlaho = new ChromaKeyingVlahos(originalImage, originalImage2,
					Config.vlahos_screencolor);
		} catch (UnsupportedSizeException e) {
			System.out.println(e.getMessage());
		} catch (UnsupportedColorspaceException e2) {
			System.out.println(e2.getMessage());
		}
		processedImage = myChromaKeyingVlaho.replace();
		showImage(processedImage, originalImage, originalImage2);
		String question = Input.readString("Directory where you want to save the output (leave blank to ignore): ");
		if(!question.equals("")){
			File outputfile = new File(question + "/image.jpg");
			try {
				ImageIO.write(processedImage, "jpg", outputfile);
				System.out.println("Your file has been saved to " + question + "/image.jpg");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void showImage(BufferedImage... images) {
		for (BufferedImage image : images) {
			JLabel label = new JLabel(new ImageIcon(image));
			JFrame f = new JFrame("Image (" + image.getWidth() + "x" + image.getHeight() + ")");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(label);
			f.pack();
			f.setLocation(20, 20);
			f.setVisible(true);
		}
	}
}
