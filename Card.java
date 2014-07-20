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
import java.awt.Color;
public class Card implements Comparable<Card>
{
	public String name;
	public Color color;
	
	public Card(String n, Color c)
	{
		name = n; //set name
		color = c;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	//====================
	// Returns 0 if the
	// cards are the same
	//====================
	public int compareTo(Card other)
	{
		if(this.getName().equals(other.getName()))
			return 0;
		return -1;
	}
}
