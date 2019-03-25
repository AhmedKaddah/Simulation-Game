package model.units;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class FireTruck extends FireUnit {

	public FireTruck(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {

		super(unitID, location, stepsPerCycle,worldListener);

	}

	public void treat() {
		((ResidentialBuilding) this.getTarget()).getDisaster().setActive(false);
		if(((ResidentialBuilding) this.getTarget()).getStructuralIntegrity()<=0)
			jobsDone();
		else {
		if((((ResidentialBuilding) this.getTarget()).getFireDamage() - 10) <= 0) {
			((ResidentialBuilding) this.getTarget()).setFireDamage(0);
			this.setState(UnitState.IDLE);
			jobsDone();}
		else
			((ResidentialBuilding) this.getTarget()).setFireDamage(((ResidentialBuilding) this.getTarget()).getFireDamage()-10);
		
		}
	}

}
