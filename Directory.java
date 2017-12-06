import java.util.ArrayList;

public class Directory
{
	private InodeTable inodes;
	private FileInfo[] info;
	private Ext2File file;
	private ArrayList<FileInfo> directoryEntries = new ArrayList<FileInfo>();
	private FileInfo tempFile;
	
	public Directory(Ext2File f, InodeTable table, int dirInode)
	{
		file = f;
		inodes = table;
		
		int dirSize = (int)inodes.getInodeLength(dirInode);
		int offset = 0;
		int totalPos = 0;
		
		for(int i=0; i<12 && !(totalPos >= dirSize); i++)
		{
			while(offset + (1024*i) < (1024*(i+1)))
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
	}
	
	public void printDirectory()
	{
		for (int i=0; i<info.length; i++)
		{
			if(info[i].getInode() > 0)
			{
				inodes.printInode(info[i].getInode());
				info[i].printFileName();
			}
		}
		System.out.println("---------------------------------------------------------------------------------------------------------");
	}
	
	public FileInfo[] getFileInfo()
	{
		return (info);
	}
	
	
	
	
}

