package roulette;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
public class HelloWorldMockExample {
	OutputStream mock;
	OutputStreamWriter osw;
	@Before
	public void init(){
		mock=mock(OutputStream.class);
		osw=new OutputStreamWriter(mock);
	}
	// simple verification
	@Test
	public void OutputStreamWriter_Closes_OutputStream_on_Close() throws IOException{
		osw.close();
		verify(mock).close();
	}
	// custom matchers
	@Test
	public void OutputStreamWriter_Buffers_And_Forwards_Writes_As_Bytes_To_OutputStream() throws IOException{

		
		mock=mock(OutputStream.class);
		osw=new OutputStreamWriter(mock);
		osw.write('a');
		osw.flush();
		// can't do this as we don't know how long the array is going to be
		// verify(mock).write(new byte[]{'a'},0,1);

		BaseMatcher<byte[]> arrayStartingWithA=new BaseMatcher<byte[]>(){
			@Override
			public void describeTo(Description description) {
				// nothing
			}
			@Override
			public boolean matches(Object item) {
				byte[] actual=(byte[]) item;
				return actual[0]=='a';
			}
		};
		
//		verify(mock).write(new int[]{},0,1);
		
		verify(mock).write(argThat(arrayStartingWithA), eq(0),eq(1));
		
	}
	@Test(expected=IOException.class)
	public void OutputStreamWriter_rethrows_an_exception_from_OutputStream() throws IOException{
		doThrow(new IOException()).when(mock).close();
		osw.close();
	}
}
