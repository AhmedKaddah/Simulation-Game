package model.units;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class GasControlUnit extends FireUnit {

	public GasControlUnit(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {

		super(unitID, location, stepsPerCycle,worldListener);

	}
	public void treat() {
		((ResidentialBuilding) this.getTarget()).getDisaster().setActive(false);
		if(((ResidentialBuilding) this.getTarget()).getStructuralIntegrity()<=0)
			jobsDone();
		else {
		if((((ResidentialBuilding) this.getTarget()).getGasLevel()-10)<=0) {
			((ResidentialBuilding) this.getTarget()).setGasLevel(0);
			this.setState(UnitState.IDLE);
			jobsDone();
		}
		else
			((ResidentialBuilding) this.getTarget()).setGasLevel(((ResidentialBuilding) this.getTarget()).getGasLevel()-10);
		
		}
	}
}
