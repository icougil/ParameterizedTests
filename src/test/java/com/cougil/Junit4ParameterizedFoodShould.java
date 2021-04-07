package com.cougil;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class Junit4ParameterizedFoodShould {

	/*
	   It is also possible to use field injection, BUT the fields have to be 'public'
	   annotating each field indicating its order
		@Parameter(value = 0), @Parameter(value = 1), etc...
	*/
	private Food food;
	private boolean isHealthy;

	/* Required constructor to be used by the JUnit parameterized runtime */
	public Junit4ParameterizedFoodShould(Food food, boolean isHealthy) {
		this.food = food;
		this.isHealthy = isHealthy;
	}

	/*
	 'name' attribute is optional, but we should provide an unique name for test 😉
	 We can use:
		'{index}' for the current parameter index
		'{0}' 	  for the 1st parameter
		'{1}'	  for the 2nd parameter
		etc
	*/
	@Parameterized.Parameters(name = "{index}: product {0} is healthy? {1}")
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][]{
				{Food.FRUIT, true},
				{Food.LEGUME, true},
				{Food.NUT, true},
				{Food.SALT, false},
				{Food.SATURATED_FAT, false},
				{Food.SUGAR, false},
				{Food.TRANS_FAT, false},
				{Food.UNSATURATED_FAT, true},
				{Food.VEGETABLE, true},
				{Food.WHOLE_GRAIN, true}
		});
	}

	@Test
	public void recognize_which_products_are_healthy() {
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}

}
