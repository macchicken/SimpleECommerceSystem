package commons;
/**
 * Constants value in the system
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public interface Constants {
	/**
	 * api key of communicating with Flickr
	 */
	public static final String API_KEY = "8d3fb09eae35f2c1e056e7aee9a9cf84";
	/**
	 * a photo search api of Flickr
	 */
	public static final String METHODSEARCH = "flickr.photos.search";
	public static final String REST_ENDPOINT = "https://api.flickr.com/services/rest/";
	public static final String SOAP_ENDPOINT="https://api.flickr.com/services/soap/";
	/**
	 * a number of photo to be retrieve from Flickr
	 */
	public static final String DEFAULT_NUMBER="10";
	/**
	 * encoding scheme
	 */
	public static final String ENC = "UTF-8";
	public static final String SOAP2 = "soap2";
	/**
	 * unit price of a tag of a photo
	 */
	public static final double unitPrice=1;
	/**
	 * base price of a photo
	 */
	public static final double basePrice=2;
	
	/**
	 * maximum page of retrieving photos from Flickr
	 */
	public static final int maxPage=10;

}
