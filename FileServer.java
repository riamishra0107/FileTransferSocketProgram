import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class FileServer
{
	static int port =Constant.port;
	public static void main(String[] args) throws Exception
	{
		System.out.println("The Server Side");
		ServerSocket serversocket= new ServerSocket(port);
		
		while(true)
		{	
			requestRecieving(serversocket);
		}
	}

	public static void requestRecieving(ServerSocket serversocket) throws Exception
	{	
		
		Socket socket = serversocket.accept();
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inputStream =new ObjectInputStream(socket.getInputStream());
		Request data =(Request)inputStream.readObject();
		
		
		if((data.reqType).equals("post") == true)
		{
			recieveTheFile(data,inputStream);
			
		}
		else
		{
			sendTheFile(data,outputStream);
		}
	}

	public static void recieveTheFile(Request data,ObjectInputStream inputStream)throws Exception
	{
		String fileName=data.fileName;
		String fileContent =data.fileContent;
		
		System.out.println(fileName);
		
		File creatingnewFile= new File(Constant.serverPath+fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(creatingnewFile);
		
		fileOutputStream.write(fileContent.getBytes());	
	}
	
	public static void sendTheFile(Request data ,ObjectOutputStream outputstream) throws Exception
	{
		File file = new File(Constant.serverPath+data.fileName);
		if(file.exists() == true) 
		{
			byte[] fileInBytes = new byte[(int)(file.length())];
			FileInputStream fileInputStream= new FileInputStream(file);
			fileInputStream.read(fileInBytes);
			
			Request fileToSend = new Request("",data.fileName , new String(fileInBytes));
			outputstream.writeObject(fileToSend);	
		}
	
		else
		{
			System.out.println("File doesnot exist");
		}
	}
	
	
    
}