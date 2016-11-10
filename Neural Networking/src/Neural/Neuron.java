package Neural;

import java.util.ArrayList;

import Utilities.MathUtils;

public class Neuron
{
	private static final int BIAS = 1;
	private static final double LEARNING_RATIO = 0.1;
	private ArrayList<Integer>inputs;
	private ArrayList<Double>weights;
	private double biasWeight;
	private double output;
	//Constructor for the neuron node
	public Neuron()
	{
		this.inputs = new ArrayList<Integer>();
		this.weights = new ArrayList<Double>();
		this.biasWeight = Math.random();
	}
	//Sets the inputs for the neuron
	public void setInputs(ArrayList<Integer> inputs)
	{
		if(this.inputs.size() == 0)
		{
			this.inputs = new ArrayList<Integer>(inputs);
			generateWeights();
		}
		this.inputs = new ArrayList<Integer>(inputs);
	}
	//Generates the weights of the connections of each neuron
	private void generateWeights()
	{
		for(int i=0; i<inputs.size(); i++)
		{
			weights.add(Math.random());
		}
	}
	//Returns the output of each neuron
	public Double getOutput()
	{
		calculateOutput();
		return output;
	}
	//Calculates the output
	private void calculateOutput()
	{
		double sum = 0;
		
		for(int i=0; i<inputs.size(); i++)
		{	//sum is the cumulative of all the inputs and weights
			if(i == 400)
			{
				draw();
			}
			sum += inputs.get(i) * weights.get(i);
		}
		//Along with the BIAS constant and its own bias weight
		sum += BIAS * biasWeight;
		//returns the sigmoid of sum, calculated in MathUtils class
		output = MathUtils.sigmoidValue(sum);
	}
	private void draw()
	{
		for(int i=0; i<inputs.size(); i++)
		{	
			if(i % 20 == 0)
			{
				System.out.println();
			}
			if(inputs.get(i) == 0)
			{
				System.out.print((char)3);
			}
			else
			{
				System.out.print(inputs.get(i));
			}
		}
	}
	public void adjustWeights(double delta)
	{
		for(int i=0; i<inputs.size(); i++)
		{	//weight of the neuron
			double d = weights.get(i);
			//Factors in the learning ratio, bias, and the difference
			d += LEARNING_RATIO * delta * BIAS;
			//puts it back into the ArrayList
			weights.set(i, d);
		}
		biasWeight += LEARNING_RATIO * delta * BIAS;
	}
}




















