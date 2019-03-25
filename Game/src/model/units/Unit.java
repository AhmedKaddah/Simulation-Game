package model.units;

import model.events.SOSResponder;
import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {

	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location,int stepsPerCycle,WorldListener worldListener) {

		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener=worldListener;
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

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public int distance(Rescuable r) {
		int x = this.getLocation().getX() - r.getLocation().getX();
		int y = this.getLocation().getY() - r.getLocation().getY();
		if (x < 0)
			x = x * -1;
		if (y < 0)
			y = y * -1;
		return (x + y);
	}

	public void cycleStep() {
		if(this instanceof Evacuator) {
			if(getState() == UnitState.IDLE && ((Evacuator) this).getDistanceToBase() != 0 ) {
				if(((Evacuator) this).getDistanceToBase() - stepsPerCycle > 0) {
					((Evacuator) this).setDistanceToBase(((Evacuator) this).getDistanceToBase() - stepsPerCycle);
				}
				else {
					getWorldListener().assignAddress(this,0,0);
					((Evacuator) this).setDistanceToBase(0);
				}
			}
			if(getState() == UnitState.RESPONDING || getState() == UnitState.TREATING) {
				if(distanceToTarget == 0 && ((Evacuator) this).getDistanceToBase()!=0) {
					if(((Evacuator) this).getDistanceToBase() - stepsPerCycle > 0) {
						((Evacuator) this).setDistanceToBase(((Evacuator) this).getDistanceToBase() - stepsPerCycle);
					}
					else {
						getWorldListener().assignAddress(this,0,0);
						((Evacuator) this).setDistanceToBase(0);
						setDistanceToTarget(getTarget().getLocation().getX() + getTarget().getLocation().getY());
						this.treat();
					}
				}
				if(distanceToTarget != 0 && ((Evacuator) this).getDistanceToBase()==0) {
					if(distanceToTarget - stepsPerCycle > 0) {
						((Evacuator) this).setDistanceToTarget(distanceToTarget - stepsPerCycle);
					}
					else {
						getWorldListener().assignAddress(this, getTarget().getLocation().getX(), getTarget().getLocation().getY());
						((Evacuator) this).setDistanceToTarget(0);
						((Evacuator)this).setDistanceToBase(getTarget().getLocation().getX() + getTarget().getLocation().getY());
						this.setState(UnitState.TREATING);
						this.treat();
					}
				}
			}
		}
		else {
			if(getState() == UnitState.RESPONDING || getState() == UnitState.TREATING) {
				   if (distanceToTarget == 0) {
                       getWorldListener().assignAddress(this, getTarget().getLocation().getX(), getTarget().getLocation().getY());
                       setState(UnitState.TREATING);
                       this.treat();
                   } else {
                       if (distanceToTarget - stepsPerCycle > 0) {
                           setDistanceToTarget(distanceToTarget - stepsPerCycle);
                       }
                       if (distanceToTarget - stepsPerCycle <= 0) {
                           setDistanceToTarget(0);
                       }
                   }
			}
			if(this.target instanceof Citizen && ((Citizen)(this.target)).getState()==CitizenState.RESCUED ) {
				((MedicalUnit)this).heal();
			}
		}
	}

	public void respond(Rescuable r) {
		this.target = r;
	}

	public abstract void treat();
	public void jobsDone() {
		setState(UnitState.IDLE);
		target = null;
	}
}
