package model.people;

import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;
import model.disasters.Disaster;
import model.events.SOSListener;
import model.events.WorldListener;

public class Citizen implements Rescuable, Simulatable {

	private CitizenState state;
	private Disaster disaster;
	private String name;
	private String nationalID;
	private int age;
	private int hp;
	private int bloodLoss;
	private int toxicity;
	private Address location;
	private SOSListener emergencyService;
	private WorldListener worldListener;

	public Citizen(Address location, String nationalID, String name, int age, WorldListener worldListener ) {
		this.name = name;
		this.nationalID = nationalID;
		this.age = age;
		this.location = location;
		this.state = CitizenState.SAFE;
		this.hp = 100;
		this.worldListener=worldListener;
	}

	public CitizenState getState() {
		return state;
	}

	public void setState(CitizenState state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if(hp>100) {
			this.hp=100;
		}
		else {
			if(hp<=0) {
				this.hp=0;
				setState(CitizenState.DECEASED);
			}
			else {
				this.hp=hp;
			}
		}
	}
		
	public int getBloodLoss() {
		return bloodLoss;
	}

	public void setBloodLoss(int bloodLoss) {
		if(bloodLoss>=100) {
			this.bloodLoss=100;
			setHp(0);
		}
		else {
			if(bloodLoss<0) {
				this.bloodLoss=0;
			}
			else {
				this.bloodLoss=bloodLoss;
			}
		}
	}

	public int getToxicity() {
		return toxicity;
	}

	public void setToxicity(int toxicity) {
		if(toxicity>=100) {
			this.toxicity=100;
			setHp(0);
		}
		else {
			if(toxicity<0) {
				this.toxicity=0;
			}
			else {
				this.toxicity=toxicity;
			}
		}
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public Disaster getDisaster() {
		return disaster;
	}

	public String getNationalID() {
		return nationalID;
	}
	
	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}
	
	public void cycleStep() {
		if(getBloodLoss()>0 && getBloodLoss()<30) {
			setHp(getHp()-5);
		}
		if(getBloodLoss()>=30 && getBloodLoss()<70){
				setHp(getHp()-10);
			}
		if(getBloodLoss()>=70)
				setHp(getHp()-15);
		
		if(getToxicity()>0 && getToxicity()<30) {
			setHp(getHp()-5);
		}
		if(getToxicity()>=30 && getToxicity()<70){
			setHp(getHp()-10);
		}
		if(getToxicity()>=70)
			setHp(getHp()-15);
	}
	
	public void struckBy(Disaster d) {
		d.setActive(true);
		setState(CitizenState.IN_TROUBLE);
		this.disaster=d;
		emergencyService.receiveSOSCall(this);
	}
	
}
