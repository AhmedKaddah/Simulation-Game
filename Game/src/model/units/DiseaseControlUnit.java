package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Infection;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class DiseaseControlUnit extends MedicalUnit {

	public DiseaseControlUnit(String unitID, Address location,
			int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);
		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getToxicity() > 0) {
			target.setToxicity(target.getToxicity() - getTreatmentAmount());
			if (target.getToxicity() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getToxicity() == 0)
			heal();

	}

	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		if(r instanceof Citizen && ((Citizen)r).getHp()==0)
			throw new CannotTreatException(this, r, "This Citizen is already dead!");
		if (canTreat(r) == false)
			throw new CannotTreatException(this, r, "This target is already safe!");
		else {
			if (r instanceof ResidentialBuilding) {
				throw new IncompatibleTargetException(this, r, "This unit can only be sent to citizens!");
			} else {
				if(r.getDisaster() instanceof Infection) {
					if (getTarget() != null && ((Citizen) getTarget()).getToxicity() > 0
							&& getState() == UnitState.TREATING)
						reactivateDisaster();
					finishRespond(r);
				}
				else {
					throw new CannotTreatException(this, r, "This unit can only be sent to citizens with an infection!");
				}
			}
		}
	}
	public String toString() {
		String r= "Disease Control Unit, " +"ID: "+ this.getUnitID()+" Steps per cycle: "+this.getStepsPerCycle()+" Unit State: "+this.getState();
		if(this.getTarget()!=null)
			r+= " Target: Citizen at "+this.getLocation().getX()+", "+this.getLocation().getY();
		return r;
		
	}

}
