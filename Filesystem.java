import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;
public class Filesystem
{
	public static void main(String[] args)
	{
		String winStr = "H:\\Computer Science\\Second Year\\SCC.211 Operating Systems\\Week 5 - 10\\files\\ext2fs";
		String unixStr = "/home/lancs/carolana/hdrive/Computer Science/Second Year/SCC.211 Operating Systems/Week 5 - 10/repo/ext2-File-Reader/files/ext2fs";

		Helper helper = new Helper();
		Volume vol;
		
		int offset = 1024;
		
		if (System.getProperty("os.name").startsWith("Windows")) {
			vol = new Volume(winStr);
		} else {
			vol = new Volume(unixStr);
		}
		
		Ext2File file = new Ext2File(vol);
		
		SuperBlock superBlock = new SuperBlock(file, offset);
		
		//Find the pointer for the inode table 
		byte buffer[] = file.read((offset*2)+8, 4);
		ByteBuffer inodeTablePointer = ByteBuffer.wrap(buffer);
		inodeTablePointer.order(ByteOrder.LITTLE_ENDIAN);
		int indodeBlock = inodeTablePointer.getInt();
		
		//Display the position
		System.out.println("------------------------------------------------------------------");
		System.out.println("Inode Table Starts at Block " + indodeBlock);
		System.out.println("------------------------------------------------------------------");
		
		//Calaculate the start of the table and use it to create a new table and root directory
		int inodeTableStart = indodeBlock*1024;
		InodeTable inodeTable = new InodeTable(file, superBlock.getInodesize(), superBlock.getNumberOfInodes(), inodeTableStart);
		Directory directory = new Directory(file, inodeTable, 2);
		
		//Access the inodes and directory entries from the root directory;
		Inode[] inodes = inodeTable.getInodeTable();
		FileInfo[] directoryEntries = directory.getFileInfo();
		
		
		//Navigate the file system
		Scanner reader = new Scanner(System.in);
		String input = new String();
		File regularFile;
		
		//System.out.println("------------------------------------------------------------------\n");
		//System.out.println("\n------------------------------------------------------------------");
		int i;
		
		while(true)
		{
			System.out.print("Enter the name of the file or directory you wish to access: ");
			input = reader.nextLine();
			
			for(i=0; i<directoryEntries.length; i++)
			{
				if(directoryEntries[i].getFileName().trim().equals(input))
				{
					if(directoryEntries[i].getFileType() == 1)
					{
						regularFile = new File(file, inodes[(directoryEntries[i].getInode()-1)]);
						break;
					}
					else if(directoryEntries[i].getFileType() == 2)
					{
						directory = new Directory(file, inodeTable, (directoryEntries[i].getInode()));
						break;
					}
				}
			}
			
			if(i >= directoryEntries.length)
			{
				System.out.println("The specified file/directory could not be found");
			}
		
		

		}
		
		

		
		
		
		
		
		
		

		
		
	}
}
