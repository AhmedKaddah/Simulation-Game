package model.units;

import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import simulation.Address;

public class FireTruck extends FireUnit {

	public FireTruck(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getFireDamage() > 0)

			target.setFireDamage(target.getFireDamage() - 10);

		if (target.getFireDamage() == 0)

			jobsDone();

	}
	public String toString() {
		String r= "Fire Truck, " +"ID: "+ this.getUnitID()+" Steps per cycle: "+this.getStepsPerCycle()+" Unit State: "+this.getState();
		if(this.getTarget()!=null)
			r+= " Target: Building at "+this.getLocation().getX()+", "+this.getLocation().getY();
		return r;
		
	}

}
