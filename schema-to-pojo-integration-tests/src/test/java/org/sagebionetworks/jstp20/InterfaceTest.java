package org.sagebionetworks.jstp20;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.sagebionetworks.DefaultConcreteTypeImpl;
import org.sagebionetworks.schema.adapter.JSONObjectAdapterException;
import org.sagebionetworks.schema.adapter.org.json.EntityFactory;

/**
 * Tests for JSTP-20 wich supports fields declared as interfaces or abstract classes.
 * 
 * @author John
 *
 */
public class InterfaceTest {
	
	
	@Test
	public void testInterfaces() throws IOException, JSONObjectAdapterException, ClassNotFoundException{
		// do a round trip test
		HasInterfaceField pojo = new HasInterfaceField();
		OneImpl one = new OneImpl();
		one.setFromInterface("abc");
		one.setFromOne("123");
		pojo.setInterfaceField(one);
		
		String json = EntityFactory.createJSONStringForEntity(pojo);
		System.out.println(json);
		HasInterfaceField clonePojo = EntityFactory.createEntityFromJSONString(json, HasInterfaceField.class);
		assertNotNull(clonePojo);
		assertEquals(pojo, clonePojo);
	}
	
	@Test
	public void testInterfaceFieldWithDefaultConcreteType() throws JSONObjectAdapterException {
		HasInterfaceField pojo = new HasInterfaceField();
		
		DefaultConcreteTypeImpl fieldImpl = new DefaultConcreteTypeImpl();
		fieldImpl.setSomeProperty("Some Property");
		
		pojo.setInterfaceFieldWithDefaultConcreteType(fieldImpl);
		
		String expectedJson = "{\"interfaceFieldWithDefaultConcreteType\":{\"someProperty\":\"Some Property\"}}";
		
		String json = EntityFactory.createJSONStringForEntity(pojo);
		
		assertEquals(expectedJson, json);
		
		HasInterfaceField clonePojo = EntityFactory.createEntityFromJSONString(json, HasInterfaceField.class);

		assertEquals(pojo, clonePojo);
	}
	
	@Test
	public void testArraOfInterfaces() throws JSONObjectAdapterException{
		// This pojo has a list of interfaces with different implement types
		HasListOfInterface pojo = new HasListOfInterface();
		// First implementation
		OneImpl one = new OneImpl();
		one.setFromInterface("abc");
		one.setFromOne("123");
		// second implementation
		TwoImpl two = new TwoImpl();
		two.setFromInterface("xyz");
		two.setFromTwo("456");
		// Add them both to the list
		pojo.setList(new LinkedList<SomeInterface>());
		pojo.getList().add(one);
		pojo.getList().add(two);
		
		// Make the round trip
		String json = EntityFactory.createJSONStringForEntity(pojo);
		System.out.println(json);
		HasListOfInterface clonePojo = EntityFactory.createEntityFromJSONString(json, HasListOfInterface.class);
		assertNotNull(clonePojo);
		assertEquals(pojo, clonePojo);
	}
	
	@Test
	public void testArrayOfInterfacesWithDefatulConcreteType() throws JSONObjectAdapterException {
		HasListOfInterface pojo = new HasListOfInterface();
		DefaultConcreteTypeImpl one = new DefaultConcreteTypeImpl();
		one.setSomeProperty("Some Property One");
		DefaultConcreteTypeImpl two = new DefaultConcreteTypeImpl();
		two.setSomeProperty("Some Property Two");
		
		// Add them both to the list
		pojo.setListWithDefaultConcreteType(Arrays.asList(one, two));
		
		String expectedJson = "{\"listWithDefaultConcreteType\":[{\"someProperty\":\"Some Property One\"},{\"someProperty\":\"Some Property Two\"}]}";
		// Make the round trip
		String json = EntityFactory.createJSONStringForEntity(pojo);
		
		assertEquals(expectedJson, json);
		
		HasListOfInterface clonePojo = EntityFactory.createEntityFromJSONString(json, HasListOfInterface.class);
		
		assertNotNull(clonePojo);
		assertEquals(pojo, clonePojo);
	}
}
