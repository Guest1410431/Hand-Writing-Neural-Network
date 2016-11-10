package Utilities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PictureInput
{
	public PictureInput()
	{
		BufferedImage img = null;
		
		try
		{
			img = ImageIO.read(new File("src/Main/ANN_input_lieb.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		for (int i = 0; i < 26; i++) // 26 Letters
		{
			char letter = (char) (i + 65);

			File file = new File("src/Main/" + letter + ".txt");
			PrintWriter pw = null;
			
			try
			{
				pw = new PrintWriter(new FileOutputStream(file, true));
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}

			ArrayList<Integer> input = new ArrayList<Integer>();

			for (int h = 0; h < 21; h++) // 21 instances of each letter
			{
				for (int j = (h * 21) + 1; j < (h * 21) + 21; j++)
				{
					for (int k = (i * 21) + 1; k < (i * 21) + 21; k++)
					{
						if (img.getRGB(j, k) == Color.WHITE.getRGB())
						{
							input.add(0);
						}
						else
						{
							input.add(1);
						}
					}
				}
			}
			System.out.println(letter + " | " + input.size());
			for (int q = 0; q < input.size(); q++)
			{
				if (q % 400 == 0)
				{
					pw.write("\n");
				}
				pw.write(input.get(q).toString());
			}
			pw.close();
		}
		System.out.println("Done");
	}
}
