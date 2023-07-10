package com.TBmail.EmailService;

import java.util.ArrayList;

public class Data {
	private ArrayList<String> tags;
	private String eMail;
	private String lastSent;
	//private String date;
	
	
	
	public Data(ArrayList<String> tags, String eMail, String lastSent) {
		this.tags = tags;
		this.eMail = eMail;
		this.lastSent = lastSent;
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getLastSent() {
		return lastSent;
	}
	public void setLastSent(String lastSent) {
		this.lastSent = lastSent;
	}

	@Override
	public String toString() {
		return "Data [tags=" + tags + ", eMail=" + eMail + ", lastSent=" + lastSent + "]";
	}
	
	
}

