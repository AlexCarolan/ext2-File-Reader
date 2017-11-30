import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class SuperBlock
{
	private Ext2File file;
	private int offset;
	private ByteBuffer inodes;
	private ByteBuffer blocks;
	private ByteBuffer name;
	private ByteBuffer blocksPerGroup;
	private ByteBuffer inodesPerGroup;
	private ByteBuffer inodeSize;
	
	public SuperBlock(Ext2File f, int o)
	{
		file = f;
		offset = o;
		
		byte buffer[] = file.read(offset, 4);
		inodes = ByteBuffer.wrap(buffer);
		inodes.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+4, 4);
		blocks = ByteBuffer.wrap(buffer);
		blocks.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+120, 16);
		name = ByteBuffer.wrap(buffer);
		name.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+32, 4);
		blocksPerGroup = ByteBuffer.wrap(buffer);
		blocksPerGroup.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+40, 4);
		inodesPerGroup = ByteBuffer.wrap(buffer);
		inodesPerGroup.order(ByteOrder.LITTLE_ENDIAN);
		
		buffer = file.read(offset+88, 4);
		inodeSize = ByteBuffer.wrap(buffer);
		inodeSize.order(ByteOrder.LITTLE_ENDIAN);
		
		this.printSuperBlock();
	}
	
	public void printSuperBlock()
	{	
		System.out.println("------------------ " + new String(name.array()) + " ------------------");
		System.out.println("Total Number of Inodes: " + inodes.getInt());
		System.out.println("Total Number of Inodes per Group: " + inodesPerGroup.getInt());
		System.out.println("Total Number of Blocks: " + blocks.getInt());
		System.out.println("Total Number of Blocks per Group: " + blocksPerGroup.getInt());
		System.out.println("Inode Size: " + inodeSize.getInt() + " Bytes");	
	}
}
