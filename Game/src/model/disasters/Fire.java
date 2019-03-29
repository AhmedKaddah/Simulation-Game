package model.disasters;

import model.infrastructure.ResidentialBuilding;

public class Fire extends Disaster {

	public Fire(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
	}
	
	public void strike() {
		super.strike();
		getTarget().struckBy(this);
		int x = ((ResidentialBuilding)(getTarget())).getFireDamage();
		x+=10;
		((ResidentialBuilding)(getTarget())).setFireDamage(x);
	}
	
	public void cycleStep() {
		int x = ((ResidentialBuilding)(getTarget())).getFireDamage();
		x+=10;
		((ResidentialBuilding)(getTarget())).setFireDamage(x);
	}

}
