package Neural;

import java.util.ArrayList;

import Data.ReadWriteFile;

public class Train
{
	private static final int NEURON_COUNT = 26;

	private Network network;
	private ArrayList<TrainingSet> trainingSets;

	// Constructor
	public Train()
	{
		this.network = new Network();
		this.network.addNeurons(NEURON_COUNT);
		this.trainingSets = ReadWriteFile.readTrainingSets();
	}

	// Trains the neural network, given a training set
	public void train(long count)
	{
		for (long i = 0; i < count; i++)
		{ //Goes through the training sets to determine best fit
			if(i % 1000 == 0)
			{
				System.out.println(i + " / " + count);
			}			
			int index = ((int) (Math.random() * trainingSets.size()));
			TrainingSet set = trainingSets.get(index);
			network.setInputs(set.getInputs());
			network.adjustData(set.getGoodOutput());
		}
	}
	//Gives the network new inputs
	public void setInputs(ArrayList<Integer> inputs)
	{
		network.setInputs(inputs);
	}
	//Adds a training set to the list
	public void addTrainingSet(TrainingSet newSet)
	{
		trainingSets.add(newSet);
	}
	//Returns the outputs of the network
	public ArrayList<Double> getOutputs()
	{
		return network.getOutputs();
	}
}
