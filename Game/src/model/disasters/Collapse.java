package model.disasters;

import model.infrastructure.ResidentialBuilding;

public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {

		super(startCycle, target);
	}
	public void strike() {
		int x = ((ResidentialBuilding)(getTarget())).getFoundationDamage();
		x+=10;
		((ResidentialBuilding)(getTarget())).setFoundationDamage(x);
	}
	public void cycleStep() {
		int x = ((ResidentialBuilding)(getTarget())).getFoundationDamage();
		x+=10;
		((ResidentialBuilding)(getTarget())).setFoundationDamage(x);
	}
	
}
