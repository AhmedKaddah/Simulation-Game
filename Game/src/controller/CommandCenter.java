package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulator;
import view.GUI;

public class CommandCenter implements SOSListener,ActionListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private GUI g;
	private JButton nextCycle = new JButton("Next Cycle");
	private ArrayList<JButton> mapButtons;


	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;

	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		g = new GUI();
		g.addNextCycleButton(nextCycle);
		nextCycle.addActionListener(this);
		mapButtons = new ArrayList<JButton>();
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				JButton temp = new JButton(i+", "+j);
				temp.addActionListener(this);
				mapButtons.add(temp);
			}
		}
		g.addMapButtons(mapButtons);
	}

	@Override
	public void receiveSOSCall(Rescuable r) {
		
		if (r instanceof ResidentialBuilding) {
			
			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);
			
		} else {
			
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}

	public Simulator getEngine() {
		return engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton b= (JButton) e.getSource();
		if(b.equals(nextCycle)) {
			engine.nextCycle();
			g.updateCasulaties(engine);
			g.updateLog(engine);
			if(engine.checkGameOver()) {
				g.dispose();
				JFrame x = new JFrame("Game Over");
				JLabel s = new JLabel("      Game Over!!");
				s.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
				s.setForeground(Color.RED);
				x.add(s,BorderLayout.CENTER);
				x.setVisible(true);
				x.setSize(500, 300);
				x.setLocation(300,100);
				x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				x.setResizable(false);
			}
			
			
			
		}
		if(mapButtons.contains(b)) {
			int g= mapButtons.indexOf(b) / 10;
			int h= mapButtons.indexOf(b) % 10;
		}
	}
//	public void updateInfo(int x, int y) {
//		String result= "                                                   Cell Info"+ "\n" +"\n"+ "Building:" +"\n";
//		Address temp = engine.getWorld()[x][y];
//		for(int i=0;i<visibleBuildings.size();i++) {
//			if(visibleBuildings.get(i).getLocation());
//		}}
//	public void updateButtons(int a, int b) {
//		for(int i=0;i<visibleBuildings.size();i++)
//			if(visibleBuildings.get(i).getLocation().getX()==a && visibleBuildings.get(i).getLocation().getX()==b)
//				g.getInfoPanel().add(new JButton(visibleBuildings.toString()));
//		
//		for(int i=0;i<visibleCitizens.size();i++)
//			if(visibleCitizens.get(i).getLocation().getX()==a && visibleCitizens.get(i).getLocation().getX()==b)
//				g.getInfoPanel().add(new JButton(visibleCitizens.toString()));
//	
//	}
		
				
	
	
	public static void main(String[] args) throws Exception {
		CommandCenter com = new CommandCenter();
	}

	

}
