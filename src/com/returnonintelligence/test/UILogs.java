package com.returnonintelligence.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UILogs extends JFrame{
    private	JTable table;
    private	JScrollPane scrollPane;
    private JButton button;
    private JTextField text;
    private String idSearch;
    public static final int len = 4;

    public UILogs() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        JMenuItem fileOpen = new JMenuItem("Open");
        mnFile.add(fileOpen);

        JMenuItem mnAbout = new JMenuItem("About");
        mnHelp.add(mnAbout);

        JMenuItem mnExit = new JMenuItem("Exit");
        mnFile.add(mnExit);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        setTitle("Log data");
        setSize((int) width / 2, (int) height / 2);
        setBackground(Color.black);

        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Timestamp");
        columnNames.addElement("ID User");
        columnNames.addElement("URL");
        columnNames.addElement("Time");
        Vector<Vector> dataValues = new Vector<Vector>();
        table = new JTable( dataValues, columnNames );
        table.setAutoCreateRowSorter(true);
        scrollPane = new JScrollPane( table );

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(scrollPane, BorderLayout.CENTER);
        pane.add(getButtonPanel(), BorderLayout.NORTH);

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

        mnAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = "1. Open (File-Open) a logfile (.csv)" + "\n" + "2.You can sort the data by column" +
                        "\n" + "3. You can filter the data by userID";
                JOptionPane.showMessageDialog(pane, s);
            }
        });

        mnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    protected JComponent getButtonPanel() {
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(1, 2, 50, 0));
        button = new JButton("Search");
        text = new JTextField("userID");
        inner.add(text);
        inner.add(button);
        return inner;
    }
}
