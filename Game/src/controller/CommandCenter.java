package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulator;
import view.GUI;

public class CommandCenter implements SOSListener,ActionListener {
	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private GUI g;
	private JButton startGame = new JButton("Start Game");
	private JButton nextCycle = new JButton("Next Cycle");
	private ArrayList<JButton> mapButtons;
	private ArrayList<JButton> unitButtons;
	private int j;
	private int k;
	private CommandCenter command=this;
	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;
	JOptionPane selecttarget = new JOptionPane();
	private Unit currentunit;
	private boolean choosetarget = false;
	
	
	
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
		unitButtons = new ArrayList<JButton>();
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				JButton temp = new JButton(i+", "+j);
				temp.setFont(new Font(Font.SERIF, Font.BOLD, 10));
				temp.addActionListener(this);
				mapButtons.add(temp);
				temp.addMouseListener(new java.awt.event.MouseAdapter() {
				    public void mouseEntered(java.awt.event.MouseEvent evt) {
				        temp.setBackground(Color.GREEN);
//				        command.j= mapButtons.indexOf(temp) / 10;
//						command.k= mapButtons.indexOf(temp) % 10;
//						command.g.updateInfo(engine, command, command.j, command.k);
				    }
				    public void mouseExited(java.awt.event.MouseEvent evt) {
				        temp.setBackground(UIManager.getColor("control"));
//				        command.g.getInfoPanel().setText("                                                   Info"+"\n");
				    }
				});
			}
		}
	
		g.addMapButtons(mapButtons);

		
		
		for(int i=0; i<emergencyUnits.size();i++)
		{
			if(emergencyUnits.get(i) instanceof Ambulance) {
				unitButtons.add(new JButton("AMB"));
			}
			if(emergencyUnits.get(i) instanceof DiseaseControlUnit) {
				unitButtons.add(new JButton("DCU"));
			}
			if(emergencyUnits.get(i) instanceof Evacuator) {
				unitButtons.add(new JButton("EVC"));
			}
			if(emergencyUnits.get(i) instanceof FireTruck) {
				unitButtons.add(new JButton("FTK"));
			}
			if(emergencyUnits.get(i) instanceof GasControlUnit) {
				unitButtons.add(new JButton("GCU"));
			}
			unitButtons.get(i).addActionListener(this);
		}
		
		g.updateUnits(this);

		
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
			g.revalidate();
			engine.nextCycle();
			if(currentunit!=null)
				g.updateUnitInfo(currentunit);
			g.updateCasulaties(engine);
			g.updateLog(engine);
			updateMap();
			g.updateInfo(engine, this, j, k);
			g.updateUnits(this);
			g.revalidate();
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
			
			g.updateDisasters(this);

		}
		if(unitButtons.contains(b)) {
			choosetarget=true;
			int n= unitButtons.indexOf(b);
			currentunit = emergencyUnits.get(n);
			g.updateUnitInfo(currentunit);
			
		}
		if(mapButtons.contains(b)) {
			j= mapButtons.indexOf(b) / 10;
			k= mapButtons.indexOf(b) % 10;
			g.updateInfo(engine, this, j, k);
			
			
			
			if(choosetarget) {
			int count =0;
			for(int i=0; i<visibleBuildings.size();i++) {
				if(visibleBuildings.get(i).getLocation().equals(engine.getWorld()[j][k])) {
					count++;
				}
			}
			for(int i=0; i<visibleCitizens.size();i++) {
				if(visibleCitizens.get(i).getLocation().equals(engine.getWorld()[j][k])) {
					count++;
				}
			}
			Object[] o= new Object[count];
			count=0;
			for(int i=0; i<visibleBuildings.size();i++) {
				if(visibleBuildings.get(i).getLocation().equals(engine.getWorld()[j][k])) {
					o[count++]= visibleBuildings.get(i).toString();
				}
			}
			for(int i=0; i<visibleCitizens.size();i++) {
				if(visibleCitizens.get(i).getLocation().equals(engine.getWorld()[j][k])) {
					o[count++]= visibleCitizens.get(i).toString();
				}
			}
		int n = JOptionPane.showOptionDialog(selecttarget,"Please choose a target","Choose target",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, o, null);
		if(n==-1) {
			choosetarget=false;
		}	
		else {
		int r=0;
		for(int i=0; i<visibleBuildings.size();i++) {
			if(visibleBuildings.get(i).getLocation().equals(engine.getWorld()[j][k])) {
				if(r==n) {
					try {
						currentunit.respond(visibleBuildings.get(i));
						g.updateUnits(this);
						g.updateUnitInfo(currentunit);}
						catch(CannotTreatException e1) {
							JOptionPane.showMessageDialog( null, e1.getMessage() );
						}
						catch(IncompatibleTargetException e2) {
							JOptionPane.showMessageDialog( null, e2.getMessage() );				
						}
				}
				r++;
			}
		}
		for(int i=0; i<visibleCitizens.size();i++) {
			if(visibleCitizens.get(i).getLocation().equals(engine.getWorld()[j][k])) {
				if(r==n) {
					try {
					currentunit.respond(visibleCitizens.get(i));
					g.updateUnits(this);
					g.updateUnitInfo(currentunit);}
					catch(CannotTreatException e1) {
						JOptionPane.showMessageDialog( null, e1.getMessage() );
					}
					catch(IncompatibleTargetException e2) {
						JOptionPane.showMessageDialog( null, e2.getMessage() );					
					}
					
				}
				r++;
			}
		}
			choosetarget=false;
			}}}
	}
	
	public ArrayList<ResidentialBuilding> getVisibleBuildings() {
		return visibleBuildings;
	}

	public ArrayList<Citizen> getVisibleCitizens() {
		return visibleCitizens;
	}
	
	public ArrayList<Unit> getEmergencyUnits() {
		return emergencyUnits;
	}		
	
	
	public static void main(String[] args) throws Exception {
		CommandCenter com = new CommandCenter();
		com.g.setVisible(true);
	}
	public ArrayList<JButton> getUnitButtons() {
		return unitButtons;
	}
	





	

}
