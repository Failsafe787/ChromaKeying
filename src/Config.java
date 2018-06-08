
public class Config {

	// AlphaBlending Parameters
	public static double alphablend_alphaf = 0.3;
	public static double alphablend_alphab = 1 - alphablend_alphaf;
	
	// Symbolic values association
	public static int KEYCOLOR_BLUE = 2;
	public static int KEYCOLOR_GREEN = 1;
	
	// Vlahos Parameters
	public static int vlahos_screencolor = KEYCOLOR_GREEN; //or ChromaKeyingVlahos.KEYCOLOR_BLUE
	
	// Primatte Parameters
	public static int primatte_keycolor = KEYCOLOR_BLUE;
	public static double primatte_r1 = 170.0;
	public static double primatte_r2 = 200.0;
}
