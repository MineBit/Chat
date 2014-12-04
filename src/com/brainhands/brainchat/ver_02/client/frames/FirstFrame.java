package com.brainhands.brainchat.ver_02.client.frames;

import javax.swing.*;
import java.awt.*;

public class FirstFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private static JLabel QwestText;
	private static JButton FirstButton;
    private static JButton SecondButton;

    private static String Qwest_test = null;
    private static String First_button_text = null;
    private static String Second_button_text = null;
	
	public static void run(String title, String qwest_test, String first_button_text, String second_button_text) {
        Qwest_test = qwest_test;
        First_button_text = first_button_text;
        Second_button_text = second_button_text;
		try {
			FirstFrame dialog = new FirstFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FirstFrame() {
		setBounds(100, 100, 356, 185);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		{
			QwestText = new JLabel("Текст");
			QwestText.setFont(new Font("Tahoma", Font.PLAIN, 20));
			GridBagConstraints gbc_QwestText = new GridBagConstraints();
			gbc_QwestText.gridheight = 3;
			gbc_QwestText.gridwidth = 13;
			gbc_QwestText.insets = new Insets(0, 0, 5, 0);
			gbc_QwestText.gridx = 0;
			gbc_QwestText.gridy = 2;
			getContentPane().add(QwestText, gbc_QwestText);
		}
		{
			FirstButton = new JButton("Кнопка1");
			GridBagConstraints gbc_FirstButton = new GridBagConstraints();
			gbc_FirstButton.gridwidth = 6;
			gbc_FirstButton.insets = new Insets(0, 0, 0, 5);
			gbc_FirstButton.gridx = 0;
			gbc_FirstButton.gridy = 6;
			getContentPane().add(FirstButton, gbc_FirstButton);
		}
		{
			SecondButton = new JButton("Кнопка2");
			GridBagConstraints gbc_SecondButton = new GridBagConstraints();
			gbc_SecondButton.gridwidth = 6;
			gbc_SecondButton.gridx = 7;
			gbc_SecondButton.gridy = 6;
			getContentPane().add(SecondButton, gbc_SecondButton);
		}
	}

}
