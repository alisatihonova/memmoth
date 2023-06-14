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
            Error connectionError = new Error("������ ����������", "�� ������� ������������ � �������. \n���������, ��� ������ ��������, ����� ��������� ������������ �����");
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
    
    //����� ���������� �� ������ ������
    public void Post(String request) {
        try {
            writer.writeUTF(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //����� �������� ����� �������
    public String Get() {
        String response = null;
        
        try {
            response = reader.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return response;
    }
    
    //����� ��� ���������� ������ ������� � ��������
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
    
    //����� ��� �������� �� ������ �����
    public String SendFile(String login)
    {
        FileInputStream fileStream;                    //����� ��� �����
        byte[]          bytesFile = new byte[16*1024]; //������ �����, ������� �� ����������
        Long            sizeFile;                      //������ �����, ������� �� ����������
        String          needFile;                      //������, �������� �������� ������� ������� �����
        int             count;                         //���������� ��� �������� ��������

        try {
            needFile = "D://superfiles//memmoth//client//" + login + ".txt";
            
            System.out.println("Start Sending...");

            //����� ��������� �������� ��������
            File sendFile = new File(needFile);
            sizeFile      = sendFile.length();
            fileStream    = new FileInputStream(sendFile);

            //���� ���� ����������
            if (sendFile.exists()) {
               System.out.println("���� ����������");
               writer.writeBoolean(true);
               writer.writeLong(sizeFile);

                while ((count = fileStream.read(bytesFile)) != -1) {
                    writer.write(bytesFile, 0, count);
                    writer.flush();
                }
                System.out.println("File send");
                fileStream.close();
                //���� ������� ���������
                return "200";
            }
            //���� ����� �� ����������
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
    
    //����� ��� ��������� �� ������� �����, ������������� � �������
    public String GetFileMessage(String login)
    {
        FileOutputStream fileStream;                    //����� ��� �����
        Long             sizeFile;                      //������ �����, ������� �� ����������
        byte[]           bytesFile = new byte[16*1024]; //������ ��� �������� �����
        int              count;                         //���������� ��� �������� ��������
                
        try
        {
            writer.writeUTF("getfile");
            //����� ������� �� ������� �����
            if(reader.readBoolean() == true) //���� ���� � �� �����
            {
                //�������� �������� �����
                sizeFile = reader.readLong();
                fileStream = new FileOutputStream("D://superfiles//memmoth//client//" + login + ".txt");  //���� ��� ���������� �����
                
                while (sizeFile > 0 && (count = reader.read(bytesFile, 0, (int) Math.min(bytesFile.length, sizeFile))) != -1)
                {
                    fileStream.write(bytesFile, 0, count);
                    sizeFile -= count;
                }

                //��������� ��������� �����. �������� ��������� ������.
                fileStream.close();
                System.out.println("File was getted!");
                //���� ������� �������
                return "200";
            }
            else //����� ��� � �� �� �����
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
