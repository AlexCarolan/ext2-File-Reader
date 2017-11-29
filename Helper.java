public class Helper
{	
	//Prints out the contents as a byte array in the format of hex and chars
	public void dumpHexBytes(byte[] bytes)
	{
		int offset = 0;
		while(offset <= bytes.length)
		{
			for(int i = offset; i < offset+8; i++)
			{	
				if(i < bytes.length)
				{
					System.out.printf("%02X ", bytes[i]);
				}else{
					System.out.print("XX ");
				}
			}
			
			System.out.print("| ");
			
			for(int i = offset+8; i < offset+16; i++)
			{
				if(i < bytes.length)
				{
					System.out.printf("%02X ", bytes[i]);
				}else{
					System.out.print("XX ");
				}
			}
			
			System.out.printf("| ");
			
			for(int i = offset; i < offset+8; i++)
			{
				if(bytes.length > i)
				{
					if (bytes[i] > 31 & bytes[i] < 123)
					{
						System.out.print((char)bytes[i]);
					}else{
						System.out.print("-");
					}
				}else{
					System.out.print(" ");
				}
			}
			
			System.out.printf(" | ");
			
			for(int i = offset+8; i < offset+16; i++)
			{
				if(bytes.length > i)
				{
					if (bytes[i] > 31 & bytes[i] < 123)
					{
						System.out.print((char)bytes[i]);
					}else{
						System.out.print("-");
					}
				}else{
					System.out.print(" ");
				}
			}
			
			System.out.printf("\n");
			offset = offset + 16;
		}
	}
	
	
	
}
