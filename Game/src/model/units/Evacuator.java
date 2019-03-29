package model.units;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
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
			if (!isTotarget()) {
				for (int i = 0; i < getMaxCapacity(); i++) {
					if (((ResidentialBuilding) this.getTarget()).getOccupants().size() > 0) {
						getPassengers().add(((ResidentialBuilding) this.getTarget()).getOccupants().remove(0));
					}
				}
			} 
			else {
				int c=getPassengers().size();
				for (int i = 0; i < c; i++) {
					Citizen x = getPassengers().get(0);
					x.setState(CitizenState.RESCUED);
					x.getWorldListener().assignAddress(x, 0, 0);
					getPassengers().remove(0);
					

				}

				if (((ResidentialBuilding) this.getTarget()).getOccupants().size() == 0) {
					jobsDone();
				} 
			}

		}

	}
	
}

