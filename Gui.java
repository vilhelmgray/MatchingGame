/*======================================================================
 *======================================================================
 * ---------------------------------------------------------------------
 * NAME:        Matching Game
 * PROGRAMMER:  William Gray
 * DATE:        July 21, 2008
 * VERSION:     1.0.0
 * ---------------------------------------------------------------------
 * Copyright (C) 2010 William Breathitt Gray <vilhelm.gray@gmail.com>
 * This file is part of Matching Game.
 * 
 * Matching Game is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Matching Game is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *======================================================================
 *======================================================================
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.util.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Thread;
import javax.swing.*;

public class Gui extends JFrame implements ActionListener
{
	public int SIZE = 0;
	public int score = 0;
	public ArrayList<Card> cards = new ArrayList<Card>();
	public ArrayList<JButton> buttons = new ArrayList<JButton>();
	public boolean clicked = false;
	public int choosen;
	public Color norm;
	public boolean[] used;
	public JLabel scoreLabel;
	public JPanel field;
	
	public Gui()
	{
		String ans = JOptionPane.showInputDialog(null, "How many colors to match?"); //set size of board
		SIZE = Integer.parseInt(ans);
		used = new boolean[SIZE * 2];
		
		initGUI(); //initialize GUI
	}
	
	//================================
	// Initialize GUI with components
	//================================
	public void initGUI()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Matching Game");
		this.setLayout(new FlowLayout());
		
		JPanel control = new JPanel(new GridLayout(2,1)); //control panel
		field = new JPanel(new GridLayout(SIZE, 2)); //where the cards will be shown
		
		initControl(control); //initialize Control Panel
		initField(field); //initialize playing field
		
		this.add(field);
		this.add(control);
		
		this.pack();
		this.setVisible(true);
	}
	
	//================================
	//   Initialize Control Panel
	//================================
	public void initControl(JPanel control)
	{
		scoreLabel = new JLabel("<html><font size=\"5\">Score: " + score + "</font></html>");
		JButton newGame = new JButton("New Game"); //restarts game
		
		newGame.addActionListener(this);
		norm = newGame.getBackground();
		
		control.add(scoreLabel);
		control.add(newGame);
	}
	
	//================================
	//   Initialize playing field
	//================================
	public void initField(JPanel field)
	{
		Random rng = new Random();
		
		for(int i = 0; i < SIZE; i++)
		{
			String name = "" + i;
			//get random color values
			float red = rng.nextFloat();
			float green = rng.nextFloat();
			float blue = rng.nextFloat();
			//create two copies of a card
			Card card1 = new Card(name, new Color(red, green, blue));
			Card card2 = new Card(name, new Color(red, green, blue));
			//add these cards to the pile
			cards.add(card1);
			cards.add(card2);
		}
		
		Collections.shuffle(cards, new Random()); //shuffle the deck
		
		for(int i = 0; i < cards.size(); i++)
		{
				JButton button = new JButton("?"/*cards.get(i).getName()*/);
				
				button.setActionCommand("" + i); //sets to index of card
				button.addActionListener(this);
				
				//add buttons to arraylists
				field.add(button);
				buttons.add(button);
		}
	}
	
	//================================
	//      Sets up a new game
	//================================
	public void newGame()
	{
		clicked = false;
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).setBackground(norm); //set buttons back to normal
		}
		
		for(int i = 0; i < used.length; i++)
		{
			used[i] = false; //set all back to unselected
		}
		
		field.removeAll(); //remove old buttons
		
		buttons = new ArrayList<JButton>(); //new buttons
		
		Collections.shuffle(cards, new Random()); //shuffle cards
		
		//put in buttons in random order
		for(int i = 0; i < cards.size(); i++)
		{
				JButton button = new JButton("?"/*cards.get(i).getName()*/);
				
				button.setActionCommand("" + i); //sets to index of card
				button.addActionListener(this);
				
				//add buttons to arraylists
				field.add(button);
				buttons.add(button);
		}
		
		//needed to show update
                this.setVisible(false);
		this.setVisible(true);
	}
	
	//================================
	//    Check if player won game
	//================================
	public boolean checkWin()
	{
		for(int i = 0; i < used.length; i++)
		{
			if(!used[i]) //if there is a false value
				return false;
		}
		return true;
	}
	
	//================================
	//   Listen for events from GUI
	//================================
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("New Game"))
			newGame();
		else
		{
			int i = Integer.parseInt(e.getActionCommand());
			buttons.get(i).setBackground(cards.get(i).getColor()); //show card color
			if(clicked && i != choosen) //if we already have a card selected and second click is on a different card
			{
				//System.out.println(cards.get(choosen).getName() + ":" + cards.get(i).getName());
				if(cards.get(choosen).getName() != cards.get(i).getName()) //if the cards do not match
				{
					try{
						Thread.sleep(100); //let the user see their error
					}
					catch(Exception ex)
					{
						System.out.println("error");
						System.exit(1);
					}
					buttons.get(choosen).setBackground(norm); //set color back to default
					buttons.get(i).setBackground(norm);
					used[choosen] = false;
				}
				else //if they do match
				{
					used[i] = true;
				}
				clicked = false;
			}
			else
			{
				choosen = i;
				used[choosen] = true;
				clicked = true;
			}
			if(checkWin()) //if all colors match
			{
				score++;
				scoreLabel.setText("<html><font size=\"5\">Score: " + score + "</font></html>");
				int ans = JOptionPane.showConfirmDialog(null, "You Won! Would you like to play again?", "You Win!", JOptionPane.YES_NO_OPTION);
				if(ans == 0)
					newGame();
				else
					System.exit(0);
			}
		}
	}
}
