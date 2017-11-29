public class Filesystem
{
	public static void main(String[] args)
	{
		//Test.txt
		//ext2fs
		Helper helper = new Helper();
		
		Volume vol = new Volume("H:\\Computer Science\\Second Year\\SCC.211 Operating Systems\\Week 5 - 10\\files\\ext2fs");
		Ext2File file = new  Ext2File(vol);
		
		byte buf[] = file.read(1024,500);	
		
		
		
		helper.dumpHexBytes(buf);
		
	}
}
