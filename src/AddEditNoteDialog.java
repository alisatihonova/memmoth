import java.awt.Color;
import javax.swing.JColorChooser;

public class AddEditNoteDialog extends javax.swing.JDialog {

    MainPage mainPage;
    Note editNote;
    
    public AddEditNoteDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
        
    //�����������, ���������� ��� ����������/�������������� �������
    //�� ���� ��������� ������ ������� � ����� ������ - ��������/��������������
    public AddEditNoteDialog(Note pEditNote, String mode, MainPage pMainPage) {
        initComponents();
        this.setTitle(mode);
        editNote = pEditNote;
        mainPage = pMainPage;
        //userNotes = pUserNotes;
        this.titleField.setText(editNote.title);
        this.textArea.setBackground(editNote.noteColor);
        this.textArea.setText(editNote.text);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        colorButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(new java.awt.Point(550, 200));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleField.setBackground(new java.awt.Color(229, 227, 255));
        titleField.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        titleField.setText("��� ��������");
        titleField.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        titleField.setSelectionColor(new java.awt.Color(204, 255, 204));
        jPanel1.add(titleField, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 16, 370, 40));

        jScrollPane1.setBackground(new java.awt.Color(229, 227, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        textArea.setBackground(new java.awt.Color(229, 227, 255));
        textArea.setColumns(20);
        textArea.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        textArea.setRows(5);
        textArea.setText("������� ��� ������");
        textArea.setWrapStyleWord(true);
        textArea.setBorder(null);
        textArea.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        textArea.setSelectionColor(new java.awt.Color(204, 255, 204));
        jScrollPane1.setViewportView(textArea);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 360, 170));

        colorButton.setBackground(new java.awt.Color(204, 255, 204));
        colorButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        colorButton.setText("������� ����");
        colorButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        colorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorButtonActionPerformed(evt);
            }
        });
        jPanel1.add(colorButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 140, 40));

        saveButton.setBackground(new java.awt.Color(204, 255, 204));
        saveButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        saveButton.setText("���������");
        saveButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel1.add(saveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 140, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //���������� ������� �� ������ ������ �����
    private void colorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorButtonActionPerformed
        Color bufColor = new Color(229, 227, 255);
        editNote.noteColor = JColorChooser.showDialog(this, "���� �������", bufColor);
        
        if (editNote.noteColor == null) {
            editNote.noteColor = bufColor;
        }
        
        textArea.setBackground(editNote.noteColor);
    }//GEN-LAST:event_colorButtonActionPerformed

    
    //���������� ������� �� ������ ����������
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        //����������� ���������� - ��������� � ������ ����� ������
        if (this.getTitle().compareTo("�������� �������") == 0)
        {
            mainPage.userNotes.add(editNote);
            mainPage.model.addElement(editNote);
        }
        
        editNote.title = titleField.getText();
        editNote.text = textArea.getText();
        this.setVisible(false);
    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton colorButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField titleField;
    // End of variables declaration//GEN-END:variables
}
