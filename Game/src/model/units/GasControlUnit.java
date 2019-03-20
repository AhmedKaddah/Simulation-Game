package model.units;

import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class GasControlUnit extends FireUnit {

	public GasControlUnit(String unitID, Address location, int stepsPerCycle) {

		super(unitID, location, stepsPerCycle);

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
