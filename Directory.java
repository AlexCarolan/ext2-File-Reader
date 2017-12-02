public class Directory
{
	private Ext2File file;
	private FileInfo[] info;
	
	public Directory(Ext2File f, int length, int inodes, int offset)
	{
		file = f;
		info = new FileInfo[inodes];
		
		for(int i = 0; i < inodes;  i++)
		{
			info[i] = new FileInfo(file, offset);
			offset = offset + length;
		}
		
		
	}
	
	public FileInfo[] getFileInfo()
	{
		return (info);
	}
	
	
	
	
}

