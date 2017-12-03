import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class FileInfo
{
	private Ext2File file;
	private ByteBuffer byteBuff;
	private int offset;
	private int inodeNumber;
	private int length;
	private int nameLength;
	private int fileType;
	private String fileName;
	
	
	public FileInfo (Ext2File f, int o)
	{
		file = f;
		offset = o;
		
		byte buffer[] = file.read(offset, 4);
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		inodeNumber = byteBuff.getInt();
		
		buffer = file.read(offset+4, 2);
		byte paddedArray[] = {0,0,0,0};
		System.arraycopy(buffer, 0, paddedArray, 0, 2);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		length = byteBuff.getInt();
		
		buffer = file.read(offset+6, 1);
		System.arraycopy(buffer, 0, paddedArray, 0, 1);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		nameLength = byteBuff.getInt();
		
		buffer = file.read(offset+7, 1);
		System.arraycopy(buffer, 0, paddedArray, 0, 1);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		fileType = byteBuff.getInt();
		
		//Calculate the number of 4 byte blocks that hold the file name
		int nameBytes = nameLength/4;
		if((nameLength%4) != 0)
		{
			nameBytes++;
		}
		
		buffer = file.read(offset+8, (nameBytes*4));
		byteBuff = ByteBuffer.wrap(buffer);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		fileName = new String(byteBuff.array());
		
	}

	public void printFileName()
	{
		System.out.print(fileName);
	}

}