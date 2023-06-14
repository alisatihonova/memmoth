import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {
    
    private Socket socket;
    private DataOutputStream writer;
    private DataInputStream reader;
   
    public Client() {
        try {
            socket = new Socket ("127.0.0.1", 8000);
            
            writer = new DataOutputStream(socket.getOutputStream());
            
            reader = new DataInputStream(socket.getInputStream());
            
        } catch (IOException e) {
            Error connectionError = new Error("Сервер недоступен", "Не удалось подключиться к серверу. \nУбедитесь, что сервер работает, затем попробуте подключиться снова");
            connectionError.setVisible(true);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.exit(-1);
            e.printStackTrace();
        }
        
    }
    
    //Метод отправляет на сервер запрос
    public void Post(String request) {
        try {
            writer.writeUTF(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Метод получает ответ сервера
    public String Get() {
        String response = null;
        
        try {
            response = reader.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return response;
    }
    
    //Метод для завершения работы клиента с сервером
    public void close () {
        try {
            socket.close();
            writer.close();
            reader.close();
            System.out.println("Client stopped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Метод для отправки на сервер файла
    public String SendFile(String login)
    {
        FileInputStream fileStream;                    //Поток для файла
        byte[]          bytesFile = new byte[16*1024]; //Размер файла, который мы отправляем
        Long            sizeFile;                      //Размер файла, который мы отправляем
        String          needFile;                      //Строка, хранящее значение нужного клиенту файла
        int             count;                         //Переменная для контроля передачи

        try {
            needFile = "D://superfiles//memmoth//client//" + login + ".txt";
            
            System.out.println("Start Sending...");

            //Задаём начальные признаки отправки
            File sendFile = new File(needFile);
            sizeFile      = sendFile.length();
            fileStream    = new FileInputStream(sendFile);

            //Если файл существует
            if (sendFile.exists()) {
               System.out.println("Файл существует");
               writer.writeBoolean(true);
               writer.writeLong(sizeFile);

                while ((count = fileStream.read(bytesFile)) != -1) {
                    writer.write(bytesFile, 0, count);
                    writer.flush();
                }
                System.out.println("File send");
                fileStream.close();
                //Файл успешно отправлен
                return "200";
            }
            //Если файла не существует
            else
            {
                System.out.println("File doesn't exists");
                writer.writeBoolean(false);
                return "500";
            }
        }
        catch(IOException i)
        {
            System.out.println(i);
            return "500";
        }
    }
    
    //Метод для получения на клиенте файла, отправленного с сервера
    public String GetFileMessage(String login)
    {
        FileOutputStream fileStream;                    //Поток для файла
        Long             sizeFile;                      //Размер файла, который мы отправляем
        byte[]           bytesFile = new byte[16*1024]; //Буффер для хранения файла
        int              count;                         //Переменная для контроля передачи
                
        try
        {
            writer.writeUTF("getfile");
            //Ответ сервера на наличие файла
            if(reader.readBoolean() == true) //Файл есть и он придёт
            {
                //Ожидание отправки файла
                sizeFile = reader.readLong();
                fileStream = new FileOutputStream("D://superfiles//memmoth//client//" + login + ".txt");  //Путь для сохранения файла
                
                while (sizeFile > 0 && (count = reader.read(bytesFile, 0, (int) Math.min(bytesFile.length, sizeFile))) != -1)
                {
                    fileStream.write(bytesFile, 0, count);
                    sizeFile -= count;
                }

                //Окончание получения файла. Закрытие файлового потока.
                fileStream.close();
                System.out.println("File was getted!");
                //Файл успещно получен
                return "200";
            }
            else //Файла нет и он не придёт
            {
                return "500";
            }
        }
        catch(IOException i)
        {
            System.out.println(i);
            return "500";
        }
    }
}
