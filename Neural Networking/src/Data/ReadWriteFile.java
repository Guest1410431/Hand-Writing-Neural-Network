package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import Neural.TrainingSet;

public class ReadWriteFile
{	//Gathers all training sets from the file and stores the in an array
	public static ArrayList<TrainingSet> readTrainingSets()
	{
		ArrayList<TrainingSet> trainingSets = new ArrayList<TrainingSet>();

		for (int i = 0; i < 26; i++)
		{ // Change ints to char values
			char letterValue = (char) (i + 65);
			String letter = String.valueOf(letterValue);
			//Gets all the sets from the files they are in, saves them into an array
			for (ArrayList<Integer> list : readFromFile("Data/" + letter + ".txt"))
			{
				trainingSets.add(new TrainingSet(list, GoodOutputs.getInstance().getGoodOutput(letter)));
			}
		}
		return trainingSets;
	}
	//grabs the file at the given filename and reads it so info can be taken
	private static ArrayList<ArrayList<Integer>> readFromFile(String filename)
	{
		ArrayList<ArrayList<Integer>> inputs = new ArrayList<ArrayList<Integer>>();

		try
		{
			InputStream in = ReadWriteFile.class.getClassLoader().getResourceAsStream(filename);
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line;

			while ((line = reader.readLine()) != null)
			{
				ArrayList<Integer> input = new ArrayList<Integer>();

				for (int i = 0; i < line.length(); i++)
				{
					int value = 0;

					try
					{
						value = Integer.parseInt(String.valueOf(line.charAt(i)));
					}
					catch (Exception e)
					{

					}
					input.add(value);
				}
				inputs.add(input);
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return inputs;
	}
	//saves the outputs and training sets into a file
	public static void saveToFile(ArrayList<Integer> input, String filename)
	{
		try
		{
			File file = new File("src/Data/" + filename + ".txt");
			PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
			
			for (Integer i : input)
			{
				pw.write(new Integer(i).toString());
			}
			System.out.println("Changes to " + filename + " have been recorded.");
			pw.write("\n");
			pw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
