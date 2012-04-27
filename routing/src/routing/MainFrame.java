package routing;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;

public class MainFrame {
	static JTextField input = new JTextField();
	static JButton add_button = new JButton("Add new Routers");
	static JButton compute_button = new JButton("Compute Path");
	static JButton addedge_button = new JButton("Add Edge");
	static JButton removeall_button = new JButton("Remove All Routers");
	static JButton removeone_button = new JButton("Remove Router");
	static JButton list_button = new JButton("Router Information");
	static JButton removeedge_button = new JButton("Remove Edge");
	Vector<String> command_history = new Vector<String>(30);
	static Vector<Router> router_vector = new Vector<Router>(10);
	static JFrame frame = new JFrame();
	static Container frame_pane = frame.getContentPane();
	static Draw canvas = new Draw(router_vector);
	static JCheckBox checkCongestion = new JCheckBox();

	public static void main(String[] args) {
		final MainFrame main_frame = new MainFrame();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				main_frame.createMainFrame();
			}
		});

	}

	void createMainFrame() {
		frame.setBounds(200, 80, 655, 555);
		frame.setVisible(true);
		frame.setTitle("Revised Dijkstra Algorithm");
		frame.setResizable(false);
		frame_pane.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		addedge_button.setBounds(10, 60, 150, 30);
		addedge_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (router_vector.size() <= 1 ) {
					JOptionPane.showMessageDialog(frame, "Please add at least two routers first!");
					return;
				}
				String routerName [] = new String[router_vector.size()];
				for (int i = 0; i < routerName.length; i++) {
					routerName[i] = router_vector.get(i).getName();
				}
				JComboBox boxFrom = new JComboBox(routerName);
				JComboBox boxTo = new JComboBox(routerName);
				JTextField conTextField = new JTextField();
				
				final JComponent[] inputs = new JComponent[] {
						new JLabel("From Router"),
						boxFrom,
						new JLabel("To Router"),
						boxTo,
						new JLabel("Congestion value"),
						conTextField,
						new JLabel("Distance"),
				};
				
				String distanceString = JOptionPane.showInputDialog(null, inputs, "Add New Edge", JOptionPane.PLAIN_MESSAGE);
				if(distanceString == null)
				{
					return;
				}
				if(boxFrom.getSelectedItem().toString() != boxTo.getSelectedItem().toString())
				{
					Double dis = Double.parseDouble(distanceString);
					Double congestion = Double.valueOf(conTextField.getText());
					addEdge(boxFrom.getSelectedItem().toString(), boxTo.getSelectedItem().toString(), dis, congestion);
					canvas.repaint();
				} else
				{
					JOptionPane.showMessageDialog(null, "ERROR!! You cannot point one router to itself! Add Edge Failed!");
				}
				
				
			}
			
		});

		add_button.setBounds(10, 20, 150, 30);
		add_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//To-do
				String name = JOptionPane.showInputDialog(null, "Enter New Router Name.");
				if (name == null) {
					return;
				} else if (name.length() == 0)
				{
					JOptionPane.showMessageDialog(null, "Please Specify a name for new router");
					return;
				}
				Router newRouter = new Router(name);
				pointGenerator(newRouter);
				router_vector.add(newRouter);
				canvas.repaint();
			}
		});


		removeone_button.setBounds(170, 20, 150, 30);
		removeone_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				String routerName = JOptionPane.showInputDialog(null, "Enter the name of Router that you want to delete:");
				removeRouterByName(routerName);
				
				canvas.repaint();
				
			}
		});
		
		removeall_button.setBounds(330, 20, 150, 30);
		removeall_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeAllRouters();
				canvas.repaint();

			}
		});
		
		checkCongestion.setBounds(490, 25, 20, 20);
		checkCongestion.setSelected(false);
		
		JLabel congestionLabel = new JLabel("Congestion");
		congestionLabel.setBounds(510, 20, 150, 30);
		
		list_button.setBounds(490, 60, 150, 30);
		list_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (Router router : router_vector) {
					printRouterInfo(router);
				}

			}
		});

		removeedge_button.setBounds(170, 60, 150, 30);
		removeedge_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (router_vector.size() <= 1 ) {
					JOptionPane.showMessageDialog(frame, "Please add at least two routers first!");
					return;
				}
				String routerName [] = new String[router_vector.size()];
				for (int i = 0; i < routerName.length; i++) {
					routerName[i] = router_vector.get(i).getName();
				}
				JComboBox boxFrom = new JComboBox(routerName);
				JComboBox boxTo = new JComboBox(routerName);
				
				final JComponent[] inputs = new JComponent[] {
						new JLabel("From Router"),
						boxFrom,
						new JLabel("To Router"),
						boxTo
//						new JLabel("Distance"),
				};
				
				JOptionPane.showConfirmDialog(null, inputs, "Remove Edge",JOptionPane.OK_CANCEL_OPTION);
				if(boxFrom.getSelectedItem().toString() != boxTo.getSelectedItem().toString())
				{
					
					removeEdge(boxFrom.getSelectedItem().toString(), boxTo.getSelectedItem().toString());
					canvas.repaint();
				}
			}
		});
		
		compute_button.setBounds(330, 60, 150, 30);
		compute_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (router_vector.size() <= 1 ) {
					JOptionPane.showMessageDialog(frame, "Please add at least two routers first!");
					return;
				}
				String routerName [] = new String[router_vector.size()];
				for (int i = 0; i < routerName.length; i++) {
					routerName[i] = router_vector.get(i).getName();
				}
				JComboBox boxFrom = new JComboBox(routerName);
				JComboBox boxTo = new JComboBox(routerName);
				
				final JComponent[] inputs = new JComponent[] {
						new JLabel("From Router"),
						boxFrom,
						new JLabel("To Router"),
						boxTo
//						new JLabel("Distance"),
				};
				
				JOptionPane.showConfirmDialog(null, inputs, "Remove Edge",JOptionPane.OK_CANCEL_OPTION);
				if(boxFrom.getSelectedItem().toString() == boxTo.getSelectedItem().toString())
				{
					
					JOptionPane.showMessageDialog(null, "Cannot start from one router to itself! Failed!");
					return;
				}
				calculation(boxFrom.getSelectedItem().toString(), boxTo.getSelectedItem().toString(), checkCongestion.isSelected());
				canvas.setFlagFalse();
			}
		});
		
		canvas.setBounds(10, 100, 630, 415);
		canvas.setBackground(Color.white);

		frame_pane.add(canvas);
		frame_pane.add(input);
		frame_pane.add(add_button);
		frame_pane.add(compute_button);
		frame_pane.add(addedge_button);
		frame_pane.add(removeall_button);
		frame_pane.add(removeone_button);
		frame_pane.add(checkCongestion);
		frame_pane.add(list_button);
		frame_pane.add(removeedge_button);
		frame_pane.add(congestionLabel);

	}

