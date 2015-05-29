package commons;

/**
 * helper function in this sytem
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public class Tools {

	/**
	 * return an ulr of original image from an url of small size image
	 * @param url
	 * @return
	 */
	public static String transferImgUrl(String url){
		int lasti=url.lastIndexOf(".");
		int length=url.length();
		return url.substring(0, lasti-2)+url.substring(lasti, length);
	}
}
