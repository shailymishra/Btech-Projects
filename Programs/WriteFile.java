import java.io.*;

// Class to write text in a file
public class WriteFile
{
	
//////////////////////////////////////////////////////////////////////////
//////////////// data fields //////////////////////
	private String path;      
	private boolean append = false;
	
	
///////////////////////////////////////////////////////////////////////////////	
////////////  Constructor  /////////////////////////////////
	public WriteFile (String Path)
	{
		path = Path;
	}
	
	public WriteFile (String file_path , boolean append_value)
	{
		path = file_path;
		append = append_value;
	}
	
///////////////////////////////////////////////////////////////////////////////
///////////// Method to write text in file////////////////
	public void WriteToFile (String text) throws IOException
	{
		FileWriter write = new FileWriter (path, append);
		PrintWriter print_line = new PrintWriter (write);
		print_line.printf("%s"+  "%n"  , text);
		print_line.close();
	}
}