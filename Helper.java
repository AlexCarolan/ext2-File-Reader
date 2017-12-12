/**
* This class provides a hex and coresponding char output from a provided byte array.
* This class is intended for troubleshooting and byte content verification by developers and is not used by the main program.
*/
public class Helper
{	
	/**
	* Outputs the provided byte array as a set of hex values and chars.
	* Output is in the format (bytes 0-7(hex) | 8-15(hex) | 0-7(char) | 8-15(char)).
	* Each row conatins 16 bytes worth of data and an X denotes that the array end has been reached.
	*
	* @param bytes The byte array to be output.
	*/
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