/*
	void confirm_command(String command, int S) {
		if (command.equalsIgnoreCase("history")) {
			show_history(command_history);
			return;
		}
		if (command.length() != 0) {
			command_history.add(command);
			String[] command_array = command.split(" ");
			input.setText("");
			console.append("> " + command + "\n");
			//
			// Interpret command: clear
			if (command_array[0].equalsIgnoreCase("clear")) {
				console.setText("");
				return;
			}

			// Interpret command: add router [Router Name 1] [Router Name 2]
			// [Router Name 3] ...
			else if (S == 0) {
				// add new routers to router_vector
				for (int i = 0; i < command_array.length; i++) {
					if (nameTaken(command_array[i])) {
						console.append("New Router's name has been taken. NOTHING TO DO!\n");
						return;
					}
					Router newRouter = new Router(command_array[i]);
					pointGenerator(newRouter);
					router_vector.add(newRouter);
					console.append("New Router Added!\n");
				}
				return;
			}

			//
			// Interpret command: ls [Router Name] [Router Name] ...
			else if (command_array[0].equalsIgnoreCase("ls")
					&& command_array.length > 1) {
				for (int i = 1; i < command_array.length; i++) {
					if (nameTaken(command_array[i])) {
						console.append("Cannot Find Router With Name"
								+ command_array[i] + "\n");
						return;
					}
					printRouterInfoByName(command_array[i]);
				}
				return;
			}
			//
			// Interpret command: ls
			else if (command_array[0].equalsIgnoreCase("ls")
					&& command_array.length == 1) {
				for (Router router : router_vector) {
					printRouterInfo(router);
				}
				return;
			}
			//
			// Interpret command: rm *
			else if (command.equalsIgnoreCase("rm *")) {
				removeAllRouters();
				return;
			}
			//
			// Interpret command: add edge between [Router Name] and [Router
			// Name]\
			// distance [Double]
			else if (command_array[0].equalsIgnoreCase("add")
					&& command_array[1].equalsIgnoreCase("edge")
					&& command_array[2].equalsIgnoreCase("between")
					&& command_array[4].equalsIgnoreCase("and")
					&& command_array[6].equalsIgnoreCase("distance")) {
				addEdge(command_array[3], command_array[5],
						Double.parseDouble(command_array[7]));
				return;
			}
			//
			// Interpret command: rm edge between [Router Name] and [Router
			// Name]
			else if (command_array[0].equalsIgnoreCase("rm")
					&& command_array[1].equalsIgnoreCase("edge")
					&& command_array[2].equalsIgnoreCase("between")
					&& command_array[4].equalsIgnoreCase("and")) {
				removeEdge(command_array[3], command_array[5]);
				return;
			}
			//
			// Interpret command: rm [Router Name] [Router Name] ...
			else if (command_array[0].equalsIgnoreCase("rm")
					&& command_array.length > 1
					&& !command_array[1].equalsIgnoreCase("edge")) {
				for (int i = 1; i < command_array.length; i++) {
					removeRouterByName(command_array[i]);
				}
				return;
			}
			//
			// Interpret command: exit
			else if (command_array[0].equalsIgnoreCase("exit")) {
				System.exit(0);
			}
			//
			// Interpret command: topology
			else if (command_array[0].equalsIgnoreCase("topology")) {
				// Draw canvas = new Draw(router_vector);
				canvas.setFlagFalse();
				canvas.repaint(); // This method calls paintComponent;
				return;
			}
			//
			// Interpret command: calc from [Router Name] to [Router Name]
			else if (command_array[0].equalsIgnoreCase("calc")
					&& command_array[1].equalsIgnoreCase("from")
					&& command_array[3].equalsIgnoreCase("to")) {
				Router source = findRouterWithName(command_array[2]);
				Router destination = findRouterWithName(command_array[4]);

				if (source == null) {
					console.append("ERROR: Cannot find Router "
							+ command_array[2] + "!\n");
					return;
				}
				if (destination == null) {
					console.append("ERROR: Cannot find Router "
							+ command_array[4] + "!\n");
					return;
				}
				Dijkstra_Core calculate = new Dijkstra_Core(source, destination);
				List<Router> path = calculate.getRouterList();
				canvas.setRouterList(path);
				canvas.setFlagTrue();
				canvas.repaint();
				// canvas.setFlagFalse();

				console.append("*********RESULT**********\n");
				console.append("Distance from " + source.getName() + " to "
						+ destination.getName() + " is: "
						+ destination.getMinDistance() + "\n");

				String pathToString = convertListToString(path);

				console.append("Path: " + pathToString);
				console.append("*************************\n");
				return;
			}

			// if no command matched, return error info and this command
			// will not be recorded.
			console.append("SYNTAX ERROR: \"" + command
					+ "\" This command cannot be recognized!\n"
					+ "NOTHING TO DO!\n");
			command_history.remove(command);
		} else {
			console.append("> \n");
		}
	}
*/


	boolean nameTaken(String name) {
		for (Router router : router_vector) {
			if (router.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	void printRouterInfo(Router router) {
		String info = router.getName() + "   " + router.getMinDistance();
		for (Edge edge : router.getAdjacences()) {
			info += "   " + edge.getTarget().getName() + "   "
					+ edge.getWeight();
		}
		info += "\n";
//		console.append(info);
	}

	void printRouterInfoByName(String name) {
		Router router = findRouterWithName(name);
		String info = router.getName() + "   " + router.getMinDistance();
		for (Edge edge : router.getAdjacences()) {
			info += "   " + edge.getTarget().getName() + "   "
					+ edge.getWeight();
		}
		info += "\n";
//		console.append(info);

	}

	Router findRouterWithName(String name) {
		for (Router router : router_vector) {
			if (router.getName().equalsIgnoreCase(name)) {
				return router;
			}
		}
		return null;
	}

	void removeRouterByName(String name) {
		for (Router router : router_vector) {
			if (router.getName().equalsIgnoreCase(name)) {
				router_vector.remove(router);
//				console.append("Router " + name + " is removed.\n");
				return;
			}
		}
//		console.append("Cannot Find Router " + name + ".\n");
		JOptionPane.showMessageDialog(null, "Cannot find this router. Failed.");
	}

	void removeAllRouters() {
		if (router_vector.isEmpty()) {
//			console.append("No Router has been added" + "\n");
			return;
		}
		router_vector.clear();
//		console.append("All Routers Removed!");
	}

	void addEdge(String from, String to, double distance, double congestion) {

		Router target = findRouterWithName(to);
		Router source = findRouterWithName(from);
		if (source == null) {
//			console.append("Cannot Find Router With Name " + from + "\n");
			return;
		} else if (target == null) {
//			console.append("Cannot Find Router With Name " + to + "\n");
			return;
		}
		Edge edge = new Edge(target, distance, congestion);
		source.addAdjacent(edge);
		Edge edgeBack = new Edge(source, distance, congestion);
		target.addAdjacent(edgeBack);
//		console.append("New Edge Added!\n");
	}

	void removeEdge(String from, String to) {
		Router source = findRouterWithName(from);
		Router target = findRouterWithName(to);
		if (source == null || target == null) {
//			console.append("ERROR: Invalid Router!");
			return;
		}
		for (Edge edge : source.getAdjacences()) {
			if (edge.getTarget().getName().equalsIgnoreCase(to)) {
				source.getAdjacences().remove(edge);
				break;
			}
		}
		for (Edge edge : target.getAdjacences()) {
			if (edge.getTarget().getName().equalsIgnoreCase(from)) {
				target.getAdjacences().remove(edge);
//				console.append("Edge removed!");
				return;
			}
		}
//		console.append("ERROR: Cannot find corresponding edge!");
		JOptionPane.showMessageDialog(null, "Cannot find corresponding edge! Failed!");
	}

	void pointGenerator(Router router) {
		Random randomX = new Random();
		Random randomY = new Random();
		router.setPoint(randomX.nextInt(canvas.getBounds().width-20) + 10, randomY.nextInt(canvas.getBounds().height-20) + 10);

	}

	String convertListToString(List<Router> list) {
		String pathToString = "";
		for (Router router : list) {
			pathToString += router.getName() + " ";
		}
		return pathToString + "\n";
	}
	
	void calculation(String fromRouter, String toRouter, boolean congestion_bool)
	{
		Router source = findRouterWithName(fromRouter);
		Router destination = findRouterWithName(toRouter);
		
		Dijkstra_Core calculate = new Dijkstra_Core(source, destination, congestion_bool);
		List<Router> path = calculate.getRouterList();
		canvas.setRouterList(path);
		canvas.setFlagTrue();
		canvas.repaint();
//		canvas.setFlagFalse();
//		double distance = 0.;
//		if(checkCongestion.isSelected())
//		{
//			distance = destination.getMinDistanceCongestion();
//		} else
//		{
//			distance = destination.getMinDistance();
//		}
		JOptionPane.showMessageDialog(null, "Min distance is: " + destination.getMinDistanceCongestion());
	}
}
