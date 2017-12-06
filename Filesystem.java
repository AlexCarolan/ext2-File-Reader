import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
public class Filesystem
{
	public static void main(String[] args)
	{
		//Select the correct filepath based on the OS
		String winStr = "files\\ext2fs";
		String unixStr = "files/ext2fs";
		Volume vol;
		
		if (System.getProperty("os.name").startsWith("Windows")) {
			vol = new Volume(winStr);
		} else {
			vol = new Volume(unixStr);
		}
		
		//Create a new ext2 file and superblock from the file and an offset of the blocksize
		Ext2File file = new Ext2File(vol);
		SuperBlock superBlock = new SuperBlock(file, 1024);
		
		//create a new inode table and root directory
		InodeTable inodeTable = new InodeTable(file, superBlock);
		Directory directory = new Directory(file, inodeTable, 2);
		System.out.println("---------------------------------------------------------------------------------------------------------");
		System.out.printf("%-13s %-4s %-5s %-5s %-11s %-22s %s\n", "PERMISSIONS", "H-L", "U-ID", "G-ID", "SIZE", "LAST MODIFIED", "NAME");
		System.out.println("---------------------------------------------------------------------------------------------------------");
		directory.printDirectory();
		
		//Access the inodes and directory entries from the root directory;
		Inode[] inodes = inodeTable.getInodeTable();
		FileInfo[] directoryEntries = directory.getFileInfo();
		
		//Configure variables needed for navigation
		Helper helper = new Helper();
		Scanner reader = new Scanner(System.in);
		String input = new String();
		String[] path;
		FileContent regularFile;
		File filePath;
		ArrayList<String> pathStrings;
		Directory startDirectory;
		
		int length;
		int start;
		boolean check;
		byte[] buffer;
		
		//Navigate & read the file system
		
		while(!input.equals("/exit")) //Exit command (ends program)
		{
			check = false;
			startDirectory = directory;
			System.out.print("Enter the name of the file or directory you wish to access or /help for commands: ");
			input = reader.nextLine();
			
			if(input.equals("/root")) //Root command (returns to starting directory)
			{
				directory = new Directory(file, inodeTable, 2);
				System.out.println("---------------------------------------------------------------------------------------------------------");
				System.out.printf("%-13s %-4s %-5s %-5s %-11s %-22s %s\n", "PERMISSIONS", "H-L", "U-ID", "G-ID", "SIZE", "LAST MODIFIED", "NAME");
				System.out.println("---------------------------------------------------------------------------------------------------------");
				directory.printDirectory();
				directoryEntries = directory.getFileInfo();
				check = true;
			}
			else if(input.equals("/help")) //Root command (returns to starting directory)
			{
				System.out.println("---------------------------------------------------------------------------------------------------------");
				System.out.print("\tAvalible Commands:\n\n\t/exit - ends the program\n\t/root - returns to the root directory\n\t/hexdump - asks for a start byte and length then outputs those bytes from the file\n");
				System.out.println("---------------------------------------------------------------------------------------------------------");
				check = true;
			}
			else if(input.equals("/hexdump")) //hexdump command (outputs a byte array in a readable format)
			{
				try
				{
					System.out.print("Enter the byte you wish to start from: ");
					start = reader.nextInt();
			
					System.out.print("Enter the number of bytes to dump: ");
					length = reader.nextInt();
					
					buffer = file.read(start, length);
					System.out.println("---------------------------------------------------------------------------------------------------------");
					helper.dumpHexBytes(buffer);
				}
				catch(Exception e)
				{
					System.out.println("---------------------------------------------------------------------------------------------------------");
					System.out.println("Invalid input for hexdump");
				}
				System.out.println("---------------------------------------------------------------------------------------------------------");
				reader.nextLine();
				check = true;
			}
			else //Navigate to a file or directory using the given filepath
			{
				filePath = new File(input);
				pathStrings = new ArrayList<String>();
				
				while(filePath.getParent() !=null)
				{
					pathStrings.add(filePath.getName());
					filePath = new File(filePath.getParent());
				}
				
				pathStrings.add(filePath.getName());
				path = new String[pathStrings.size()];
				path = pathStrings.toArray(path);
				
				for(int i = (path.length-1); i>=0; i--)
				{
					
					for(int j=0; j<directoryEntries.length; j++)
					{
						if(directoryEntries[j].getFileName().equals(path[i]))
						{
							if(directoryEntries[j].getFileType() == 1 && i == 0) //Access a regular file
							{
								System.out.println("---------------------------------------------------------------------------------------------------------");
								regularFile = new FileContent(file, inodes[(directoryEntries[j].getInode()-1)]);
								System.out.println("---------------------------------------------------------------------------------------------------------");
								directory = startDirectory;
								directoryEntries = directory.getFileInfo();
								check = true;
								break;
							}
							else if(directoryEntries[j].getFileType() == 2 && i > 0) //Access a directory (not at the end of the file path)
							{
								directory = new Directory(file, inodeTable, (directoryEntries[j].getInode()));
								directoryEntries = directory.getFileInfo();
								break;
							}
							else if(directoryEntries[j].getFileType() == 2 && i == 0) //Access a directory (at end of file path)
							{
								System.out.println("---------------------------------------------------------------------------------------------------------");
								System.out.printf("%-13s %-4s %-5s %-5s %-11s %-22s %s\n", "PERMISSIONS", "H-L", "U-ID", "G-ID", "SIZE", "LAST MODIFIED", "NAME");
								System.out.println("---------------------------------------------------------------------------------------------------------");
								directory = new Directory(file, inodeTable, (directoryEntries[j].getInode()));
								directoryEntries = directory.getFileInfo();
								directory.printDirectory();
								check = true;
								break;
							}
						}
					}
				}
			}
			
			if(check == false && !input.equals("/exit"))//If access failed or was not a valid command 
			{
				directory = startDirectory;
				directoryEntries = directory.getFileInfo();
				System.out.println("The specified file, directory or command could not be found");
			}
		}
		
		System.out.println("\n  --- You have chosen to exit the filesystem reader, goodbye! ---");
		
	}
}
