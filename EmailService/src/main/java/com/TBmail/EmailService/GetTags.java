package com.TBmail.EmailService;

public class GetTags {
	public static String get(String page) {
		String allTags=new String();
		int sp = page.indexOf("tdb-tags");
		for(int i=sp; i<page.indexOf("ul", sp); i++) {
			if((page.charAt(i)== '"') && (page.charAt(i+1)=='>')) { //">  <
				i=i+2;
				while(page.charAt(i)!='<') {
					allTags+=page.charAt(i);
					i++;
					if(page.charAt(i+1)=='<') {
						allTags+=page.charAt(i);
						allTags+=", ";
						i++;
					}
						
				}
			
			}
		}
		allTags=allTags.substring(0,allTags.length()-2);
		return allTags;
	}
}

