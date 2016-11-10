package Neural;

import java.util.ArrayList;

public class TrainingSet
{
	private ArrayList<Integer>inputs;
	private ArrayList<Double>goodOutput;
	//Constructor
	public TrainingSet(ArrayList<Integer>inputs, ArrayList<Double>goodOutput)
	{
		this.inputs = inputs;
		this.goodOutput = goodOutput;
	}
	//Returns the inputs
	public ArrayList<Integer> getInputs()
	{
		return inputs;
	}
	//Returns the outputs
	public ArrayList<Double> getGoodOutput()
	{
		return goodOutput;
	}

}
