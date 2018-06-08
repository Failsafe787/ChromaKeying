import java.awt.image.BufferedImage;

public class AlphaBlending {

	BufferedImage f = null;
	BufferedImage b = null;
	double alphaf = 0.0;
	double alphab = 0.0;

	public AlphaBlending(BufferedImage foreground, double alphaf, BufferedImage background, double alphab)
			throws UnsupportedSizeException {
		if (foreground.getHeight() != background.getHeight() || foreground.getWidth() != background.getWidth()) {
			throw new UnsupportedSizeException("The images must be of the same size");
		}
		f = foreground;
		this.alphaf = alphaf;
		b = background;
		this.alphab = alphab;
	}

	public BufferedImage blend() {
		if (f == null || b == null) {
			throw new NullPointerException("There was an error while initializating this AlphaBlending object");
		}
		BufferedImage c = new BufferedImage(f.getWidth(), f.getHeight(), f.getType());
		double alphafrec = 1 - alphaf;
		double denominator = alphaf + alphafrec * alphab;
		for (int level = 0; level < f.getRaster().getNumBands(); level++) {
			for (int x = 0; x < f.getWidth(); x++) {
				for (int y = 0; y < f.getHeight(); y++) {
					double numerator = alphaf * f.getRaster().getSample(x, y, level)
							+ alphafrec * alphab * b.getRaster().getSample(x, y, level);
					c.getRaster().setSample(x, y, level, numerator / denominator);
				}
			}
		}
		return c;
	}
}
