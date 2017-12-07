# ext2-File-Reader
A java program to read and interpret the contents of an ext2 filesystem

## Commands
/help - displays all available commands

/exit - ends the program

/root - navigates back to the starting directory, useful for exiting deeply nested directories

/hexdump - asks for a starting position and length, outputs an array of bytes in hexadecimal format as well as the corresponding set of character if possible (used for de-bugging)

## Limitations

*PLEASE NOTE* 

>This program assumes a blocksize of 1024 bytes
>
>User & group IDs in directory entries cannot be mapped to a readable output as this is stored by the OS and not found directly within the filesystem, instead they are printed as their integer values
>
>Currently a maximum of 12288 bytes can be read from a directory, if this is exceeded only the first 12288 bytes will be read
>
>Files up to the maximum size for 1K blocks can be read however, files using triple-indirect pointers will take longer to read (about 35s vs almost instant)
