package pwr.beans;

import java.awt.Color;
import java.beans.PropertyVetoException;
import java.text.NumberFormat;

import javax.swing.JFrame;

import pwr.java.BeanChangeListener;
import pwr.java.Task;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class App {
	private static JTextField newTitleField;
	private static JFormattedTextField newPriorityField;
	private static JColorChooser newColorChooser;
	
	static NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
	
	public static void main(String[] args) throws PropertyVetoException {
		JFrame mainFrame = new JFrame("Task bean");
		mainFrame.setBounds(100, 100, 600, 600);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.setSize(455, 540);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Task task = new Task();
		task.setPriority(1);
		task.setBounds(0, 0, 439, 100);
		task.setBgColor(new Color(153, 153, 255));
		task.setTitle("Name");
		task.setVisible(true);
		mainFrame.getContentPane().add(task);
		
		newTitleField = new JTextField(task.getTitle());
		newTitleField.setBounds(10, 130, 165, 25);
		mainFrame.getContentPane().add(newTitleField);
		newTitleField.setColumns(10);
		
		newPriorityField = new JFormattedTextField(numberFormatter);
		newPriorityField.setText(String.valueOf(task.getPriority()));
		newPriorityField.setBounds(10, 184, 61, 25);
		mainFrame.getContentPane().add(newPriorityField);
		newPriorityField.setColumns(10);
		
		JLabel titleLabel = new JLabel("New title");
		titleLabel.setBounds(10, 111, 86, 14);
		mainFrame.getContentPane().add(titleLabel);
		
		JLabel priorityLabel = new JLabel("New priority");
		priorityLabel.setBounds(10, 166, 86, 14);
		mainFrame.getContentPane().add(priorityLabel);
		
		newColorChooser = new JColorChooser(task.getBgColor());
		newColorChooser.setBounds(10, 240, 420, 220);
		mainFrame.getContentPane().add(newColorChooser);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				task.setTitle(newTitleField.getText());
				try {
					task.setPriority(Integer.parseInt(newPriorityField.getText()));
				} catch (NumberFormatException | PropertyVetoException e) {
					e.printStackTrace();
					System.out.println("You provided wrong number!");
				}
				task.setBgColor(newColorChooser.getColor());
				task.repaint();
			}
		});
		saveButton.setBounds(7, 471, 89, 23);
		mainFrame.getContentPane().add(saveButton);
		
		JLabel colorLabel = new JLabel("New color");
		colorLabel.setBounds(10, 220, 86, 14);
		mainFrame.getContentPane().add(colorLabel);
		
		BeanChangeListener changeListener = new BeanChangeListener();
		task.addPropertyChangeListener(changeListener);
		task.addVetoableChangeListener(changeListener);
		
		
		mainFrame.setVisible(true);

	}
}
