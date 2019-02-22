package model.units;
import java.util.ArrayList;
import model.people.Citizen;
import simulation.Address;
public abstract class PoliceUnit extends Unit {
	ArrayList<Citizen> passengers;
	int maxCapacity;
	int distanceToBase;
	public PoliceUnit(String id, Address location, int stepsPerCycle, int maxCapacity){
		super(id,location,stepsPerCycle);
		this.maxCapacity=maxCapacity;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public int getDistanceToBase() {
		return distanceToBase;
	}
	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
	}
}
