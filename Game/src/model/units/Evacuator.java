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
		if (!isTotarget()) {
			boolean flag = false;
			for(int i=0;i<((ResidentialBuilding) this.getTarget()).getOccupants().size();i++) {
				if(((ResidentialBuilding) this.getTarget()).getOccupants().get(i).getState() != CitizenState.DECEASED) {
					flag = true;
				}
			}
			if (((ResidentialBuilding) this.getTarget()).getStructuralIntegrity() <= 0
					|| ((ResidentialBuilding) this.getTarget()).getDisaster().isActive() == false || flag == false)
				jobsDone();
			else {
				for (int i = 0; i < ((ResidentialBuilding) this.getTarget()).getOccupants().size(); i++) {
					if (getPassengers().size() < getMaxCapacity() && ((ResidentialBuilding) this.getTarget()).getOccupants().get(i).getState()!=CitizenState.DECEASED) {
						getPassengers().add(((ResidentialBuilding) this.getTarget()).getOccupants().remove(0));
						i--;
					}
				}
			}
		} 
		else {
			int c = getPassengers().size();
			for (int i = 0; i < c; i++) {
				Citizen x = getPassengers().get(0);
				if (x.getState() != CitizenState.DECEASED)
					x.setState(CitizenState.RESCUED);
				x.getWorldListener().assignAddress(x, 0, 0);
				getPassengers().remove(0);
			}
			boolean flag = false;
			for(int i=0;i<((ResidentialBuilding) this.getTarget()).getOccupants().size();i++) {
				if(((ResidentialBuilding) this.getTarget()).getOccupants().get(i).getState() != CitizenState.DECEASED) {
					flag = true;
				}
			}
			if (((ResidentialBuilding) this.getTarget()).getOccupants().size() == 0
					|| ((ResidentialBuilding) this.getTarget()).getStructuralIntegrity() <= 0
					|| ((ResidentialBuilding) this.getTarget()).getDisaster().isActive() == false || flag == false ) {
				jobsDone();
			}
		}
	}

}

