import java.io.RandomAccessFile;
public class Volume
{
	
	private RandomAccessFile file;
	
	public Volume(String fileName)
	{
		try
		{
			file = new RandomAccessFile(fileName, "r");
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public RandomAccessFile getFile()
	{
		return(file);
	}
	
	
	
}

