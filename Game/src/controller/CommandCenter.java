package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

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
	private JButton startGame = new JButton();
	private JButton nextCycle = new JButton("Next Cycle");
	private JButton demo = new JButton();
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
	private JFrame start;
	private JPanel startP;
	private JPanel startT;
	private JFrame demowindow;
	
	ImageIcon hq = new ImageIcon("hq.png");
	ImageIcon building = new ImageIcon("building.png");
	ImageIcon bandc= new ImageIcon("bandc.png");
	ImageIcon amb = new ImageIcon("amb.png");
	ImageIcon ftk = new ImageIcon("ftk.png");
	ImageIcon evc = new ImageIcon("evc.png");
	ImageIcon dcu = new ImageIcon("dcu.png");
	ImageIcon gcu = new ImageIcon("gcu.png");
	ImageIcon citizen = new ImageIcon("person.png");
	ImageIcon citizens = new ImageIcon("persons.png");
	ImageIcon terr = new ImageIcon("terrain.png");
	ImageIcon welcome = new ImageIcon("WELCOME.png");
	ImageIcon sgame = new ImageIcon("sg.png");
	ImageIcon d = new ImageIcon("d.png");
	
	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		
		start = new JFrame();
		start.setVisible(true);
		start.setTitle("Rescue Simulation Game");
		start.setLayout(new BorderLayout());
		start.setSize(new Dimension(450, 200));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		start.setLocation(dim.width/2-start.getSize().width/2, dim.height/2-start.getSize().height/2);
		start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start.setResizable(false);
		
		demowindow = new JFrame();
		demowindow.setVisible(false);
		demowindow.setTitle("Demo!");
		demowindow.setLayout(new BorderLayout());
		demowindow.setSize(new Dimension(450, 200));
		demowindow.setLocation(dim.width/2-start.getSize().width/2, dim.height/2-start.getSize().height/2);
		demowindow.setResizable(false);
		
		startP = new JPanel(new BorderLayout());
		startT = new JPanel();
		startP.setSize(new Dimension(450, 50));
		startT.setSize(new Dimension(450, 150));
		startT.add(new JLabel(welcome));
		start.add(startP,BorderLayout.SOUTH);
		start.add(startT,BorderLayout.NORTH);
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		startGame.setBorder(emptyBorder);
		demo.setBorder(emptyBorder);

		startGame.setPreferredSize(new Dimension(250, 50));
		startGame.setIcon(sgame);
		demo.setPreferredSize(new Dimension(200, 50));
		demo.setIcon(d);
		startP.add(startGame,BorderLayout.WEST);
		startP.add(demo,BorderLayout.EAST);
		
		g = new GUI();
		nextCycle.addActionListener(this);
		startGame.addActionListener(this);
		demo.addActionListener(this);
		mapButtons = new ArrayList<JButton>();
		unitButtons = new ArrayList<JButton>();
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				JButton temp = new JButton();
				temp.addActionListener(this);
				temp.setIcon(terr);
				mapButtons.add(temp);
			}
		}
		mapButtons.get(0).setIcon(hq);
	
		g.addMapButtons(mapButtons);

		
		
		for(int i=0; i<emergencyUnits.size();i++)
		{	JButton temp = new JButton();
			temp.setPreferredSize(new Dimension(70, 60));
			if(emergencyUnits.get(i) instanceof Ambulance) {
				temp.setIcon(amb);
				unitButtons.add(temp);
			}
			if(emergencyUnits.get(i) instanceof DiseaseControlUnit) {
				temp.setIcon(dcu);
				unitButtons.add(temp);
			}
			if(emergencyUnits.get(i) instanceof Evacuator) {
				temp.setIcon(evc);
				unitButtons.add(temp);
			}
			if(emergencyUnits.get(i) instanceof FireTruck) {
				temp.setIcon(ftk);
				unitButtons.add(temp);
			}
			if(emergencyUnits.get(i) instanceof GasControlUnit) {
				temp.setIcon(gcu);
				unitButtons.add(temp);
			}
			unitButtons.get(i).addActionListener(this);
		}
		
		g.updateUnits(this);

		
	}

	public void updateMap() {
		for (int i = 0; i < mapButtons.size(); i++) {
			int mapx = i / 10;
			int mapy = i % 10;
			Address temp = engine.getWorld()[mapx][mapy];
			int Bcount = 0;
			int Ccount = 0;
			for (int j = 0; j < visibleBuildings.size(); j++) {
				if (temp.equals(visibleBuildings.get(j).getLocation())) {
					Bcount++;
					break;
				}
			}
			for (int j = 0; j < visibleCitizens.size(); j++) {
				if (temp.equals(visibleCitizens.get(j).getLocation())) {
					Ccount++;
				}
			}
			if (Bcount > 0) {
				mapButtons.get(i).setIcon(building);
				if (Ccount > 0) {
					mapButtons.get(i).setIcon(bandc);
				}
			} else {
				if (Ccount > 0) {
					mapButtons.get(i).setIcon(citizen);
					if (Ccount > 1)
						mapButtons.get(i).setIcon(citizens);
				}
			}
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
		if(b.equals(demo)) {
		demowindow.setVisible(true);
		}
		if(b.equals(startGame)) {
			start.dispose();
			g.setVisible(true);
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
			g.sb.setValue( g.sb.getMaximum() );

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
	}
	public ArrayList<JButton> getUnitButtons() {
		return unitButtons;
	}
	





	

}
