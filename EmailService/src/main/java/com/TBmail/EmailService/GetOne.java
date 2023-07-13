package com.TBmail.EmailService;

public class GetOne {
	
	public static String get(String page,int start) {
		String news=new String();
 
        for(int i=start+3; i<page.indexOf("</p>",start) && i<page.indexOf("<!--",start); i++) {
        	if(page.charAt(i)=='<' && page.charAt(i+1)=='a') {  //<a
        		while(page.charAt(i)!='"' ||  page.charAt(i+1)!='>')
        			i++;
        		i=i+2;
        	}
        	if(page.charAt(i)=='<' && page.charAt(i+1)=='/'&& page.charAt(i+2)=='a') {	//</a>
        		i=i+4;
        	}
        	
        	if(page.charAt(i)=='<' && page.charAt(i+1)=='/'&& page.charAt(i+2)=='e') {	//</em>
        		i=i+5;
        	}
        	if(page.charAt(i)=='<' && page.charAt(i+1)=='s' && page.charAt(i+2)=='t' && page.charAt(i+3)=='r') {
        		i=i+8;
        	}
        	
        	if(page.charAt(i)=='<' && page.charAt(i+1)=='e') {	//<em>
        		i=i+4;
        	}
        	
        	if(page.charAt(i)=='.'&& page.charAt(i+1)==' ' && (Character.isUpperCase(page.charAt(i+2)) || Character.isDigit(page.charAt(i+2))) ) { //noktadan sonra yeni satıra geç
        		news=news+page.charAt(i);
        		news=news+page.charAt(i+1)+"\n";
        		i+=2;
        		
        	}
        	
        	
        	if(page.charAt(i)=='<' && page.charAt(i+1)=='/' && page.charAt(i+2)=='s' && page.charAt(i+3)=='t' && page.charAt(i+4)=='r') {
        		i=i+9;
        	}
        	if(i==page.indexOf("<script async src")) {
        		int initial=i;
        		while(i!=page.indexOf("</p>",initial))
        			i++;
        	}
        	
        	
        	news+=page.charAt(i);
        }
        news+="\n";
        news+="\n";
        
        return news;
	}
}

