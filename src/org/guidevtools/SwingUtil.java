package org.guidevtools;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class SwingUtil {
	
	static public void printAllComponents(Component root, int level){
		if(root == null) return;
		
		printSpaceBeforeContent(level, root.getClass().getSimpleName());
		if(root instanceof Container){
			Component[] components = ((Container)root).getComponents();
			if(components == null || components.length == 0){
				return;
			}
			for(Component comp : components){
				printAllComponents(comp, level + 1);
			}
		}
	}

	private static void printSpaceBeforeContent(int count, String content) {
		for(int i = 0; i < count; i++){
			System.out.print(" ");
		}
		System.out.println(content);
	}

	public static List<Component> getAllComponents(Component root){
		List<Component> comps = new ArrayList<Component>();
		sub_getAllComponents(comps, root);
		
		return comps;
	}
	
	/**
	 * 完成getAllComponents功能的子方法
	 * @param comps
	 * @param root
	 */
	private static void sub_getAllComponents(List<Component> comps, Component root) {
		comps.add(root);
		if(root instanceof Container){
			Component[] components = ((Container)root).getComponents();
			if(components == null || components.length == 0){
				return;
			}
			for(Component comp : components){
				sub_getAllComponents(comps, comp);
			}
		}
	}

	public static RtnTop findTopComponent(final Component root, final Point point) {
		if(root == null) return null;
		RtnTop top = func(root, point, 0);
		return top;
	}
	
	private static RtnTop func(final Component root, final Point point, final int level){
		Rectangle rootBound = root.getBounds();
		if(!rootBound.contains(point)){
			return null;
		}
		
		if(root instanceof Container){
			Component[] components = ((Container)root).getComponents();
			if(components != null && components.length != 0){
				int childLevel = -1;
				Component child = null;
				Point childPoint = null;	//子组件相对于其父节点（在这里就是root）的左上角坐标
				for(final Component item : ((Container) root).getComponents()){
					RtnTop childTop = func(item, new Point(point.x - root.getX(), point.y - root.getY()), level + 1);
					if(childTop != null){
						if(childTop.getLevel() > childLevel){
							childLevel = childTop.getLevel();
							child = childTop.getComponent();
							childPoint = childTop.getPoint();
						}
					}
				}
				if(child != null){
					return new RtnTop(childLevel, child, new Point(root.getLocation().x + childPoint.x, root.getLocation().y + childPoint.y));
				}
			}
		}
		return new RtnTop(level, root, root.getLocation());
	}
	
	public static class RtnTop{
		
		private int level;
		
		private Component component;
		
		private Point point;

		public RtnTop(int level, Component component, Point point) {
			this.level = level;
			this.component = component;
			this.point = point;
		}

		public int getLevel() {
			return level;
		}

		public Component getComponent() {
			return component;
		}

		public Point getPoint() {
			return point;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((component == null) ? 0 : component.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RtnTop other = (RtnTop) obj;
			if (component == null) {
				if (other.component != null)
					return false;
			} else if (!component.equals(other.component))
				return false;
			return true;
		}
	}
}