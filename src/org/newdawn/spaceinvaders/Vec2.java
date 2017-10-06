package org.newdawn.spaceinvaders;

public class Vec2 {
	public double x = 0;
	public double y = 0;
	Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double sqrMagnitude() {
		return x * x + y * y;
	}
	
	public double magnitude() {
		return Math.sqrt(this.sqrMagnitude());
	}
	
	public void normalize() {
		double mag = this.magnitude();
		x = x/mag;
		y = y/mag;
	}
	
	public void clamp(double length) {
		normalize();
		x = x*length;
		y = y*length;
	}
}
