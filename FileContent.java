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
		int i = 0;
		long fileSize = inode.getLength();
		long blocksRemaining =  fileSize/1024;
		long remainder = fileSize%1024;
		
		int indirectPointer = inode.getIndirectPointer();
		int doubleIndirectPointer = inode.getDoubleIndirectPointer();
		int tripleIndirectPointer = inode.getTripleIndirectPointer();
		
		//Read from the first 12 blocks
		for(i=0; i<12 && blocksRemaining>0; i++)
		{
			buffer = file.read(((inode.getBlockPointer(i)*1024)), 1024);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
			blocksRemaining--;
		}
		
		//Get any remainder from the first 12 blocks
		if((blocksRemaining == 0) && (i <12) && (remainder > 0))
		{
			buffer = file.read((inode.getBlockPointer(i)*1024), remainder);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
			return;
		}
		
		int[] indirectPointers = new int[256];
		for(int x=0; x <256; x++)
		{
			buffer = file.read((indirectPointer*1024)+(x*4), 4);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			indirectPointers[x] = byteBuff.getInt();
		}
		
		//Read from the 256 indirect blocks
		for(i=0; i<256 && blocksRemaining>0; i++)
		{
			buffer = file.read(((indirectPointers[i]*1024)), 1024);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
			blocksRemaining--;
		}
		
		//Get any remainder from the indirect blocks
		if(blocksRemaining == 0 && i <256 && remainder > 0)
		{
			buffer = file.read((indirectPointers[i]*1024), remainder);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
			return;
		}
		
		int[] doubleIndirectPointers = new int[65536];
		int pointer = 0;
		for(int x=0; x <256; x++)
		{
			buffer = file.read((doubleIndirectPointer*1024)+(x*4), 4);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			pointer = byteBuff.getInt();
			
			for(int y=0; y <256; y++)
			{
			buffer = file.read((pointer*1024)+(y*4), 4);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			doubleIndirectPointers[y+(x*256)] = byteBuff.getInt();
			}
		}
		
		//Read from the 65536 double-indirect blocks
		for(i=0; i<65536 && blocksRemaining>0; i++)
		{
			buffer = file.read(((doubleIndirectPointers[i]*1024)), 1024);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
			blocksRemaining--;
		}
		
		//Get any remainder from the indirect blocks
		if(blocksRemaining == 0 && i <65536 && remainder > 0)
		{
			buffer = file.read((doubleIndirectPointers[i]*1024), remainder);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			fileText = new String(byteBuff.array());
			this.printFile();
			return;
		}
		
		

		
	}
	
	public void printFile()
	{
		System.out.print(fileText.trim());
	}

}