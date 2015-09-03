package example;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fit.Counts;
import fitnesse.trinidad.*;
 
public class RunFromJUnit{
	TestRunner tr;
	@Before
	public void initHelper() throws Exception{
		tr=new TestRunner(
				new FitNesseRepository("../lib"),
				new FitTestEngine(),
				new File(System.getProperty("java.io.tmpdir"),"fitnesse").getAbsolutePath());
	}
	@Test
	public void runTest() throws Exception{
		Counts ct=tr.runTest("ExerciseTen");
		Assert.assertEquals("exceptions in tests", 0, ct.exceptions);
		Assert.assertEquals("failing tests", 0, ct.wrong);
	}
}