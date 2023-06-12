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
                   //Получение запроса
                   while (true) {
                       String request = reader.readLine();

                        //Определение запроса
                        String[] split;
                        //Разделение запроса на части для определения метода
                        split = request.split("/");
                        System.out.println("request: " + request);

                        //Обработка запроса на авторизацию
                        switch (split[0]) {
                            //Обработка запроса на авторизацию
                            case ("authorize") -> {                                
                                //Сравнение хэшей
                                System.out.println("определили авторизацию");
                                String authResult = CompareHash(split[1], split[2]);
                                System.out.println("сравнили хэши");
                                //Отправка ответа клиенту
                                writer.write(authResult);
                                writer.newLine();
                                writer.flush();
                            }
                            //Обработка запроса на регистрацию
                            case ("register") -> {
                                //Запись данных пользователя в файл
                                String regResult = UserSerialize(split[1], split[2], split[3]);
                                //Отправка ответа клиенту
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
    
    //Сохраняет данные пользователя в xml-файл в директории D://superfiles//memmoth//users
    //Возвращает 200 - файл успешно создан; 404 - файл уже существует; 500 - ошибка
    public static String UserSerialize(String pLogin, String pUsername, String pHash) {
        File file = new File("D://superfiles//memmoth//users//" + pLogin + ".xml");
               
        try {
            System.out.println("file.exists() = " + file.exists());
            if (!file.exists() && file.createNewFile()) {
                try {
                    XMLOutputFactory output = XMLOutputFactory.newInstance();
                    Writer oswriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                    XMLStreamWriter writer = output.createXMLStreamWriter(oswriter); 
                    writer.writeStartDocument("UTF-8", "1.0"); // Открываем XML-документ и Пишем корневой элемент ServerUsers
                    writer.writeStartElement("ServerUsers"); 

                    writer.writeStartElement("User");  // Записываем User 
                    // Заполняем все тэги                        
                    writer.writeStartElement("Login");
                    writer.writeCharacters(pLogin);
                    writer.writeEndElement();                
                    writer.writeStartElement("Username");
                    writer.writeCharacters(pUsername);
                    writer.writeEndElement();                
                    writer.writeStartElement("Hash");
                    writer.writeCharacters(pHash);
                    writer.writeEndElement();
                    writer.writeEndElement(); // Закрываем тэг User

                    writer.writeEndElement();     // Закрываем корневой элемент            
                    writer.writeEndDocument();    // Закрываем XML-документ
                    writer.flush();
                } catch (IOException | XMLStreamException e) {
                    //Ошибка при записи
                    e.printStackTrace();   
                    return "500";
                }  
                return "200";
            } else {
                //Пользователь с таким логином уже существует
                return "404";
            }
        } catch (IOException e) {
            //Ошибка при создании
            e.printStackTrace();
            return "500";
        }
    }
    
    //Извлекает хэш пароля пользователя из файла с его записью
    public static String GetHash(Document document) throws DOMException, XPathExpressionException {
        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = xpath.compile("ServerUsers/User/Hash/text()");
                
        Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
        return node.getTextContent();
    }
    
    //Извлекает имя пользователя из файла с его записью
    public static String GetUsername(Document document) throws DOMException, XPathExpressionException {
        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = xpath.compile("ServerUsers/User/Username/text()");
                
        Node node = (Node) expr.evaluate(document, XPathConstants.NODE);
        return node.getTextContent();
    }
    
    //Сравнивает хэш пароля, введённого пользователем при авторизации, с хэшем, хранящемся для него на сервере
    //Возвращает: 200/username - пароль верный, 404 - неверный логин/пароль, 500 - ошибка
    public static String CompareHash(String pLogin, String pHash) {
        File file = new File("D://superfiles//memmoth//users//" + pLogin + ".xml");
        
        if (file.exists()) {
            try {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                String fileHash = GetHash(document);

                //Хэши совпали, авторизация прошла успешно
                if (fileHash.compareTo(pHash) == 0) {
                    return "200/" + GetUsername(document);
                }
                else {
                    //Хэши не совпали, авторизация не удалась
                    return "404";
                }
            } catch (XPathException | ParserConfigurationException | SAXException | IOException e) {
               e.printStackTrace(System.out);
               return "500";
            }
        } else {
            //Пользователь не найден
            return "404";
        }
    }
}
