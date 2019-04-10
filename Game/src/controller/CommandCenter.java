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
import javax.swing.UIManager;

import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulator;
import view.GUI;
//fsf
public class CommandCenter implements SOSListener,ActionListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private GUI g;
	private JButton startGame = new JButton("Start Game");
	private JButton nextCycle = new JButton("Next Cycle");
	private ArrayList<JButton> mapButtons;
	private int j;
	private int k;
	private CommandCenter command=this;
	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;

	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		g = new GUI();
		g.addStartGameButton(startGame);
		nextCycle.addActionListener(this);
		startGame.addActionListener(this);
		mapButtons = new ArrayList<JButton>();
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				JButton temp = new JButton(i+", "+j);
				temp.setFont(new Font(Font.SERIF, Font.BOLD, 10));
				temp.addActionListener(this);
				mapButtons.add(temp);
				temp.addMouseListener(new java.awt.event.MouseAdapter() {
				    public void mouseEntered(java.awt.event.MouseEvent evt) {
				        temp.setBackground(Color.GREEN);
				        command.j= mapButtons.indexOf(temp) / 10;
						command.k= mapButtons.indexOf(temp) % 10;
						command.g.updateInfo(engine, command, command.j, command.k);
				    }
				    public void mouseExited(java.awt.event.MouseEvent evt) {
				        temp.setBackground(UIManager.getColor("control"));
				        command.g.getInfoPanel().setText("                                                   Info"+"\n");
				    }
				});
			}
		}
		g.addMapButtons(mapButtons);
	}
	public void updateMap() {
		for(int i=0;i<mapButtons.size();i++) {
			int mapx= i/ 10;
			int mapy= i % 10;
			Address temp = engine.getWorld()[mapx][mapy];
			String r="";
			for(int j=0;j<visibleBuildings.size();j++) {
				if(temp.equals(visibleBuildings.get(j).getLocation())) {
					r+="B";
					for(int cc=0;cc<visibleBuildings.get(j).getOccupants().size();cc++) {
						r+="C";
					}
					mapButtons.get(i).setText(r);
					break;
				}
			}
			for(int j=0;j<visibleCitizens.size();j++) {
				if(temp.equals(visibleCitizens.get(j).getLocation())) {
					r+="C";
				}
			}
			mapButtons.get(i).setText(r);
			
			
		}
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
		if(b.equals(startGame)) {
			startGame.setVisible(false);
			g.addNextCycleButton(nextCycle);
		}
		if(b.equals(nextCycle)) {
			engine.nextCycle();
			g.updateCasulaties(engine);
			g.updateLog(engine);
			updateMap();
//			g.updateInfo(engine, this, j, k);
			if(engine.checkGameOver()) {
				g.dispose();
				JFrame x = new JFrame("Game Over");
				JLabel s = new JLabel("    Game Over!!");
				JLabel c = new JLabel("         Number of casualties: "+engine.calculateCasualties());
				s.setFont(new Font(Font.SANS_SERIF, Font.BOLD,60 ));
				s.setForeground(Color.RED);
				c.setFont(new Font(Font.SANS_SERIF, Font.BOLD,30 ));
				c.setForeground(Color.BLACK);
				x.add(s,BorderLayout.NORTH);
				x.add(c, BorderLayout.CENTER);
				x.setVisible(true);
				x.setSize(500, 300);
				x.setLocation(300,100);
				x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				x.setResizable(false);
			}
			
			
			
		}
		if(mapButtons.contains(b)) {
//			j= mapButtons.indexOf(b) / 10;
//			k= mapButtons.indexOf(b) % 10;
//			g.updateInfo(engine, this, j, k);
		}
	}
	
	public ArrayList<ResidentialBuilding> getVisibleBuildings() {
		return visibleBuildings;
	}

	public ArrayList<Citizen> getVisibleCitizens() {
		return visibleCitizens;
	}
	
				
	
	
	public static void main(String[] args) throws Exception {
		CommandCenter com = new CommandCenter();
	}


	

}
