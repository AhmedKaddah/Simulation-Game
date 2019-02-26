package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import model.disasters.Disaster;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.*;
import model.disasters.*;

public class Simulator {
	private int currentCycle;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][] world;

	public Simulator() throws IOException {
		world = new Address[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				world[i][j] = new Address(i, j);
		}
		emergencyUnits = new ArrayList<>();
		plannedDisasters = new ArrayList<>();
		executedDisasters = new ArrayList<>();
		buildings = new ArrayList<>();
		citizens = new ArrayList<>();
		loadUnits("units.csv");
		loadBuildings("buildings.csv");
		loadCitizens("citizens.csv");
		loadDisasters("disasters.csv");
	}

	private void loadUnits(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");
			switch (result[0]) {
			case "AMB":
				emergencyUnits.add(new Ambulance(result[1], world[0][0], Integer.parseInt(result[2])));
				break;
			case "DCU":
				emergencyUnits.add(new DiseaseControlUnit(result[1], world[0][0], Integer.parseInt(result[2])));
				break;
			case "EVC":
				emergencyUnits.add(new Evacuator(result[1], world[0][0], Integer.parseInt(result[2]),
						Integer.parseInt(result[3])));
				break;
			case "FTK":
				emergencyUnits.add(new FireTruck(result[1], world[0][0], Integer.parseInt(result[2])));
				break;
			case "GCU":
				emergencyUnits.add(new GasControlUnit(result[1], world[0][0], Integer.parseInt(result[2])));
				break;
			}
		}
	}

	private void loadBuildings(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");
			buildings.add(new ResidentialBuilding(world[Integer.parseInt(result[0])][Integer.parseInt(result[1])]));
		}
	}

	private void loadCitizens(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");
			citizens.add(new Citizen(world[Integer.parseInt(result[0])][Integer.parseInt(result[1])], result[2],
					result[3], Integer.parseInt(result[4])));
		}
	}

	private void loadDisasters(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");
			switch (result[1]) {
			case "INJ":
				plannedDisasters.add(new Injury(Integer.parseInt(result[0]), helper(result[2])));
				break;
			case "INF":
				plannedDisasters.add(new Infection(Integer.parseInt(result[0]), helper(result[2])));
				break;
			case "FIR":
				plannedDisasters.add(new Fire(Integer.parseInt(result[0]),
						helper1(Integer.parseInt(result[2]), Integer.parseInt(result[3]))));
				break;
			case "GLK":
				plannedDisasters.add(new GasLeak(Integer.parseInt(result[0]),
						helper1(Integer.parseInt(result[2]), Integer.parseInt(result[3]))));
				break;
			}

		}
	}

	public Citizen helper(String id) {
		int position =0;
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getNationalID() == id) {
				position = i;
				break;
			}
		}
		return citizens.get(position);
		//hi
	}

	public ResidentialBuilding helper1(int x, int y) {
		Address temp = new Address(x, y);
		int position = 0;
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).getLocation().equals(temp)) {
				position=i;
				break;
			}
		}
		return buildings.get(position);
	}
}
