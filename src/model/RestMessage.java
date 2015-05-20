package model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestMessage {

	private String status;
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
