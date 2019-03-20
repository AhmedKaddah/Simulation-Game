package model.disasters;

import model.people.Citizen;

public class Infection extends Disaster {

	public Infection(int startCycle, Citizen target) {

		super(startCycle, target);
	}
	public void strike() {
		this.setActive(true);
		getTarget().struckBy(this);
		int x = ((Citizen)(getTarget())).getToxicity();
		x+=25;
		((Citizen)(getTarget())).setToxicity(x);
	}
	public void cycleStep() {
		int x = ((Citizen)(getTarget())).getToxicity();
		x+=15;
		((Citizen)(getTarget())).setToxicity(x);;
	}
}
