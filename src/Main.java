public class Main {
    
    public static void main(String[] args) {  
        
        //�������� ������� �������
        Client client = new Client();
           
        System.out.println("Connected to server");
        
        //�������� ����� �����������
        Authorization authDialog = new Authorization(client);
        authDialog.setVisible(true);
    }
}
