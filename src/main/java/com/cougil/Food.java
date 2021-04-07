package com.cougil;

import java.util.StringJoiner;

public enum Food {

	FRUIT(true, "FRUIT"),
	LEGUME(true,"LEGUME"),
	NUT(true, "NUT"),
	SALT(false, "SALT"),
	SATURATED_FAT(false, "SATURATED FAT"),
	SUGAR(false, "SUGAR"),
	TRANS_FAT(false, "TRANS FAT"),
	UNSATURATED_FAT(true, "UNSATURATED FAT"),
	VEGETABLE(true, "VEGETABLE"),
	WHOLE_GRAIN(true, "WHOLE GRAIN");

	private boolean healthy;
	private String name;

	Food(boolean healthy,String name) {
		this.healthy = healthy;
		this.name = name;
	}

	public boolean isHealthy() {
		return healthy;
	}

	@Override
	public String toString() {
		return name;
	}
}
