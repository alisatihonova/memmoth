import java.nio.charset.StandardCharsets;
import java.security.*;

public class Authorization extends javax.swing.JDialog {

    private Client client;
    
    public Authorization(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public Authorization(Client pClient) {
        client = pClient;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        loginField = new javax.swing.JTextField();
        loginLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        authButton = new javax.swing.JButton();
        authLabel = new javax.swing.JLabel();
        regButton = new javax.swing.JButton();

        jLabel3.setText("Логин");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Авторизация");
        setBackground(new java.awt.Color(204, 204, 255));
        setLocation(new java.awt.Point(550, 200));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        loginField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        loginField.setMaximumSize(new java.awt.Dimension(100, 30));
        loginField.setMinimumSize(new java.awt.Dimension(100, 30));
        loginField.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel1.add(loginField, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 310, -1));

        loginLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        loginLabel.setText("Логин");
        jPanel1.add(loginLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        passwordLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        passwordLabel.setText("Пароль");
        jPanel1.add(passwordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        passwordField.setMaximumSize(new java.awt.Dimension(100, 30));
        passwordField.setMinimumSize(new java.awt.Dimension(100, 30));
        passwordField.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel1.add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 310, -1));

        authButton.setBackground(new java.awt.Color(204, 255, 204));
        authButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        authButton.setText("Вход");
        authButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        authButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authButtonActionPerformed(evt);
            }
        });
        jPanel1.add(authButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 140, 40));

        authLabel.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        authLabel.setText("memmoth");
        jPanel1.add(authLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        regButton.setBackground(new java.awt.Color(204, 255, 204));
        regButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        regButton.setText("Регистрация");
        regButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        regButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regButtonActionPerformed(evt);
            }
        });
        jPanel1.add(regButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 140, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Обработчик нажатия на кнопку авторизации
    private void authButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authButtonActionPerformed
        try {
            //Формироваие и отправка запроса
            client.Post("authorize/" + loginField.getText() + "/" + getHashedPassword());
            String serverResponse = client.Get();
        
            //Обработка ответа от сервера
            String[] split = serverResponse.split("/");

            switch (split[0]) {
                //Сервер обработал запрос, логин существует, пароль совпал
                case ("200") -> {
                    //Отображение основного окна пользователя
                    User authUser = new User(loginField.getText(), split[1]);
                    //Notes mainWindow = new Notes(client, authUser);
                    MainPage mainWindow = new MainPage(client, authUser);
                    mainWindow.setVisible(true);
                    this.dispose();
                }

                //Сервер обработал запрос, пользователь не найден
                case ("404") -> {
                    //Отображение окна ошибки, очистка текстовых полей
                    Error unauthorized = new Error("Не удалось авторизоваться", "Введен неверный логин или пароль");
                    unauthorized.setVisible(true);
                    loginField.setText("");
                    passwordField.setText("");
                }

                //Ошибка при обработке запроса
                case ("500") -> {
                    //Отображение окна ошибки, очистка текстовых полей
                    Error error = new Error("Ошибка", "Ошибка авторизации");
                    error.setVisible(true);
                    loginField.setText("");
                    passwordField.setText("");
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            //Отображение окна ошибки
            Error unauthorized = new Error("Не введен логин или пароль", "Проверьте корректность введенных данных");
            unauthorized.setVisible(true);
            loginField.setText("");
            passwordField.setText("");
        }        
    }//GEN-LAST:event_authButtonActionPerformed

    //Обработчик перехода к регистрации
    private void regButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regButtonActionPerformed
        //Переход к окну регистрации, сокрытие окна авторизации
        Registration regDialog = new Registration(client, this);
        regDialog.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_regButtonActionPerformed
    
    //Возвращает хэш пароля, введённого пользователем в поле "Пароль" или null, если произошли беды
    public String getHashedPassword() {
        String passw = String.copyValueOf(passwordField.getPassword());
        byte[] encodedhash = null;
        try {   
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            encodedhash = digest.digest(passw.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        if (encodedhash != null) {
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < encodedhash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if(hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
            }
            return hexString.toString();
        } else
            return null;     
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton authButton;
    private javax.swing.JLabel authLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField loginField;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton regButton;
    // End of variables declaration//GEN-END:variables
}
