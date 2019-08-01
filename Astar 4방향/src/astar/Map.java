package astar;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

@SuppressWarnings("serial")
public class Map extends JFrame {
	Route route;
	private JPanel panel;
	private JFrame frame;
	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton[][] buttons = new JButton[WIDTH][HEIGHT];
	private Node[][] nodes = new Node[WIDTH][HEIGHT];
	BevelBorder b4 = new BevelBorder(BevelBorder.RAISED); // Ƣ��� ���� ����
	SoftBevelBorder b5 = new SoftBevelBorder(SoftBevelBorder.LOWERED); // �� ���� ����
	LinkedList<Node> d1;
	private int[][] map = {
			// x: 0 1 2 3 4 5 6 7 8 9'0'1'2'3'4'5 //y
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 0
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 1
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 2
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 3
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 }, // 4
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 5
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 6
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 7
			{ 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 8
			{ 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 9
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 10
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 11
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 12
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 13
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 14
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } // 15
	};

	/**
	 * Create the frame.
	 */
	public Map(Route r) {
		this.route = r;
		initialize();
	}

	private void initialize() {

		// Frame ����, �ʱ�ȭ
		frame = new JFrame();
		frame.getContentPane().setForeground(SystemColor.text);
		frame.getContentPane().setBackground(SystemColor.text);
		frame.setBounds(100, 100, 776, 880);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("A star Algorithm team 4");

		// �г� ����, �ʱ�ȭ
		panel = new JPanel();
		panel.setBounds(12, 10, 736, 736);
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setLayout(null);

		// ��ư, ���迭 ����, �ʱ�ȭ
		setButtonArray();

		// ���¹�ư ����, �ʱ�ȭ
		JButton btn_reset = new JButton("RESET");
		btn_reset.setBounds(208, 756, 120, 64);
		buttonGroup.add(btn_reset);
		btn_reset.setBorderPainted(false);
		btn_reset.setBackground(Color.WHITE);
		btn_reset.setForeground(Color.DARK_GRAY);
		btn_reset.setFont(new Font("UD Digi Kyokasho NP-B", Font.PLAIN, 25));

		btn_reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				while (true) {
					int startx = (int) ((Math.random()) * WIDTH);
					int starty = (int) ((Math.random()) * HEIGHT);
					int endx = (int) ((Math.random()) * WIDTH);
					int endy = (int) ((Math.random()) * HEIGHT);

					if (startx == endx && starty == endy)
						continue;

					if (map[startx][starty] == 0 || map[endx][endy] == 0)
						continue;
					
					for (int i = 0; i < WIDTH; ++i) {
						for (int j = 0; j < HEIGHT; ++j) {
							if (map[i][j] == 0)
								buttons[i][j].setBackground(Color.BLACK);
							else
								buttons[i][j].setBackground(Color.WHITE);
						}
					}
					buttons[startx][starty].setBackground(Color.YELLOW);
					buttons[endx][endy].setBackground(Color.BLUE);
					System.out.println("draw ����");
					d1 = route.findPath(startx, starty, endx, endy);
					break;
				}
			}
		});
		
		
		// end ��ư ����, �ʱ�ȭ
		JButton btn_end = new JButton("START");
		btn_end.setBounds(377, 756, 120, 64);
		buttonGroup.add(btn_end);
		btn_end.setEnabled(true);
		btn_end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				for(int i = 0; i < route.copyopenList.size()-1; i++) {
					
					route.copyopenList.get(i).getButton().setBackground(Color.GREEN);
				}
				
				
				for(int i = 0; i < d1.size()-1; ++i) {
					d1.get(i).getButton().setBackground(Color.RED);
				}
			}
		});
		
		
		btn_end.setFont(new Font("UD Digi Kyokasho NP-B", Font.PLAIN, 25));
		btn_end.setBorderPainted(false);
		btn_end.setBackground(Color.WHITE);
		btn_end.setForeground(Color.DARK_GRAY);
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(btn_reset);
		frame.getContentPane().add(btn_end);
		frame.setResizable(false);
		frame.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/* ��ư, ��� �ʱ�ȭ */
	public void setButtonArray() {
		for (int i = 0; i < WIDTH; ++i) {
			for (int j = 0; j < HEIGHT; ++j) {

				JButton cell = new JButton();
				Node node = new Node(cell, i, j); // �ؿ��� cell �Ӽ� �����ص� ���� ��ü(�ּ�)�����ϱ� ���������?

				cell.setForeground(Color.BLACK);
				cell.setBorderPainted(false);
				if (map[i][j] == 0) { // wall
					cell.setBackground(Color.BLACK);
					cell.setBorder(b4);
					node.setRoad(false);
				} else { // road
					cell.setBackground(Color.WHITE);
					cell.setBorder(b5);
					node.setRoad(true);
				}

				cell.setBorderPainted(true);
				cell.setBounds(6 + ((j + 1) * 40), 5 + ((i + 1) * 40), 40, 40);
				cell.setEnabled(false);

				panel.add(cell);

				buttons[i][j] = cell;
				nodes[i][j] = node;
				System.out.print(nodes[i][j]);
			}
			System.out.println();
		}
	}

	public Node[][] getNodes() {
		return nodes;
	}
}