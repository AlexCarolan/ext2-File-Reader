import java.util.ArrayList;

public class Directory
{
	private InodeTable inodes;
	private FileInfo[] info;
	private Ext2File file;
	private int dirInode;
	private ArrayList<FileInfo> directoryEntries = new ArrayList<FileInfo>();
	private FileInfo tempFile;
	
	public Directory(Ext2File f, InodeTable table, int di)
	{
		file = f;
		dirInode = di;
		inodes = table;
		
		int dirSize = (int)inodes.getInodeLength(dirInode);
		int offset = 0;
		int totalPos = 0;
		
		for(int i=0; i<12; i++)
		{
			if(totalPos >= dirSize)
			{
				break;
			}
			
			while(offset < (1024*(i+1)))
			{
				tempFile = new FileInfo(file, ((offset)+(inodes.getInodeBlockPointer(dirInode ,i)*1024)));
				directoryEntries.add(tempFile);
				offset = offset + tempFile.getFileLength();
			}
			
			offset = 0;
			totalPos = totalPos + 1024;
		}
		
		info = new FileInfo[directoryEntries.size()];
		info = directoryEntries.toArray(info);
		
		for (int i=0; i<info.length; i++)
		{
			inodes.printInode(info[i].getInode());
			info[i].printFileName();
		}
		
		System.out.println("------------------------------------------------------------------");
		
	}
	
	public FileInfo[] getFileInfo()
	{
		return (info);
	}
	
	
	
	
}

