package roulette;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Test;
public class HelloWorldStubExample {
	@Test
	public void iterator_will_return_hello_world(){
		//arrange
		Iterator i=mock(Iterator.class);
		when(i.next()).thenReturn("Hello").thenReturn("World");
		//act
		String result=i.next()+" "+i.next();
		//assert
		assertEquals("Hello World", result);
	}
	@Test
	public void with_arguments(){
		Comparable c=mock(Comparable.class);
		when(c.compareTo("Test")).thenReturn(1);
		assertEquals(1,c.compareTo("Test"));
	}
	@Test
	public void with_unspecified_arguments(){
		Comparable c=mock(Comparable.class);
		when(c.compareTo(anyInt())).thenReturn(-1);
		assertEquals(-1,c.compareTo(5));
	}
}
