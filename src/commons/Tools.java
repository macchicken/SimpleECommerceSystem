package commons;

public class Tools {

	public static String transferImgUrl(String url){
		int lasti=url.lastIndexOf(".");
		int length=url.length();
		return url.substring(0, lasti-2)+url.substring(lasti, length);
	}
}
