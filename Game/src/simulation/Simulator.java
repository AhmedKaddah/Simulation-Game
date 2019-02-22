package simulation;

import java.util.ArrayList;

import model.disasters.Disaster;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;

public class Simulator {
private int currentCycle;
private ArrayList<ResidentialBuilding> buildings;
private ArrayList<Citizen> citizens;
private ArrayList<Unit> emergencyUnits;
//private ArrayList<Disaster> plannedDisasters;
private ArrayList<Disaster> executedDisasters;
private Address[][]world;

	public Simulator() {
		
	}
}
