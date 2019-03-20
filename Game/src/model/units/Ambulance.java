package model.units;

import model.people.Citizen;
import simulation.Address;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle) {

		super(unitID, location, stepsPerCycle);
	}


	public void treat() {
		if(((Citizen) this.getTarget()).getHp()<=0)
			jobsDone();
		else {
		if(((Citizen) this.getTarget()).getBloodLoss()==0)
			this.heal();
		else 
			((Citizen) this.getTarget()).setBloodLoss(((Citizen) this.getTarget()).getBloodLoss()-getTreatmentAmount());
		}
	}
}
