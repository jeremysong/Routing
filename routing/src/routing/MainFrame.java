package routing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ActionMapUIResource;

public class MainFrame {
	static JTextField input = new JTextField();
	static JTextArea console = new JTextArea();
	static JButton confirm_button = new JButton("Confirm");
	static JButton clear_button = new JButton("Clear History");
	static JButton add_button = new JButton("Add new Routers");
	static JButton compute_button = new JButton("Compute Path");
	static JButton config_button = new JButton("Add Edge");
	static JButton topology_button = new JButton("Show Topology");
	static JButton history_button = new JButton("Show History");
	static JButton removeall_button = new JButton("Remove All Routers");
	static JButton removeone_button = new JButton("Remove Router");
	static JButton list_button = new JButton("Router Information");
	static JButton removeedge_button = new JButton("Remove Edge");
	Vector<String> command_history = new Vector<String>(30);
	static Vector<Router> router_vector = new Vector<Router>(10);
	static JFrame frame = new JFrame();
	static Container frame_pane = frame.getContentPane();
	static Draw canvas = new Draw(router_vector);
	static int S;

	public static void main(String[] args) {
		final MainFrame main_frame = new MainFrame();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				main_frame.createMainFrame();
			}
		});
		/*
		 * InputMap keyMap = new ComponentInputMap(confirm_button);
		 * keyMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
		 * "confirm_action"); ActionMap actionMap = new ActionMapUIResource();
		 * actionMap.put("confirm_action", new AbstractAction() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { // TODO
		 * Auto-generated method stub
		 * main_frame.confirm_command(input.getText()); } });
		 * 
		 * SwingUtilities.replaceUIActionMap(confirm_button, actionMap);
		 * SwingUtilities.replaceUIInputMap(confirm_button,
		 * JComponent.WHEN_IN_FOCUSED_WINDOW, keyMap);
		 */
		console.append("Welcome to this simulation!\n");
		console.setCaretPosition(console.getDocument().getLength());

	}

	void createMainFrame() {

		frame.setBounds(200, 80, 825, 720);
		frame.setVisible(true);
		frame.setTitle("Revised Dijkstra Algorithm");
		frame.setResizable(false);
		frame_pane.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Font console_font = new Font("Arial", Font.BOLD, 14);
		JLabel console_label = new JLabel("Console:");
		console_label.setBounds(10, 0, 100, 40);
		console_label.setFont(console_font);
		Border border = BorderFactory.createLineBorder(Color.black);
		console.setBorder(border);
		console.setFont(console_font);
		console.setLineWrap(true);
		console.setEditable(false);

		JScrollPane scroll = new JScrollPane(console);
		scroll.setBounds(10, 40, 800, 180);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JLabel input_label = new JLabel("Typing Area:");
		input_label.setBounds(10, 225, 90, 40);
		input_label.setFont(console_font);

		input.setBounds(105, 233, 585, 30);
		input.setFont(console_font);

		confirm_button.setBounds(700, 233, 100, 30);

		compute_button.setBounds(490, 275, 150, 30);

		topology_button.setBounds(330, 275, 150, 30);
		topology_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.setFlagFalse();
				canvas.repaint(); // This method calls paintComponent;
				return;

			}
		});

		config_button.setBounds(170, 275, 150, 30);

		add_button.setBounds(10, 275, 150, 30);
		add_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				console.append("Please type in the names of the routers you want to be added in the typing area"
						+ "\nThen press Enter on the keyboard or the confirm button on the right of the typing area\n");
				int S = 1;
				confirm_command(null, S);
				return;

			}
		});

		clear_button.setBounds(650, 275, 150, 30);
		clear_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				console.setText("");
				return;

			}
		});

		history_button.setBounds(650, 315, 150, 30);
		history_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				show_history(command_history);
				return;

			}
		});
		removeone_button.setBounds(10, 315, 150, 30);

		removeall_button.setBounds(170, 315, 150, 30);
		removeall_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeAllRouters();
				return;

			}
		});

		list_button.setBounds(490, 315, 150, 30);
		list_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (Router router : router_vector) {
					printRouterInfo(router);
				}
				return;

			}
		});

		removeedge_button.setBounds(330, 315, 150, 30);

		canvas.setBounds(10, 355, 800, 325);
		canvas.setBackground(Color.white);

		frame_pane.add(canvas);
		frame_pane.add(scroll);
		frame_pane.add(console_label);
		frame_pane.add(input_label);
		frame_pane.add(input);
		frame_pane.add(clear_button);
		frame_pane.add(add_button);
		frame_pane.add(compute_button);
		frame_pane.add(config_button);
		frame_pane.add(topology_button);
		frame_pane.add(history_button);
		frame_pane.add(removeall_button);
		frame_pane.add(removeone_button);
		frame_pane.add(list_button);
		frame_pane.add(removeedge_button);
		frame_pane.add(confirm_button);

	}

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

	void show_history(Vector<String> history) {
		console.append("\n*********HISTROY*********\n");
		for (String str : history) {
			console.append(str + "\n");
		}
		console.append("*********HISTROY END*********\n\n");
	}

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
		console.append(info);
	}

	void printRouterInfoByName(String name) {
		Router router = findRouterWithName(name);
		String info = router.getName() + "   " + router.getMinDistance();
		for (Edge edge : router.getAdjacences()) {
			info += "   " + edge.getTarget().getName() + "   "
					+ edge.getWeight();
		}
		info += "\n";
		console.append(info);

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
				console.append("Router " + name + " is removed.\n");
				return;
			}
		}
		console.append("Cannot Find Router " + name + ".\n");
	}

	void removeAllRouters() {
		if (router_vector.isEmpty()) {
			console.append("No Router has been added" + "\n");
			return;
		}
		router_vector.clear();
		console.append("All Routers Removed!");
	}

	void addEdge(String from, String to, double distance) {
		if (from.equalsIgnoreCase(to) && distance == 0.) {
			console.append("The distance from one router to itself has to be ZERO. NOTHING TO DO.\n");
			return;
		}
		Router target = findRouterWithName(to);
		Router source = findRouterWithName(from);
		if (source == null) {
			console.append("Cannot Find Router With Name " + from + "\n");
			return;
		} else if (target == null) {
			console.append("Cannot Find Router With Name " + to + "\n");
			return;
		}
		Edge edge = new Edge(target, distance);
		source.addAdjacent(edge);
		Edge edgeBack = new Edge(source, distance);
		target.addAdjacent(edgeBack);
		console.append("New Edge Added!\n");
	}

	void removeEdge(String from, String to) {
		Router source = findRouterWithName(from);
		Router target = findRouterWithName(to);
		if (source == null || target == null) {
			console.append("ERROR: Invalid Router!");
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
				console.append("Edge removed!");
				return;
			}
		}
		console.append("ERROR: Cannot find corresponding edge!");
	}

	void pointGenerator(Router router) {
		Random randomX = new Random();
		Random randomY = new Random();
		router.setPoint(randomX.nextInt(800) + 10, randomY.nextInt(270) + 10);

	}

	String convertListToString(List<Router> list) {
		String pathToString = "";
		for (Router router : list) {
			pathToString += router.getName() + " ";
		}
		return pathToString + "\n";
	}
}
