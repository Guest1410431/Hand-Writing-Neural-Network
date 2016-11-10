package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Data.GoodOutputs;
import Data.ReadWriteFile;
import Neural.Train;
import Neural.TrainingSet;

public class Window extends JPanel
{
	private final int WIDTH = 1500;
	private final int HEIGHT = 750;

	int totalSets = 0;
	int totalCounts = 0;
	
	JFrame frame;

	private Train networkTrainer;

	private Grid inputGrid;
	private Grid outputGrid;

	private ArrayList<Integer>transfer = new ArrayList<Integer>();
	private ArrayList<Button>rects = new ArrayList<Button>();
	
	public Window()
	{
		frame = new JFrame();

		inputGrid = new Grid(HEIGHT / 15, HEIGHT / 15, HEIGHT - (3 * (HEIGHT / 15)), HEIGHT - (3 * (HEIGHT / 15)), true);
		outputGrid = new Grid((2 * (HEIGHT / 15)) + (HEIGHT - (3 * (HEIGHT / 15))), HEIGHT / 15, HEIGHT - (3 * (HEIGHT / 15)), HEIGHT - (3 * (HEIGHT / 15)), false);

		networkTrainer = new Train();
		networkTrainer.train(25000);

		frame.setTitle("Artificial Neural Network - Hand Writing Recognition");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setResizable(false);
		
		generateRectangles();
		
		frame.add(this);
		frame.addMouseMotionListener(new MouseMotionListener()
		{

			public void mouseMoved(MouseEvent e)
			{
				for(Button b : rects)
				{
					if(b.rect.contains(e.getPoint()))
					{
						b.hover = true;
					}
					else
					{
						b.hover = false;
					}
				}
			}

			public void mouseDragged(MouseEvent e)
			{	//For the user's input
				if(e.getX() > inputGrid.xPos && e.getX() < (inputGrid.xPos + inputGrid.width) &&
						e.getY() > inputGrid.yPos && e.getY() < (inputGrid.yPos + inputGrid.height))
				{
					transfer = inputGrid.update(e.getX(), e.getY()-25, networkTrainer, e);
					outputGrid.update(transfer);
				}
			}
		});
		frame.addMouseListener(new MouseListener()
		{

			public void mouseReleased(MouseEvent e)
			{
			}

			public void mousePressed(MouseEvent e)
			{
			}

			public void mouseExited(MouseEvent e)
			{
			}

			public void mouseEntered(MouseEvent e)
			{
			}

			public void mouseClicked(MouseEvent e)
			{
				if(e.getX() > inputGrid.xPos && e.getX() < (inputGrid.xPos + inputGrid.width) &&
						e.getY() > inputGrid.yPos && e.getY() < (inputGrid.yPos + inputGrid.height))
				{
					inputGrid.update(e.getX(), e.getY()-25, networkTrainer, e);
					outputGrid.update(transfer);
				}
				else
				{
					for(Button b : rects)
					{
						if(b.rect.contains(e.getX(), e.getY()))
						{	//For training the input as a specific letter
							networkTrainer.addTrainingSet(new TrainingSet(inputGrid.getPixels(), GoodOutputs.getInstance().getGoodOutput(b.letter + "")));
							ReadWriteFile.saveToFile(inputGrid.getPixels(), b.letter + "");
							totalSets++;
						}
					}
				}
			}
		});
		frame.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent e)
			{

			}

			public void keyReleased(KeyEvent e)
			{

			}

			public void keyPressed(KeyEvent e)
			{
				//Exits the program
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					System.exit(0);
				}
				//Clears the board
				if(e.getKeyCode() == KeyEvent.VK_C)
				{
					inputGrid.clear();
					outputGrid.clear();
				}
			}
		});
		frame.setVisible(true);
	}

	private void generateRectangles()
	{
		for(int i=0; i<26; i++)
		{
			rects.add(new Button(new Rectangle((i*50)+50, HEIGHT-(HEIGHT/9), 40, 40), (char)(i+65)));
		}
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(HEIGHT / 15, HEIGHT / 15, HEIGHT - (3 * (HEIGHT / 15)), HEIGHT - (3 * (HEIGHT / 15)));
		g.fillRect((2 * (HEIGHT / 15)) + (HEIGHT - (3 * (HEIGHT / 15))), HEIGHT / 15, HEIGHT - (3 * (HEIGHT / 15)), HEIGHT - (3 * (HEIGHT / 15)));
		g.fillRect((int) (WIDTH - (3.5 * (HEIGHT / 15))), HEIGHT / 15, 3 * (HEIGHT / 15), HEIGHT - (3 * (HEIGHT / 15)));
		g.setColor(Color.BLACK);
		g.drawRect(HEIGHT / 15, HEIGHT / 15, HEIGHT - (3 * (HEIGHT / 15)), HEIGHT - (3 * (HEIGHT / 15)));
		g.drawRect((2 * (HEIGHT / 15)) + (HEIGHT - (3 * (HEIGHT / 15))), HEIGHT / 15, HEIGHT - (3 * (HEIGHT / 15)), HEIGHT - (3 * (HEIGHT / 15)));
		g.drawRect((int) (WIDTH - (3.5 * (HEIGHT / 15))), HEIGHT / 15, 3 * (HEIGHT / 15), HEIGHT - (3 * (HEIGHT / 15)));

		g.setFont(new Font("Garamond", Font.PLAIN, WIDTH/100));
		ArrayList<Double> outputs = networkTrainer.getOutputs();
		for (int i = 0; i < 26; i++)
		{
			g.drawString((char) (i + 65) + "", WIDTH - (3 * (HEIGHT / 15)) - 10, (HEIGHT / 15) + ((i + 1) * 20));
			g.drawString(" -|- " + round(outputs.get(i)), WIDTH - (3 * (HEIGHT / 15)), (HEIGHT / 15) + ((i + 1) * 20));
			g.drawString(" -|- " + numLines((char) (i + 65)), WIDTH - (3 * (HEIGHT / 15)) + 65, (HEIGHT / 15) + ((i + 1) * 20));
		}
		g.drawString("Total: " + totalSets,WIDTH - (3 * (HEIGHT / 15)), (HEIGHT / 15) + 550);
		inputGrid.render(g);
		outputGrid.render(g);
		g.drawString("Press \"C\" to clear the grid", 100, 40);
		g.drawString("Not the right letter? Help train the brain by correcting it", 50, (int)(HEIGHT-(HEIGHT/8.5)));
		for(Button b : rects)
		{
			b.render(g);
		}
		repaint();
	}

	private double round(double value)
	{
		if (value < 0.01)
		{
			value = 0;
		}
		if (value > 0.99)
		{
			value = 1;
		}

		value *= 1000;
		int x = (int) (value);
		value = x / 1000.0;

		return value;
	}

	private int numLines(char letter)
	{
		int count = 0;
		try
		{
			LineNumberReader reader = new LineNumberReader(new FileReader("src/Data/" + letter + ".txt"));

			while (reader.readLine() != null)
			{

			}
			count = reader.getLineNumber();
			
			if(totalCounts < 26)
			{
				totalCounts++;
				totalSets+=count;
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return count;
	}
}
