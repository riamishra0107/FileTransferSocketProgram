import java.io.Serializable;

public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String reqType;
	public String fileName;
	public String fileContent;

	Request(String reqType, String fileName, String fileContent)
	{
		this.reqType = reqType;
		this.fileName = fileName;
		this.fileContent = fileContent;

	}

	// TODO Auto-generated constructor stub
}
