package flk;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.Product;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import commons.Constants;
import commons.Tools;


/**
 * This showcase the request for flickr panda service using REST style
 * @author barry
 *
 */
public class PhotoQuerySearch {
	
	private static class PhotoQuerySearchHolder{
		private static final PhotoQuerySearch INSTANCE=new PhotoQuerySearch();
	}
	
	private PhotoQuerySearch(){}
	
	public static PhotoQuerySearch getInstance(){
		return PhotoQuerySearchHolder.INSTANCE;
	}
	
	public List<Product> getProducts(String keywords){		
		HttpURLConnection urlConnection=null;
		try{
			String callUrlStr = Constants.REST_ENDPOINT+"?method="+Constants.METHODSEARCH+
			"&format=rest"+"&per_page="+Constants.DEFAULT_NUMBER+"&api_key="+Constants.API_KEY+"&extras=tags,url_sq&tags="+keywords;
			System.out.println(callUrlStr);
			URL callUrl = new URL(callUrlStr);			
			urlConnection = (HttpURLConnection)callUrl.openConnection();
			InputStream urlStream = urlConnection.getInputStream();
		
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document response = db.parse(urlStream);
			NodeList nl = response.getElementsByTagName("photo");
			int total=nl.getLength();
			if (total==0){return null;}
			List<Product> result=new ArrayList<Product>();
			NamedNodeMap temp;
			String[] tagsl=null;
			for (int i = 0; i < total; i ++){
				temp=nl.item(i).getAttributes();
				String title=temp.getNamedItem("title").getTextContent();
				String tags=temp.getNamedItem("tags").getTextContent();
				String urlSq=temp.getNamedItem("url_sq").getTextContent();
				String poid=temp.getNamedItem("id").getTextContent();
				tagsl=tags.split(" ");
				String urlO=Tools.transferImgUrl(urlSq);
				Product t=new Product(poid,title,tags,urlSq,tagsl.length*Constants.unitPrice+Constants.basePrice);
				t.setOriginImg(urlO);
				result.add(t);
			}
			return result;
		}catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			if (urlConnection!=null){
				urlConnection.disconnect();
			}
		}
		return null;
	}

	
}
