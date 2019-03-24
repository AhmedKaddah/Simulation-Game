package model.units;

import java.util.ArrayList;

import simulation.Address;
import simulation.Rescuable;
import model.events.WorldListener;
import model.people.Citizen;

public abstract class PoliceUnit extends Unit {

	private ArrayList<Citizen> passengers;
	private int maxCapacity;
	private int distanceToBase;

	public PoliceUnit(String unitID, Address location, int stepsPerCycle,WorldListener worldListener,int maxCapacity) {

		super(unitID, location, stepsPerCycle,worldListener);
		passengers = new ArrayList<Citizen>();
		this.maxCapacity = maxCapacity;

	}

	public int getDistanceToBase() {
		return distanceToBase;
	}

	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}
	public ArrayList<Citizen> getPassengers() {
		return passengers;
	}
	public void respond(Rescuable r) {
		super.respond(r);
		setDistanceToTarget(distance(r));
		if(getState()==UnitState.IDLE) {
				setState(UnitState.RESPONDING);
		}
		else {
			getTarget().struckBy(getTarget().getDisaster());
			setState(UnitState.RESPONDING);
		}
	}
}
