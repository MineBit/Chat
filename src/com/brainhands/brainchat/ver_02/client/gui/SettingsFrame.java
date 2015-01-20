package com.brainhands.brainchat.ver_02.client.gui;

import com.brainhands.brainchat.ver_02.client.ChatClient;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Mine_Bit[Brain Hands]
 * forum.brainhands.ru
 * brain-soft.org
 */

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	public static void View() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsFrame frame = new SettingsFrame();
					frame.setVisible(true);
					frame.setTitle(ChatClient.Version);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SettingsFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 209);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JRadioButton SkinSystemChButton = new JRadioButton("Стиль System");
		SkinSystemChButton.setSelected(true);
		
		JRadioButton SkinBrainChButton = new JRadioButton("Стиль Brain");
		
		JRadioButton SkinNimbusChButton = new JRadioButton("Стиль Nimbus");
		
		JRadioButton SkinMetalChButton = new JRadioButton("Стиль Metal");
		
		JLabel Lb1 = new JLabel("Стиль интерфейса");
		Lb1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JRadioButton LangRusChButtton = new JRadioButton("Русский");
		
		JRadioButton LangEngChButton = new JRadioButton("English");
		
		JLabel Lb2 = new JLabel("Язык");
		Lb2.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton ExitButton = new JButton("Выход");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(52, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(SkinSystemChButton)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(SkinBrainChButton)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(SkinNimbusChButton)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(SkinMetalChButton)
										.addGap(43))
									.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
										.addComponent(Lb1)
										.addGap(154)))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(LangRusChButtton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(LangEngChButton)
									.addGap(160)))
							.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addComponent(Lb2)
								.addGap(203)))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(ExitButton)
							.addGap(189))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(Lb2)
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(LangRusChButtton)
						.addComponent(LangEngChButton))
					.addGap(18)
					.addComponent(Lb1)
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(SkinMetalChButton)
						.addComponent(SkinNimbusChButton)
						.addComponent(SkinBrainChButton)
						.addComponent(SkinSystemChButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ExitButton)
					.addContainerGap(112, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
