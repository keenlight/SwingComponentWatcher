package org.guidevtools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import org.guidevtools.SwingUtil.RtnTop;

public class GUIComponentWatcher extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Window watchObject;
	
	private Rectangle lastSelectedBound;
	
	private Rectangle newSelectedBound;
	
	private RtnTop lastTop;
	
	private JTable detailTable;
	
	private Object[] title = new Object[]{"属性", "值"};
	
	public GUIComponentWatcher(Window watchObject){
		this.watchObject = watchObject;
		init();
	}

	private void init() {
		add(createOpen(), BorderLayout.NORTH);
		add(createDetailTable());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
	}

	private JPanel createOpen() {
		JPanel panel = new JPanel();
		JToggleButton open = new JToggleButton("watch");
		open.setPreferredSize(new Dimension(80, 25));
		open.addActionListener(event -> {
			new Thread(() -> {
				while(open.getModel().isSelected()){
					try {
						Thread.sleep(200);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Point point = MouseInfo.getPointerInfo().getLocation();
					point.x = point.x - watchObject.getX();
					point.y = point.y - watchObject.getY();
					RtnTop top = SwingUtil.findTopComponent(watchObject.getComponentAt(point), point);
					if(top == null || top.equals(lastTop)){
						continue;
					}
					lastTop = top;
					Rectangle bound = new Rectangle(top.getPoint(), top.getComponent().getSize());
					
					if(bound.equals(lastSelectedBound)){
						continue;
					}
					
					newSelectedBound = bound;
					
					updateSelect(top.getComponent());
					updateTopComponentDetail(top.getComponent());
				}
				if(lastSelectedBound != null){//关闭了watch后要清理之前留下的痕迹（如果有）
					Graphics g = watchObject.getGraphics();
					g.setColor(watchObject.getBackground());
					g.drawRect(lastSelectedBound.x, lastSelectedBound.y, lastSelectedBound.width, lastSelectedBound.height);
				}
			}).start();
		});
		panel.add(open);
		
		return panel;
	}
	
	private void updateSelect(Component top) {
		Graphics g = watchObject.getGraphics();
		if(newSelectedBound == null){
			return;
		}
		if(lastSelectedBound == null){
			lastSelectedBound = newSelectedBound;
		}
		
		Color bgColor = top.getParent() != null ? top.getParent().getBackground() : watchObject.getBackground();
		g.setColor(bgColor);
		g.drawRect(lastSelectedBound.x, lastSelectedBound.y, lastSelectedBound.width, lastSelectedBound.height);
		
		g.setColor(Color.BLUE);
		g.drawRect(newSelectedBound.x, newSelectedBound.y, newSelectedBound.width, newSelectedBound.height);
		
		lastSelectedBound = newSelectedBound;
	}

	private JScrollPane createDetailTable() {
		detailTable = new JTable(new DefaultTableModel(null, title));
		JScrollPane jsp = new JScrollPane(detailTable);
		jsp.setPreferredSize(new Dimension(300, 170));
		return jsp;
	}
	
	private void updateTopComponentDetail(Component component) {
		List<NameValuePair> list = new ArrayList<>();
		list.add(new NameValuePair("type", component.getClass().getSimpleName()));
		list.add(new NameValuePair("bounds", component.getBounds().toString()));
		int row = list.size();
		Object[][] values = new Object[row][2];
		for(int i = 0; i < row; i++){
			values[i][0] = list.get(i).getName();
			values[i][1] = list.get(i).getValue();
		}
		
		detailTable.setModel(new DefaultTableModel(values, title));
	}
	
	private class NameValuePair{
		private String name;
		private String value;
		
		public NameValuePair(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}
}
