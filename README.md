# ext2-File-Reader
A java program to read and interpret the contents of an ext2 filesystem

#Commands

exit - ends the program

root - navigates back to the starting directory, useful for exiting deeply nested directories

*NOTE* 

>This program assumes a blocksize of 1024 bytes
>
>User & group IDs in directory entries cannot be mapped to a readable output as this is stored by the OS and not found directly within the filesystem, instead they are printed as their integer values
>
>Currently a maximum of 12288 bytes can be read from a directory, if this is exceeded only the first 12288 bytes will be read
