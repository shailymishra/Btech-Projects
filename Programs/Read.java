import java.io.*;

// Class : Read - to read words from a file
public class Read
{
	
//////////////////////////////////////////////////////////////////////////
////////////////  data fields //////////////////////
	private String path;
	
//////////////////////////////////////////////////////////////////////////
////////////  Constructor  /////////////////////////////////
	public Read (String Path)
	{
		path = Path;
	}
	
/////////////////////////////////////////////////////////////////////////
////// Method - to count number of lines in a file//////////////
	int Lines() throws IOException
	{
		FileReader fr = new FileReader (path);
		BufferedReader bf = new BufferedReader(fr);
		
		String Line;
		int Lines=0;		
		while ((Line = bf.readLine() ) != null ) // counts until the end of file
			Lines++;
		bf.close();
		
		return Lines;
	}
	
/////////////////////////////////////////////////////////////////////////////
/////////// Method - to read from a file and return the data////////////	
	public String[] OpenFile() throws IOException
	{
		FileReader f = new FileReader (path);
		BufferedReader b = new BufferedReader(f);
		
		int Lines = Lines();
		String[] Data = new String[Lines];
		int i;
		for( i=0; i<Lines; i++ )
		{
			Data[i] = b.readLine(); // Reads line from file and stores in an array
		}
		
		b.close(); 
		return Data;
	}
	
}