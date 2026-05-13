package hu.neuberger.base_math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BaseMathTest {

	@Test
	public void testAddPositive() {
		assertEquals(5, BaseMath.add(2, 3));
	}

	@Test
	public void testAddNegative() {
		assertEquals(-5, BaseMath.add(-2, -3));
	}
}
