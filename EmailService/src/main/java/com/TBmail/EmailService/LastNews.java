package com.TBmail.EmailService;

public class LastNews {
	public static String getLastNewsTitle(String url) {
		String page=MailContent.getHtml(url);
		int start=page.indexOf("tle\"><a href="); //<h3 class=\"entry-title td-module-title\"><a href=
		int next=page.indexOf("title=\"",start);
		String title=new String();
		for(int i=next+7; i<page.indexOf("\">",next); i++) {
			title+=page.charAt(i);
		}
		return title;
	}
	
	
	/*public static String getLastNewsTime(String url) {
		String page=MailContent.getHtml(url);
		int start=page.indexOf("time=\"");
		
		String timeStr=new String();
		for(int i=start+6; i<page.indexOf("+03:00",start); i++) {
			timeStr+=page.charAt(i);
		}
		return timeStr;
	}
	
	protected static String getLastNewsTimeFromPage(String page) {
		
		int start=page.indexOf("time=\"");
		
		String timeStr=new String();
		for(int i=start+6; i<page.indexOf("+03:00",start); i++) {
			timeStr+=page.charAt(i);
		}
		return timeStr;
	}
	
	public static boolean compareToSend(String time1, String time2) {
		if(time1.equals(time2))
			return false;
		return true;
		
	}
	
	
	
	*/
	
	
	private static int index=0; 
	public static String getNewsUrl(String page) {
		int start=page.indexOf("b\"><a href=\"",index);
		String url=new String();
		for(int i=start+12; i<page.indexOf("\"", start+20); i++) {
			url+=page.charAt(i);
			
		}
		index=start+1;
		return url;
	}
	public static void resetIndex() {
		index=0;
	}
	
	
	
	
	
	
}
