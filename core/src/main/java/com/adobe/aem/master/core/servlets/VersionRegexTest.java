/**
 * 
 */
package com.adobe.aem.master.core.servlets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kartikkarnayil
 *
 */
public class VersionRegexTest {

	public static final String CONTENT_HIERARCHY =
		      "/content/cms-commons(/sigmaaldrich|/sigmaaldrich-cn|/performance-materials)?/(\\w{2})/(\\w{2})(.*)?";

	
	public static void main(String[] args) {
		
		String str = "/apps/v1/test";
        
		Pattern p = Pattern.compile("v[0-9]");

        Matcher matcher = p.matcher(str);
        System.out.println(matcher.replaceAll("v2"));

		
	}
}
