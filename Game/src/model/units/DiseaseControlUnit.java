package model.units;

import model.people.Citizen;
import simulation.Address;

public class DiseaseControlUnit extends MedicalUnit {

	public DiseaseControlUnit(String unitID, Address location, int stepsPerCycle) {

		super(unitID, location, stepsPerCycle);

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
