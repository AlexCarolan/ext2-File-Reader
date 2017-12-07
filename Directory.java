import java.util.ArrayList;

/**
* This class holds directory entries in the form of an array of FileInfo instances.
*/
public class Directory
{
	private static InodeTable inodes;
	private FileInfo[] info;
	
	/**
	* Creates a new Directory instance.
	* An array of FileInfo instances is created, one instance for each entry in the directory.
	* 
	* @param file The Ext2 Filesystem.
	* @param table The inode table for the filesystem.
	* @param dirInode The number of the inode that references this directory.
	*/
	public Directory(Ext2File file, InodeTable table, int dirInode)
	{
		inodes = table;
		FileInfo tempFile;
		ArrayList<FileInfo> directoryEntries = new ArrayList<FileInfo>();
		
		int dirSize = (int)inodes.getInodeLength(dirInode);
		int offset = 0;
		int totalPos = 0;
		
		//Read from the first 12 block pointers
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
		
		//get the complete array from the array list
		info = new FileInfo[directoryEntries.size()];
		info = directoryEntries.toArray(info);
	}
	
	/**
	* Prints the contents of the directory in a unix style format.
	*/
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
	
	/**
	* provides the array containing the file information.
	* 
	* @return info The FileInfo array of file information such as the name and inode number.
	*/
	public FileInfo[] getFileInfo()
	{
		return (info);
	}
	
	
	
	
}

