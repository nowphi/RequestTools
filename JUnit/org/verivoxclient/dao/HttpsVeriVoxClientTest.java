package org.verivoxclient.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HttpsVeriVoxClientTest {

	HttpsVeriVoxClient vericlient;
	
	@BeforeEach
	void setUp() throws Exception {
		vericlient = new HttpsVeriVoxClient();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRequestPostCodeValue() {
		List<String> list = vericlient.requestPostCodeValue("01156");
		//list.forEach(s -> System.out.println(s));
	}

	@Test
	void testRequestBenchmarkTarifIDIntStringStringString() {
		vericlient.requestBenchmarkTarifID(2000, "01156", "13433");
		fail("Not yet implemented");
	}

	@Test
	void testRequestBenchmarkTarifIDIntStringString() {
		fail("Not yet implemented");
	}

	@Test
	void testSetConnectionRequestProperty() {
		fail("Not yet implemented");
	}

	@Test
	void testRequestOffers() {
		fail("Not yet implemented");
	}

}
