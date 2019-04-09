package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

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
		setVisible(true);
		this.setTitle("Command-Center");
		this.setSize(1000, 600);
		this.setLocation(80,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		main = new JPanel(new BorderLayout());
		left = new JPanel(new BorderLayout());
		left.setSize(250,600);
		left.setBackground(Color.blue);
		right = new JPanel(new BorderLayout());
		right.setSize(250,600);
		middle = new JPanel(new BorderLayout());
		middle.setSize(500,600);
		main.add(left,BorderLayout.WEST);
//		main.add(right,BorderLayout.EAST);
//		main.add(middle,BorderLayout.CENTER);
		
//		RespondingUnits = new JPanel(new GridLayout());
//		TreatingUnits = new JPanel(new GridLayout());
//		AvailbleUnits = new JPanel(new GridLayout());
//		right.add(AvailbleUnits,BorderLayout.NORTH);
//		right.add(RespondingUnits,BorderLayout.CENTER);
//		right.add(TreatingUnits,BorderLayout.SOUTH);
//		
//		map = new JPanel(new GridLayout(10,10));
//		next = new JPanel(new FlowLayout());
//		middle.add(map,BorderLayout.NORTH);
//		middle.add(next,BorderLayout.SOUTH);
//
//		InfoPanel = new JTextArea();  
//		log = new JTextArea();
//		display = new JTextArea();
//		log.setEditable(false);
//		InfoPanel.setEditable(false);
//		display.setEditable(false);
//		left.add(InfoPanel,BorderLayout.NORTH);
//		left.add(log,BorderLayout.SOUTH);
//		left.add(display,BorderLayout.CENTER);

		
		
		
		this.add(main,BorderLayout.CENTER);
		
		
		
		
		
		
	}
	public static void main (String [] args) {
		GUI a = new GUI();
	}
	
}
