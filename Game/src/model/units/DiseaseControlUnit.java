package model.units;

import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

public class DiseaseControlUnit extends MedicalUnit {

	public DiseaseControlUnit(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {

		super(unitID, location, stepsPerCycle,worldListener);

	}


	public void treat() {
		this.getTarget().getDisaster().setActive(false);
		if(((Citizen) this.getTarget()).getHp()<=0)
			jobsDone();
		else {
		if((((Citizen) this.getTarget()).getToxicity()-getTreatmentAmount())<=0) {
			((Citizen) this.getTarget()).setState(CitizenState.RESCUED);
			((Citizen) this.getTarget()).setToxicity(0);
			this.setState(UnitState.IDLE);
			this.heal();
		}
		else 
			((Citizen) this.getTarget()).setToxicity(((Citizen) this.getTarget()).getToxicity()-getTreatmentAmount());
		}
	}
}
