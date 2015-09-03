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
		osw.write('a');
		osw.flush();
		verify(mock).write(argThat(new BaseMatcher<byte[]>(){
			@Override
			public void describeTo(Description description) {
				// nothing
			}
			@Override
			public boolean matches(Object item) {
				byte[] actual=(byte[]) item;
				return actual[0]=='a';
			}
		}), eq(0),eq(1));
	}
}
