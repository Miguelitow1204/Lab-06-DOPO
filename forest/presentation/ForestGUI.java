package presentation;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ForestGUI extends JFrame{  
    public static final int SIDE=20;

    public final int SIZE;
    private JButton ticTacButton;
    private JPanel  controlPanel;
    private PhotoForest photo;
    private Forest theForest;
    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem saveAsItem;
    private JMenuItem importItem;
    private JMenuItem exportAsItem;
    private JMenuItem exitItem;
    
    private ForestGUI() {
        theForest=new Forest();
        SIZE=theForest.getSize();
        prepareElements();
        prepareActions();
    }
    
    private void prepareElements() {
        setTitle("Schelling Forest");
        photo=new PhotoForest(this);
        ticTacButton=new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo,BorderLayout.NORTH);
        add(ticTacButton,BorderLayout.SOUTH);
        setSize(new Dimension(SIDE*SIZE+15,SIDE*SIZE+72)); 
        setResizable(false);
        photo.repaint();
        prepareElementsMenu();
    }
    
    private void prepareElementsMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        
        newItem = new JMenuItem("Nuevo");
        openItem = new JMenuItem("Abrir");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        
        saveAsItem = new JMenuItem("Guardar como");
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        
        importItem = new JMenuItem("Importar");
        exportAsItem = new JMenuItem("Exportar como");
        fileMenu.add(importItem);
        fileMenu.add(exportAsItem);
        fileMenu.addSeparator();
        
        exitItem = new JMenuItem("Salir");
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void prepareActions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        ticTacButton.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    ticTacButtonAction();
                }
            });
        prepareActionsMenu();

    }
    
    private void prepareActionsMenu(){
        exitItem.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    optionExit();
                }
            });
        newItem.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    optionNew();
                }
            });
        openItem.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    optionOpen();
                }
            });
        saveAsItem.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    optionSaveAs();
                }
            });
        importItem.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    optionImport();
                }
            });
        exportAsItem.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    optionExportAs();
                }
            });
    }
    
    private void optionExit(){
        System.exit(0);
    }
    
    private void optionNew(){
        theForest = new Forest();
        photo.repaint();
    }
    
    private void optionOpen(){
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            try{
                theForest.open(file);
                photo.repaint();
            } catch(ForestException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void optionSaveAs() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                theForest.saveAs(file);
            } catch (ForestException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void optionImport() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                theForest.importAs(file);
            } catch (ForestException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void optionExportAs() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                theForest.exportAs(file);
            } catch (ForestException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ticTacButtonAction() {
        theForest.ticTac();
        photo.repaint();
    }

    public Forest gettheForest(){
        return theForest;
    }
    
    public static void main(String[] args) {
        ForestGUI cg=new ForestGUI();
        cg.setVisible(true);
    }  
}

class PhotoForest extends JPanel{
    private ForestGUI gui;

    public PhotoForest(ForestGUI gui) {
        this.gui=gui;
        setBackground(Color.white);
        setPreferredSize(new Dimension(gui.SIDE*gui.SIZE+10, gui.SIDE*gui.SIZE+10));         
    }


    public void paintComponent(Graphics g){
        Forest theForest=gui.gettheForest();
        super.paintComponent(g);
         
        for (int c=0;c<=theForest.getSize();c++){
            g.drawLine(c*gui.SIDE,0,c*gui.SIDE,theForest.getSize()*gui.SIDE);
        }
        for (int f=0;f<=theForest.getSize();f++){
            g.drawLine(0,f*gui.SIDE,theForest.getSize()*gui.SIDE,f*gui.SIDE);
        }       
        for (int f=0;f<theForest.getSize();f++){
            for(int c=0;c<theForest.getSize();c++){
                if (theForest.getThing(f,c)!=null){
                    g.setColor(theForest.getThing(f,c).getColor());
                    if (theForest.getThing(f,c).shape()==Thing.SQUARE){                  
                        g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);   
                    }else {
                        g.fillOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                    }
                    if (theForest.getThing(f,c).isLivingThing()){
                        g.setColor(Color.red);
                        if (((LivingThing)theForest.getThing(f,c)).getEnergy()>=50){
                            g.drawString("+",gui.SIDE*c+6,gui.SIDE*f+15);
                        } else {
                            g.drawString("~",gui.SIDE*c+6,gui.SIDE*f+17);
                        }
                    }    
                }
            }
        }
    }
    
    
}