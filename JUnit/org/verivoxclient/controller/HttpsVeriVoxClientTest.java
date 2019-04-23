package org.verivoxclient.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.verivoxclient.controller.HttpsVeriVoxClient;
import org.verivoxclient.controller.LocationPropertiesThread;
import org.verivoxclient.model.VeriVoxQueryModel;
import org.verivoxclient.model.VeriVoxQueryRecommended;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class HttpsVeriVoxClientTest {

	HttpsVeriVoxClient vericlient;
	VeriVoxQueryModel VM;
	LocationPropertiesThread lp = null;
	
	@BeforeEach
	void setUp() throws Exception {
		vericlient = new HttpsVeriVoxClient();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRequestPostCodeValue() {
		//List<String> list = vericlient.requestPostCodeValue("01156");
		
		assertNotNull(vericlient.requestPostCodeValue("01156"));
	/*	
		JSONObject jo = new JSONObject(list.get(0));
		String paolaType = jo.getString("id");
		String paolaText = jo.getString("partName");
		
		//lp = new LocationPropertiesThread("01156", 2000, paolaType, paolaText);
	*/
	}

	@Test
	void testRequestBenchmarkTarifIDIntStringStringString() {
		assertEquals(646891, vericlient.requestBenchmarkTarifID(2000, "01156", "13433") );
	}

	@Test
	void testRequestOffers() {
		VM = new VeriVoxQueryRecommended("Strom.Manufaktur", "H0", "01156", 2000, 646891, 13433, "Altfranken"); 
		VM.toJSONQuery();
		
		JsonElement jelement = new JsonParser().parse( vericlient.requestOffers(VM) );
		JsonObject  jobject = jelement.getAsJsonObject();
	
		assertTrue(jobject.has("totalResults"));
	}

}
