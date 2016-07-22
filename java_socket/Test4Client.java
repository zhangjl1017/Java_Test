import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
 
public class Test4Client {
    public static Object obj = new Object();
 
    // 客服端的主入口
    public static void main(String[] args) throws IOException {
        socketClient();
    }
 
    // 与服务器连通地址本机（127.0.0.1），局域网中其他机器是（服务器在局域网中的ip地址） 端口都是8888
    public static void socketClient() throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        if (socket.isConnected()) {
            // 如果连接成功了就开启写和读的进程
            new writer(socket).start();
            new read(socket).start();
        } else {
            System.out.println("服务器未开启");
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
 
// 从通道中读取的线程
class read extends Thread {
    private Socket socket;
    private BufferedReader bufferedReader;
    private String str = null;
 
    public read(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket
                .getInputStream()));
    }
 
    @Override
    public void run() {
        while (true) {
 
                try {
                    str = bufferedReader.readLine();
                    System.out.println(str);
                } catch (IOException e) {
                }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
             
                e.printStackTrace();
            }
        }
    }
}