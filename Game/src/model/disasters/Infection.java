package model.disasters;
import model.infrastructure.ResidentialBuilding;

public class Infection extends Disaster {
	public  Infection(int cycle, ResidentialBuilding target) {
		super(cycle,target);
	}
}
