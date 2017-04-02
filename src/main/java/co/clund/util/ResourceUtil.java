package co.clund.util;

import java.util.Scanner;

public class ResourceUtil {

	public static String getResourceAsString(String resourceName){
		return getResourceAsString((new ResourceUtil()).getClass(), resourceName);
	}
	
	public static String getResourceAsString(Class<?> c, String resourceName){
		
		String text = "";
		
		try (Scanner scanner = new Scanner(c.getResourceAsStream(resourceName), "UTF-8")) {
			text = scanner.useDelimiter("\\A").next();
		}
		catch (Exception e){
			System.out.println("cannot open resource " + resourceName + ": " + e.getMessage());
		}
		
		return text;
	}
	
	
}
