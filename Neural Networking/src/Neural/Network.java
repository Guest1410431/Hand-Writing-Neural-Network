package Neural;

import java.util.ArrayList;

public class Network
{
	private ArrayList<Neuron>neurons;
	//Constructor
	public Network()
	{
		neurons = new ArrayList<Neuron>();
	}
	//Add a set number of neurons into the ArrayList
	public void addNeurons(int count)
	{
		for(int i=0; i<count; i++)
		{
			neurons.add(new Neuron());
		}
	}
	//Given a set of inputs, gives the inputs to the neurons
	public void setInputs(ArrayList<Integer>inputs)
	{
		for(Neuron n : neurons)
		{
			n.setInputs(inputs);
		}
	}
	//returns the neurons outputs
	public ArrayList<Double> getOutputs()
	{
		ArrayList<Double>outputs = new ArrayList<Double>();
		
		for(Neuron n : neurons)
		{
			outputs.add(n.getOutput());
		}
		return outputs;
	}
	//Adjusts the data certain neurons provided new information
	public void adjustData(ArrayList<Double>goodOutput)
	{
		for(int i=0; i<neurons.size(); i++)
		{
			double delta = goodOutput.get(i) - neurons.get(i).getOutput();
			neurons.get(i).adjustWeights(delta);
		}
	}
}
