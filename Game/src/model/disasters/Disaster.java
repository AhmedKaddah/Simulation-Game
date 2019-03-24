package model.disasters;

import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable {

	private int startCycle;
	private Rescuable target;
	private boolean active;

	public Disaster(int startCycle, Rescuable target) {

		this.startCycle = startCycle;
		this.target = target;

	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getStartCycle() {
		return startCycle;
	}

	public Rescuable getTarget() {
		return target;
	}
	public void strike() {
		if(this instanceof Fire){
			int x = ((ResidentialBuilding)(getTarget())).getFireDamage();
			x+=10;
			((ResidentialBuilding)(getTarget())).setFireDamage(x);
		}
		if(this instanceof GasLeak) {
			int x = ((ResidentialBuilding)(getTarget())).getGasLevel();
			x+=10;
			((ResidentialBuilding)(getTarget())).setGasLevel(x);
		}
		if(this instanceof Collapse) {
			int x = ((ResidentialBuilding)(getTarget())).getFoundationDamage();
			x+=10;
			((ResidentialBuilding)(getTarget())).setFoundationDamage(x);
		}
		if(this instanceof Infection) {
			int x = ((Citizen)(getTarget())).getToxicity();
			x+=25;
			((Citizen)(getTarget())).setToxicity(x);
		}
		if(this instanceof Injury) {
			int x = ((Citizen)(getTarget())).getBloodLoss();
			x+=30;
			((Citizen)(getTarget())).setBloodLoss(x);
		}
		this.setActive(true);
		getTarget().struckBy(this);
	}
	public abstract void cycleStep();
}
