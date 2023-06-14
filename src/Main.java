public class Main {
    
    public static void main(String[] args) {  
        
        //Создание объекта клиента
        Client client = new Client();
           
        System.out.println("Connected to server");
        
        //Создание формы авторизации
        Authorization authDialog = new Authorization(client);
        authDialog.setVisible(true);
    }
}
