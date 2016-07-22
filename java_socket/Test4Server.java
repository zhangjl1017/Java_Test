import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
 
/*
 * 4.用socket通讯写出多个客户端和一个服务器端的通讯，
 * 要求客户发送数据后能够回显相同的数据（回显功能）（实用TCP方式）。
 */
public class Test4Server {
 
     
     
    // 主入口
    public static void main(String[] args) throws IOException {
        scoketServer();
    }
 
    // 开启的tcp8888监听端口
    public static void scoketServer() throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            // 未连通前线程阻塞，连通后开启一个socket通道线程后继续监听8888端口
            Socket socket = server.accept();
            System.out.println(socket.getInetAddress().getHostAddress()
                    + "连接进入");
            new SocketThread(socket).start();
			new writer(socket).start();
        }
    }
     
     
 
}

// 写入到通道的线程
class writer extends Thread {
    @SuppressWarnings("unused")
    private Socket socket;
    private PrintWriter printWriter;
    private Scanner scanner = new Scanner(System.in);
    private String str = null;
 
    public writer(Socket socket) throws IOException {
        this.socket = socket;
        this.printWriter = new PrintWriter(socket.getOutputStream());
 
    }
 
    @Override
    public void run() {
        scanner.useDelimiter("\r\n");
 
        while (true) {
                System.out.print("请输入信息：");
                // 产生扫描器的线程阻塞
                str = scanner.next();
                System.out.println("我说："+str);
                printWriter.write(str + "\r\n");
                printWriter.flush();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
 
// 一个服务器端口中监听多个客服端通道线程
class SocketThread extends Thread {
//  所有通道写入流的集合
    private static List<PrintWriter> list =new ArrayList<PrintWriter>();
     
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
 
    public SocketThread(Socket socket) throws IOException {
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket
                .getInputStream()));
        this.printWriter = new PrintWriter(socket.getOutputStream());
        list.add(printWriter);
    }
 
    @Override
    public void run() {
        String string = null;
        while (true) {
            try {
                // 服务器在通道中读到的信息回显给客服端
                string = bufferedReader.readLine();
                System.out.println("客服端信息：" + string);
                for(PrintWriter printWriter:list ){
                    printWriter.write("服务器回显：" + string + "\r\n");
                    printWriter.flush();
                }
            } catch (IOException e) {
 
            }
        }
 
    }
}