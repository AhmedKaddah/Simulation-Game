package model.units;

import model.events.WorldListener;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;

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

	public void heal() {
		if((((Citizen) this.getTarget()).getHp()+healingAmount)>=100) {
			((Citizen) this.getTarget()).setHp(100);
			((Citizen) this.getTarget()).setState(CitizenState.SAFE);
			jobsDone();
		}
		else
			((Citizen) this.getTarget()).setHp(((Citizen) this.getTarget()).getHp()+healingAmount);
	}
	
}
