import java.io.RandomAccessFile;

/**
* This class holds the raw file data from the provided file
*/
public class Volume
{
	private RandomAccessFile file;
	
	/**
	* Creates a new insance of Volume using a filepath to locate the file
	* 
	* @param fileName The filepath to the file to be added.
	*/
	public Volume(String fileName)
	{
		try
		{
			file = new RandomAccessFile(fileName, "r");
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	* Provides access to the volume file.
	* 
	* @return file The volume file this instance contains.
	*/
	public RandomAccessFile getFile()
	{
		return(file);
	}
	
	
	
}

