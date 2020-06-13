package pwr.jmx;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class Main {
	public static Control beanControl = new Control(10, 10);
	static List<SolverThread> threads = new ArrayList<>();

	public static void main(String[] args) {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
	        ObjectName name = new ObjectName("pwr.jmx:type=Control");
	        mbs.registerMBean(beanControl, name);
	        do{
	        	manageThreads();
	            Thread.sleep(2500);
	            System.out.println("Thread Count = " + beanControl.getThreadCount() + " | Cache size = " + beanControl.getCacheSize());
	            beanControl.printReport();
	        }while(beanControl.getThreadCount() !=0);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | InterruptedException e) {
        	e.printStackTrace();
        }
        manageThreads();
	}
	
	static void manageThreads() {
		while(threads.size() < beanControl.getThreadCount()) {
			SolverThread solver = new SolverThread();
			solver.start();
			threads.add(solver);
		}
		
		while(threads.size() > beanControl.getThreadCount()) {
			SolverThread solverThread = threads.get(0);
			solverThread.prepareToDie();
			threads.remove(0);
		}
		System.gc();
	}

}
