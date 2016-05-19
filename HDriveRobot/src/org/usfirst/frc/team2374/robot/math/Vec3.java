package org.usfirst.frc.team2374.robot.math;

public class Vec3 {

    public static final Vec3 ZERO = new Vec3(0);

    public final double x;
    public final double y;
    public final double z;

    public Vec3(double a) {
        this(a, a, a);
    }

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 add(Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    public Vec3 cross(Vec3 other) {
        return new Vec3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }

    public double direction() {
        return Math.atan2(y, x);
    }

    public double direction2() {
        return Math.atan2(z, Math.sqrt(x * x + y * y));
    }

    public Vec3 divide(double d) {
        return new Vec3(x / d, y / d, z / d);
    }

    public Vec3 divide(Vec3 v) {
        return new Vec3(x / v.x, y / v.y, z / v.z);
    }

    public double dot(Vec3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vec3) {
            Vec3 v = (Vec3) o;
            return x == v.x && y == v.y && z == v.z;
        }
        return false;
    }

    public Vec3 interpolate(Vec3 other, double amt) {
        return multiply(amt).add(other.multiply(1 - amt));
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vec3 multiply(double d) {
        return new Vec3(x * d, y * d, z * d);
    }

    public Vec3 multiply(Vec3 other) {
        return new Vec3(x * other.x, y * other.y, z * other.z);
    }

    public Vec3 normalize() {
        final double len = length();
        if (len == 0) {
            return new Vec3(1, 0, 0);
        }
        return multiply(1 / len);
    }

    public Vec3 reverse() {
        return new Vec3(-x, -y, -z);
    }

    public Vec3 rotateAboutZ(double t) {
        return new Vec3(x * Math.cos(t) - y * Math.sin(t), x * Math.sin(t) + y * Math.cos(t), z);
    }

    public Vec3 subtract(Vec3 other) {
        return new Vec3(x - other.x, y - other.y, z - other.z);
    }

    @Override
    public String toString() {
        return "(" + (float) x + ", " + (float) y + ", " + (float) z + ")";
    }

    public Vec3 withLength(double l) {
        if (l == 0) {
            return ZERO;
        }
        return multiply(l / length());
    }

    public Vec3 withX(double newx) {
        return new Vec3(newx, y, z);
    }

    public Vec3 withY(double newy) {
        return new Vec3(x, newy, z);
    }

    public Vec3 withZ(double newZ) {
        return new Vec3(x, y, newZ);
    }
}
