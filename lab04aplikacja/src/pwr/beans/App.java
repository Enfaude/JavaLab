package pwr.beans;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import pwr.java.BeanChangeListener;
import pwr.java.Task;
import java.awt.Color;
import java.beans.PropertyVetoException;

public class App {
	
	public static void main(String[] args) throws PropertyVetoException {
		JFrame mainFrame = new JFrame("Task bean");
		Task task = new Task();
		task.setPriority(3);
		task.setBgColor(new Color(0, 153, 0));
		task.setTitle("wawdTask");
		
		mainFrame.setSize(400, 400);
		mainFrame.getContentPane().setLayout(new BorderLayout());
		mainFrame.getContentPane().add(task, BorderLayout.CENTER);
		
		BeanChangeListener changeListener = new BeanChangeListener();
		task.addPropertyChangeListener(changeListener);
		task.addVetoableChangeListener(changeListener);
		
		mainFrame.setVisible(true);

	}

}
