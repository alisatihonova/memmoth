import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Error extends javax.swing.JDialog {

    public Error(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    //Конструкртор окна ошибки с заголовком и текстом, установленными при создании
    public Error (String pTitle, String pBody) {
        initComponents();
        StyledDocument doc = warningBody.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        warningTitle.setText(pTitle);
        warningBody.setText(pBody);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        warningTitle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        warningBody = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ошибка!");
        setLocation(new java.awt.Point(600, 225));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 20, 147), 3));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/warning.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 60, 60));

        warningTitle.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        warningTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(warningTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 360, 40));

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        warningBody.setEditable(false);
        warningBody.setBackground(new java.awt.Color(204, 204, 255));
        warningBody.setBorder(null);
        warningBody.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jScrollPane2.setViewportView(warningBody);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 300, 120));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane warningBody;
    private javax.swing.JLabel warningTitle;
    // End of variables declaration//GEN-END:variables
}
