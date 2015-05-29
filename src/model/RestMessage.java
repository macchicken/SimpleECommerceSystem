package model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A message data transfer object to be used for communicating with web service
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
@XmlRootElement
public class RestMessage {

	/**
	 * resulting status of one calling a web service 
	 */
	private String status;
	/**
	 * returns of a web service
	 */
	private Map<String,Object> result;

	public String getStatus() {
		return status;
	}
	public RestMessage() {
		super();
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, Object> getResult() {
		return result;
	}
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Message [status=" + status + ", result=" + result + "]";
	}
	
	
}
