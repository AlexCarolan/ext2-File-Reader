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
		
		byte buffer[] = file.read((offset*2)+8, 4);
		ByteBuffer inodeTablePointer = ByteBuffer.wrap(buffer);
		inodeTablePointer.order(ByteOrder.LITTLE_ENDIAN);
		
		int indodeBlock = inodeTablePointer.getInt();
		
		System.out.println("------------------------------------------------------------------");
		System.out.println("Inode Table Starts at Block " + indodeBlock);
		System.out.println("------------------------------------------------------------------");
		
		int inodeTableStart = indodeBlock*1024;
		

		
		InodeTable inodeTable = new InodeTable(file, superBlock.getInodesize(), superBlock.getNumberOfInodes(), inodeTableStart);
		Directory rootDirectory = new Directory(file, inodeTable, 2);
		
		Inode[] inodes = inodeTable.getInodeTable();
		
		//Check inode values for debugging

		
		Scanner reader = new Scanner(System.in);
		int inodeNumber = 0;
		
		buffer = file.read(inodeTableStart+128+16, 4);
		helper.dumpHexBytes(buffer);
		
		System.out.println("Enter the number of the inode you wish to access(or 0 to exit): ");
		inodeNumber = reader.nextInt();
		
		while(inodeNumber != 0)
		{
			System.out.println("------------------------------------------------------------------\n");
			inodes[inodeNumber-1].printInode();
			System.out.println("\n------------------------------------------------------------------");
			System.out.println("Enter the number of the inode you wish to access(or 0 to exit): ");
			inodeNumber = reader.nextInt();
		}
		
		
		
		
		
		
		
		

		
		
	}
}
