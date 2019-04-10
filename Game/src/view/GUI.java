package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.tools.DocumentationTool.Location;

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
	private JTextArea InfoPanel;
	private JTextArea log;
	private JTextArea display;
	private JPanel next;
	
	public GUI() {
		this.setVisible(true);
		this.setTitle("Command-Center");
		this.setLayout(new BorderLayout());
		this.setSize(1200, 720);
		this.setLocation(100,0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.validate();
	
		main = new JPanel(new BorderLayout());
		left = new JPanel(new BorderLayout());
		right = new JPanel(new BorderLayout());
		middle = new JPanel(new BorderLayout());
		main.setPreferredSize(new Dimension(1200, 720));
		left.setPreferredSize(new Dimension(350, 720));
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
		
		
		
		map = new JPanel(new GridLayout(10,10));
		map.setPreferredSize(new Dimension(600, 600));
		next = new JPanel(new BorderLayout());
		next.setPreferredSize(new Dimension(600, 120));
		middle.add(map,BorderLayout.NORTH);
		middle.add(next,BorderLayout.CENTER);
		
		

		InfoPanel = new JTextArea();  
		InfoPanel.setPreferredSize(new Dimension(350, 450));
		log = new JTextArea();
		log.setPreferredSize(new Dimension(350, 242));
		display = new JTextArea();
		display.setPreferredSize(new Dimension(395, 120));
		log.setEditable(false);
		display.setEditable(false);
		left.add(InfoPanel,BorderLayout.NORTH);
		left.add(log,BorderLayout.SOUTH);
//		left.add(display,BorderLayout.CENTER);
		InfoPanel.setText("                                                   Info"+"\n");
		display.setText("                                                   DISPLAY");
		log.setText("                                                    LOG");
		log.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		InfoPanel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		display.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		
		
		
		this.add(main);

	}
	
	public void addStartGameButton(JButton b) {
		b.setPreferredSize(new Dimension(600, 120));
		next.add(b);
	}
	
	public void addNextCycleButton(JButton b) {
		next.add(display, BorderLayout.WEST);
		next.add(b,BorderLayout.EAST);
		b.setPreferredSize(new Dimension(200, 120));
	}
	
	public void addMapButtons(ArrayList<JButton> arr) {
		for(int i=0;i<arr.size();i++)
			map.add(arr.get(i));
	}
	public void updateCasulaties(Simulator s) {
		int c = s.calculateCasualties();
		display.setText("                                                   DISPLAY"+"\n"+"Casulaties: "+c+"\n"+"\n"+"Current Cycle: "+s.getCurrentCycle());
		
	}
	
	public void updateLog(Simulator s) {
		String result ="                                                    LOG"+"\n"+"\n"+"Executed Disaters: "+"\n";
		for(int i=0;i<s.getExecutedDisasters().size();i++) {
			if(s.getExecutedDisasters().get(i).getStartCycle() == s.getCurrentCycle()) {
				result+="A "+s.getExecutedDisasters().get(i)+" disaster has struck "+s.getExecutedDisasters().get(i).getTarget()+"."+"\n";
			}
		}
		result+="\n"+"Citizens that died this cycle: "+"\n";
		for(int i=0;i<s.getCitizens().size();i++) {
			if(s.getCitizens().get(i).getState() == CitizenState.DECEASED && s.getCitizens().get(i).isJustDied()) {
				result+=s.getCitizens().get(i) + " at location:  " +s.getCitizens().get(i).getLocation().getX()+", "+s.getCitizens().get(i).getLocation().getY() +"\n";
				s.getCitizens().get(i).setJustDied(false);
			}
		}
		
		result+="\n"+"Buildings that got destroyed this cycle: "+"\n";
		for(int i=0;i<s.getBuildings().size();i++) {
			if(s.getBuildings().get(i).getStructuralIntegrity() ==0 && s.getBuildings().get(i).isJustDied()) {
				result+=s.getBuildings().get(i) +"\n";
				s.getBuildings().get(i).setJustDied(false);
			}
		}
		log.setText(result);
	}
	public void updateInfo(Simulator s,CommandCenter c, int x,int y) {
		String result="                                                   Cell "+x+", "+y+" Info"+"\n";
		if(x==0&& y==0) {
			result="                                                   Base Info"+"\n";
		}
		Address temp = s.getWorld()[x][y];
		for (int i=0;i<c.getVisibleBuildings().size();i++) {
			if(temp.equals(c.getVisibleBuildings().get(i).getLocation())) {
				ResidentialBuilding b= c.getVisibleBuildings().get(i);
				result+= b.toString()+"\n"+"Structural Integrity: "+b.getStructuralIntegrity()+"\n"+"Fire Damage: "+b.getFireDamage()+"\n"+
				"Gas Level: "+ b.getGasLevel()+"\n"+"Foundation Damage: "+ b.getFoundationDamage()+"\n"+"Number Of Occupants: "+ b.getOccupants().size()+"\n";
				if(b.getDisaster()!=null &&b.getDisaster().isActive()) {
				result+= "Affected by: "+ b.getDisaster()+" Disaster"+"\n";
				}
				if (b.getOccupants().size()>0) {
					result+="\n"+"Citizens inside the building:"+"\n";
					for(int j=0; j<b.getOccupants().size();j++) {
						Citizen k= b.getOccupants().get(j);
						result+= k+" at "+ x+", "+y+"\n"+"Age: "+k.getAge()+"\n"+"National ID: "+k.getNationalID()+"\n"+"HP: "+k.getHp()+"\n"+"Blood Loss: "+k.getBloodLoss()+"\n"+
						"Toxicity: "+k.getToxicity()+"\n"+"State: "+k.getState()+"\n";
						if(k.getDisaster()!=null &&k.getDisaster().isActive()) {
							result+= "Affected by: "+ k.getDisaster()+" Disaster"+"\n";
							}
						result+="\n";
					}
				}
				InfoPanel.setText(result);
				return;
			}
		}
		String z1="";
		String z2="";
		for(int i=0;i<c.getVisibleCitizens().size();i++) {
			if(temp.equals(c.getVisibleCitizens().get(i).getLocation())) {
					z1="Citizens at this cell: "+"\n";
					Citizen k= c.getVisibleCitizens().get(i);
					z2+= k+" at "+ x+", "+y+"\n"+"Age: "+k.getAge()+"\n"+"National ID: "+k.getNationalID()+"\n"+"HP: "+k.getHp()+"\n"+"Blood Loss: "+k.getBloodLoss()+"\n"+
					"Toxicity: "+k.getToxicity()+"\n"+"State: "+k.getState()+"\n";
					if(k.getDisaster()!=null &&k.getDisaster().isActive()) {
						z2+= "Affected by: "+ k.getDisaster()+" Disaster"+"\n";
						}
					z2+="\n";

			}
		}
		InfoPanel.setText(result+z1+z2);
		
	}
	
	

	public JPanel getMap() {
		return map;
	}

	public JTextArea getInfoPanel() {
		return InfoPanel;
	}


	
}
