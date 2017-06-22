package roulette;

import org.junit.Before;
import org.junit.Test;

import static org.testng.Assert.assertEquals;

public class HelloWorldTest {

	// this would normally be in a separate (domain) class
	private String concatenate(String s1, String s2){
		return s1+" "+ s2;
	}
	
	@Before
	public void thisGetsExecutedBeforeEachTest(){
		// initialise common variables
	}
	@Test
	public void concatenation_appends_second_string(){
		// arrange
		// act
		String result=concatenate("Hello", "World");			
		// assert
		assertEquals("Hello World", result);
	}
	
	private void burp(){
		throw new IllegalArgumentException("Exception");
	}

	@Test(expected = IllegalArgumentException.class)
	public void burp_throws_exception() {
		// arrange
		// act
		burp();
		// assert
	}
}
