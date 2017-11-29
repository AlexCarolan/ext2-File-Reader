import java.io.RandomAccessFile;
public class Ext2File
{
	private Volume volume;
	private long curentPosition;
	
	//Constructor for the class
	public Ext2File(Volume vol)
	{
		volume = vol;
		curentPosition = 0;
	}
	
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
	
	public void seek(long position)
	{
		curentPosition = position;
	}
	
	public long position()
	{
		return curentPosition;
	}
	
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
