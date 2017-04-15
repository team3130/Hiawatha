package org.usfirst.frc.team3130.robot.subsystems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import org.usfirst.frc.team3130.misc.SplineInterpolator;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class WheelSpeedCalculations extends Subsystem {
	
	private static Comparator<DataPoint> compPoint = new Comparator<DataPoint>() {
		@Override
		public int compare(DataPoint p1, DataPoint p2) {
			if(p1.greaterThan(p2))
				return 1;
			else if(p1.lessThan(p2))
				return -1;
			return 0;
		}
	};
	
	private static class DataPoint
	{
		double distance;
		double speed;
		
		public DataPoint(double dist, double speed)
		{
			distance = dist;
			this.speed = speed;
		}
		
		public DataPoint(String point)
		{
			if(point.charAt(0) == '(' && point.charAt(point.length()-1) == ')' && point.contains(",")){
				point = point.substring(1, point.length()-1);
				String[] parts = point.split(",");
				distance = Double.parseDouble(parts[0]);
				speed = Double.parseDouble(parts[1]);
			}
		}
		
		@Override
		public String toString()
		{
			return "("+distance+","+speed+")";
		}
		
		public boolean greaterThan(DataPoint other)
		{
			return distance > other.distance;
		}
		
		public boolean lessThan(DataPoint other)
		{
			return distance < other.distance;
		}
		
		@SuppressWarnings("unused")
		public boolean equals(DataPoint other)
		{
			return distance == other.distance;
		}
	}

	private ArrayList<DataPoint> data_MainStorage;
	private SplineInterpolator speedCurve;
	private final String FILEPATH;

	public WheelSpeedCalculations(String path)
	{
		FILEPATH = path;
		
		data_MainStorage = new ArrayList<DataPoint>();
		ReadFile();
		speedCurve = null;
		ReloadCurve();
	}
	
 	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	
 	public void AddPoint(double dist, double speed)
 	{
 		for(DataPoint p : data_MainStorage){
 			if(Math.abs(p.distance - dist) < Preferences.getInstance().getDouble("DataPoint Distance Variance", .01))
 				return;
 		}
 		
 		data_MainStorage.add(new DataPoint(dist, speed));
 		data_MainStorage.sort(compPoint);
 		SmartDashboard.putNumber("Number of Points", data_MainStorage.size());
 		SaveToFile();
 		ReloadCurve();
 	}
 	
	public void ReloadCurve()
	{
		ArrayList<Double> data_Dist = new ArrayList<Double>();
		ArrayList<Double> data_Speed = new ArrayList<Double>();
		
		for(int iii = 0; iii < data_MainStorage.size(); iii++){
			DataPoint pt = data_MainStorage.get(iii);
			data_Dist.add(pt.distance);
			data_Speed.add(pt.speed);
		}
		
		speedCurve = SplineInterpolator.createMonotoneCubicSpline(data_Dist, data_Speed);
	}

	public void SaveToFile()
	{
		FileWriter out = null;
		try{
			out = new FileWriter(FILEPATH);
		}catch(IOException ex){
			ex.printStackTrace();
			return;
		}
		
		try{
			for(int iii = 0; iii < data_MainStorage.size(); iii++){
				out.write(data_MainStorage.get(iii).toString() + '\n');
			}
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void ReadFile()
	{
		data_MainStorage.clear();
		
		System.out.println("Open Read");
		
		try(BufferedReader br = new BufferedReader(new FileReader(FILEPATH))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	System.out.println(line);
		        if(!line.equals(""))data_MainStorage.add(new DataPoint(line));
		    }
		    // line is not visible here.
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("DoneReading");
		data_MainStorage.sort(compPoint);
		for (DataPoint dataPoint : data_MainStorage) {
			System.out.println(dataPoint);
		}
		ReloadCurve();
	}

	public void WipeData()
	{
		data_MainStorage.clear();
		data_MainStorage.add(new DataPoint(0,3000));	//TODO: Get resonable closest range values
		data_MainStorage.add(new DataPoint(1000, 4000));	//TODO: Get resonable farthest range values
		SaveToFile();
	}

	public double GetSpeed(Double dist)
	{
		return speedCurve.interpolate(dist);
	}
}
