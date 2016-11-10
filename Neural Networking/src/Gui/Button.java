package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Button
{
	public Rectangle rect;
	public char letter;
	
	public boolean hover = false;
	
	public Button(Rectangle r, char l)
	{
		rect = r;
		letter = l;
	}
	
	public void render(Graphics g)
	{
		if(hover)
		{
			g.setColor(Color.GRAY);
		}
		else
		{
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		g.setColor(Color.BLACK);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.drawString(letter + "", (int)(rect.x + (rect.width/2)), (int)(rect.y + (2*(rect.height/3))));
	}
}
