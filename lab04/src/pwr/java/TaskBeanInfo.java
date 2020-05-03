package pwr.java;

import java.beans.BeanDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;

public class TaskBeanInfo extends SimpleBeanInfo {

	@Override
	public BeanDescriptor getBeanDescriptor() {
		BeanDescriptor beanDescriptor = new BeanDescriptor(Task.class, TaskCustomizer.class);
		beanDescriptor.setDisplayName("Task");
		beanDescriptor.setShortDescription("Task bean class");
		return beanDescriptor;
	}

	@Override
	public MethodDescriptor[] getMethodDescriptors() {
		try {
			Method paint = Task.class.getMethod("paint");
			MethodDescriptor mdPaint = new MethodDescriptor(paint);
			MethodDescriptor[] mds = new MethodDescriptor[] {mdPaint};
			return mds;
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor pdPriority = new PropertyDescriptor("priority", Task.class, "getPriority", "setPriority");
			PropertyDescriptor pdTitle = new PropertyDescriptor("title", Task.class, "getTitle", "setTitle");
			
			PropertyDescriptor[] pds = new PropertyDescriptor[] {pdPriority, pdTitle};
			 return pds;		
		} catch (java.beans.IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
	}

}
