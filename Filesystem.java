public class Filesystem
{
	public static void main(String[] args)
	{
		//Test.txt
		//ext2fs
		
		Volume vol = new Volume("H:\\Computer Science\\Second Year\\SCC.211 Operating Systems\\Week 5 - 10\\files\\ext2fs");
		Ext2File file = new  Ext2File(vol);
		
		byte buf[ ] = file.read(0L, file.size());
		
		for(int i = 0; i < file.size(); i++)
		{
			System.out.print(buf[i] + " ");
		}
		
	}
}
