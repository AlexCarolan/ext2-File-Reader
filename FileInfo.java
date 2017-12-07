import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
* This class holds information relating directly to the file.
*/
public class FileInfo
{
	private final int inodeNumber;
	private final int length;
	private final int nameLength;
	private final int fileType;
	private final String fileName;
	
	/**
	* Creates a new instnace of FileInfo.
	* The file information is found by seperating and reading the bytes in the file from the offset.
	* 
	* @param file The Ext2 Filesystem.
	* @param offset The position (in bytes) at which the file information begins.
	*/
	public FileInfo (Ext2File file, int offset)
	{
		ByteBuffer byteBuff;
		
		//Read the file information starting from the offset
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
		
		paddedArray = new byte[]{0,0,0,0};
		buffer = file.read(offset+6, 1);
		System.arraycopy(buffer, 0, paddedArray, 0, 1);
		byteBuff = ByteBuffer.wrap(paddedArray);
		byteBuff.order(ByteOrder.LITTLE_ENDIAN);
		nameLength = byteBuff.getInt();
		
		paddedArray = new byte[]{0,0,0,0};
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
		fileName = (new String(byteBuff.array())).trim();
	}
	
	/**
	* Prints out the file information in a readable format.
	*/
	public void printFileInfo()
	{
		System.out.println("Inode Number: " + inodeNumber);
		System.out.println("length: " + length);
		System.out.println("Name Length: " + nameLength);
		System.out.println("File Type: " + fileType);
		System.out.println("File Name: " + fileName + "\n");
	}
	
	/**
	* Provides the inode number for the file.
	* 
	* @return inodeNumber The number of the inode related to the file.
	*/
	public int getInode()
	{
		return inodeNumber;
	}
	
	/**
	* Provides integer value of the files type.
	* 
	* @return fileType The type of the file.
	*/
	public int getFileType()
	{
		return fileType;
	}
	
	/**
	* Provides the name of the file as a String.
	* 
	* @return fileName The name of the file.
	*/
	public String getFileName()
	{
		return fileName;
	}

	/**
	* Prints out the file name, used at the end of a unix style directory listing.
	*/
	public void printFileName()
	{
		System.out.println(fileName);
	}
	
	/**
	* Provides length (in bytes) of this files information.
	* 
	* @return length The length of this files entry.
	*/
	public int getFileLength()
	{
		return length;
	}

}
