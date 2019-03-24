package model.units;

import model.events.WorldListener;
import model.people.Citizen;
import simulation.Address;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {

		super(unitID, location, stepsPerCycle,worldListener);
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
