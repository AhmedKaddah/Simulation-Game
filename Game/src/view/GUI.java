package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;
import controller.CommandCenter;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Simulator;

public class GUI extends JFrame{
	private JPanel main;
	private JPanel left;
	private JPanel right;
	private JPanel middle;
	private JPanel RespondingUnits;
	private JPanel TreatingUnits;
	private JPanel  AvailbleUnits;
	private JPanel map;
	private DefaultListModel<String> InfoPanel;
	private DefaultListModel<String> log;
	private DefaultListModel<String> disasters;
	
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
		middle = new JPanel(new BorderLayout());
		main.setPreferredSize(new Dimension(1250, 720));
		left.setPreferredSize(new Dimension(400, 720));
		left.setBackground(Color.blue);
		right.setPreferredSize(new Dimension(250, 720));
		right.setBackground(Color.CYAN);
		middle.setPreferredSize(new Dimension(600,720));
		middle.setBackground(Color.red);
		main.add(middle,BorderLayout.CENTER);
		main.add(left,BorderLayout.WEST);
		main.add(right,BorderLayout.EAST);
		
		
		
		
		RespondingUnits = new JPanel(new FlowLayout());
		RespondingUnits.setPreferredSize(new Dimension(250, 240));
		TreatingUnits = new JPanel(new FlowLayout());
		TreatingUnits.setPreferredSize(new Dimension(250, 240));
		AvailbleUnits = new JPanel(new FlowLayout());
		AvailbleUnits.setPreferredSize(new Dimension(250, 240));
		right.add(AvailbleUnits,BorderLayout.NORTH);
		right.add(RespondingUnits,BorderLayout.CENTER);
		right.add(TreatingUnits,BorderLayout.SOUTH);
		RespondingUnits.add(new JLabel("Availble Units."));
		TreatingUnits.add(new JLabel("Treating Units."));
		AvailbleUnits.add(new JLabel("Reponding Units."));
		
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
		for (int i=0;i<c.getVisibleBuildings().size();i++) {
			if(temp.equals(c.getVisibleBuildings().get(i).getLocation())) {
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
				return;
			}
		}
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
		}
	}
	
	

	public JPanel getMap() {
		return map;
	}



	
}
