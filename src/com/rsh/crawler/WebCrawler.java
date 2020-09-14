package com.rsh.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
	
	public static Queue<String> queue = new LinkedList<String>();
	public static Set<String> marked = new HashSet<>();
	
	public static String regex = "http[s]*://(\\w+\\.)*(\\w+)";
	
	public static void bfsAlgoritm(String root) throws IOException{
		queue.add(root);
		
		while(!queue.isEmpty()) {
			String crawleUrl = queue.poll();
			System.out.println("\n=== Site crawled :" + crawleUrl + " === ");
			
			//we limit to 	100 websites here
			if(marked.size() > 100)
				return;
			
			boolean ok = false;
			URL url = null;
			BufferedReader br = null;
			
			while(!ok) {
				try {
					url = new URL(crawleUrl);
					br = new BufferedReader(new InputStreamReader(url.openStream()));
					ok = true;
					
				}catch(MalformedURLException e) {
					System.out.println("*** Maformed URL :" + crawleUrl);
					System.out.println("*** e:" + e);
					crawleUrl = queue.poll();
					ok = false;
				}catch(IOException ioe) {
					System.out.println("*** IOException for URL : "+crawleUrl);
					crawleUrl = queue.poll();
					ok = false;
				}/*finally {
					if(br != null) {
						br.close();
					}
				}*/
			}
			
			StringBuilder sb = new StringBuilder();
			//String tmp = null;
			
			//while((tmp = br.readLine()) != null) {
			while((crawleUrl = br.readLine()) != null) {
				sb.append(crawleUrl);
			}
			
			//tmp = sb.toString();
			crawleUrl = sb.toString();
			Pattern pattern = Pattern.compile(regex);
			//Matcher matcher = pattern.matcher(tmp);
			Matcher matcher = pattern.matcher(crawleUrl);
			
			
			while(matcher.find()) {
				String w = matcher.group();
				
				if(!marked.contains(w)) {
					marked.add(w);
					System.out.println("Sited added for crawling: " + w);
					queue.add(w);
				}
			}
				
			if(br != null) {
				br.close();
			}
			
		}
	}
	
	public static void showResult() {
		System.out.println("\n\nResults: ");
		System.out.println("Web sites crawled : " + marked.size() + "\n");
		
		for(String s: marked) {
			System.out.println("* " + s);
		}
		
	}
	
	
	public static void main(String[] args) {
		try {
			//bfsAlgoritm("http://www.ssaurel.com/blog");
			bfsAlgoritm("http://www.rsh-engineering.com");
			showResult();
			
		}catch(IOException e) {
			
		}
	}
	

}
