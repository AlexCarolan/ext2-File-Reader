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
		
		if (System.getProperty("os.name").startsWith("Windows")) {
			vol = new Volume(winStr);
		} else {
			vol = new Volume(unixStr);
		}
		
		//Create a new ext2 file and superblock from the file and an offset of the blocksize
		Ext2File file = new Ext2File(vol);
		SuperBlock superBlock = new SuperBlock(file, 1024);
		System.out.println("------------------------------------------------------------------");
		
		
		//Calaculate the start of the table and use it to create a new table and root directory
		InodeTable inodeTable = new InodeTable(file, superBlock);
		Directory directory = new Directory(file, inodeTable, 2);
		
		//Access the inodes and directory entries from the root directory;
		Inode[] inodes = inodeTable.getInodeTable();
		FileInfo[] directoryEntries = directory.getFileInfo();
		
		
		//Navigate the file system
		Scanner reader = new Scanner(System.in);
		String input = new String();
		File regularFile;
		
		int length;
		int start;
		boolean check;
		byte[] buffer;
		
		while(!input.equals("exit"))
		{
			check = false;
			System.out.print("Enter the name of the file or directory you wish to access or \"exit\" to quit: ");
			input = reader.nextLine();
			
			for(int i=0; i<directoryEntries.length; i++)
			{

				if(input.equals("root"))
				{
					System.out.println("------------------------------------------------------------------");
					directory = new Directory(file, inodeTable, 2);
					directoryEntries = directory.getFileInfo();
					check = true;
					break;
				}
				else if(input.equals("hexdump"))
				{
					System.out.print("Enter the byte you wish to start from: ");
					start = reader.nextInt();
					
					System.out.print("Enter the number of bytes to dump: ");
					length = reader.nextInt();
					
					buffer = file.read(start, length);
					System.out.println("------------------------------------------------------------------");
					helper.dumpHexBytes(buffer);
					System.out.println("------------------------------------------------------------------");
					reader.nextLine();
					check = true;
					break;
				}
				else if(directoryEntries[i].getFileName().equals(input))
				{
					if(directoryEntries[i].getFileType() == 1)
					{
						System.out.println("------------------------------------------------------------------");
						regularFile = new File(file, inodes[(directoryEntries[i].getInode()-1)]);
						System.out.println("------------------------------------------------------------------");
						check = true;
						break;
					}
					else if(directoryEntries[i].getFileType() == 2)
					{
						System.out.println("------------------------------------------------------------------");
						directory = new Directory(file, inodeTable, (directoryEntries[i].getInode()));
						directoryEntries = directory.getFileInfo();
						check = true;
						break;
					}
				}
			}
			
			if(check == false)
			{
				System.out.println("The specified file, directory or command could not be found");
			}
		}
		
		System.out.println("You have chosen to exit the filesystem reader, goodbye!");
		
	}
}
