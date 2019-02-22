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
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++)
				world[i][j] = new Address(i, j);
		}
		loadUnits("units.csv");
		loadBuildings("buildings.csv");
		loadCitizens("citizens.csv");
		loadDisasters("disaters.csv");
	}

	private void loadUnits(String filePath) throws IOException{
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");
			switch(result[0]) {
			case "AMB":emergencyUnits.add(new Ambulance(result[1],new Address(0,0),Integer.parseInt(result[2])));break;
			case "DCU":emergencyUnits.add(new DiseaseControlUnit(result[1],new Address(0,0),Integer.parseInt(result[2])));break;
			case "EVC":emergencyUnits.add(new Evacuator(result[1],new Address(0,0),Integer.parseInt(result[2]),Integer.parseInt(result[3])));break;
			case "FTK":emergencyUnits.add(new FireTruck(result[1],new Address(0,0),Integer.parseInt(result[2])));break;
			case "GCU":emergencyUnits.add(new GasControlUnit(result[1],new Address(0,0),Integer.parseInt(result[2])));break;
			}
		}
	}
	private void loadBuildings(String filePath) throws IOException{
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");
			buildings.add(new ResidentialBuilding(new Address(Integer.parseInt(result[0]),Integer.parseInt(result[1]))));
		}
	}
	private void loadCitizens(String filePath) throws IOException{
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");
			citizens.add(new Citizen(new Address(Integer.parseInt(result[0]),Integer.parseInt(result[1])),result[2],result[3],Integer.parseInt(result[4])));
		}
	}
	private void loadDisasters(String filePath) throws IOException{
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");
			switch(result[1]) {
				case "INJ": plannedDisasters.add(new Injury(Integer.parseInt(result[0]),));
				case "INF": plannedDisasters.add(new Infection(Integer.parseInt(result[0]),));
				case "FIR": plannedDisasters.add(new Fire(Integer.parseInt(result[0]),new ResidentialBuilding(new Address(Integer.parseInt(result[2]),Integer.parseInt(result[3])))));
				case "GLK": plannedDisasters.add(new GasLeak(Integer.parseInt(result[0]),new ResidentialBuilding(new Address(Integer.parseInt(result[2]),Integer.parseInt(result[3])))));
			}
		}
	}
}
