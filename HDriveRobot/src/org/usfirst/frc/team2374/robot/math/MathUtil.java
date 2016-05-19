package org.usfirst.frc.team2374.robot.math;

public abstract class MathUtil {

    /**
     * Returns whether two angles are close to each other, within a given
     * maximum difference.
     *
     * @param angle1 The first angle.
     * @param angle2 The second angle.
     * @param maxError The maximum difference between the angles.
     * @return Whether the difference of the two angles is within the maximum
     * error.
     */
    public static boolean angleNear(double angle1, double angle2, double maxError) {
        double diff = Math.abs(angle1 - angle2) % (2 * Math.PI);
        return diff < maxError || diff > 2 * Math.PI - maxError;
    }

    /**
     * Returns whether two numbers are close to each other, within a given
     * maximum difference.
     *
     * @param value1 The first number.
     * @param value2 The second number.
     * @param maxError THe maximum difference between the numbers.
     * @return Whether the difference of the two numbers is within the maximum
     * error.
     */
    public static boolean valueNear(double value1, double value2, double maxError) {
        return Math.abs(value1 - value2) < maxError;
    }
    public static double mod(double value, double base){
    	if (value>=0)
    			return value%base;
    	else return mod(value+base,base);
    }
    //True means turn right
    public static boolean turnDirection(double currentAngle, double desiredAngle) {
    	double relAngle = mod(currentAngle-desiredAngle,2*Math.PI);
    	return relAngle>Math.PI;
    }
}
