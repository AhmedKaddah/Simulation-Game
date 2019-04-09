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

import model.people.CitizenState;
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
		this.validate();
		this.setTitle("Command-Center");
		this.setLayout(new BorderLayout());
		this.setSize(1200, 600);
		this.setLocation(100,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	
		main = new JPanel(new BorderLayout());
		left = new JPanel(new BorderLayout());
		right = new JPanel(new BorderLayout());
		middle = new JPanel(new BorderLayout());
		main.setPreferredSize(new Dimension(1200, 600));
		left.setPreferredSize(new Dimension(350, 600));
		left.setBackground(Color.blue);
		right.setPreferredSize(new Dimension(250, 600));
		right.setBackground(Color.CYAN);
		middle.setPreferredSize(new Dimension(600,600));
		middle.setBackground(Color.red);
		main.add(middle,BorderLayout.CENTER);
		main.add(left,BorderLayout.WEST);
		main.add(right,BorderLayout.EAST);
		
		
		
		
		RespondingUnits = new JPanel(new FlowLayout());
		RespondingUnits.setPreferredSize(new Dimension(250, 200));
		TreatingUnits = new JPanel(new FlowLayout());
		TreatingUnits.setPreferredSize(new Dimension(250, 200));
		AvailbleUnits = new JPanel(new FlowLayout());
		AvailbleUnits.setPreferredSize(new Dimension(250, 200));
		right.add(AvailbleUnits,BorderLayout.NORTH);
		right.add(RespondingUnits,BorderLayout.CENTER);
		right.add(TreatingUnits,BorderLayout.SOUTH);
		RespondingUnits.add(new JLabel("Availble Units."));
		TreatingUnits.add(new JLabel("Treating Units."));
		AvailbleUnits.add(new JLabel("Reponding Units."));
		
		
		
		map = new JPanel(new GridLayout(10,10));
		map.setPreferredSize(new Dimension(600, 500));
		next = new JPanel(new FlowLayout());
		next.setPreferredSize(new Dimension(600, 100));
		middle.add(map,BorderLayout.NORTH);
		middle.add(next,BorderLayout.CENTER);
		
		

		InfoPanel = new JTextArea();  
		InfoPanel.setPreferredSize(new Dimension(350, 300));
		log = new JTextArea();
		log.setPreferredSize(new Dimension(350, 170));
		display = new JTextArea();
		display.setPreferredSize(new Dimension(350, 130));
		log.setEditable(false);
		InfoPanel.setEditable(false);
		display.setEditable(false);
		left.add(InfoPanel,BorderLayout.NORTH);
		left.add(log,BorderLayout.SOUTH);
		left.add(display,BorderLayout.CENTER);
		InfoPanel.setText("Info.");
		display.setText("                                                   DISPLAY");
		log.setText("                                                    LOG");
		log.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		display.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		
		
		this.add(main);

	}
	
	public void addNextCycleButton(JButton b) {
		next.add(b);
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
		String result ="                                                    LOG"+"\n" + "Current cycle: "+s.getCurrentCycle()+"\n"+"\n"+"Executed Disaters: "+"\n";
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
		log.setText(result);
	}
	
	public void updateInfo(Simulator s,Location l) {
		
	}

	public JPanel getMap() {
		return map;
	}
	
}
