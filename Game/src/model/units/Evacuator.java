package model.units;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.CitizenState;
import simulation.Address;

public class Evacuator extends PoliceUnit {

	public Evacuator(String unitID, Address location, int stepsPerCycle, WorldListener worldListener,int maxCapacity) {

		super(unitID, location, stepsPerCycle, worldListener,maxCapacity);
	}


	public void treat() {
		if (((ResidentialBuilding) this.getTarget()).getStructuralIntegrity() <= 0 || ((ResidentialBuilding) this.getTarget()).getDisaster().isActive()==false )
				jobsDone();
		else {
			if (this.getDistanceToBase() != 0) {
				for (int i = 0; i < getMaxCapacity(); i++) {
					if (((ResidentialBuilding) this.getTarget()).getOccupants().size() > 0) {
						getPassengers().add(((ResidentialBuilding) this.getTarget()).getOccupants().remove(0));
					}
				}
			} 
			else {
				for (int i = 0; i < getMaxCapacity(); i++) {

					getPassengers().get(0).getWorldListener().assignAddress(getPassengers().get(0), 0, 0);
					getPassengers().remove(0).setState(CitizenState.RESCUED);

				}

				if (((ResidentialBuilding) this.getTarget()).getOccupants().size() == 0) {
					setState(UnitState.IDLE);
					jobsDone();
				} 
			}

		}

	}
	
}

