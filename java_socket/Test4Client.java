import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
 
public class Test4Client {
    public static Object obj = new Object();
 
    // �ͷ��˵������
    public static void main(String[] args) throws IOException {
        socketClient();
    }
 
    // ���������ͨ��ַ������127.0.0.1���������������������ǣ��������ھ������е�ip��ַ�� �˿ڶ���8888
    public static void socketClient() throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        if (socket.isConnected()) {
            // ������ӳɹ��˾Ϳ���д�Ͷ��Ľ���
            new writer(socket).start();
            new read(socket).start();
        } else {
            System.out.println("������δ����");
        }
    }
}
 
// д�뵽ͨ�����߳�
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
                System.out.print("��������Ϣ��");
                // ����ɨ�������߳�����
                str = scanner.next();
                System.out.println("��˵��"+str);
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
 
// ��ͨ���ж�ȡ���߳�
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