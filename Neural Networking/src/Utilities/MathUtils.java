package Utilities;

public class MathUtils
{
	public static double sigmoidValue(double sum)
	{
		return (1 / (1+Math.exp(-sum)));
	}
}
