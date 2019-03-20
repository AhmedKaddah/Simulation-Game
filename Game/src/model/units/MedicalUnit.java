package model.units;

import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public abstract class MedicalUnit extends Unit {

	private int healingAmount;
	private int treatmentAmount;

	public MedicalUnit(String unitID, Address location, int stepsPerCycle) {

		super(unitID, location, stepsPerCycle);
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
			if(distance(r)==0) {
				setState(UnitState.TREATING);
			}
			else {
				setState(UnitState.RESPONDING);
			}
		}
		else {
			if(((Citizen)(getTarget())).getState()!=CitizenState.RESCUED) {
				getTarget().struckBy(getTarget().getDisaster());
			}
			if(distance(r)==0) {
				setState(UnitState.TREATING);
			}
			else {
				setState(UnitState.RESPONDING);
			}
		}
	}
}
