import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.HashMap;
public class Inode
{
	private Ext2File file;
	private int offset;
	private ByteBuffer byteBuff;
	private byte padding = 0x00;
	private int fileMode;
	private int userID;
	private int fileSizeLower;
	private int lastAcessTime;
	private int creationTime;
	private int lastModifiedTime;
	private int deletedTime;
	private int groupID;
	private int hardLinks;
	private long blockPointers;
	private int indirectPointer;
	private int doubleIndirectPointer;
	private int tripleIndirectPointer;
	private int fileSizeUpper;
	private HashMap<Integer, String> monthMap = new HashMap<Integer, String>();

	
	
	public Inode (Ext2File f, int o)
	{
		file = f;
		offset = o;
		
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
		
		buffer = file.read(offset+24, 2);
		System.arraycopy(buffer, 0, paddedArray, 0, 2);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		groupID = byteBuff.getInt();
		
		buffer = file.read(offset+26, 2);
		System.arraycopy(buffer, 0, paddedArray, 0, 2);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		hardLinks = byteBuff.getInt();
		
		buffer = file.read(offset+40, 48);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		blockPointers = byteBuff.getLong();

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

	}

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
			System.out.print("x ");
		}else{
			System.out.print("- ");
		}
		
		System.out.print(hardLinks + " ");
		
		if (userID == 0)
		{
			System.out.print("root ");
		}else{
			System.out.print(userID + " ");
		}
		
		if (groupID == 0)
		{
			System.out.print("root ");
		}else{
			System.out.print(groupID + " ");
		}
		long fileSize = (fileSizeLower) + (fileSizeUpper << 32);
		System.out.print(fileSize + " ");
		
		long lastModifiedMillSec = lastModifiedTime * 1000L;
		
		Date lastModified = new Date(lastModifiedMillSec);
		
		System.out.print(lastModified.getYear() + 1900 + " "); //Month
		System.out.print(monthMap.get(lastModified.getMonth())); //Month
		System.out.print(" " + lastModified.getDate() + " "); //Day
		System.out.print(lastModified.getHours() + ":" + lastModified.getMinutes()); //Time
		
		System.out.print("\n");
		
		
	}

}