import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
* This class contains the SuperBlock data
* The superblock contains information relating to blocks and inodes.
*/
public class SuperBlock
{
	private Ext2File file;
	private ByteBuffer byteBuff;
	private int offset;
	private int inodes;
	private int blocks;
	private String name;
	private int blocksPerGroup;
	private int inodesPerGroup;
	private int inodeSize;
	
	public SuperBlock(Ext2File f, int o)
	{
		file = f;
		offset = o;
		
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
		System.out.println("------------------ SuperBlock: " + name + " ------------------");
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
	* provides the total number of inodes per group.
	* 
	* @return The size in bytes of each inode.
	*/
	public int getNumberOfInodes()
	{
		return (inodesPerGroup);
	}
	
}
