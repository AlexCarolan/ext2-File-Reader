import java.io.RandomAccessFile;

/**
* This class allows for bytes of data to be read from the Ext2 file provided in the Volume class.
* This class also allows for tracking the current file position.
*/
public class Ext2File
{
	private final Volume volume;
	private long curentPosition;
	
	/**
	* Creates a new Ext2File using the data from a Volume class.
	* 
	* @param vol a Volume class containing the filesystem.
	*/
	public Ext2File(Volume vol)
	{
		volume = vol;
		curentPosition = 0;
	}
	
	/**
	* Reads a specified number of bytes from the Ext2 file.
	* (Using a provided start byte)
	* 
	* @param startByte The byte from which reading will begin.
	* @param length The number of bytes to be read.
	*
	* @return an array of bytes read from the file.
	*/
	public byte[] read(long startByte, long length)
	{
		byte[] byteArray = new byte[(int) length];
		try
		{
			volume.getFile().seek(startByte);
			volume.getFile().read(byteArray, 0, (int) length);
			return(byteArray);
		}catch(Exception e){
			System.out.println(e);
			return(null);
		}
	}
	
	/**
	* Reads a specified number of bytes from the Ext2 file.
	* (Using the current position as the start point)
	* 
	* @param length The number of bytes to be read.
	*
	* @return an array of bytes read from the file.
	*/
	public byte[] read(long length)
	{
		byte[] byteArray = new byte[(int) length];
		try
		{
			volume.getFile().seek(curentPosition);
			volume.getFile().read(byteArray, 0, (int) length);
			return(byteArray);
		}catch(Exception e){
			System.out.println(e);
			return(null);
		}
	}
	
	/**
	* Sets the current position of the file accessor to the specified value.
	* 
	* @param position The location of the byte that the file is to be accessed from.
	*/
	public void seek(long position)
	{
		curentPosition = position;
	}
	
	/**
	* Provides the current location (byte) that the file is being accessed from.
	* 
	* @return The current position of the file accessor.
	*/
	public long position()
	{
		return curentPosition;
	}
	
	/**
	* Provides the size of the file as the number of bytes.
	* 
	* @return The size of the file.
	*/
	public long size()
	{
		try
		{
			return volume.getFile().length();
		}catch(Exception e){
			System.out.println(e);
			return 0;
		}
	}
	
}
