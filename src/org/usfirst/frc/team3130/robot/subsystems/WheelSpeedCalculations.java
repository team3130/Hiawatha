package org.usfirst.frc.team3130.robot.subsystems;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import org.usfirst.frc.team3130.misc.LinearInterp;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class WheelSpeedCalculations extends Subsystem {

	//Instance Handling
    private static WheelSpeedCalculations m_pInstance;
    /**
     * A system for getting an instance of this class.
     * The function provides a method by which the class is setup as a singleton
     * with only a single copy of it existing in memory.
     * <p> It will return a reference to the class, which is shared amoungst all callers of GetInstance()
     * 
     * @return the reference to the class refered to in GetInstance. In this case, WheelSpeedCalculations.
     */
    public static WheelSpeedCalculations GetInstance()
    {
    	if(m_pInstance == null) m_pInstance = new WheelSpeedCalculations();
    	return m_pInstance;
    }
	
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

	private static ArrayList<DataPoint> data_MainStorage;
	private static LinearInterp speedCurve;
	private static final String FILEPATH = "home/lvuser/speed-storage.ini";

	private WheelSpeedCalculations()
	{
		ReadFile();
		speedCurve = null;
		ReloadCurve();
	}
	
 	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	
 	public static void AddPoint(double dist, double speed)
 	{
 		data_MainStorage.add(new DataPoint(dist, speed));
 		data_MainStorage.sort(compPoint);
 		SmartDashboard.putNumber("Number of Points", data_MainStorage.size());
 		SaveToFile();
 		ReloadCurve();
 	}
 	
	public static void ReloadCurve()
	{
		ArrayList<Double> data_Dist = new ArrayList<Double>();
		ArrayList<Double> data_Speed = new ArrayList<Double>();
		
		for(int iii = 0; iii < data_MainStorage.size(); iii++){
			DataPoint pt = data_MainStorage.get(iii);
			data_Dist.add(pt.distance);
			data_Speed.add(pt.speed);
		}
		
		speedCurve = new LinearInterp(data_Dist, data_Speed);
	}

	public static void SaveToFile()
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
	
	public static void ReadFile()
	{
		data_MainStorage.clear();
		
		FileReader in = null;
		try{
			in = new FileReader(FILEPATH);
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
			return;
		}
		
		String point = "";
		try {
			while(in.ready()){
				point += String.valueOf(in.read());
				if(point.charAt(point.length()-1) == '\n')
				{
					//Shave the new line off
					point = point.substring(0, point.length()-1);
					//Create a new point and add it to the storage array
					data_MainStorage.add(new DataPoint(point));
					//Reset the point string
					point = "";
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		data_MainStorage.sort(compPoint);
		ReloadCurve();
	}

	public static void WipeData()
	{
		data_MainStorage.clear();
		data_MainStorage.add(new DataPoint(0,0));	//TODO: Get resonable closest range values
		data_MainStorage.add(new DataPoint(1000, 0));	//TODO: Get resonable farthest range values
		SaveToFile();
	}

	public static double GetSpeed(Double dist)
	{
		if(speedCurve != null && speedCurve.IsSane()){
			return speedCurve.GetY(dist);
		}
		return -1;
	}
}

