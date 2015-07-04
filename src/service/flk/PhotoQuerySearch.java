package service.flk;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.PageModel;
import model.Product;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import commons.Constants;
import commons.Tools;


/**
 * a singleton of the request for flickr panda service using REST style
 * @author barry
 * @version 1.0
 * @since 29/05/2015
 */
public class PhotoQuerySearch {
	
	private static DocumentBuilder db;

	private static class PhotoQuerySearchHolder{
		private static final PhotoQuerySearch INSTANCE=new PhotoQuerySearch();
		
	}
	
	private PhotoQuerySearch(){
		try {
			db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
	}}
	
	public static PhotoQuerySearch getInstance(){
		return PhotoQuerySearchHolder.INSTANCE;
	}
	
	/**
	 * search photos with key words with desired page number
	 * @param keywords - keywords would be sent to flikr in the 'tags' field
	 * @return Products - list of photos from flickr
	 * @see Products
	 */
	public PageModel<Product> getProducts(String keywords,String pageNumber){		
		HttpURLConnection urlConnection=null;InputStream urlStream=null;
		try{
			String callUrlStr = Constants.REST_ENDPOINT+"?method="+Constants.METHODSEARCH+
			"&format=rest"+"&per_page="+Constants.DEFAULT_NUMBER+"&page="+pageNumber+"&api_key="+Constants.API_KEY+"&extras=tags,url_sq&tags="+keywords;
			System.out.println(callUrlStr);
			URL callUrl = new URL(callUrlStr);			
			urlConnection = (HttpURLConnection)callUrl.openConnection();
			urlStream = urlConnection.getInputStream();
			Document response = db.parse(urlStream);
			NodeList nl=response.getElementsByTagName("rsp");
			String state=nl.item(0).getAttributes().getNamedItem("stat").getTextContent();
			if (!"ok".equals(state)){return new PageModel<Product>(0,0,null);}
			nl=response.getElementsByTagName("photos");
			String rPages=nl.item(0).getAttributes().getNamedItem("pages").getTextContent();
			int totalPage=0;int tPages=Integer.parseInt(rPages); 
			if (tPages<Constants.maxPage){totalPage=tPages;}
			else{totalPage=Constants.maxPage;}
			int currentPage=Integer.parseInt(nl.item(0).getAttributes().getNamedItem("page").getTextContent());
			nl = response.getElementsByTagName("photo");
			int total=nl.getLength();
			if (total==0){return null;}
			List<Product> result=new ArrayList<Product>();
			String[] tagsl=null;
			for (int i = 0; i < total; i ++){
				NamedNodeMap temp=nl.item(i).getAttributes();
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
			return new PageModel<Product>(totalPage,currentPage,result);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if (urlStream!=null){
				try {
					urlStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (urlConnection!=null){
				urlConnection.disconnect();
			}
		}
		return new PageModel<Product>(0,0,null);
	}

	
}
