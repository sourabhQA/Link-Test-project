package Brokenlink.LinkTest;

import org.testng.TestNG;

public class TestRunner {
	
	
	static TestNG testng;
	
	public static void main(String[] args) {
		
		testng = new TestNG();
		testng.setTestClasses(new Class[] {linkTest.class});
		testng.run();
				
		
	}
	

}
 