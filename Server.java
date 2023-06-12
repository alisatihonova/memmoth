import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class Server {
    public static void main (String[] args) {  
       try (ServerSocket server = new ServerSocket(8000)) {
           System.out.println("Server Started");
           
           while (true) {
               try (Socket socket = server.accept();
                        BufferedWriter writer = 
                            new BufferedWriter(
                                new OutputStreamWriter(
                                    socket.getOutputStream()));
                        BufferedReader reader =
                            new BufferedReader (
                                new InputStreamReader(
                                    socket.getInputStream()));) {
                   //��������� �������
                   while (true) {
                       String request = reader.readLine();

                        //����������� �������
                        String[] split;
                        //���������� ������� �� ����� ��� ����������� ������
                        split = request.split("/");
                        System.out.println("request: " + request);

                        //��������� ������� �� �����������
                        switch (split[0]) {
                            //��������� ������� �� �����������
                            case ("authorize") -> {                                
                                //��������� �����
                                System.out.println("���������� �����������");
                                String authResult = CompareHash(split[1], split[2]);
                                System.out.println("�������� ����");
                                //�������� ������ �������
                                writer.write(authResult);
                                writer.newLine();
                                writer.flush();
                            }
                            //��������� ������� �� �����������
                            case ("register") -> {
                                //������ ������ ������������ � ����
                                String regResult = UserSerialize(split[1], split[2], split[3]);
                                //�������� ������ �������
                                writer.write(regResult);
                                writer.newLine();
                                writer.flush();
                            }
                        //}
                        }
                   }
               }
           }
                      
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
    
    //��������� ������ ������������ � xml-���� � ���������� D://superfiles//memmoth//users
    //���������� 200 - ���� ������� ������; 404 - ���� ��� ����������; 500 - ������
    public static String UserSerialize(String pLogin, String pUsername, String pHash) {
        File file = new File("D://superfiles//memmoth//users//" + pLogin + ".xml");
               
        try {
            System.out.println("file.exists() = " + file.exists());
            if (!file.exists() && file.createNewFile()) {
                try {
                    XMLOutputFactory output = XMLOutputFactory.newInstance();
                    Writer oswriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                    XMLStreamWriter writer = output.createXMLStreamWriter(oswriter); 
                    writer.writeStartDocument("UTF-8", "1.0"); // ��������� XML-�������� � ����� �������� ������� ServerUsers
                    writer.writeStartElement("ServerUsers"); 

                    writer.writeStartElement("User");  // ���������� User 
                    // ��������� ��� ����                        
                    writer.writeStartElement("Login");
                    writer.writeCharacters(pLogin);
                    writer.writeEndElement();                
                    writer.writeStartElement("Username");
                    writer.writeCharacters(pUsername);
                    writer.writeEndElement();                
                    writer.writeStartElement("Hash");
                    writer.writeCharacters(pHash);
                    writer.writeEndElement();
                    writer.writeEndElement(); // ��������� ��� User

                    writer.writeEndElement();     // ��������� �������� �������            
                    writer.writeEndDocument();    // ��������� XML-��������
                    writer.flush();
                } catch (IOException | XMLStreamException e) {
                    //������ ��� ������
                    e.printStackTrace();   
                    return "500";
                }  
                return "200";
            } else {
                //������������ � ����� ������� ��� ����������
                return "404";
            }
        } catch (IOException e) {
            //������ ��� ��������
            e.printStackTrace();
            return "500";
        }
    }
    
    //��������� ��� ������ ������������ �� ����� � ��� �������
    public static String GetHash(Document document) throws DOMException, XPathExpressionException {
        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = xpath.compile("ServerUsers/User/Hash/text()");
                
        Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
        return node.getTextContent();
    }
    
    //��������� ��� ������������ �� ����� � ��� �������
    public static String GetUsername(Document document) throws DOMException, XPathExpressionException {
        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = xpath.compile("ServerUsers/User/Username/text()");
                
        Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
        return node.getTextContent();
    }
    
    //���������� ��� ������, ��������� ������������� ��� �����������, � �����, ���������� ��� ���� �� �������
    //����������: 200/username - ������ ������, 404 - �������� �����/������, 500 - ������
    public static String CompareHash(String pLogin, String pHash) {
        File file = new File("D://superfiles//memmoth//users//" + pLogin + ".xml");
        
        if (file.exists()) {
            try {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                String fileHash = GetHash(document);

                //���� �������, ����������� ������ �������
                if (fileHash.compareTo(pHash) == 0) {
                    return "200/" + GetUsername(document);
                }
                else {
                    //���� �� �������, ����������� �� �������
                    return "404";
                }
            } catch (XPathException | ParserConfigurationException | SAXException | IOException e) {
               e.printStackTrace(System.out);
               return "500";
            }
        } else {
            //������������ �� ������
            return "404";
        }
    }
}
