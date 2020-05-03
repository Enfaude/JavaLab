package pwr.java;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class BeanChangeListener implements VetoableChangeListener, PropertyChangeListener {
	int minPriority = 1;
	int maxPriority = 5;

	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
		int newPriority = (int) evt.getNewValue();
		
		if (newPriority < minPriority || newPriority > maxPriority) {
			throw new PropertyVetoException("New priority is out of bounds! (1-5)", evt);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getOldValue() instanceof String) {
			System.out.println("Previous title: " + evt.getOldValue());
			System.out.println("New title: " + evt.getNewValue());
		} else if(evt.getOldValue() instanceof Integer) {
			System.out.println("Previous value: " + evt.getOldValue());
			System.out.println("New value: " + evt.getNewValue());
		} else if (evt.getOldValue() instanceof Color) {
			System.out.println("Previous bgColor: " + evt.getOldValue());
			System.out.println("New bgColor: " + evt.getNewValue());
		}		
	}
}
