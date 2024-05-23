import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel {

	private JPanel leftPanel, rightPanel, leftDown, leftUp;
	private JLabel idLabel, takenLabel, providedLabel, pointsLabel, LvLabel;
	private JButton levelUpButton;
	private JTextArea textArea, historyTextArea;
	private JLabel itemTakenLabel, itemProvidedLabel;
	private int pointsSpent, accumulatedPoints;
	private JPanel histroyPanel;
	private JComboBox<String> LvCombo;

	public InfoPanel() {
		this.pointsSpent = 0;
		this.accumulatedPoints = 90;
		this.itemTakenLabel = new JLabel("你在____拿了____個____");
		this.itemProvidedLabel = new JLabel("你在____提供了____個____");

		createLayout();
		createBtn();
	}

	public void createLayout() {
		setLayout(new GridLayout(0, 1, 0, 0));

		leftPanel = new JPanel();
		leftPanel.setLayout(null);

		idLabel = new JLabel("   ID: ");
		idLabel.setHorizontalAlignment(SwingConstants.LEFT);
		idLabel.setBounds(0, 16, 300, 40);
		leftUp = new JPanel(new BorderLayout());
		leftUp.setBounds(0, 16, 232, 40);
		leftUp.add(idLabel, BorderLayout.WEST);
		leftPanel.add(leftUp);

		takenLabel = new JLabel("   本次已取: ____ 項用品");
		takenLabel.setBounds(0, 108, 300, 55);
		leftPanel.add(takenLabel);

		providedLabel = new JLabel("   總提供: ____ 項用品");
		providedLabel.setBounds(0, 144, 300, 61);
		leftPanel.add(providedLabel);

		pointsLabel = new JLabel("   累計點數: " + accumulatedPoints);
		pointsLabel.setBounds(0, 220, 300, 55);

		leftDown = new JPanel(new BorderLayout());
		leftDown.setBounds(0, 250, 272, 40);
		leftPanel.add(leftDown);

		// 右邊面板
		rightPanel = new JPanel();
		rightPanel.setLayout(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		mainPanel.add(leftPanel);
		LvCombo = new JComboBox<>(new String[] { "Lv.1 可愛的人" });
		LvCombo.setBounds(6, 56, 138, 40);
		leftPanel.add(LvCombo);
		mainPanel.add(rightPanel);

		JPanel achivePanel = new JPanel();
		achivePanel.setBounds(6, 6, 288, 159);
		rightPanel.add(achivePanel);
		achivePanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("成就牆:");
		lblNewLabel.setBounds(0, 0, 288, 30);
		achivePanel.add(lblNewLabel);

		textArea = new JTextArea();
		textArea.setBounds(0, 31, 288, 127);
		achivePanel.add(textArea);
		textArea.append("Lv.1 可愛的人");

		histroyPanel = new JPanel();
		histroyPanel.setBounds(10, 174, 288, 220);
		rightPanel.add(histroyPanel);
		histroyPanel.setLayout(null);

		JLabel label = new JLabel("歷史紀錄:");
		label.setBounds(0, 0, 288, 30);
		histroyPanel.add(label);

		historyTextArea = new JTextArea();
		historyTextArea.setTabSize(7);
		historyTextArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(historyTextArea);
		scrollPane.setBounds(0, 28, 288, 192);
		histroyPanel.add(scrollPane);

		add(mainPanel);
		setVisible(true);
	}

	public void createBtn() {
		levelUpButton = new JButton("Level up!");

		levelUpButton.setEnabled(false);
		int lineCount = textArea.getLineCount();
		String lastLine = null;

		if (lineCount > 0) {
			try {
				int endOffset = textArea.getLineEndOffset(lineCount - 1);
				int startOffset = textArea.getLineStartOffset(lineCount - 1);
				lastLine = textArea.getText(startOffset, endOffset - startOffset);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		String level = lastLine;
		switch (level) {
		case "Lv.1 可愛的人":
			if (accumulatedPoints > 10) {
				levelUpButton.setEnabled(true);
			}
			break;
		case "Lv.10 溫暖的人":
			if (accumulatedPoints > 20) {
				levelUpButton.setEnabled(true);
			}
			break;
		case "Lv.20 可愛的天使":
			if (accumulatedPoints > 30) {
				levelUpButton.setEnabled(true);
			}
			break;
		case "Lv.30 溫暖的天使":
			if (accumulatedPoints > 40) {
				levelUpButton.setEnabled(true);
			}
			break;
		case "Lv.40 可愛的神":
			if (accumulatedPoints > 50) {
				levelUpButton.setEnabled(true);
			}
			break;
		}

		levelUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				levelUpButton.setEnabled(false);
				int lineCount = textArea.getLineCount();
				String lastLine = null;

				if (lineCount > 0) {
					try {
						int endOffset = textArea.getLineEndOffset(lineCount - 1);
						int startOffset = textArea.getLineStartOffset(lineCount - 1);
						lastLine = textArea.getText(startOffset, endOffset - startOffset);
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				}

				String level = lastLine;
				switch (level) {
				case "Lv.1 可愛的人":
					textArea.append("\n");
					textArea.append("Lv.10 溫暖的人");
					accumulatedPoints -= 10;
					historyTextArea.append("感謝你為這世上增添了一份溫暖，\n恭喜你成為Lv.10 溫暖的人!\n");
					level = "Lv.10 溫暖的人";
					LvCombo.addItem("Lv.10 溫暖的人");
					break;
				case "Lv.10 溫暖的人":
					textArea.append("\n");
					textArea.append("Lv.20 可愛的天使");
					accumulatedPoints -= 20;
					historyTextArea.append("感謝你為這世上增添了一份溫暖，\n恭喜你成為Lv.20 可愛的天使!\n");
					level = "Lv.20 可愛的天使";
					LvCombo.addItem("Lv.20 可愛的天使");
					break;
				case "Lv.20 可愛的天使":
					textArea.append("\n");
					textArea.append("Lv.30 溫暖的天使");
					accumulatedPoints -= 30;
					historyTextArea.append("感謝你為這世上增添了一份溫暖，\n恭喜你成為Lv.30 溫暖的天使!\n");
					level = "Lv.30 溫暖的天使";
					LvCombo.addItem("Lv.30 溫暖的天使");

					break;
				case "Lv.30 溫暖的天使":
					textArea.append("\n");
					textArea.append("Lv.40 可愛的神");
					accumulatedPoints -= 40;
					historyTextArea.append("感謝你為這世上增添了一份溫暖，\n恭喜你成為Lv.40 可愛的神!\n");
					level = "Lv.40 可愛的神";
					LvCombo.addItem("Lv.40 可愛的神");

					break;
				case "Lv.40 可愛的神":
					textArea.append("\n");
					textArea.append("Lv.50 溫暖的神");
					accumulatedPoints -= 50;
					historyTextArea.append("感謝你為這世上增添了一份溫暖，\n恭喜你成為Lv.50 溫暖的神!\n");
					level = "Lv.50 溫暖的神";
					LvCombo.addItem("Lv.50 溫暖的神");
					break;
				}
				pointsLabel.setText("   累計點數: " + accumulatedPoints);

				// 歷史紀錄更新
				historyTextArea.append("你在____拿了____個____\n");
				historyTextArea.append("你在____提供了____個____\n");
				historyTextArea.append("xxx感謝了你!\n");

				switch (level) {
				case "Lv.1 可愛的人":
					if (accumulatedPoints > 10) {
						levelUpButton.setEnabled(true);
					}
					break;
				case "Lv.10 溫暖的人":
					if (accumulatedPoints > 20) {
						levelUpButton.setEnabled(true);
					}
					break;
				case "Lv.20 可愛的天使":
					if (accumulatedPoints > 30) {
						levelUpButton.setEnabled(true);
					}
					break;
				case "Lv.30 溫暖的天使":
					if (accumulatedPoints > 40) {
						levelUpButton.setEnabled(true);
					}
					break;
				case "Lv.40 可愛的神":
					if (accumulatedPoints > 50) {
						levelUpButton.setEnabled(true);
					}
					break;
				}
			}
		});

		leftDown.add(levelUpButton, BorderLayout.EAST);
		leftDown.add(pointsLabel, BorderLayout.CENTER);
	}

	/*public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("InfoPanel");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new InfoPanel());
				frame.pack();
				frame.setSize(600, 500);
				frame.setVisible(true);
			}
		});
	}*/
}