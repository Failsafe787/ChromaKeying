import java.awt.image.BufferedImage;

public class ChromaKeyingVlahos {

	private BufferedImage f = null;
	private BufferedImage b = null;
	private int keycolor = 0;

	public ChromaKeyingVlahos(BufferedImage foreground, BufferedImage background, int keycolor)
			throws UnsupportedSizeException, UnsupportedColorspaceException {
		if (foreground == null || background == null) {
			throw new NullPointerException("There was an error while initializating this ChromaKeyingVlaho object");
		}
		if (foreground.getRaster().getNumBands() != 3) {
			throw new UnsupportedColorspaceException("The images must be in RGB format");
		}
		if (foreground.getHeight() != background.getHeight() || foreground.getWidth() != background.getWidth()) {
			throw new UnsupportedSizeException("The images must be of the same size");
		}
		f = foreground;
		b = background;
		this.keycolor = keycolor;
	}

	public BufferedImage replace() {
		BufferedImage c = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < f.getWidth(); x++) {
			for (int y = 0; y < f.getHeight(); y++) {
				double alpha = 1 - (f.getRaster().getSample(x, y, keycolor)
						- Math.max(f.getRaster().getSample(x, y, 0), f.getRaster().getSample(x, y, 3 - keycolor)))
						/ 255.0;
				for (int level = 0; level < f.getRaster().getNumBands(); level++) {
					double value = alpha * f.getRaster().getSample(x, y, level)
							+ (1 - alpha) * b.getRaster().getSample(x, y, level);
					c.getRaster().setSample(x, y, level, ImageUtils.clamp(value));
				}
			}
		}
		return c;
	}
}
