// Hangbot

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

public class Hangbot extends JFrame {
	JPanel cards = new JPanel();
	JPanel pnlStart, pnlComplete, pnlInGame, pnlFail;
	JLabel lblToAnswer, lblRobotBody, lblRobotRight, lblRobotLeft;
	JButton[] btnLetter;
	Hang hang = new Hang();
	ImageIcon imgIconBg = new ImageIcon("Images/Background.png");
	ImageIcon imgIconRope = new ImageIcon("Images/Line.png");
	ImageIcon imgIconBgScaled = new ImageIcon(imgIconBg.getImage().getScaledInstance(900, 500, java.awt.Image.SCALE_SMOOTH));
	int fail = 0, levels = 0;
	String underline;
	
	static ImageIcon imgIcon = new ImageIcon("Images/Robot1.png");

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				myGUI();
			}
		});
	}

	private static void myGUI() {
		Hangbot hangbot = new Hangbot("Hangbot");
		hangbot.myComponents(hangbot.getContentPane());
		hangbot.setSize(900, 500);
		hangbot.setLocationRelativeTo(null);
		hangbot.setResizable(false);
		hangbot.setVisible(true);
		hangbot.setIconImage(imgIcon.getImage());
		hangbot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Hangbot(String name) {
		super(name);
	}

	ActionListener aPerformed = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout)(cards.getLayout());
			pnlInGame = new JPanel();
			cards.add(pnlInGame, "in-game");

			if (e.getActionCommand().equals("Start")) {
				cards.remove(pnlStart);
				cl.show(cards, "in-game");
				underline = "";
				hang.discreteProcedure(levels);
				int x = 1, y = 1, numPerRow = 7, distance = 240;

				for (int i = 0; i < hang.getGuessWord().length; i++) {
					underline += hang.getGuessWord()[i];

					if (i + 1 != hang.getGuessWord().length)
						underline += " ";
				}
				
				ImageIcon imgIconRobotBody = new ImageIcon("Images/Robot Body1.png");
				ImageIcon imgIconRobotRight = new ImageIcon("Images/Robot Right Arm1.png");
				ImageIcon imgIconRobotLeft = new ImageIcon("Images/Robot Left Arm1.png");
				ImageIcon imgIconTxt = new ImageIcon("Images/Text Area.png");
				
				imgIconRobotBody = new ImageIcon(imgIconRobotBody.getImage().getScaledInstance(280, 280, java.awt.Image.SCALE_SMOOTH));
				imgIconRobotRight = new ImageIcon(imgIconRobotRight.getImage().getScaledInstance(280, 280, java.awt.Image.SCALE_SMOOTH));
				imgIconRobotLeft = new ImageIcon(imgIconRobotLeft.getImage().getScaledInstance(280, 280, java.awt.Image.SCALE_SMOOTH));
				
				imgIconTxt = new ImageIcon(imgIconTxt.getImage().getScaledInstance(460, 100, java.awt.Image.SCALE_SMOOTH));
				
				lblRobotBody = new JLabel(imgIconRobotBody);
				lblRobotBody.setBounds(40, 20, 280, 280);

				lblRobotRight = new JLabel(imgIconRobotRight);
				lblRobotRight.setBounds(40, 20, 280, 280);

				lblRobotLeft = new JLabel(imgIconRobotLeft);
				lblRobotLeft.setBounds(40, 20, 280, 280);

				JLabel lblBackground = new JLabel(imgIconBgScaled);
				lblBackground.setBounds(0, 0, 900, 500);

				JLabel[] lblRope = new JLabel[3];				
				JLabel lblTextArea = new JLabel(imgIconTxt);

				lblToAnswer = new JLabel(underline);
				btnLetter = new JButton[26];
				lblTextArea.setBounds(340, 70, 460, 100);
				
				lblToAnswer.setBounds(340, 70, 460, 100);
				lblToAnswer.setHorizontalAlignment(SwingConstants.CENTER);
				lblToAnswer.setFont(new Font("Consolas", Font.BOLD, 30));
				lblToAnswer.setForeground(Color.white);
				
				for (int i = 1; i <= 26; i++) {
					char c = 'A';

					ImageIcon imgIconBtn = new ImageIcon("Images/Button Letter" + i + ".png");
					imgIconBtn = new ImageIcon(imgIconBtn.getImage().getScaledInstance(62, 55, java.awt.Image.SCALE_SMOOTH));

					btnLetter[i - 1] = new JButton(imgIconBtn);
					btnLetter[i - 1].setBounds(distance + (x * 100), 160 + (y * 30), 62, 55);
					btnLetter[i - 1].setContentAreaFilled(false);
					btnLetter[i - 1].setBorderPainted(false);
					btnLetter[i - 1].setActionCommand((c += i - 1) + "");
					btnLetter[i - 1].addActionListener(answer);

					pnlInGame.add(btnLetter[i - 1]);

					if (x % numPerRow != 0) {				
						x++;
					} else {
						y++;
						x = 1;
					}

					if (y % 2 != 0) {
						numPerRow = 5;
						distance = 240;
					} else {
						numPerRow = 4;
						distance = 290;
					}
				}

				pnlInGame.setLayout(null);
				pnlInGame.add(lblRobotBody);
				pnlInGame.add(lblRobotRight);
				pnlInGame.add(lblRobotLeft);
				
				for (int i = 0; i < 3; i++) {
					lblRope[i] = new JLabel(imgIconRope);
					lblRope[i].setBounds(100 + (i * 75), -5, 5, 200);
					pnlInGame.add(lblRope[i]);
				}

				pnlInGame.add(lblToAnswer);
				pnlInGame.add(lblTextArea);
				pnlInGame.add(lblBackground);		
			}
		}
	};

	ActionListener answer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout)(cards.getLayout());
			char c = 'A';

			for (int i = 0; i < 26; i++, c++) {
				if (e.getActionCommand().equals(c + "")) {
					hang.setAnswer(c);
					btnLetter[i].setEnabled(false);
				}
			}

			if (hang.validate() == true) {
				underline = "";

				for (int i = 0; i < hang.getGuessWord().length; i++) {
					underline += hang.getGuessWord()[i];

					if (i + 1 != hang.getGuessWord().length)
						underline += " ";
				}

				lblToAnswer.setText(underline);

				if (hang.complete() == true) {
					levels++;
					cards.remove(pnlInGame);
					pnlComplete = new JPanel();

					ImageIcon imgIconRobot = imgIcon;
					imgIconRobot = new ImageIcon(imgIconRobot.getImage().getScaledInstance(330, 330, java.awt.Image.SCALE_SMOOTH));

					ImageIcon imgIconTitle = new ImageIcon("Images/Completed.png");
					imgIconTitle = new ImageIcon(imgIconTitle.getImage().getScaledInstance(340, 95, java.awt.Image.SCALE_SMOOTH));

					ImageIcon imgIconBtn = new ImageIcon("Images/Next Button.png");
					if (levels < 5)
						imgIconBtn = new ImageIcon(imgIconBtn.getImage().getScaledInstance(155, 70, java.awt.Image.SCALE_SMOOTH));

					JLabel lblBackground = new JLabel(imgIconBgScaled);
					lblBackground.setBounds(0, 0, 900, 500);

					JLabel lblRobot = new JLabel(imgIconRobot);
					lblRobot.setBounds(70, 130, 330, 330);

					JLabel lblTitle = new JLabel(imgIconTitle);
					lblTitle.setBounds(400, 100, 340, 95);

					JButton btnStart = new JButton(imgIconBtn);
					if (levels < 5) {
						btnStart.setBounds(490, 210, 155, 70);
						btnStart.setFocusable(false);
						btnStart.setOpaque(false);
						btnStart.setContentAreaFilled(false);
						btnStart.setBorderPainted(false);
						btnStart.setActionCommand("Start");
						btnStart.addActionListener(aPerformed);
					}
					
					pnlComplete.setLayout(null);
					pnlComplete.add(lblRobot);
					pnlComplete.add(lblTitle);

					if (levels < 5)
						pnlComplete.add(btnStart);

					pnlComplete.add(lblBackground);
					
					cards.add(pnlComplete, "complete");
					cl.show(cards, "complete");
					fail = 0;
				}				
			} else if(hang.validate() == false) {
				fail++;

				if (fail == 1) {
					lblRobotRight.setVisible(false);
				} else if(fail == 2) {
					lblRobotLeft.setVisible(false);
				} else if(fail == 3) {
					lblRobotBody.setVisible(false);
					pnlFail = new JPanel();

					ImageIcon imgIconRobot = imgIcon;
					imgIconRobot = new ImageIcon(imgIconRobot.getImage().getScaledInstance(330, 330, java.awt.Image.SCALE_SMOOTH));

					ImageIcon imgIconTitle = new ImageIcon("Images/Game Over.png");
					imgIconTitle = new ImageIcon(imgIconTitle.getImage().getScaledInstance(340, 95, java.awt.Image.SCALE_SMOOTH));

					ImageIcon imgIconBtn = new ImageIcon("Images/Again Button.png");
					imgIconBtn = new ImageIcon(imgIconBtn.getImage().getScaledInstance(155, 70, java.awt.Image.SCALE_SMOOTH));

					JLabel lblBackground = new JLabel(imgIconBgScaled);
					lblBackground.setBounds(0, 0, 900, 500);

					JLabel lblRobot = new JLabel(imgIconRobot);
					lblRobot.setBounds(70, 130, 330, 330);

					JLabel lblTitle = new JLabel(imgIconTitle);
					lblTitle.setBounds(400, 100, 340, 95);

					JButton btnStart = new JButton(imgIconBtn);
					btnStart.setBounds(490, 210, 155, 70);
					btnStart.setFocusable(false);
					btnStart.setOpaque(false);
					btnStart.setContentAreaFilled(false);
					btnStart.setBorderPainted(false);
					btnStart.setActionCommand("Start");
					btnStart.addActionListener(aPerformed);
					
					pnlFail.setLayout(null);
					pnlFail.add(lblRobot);
					pnlFail.add(lblTitle);
					pnlFail.add(btnStart);
					pnlFail.add(lblBackground);
					
					cards.add(pnlFail, "fail");
					cl.show(cards, "fail");
					fail = 0;
				}
			}
		}
	};

	private void myComponents(final Container pane) {
		startingPanel();

		cards = new JPanel(new CardLayout());
		cards.add(pnlStart, "start");		
		pane.add(cards, BorderLayout.CENTER);

		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "start");
	}

	private void startingPanel() {
		pnlStart = new JPanel();

		ImageIcon imgIconRobot = imgIcon;
		ImageIcon imgIconTitle = new ImageIcon("Images/Title.png");
		ImageIcon imgIconBtn = new ImageIcon("Images/Start Button.png");

		imgIconRobot = new ImageIcon(imgIconRobot.getImage().getScaledInstance(330, 330, java.awt.Image.SCALE_SMOOTH));
		imgIconTitle = new ImageIcon(imgIconTitle.getImage().getScaledInstance(340, 95, java.awt.Image.SCALE_SMOOTH));
		imgIconBtn = new ImageIcon(imgIconBtn.getImage().getScaledInstance(155, 70, java.awt.Image.SCALE_SMOOTH));

		JLabel lblBackground = new JLabel(imgIconBgScaled);
		lblBackground.setBounds(0, 0, 900, 500);

		JLabel lblRobot = new JLabel(imgIconRobot);
		lblRobot.setBounds(70, 130, 330, 330);

		JLabel lblTitle = new JLabel(imgIconTitle);
		lblTitle.setBounds(400, 100, 340, 95);

		JButton btnStart = new JButton(imgIconBtn);
		btnStart.setBounds(490, 210, 155, 70);
		btnStart.setFocusable(false);
		btnStart.setOpaque(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setBorderPainted(false);
		btnStart.setActionCommand("Start");
		btnStart.addActionListener(aPerformed);

		pnlStart.setLayout(null);
		pnlStart.add(lblRobot);
		pnlStart.add(btnStart);
		pnlStart.add(lblTitle);
		pnlStart.add(lblBackground);
	}
}