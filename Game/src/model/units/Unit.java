package model.units;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable {
	private String unitID;
	UnitState state;
	Address location;
	Rescuable target;
	int distanceToTarget;
	int stepsPerCycle;
	public Unit(String id, Address location, int stepsPerCycle){
		unitID=id;
		this.location=location;
		this.stepsPerCycle=stepsPerCycle;
		state=UnitState.IDLE;
	}
	public String getUnitID() {
		return unitID;
	}
	public UnitState getState() {
		return state;
	}
	public void setState(UnitState state) {
		this.state = state;
	}
	public Address getLocation() {
		return location;
	}
	public void setLocation(Address location) {
		this.location = location;
	}
	public Rescuable getTarget() {
		return target;
	}
	public int getDistanceToTarget() {
		return distanceToTarget;
	}
	public int getStepsPerCycle() {
		return stepsPerCycle;
	}
}
