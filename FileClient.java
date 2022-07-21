import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class FileClient {
	public static void main(String[] args) throws Exception{
		 
		 System.out.println("The Client Side");
		 while(true)
		 {
			 Socket socket = new Socket("localhost", 3996);
			 MakeRequest(socket);
		 }
	}

	public static void MakeRequest(Socket socket) throws Exception {
		System.out.println("Enter your request to transfer file (post) or download the file(get) ");
		 Scanner sc = new Scanner(System.in);
		 String reqType = sc.nextLine();
		 System.out.println(reqType);
		
		if(reqType.toLowerCase().equals("post") == true) 
		{
			try 
			{
				transferfile(socket);
			}
				
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
			
			else if (reqType.toLowerCase().equals("get") == true)
			{
				try {
						receivingfile(socket);
					}
				
				
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
				else 
				{
					System.out.println("no choice");
					
				}
	}
		public static void transferfile(Socket socket) 
		{
			Scanner sc =new Scanner(System.in);
			System.out.println("Enter the name of the file:");
			String name =sc.nextLine();
			System.out.println("Enter the content of the file:");
			String content= sc.nextLine();
			Request data = new Request("post",name,content);
			ObjectOutputStream outputStream ;
			try
			{
				 outputStream = new ObjectOutputStream(socket.getOutputStream());
				
				outputStream.writeObject(data);
				System.out.println("The file is sent");
			} catch(IOException e)
			{
				System.out.println("Error in sending the file");
				e.printStackTrace();
			}	
		}
		
		public static void receivingfile(Socket socket)	 throws Exception
		{
			
			Scanner sc =new Scanner(System.in);
			System.out.println("Enter the name of the file:");
			String name =sc.nextLine();
			//System.out.println("Enter the content of the file:");
			//String content= sc.nextLine();
			
			ObjectOutputStream outputstream =new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inputStream =new ObjectInputStream(socket.getInputStream());
			System.out.println("Enter the name of the file:");
			
			Request data =new Request("get",name," ");
			outputstream.writeObject(data);
			
			Request receiveData=(Request)inputStream.readObject();
			File filetobeDownloaded = new File("C:\\Users\\Ria Mishra\\clientserver\\"+receiveData.fileName);
			
			FileOutputStream fileOutputStream = new FileOutputStream(filetobeDownloaded);
			fileOutputStream.write((receiveData.fileContent).getBytes());
			
			
			
			
	
			
			
				
			
			
			
			
			
			
			
		}

		
		
		
		    
		    
		    
		    
		    
			
			
		
			
		}
	
		// TODO Auto-generated method stub