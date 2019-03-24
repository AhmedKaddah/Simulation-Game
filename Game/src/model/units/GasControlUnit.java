package model.units;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class GasControlUnit extends FireUnit {

	public GasControlUnit(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {

		super(unitID, location, stepsPerCycle,worldListener);

	}
	public void treat() {
		if(((ResidentialBuilding) this.getTarget()).getStructuralIntegrity()<=0)
			jobsDone();
		else {
		if(((ResidentialBuilding) this.getTarget()).getGasLevel()>0)
			((ResidentialBuilding) this.getTarget()).setGasLevel(((ResidentialBuilding) this.getTarget()).getGasLevel()-10);
		else
			jobsDone();
		((ResidentialBuilding) this.getTarget()).getDisaster().setActive(false);
		}
	}
}
