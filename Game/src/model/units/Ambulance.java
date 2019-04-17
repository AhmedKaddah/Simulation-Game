package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Injury;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getBloodLoss() > 0) {
			target.setBloodLoss(target.getBloodLoss() - getTreatmentAmount());
			if (target.getBloodLoss() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getBloodLoss() == 0)

			heal();

	}

	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		if(r instanceof Citizen && ((Citizen)r).getHp()==0)
			throw new CannotTreatException(this, r, "This Citizen is already dead!");
		if (canTreat(r) == false)
			throw new CannotTreatException(this, r, "This Citizen is already safe!");
		else {
			if (r instanceof ResidentialBuilding) {
				throw new IncompatibleTargetException(this, r, "This unit can only be sent to citizens!");
			} 
			else {
				if(r.getDisaster() instanceof Injury) {
					if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0
							&& getState() == UnitState.TREATING)
						reactivateDisaster();
					finishRespond(r);
				}
				else {
					throw new CannotTreatException(this, r, "This unit can only be sent to citizens with an injury!");
				}
			}
		}
	}
	public String toString() {
		String r= "Ambulance";
		return r;
		
	}

}
