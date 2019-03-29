package model.disasters;

import model.people.Citizen;

public class Injury extends Disaster {

	public Injury(int startCycle, Citizen target) {

		super(startCycle, target);
	}
	public void strike() {
		super.strike();
		getTarget().struckBy(this);
		int x = ((Citizen)(getTarget())).getBloodLoss();
		x+=30;
		((Citizen)(getTarget())).setBloodLoss(x);
	}
	public void cycleStep() {
		int x = ((Citizen)(getTarget())).getBloodLoss();
		x+=10;
		((Citizen)(getTarget())).setBloodLoss(x);
	}


}
