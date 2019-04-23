package org.verivoxclient.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.verivoxclient.controller.LocationPropertiesThread;

import json.JSONObject;

class LocationPropertiesThreadTest {

	LocationPropertiesThread lp = null;
		
	@BeforeEach
	void setUp() throws Exception {
		lp = new LocationPropertiesThread("01156", 2000, "13433", "Altfranken");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLocationPropertiesThreadStringStringInt() {
		lp = new LocationPropertiesThread("01156","13433", 646891);
	}

	@Test
	void testLocationPropertiesThreadStringIntStringString() {
		lp = new LocationPropertiesThread("01156", 2000, "13433", "Altfranken");
	}

	@Test
	void testGetPostCode() {
		assertEquals("01156", lp.getPostCode());
	}

	@Test
	void testGetPaolaTypeLocationId() {
		assertEquals(13433,lp.getPaolaTypeLocationId());
	}

	@Test
	void testGetPaolaTypeLocationText() {
		assertEquals("Altfranken",lp.getPaolaTypeLocationText());
	}

	@Test
	void testBenchmarkTariffId() {
		lp.run();
		assertEquals(646891,lp.benchmarkTariffId());
	}

	@Test
	void testAnnualTotal() {
		assertEquals(2000,lp.annualTotal());
	}

}
