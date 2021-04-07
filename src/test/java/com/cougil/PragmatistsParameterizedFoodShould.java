package com.cougil;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.converters.ConversionFailedException;
import junitparams.converters.Converter;
import junitparams.converters.Param;
import junitparams.naming.TestCaseName;

@RunWith(JUnitParamsRunner.class)
public class PragmatistsParameterizedFoodShould {

	/*	It is also possible to refer to the nth-parameter,
	   	using '{0}' for the 1st one, '{1}' for the second one, etc */
	@Test
	@Parameters(method = "parameters")
	@TestCaseName("{method} - [{index}] - {params}")
	public void recognize_which_food_is_healthy(Food food, boolean isHealthy) {
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}

	private static Object[] parameters() {
		return new Object[][]{
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
		};
	}

	//region We can use a method explicitly (it has to start with "provide")
	@Test
	@Parameters(source = PragmatistsParameterizedFoodShould.class)
	public void recognize_which_food_is_healthy_with_a_provider(Food food, boolean isHealthy) {
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}

	public static Object[] provideParameters() {
		return parameters();
	}
	//endregion

	//region Also, we can instantiate enums based on its names, also with objects, etc
	@Test
	@Parameters({
			"FRUIT, true",
			"LEGUME, true",
			"NUT, true",
			"SALT, false",
			"SATURATED_FAT, false",
			"SUGAR, false",
			"TRANS_FAT, false",
			"UNSATURATED_FAT, true",
			"VEGETABLE, true",
			"WHOLE_GRAIN, true"
	})
	public void recognize_which_food_is_healthy_with_enum_names(Food food, boolean isHealthy) {
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}
	//endregion

	//region We can use mappers and converters to transform parameters into objects
	@Test
	@Parameters({
			"FRUIT",
			"LEGUME",
			"NUT",
			"UNSATURATED FAT",
			"VEGETABLE",
			"WHOLE GRAIN"
	})
	public void recognize_which_food_is_healthy_with_a_converter(@Param(converter = HealthyFoodConverter.class) Food food) {
		assertThat(food.isHealthy()).isTrue();
	}

	public static class HealthyFoodConverter implements Converter<Param, Food> {

		@Override
		public void initialize(Param annotation) { }

		@Override
		public Food convert(Object param) throws ConversionFailedException {
			for (Food food : Food.values()) {
				if (food.toString().equals(param)) {
					if (food.isHealthy()) return food;
				}
			}
			throw new ConversionFailedException("failed converting "+param);
		}
	}
	//endregion

	//region It is easy to use CSV files that can be parsed and used as parameters
	@Test
	@FileParameters("classpath:food.csv")
	public void recognize_which_food_is_healthy_reading_from_csv(Food food, boolean isHealthy) {
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}
	//endregion

}
