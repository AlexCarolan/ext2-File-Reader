public class Filesystem
{
	public static void main(String[] args)
	{
		String winStr = "H:\\Computer Science\\Second Year\\SCC.211 Operating Systems\\Week 5 - 10\\files\\ext2fs";
		String unixStr = "/home/lancs/carolana/hdrive/Computer Science/Second Year/SCC.211 Operating Systems/Week 5 - 10/repo/ext2-File-Reader/files/ext2fs";

		
		//Test.txt
		//ext2fs
		Helper helper = new Helper();
		Volume vol;
		
		if (System.getProperty("os.name").startsWith("Windows")) {
			vol = new Volume(winStr);
		} else {
			vol = new Volume(unixStr);
		}
		
		
		
		Ext2File file = new  Ext2File(vol);
		
		
		byte buf[] = file.read(2048, 1000);
		
		helper.dumpHexBytes(buf);
		
	}
}
