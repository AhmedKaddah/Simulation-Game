package model.units;

import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit {

	public Evacuator(String unitID, Address location, int stepsPerCycle, int maxCapacity) {

		super(unitID, location, stepsPerCycle, maxCapacity);
	}
	
}

