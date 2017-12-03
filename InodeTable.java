/**
* This class creates an array of inodes when provided with an Ext2 file and data from the superblock.
*/
public class InodeTable
{
	private Ext2File file;
	private Inode[] inodes;
	
	/**
	* Creates a new InodeTable 
	* 
	* @param f The Ext2 Filesystem.
	* @param length The length in bytes of each inode.
	* @param inodesNum The total number of inodes in the table.
	* @param offset The position (in bytes) at which the inode table begins in the filesystem.
	*/
	public InodeTable(Ext2File f, int length, int inodesNum, int offset)
	{
		file = f;
		inodes = new Inode[inodesNum];
		
		//Create an array of inodes
		for(int i = 0; i < inodesNum;  i++)
		{
			inodes[i] = new Inode(file, offset);
			offset = offset + length;
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
	
	public void printInode(int inodeNum)
	{
		inodes[inodeNum-1].printInode();
	}
	
	public long getInodeLength(int inodeNum)
	{
		return inodes[inodeNum-1].getLength();
	}
	
	public int getInodeBlockPointer(int inodeNum, int blockNum)
	{
		return inodes[inodeNum-1].getBlockPointer(blockNum);
	}
	
}