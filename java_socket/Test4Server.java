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
 * 4.��socketͨѶд������ͻ��˺�һ���������˵�ͨѶ��
 * Ҫ��ͻ��������ݺ��ܹ�������ͬ�����ݣ����Թ��ܣ���ʵ��TCP��ʽ����
 */
public class Test4Server {
 
     
     
    // �����
    public static void main(String[] args) throws IOException {
        scoketServer();
    }
 
    // ������tcp8888�����˿�
    public static void scoketServer() throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            // δ��ͨǰ�߳���������ͨ����һ��socketͨ���̺߳��������8888�˿�
            Socket socket = server.accept();
            System.out.println(socket.getInetAddress().getHostAddress()
                    + "���ӽ���");
            new SocketThread(socket).start();
			new writer(socket).start();
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
 
// һ���������˿��м�������ͷ���ͨ���߳�
class SocketThread extends Thread {
//  ����ͨ��д�����ļ���
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
                // ��������ͨ���ж�������Ϣ���Ը��ͷ���
                string = bufferedReader.readLine();
                System.out.println("�ͷ�����Ϣ��" + string);
                for(PrintWriter printWriter:list ){
                    printWriter.write("���������ԣ�" + string + "\r\n");
                    printWriter.flush();
                }
            } catch (IOException e) {
 
            }
        }
 
    }
}