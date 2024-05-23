import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

public class InfoSharePanel extends JPanel {

    private JTextField titleField;
    private JLabel nameLabel;
    private JTextArea contentArea;
    private JButton publishButton, updateButton, deleteButton;
    private JTable postTable;
    private DefaultTableModel tableModel;
    private boolean isNewPost = true;
    private JTextField searchField;
    private JButton searchButton;
 private String username;

    public InfoSharePanel() {
//     this.username = username;
        setLayout(new BorderLayout());

        // Left Panel for inputs and buttons
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input panel
        JPanel insertPanel = new JPanel(new BorderLayout());
        String IdNum = "112306024";
        nameLabel = new JLabel("發布者學號" + IdNum);
        titleField = new JTextField("貼文標題");
        titleField.setEditable(false);
        contentArea = new JTextArea("貼文內容", 10, 20);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        insertPanel.add(nameLabel, BorderLayout.NORTH);
        insertPanel.add(titleField, BorderLayout.CENTER);
        insertPanel.add(new JScrollPane(contentArea), BorderLayout.SOUTH);
        leftPanel.add(insertPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        publishButton = new JButton("建立新貼文");
        updateButton = new JButton("修改内容");
        deleteButton = new JButton("刪除");
        buttonPanel.add(publishButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);

        // Right Panel for displaying posts
        JPanel rightPanel = new JPanel(new BorderLayout());
        searchField = new JTextField("根據標題搜索內容", 15);
        searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchButton);
        rightPanel.add(searchPanel, BorderLayout.NORTH);

        // Table for posts
        String[] columnNames = {"發布者", "標題", "內容預覽", "完整內容"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        postTable = new JTable(tableModel);
        TableColumnModel columnModel = postTable.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(3));
        rightPanel.add(new JScrollPane(postTable), BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);

        // Configure button actions and other interactions
        configureButtonActions(IdNum);
    }

    private void configureButtonActions(String IdNum) {
        publishButton.addActionListener(e -> {
            if (isNewPost) {
                titleField.setText("");
                contentArea.setText("");
                titleField.setEditable(true);
                contentArea.setEditable(true);
                publishButton.setText("發布貼文");
                isNewPost = false;
            } else {
                String title = titleField.getText();
                String content = contentArea.getText();
                if (!title.isEmpty() && !content.isEmpty()) {
                    String contentPreview = content.length() > 30 ? content.substring(0, 30) + "..." : content;
                    tableModel.addRow(new Object[]{IdNum, title, contentPreview, content});
                    
                    titleField.setText("貼文標題");
                    contentArea.setText("貼文內容");
                    titleField.setEditable(false);
                    contentArea.setEditable(false);
                    publishButton.setText("建立新貼文");
                    isNewPost = true;
                } else {
                    JOptionPane.showMessageDialog(this, "標題和內容不能為空", "錯誤", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        postTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = postTable.getSelectedRow();
                if (selectedRow != -1) {
                    String publisherId = (String) tableModel.getValueAt(selectedRow, 0);
                    // 對比使用者學號與發布者學號，若相同才可編輯
                    updateButton.setVisible(publisherId.equals(IdNum));
                    titleField.setEditable(publisherId.equals(IdNum));
                    contentArea.setEditable(publisherId.equals(IdNum));
                    updateDetailList(selectedRow);

                } else {
                    updateButton.setVisible(false);
                }
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = postTable.getSelectedRow();
            if (selectedRow >= 0) {
                // 彈出確認對話框
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "你確定要修改嗎?",
                    "確認修改",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                // 如果用戶點擊“是”（YES），則執行更新操作
                if (confirm == JOptionPane.YES_OPTION) {
                    String title = titleField.getText();
                    String content = contentArea.getText();
                    if (!title.isEmpty() && !content.isEmpty()) {
                        // 更新表格模型的數據
                        tableModel.setValueAt(title, selectedRow, 1);  // 更新標題
                        tableModel.setValueAt(content, selectedRow, 3); // 更新完整內容
                        String contentPreview = content.length() > 30 ? content.substring(0, 30) + "..." : content;
                        tableModel.setValueAt(contentPreview, selectedRow, 2); // 更新內容預覽

                        // 顯示更新成功的提示信息
                        JOptionPane.showMessageDialog(this, "貼文已更新", "提醒", JOptionPane.INFORMATION_MESSAGE);
                        publishButton.setText("建立新貼文");
                        updateButton.setVisible(false);

                        // 重置新帖文的狀態
                        isNewPost = true;
                    } else {
                        // 如果標題或內容為空，顯示錯誤提示
                        JOptionPane.showMessageDialog(this, "標題和內容不能為空", "錯誤", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        deleteButton.addActionListener(e -> {
            int selectedRow = postTable.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "貼文已刪除", "提醒", JOptionPane.INFORMATION_MESSAGE);
                if (postTable.getRowCount() == 0) { // Check if the table is empty to reset to new post state
                    isNewPost = true;
                    titleField.setEditable(false);
                    contentArea.setEditable(false);
                    publishButton.setText("建立新貼文");
                    updateButton.setVisible(false);
                }
            }
        });

        searchButton.addActionListener(e -> filterData());
    }

    private void updateDetailList(int selectedRow) {
        if (selectedRow >= 0) {
            String publisher = (String) tableModel.getValueAt(selectedRow, 0); // 發布者
            String title = (String) tableModel.getValueAt(selectedRow, 1); // 標題
            String content = (String) tableModel.getValueAt(selectedRow, 3); // 完整内容(有隱藏)

            titleField.setText(title);
            contentArea.setText(content);
            nameLabel.setText("發布者學號" + publisher);
        }
    }
    
    private void filterData() {
        String searchText = searchField.getText();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        postTable.setRowSorter(sorter);
        if (searchText.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            // The filter checks for a match in the title (column index 1)
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(searchText), 1));
        }
    }}