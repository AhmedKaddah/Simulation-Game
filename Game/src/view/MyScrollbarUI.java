package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

class MyScrollbarUI extends BasicScrollBarUI {

    private ImageIcon downArrow, upArrow, leftArrow, rightArrow;

    public MyScrollbarUI(){
    	thumbColor = Color.BLACK;
//    	thumbDarkShadowColor = new Color(74, 163, 232);
    	thumbLightShadowColor = new Color(74, 163, 232);
    	thumbHighlightColor = new Color(74, 163, 232);

     
            upArrow = new ImageIcon("up.png");
            downArrow = new ImageIcon("down.png");
            rightArrow = new ImageIcon("right.png");
            leftArrow = new ImageIcon("left.png");
                
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton decreaseButton = new JButton(getAppropriateIcon(orientation)){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(22, 22);
            }
        };
        decreaseButton.setBackground(Color.BLACK);
        decreaseButton.setBorder(null);
        return decreaseButton;
    }
    protected void configureScrollBarColors()  {
    	
    	scrollbar.setBackground(Color.BLACK);
    }


    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton increaseButton = new JButton(getAppropriateIcon(orientation)){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(22, 22);
            }
        };
        increaseButton.setBackground(Color.BLACK);
        increaseButton.setBorder(null);
        return increaseButton;
    }

    private ImageIcon getAppropriateIcon(int orientation){
        switch(orientation){
            case SwingConstants.SOUTH: return downArrow;
            case SwingConstants.NORTH: return upArrow;
            case SwingConstants.EAST: return rightArrow;
                default: return leftArrow;
        }
    }
}    
