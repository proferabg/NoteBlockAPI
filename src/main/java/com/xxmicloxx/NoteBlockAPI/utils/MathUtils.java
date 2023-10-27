package com.xxmicloxx.NoteBlockAPI.utils;

import dev.simplix.protocolize.api.Location;

public class MathUtils {

	private static final double[] cos = new double[360];
	private static final double[] sin = new double[360];

	static {
		for (int deg = 0; deg < 360; deg++) {
			cos[deg] = Math.cos(Math.toRadians(deg));
			sin[deg] = Math.sin(Math.toRadians(deg));
		}
	}
	
	private static double[] getCos(){
		return cos;
	}
	
	private static double[] getSin(){
		return sin;
	}
	
	public static Location stereoSourceLeft(Location location, float distance) {
		int angle = getAngle(location.yaw());
	    return new Location(location.x(), location.y(), location.z(), location.yaw(), location.pitch()).add(-getCos()[angle] * distance, 0, -getSin()[angle] * distance);
	}
	public static Location stereoSourceRight(Location location, float distance) {
		int angle = getAngle(location.yaw());
	    return new Location(location.x(), location.y(), location.z(), location.yaw(), location.pitch()).add(getCos()[angle] * distance, 0, getSin()[angle] * distance);
	}

	/**
	 * Calculate new location for stereo
	 * @param location origin location
	 * @param distance negative for left side, positive for right side
	 * @return
	 */
	public static Location stereoPan(Location location, float distance){
		int angle = getAngle(location.yaw());
		return new Location(location.x(), location.y(), location.z(), location.yaw(), location.pitch()).add( getCos()[angle] * distance, 0, getSin()[angle] * distance);
	}

	private static int getAngle(float yaw){
		int angle = (int) yaw;
		while (angle < 0) angle += 360;
		return angle % 360;
	}
	
}
