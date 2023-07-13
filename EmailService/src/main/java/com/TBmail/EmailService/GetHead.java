package com.TBmail.EmailService;

public class GetHead {
	public static String getHead(String page) {
		String head=new String();
        int start=page.indexOf("<h4><strong>"); 
        for(int i=start+12; i<page.indexOf("</strong></h4>"); i++)
        {
        	head+=(page.charAt(i));
        }
        head+="\n";
        head+="\n";
        return head;
	}
}
