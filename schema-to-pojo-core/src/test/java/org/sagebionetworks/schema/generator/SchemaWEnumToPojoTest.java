package org.sagebionetworks.schema.generator;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sagebionetworks.schema.adapter.JSONObjectAdapterException;
import org.sagebionetworks.schema.generator.handler.HandlerFactory;
import org.sagebionetworks.schema.generator.handler.schema03.HandlerFactoryImpl03;

public class SchemaWEnumToPojoTest {
	//directory where autogenerated classes will live
	File outputDir;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Create a temp directory for output
		outputDir = FileUtil.createTempDirectory("output");
	}

	@After
	public void tearDown() throws Exception {
		// Delete the output directory
		FileUtil.recursivelyDeleteDirectory(outputDir);
		assertFalse(outputDir.exists());
	}

	/**
	 * Tests behavior of how marshalling works when a json has an array that should contain an enum
	 * @throws IOException
	 * @throws JSONObjectAdapterException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void loadFileWithEnum() throws IOException,
			JSONObjectAdapterException, ClassNotFoundException {
		
		// Load form the sample file
		File sampleFile = new File("src/test/resources/DatasetWithEnum.json");
		assertTrue("Test file does not exist: " + sampleFile.getAbsolutePath(),
				sampleFile.exists());
		// Create the class
		HandlerFactory factory = new HandlerFactoryImpl03();
		// Generate the class
		SchemaToPojo.generatePojos(sampleFile, outputDir, factory, new StringBuilder());
		// Make sure the file exists
		File result = new File(outputDir, "DatasetWithEnum.java");
		System.out.println(result.getAbsolutePath());
		assertTrue(result.exists());
		// Load the file string
		String resultString = FileUtil.readToString(result);
		System.out.println(resultString);
	}
}
