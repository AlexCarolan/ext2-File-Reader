import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.HashMap;

/**
* This class contains the data for a single inode.
*/
public class Inode
{
	private int fileMode;
	private int userID;
	private int fileSizeLower;
	private int lastAcessTime;
	private int creationTime;
	private int lastModifiedTime;
	private int deletedTime;
	private int groupID;
	private int hardLinks;
	private int[] blockPointers;
	private int indirectPointer;
	private int doubleIndirectPointer;
	private int tripleIndirectPointer;
	private int fileSizeUpper;
	private long fileSize;
	private HashMap<Integer, String> monthMap = new HashMap<Integer, String>();
	
	/**
	* Creates a new Inode from the specified position (offset).
	* Inode data is broken down byte by byte and seperated into individual values.
	* 
	* @param file The Ext2 Filesystem.
	* @param offset The position (in bytes) at which the inode table begins in the filesystem.
	*/
	public Inode (Ext2File file, int offset)
	{
		
		ByteBuffer byteBuff;
		
		//Initialize the hashmap for integers(0-11) -> months
		monthMap.put(0, "January");
		monthMap.put(1, "Febuary");
		monthMap.put(2, "March");
		monthMap.put(3, "April");
		monthMap.put(4, "May");
		monthMap.put(5, "June");
		monthMap.put(6, "July");
		monthMap.put(7, "August");
		monthMap.put(8, "September");
		monthMap.put(9, "October");
		monthMap.put(10, "November");
		monthMap.put(11, "December");
		
		//use paddedArray to fill the 4 byte requirment of getInt()
		byte buffer[] = file.read(offset, 2);
		byte paddedArray[] = {0,0,0,0};
		System.arraycopy(buffer, 0, paddedArray, 0, 2);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		fileMode = byteBuff.getInt();
		
		paddedArray = new byte[]{0,0,0,0};
		buffer = file.read(offset+2, 2);
		System.arraycopy(buffer, 0, paddedArray, 0, 2);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		userID = byteBuff.getInt();
		
		buffer = file.read(offset+4, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		fileSizeLower = byteBuff.getInt();
		
		buffer = file.read(offset+8, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		lastAcessTime = byteBuff.getInt();
		
		buffer = file.read(offset+12, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		creationTime = byteBuff.getInt();;
		
		buffer = file.read(offset+16, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		lastModifiedTime = byteBuff.getInt();;
		
		buffer = file.read(offset+20, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		deletedTime = byteBuff.getInt();
		
		paddedArray = new byte[]{0,0,0,0};
		buffer = file.read(offset+24, 2);
		System.arraycopy(buffer, 0, paddedArray, 0, 2);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		groupID = byteBuff.getInt();
		
		paddedArray = new byte[]{0,0,0,0};
		buffer = file.read(offset+26, 2);
		System.arraycopy(buffer, 0, paddedArray, 0, 2);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		hardLinks = byteBuff.getInt();
		
		blockPointers = new int[12];
		for(int i=0; i <12; i++)
		{
			buffer = file.read(offset+40+(i*4), 4);
			byteBuff = ByteBuffer.wrap(buffer);
			byteBuff.order(ByteOrder.LITTLE_ENDIAN);
			blockPointers[i] = byteBuff.getInt();
		}

		buffer = file.read(offset+88, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		indirectPointer = byteBuff.getInt();

		buffer = file.read(offset+92, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		doubleIndirectPointer = byteBuff.getInt();

		buffer = file.read(offset+96, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		tripleIndirectPointer = byteBuff.getInt();
		
		buffer = file.read(offset+108, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		fileSizeUpper = byteBuff.getInt();
		
		//Calculate the file size by combing the lower and upper values
		fileSize = (((long)fileSizeUpper << 32)  | (fileSizeLower));

	}
	
	/**
	* Prints out the contents of the inode in unix directory listing format.
	* (Note this does not include the file name as it resides in the directory lisitng seperate form the inode)
	*/
	public void printInode()
	{
		//File Type
		if((fileMode & 0x8000) == 0x8000)
		{
			System.out.print("-"); //Regular File
		} 
		else if(((fileMode) & 0x4000) == 0x4000)
		{
			System.out.print("d"); //Directory
		}
		
		//User Permissions
		if((fileMode & 0x0100) == 0x0100)
		{
			System.out.print("r");
		}else{
			System.out.print("-");
		}
		
		if((fileMode & 0x0080) == 0x0080)
		{
			System.out.print("w");
		}else{
			System.out.print("-");
		}
		
		if((fileMode & 0x0040) == 0x0040)
		{
			System.out.print("x");
		}else{
			System.out.print("-");
		}
		
		//Group Permissions
		if((fileMode & 0x0020) == 0x0020)
		{
			System.out.print("r");
		}else{
			System.out.print("-");
		}
		
		if((fileMode & 0x0010) == 0x0010)
		{
			System.out.print("w");
		}else{
			System.out.print("-");
		}
		
		if((fileMode & 0x0008) == 0x0008)
		{
			System.out.print("x");
		}else{
			System.out.print("-");
		}
		
		//Others Permissions
		if((fileMode & 0x0004) == 0x0004)
		{
			System.out.print("r");
		}else{
			System.out.print("-");
		}
		
		if((fileMode & 0x0002) == 0x0002)
		{
			System.out.print("w");
		}else{
			System.out.print("-");
		}
		
		if((fileMode & 0x0001) == 0x0001)
		{
			System.out.print("x    ");
		}else{
			System.out.print("-    ");
		}
		
		//Hard Links, User-ID, Group-ID
		System.out.printf("%-4d %-5d %-5d %-12d", hardLinks, userID, groupID, fileSize);
		
		long lastModifiedMillSec = (long)lastModifiedTime * 1000L;
		Date lastModified = new Date(lastModifiedMillSec);
		
		System.out.printf("%-4d %-9s %-3d", (lastModified.getYear() + 1900), (monthMap.get(lastModified.getMonth())), (lastModified.getDate()));
		
		if(lastModified.getHours() < 10)//Hours
		{
		System.out.print("0" + lastModified.getHours() + ":");
		}else{
		System.out.print(lastModified.getHours() + ":");
		}
		
		if(lastModified.getMinutes() < 10)//Minutes
		{
			System.out.print("0" + lastModified.getMinutes() + " ");
		}else{
			System.out.print(lastModified.getMinutes() + " ");
		}
	}
	
	public int getBlockPointer(int blockNum)
	{
		return blockPointers[blockNum];
	}
	
	public long getLength()
	{
		return fileSize;
	}
	
	public int getIndirectPointer()
	{
		return indirectPointer;
	}
	
	public int getDoubleIndirectPointer()
	{
		return doubleIndirectPointer;
	}
	
	public int getTripleIndirectPointer()
	{
		return tripleIndirectPointer;
	}

}