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

	private JPanel panel;
	private JFrame frame;
	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton[][] buttons = new JButton[WIDTH][HEIGHT];
	private Node[][] nodes = new Node[WIDTH][HEIGHT];
	private Game game;
	private BevelBorder b4 = new BevelBorder(BevelBorder.RAISED); // 튀어나온 느낌 보더
	private SoftBevelBorder b5 = new SoftBevelBorder(SoftBevelBorder.LOWERED); // 들어간 느낌 보더

	private int[][] map = {
			// y: 0 1 2 3 4 5 6 7 8 9'0'1'2'3'4'5 //x
			{ 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 }, // 0
			{ 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1 }, // 1
			{ 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 }, // 2
			{ 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 3
			{ 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1 }, // 4
			{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1 }, // 5
			{ 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 6
			{ 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 7
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, // 8
			{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1 }, // 9
			{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1 }, // 10
			{ 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1 }, // 11
			{ 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1 }, // 12
			{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1 }, // 13
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 14
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } // 15
	};

	/**
	 * Create the frame.
	 */
	public Map(Game game) {
		initialize();
		this.game = game;
	}

	private void initialize() {

		// Frame 생성, 초기화
		frame = new JFrame();
		frame.getContentPane().setForeground(SystemColor.text);
		frame.getContentPane().setBackground(SystemColor.text);
		frame.setBounds(100, 100, 776, 880);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("A star Algorithm team 4");

		// 패널 생성, 초기화
		panel = new JPanel();
		panel.setBounds(12, 10, 736, 736);
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setLayout(null);

		// 버튼, 노드배열 생성, 초기화
		setButtonArray();

		// 리셋버튼 생성, 초기화
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
				//System.out.println("btn_reset");				
				game.redraw();
				//System.out.println("리드로우끝");	

			}
		});

		// Start 버튼 생성, 초기화
		JButton btn_start = new JButton("START");
		btn_start.setBounds(377, 756, 120, 64);
		buttonGroup.add(btn_start);
		btn_start.setEnabled(true);
		btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.exit(0);
				// frame.setVisible(false);
				LinkedList<Node> path = game.search(); // 탐색 시작
				System.out.println("path나옴");
				// 찾은 길로 진행
				game.pathDraw(path);
			}
		});
		btn_start.setFont(new Font("UD Digi Kyokasho NP-B", Font.PLAIN, 25));
		btn_start.setBorderPainted(false);
		btn_start.setBackground(Color.WHITE);
		btn_start.setForeground(Color.DARK_GRAY);

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(btn_reset);
		frame.getContentPane().add(btn_start);
		frame.setResizable(false);
		frame.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/* 버튼, 노드 초기화 */
	public void setButtonArray() {
		for (int i = 0; i < WIDTH; ++i) {
			for (int j = 0; j < HEIGHT; ++j) {

				JButton cell = new JButton();
				Node node = new Node(cell, i, j); // 밑에서 cell 속성 변경해도 같은 객체(주소)가지니까 상관없을듯?

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