package model.units;

import model.events.WorldListener;
import model.people.Citizen;
import simulation.Address;

public class DiseaseControlUnit extends MedicalUnit {

	public DiseaseControlUnit(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {

		super(unitID, location, stepsPerCycle,worldListener);

	}


	public void treat() {
		if(((Citizen) this.getTarget()).getHp()<=0)
			jobsDone();
		else {
		if(((Citizen) this.getTarget()).getToxicity()==0)
			this.heal();
		else 
			((Citizen) this.getTarget()).setToxicity(((Citizen) this.getTarget()).getToxicity()-getTreatmentAmount());
		}
	}
}
