package pwr.java;

import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import javax.swing.JPanel;

public class Task extends JPanel implements Serializable {

	private static final long serialVersionUID = 4024789531612688217L;
	
	public Task(){
		priority = 0;
		title = "Task";
		bgColor = Color.blue;
	}	
	
	private int priority;
	private String title;
	private Color bgColor;

	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int newPriority) throws PropertyVetoException {
		vetoableChangeSupport.fireVetoableChange("priority", this.priority, newPriority);
		this.priority = newPriority;
		propertyChangeSupport.firePropertyChange("priority", this.priority, newPriority);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String newTitle) {
		this.title = newTitle;
		propertyChangeSupport.firePropertyChange("title", this.title, newTitle);
	}
	
	public Color getBgColor() {
		return bgColor;
	}
	
	public void setBgColor(Color newBgColor) {
		propertyChangeSupport.firePropertyChange("bgColor", this.bgColor, newBgColor);
		this.bgColor = newBgColor;
	}
	
	public void paint(Graphics g) {
		g.setColor(bgColor);
		this.setBackground(bgColor);
		super.paintComponent(g);
		g.setColor(Color.black);
		g.drawString(title + " | Task priority: " + priority, 100, 100);
	}
	
	private VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);
	
	public void addVetoableChangeListener(VetoableChangeListener listener) {
		vetoableChangeSupport.addVetoableChangeListener(listener);
	}
	
	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		vetoableChangeSupport.removeVetoableChangeListener(listener);
	}
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
