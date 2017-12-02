public class InodeTable
{
	private Ext2File file;
	private Inode[] inodes;
	
	public InodeTable(Ext2File f, int length, int inodesNum, int offset)
	{
		file = f;
		inodes = new Inode[inodesNum];
		
		for(int i = 0; i < inodesNum;  i++)
		{
			inodes[i] = new Inode(file, offset);
			offset = offset + length;
		}
		
		
	}
	
	public Inode[] getInodeTable()
	{
		return (inodes);
	}
}