import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.HashMap;
public class FileInfo
{
	private Ext2File file;
	private int inodeNumber;
	private int length;
	private int nameLength
	private int fileType
	private String fileName;
	
	
	
	public FileInfo (Ext2File f, int o)
	{
		file = f;
	}

	public void printFileInfo()
	{
		
		
	}

}