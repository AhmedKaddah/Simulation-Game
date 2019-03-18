package model.disasters;

import model.infrastructure.ResidentialBuilding;

public class GasLeak extends Disaster {

	public GasLeak(int startCycle, ResidentialBuilding target) {

		super(startCycle, target);
	}
	public void strike() {
		int x = ((ResidentialBuilding)(getTarget())).getGasLevel();
		x+=10;
		((ResidentialBuilding)(getTarget())).setGasLevel(x);
	}
	public void cycleStep() {
		int x = ((ResidentialBuilding)(getTarget())).getGasLevel();
		x+=15;
		((ResidentialBuilding)(getTarget())).setGasLevel(x);
	}
}
