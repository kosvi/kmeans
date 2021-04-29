package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.XY;

public class DistancesTest {

	/*
	 * Test that our distance counter works correctly
	 */
	@Test
	public void calculateDistanceTest() throws Exception {
		// we use the knowledge that
		// 3^2 + 4^2 = 5^2 here
		XY p = new XY(10, 10);
		XY a1 = new XY(13, 14);
		XY a2 = new XY(6, 7);
		List<XY> l1 = new ArrayList<>();
		l1.add(a1);
		l1.add(a2);
		assertEquals(10, (int) Distances.calculateDistance(p, l1));
		// Just my paranoia
		assertTrue(10 == (int) Distances.calculateDistance(p, l1));
		assertFalse(11 == (int) Distances.calculateDistance(p, l1));
	}

	// And without List
	@Test
	public void calculateDistanceWithoutListTest() throws Exception {
		XY p = new XY(10, 10);
		XY o = new XY(14, 13);
		assertEquals(5, (int) Distances.calculateDistance(p, o));
	}
}
