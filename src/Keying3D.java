import java.awt.image.BufferedImage;

public class Keying3D {

	private BufferedImage f = null;
	private BufferedImage b = null;
	private int keycolor = 0;
	private double r1 = 0.0;
	private double r2 = 0.0;
	private int[] blue = { 0, 0, 255 };
	private int[] green = { 0, 255, 0 };

	public Keying3D(BufferedImage foreground, BufferedImage background, int keycolor, double radius1, double radius2)
			throws UnsupportedSizeException, UnsupportedColorspaceException, IllegalRadiusSizeException {
		if (foreground == null || background == null) {
			throw new NullPointerException("There was an error while initializating this Keying3D object");
		}
		if (foreground.getRaster().getNumBands() != 3) {
			throw new UnsupportedColorspaceException("The images must be in RGB format");
		}
		if (foreground.getHeight() != background.getHeight() || foreground.getWidth() != background.getWidth()) {
			throw new UnsupportedSizeException("The images must be of the same size");
		}
		if (radius1 >= radius2) {
			throw new IllegalRadiusSizeException("radius1 must be lower than radius2");
		}
		f = foreground;
		b = background;
		this.keycolor = keycolor;
		r1 = radius1;
		r2 = radius2;
	}

	public BufferedImage replace() {
		BufferedImage c = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_RGB);
		int kcolor[];
		if (keycolor == 1) {
			kcolor = green;
		} else {
			kcolor = blue;
		}
		for (int x = 0; x < f.getWidth(); x++) {
			for (int y = 0; y < f.getHeight(); y++) {
				double alpha = alphaCircles(f.getRaster().getSample(x, y, 0), f.getRaster().getSample(x, y, 1),
						f.getRaster().getSample(x, y, 2), (double) kcolor[0], (double) kcolor[1], (double) kcolor[2]);
				for (int level = 0; level < f.getRaster().getNumBands(); level++) {
					double value = alpha * f.getRaster().getSample(x, y, level)
							+ (1 - alpha) * b.getRaster().getSample(x, y, level);
					c.getRaster().setSample(x, y, level, ImageUtils.clamp(value));
				}
			}
		}
		return c;
	}

	private double alphaCircles(double red, double green, double blue, double kred, double kgreen, double kblue) {
		if ((red - kred)*(red- kred) + (green - kgreen)*(green - kgreen) + (blue - kblue)*(blue - kblue) <= r1*r1) {
			return 0.0;
		} else if ((red - kred)*(red- kred) + (green - kgreen)*(green - kgreen) + (blue - kblue)*(blue - kblue) > r2*r2) {
			return 1.0;
		} else {
			return (Math.sqrt((red - kred)*(red- kred) + (green - kgreen)*(green - kgreen) + (blue - kblue)*(blue - kblue)) - r1)
					/ (r2 - r1);
		}
	}
}
