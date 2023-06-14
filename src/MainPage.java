import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainPage extends javax.swing.JFrame {
    Client client;
    User user;
    public LinkedList<Note> userNotes = new LinkedList<>();
    
    public MainPage(Client pClient, User pUser) {
        client = pClient;
        user = pUser;
        initComponents();
        usernameField.setText(user.username);
        
        //Получение с сервера данных пользователя
        String fileResult = client.GetFileMessage(user.login);
                
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D://superfiles//memmoth//client//" + user.login + ".txt"));
            try {
                userNotes = (LinkedList<Note>)ois.readObject();
                ois.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }            
            
        noteList.setModel(model);
        noteList.setBackground(new Color(229, 227, 255));
        
        for (int i = 0; i < userNotes.size(); i++)
        {
            model.addElement(userNotes.get(i));
        }
                
        noteList.getSelectionModel().addListSelectionListener(e -> {
            Note viewNote = noteList.getSelectedValue();
            if (noteList.getSelectedValue() != null) {
                noteBody.setBackground(viewNote.getColor());
                noteBody.setText(viewNote.getText());
            } else {
                noteBody.setBackground(new Color(229, 227, 255));
                noteBody.setText("");
            }
           
        });
        
        splitPane.setLeftComponent(new javax.swing.JScrollPane(noteList));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        usernameField = new javax.swing.JLabel();
        noteAreaPanel = new javax.swing.JPanel();
        splitPane = new javax.swing.JSplitPane();
        noteBodyScroll = new javax.swing.JScrollPane();
        noteBody = new javax.swing.JTextArea();
        exitButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("memmoth");
        setLocation(new java.awt.Point(450, 100));
        setMaximumSize(new java.awt.Dimension(710, 435));
        setMinimumSize(new java.awt.Dimension(710, 435));
        setPreferredSize(new java.awt.Dimension(710, 435));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setBackground(new java.awt.Color(204, 204, 255));
        mainPanel.setMaximumSize(new java.awt.Dimension(700, 430));
        mainPanel.setMinimumSize(new java.awt.Dimension(700, 430));
        mainPanel.setPreferredSize(new java.awt.Dimension(700, 430));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameField.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        usernameField.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        usernameField.setText("jLabel1");
        mainPanel.add(usernameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        noteAreaPanel.setBackground(new java.awt.Color(229, 227, 255));
        noteAreaPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        splitPane.setResizeWeight(0.5);

        noteBodyScroll.setBorder(null);
        noteBodyScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        noteBody.setEditable(false);
        noteBody.setBackground(new java.awt.Color(229, 227, 255));
        noteBody.setColumns(20);
        noteBody.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        noteBody.setLineWrap(true);
        noteBody.setRows(5);
        noteBody.setBorder(null);
        noteBody.setMaximumSize(new java.awt.Dimension(300, 380));
        noteBody.setMinimumSize(new java.awt.Dimension(300, 380));
        noteBody.setName(""); // NOI18N
        noteBody.setPreferredSize(new java.awt.Dimension(300, 380));
        noteBody.setSelectionColor(new java.awt.Color(204, 255, 204));
        noteBodyScroll.setViewportView(noteBody);

        splitPane.setRightComponent(noteBodyScroll);

        noteAreaPanel.add(splitPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 380));

        mainPanel.add(noteAreaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 490, 380));

        exitButton.setBackground(new java.awt.Color(204, 255, 204));
        exitButton.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        exitButton.setText("Выход");
        exitButton.setBorder(null);
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        mainPanel.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 180, 30));

        addButton.setBackground(new java.awt.Color(204, 255, 204));
        addButton.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        addButton.setText("Добавить заметку");
        addButton.setBorder(null);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        mainPanel.add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 180, 30));

        editButton.setBackground(new java.awt.Color(204, 255, 204));
        editButton.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        editButton.setText("Изменить заметку");
        editButton.setBorder(null);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        mainPanel.add(editButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 180, 30));

        deleteButton.setBackground(new java.awt.Color(204, 255, 204));
        deleteButton.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        deleteButton.setText("Удалить заметку");
        deleteButton.setBorder(null);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        mainPanel.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 180, 30));

        getContentPane().add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
               
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D://superfiles//memmoth//client//" + user.login + ".txt"));
            oos.writeObject(userNotes);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Запрос на сохранение данных пользователя
        client.Post("save");
        //Отправка файла
        client.SendFile(user.login);
        
        client.close();
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        Note newNote = new Note();
        AddEditNoteDialog addDialog = new AddEditNoteDialog(newNote, "Создание заметки", this);
        addDialog.setVisible(true);
        
    }//GEN-LAST:event_addButtonActionPerformed

    //Удаление заметки, созданной ранее
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (noteList.isSelectionEmpty()) {
            Error noSelection = new Error("Не выбрана заметка", "Необходимо выбрать ранее созданную заметку для удаления");
            noSelection.setVisible(true);
            return;
        } else {
            Note bufNote = noteList.getSelectedValue();
            
            model.removeAllElements();
            
            userNotes.remove(bufNote);
            
            noteList.setModel(model);
            noteList.setBackground(new Color(229, 227, 255));

            for (int i = 0; i < userNotes.size(); i++)
            {
                model.addElement(userNotes.get(i));
            }
        
            splitPane.setLeftComponent(new javax.swing.JScrollPane(noteList));
        }        
    }//GEN-LAST:event_deleteButtonActionPerformed

    //Изменение созданное ранее заметки
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        //Проверка на выбор заметки
        if (noteList.isSelectionEmpty()) {
            Error noSelection = new Error("Не выбрана заметка", "Необходимо выбрать ранее созданную заметку для редактирования");
            noSelection.setVisible(true);
            return;
        } else {
            AddEditNoteDialog editDialog = new AddEditNoteDialog(noteList.getSelectedValue(), "Редактирование заметки", this);
            editDialog.setVisible(true);
        }
    }//GEN-LAST:event_editButtonActionPerformed

    
    private JList <Note> noteList = new JList<>();
    public DefaultListModel<Note> model = new DefaultListModel<>();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel noteAreaPanel;
    private javax.swing.JTextArea noteBody;
    private javax.swing.JScrollPane noteBodyScroll;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JLabel usernameField;
    // End of variables declaration//GEN-END:variables
}
