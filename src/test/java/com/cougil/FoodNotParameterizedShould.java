package com.cougil;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class FoodNotParameterizedShould {

	@Test
	public void fruit_is_healthy() {
		assertThat(Food.FRUIT.isHealthy()).isTrue();
	}

	@Test
	public void legume_is_healthy() {
		assertThat(Food.LEGUME.isHealthy()).isTrue();
	}

	@Test
	public void nut_is_healthy() {
		assertThat(Food.NUT.isHealthy()).isTrue();
	}

	@Test
	public void salt_is_unhealthy() {
		assertThat(Food.SALT.isHealthy()).isFalse();
	}

	@Test
	public void saturated_fat_is_unhealthy() {
		assertThat(Food.SATURATED_FAT.isHealthy()).isFalse();
	}

	@Test
	public void sugar_is_not_healthy() {
		assertThat(Food.SUGAR.isHealthy()).isFalse();
	}

	@Test
	public void trans_fat_is_unhealthy() {
		assertThat(Food.TRANS_FAT.isHealthy()).isFalse();
	}

	@Test
	public void unsaturated_fat_is_healthy() {
		assertThat(Food.UNSATURATED_FAT.isHealthy()).isTrue();
	}

	@Test
	public void vegetable_is_healthy() {
		assertThat(Food.VEGETABLE.isHealthy()).isTrue();
	}

	@Test
	public void whole_grain_is_healthy() {
		assertThat(Food.WHOLE_GRAIN.isHealthy()).isTrue();
	}

}
