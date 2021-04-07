package com.cougil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class Junit5ParameterizedFoodShould {

	@ParameterizedTest(name="#{index} Food {0} is healthy? ")
	@EnumSource(Food.class)
	void recognize_which_food_is_healthy_using_enums(Food food) {
		assertThat(food.isHealthy()).isEqualTo(food.isHealthy());
	}

	//region We can use a method to provide a list of objects to use as parameters
	@ParameterizedTest(name="using a method provider: Food {0} is healthy? {1}")
	@MethodSource("parameters")
	void recognize_which_food_is_healthy_using_a_method_provider(Food food, boolean isHealthy) {
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}

	private static Stream<Arguments> parameters() {
		return Stream.of(
				arguments("FRUIT", true),
				arguments("LEGUME", true),
				arguments("NUT", true),
				arguments("SALT", false),
				arguments("SATURATED_FAT", false),
				arguments("SUGAR", false),
				arguments("TRANS_FAT", false),
				arguments("UNSATURATED_FAT", true),
				arguments("VEGETABLE", true),
				arguments("WHOLE_GRAIN", true)
		);
	}
	//endregion

	//region It is possible to use a default method. It has to be static without no args
	@ParameterizedTest(name="using a default static method: Food {0} is healthy? {1}")
	@MethodSource
	void recognize_which_food_is_healthy_using_a_default_static_method(Food food, boolean isHealthy) {
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}

	private static Stream<Arguments> recognize_which_food_is_healthy_using_a_default_static_method () {
		return parameters();
	}
	//endregion

	//region We can use Strings to convert them into Enums (based on its names)
	@ParameterizedTest(name = "Using Strings parsed into Enums and booleans - {0} healthy? {1}")
	@CsvSource({
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
	void recognize_which_food_is_healthy_using_enum_names(Food food, boolean isHealthy) {
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}
	//endregion

	//region It is also possible to use an specific converter if we need it
	@ParameterizedTest(name="Using an specific converter: {0} is healthy")
	@ValueSource( strings = {
					"FRUIT",
					"LEGUME",
					"NUT",
					"UNSATURATED FAT",
					"VEGETABLE",
					"WHOLE GRAIN"
	})
	void recognize_which_food_is_healthy_with_a_converter(@ConvertWith(HealthyFoodConverter.class) Food food) {
		assertThat(food.isHealthy()).isTrue();
	}

	static class HealthyFoodConverter extends SimpleArgumentConverter {
		@Override
		protected Object convert(Object source, Class<?> targetType) {
			for (Food food : Food.values()) {
				if (food.toString().equals(source.toString())) {
					if (food.isHealthy()) return food;
				}
			}
			return null;
		}
	}
	//endregion

	//region Of course, we can use CSV files to read them and use it easily
	@ParameterizedTest(name = "Reading from a CSV file: {0} is healthy? {1}")
	@CsvFileSource(resources = "/food.csv")
	public void recognize_which_food_is_healthy_reading_from_csv(Food food, boolean isHealthy) {
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}
	//endregion

	//region Last but not least, we can manage it in our own way with ArgumentsAccessor 😉
	@ParameterizedTest(name = "With arguments accessor: {0} is healthy? {1}")
	@CsvSource({
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
	void recognize_which_food_is_healthy_with_arguments_accessor(ArgumentsAccessor accessor){
		Food food = Food.valueOf(accessor.getString(0));
		final boolean isHealthy = accessor.getBoolean(1);
		assertThat(food.isHealthy()).isEqualTo(isHealthy);
	}
	//endregion

}
