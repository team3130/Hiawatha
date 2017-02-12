package org.usfirst.frc.team3130.misc;

import java.util.ArrayList;

public class LinearInterp {
		
	private ArrayList<Double> dist;
	private ArrayList<Double> speed;
	
	boolean sane = true;
	
	public LinearInterp(ArrayList<Double> dist, ArrayList<Double> speed)
	{
		this.dist = dist;
		this.speed = speed;
		
		if(!IsInputSane())
			sane = false;
			return;
	}
	
	public boolean IsSane() {return sane;}
	
	
	/**
	 * Returns the y value coresponding to the Linear Interpolation of the data provided
	 * 
	 * @param x the x value to find the coresponding y of
	 * @return the y value that matches x
	 */
	public double GetY(double x)
	{
		int distSize = dist.size()-1;
		int iii = 0;
		
		for(; iii < distSize && x <= dist.get(iii); iii++){			
		}
		
		double m = ((speed.get(iii+1)-speed.get(iii))/(dist.get(iii+1)-dist.get(iii)));
		
		return m*(x-dist.get(iii)) + speed.get(iii);
	}
	
	/**
	 * Checks if the input is in a usable state for interpolation
	 * <p>Returns false if either array is empty, if they aren't equal to eachother, or if the distance array isn't sorted
	 * @return if the Input arrays are sane
	 */
	private boolean IsInputSane()
	{
		if(dist.size() == 0 || speed.size()==0)
			return false;
		if(dist.size() == speed.size())
			return false;
		
		return IsSorted(dist);
	}
	
	/**
	 * Checks if an arrayList is sorted
	 * <p>Returns false if the array list has a higher number followed by a lower one, or if there are duplicate numbers in a row
	 * @param list the array list to check
	 * @return if the list is sorted
	 */
	private boolean IsSorted(ArrayList<Double> list)
	{
		for(int iii = 0; iii < list.size() - 1; iii++){
			if(list.get(iii) >= list.get(iii+1))
				return false;
		}
		return true;
	}
}
