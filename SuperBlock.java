import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
* This class contains the SuperBlock data
* The superblock contains information relating to blocks and inodes.
*/
public class SuperBlock
{
	private final int inodes;
	private final int blocks;
	private final String name;
	private final int blocksPerGroup;
	private final int inodesPerGroup;
	private final int inodeSize;
	
	public SuperBlock(Ext2File file, int offset)
	{
		ByteBuffer byteBuff;
		
		//Seperate and store each value byte by byte
		byte buffer[] = file.read(offset, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		inodes = byteBuff.getInt();
		
		buffer = file.read(offset+4, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		blocks = byteBuff.getInt();
		
		buffer = file.read(offset+120, 16);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		name = new String(byteBuff.array());
		
		buffer = file.read(offset+32, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		blocksPerGroup = byteBuff.getInt();
		
		buffer = file.read(offset+40, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		inodesPerGroup = byteBuff.getInt();
		
		buffer = file.read(offset+88, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		inodeSize = byteBuff.getInt();
		
		this.printSuperBlock();
	}
	
	/**
	* Provides the size of each inode.
	* 
	* @return The size in bytes of each inode.
	*/
	public void printSuperBlock()
	{	
		System.out.println("------------------------------------- SuperBlock: " + name + " --------------------------------------");
		System.out.println("Total Number of Inodes: " + inodes);
		System.out.println("Total Number of Inodes per Group: " + inodesPerGroup);
		System.out.println("Total Number of Blocks: " + blocks);
		System.out.println("Total Number of Blocks per Group: " + blocksPerGroup);
		System.out.println("Inode Size: " + inodeSize + " Bytes");	
	}
	
	/**
	* Provides the size of each inode.
	* 
	* @return The size in bytes of each inode.
	*/
	public int getInodesize()
	{
		return (inodeSize);
	}
	
	/**
	* provides the total number of inodes.
	* 
	* @return inodes The total number of inodes in the filesystem.
	*/
	public int getNumberOfInodes()
	{
		return (inodes);
	}
	
	/**
	* provides the total number of inodes per group.
	* 
	* @return inodesPerGroup The maximum number of inodes per group.
	*/
	public int getInodesPerGroup()
	{
		return (inodesPerGroup);
	}
	
	/**
	* provides the total number of blocks per group.
	* 
	* @return blocksPerGroup The number of blocks in each block group.
	*/
	public int getBlocksPerGroup()
	{
		return (blocksPerGroup);
	}
	
	
}
