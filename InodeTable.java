import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
* This class creates an array of inodes when provided with an Ext2 file and data from the superblock.
*/
public class InodeTable
{
	private final Ext2File file;
	private Inode[] inodes;
	
	/**
	* Creates a new InodeTable 
	* 
	* @param f The Ext2 Filesystem.
	* @param superBlock The superblock for the filesystem
	*/
	public InodeTable(Ext2File f, SuperBlock superBlock)
	{
		file = f;
		inodes = new Inode[superBlock.getNumberOfInodes()];
		
		//Obtain the relevent data using the superblock
		int wholeBlocks = superBlock.getNumberOfInodes()/superBlock.getInodesPerGroup();
		int remainder = superBlock.getNumberOfInodes()%superBlock.getInodesPerGroup();
		int inodesPerGroup = superBlock.getInodesPerGroup();
		int inodeLength = superBlock.getInodesize();
		
		int j;
		int indodeBlock;
		byte buffer[];
		int offset;

		//Populate the array of inodes using full groups
		for(j=0; j<wholeBlocks; j++)
		{
			buffer = file.read((2048)+(32*(j))+8, 4);
			ByteBuffer inodeTablePointer = ByteBuffer.wrap(buffer);
			inodeTablePointer.order(ByteOrder.LITTLE_ENDIAN);
			offset = (inodeTablePointer.getInt()*1024);
			
			for(int i = inodesPerGroup*j; i < inodesPerGroup*(j+1);  i++)
			{
				inodes[i] = new Inode(file, offset);
				offset = offset + inodeLength;
			}
		}
		
		//add the remaining inodes from the last group
		if(remainder>0)
		{
			buffer = file.read((2048)+(32*(j))+8, 4);
			ByteBuffer inodeTablePointer = ByteBuffer.wrap(buffer);
			inodeTablePointer.order(ByteOrder.LITTLE_ENDIAN);
			offset = inodeTablePointer.getInt();
			
			for(int i = 0; i < remainder;  i++)
			{
				inodes[i] = new Inode(file, offset);
				offset = offset + inodeLength;
			}
		}
		
		
	}
	
	/**
	* Provides the array of inodes from the inode table.
	* @return the inode array.
	*/
	public Inode[] getInodeTable()
	{
		return inodes;
	}
	
	/**
	* Prints out the inode data of the inode at the given position.
	* 
	* @param inodeNum The number of the inode to be printed.
	*/
	public void printInode(int inodeNum)
	{
		inodes[inodeNum-1].printInode();
	}
	
	/**
	* Provides the file size of the file related to the provided inode number.
	* 
	* @param inodeNum The number of the inode.
	*/
	public long getInodeLength(int inodeNum)
	{
		return inodes[inodeNum-1].getLength();
	}
	
	/**
	* Provides a block pointer from the inodes 12 direct pointers.
	* Valid block numbers are in the range of (0 - 11).
	* 
	* @param inodeNum The number of the inode.
	* @param blockNum The number of the direct block.
	*/
	public int getInodeBlockPointer(int inodeNum, int blockNum)
	{
		return inodes[inodeNum-1].getBlockPointer(blockNum);
	}
	
}
