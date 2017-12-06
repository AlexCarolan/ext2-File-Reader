import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class FileContent
{
	private Ext2File file;
	private String fileText;
	private byte[] buffer;
	
	public FileContent(Ext2File f, Inode inode)
	{
		file = f;
		ByteBuffer byteBuff;
		int totalPos = 0;
		int i = 0;
		long fileSize = inode.getLength();
		long blocksRemaining =  fileSize/1024;
		long remainder = fileSize%1024;
		
		//Read from the first 12 blocks
		for(i=0; i<12 && blocksRemaining>0; i++)
		{
			System.out.println("HERE!!!!!!!!!!!!");
			buffer = file.read((inode.getBlockPointer(i)*1024), 1024);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
			totalPos = totalPos + 1024;
			blocksRemaining--;
			i++;
		}

		//Get any remainder from the first 12 blocks
		if(blocksRemaining == 0 && i <12)
		{
			buffer = file.read((inode.getBlockPointer(i)*1024), remainder);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
			return;
		}
		
		
	}
	
	public void printFile()
	{
		System.out.print(fileText);
	}

}