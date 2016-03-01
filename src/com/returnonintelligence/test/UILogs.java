package com.returnonintelligence.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UILogs extends JFrame{
    private	JPanel topPanel;
    private	JTable table;
    private	JScrollPane scrollPane;
    private JButton button;
    private JTextField text;
    private DefaultTableModel model;
    private String idSearch;
    public static final int len = 4;

    public UILogs() {


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem fileOpen = new JMenuItem("Open");
        mnFile.add(fileOpen);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();


        setTitle("Log data");
        setSize( (int)width/2, (int)height/2);
        setBackground( Color.black );

        button = new JButton("Search");
        text = new JTextField("userID");
        topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );

        getContentPane().add( topPanel );

        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Timestamp");
        columnNames.addElement("ID User");
        columnNames.addElement("URL");
        columnNames.addElement("Time");

        Vector<Vector> dataValues = new Vector<Vector>();

        table = new JTable( dataValues, columnNames );


        table.setAutoCreateRowSorter(true);

        scrollPane = new JScrollPane( table );
        topPanel.add(text, BorderLayout.BEFORE_FIRST_LINE);
        topPanel.add(button, BorderLayout.BEFORE_LINE_BEGINS);
        topPanel.add( scrollPane, BorderLayout.CENTER );

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                idSearch = text.getText();
                int k = 0;
                int rowCount = table.getRowCount();
                String copy[][] = new String[rowCount][len];
                for (int i = 0; i < rowCount; i++){
                    if (table.getValueAt(i, 1).toString().equals(idSearch)){
                        for (int j = 0; j < len; j++){
                            copy[k][j] = table.getValueAt(i, j).toString();
                        }
                        k++;
                    }
                }
                DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                dtm.setRowCount(0);
                table.setModel(dtm);
                dtm.setRowCount(k);

                table.setModel(dtm);

                for (int i = 0; i < k; i++){
                    for (int j = 0; j < len; j++) {
                        table.setValueAt(copy[i][j], i, j);
                        System.out.println(copy[i][j] + "   " +  i + "   " +  j);
                    }
                }

                table.repaint();
            }
        });

        fileOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("File Browser");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.setControlButtonsAreShown(false);
                frame.getContentPane().add(fileChooser, BorderLayout.CENTER);

                ActionListener actionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        JFileChooser theFileChooser = (JFileChooser) actionEvent.getSource();
                        String command = actionEvent.getActionCommand();
                        if (command.equals(JFileChooser.APPROVE_SELECTION)) {
                            File selectedFile = theFileChooser.getSelectedFile();
                            try{
                                frame.setVisible(false);
                                java.util.List<java.util.List<String>> list = TableFromCSV.read(selectedFile);
                                int listSize = list.size();

                                DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                                dtm.setRowCount(listSize);
                                table.setModel(dtm);

                                for (int i = 0; i < listSize; i++){
                                    for (int j = 0; j < len; j++) {
                                        table.setValueAt(list.get(i).get(j), i, j);
                                    }
                                }

                                table.repaint();

                            } catch(Exception e){}
                        } else if (command
                                .equals(JFileChooser.CANCEL_SELECTION)) {
                            System.out.println(JFileChooser.CANCEL_SELECTION);
                        }
                    }
                };
                fileChooser.addActionListener(actionListener);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}