package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

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
	private JScrollPane RespondingUnits;
	private JScrollPane TreatingUnits;
	private JScrollPane  AvailbleUnits;
	private JPanel map;
	private DefaultListModel<String> InfoPanel;
	private DefaultListModel<String> log;
	private DefaultListModel<String> disasters;
	private DefaultListModel<String> displayy;
	
	private DefaultListModel<String> unitInfo;
	private JTextArea display;
	private JPanel next;
	private JScrollPane sc;
	private JScrollPane sc2;
	public JScrollPane sc3;
	public JScrollBar sb;
	private JPanel u1;
	private JPanel container1;
	private JPanel u2;
	private JPanel container2;
	private JPanel u3;
	private JPanel container3;
	private ImageIcon infopic;
	private ImageIcon logpic;
	private ImageIcon available;
	private ImageIcon responding;
	private ImageIcon treating;
	private ImageIcon unitinfopic;
	private ImageIcon dispic;
	private ImageIcon displaypic;
	private JScrollPane displayPanel;
	
	public GUI() {
		u1 = new JPanel(new GridLayout(0, 3,10,10));
		u1.setBackground(Color.black);
		container1 = new JPanel();
		u2 = new JPanel(new GridLayout(0, 3,10,10));
		u2.setBackground(Color.black);
		container2 = new JPanel();
		u3 = new JPanel(new GridLayout(0, 3,10,10));
		u3.setBackground(Color.black);
		container3 = new JPanel();
		this.setTitle("Command-Center");
		this.setLayout(new BorderLayout());
		this.setSize(1250, 720);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
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
		JLabel uf = new JLabel();
		unitinfopic = new ImageIcon("unitinfo.png");
		uf.setIcon(unitinfopic);
		uf.setHorizontalAlignment(JLabel.CENTER);
		uf.setVerticalAlignment(JLabel.CENTER);
		sc4.setColumnHeaderView(uf);
		

		RespondingUnits = new JScrollPane(container2);
		RespondingUnits.setPreferredSize(new Dimension(250, 160));
		RespondingUnits.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		RespondingUnits.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		TreatingUnits = new JScrollPane(container3);
		TreatingUnits.setPreferredSize(new Dimension(250, 160));
		TreatingUnits.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		TreatingUnits.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		AvailbleUnits = new JScrollPane(container1);
		AvailbleUnits.setPreferredSize(new Dimension(250, 160));
		AvailbleUnits.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		AvailbleUnits.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		rightup.add(AvailbleUnits,BorderLayout.NORTH);
		rightup.add(RespondingUnits,BorderLayout.CENTER);
		rightup.add(TreatingUnits,BorderLayout.SOUTH);
		right.add(rightup,BorderLayout.NORTH);
		right.add(sc4,BorderLayout.CENTER);
		
		JLabel u = new JLabel();
		available = new ImageIcon("available.png");
		u.setIcon(available);
		u.setHorizontalAlignment(JLabel.CENTER);
		u.setVerticalAlignment(JLabel.CENTER);
		
		JLabel u1 = new JLabel();
		responding = new ImageIcon("responding.png");
		u1.setIcon(responding);
		u1.setHorizontalAlignment(JLabel.CENTER);
		u1.setVerticalAlignment(JLabel.CENTER);
		
		JLabel u2 = new JLabel();
		treating = new ImageIcon("treating.png");
		u2.setIcon(treating);
		u2.setHorizontalAlignment(JLabel.CENTER);
		u2.setVerticalAlignment(JLabel.CENTER);
		
		AvailbleUnits.setColumnHeaderView(u);
		RespondingUnits.setColumnHeaderView(u1);
		TreatingUnits.setColumnHeaderView(u2);
		
		disasters = new DefaultListModel<>(); 
		JList<String> c = new JList<>(disasters);
		JScrollPane sc3 = new JScrollPane(c,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sc3.setPreferredSize(new Dimension(300, 120));
		JLabel dis = new JLabel();
		dispic = new ImageIcon("disaster.png");
		dis.setIcon(dispic);
		dis.setHorizontalAlignment(JLabel.CENTER);
		dis.setVerticalAlignment(JLabel.CENTER);
		sc3.setColumnHeaderView(dis);
		
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
		displayy = new DefaultListModel<>(); 
		JList<String> f = new JList<>(displayy);
		displayPanel = new JScrollPane(f,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		displayPanel.setPreferredSize(new Dimension(200, 120));
		left.add(sc2,BorderLayout.NORTH);
		JList<String> a = new JList<>(log);
		JScrollPane sc = new JScrollPane(a,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JLabel display = new JLabel();
		displaypic = new ImageIcon("display.png");
		display.setIcon(displaypic);
		display.setHorizontalAlignment(JLabel.CENTER);
		display.setVerticalAlignment(JLabel.CENTER);
		displayPanel.setColumnHeaderView(display);
		
		sc.setPreferredSize(new Dimension(400, 242));
		sc2.setPreferredSize(new Dimension(400, 450));
		JLabel r2 = new JLabel();
		infopic = new ImageIcon("Info.png");
		r2.setIcon(infopic);
		r2.setHorizontalAlignment(JLabel.CENTER);
		r2.setVerticalAlignment(JLabel.CENTER);
		
		infopic = new ImageIcon("Info.png");
		r2.setIcon(infopic);
		
		JLabel r = new JLabel();
		logpic = new ImageIcon("log.png");
		r.setIcon(logpic);
		r.setHorizontalAlignment(JLabel.CENTER);
		r.setVerticalAlignment(JLabel.CENTER);
		sb = sc.getVerticalScrollBar();
		
		sc.setColumnHeaderView(r);
		sc2.setColumnHeaderView(r2);
		sc3.setColumnHeaderView(dis);
		left.add(sc,BorderLayout.SOUTH);
		left.add(sc2,BorderLayout.NORTH);
		
		sc.setBackground(Color.BLACK);
		sc2.setBackground(Color.BLACK);
		sc3.setBackground(Color.BLACK);
		displayPanel.setBackground(Color.BLACK);
		RespondingUnits.setBackground(Color.BLACK);
		TreatingUnits.setBackground(Color.BLACK);
		AvailbleUnits.setBackground(Color.BLACK);
		
		sc.getVerticalScrollBar().setUI(new MyScrollbarUI());
		sc2.getVerticalScrollBar().setUI(new MyScrollbarUI());
		sc3.getVerticalScrollBar().setUI(new MyScrollbarUI());
		displayPanel.getVerticalScrollBar().setUI(new MyScrollbarUI());
		RespondingUnits.getVerticalScrollBar().setUI(new MyScrollbarUI());
		TreatingUnits.getVerticalScrollBar().setUI(new MyScrollbarUI());
		AvailbleUnits.getVerticalScrollBar().setUI(new MyScrollbarUI());

//		sc.getVerticalScrollBar().setBackground(Color.BLACK);
//		sc2.getVerticalScrollBar().setBackground(Color.BLACK);
//		sc3.getVerticalScrollBar().setBackground(Color.BLACK);
//		displayPanel.getVerticalScrollBar().setBackground(Color.BLACK);
//		RespondingUnits.getVerticalScrollBar().setBackground(Color.BLACK);
//		TreatingUnits.getVerticalScrollBar().setBackground(Color.BLACK);
//		AvailbleUnits.getVerticalScrollBar().setBackground(Color.BLACK);
		
		a.setBackground(Color.BLACK);
		b.setBackground(Color.BLACK);
		c.setBackground(Color.BLACK);
		d.setBackground(Color.BLACK);
		f.setBackground(Color.BLACK);
		container1.setBackground(Color.BLACK);
		container2.setBackground(Color.BLACK);
		container3.setBackground(Color.BLACK);
		u1.setBackground(Color.BLACK);
		u2.setBackground(Color.BLACK);
		u3.setBackground(Color.BLACK);
		
		a.setForeground(Color.WHITE);
		b.setForeground(Color.WHITE);
		c.setForeground(Color.WHITE);
		d.setForeground(Color.WHITE);
		f.setForeground(Color.WHITE);
		
		a.setSelectionModel(new DisabledItemSelectionModel());
		b.setSelectionModel(new DisabledItemSelectionModel());
		c.setSelectionModel(new DisabledItemSelectionModel());
		d.setSelectionModel(new DisabledItemSelectionModel());
		f.setSelectionModel(new DisabledItemSelectionModel());
		
		this.add(main);
		map.setBorder(new MatteBorder(5, 2, 5, 2, new Color(74, 163, 232)));
		sc.setBorder(new MatteBorder(0, 2, 2, 0, new Color(74, 163, 232)));
		sc2.setBorder(new MatteBorder(2, 2, 2, 0, new Color(74, 163, 232)));
		sc3.setBorder(new MatteBorder(0, 2, 2, 2, new Color(74, 163, 232)));
		displayPanel.setBorder(new MatteBorder(0, 2, 2, 2, new Color(74, 163, 232)));
		RespondingUnits.setBorder(new MatteBorder(0, 0, 2, 2, new Color(74, 163, 232)));
		TreatingUnits.setBorder(new MatteBorder(0, 0, 0, 2, new Color(74, 163, 232)));
		AvailbleUnits.setBorder(new MatteBorder(2,0, 2, 2, new Color(74, 163, 232)));
		sc4.setBorder(new MatteBorder(2, 0, 2, 2, new Color(74, 163, 232)));
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

	
	public void addNextCycleButton(JButton b) {
		next.add(displayPanel, BorderLayout.WEST);
		next.add(b,BorderLayout.CENTER);
		b.setPreferredSize(new Dimension(5, 120));
	}
	
	public void addMapButtons(ArrayList<JButton> arr) {
		for(int i=0;i<arr.size();i++)
			map.add(arr.get(i));
	}
	public void updateCasulaties(Simulator s) {
		displayy.removeAllElements();
		int c = s.calculateCasualties();
		displayy.addElement("Casulaties: "+c);
		displayy.addElement("Current Cycle: "+s.getCurrentCycle());
		
	}
	
	public void updateLog(Simulator s) {
		if(s.getCurrentCycle()>1) {
			log.addElement("                                   _______________________");
		}
		log.addElement("");
		log.addElement("                                                Cycle number "+s.getCurrentCycle());
		log.addElement(" ");
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
		InfoPanel.addElement(" ");
		if(x==0&& y==0) {
			InfoPanel.removeAllElements();
			InfoPanel.addElement("                                                       Base Info"+"\n");
			InfoPanel.addElement(" ");
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
				InfoPanel.addElement("------------------------------------------");
				if (b.getOccupants().size()>0) {
					InfoPanel.addElement("                                    Citizens inside this building:");
					InfoPanel.addElement(" ");
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
						if(j+1 != b.getOccupants().size())
						InfoPanel.addElement("------------------------------------------");
					}
				}
				
			}
		}
		if(flag==false) {
		for(int i=0;i<c.getVisibleCitizens().size();i++) {
			if(temp.equals(c.getVisibleCitizens().get(i).getLocation())) {
				InfoPanel.addElement("                                             Citizens at this cell: ");
				InfoPanel.addElement(" ");
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
					if(i+1 != c.getVisibleCitizens().size())
					InfoPanel.addElement("------------------------------------------");
			}
		}}
		for(int i=0; i<c.getEmergencyUnits().size();i++) {
			if(c.getEmergencyUnits().get(i).getLocation().getX()== x && c.getEmergencyUnits().get(i).getLocation().getY()== y) {
				InfoPanel.addElement("                                          Units at this location:");
				InfoPanel.addElement(" ");
				break;
			}
		}
		for(int i=0; i<c.getEmergencyUnits().size();i++) {
			if(c.getEmergencyUnits().get(i).getLocation().equals(temp)) {
				InfoPanel.addElement(c.getEmergencyUnits().get(i).toString()+":");
				InfoPanel.addElement("ID: "+c.getEmergencyUnits().get(i).getUnitID());
				InfoPanel.addElement("Steps per cyle: "+c.getEmergencyUnits().get(i).getStepsPerCycle());
				InfoPanel.addElement("Unit State: "+c.getEmergencyUnits().get(i).getState());
				if(c.getEmergencyUnits().get(i).getTarget()!=null)
				{
					if(c.getEmergencyUnits().get(i).getTarget() instanceof Citizen) {
						InfoPanel.addElement("Target: Citizen at "+c.getEmergencyUnits().get(i).getTarget().getLocation().getX()+", "+c.getEmergencyUnits().get(i).getTarget().getLocation().getY());
					}
					else {
						InfoPanel.addElement("Target: Building at "+c.getEmergencyUnits().get(i).getTarget().getLocation().getX()+", "+c.getEmergencyUnits().get(i).getTarget().getLocation().getY());
					}
				}
				
			if(c.getEmergencyUnits().get(i) instanceof Evacuator) {
				if(((Evacuator) c.getEmergencyUnits().get(i)).getPassengers().size()>0)
				InfoPanel.addElement("                                    Citizens inside the Evacuator: ");
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
				}
				if(((Evacuator) c.getEmergencyUnits().get(i)).getPassengers().size()==0) {
					InfoPanel.addElement("------------------------------------------");
				}}
			else {
				if (c.getEmergencyUnits().size()!=i+1)
				InfoPanel.addElement("------------------------------------------");
			}
				
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
				u1.add(c.getUnitButtons().get(i));
				
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				u2.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				u3.add(c.getUnitButtons().get(i));
			}
		}
		if(c.getEmergencyUnits().get(i) instanceof DiseaseControlUnit) {
			if(c.getEmergencyUnits().get(i).getState()== UnitState.IDLE) {
				u1.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				u2.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				u3.add(c.getUnitButtons().get(i));
			}
		}
		if(c.getEmergencyUnits().get(i) instanceof Evacuator) {
			if(c.getEmergencyUnits().get(i).getState()== UnitState.IDLE) {
				u1.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				u2.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				u3.add(c.getUnitButtons().get(i));
			}
		}
		if(c.getEmergencyUnits().get(i) instanceof FireTruck) {
			if(c.getEmergencyUnits().get(i).getState()== UnitState.IDLE) {
				u1.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				u2.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				u3.add(c.getUnitButtons().get(i));
			}
		}
		if(c.getEmergencyUnits().get(i) instanceof GasControlUnit) {
			if(c.getEmergencyUnits().get(i).getState()== UnitState.IDLE) {
				u1.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.RESPONDING) {
				u2.add(c.getUnitButtons().get(i));
			}
			if(c.getEmergencyUnits().get(i).getState()== UnitState.TREATING) {
				u3.add(c.getUnitButtons().get(i));
			}
		}
			
			
		c.getUnitButtons().get(i).setVisible(false);
		c.getUnitButtons().get(i).setVisible(true);
		
		}
		
		container1.add(u1);
		container2.add(u2);
		container3.add(u3);
		AvailbleUnits.repaint();
		TreatingUnits.repaint();
		RespondingUnits.repaint();
	}

	public JPanel getMap() {
		return map;
	}


	public JPanel getRight() {
		return right;
	}



	
}
