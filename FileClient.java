import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class FileClient {
	public static void main(String[] args) throws Exception {

		int port = Constant.port;
		String ip = Constant.ip;
		System.out.println("The Client Side");

		while (true) {

			Socket socket = new Socket(ip, port);
			makeRequest(socket);

		}
	}

	public static void makeRequest(Socket socket) throws Exception {
		System.out.println("Enter the choice = 1 (for sending the file) or choice = 2 (for downloading the file)");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
        try {
		if (choice == 1) {
			
				transferFile(socket);
			
		}

		else if (choice == 2) {
			
				receivingFile(socket);
			
			}
		 else {
			System.out.println("no choice");
			}
		}
				catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static void transferFile(Socket socket) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the file:");
		String name = sc.nextLine();
		System.out.println("Enter the content of the file:");
		String content = sc.nextLine();
		Request data = new Request("post", name, content);
		ObjectOutputStream outputStream;
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());

			outputStream.writeObject(data);
			System.out.println("The file is sent");
		} catch (IOException e) {
			System.out.println("Error in sending the file");
			e.printStackTrace();
		}
	}

	public static void receivingFile(Socket socket) throws Exception {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the name of the file:");
		String name = sc.nextLine();

		ObjectOutputStream outputstream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
		System.out.println("Enter the name of the file:");

		Request data = new Request("get", name, " ");
		outputstream.writeObject(data);

		Request receiveData = (Request) inputStream.readObject();
		File filetobeDownloaded = new File(Constant.clientPath + receiveData.fileName);

		FileOutputStream fileOutputStream = new FileOutputStream(filetobeDownloaded);
		fileOutputStream.write((receiveData.fileContent).getBytes());

	}

}

// TODO Auto-generated method stub