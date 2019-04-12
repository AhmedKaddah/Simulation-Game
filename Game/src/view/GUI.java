package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

import controller.CommandCenter;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Address;
import simulation.Simulator;

public class GUI extends JFrame{
	private JPanel main;
	private JPanel left;
	private JPanel right;
	private JPanel rightup;
	private JPanel middle;
	private JPanel RespondingUnits;
	private JPanel TreatingUnits;
	private JPanel  AvailbleUnits;
	private JPanel map;
	private DefaultListModel<String> InfoPanel;
	private DefaultListModel<String> log;
	private DefaultListModel<String> disasters;
	
	private DefaultListModel<String> unitInfo;
	private JTextArea display;
	private JPanel next;
	private JScrollPane sc;
	private JScrollPane sc2;
	public JScrollPane sc3;
	
	
	public GUI() {
		this.setVisible(true);
		this.setTitle("Command-Center");
		this.setLayout(new BorderLayout());
		this.setSize(1250, 720);
		this.setLocation(80,0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.validate();
	
		main = new JPanel(new BorderLayout());
		left = new JPanel(new BorderLayout());
		right = new JPanel(new BorderLayout());
		rightup = new JPanel(new BorderLayout());
		middle = new JPanel(new BorderLayout());
		main.setPreferredSize(new Dimension(1250, 720));
		left.setPreferredSize(new Dimension(400, 720));
		left.setBackground(Color.blue);
		right.setPreferredSize(new Dimension(250, 720));
		rightup.setPreferredSize(new Dimension(250, 480));
		right.setBackground(Color.CYAN);
		middle.setPreferredSize(new Dimension(600,720));
		middle.setBackground(Color.red);
		main.add(middle,BorderLayout.CENTER);
		main.add(left,BorderLayout.WEST);
		main.add(right,BorderLayout.EAST);
		
		
		unitInfo = new DefaultListModel<>(); 
		JList<String> d = new JList<>(unitInfo);
		JScrollPane sc4 = new JScrollPane(d,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sc4.setPreferredSize(new Dimension(250, 240));
		JLabel r4 = new JLabel("              Unit Info");
		r4.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
		sc4.setColumnHeaderView(r4);
		

		RespondingUnits = new JPanel(new FlowLayout());
		RespondingUnits.setPreferredSize(new Dimension(250, 160));
		TreatingUnits = new JPanel(new FlowLayout());
		TreatingUnits.setPreferredSize(new Dimension(250, 160));
		AvailbleUnits = new JPanel(new FlowLayout());
		AvailbleUnits.setPreferredSize(new Dimension(250, 160));
		
		rightup.add(AvailbleUnits,BorderLayout.NORTH);
		rightup.add(RespondingUnits,BorderLayout.CENTER);
		rightup.add(TreatingUnits,BorderLayout.SOUTH);
		right.add(rightup,BorderLayout.NORTH);
		right.add(sc4,BorderLayout.CENTER);
		Border BA = BorderFactory.createTitledBorder("                           Available Units");
		Border TU = BorderFactory.createTitledBorder("                           Treating Units");
		Border RU = BorderFactory.createTitledBorder("                           Responding Units");
		AvailbleUnits.setBorder(BA);
		TreatingUnits.setBorder(TU);
		RespondingUnits.setBorder(RU);
		
		disasters = new DefaultListModel<>(); 
		JList<String> c = new JList<>(disasters);
		JScrollPane sc3 = new JScrollPane(c,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sc3.setPreferredSize(new Dimension(300, 120));
		JLabel r3 = new JLabel("         Current Disasters");
		r3.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
		map = new JPanel(new GridLayout(10,10));
		map.setPreferredSize(new Dimension(600, 600));
		next = new JPanel(new BorderLayout());
		next.setPreferredSize(new Dimension(600, 120));
		next.add(sc3,BorderLayout.EAST);
		middle.add(map,BorderLayout.NORTH);
		middle.add(next,BorderLayout.CENTER);
		
		
		
		InfoPanel = new DefaultListModel<>(); 
		JList<String> b = new JList<>(InfoPanel);
		JScrollPane sc2 = new JScrollPane(b,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		log = new DefaultListModel<>();
		display = new JTextArea();
		display.setPreferredSize(new Dimension(200, 120));
		display.setEditable(false);
		left.add(sc2,BorderLayout.NORTH);
		JList<String> a = new JList<>(log);
		JScrollPane sc = new JScrollPane(a,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sc.setPreferredSize(new Dimension(400, 242));
		sc2.setPreferredSize(new Dimension(400, 450));
		JLabel r2 = new JLabel("                            INFO");
		r2.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
		JLabel r = new JLabel("                            LOG");
		r.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
		
		
		sc.setColumnHeaderView(r);
		sc2.setColumnHeaderView(r2);
		sc3.setColumnHeaderView(r3);
		left.add(sc,BorderLayout.SOUTH);
		left.add(sc2,BorderLayout.NORTH);
		

		
		
		
		this.add(main);

	}
	
	public void updateDisasters(CommandCenter c) {
		disasters.removeAllElements();
		for(int i =0;i<c.getVisibleBuildings().size();i++) {
			if(c.getVisibleBuildings().get(i).getDisaster().isActive() && c.getVisibleBuildings().get(i).getStructuralIntegrity()!=0) {
				disasters.addElement(c.getVisibleBuildings().get(i).getDisaster()+" on "+c.getVisibleBuildings().get(i));
			}
		}
		for(int i =0;i<c.getVisibleCitizens().size();i++) {
			if(c.getVisibleCitizens().get(i).getDisaster().isActive() && c.getVisibleCitizens().get(i).getHp()!=0) {
				disasters.addElement(c.getVisibleCitizens().get(i).getDisaster()+" on "+c.getVisibleCitizens().get(i)+" at "+c.getVisibleCitizens().get(i).getLocation().getX()+", "+c.getVisibleCitizens().get(i).getLocation().getY());
			}
		}
		
	}
	
	public void updateUnitInfo(Unit u) {
		unitInfo.removeAllElements();
		if(u instanceof FireTruck)
			unitInfo.addElement("Type: Fire Truck");
		if(u instanceof Evacuator)
			unitInfo.addElement("Type: Evacuator");
		if(u instanceof DiseaseControlUnit)
			unitInfo.addElement("Type: Disease Control Unit");
		if(u instanceof Ambulance)
			unitInfo.addElement("Type: Ambulance");
		if(u instanceof GasControlUnit)
			unitInfo.addElement("Type: Gas Control Unit");
		unitInfo.addElement("ID: "+u.getUnitID());
		unitInfo.addElement("Location: "+u.getLocation().getX()+","+u.getLocation().getY());
		unitInfo.addElement("Steps per cyle: "+u.getStepsPerCycle());
		unitInfo.addElement("State: "+u.getState());
		if(u.getTarget() instanceof Citizen)
			unitInfo.addElement("Target: Citizen at location "+u.getTarget().getLocation().getX()+","+u.getTarget().getLocation().getY());
		if(u.getTarget() instanceof ResidentialBuilding)
			unitInfo.addElement("Target: Building at location "+u.getTarget().getLocation().getX()+","+u.getTarget().getLocation().getY());
		if(u instanceof Evacuator) {
			unitInfo.addElement("Number of passengers: "+((Evacuator)u).getPassengers().size());
			if (((Evacuator) u).getPassengers().size()>0) {
				unitInfo.addElement("");
				unitInfo.addElement("");
				unitInfo.addElement("Citizens inside the Evacuator:");
				unitInfo.addElement("");
				unitInfo.addElement("");
				for(int i=0; i<((Evacuator) u).getPassengers().size();i++) {
					Citizen k= ((Evacuator) u).getPassengers().get(i);
					unitInfo.addElement( k+" at "+ u.getLocation().getX()+", "+u.getLocation().getY());
					unitInfo.addElement(("Age: "+k.getAge()));
					unitInfo.addElement("National ID: "+k.getNationalID());
					unitInfo.addElement("HP: "+k.getHp());
					unitInfo.addElement("Blood Loss: "+k.getBloodLoss());
					unitInfo.addElement("Toxicity: "+k.getToxicity());
					unitInfo.addElement("State: "+k.getState());
					unitInfo.addElement("---------------");
				}
			}
		}
	}
	public void addStartGameButton(JButton b) {
		b.setPreferredSize(new Dimension(600, 120));
		next.add(b);
	}
	
	public void addNextCycleButton(JButton b) {
		next.add(display, BorderLayout.WEST);
		next.add(b,BorderLayout.CENTER);
		b.setPreferredSize(new Dimension(5, 120));
	}
	
	public void addMapButtons(ArrayList<JButton> arr) {
		for(int i=0;i<arr.size();i++)
			map.add(arr.get(i));
	}
	public void updateCasulaties(Simulator s) {
		int c = s.calculateCasualties();
		display.setText("                         DISPLAY"+"\n"+"Casulaties: "+c+"\n"+"\n"+"Current Cycle: "+s.getCurrentCycle());
		
	}
	
	public void updateLog(Simulator s) {
		log.addElement("");
		log.addElement("                                                Cycle number "+s.getCurrentCycle());
		log.addElement("Executed Diasters: ");
		for(int i=0;i<s.getExecutedDisasters().size();i++) {
			if(s.getExecutedDisasters().get(i).getStartCycle() == s.getCurrentCycle()) {
				log.addElement("        "+"A "+s.getExecutedDisasters().get(i)+" disaster has struck "+s.getExecutedDisasters().get(i).getTarget()+".");
			}
		}
		log.addElement("Citizens that died this cycle: ");
		for(int i=0;i<s.getCitizens().size();i++) {
			if(s.getCitizens().get(i).getState() == CitizenState.DECEASED && s.getCitizens().get(i).isJustDied()) {
				log.addElement("        "+s.getCitizens().get(i) + " at location:  " +s.getCitizens().get(i).getLocation().getX()+", "+s.getCitizens().get(i).getLocation().getY());
				s.getCitizens().get(i).setJustDied(false);
			}
		}
		
		log.addElement("\n"+"Buildings that got destroyed this cycle: "+"\n");
		for(int i=0;i<s.getBuildings().size();i++) {
			if(s.getBuildings().get(i).getStructuralIntegrity() ==0 && s.getBuildings().get(i).isJustDied()) {
				log.addElement("        "+s.getBuildings().get(i));
				s.getBuildings().get(i).setJustDied(false);
			}
		}
	}
	public void updateInfo(Simulator s,CommandCenter c, int x,int y) {
		InfoPanel.removeAllElements();
		InfoPanel.addElement("                                                      Cell "+x+", "+y+" Info"+"\n");
		if(x==0&& y==0) {
			InfoPanel.removeAllElements();
			InfoPanel.addElement("                                                       Base Info"+"\n");
		}
		InfoPanel.addElement("");
		InfoPanel.addElement("");
		Address temp = s.getWorld()[x][y];
		boolean flag = false;
		for (int i=0;i<c.getVisibleBuildings().size();i++) {
			if(temp.equals(c.getVisibleBuildings().get(i).getLocation())) {
				flag=true;
				ResidentialBuilding b= c.getVisibleBuildings().get(i);
				InfoPanel.addElement( b.toString());
				InfoPanel.addElement("Structural Integrity: "+b.getStructuralIntegrity());
				InfoPanel.addElement("Fire Damage: "+b.getFireDamage());
				InfoPanel.addElement("Gas Level: "+ b.getGasLevel());
				InfoPanel.addElement("Foundation Damage: "+ b.getFoundationDamage());
				InfoPanel.addElement("Number Of Occupants: "+ b.getOccupants().size());
				if(b.getDisaster()!=null &&b.getDisaster().isActive()) {
					InfoPanel.addElement( "Affected by: "+ b.getDisaster()+" Disaster");
				}
				InfoPanel.addElement("---------------------");
				if (b.getOccupants().size()>0) {
					InfoPanel.addElement("                                Citizens inside the building:");
					for(int j=0; j<b.getOccupants().size();j++) {
						Citizen k= b.getOccupants().get(j);
						InfoPanel.addElement( k+" at "+ x+", "+y);
						InfoPanel.addElement(("Age: "+k.getAge()));
						InfoPanel.addElement("National ID: "+k.getNationalID());
						InfoPanel.addElement("HP: "+k.getHp());
						InfoPanel.addElement("Blood Loss: "+k.getBloodLoss());
						InfoPanel.addElement("Toxicity: "+k.getToxicity());
						InfoPanel.addElement("State: "+k.getState());
						if(k.getDisaster()!=null &&k.getDisaster().isActive()) {
							InfoPanel.addElement( "Affected by: "+ k.getDisaster()+" Disaster");
							}
						InfoPanel.addElement("---------------------");
					}
				}
				
			}
		}
		if(flag==false) {
		for(int i=0;i<c.getVisibleCitizens().size();i++) {
			if(temp.equals(c.getVisibleCitizens().get(i).getLocation())) {
				InfoPanel.addElement("                                Citizens at this cell: ");
					Citizen k= c.getVisibleCitizens().get(i);
					InfoPanel.addElement( k+" at "+ x+", "+y);
					InfoPanel.addElement(("Age: "+k.getAge()));
					InfoPanel.addElement("National ID: "+k.getNationalID());
					InfoPanel.addElement("HP: "+k.getHp());
					InfoPanel.addElement("Blood Loss: "+k.getBloodLoss());
					InfoPanel.addElement("Toxicity: "+k.getToxicity());
					InfoPanel.addElement("State: "+k.getState());
					if(k.getDisaster()!=null &&k.getDisaster().isActive()) {
						InfoPanel.addElement( "Affected by: "+ k.getDisaster()+" Disaster");
						}
					InfoPanel.addElement("---------------------");
			}
		}}
		for(int i=0; i<c.getEmergencyUnits().size();i++) {
			if(c.getEmergencyUnits().get(i).getLocation().getX()== x && c.getEmergencyUnits().get(i).getLocation().getY()== y) {
				InfoPanel.addElement("                                Units at this location:");
				break;
			}
		}
		for(int i=0; i<c.getEmergencyUnits().size();i++) {
			if(c.getEmergencyUnits().get(i).getLocation().equals(temp)) {
				InfoPanel.addElement(c.getEmergencyUnits().get(i).toString());
				
			if(c.getEmergencyUnits().get(i) instanceof Evacuator) {
				if(((Evacuator) c.getEmergencyUnits().get(i)).getPassengers().size()>0)
				InfoPanel.addElement("                             Citizens inside the Evacuator: ");
				for(int j=0;j<((Evacuator) c.getEmergencyUnits().get(i)).getPassengers().size();j++) {
				Citizen k= ((Evacuator) c.getEmergencyUnits().get(i)).getPassengers().get(j);
				InfoPanel.addElement( k+" at "+ x+", "+y);
				InfoPanel.addElement(("Age: "+k.getAge()));
				InfoPanel.addElement("National ID: "+k.getNationalID());
				InfoPanel.addElement("HP: "+k.getHp());
				InfoPanel.addElement("Blood Loss: "+k.getBloodLoss());
				InfoPanel.addElement("Toxicity: "+k.getToxicity());
				InfoPanel.addElement("State: "+k.getState());
				InfoPanel.addElement("---------------------");
				}}
			}
		}
	}
	
	public void updateUnits(CommandCenter c) {
		for(int i=0;i<c.getUnitButtons().size();i++) {
			AvailbleUnits.remove(c.getUnitButtons().get(i));
			AvailbleUnits.revalidate();
			TreatingUnits.remove(c.getUnitButtons().get(i));
			TreatingUnits.revalidate();
			RespondingUnits.remove(c.getUnitButtons().get(i));
			RespondingUnits.revalidate();
		}
		
		for(int i=0; i<c.getEmergencyUnits().size();i++) {
		if(c.getEmergencyUnits().get(i) instanceof Ambulance) {
			if(c.getEmergencyUnits().get(i).getState()== UnitState.IDLE) {
				AvailbleUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				RespondingUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				TreatingUnits.add(c.getUnitButtons().get(i));
			}
		}
		if(c.getEmergencyUnits().get(i) instanceof DiseaseControlUnit) {
			if(c.getEmergencyUnits().get(i).getState()== UnitState.IDLE) {
				AvailbleUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				RespondingUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				TreatingUnits.add(c.getUnitButtons().get(i));
			}
		}
		if(c.getEmergencyUnits().get(i) instanceof Evacuator) {
			if(c.getEmergencyUnits().get(i).getState()== UnitState.IDLE) {
				AvailbleUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				RespondingUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				TreatingUnits.add(c.getUnitButtons().get(i));
			}
		}
		if(c.getEmergencyUnits().get(i) instanceof FireTruck) {
			if(c.getEmergencyUnits().get(i).getState()== UnitState.IDLE) {
				AvailbleUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				RespondingUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				TreatingUnits.add(c.getUnitButtons().get(i));
			}
		}
		if(c.getEmergencyUnits().get(i) instanceof GasControlUnit) {
			if(c.getEmergencyUnits().get(i).getState()== UnitState.IDLE) {
				AvailbleUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				RespondingUnits.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				TreatingUnits.add(c.getUnitButtons().get(i));
			}
		}
			
			
		c.getUnitButtons().get(i).setVisible(false);
		c.getUnitButtons().get(i).setVisible(true);
		
		}
		AvailbleUnits.repaint();
		TreatingUnits.repaint();
		RespondingUnits.repaint();
	}

	public JPanel getMap() {
		return map;
	}

	public JPanel getRespondingUnits() {
		return RespondingUnits;
	}

	public JPanel getTreatingUnits() {
		return TreatingUnits;
	}

	public JPanel getAvailbleUnits() {
		return AvailbleUnits;
	}

	public JPanel getRight() {
		return right;
	}



	
}
