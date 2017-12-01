import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.HashMap;
public class FileInfo
{
	private Ext2File file;
	private int offset;
	private ByteBuffer fileMode;
	private ByteBuffer userID;
	private ByteBuffer fileSizeLower;
	private ByteBuffer lastAcessTime;
	private ByteBuffer creationTime;
	private ByteBuffer lastModifiedTime;
	private ByteBuffer deletedTime;
	private ByteBuffer groupID;
	private ByteBuffer hardLinks;
	private ByteBuffer blockPointers;
	private ByteBuffer indirectPointer;
	private ByteBuffer doubleIndirectPointer;
	private ByteBuffer tripleIndirectPointer;
	private ByteBuffer fileSizeUpper;
	
	HashMap<Integer, String> monthMap = new HashMap<Integer, String>();

	
	
	public FileInfo (Ext2File f, int o)
	{
		file = f;
		offset = o;
		
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
		
		byte buffer[] = file.read(offset, 2);
		fileMode = ByteBuffer.wrap(buffer);
		fileMode.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+2, 2);
		userID = ByteBuffer.wrap(buffer);
		userID.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+4, 4);
		fileSizeLower = ByteBuffer.wrap(buffer);
		fileSizeLower.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+8, 4);
		lastAcessTime = ByteBuffer.wrap(buffer);
		lastAcessTime.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+12, 4);
		creationTime = ByteBuffer.wrap(buffer);
		creationTime.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+16, 4);
		lastModifiedTime = ByteBuffer.wrap(buffer);
		lastModifiedTime.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+20, 4);
		deletedTime = ByteBuffer.wrap(buffer);
		deletedTime.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+24, 2);
		groupID = ByteBuffer.wrap(buffer);
		groupID.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+26, 2);
		hardLinks = ByteBuffer.wrap(buffer);
		hardLinks.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+40, 48);
		blockPointers = ByteBuffer.wrap(buffer);
		blockPointers.order(ByteOrder.LITTLE_ENDIAN);

		buffer = file.read(offset+88, 4);
		indirectPointer = ByteBuffer.wrap(buffer);
		indirectPointer.order(ByteOrder.LITTLE_ENDIAN);

		buffer = file.read(offset+92, 4);
		doubleIndirectPointer = ByteBuffer.wrap(buffer);
		doubleIndirectPointer.order(ByteOrder.LITTLE_ENDIAN);

		buffer = file.read(offset+96, 4);
		tripleIndirectPointer = ByteBuffer.wrap(buffer);
		tripleIndirectPointer.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+108, 4);
		fileSizeUpper = ByteBuffer.wrap(buffer);
		fileSizeUpper.order(ByteOrder.LITTLE_ENDIAN);

	}

	public void printFileInfo()
	{
		//File Type
		if((fileMode.getInt() | 0x8000) == 0x8000)
		{
			System.out.print("-"); //Regular File
		} 
		else if((fileMode.getInt() | 0x4000) == 0x4000)
		{
			System.out.print("b"); //Directory
		}
		
		//User Permissions
		if((fileMode.getInt() | 0x0100) == 0x0100)
		{
			System.out.print("r");
		}else{
			System.out.print("-");
		}
		
		if((fileMode.getInt() | 0x0080) == 0x0080)
		{
			System.out.print("w");
		}else{
			System.out.print("-");
		}
		
		if((fileMode.getInt() | 0x0040) == 0x0040)
		{
			System.out.print("x");
		}else{
			System.out.print("-");
		}
		
		//Group Permissions
		if((fileMode.getInt() | 0x0020) == 0x0020)
		{
			System.out.print("r");
		}else{
			System.out.print("-");
		}
		
		if((fileMode.getInt() | 0x0010) == 0x0010)
		{
			System.out.print("w");
		}else{
			System.out.print("-");
		}
		
		if((fileMode.getInt() | 0x0008) == 0x0008)
		{
			System.out.print("x");
		}else{
			System.out.print("-");
		}
		
		//Others Permissions
		if((fileMode.getInt() | 0x0004) == 0x0004)
		{
			System.out.print("r");
		}else{
			System.out.print("-");
		}
		
		if((fileMode.getInt() | 0x0002) == 0x0002)
		{
			System.out.print("w");
		}else{
			System.out.print("-");
		}
		
		if((fileMode.getInt() | 0x0001) == 0x0001)
		{
			System.out.print("x ");
		}else{
			System.out.print("- ");
		}
		
		System.out.print(hardLinks.getInt());
		System.out.print(userID.getInt() + " ");
		System.out.print(groupID.getInt() + " ");
		System.out.print(((fileSizeLower.getInt()) + (fileSizeUpper.getInt() << 32)) + " ");
		
		Date lastModified = new Date(lastModifiedTime.getLong());
		
		
		System.out.print(monthMap.get(lastModified.getMonth())); //Month
		System.out.print(" " + lastModified.getDate() + " "); //Day
		System.out.print(lastModified.getHours() + ":" + lastModified.getMinutes()); //Time
		
		
		
	}

	
	private void printMonth(int month)
	{
		if(month == 0)
		{
			
		}
	}

}