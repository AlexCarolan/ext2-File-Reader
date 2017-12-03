# ext2-File-Reader
A java program to read and interpret the contents of an ext2 filesystem

*NOTE* 

>This program assumes a blocksize of 1024 bytes
>
>User & group IDs in directory entries cannot be mapped to a readable output as this is stored by the OS and not found directly within the filesystem, instead they are printed as their integer values
