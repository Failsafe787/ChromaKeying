import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import jbook.util.Input;

public class _AlphaBlendingTest {
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
		AlphaBlending myAlphaBlending = null;
		try {
			myAlphaBlending = new AlphaBlending(originalImage, Config.alphablend_alphaf, originalImage2, Config.alphablend_alphab);
		} catch (UnsupportedSizeException e) {
			System.out.println(e.getMessage());
		}
		processedImage = myAlphaBlending.blend();
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
		int i = 1;
		for (BufferedImage image : images) {
			JLabel label = new JLabel(new ImageIcon(image));
			String windowText = "Image" + (i++) + " (" + image.getWidth() + "x" + image.getHeight() + ")";
			JFrame f = new JFrame(windowText);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(label);
			f.pack();
			f.setLocation(20, 20);
			f.setVisible(true);
		}
	}
}
