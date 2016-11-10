package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import Data.GoodPixels;
import Neural.Train;

public class Grid
{
	public boolean editable;

	public int xPos;
	public int yPos;
	public int width;
	public int height;

	public int cellWidth = 20;
	public int cellHeight = 20;
	public int wRatio;
	public int hRatio;

	public boolean[][] cells;

	public Grid(int x, int y, int w, int h, boolean e)
	{
		xPos = x;
		yPos = y;
		width = w;
		height = h;

		wRatio = (int) (width / cellWidth);
		hRatio = (int) (height / cellHeight);

		cells = new boolean[cellWidth][cellHeight];
	}

	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);

		for (int i = 0; i < cellWidth; i++)
		{
			for (int h = 0; h < cellHeight; h++)
			{
				if (cells[i][h])
				{
					g.fillRect((i * wRatio) + xPos, (h * hRatio) + yPos, wRatio, hRatio);
				}
				else
				{
					g.drawRect((i * wRatio) + xPos, (h * hRatio) + yPos, wRatio, hRatio);
				}
			}
		}
	}

	// Drawing on the grid
	public ArrayList<Integer> update(int x, int y, Train networkTrainer, MouseEvent e)
	{
		ArrayList<Integer> inputs = new ArrayList<Integer>();

		if (SwingUtilities.isLeftMouseButton(e))
		{
			cells[(x - xPos) / wRatio][(y - yPos) / hRatio] = true;
		}
		else
		{
			cells[(x - xPos) / wRatio][(y - yPos) / hRatio] = false;
		}
		for (int i = 0; i < cellWidth; i++)
		{
			for (int h = 0; h < cellHeight; h++)
			{
				if (cells[i][h])
				{
					inputs.add(1);
				}
				else
				{
					inputs.add(0);
				}
			}
		}
		networkTrainer.setInputs(inputs);

		ArrayList<Double> outputs = networkTrainer.getOutputs();
		int index = 0;

		for (int i = 0; i < outputs.size(); i++)
		{
			if (outputs.get(i) > outputs.get(index))
			{
				index = i;
			}
		}
		return GoodPixels.getInstance().getGoodPixels(index);
	}

	// Outputing the letter
	public void update(ArrayList<Integer> transfer)
	{
		int j = 0;
		for (int i = 0; i < cellWidth; i++)
		{
			for (int h = 0; h < cellHeight; h++)
			{
				if (transfer.get(j) == 0)
				{
					cells[i][h] = false;
				}
				else
				{
					cells[i][h] = true;
				}
				j++;
			}
		}
	}

	public void clear()
	{
		for (int i = 0; i < cellWidth; i++)
		{
			for (int h = 0; h < cellHeight; h++)
			{
				cells[i][h] = false;
			}
		}
	}

	public void send(String letter)
	{
		ArrayList<Integer> input = GoodPixels.getInstance().getGoodPixels(letter);

		int j = 0;
		for (int i = 0; i < cellWidth; i++)
		{
			for (int h = 0; h < cellHeight; h++)
			{
				if (input.get(j) == 1)
				{
					cells[i][h] = true;
				}
				else
				{
					cells[i][h] = false;
				}
				j++;
			}
		}
	}
	public ArrayList<Integer> getPixels()
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		
		for(int i=0; i<cellWidth; i++)
		{
			for(int h=0; h<cellHeight; h++)
			{
				if(cells[i][h])
				{
					ret.add(1);
				}
				else
				{
					ret.add(0);
				}
			}
		}
		return ret;
	}
}
