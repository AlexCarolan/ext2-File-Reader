import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class File
{
	private Ext2File file;
	private String fileText;
	private byte[] buffer;
	
	public File(Ext2File f, Inode inode)
	{
		file = f;
		ByteBuffer byteBuff;
		int count = 0;
		int i = 0;
		int length = (int)inode.getLength();
		
		while(length <= count && i < 12 && (length-(1024*(i+1)))>1024)
		{			
			buffer = file.read((inode.getBlockPointer(i)*1024), 1024);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
			count = count + 1024;
			i++;
		}
		
		if(length-(1024*(i+1)) < 1024)
		{
			buffer = file.read((inode.getBlockPointer(i)*1024), ((int)inode.getLength())-count);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
		}
		
	}
	
	public void printFile()
	{
		System.out.println(fileText);
	}

}