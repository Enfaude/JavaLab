package pwr.java;

import java.beans.Customizer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.text.NumberFormat;

import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

public class TaskCustomizer extends JPanel implements Customizer{
	
	private static final long serialVersionUID = 4551432144754445219L;
	private Task task;
	NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
	
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private VetoableChangeSupport veto = new VetoableChangeSupport(this);
	
	
	JTextField titleField;
	JFormattedTextField priorityField;
	JColorChooser colorChooser;
	
	@Override
	public void setObject(Object bean) {
		task = (Task) bean;

		setLayout(null);
		setBounds(100, 100, 440, 326);
		
		titleField = new JTextField(task.getTitle());
		titleField.setBounds(10, 10, 420, 20);
		add(titleField);
		titleField.setColumns(10);
		titleField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				String txt = titleField.getText();
				task.setTitle(txt);
				support.firePropertyChange("", null, null);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				String txt = titleField.getText();
				task.setTitle(txt);
				support.firePropertyChange("", null, null);
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				String txt = titleField.getText();
				task.setTitle(txt);
				support.firePropertyChange("", null, null);				
			}
		});
		
		priorityField = new JFormattedTextField(numberFormatter);
		priorityField.setValue(task.getPriority());
		priorityField.setBounds(10, 41, 35, 20);
		add(priorityField);
		priorityField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					task.setPriority(Integer.parseInt(priorityField.getText()));
				} catch (NumberFormatException | PropertyVetoException e1) {
					e1.printStackTrace();
					System.out.println("Wrong priority value!");
				}
				support.firePropertyChange("", null, null);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					task.setPriority(Integer.parseInt(priorityField.getText()));
				} catch (NumberFormatException | PropertyVetoException e1) {
					e1.printStackTrace();
					System.out.println("Wrong priority value!");
				}
				support.firePropertyChange("", null, null);
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					task.setPriority(Integer.parseInt(priorityField.getText()));
				} catch (NumberFormatException | PropertyVetoException e1) {
					e1.printStackTrace();
					System.out.println("Wrong priority value!");
				}
				support.firePropertyChange("", null, null);				
			}
		});
		
		colorChooser = new JColorChooser(task.getBgColor());
		colorChooser.setBounds(10, 72, 420, 218);
		colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				task.setBgColor(colorChooser.getColor());
				support.firePropertyChange("", null, null);
			}
		});
		add(colorChooser);
	}
	
	 public void addPropertyChangeListener(PropertyChangeListener l) {
		 support.addPropertyChangeListener(l);
	 }
	 
	 public void removePropertyChangeListener(PropertyChangeListener l) {
		 support.removePropertyChangeListener(l);
	 } 
	 
	public void addVetoableChangeListener(VetoableChangeListener listener) {
		veto.addVetoableChangeListener(listener);
	}
	
	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		veto.removeVetoableChangeListener(listener);
	}
}
