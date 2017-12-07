import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.lang.StringBuilder;

/**
* This class holds the text content of a file in the fileystem
*/
public class FileContent
{
	private String fileText = "";
	
	/**
	* Creates a new Inode from the specified position (offset).
	* Inode data is broken down byte by byte and seperated into individual values.
	* 
	* @param file The Ext2 Filesystem.
	* @param inode The inode belonging to the file.
	*/
	public FileContent(Ext2File file, Inode inode)
	{
		ByteBuffer byteBuff;
		int i = 0;
		long fileSize = inode.getLength();
		long blocksRemaining =  fileSize/1024;
		long remainder = fileSize%1024;
		byte[] buffer;
		
		int indirectPointer = inode.getIndirectPointer();
		int doubleIndirectPointer = inode.getDoubleIndirectPointer();
		int tripleIndirectPointer = inode.getTripleIndirectPointer();
		
		//Read from the first 12 blocks
		for(i=0; i<12 && blocksRemaining>0; i++)
		{
			buffer = file.read(((inode.getBlockPointer(i)*1024)), 1024);
			fileText = fileText + (new String(buffer)).trim();
			blocksRemaining--;
		}
		
		//Get any remainder from the first 12 blocks
		if((blocksRemaining == 0) && (i <12) && (remainder > 0))
		{
			buffer = file.read((inode.getBlockPointer(i)*1024), remainder);
			fileText = fileText + (new String(buffer)).trim();
			this.printFile();
			return;
		}
		
		//Acess the indirect pointers
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
			fileText = fileText + (new String(buffer)).trim();
			blocksRemaining--;
		}
		
		//Get any remainder from the indirect blocks
		if(blocksRemaining == 0 && i <256 && remainder > 0)
		{
			buffer = file.read((indirectPointers[i]*1024), remainder);
			fileText = fileText + (new String(buffer)).trim();
			this.printFile();
			return;
		}
		
		//Acess the double-indirect pointers
		int[] doubleIndirectPointers = new int[65536];
		int pointer;
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
			fileText = fileText + (new String(buffer)).trim();
			blocksRemaining--;
		}
		
		//Get any remainder from the double-indirect blocks
		if(blocksRemaining == 0 && i <65536 && remainder > 0)
		{
			buffer = file.read((doubleIndirectPointers[i]*1024), remainder);
			fileText = fileText + (new String(buffer)).trim();
			this.printFile();
			return;
		}
		
		//Acess the triple-indirect pointers
		int[] tripleIndirectPointers = new int[16777216];
		int secondPointer;
		for(int x=0; x <256; x++)
		{
			buffer = file.read((tripleIndirectPointer*1024)+(x*4), 4);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			pointer = byteBuff.getInt();
			
			for(int y=0; y <256; y++)
			{
			buffer = file.read((pointer*1024)+(y*4), 4);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			secondPointer = byteBuff.getInt();
				for(int z=0; z <256; z++)
				{
				buffer = file.read((secondPointer*1024)+(z*4), 4);
				byteBuff = ByteBuffer.wrap(buffer);
				byteBuff.order(ByteOrder.LITTLE_ENDIAN);
				tripleIndirectPointers[z+(y*256)+(x*65536)] = byteBuff.getInt();
				}
			}
		}
		
		//Read from the 16777216 triple-indirect blocks
		for(i=0; i<16777216 && blocksRemaining>0; i++)
		{
			buffer = file.read(((tripleIndirectPointers[i]*1024)), 1024);
			fileText = fileText + (new String(buffer)).trim();
			blocksRemaining--;
		}
	
		//Get any remainder from the triple-indirect blocks
		if(blocksRemaining == 0 && i <16777216 && remainder > 0)
		{
			buffer = file.read((tripleIndirectPointers[i]*1024), remainder);
			fileText = fileText + (new String(buffer)).trim();
			this.printFile();
			return;
		}
		
		this.printFile();
		
	}
	
	/**
	* Prints out the content of the file.
	*/
	public void printFile()
	{
		System.out.print(fileText);
	}

}