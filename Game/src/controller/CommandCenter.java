package controller;
import java.util.ArrayList;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;

public class CommandCenter {
	Simulator engine;
	ArrayList<ResidentialBuilding> visibleBuildings;
	ArrayList<Citizen> visibleCitizens;
	ArrayList<Unit> emergencyUnits;
	public CommandCenter() {
		engine = new Simulator();
		visibleBuildings = new ArrayList<>();
		visibleCitizens = new ArrayList<>();
		emergencyUnits = new ArrayList<>();
	}
}
