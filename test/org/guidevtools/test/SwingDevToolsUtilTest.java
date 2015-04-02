package org.guidevtools.test;

import java.awt.Component;
import java.awt.Point;
import java.util.List;

import org.guidevtools.SwingUtil;
import org.guidevtools.SwingUtil.RtnTop;
import org.junit.Test;

public class SwingDevToolsUtilTest {

	@Test
	public void testPrintAllComponents(){
		Simple simple = new Simple();
		SwingUtil.printAllComponents(simple, 0);
	}
	
	@Test
	public void testGetAllComponents(){
		Simple simple = new Simple();
		List<Component> allComponents = SwingUtil.getAllComponents(simple);
		for(Component comp : allComponents){
			System.out.println(comp.getClass().getSimpleName());
			System.out.println(comp.getLocation());
		}
	}
	
	@Test
	public void testFindTopComponent(){
		Simple simple = new Simple();
		RtnTop top = SwingUtil.findTopComponent(simple.getComponentAt(new Point(100, 180)), new Point(100, 180));
		System.out.println(top.getClass().getSimpleName());
	}
}
