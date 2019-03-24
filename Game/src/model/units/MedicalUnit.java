package model.units;

import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public abstract class MedicalUnit extends Unit {

	private int healingAmount;
	private int treatmentAmount;

	public MedicalUnit(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {

		super(unitID, location, stepsPerCycle,worldListener);
		healingAmount = 10;
		treatmentAmount = 10;
	}
	public int getTreatmentAmount() {
		return treatmentAmount;
	}
	public void respond(Rescuable r) {
		super.respond(r);
		setDistanceToTarget(distance(r));
		if(getState()==UnitState.IDLE) {
				setState(UnitState.RESPONDING);
		}
		else {
			if(((Citizen)(getTarget())).getState()!=CitizenState.RESCUED) {
				getTarget().struckBy(getTarget().getDisaster());
			}
				setState(UnitState.RESPONDING);
		}
	}
	public void heal() {
		if(((Citizen) this.getTarget()).getHp()==100)
			jobsDone();
		else
			((Citizen) this.getTarget()).setHp(((Citizen) this.getTarget()).getHp()+healingAmount);
	}
}
