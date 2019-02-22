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
private ArrayList<Disaster> plannedDisasters;
private ArrayList<Disaster> executedDisasters;
private Address[][]world;

	public Simulator() {
		world= new Address[10][10];
		for(int i=1; i<=10;i++) {
			for(int j=1; j<=10;j++)
				world[i][j]= new Address(i,j);			
		}
	//  The ArrayLists of ResidentialBuildings, Citizens, emergencyUnits and plannedDisasters are initially loaded from csv files.
	}
}
