package fxmarketdataserver;
import java.nio.channels.FileChannel;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileIOv2
{
	public String getFileLine(String directory,String filename,int line_num)
	{
		String directoryFilename = directory + "\\" + filename;
		String line;
		line = "Error";
		int count=0;
		
		try
		{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(directoryFilename);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine="";
			
			//Read File Line By Line
			while ((line_num>=count))		//read through lines
			{
				strLine = br.readLine();
				count++;
				//System.out.println(count+":strLine");
			
			}
				in.close();
				return strLine;
		}
		catch (Exception e)
		{
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return line;
		}
	}
	public int countLines(String filename)
	{
		try
		{
			LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
			int cnt = 0;
			String lineRead = "";
			while ((lineRead = reader.readLine()) != null) {}

			cnt = reader.getLineNumber(); 
			reader.close();
			return cnt;
		}
		catch(Exception e)
		{
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return -1;
		}
	}
	public void saveStringArrayToFile(String[] Line,String File_Full_Directory)			//appends data
	{

        FileReader inputStream = null;
        FileWriter outputStream = null;

		
		//writer.write("HELLO\n");
		//System.out.println("Line.length: "+ Line.length);
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(File_Full_Directory,true));
			for(int count=0; count<Line.length; count++)
			{
				writer.write(Line[count]);
				writer.write("\n");			//new row new line
			}
		writer.close();
		}
		catch (IOException e)
        {
          System.out.println("IOException : " + e);
        }
    }
	public void WriteCharToFile(String strFilePath,char character_input,int close_stream)
	{
	/*
		import java.io.DataOutputStream;
		import java.io.FileOutputStream;
		import java.io.IOException;
      Write char to a file using DataOutputStream
      This Java example shows how to write a Java character primitive value to a
      file using writeChar method of Java DataOutputStream class.
    */
       
        try
        {
          //create FileOutputStream object
          FileOutputStream fos = new FileOutputStream(strFilePath);
         
          /*
           * To create DataOutputStream object from FileOutputStream use,
           * DataOutputStream(OutputStream os) constructor.
           *
           */
         
           DataOutputStream dos = new DataOutputStream(fos);
         
           int ch = 65;
         
           /*
            * To write a char value to a file, use
            * void writeChar(int ch) method of Java DataOutputStream class.
            *
            * This method writes specified char to output stream as 2 bytes value.
            */
           
            dos.writeChar(65);
           
            /*
             * To close DataOutputStream use,
             * void close() method.
             *
             */
           if(close_stream==1)
		   {
             dos.close();
           }
		   
        }
        catch (IOException e)
        {
          System.out.println("IOException : " + e);
        }
      }	
	public void WriteStringToFile(String strFilePath, String inputString)
	{        
		BufferedWriter writer = null;
        try
        {
                writer = new BufferedWriter( new FileWriter( strFilePath));
                writer.write(inputString);

        }
        catch ( IOException e)
        {
        }
        finally
        {
                try
                {
                        if ( writer != null)
                                writer.close( );
                }
                catch ( IOException e)
                {
                }
		}
	}
	public void appendFile(String LineInput, String Input_Directory) 
	{
		try
		{
			// Create file 
			FileWriter fstream = new FileWriter(Input_Directory,true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(LineInput);
			
			//Close the output stream
			out.close();

		}
		catch(Exception e)
		{	
			//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	public void createDirectory(String Directory)
	{
		File f = new File(Directory);
		try
		{
			if(f.mkdir())
			{
				//System.out.println("Directory Created");
			}
			else
			{
				//System.out.println("Directory is not created");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void deleteDirectory(String Directory)
	{
		File f = new File(Directory);
		try
		{
			if(f.delete())
			System.out.println("Directory Deleted");
			else
			System.out.println("Directory not deleted, make sure all files are deleted in directory");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void createFile(String Path)
	{
	
		File f;
		f=new File(Path);			//set new file location
		try
		{
			//System.out.println("reaching here");
			if(!f.exists())
			{
				//System.out.println("reaching here again");
				f.createNewFile();
				//System.out.println(buffer);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void deleteFile(String Path)
	{

		File f1 = new File(Path);
		boolean success = f1.delete();
		if (!success)
		{
			//System.out.println("Deletion failed. " + Path);
		}
		else
		{
			//System.out.println("File deleted. " + Path);
		}
	}
	public int fileFolderExists(String Directory) //1=exists 0=does not exist
	{
		
		boolean exists = (new File(Directory)).exists();
		if (exists) 
		{
			// File or directory exists
			return 1;
		}
		else 
		{
			// File or directory does not exist
			return 0;
		}
	}
	public boolean isExist(String Directory) 
	{
		boolean exists = (new File(Directory)).exists();	//not case sensitive
		if (exists) 
		{
			// File or directory exists
			return true;
		}
		else 
		{
			// File or directory does not exist
			return false;
		}
	}
	public String FileLastModified(String filename)
	{
		File f = new File(filename);
		long datetime = f.lastModified();
		Date d = new Date(datetime);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String dateString = sdf.format(d);
		//System.out.println("The file was last modified on: " + dateString);
		return dateString;
	}
	public long FileLastModifiedVer2(String filename)
	{
		File f = new File(filename);
		long datetime = f.lastModified();
		//System.out.println("datetime(long format):"+datetime);
		
		return datetime;
		//Date d = new Date(datetime);
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		//String dateString = sdf.format(d);
		//System.out.println("The file was last modified on: " + dateString);
	}
    public void changeFileLastModified(File targetFile,long newLastModified)
    {	

    		//print the original last modified date
    		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    		//System.out.println("Original Last Modified Date : " + sdf.format(targetFile.lastModified()));
 
    		//set this date 
    		//String newLastModified = "01/31/1998";
 
    		//need convert the above date to milliseconds in long value 
    		//Date newDate = sdf.parse(newLastModified);
    		Date d = new Date(newLastModified);
			
			targetFile.setLastModified(d.getTime());
 
    		//print the latest last modified date
    		//System.out.println("Lastest Last Modified Date : " + sdf.format(targetFile.lastModified()));
    }
	public String userdir()
	{
		//System.out.println("Working Directory = " + System.getProperty("user.dir"));	
		return System.getProperty("user.dir");
	}
	public String[] getFileListTxt(String path)
	{
		String files;
		//String[] Filelist = new String[200];
		
		int count=0;
		int num_of_txt_files=0;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if(listOfFiles[i].isFile()) 
			{
			files = listOfFiles[i].getName();
				if(files.endsWith(".txt") || files.endsWith(".TXT"))
				{
					num_of_txt_files++;
				}
			}
		}
		
		
		String[] Filelist = new String[num_of_txt_files];
					
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if(listOfFiles[i].isFile()) 
			{
			files = listOfFiles[i].getName();
				if(files.endsWith(".txt") || files.endsWith(".TXT"))
				{
					Filelist[count]=files;
					count++;
				}
			}
		}


		
		
		return Filelist;
	}
	public String[] getFileList(String path,String prefix)
	{
		String files;
		//String[] Filelist = new String[200];
		
		int count=0;
		int num_of_txt_files=0;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if(listOfFiles[i].isFile()) 
			{
			files = listOfFiles[i].getName();
				if(files.endsWith(prefix.toLowerCase()) || files.endsWith(prefix.toUpperCase()))
				{
					num_of_txt_files++;
				}
			}
		}
		
		
		String[] Filelist = new String[num_of_txt_files];
					
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if(listOfFiles[i].isFile()) 
			{
			files = listOfFiles[i].getName();
				if(files.endsWith(prefix.toLowerCase()) || files.endsWith(prefix.toUpperCase()))
				{
					Filelist[count]=files;
					count++;
				}
			}
		}


		
		
		return Filelist;
	}
	public void displayFileList(String path,String prefix)
	{
		String files;
		//String[] Filelist = new String[200];
		
		int count=0;
		int numOfFiles=0;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 

		//count how many files their are in this folder with the supplied prefix
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if(listOfFiles[i].isFile()) 
			{
			files = listOfFiles[i].getName();
				if(files.endsWith(prefix.toLowerCase()) || files.endsWith(prefix.toUpperCase()))
				{
					numOfFiles++;
				}
			}
		}
		
		//create a new array container for the found file names.
		String[] Filelist = new String[numOfFiles];
					
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if(listOfFiles[i].isFile()) 
			{
			files = listOfFiles[i].getName();
				if(files.endsWith(prefix.toLowerCase()) || files.endsWith(prefix.toUpperCase()))
				{
					Filelist[count]=files;
					count++;
				}
			}
		}

		//print fileList
		
		for(int count1=0; count1<Filelist.length; count1++)
			System.out.println(Filelist[count1]);
			
	}
	public void save_multi_diamensional_Array_To_File(String[][] Line,String File_Full_Directory)//appends data
	{

        FileReader inputStream = null;
        FileWriter outputStream = null;
		
		String Line_buffer="";
		
		//writer.write("HELLO\n");
		//System.out.println("Line.length: "+ Line.length);
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(File_Full_Directory,true));
			
			int x=0;
			int y=0;
			int elements_count=0;
			
			for(x=0; x<Line.length-1 && Line[x][y]!=null; x++)
			{
				Line_buffer=Line[x][0] + "------" + Line[x][1] + "------" + Line[x][2] + "------" + Line[x][3] + "------" + Line[x][4] + "------" + Line[x][5] + "------" + Line[x][6]; 

				//System.out.println(Line_buffer);
				writer.write(Line_buffer);
				//System.out.println();
				writer.write("\n");			//new row new line
				Line_buffer="";	
			}
		
			
		writer.close();
		}
		catch (IOException e)
        {
          System.out.println("IOException : " + e);
        }
    }
	public String[] getFileContents(String directory, String filename)
	{
		String path = directory + "\\" + filename;
		int maxLines = countLines(path);
		int count=0;
		String[] contents = new String[maxLines];
		
		for(count=0; count<maxLines; count++)
		{
			contents[count]=getFileLine(directory,filename,count);
		}
		return contents;
	}
        public ArrayList<String> readFile(String path)
        {
            ArrayList<String> buff = new ArrayList<String>();
            try(BufferedReader br = new BufferedReader(new FileReader(path))) 
            {
                String line = br.readLine();
                while (line != null) 
                {
                    buff.add(line);
                }
            } 
            catch (FileNotFoundException ex) 
            {
                System.out.println(ex.getMessage());
                Logger.getLogger(FileIOv2.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            return buff;
        }
        public String tail2( String path, int lines) 
        {
            File file = new File(path);
    java.io.RandomAccessFile fileHandler = null;
    try {
        fileHandler = 
            new java.io.RandomAccessFile( file, "r" );
        long fileLength = fileHandler.length() - 1;
        StringBuilder sb = new StringBuilder();
        int line = 0;

        for(long filePointer = fileLength; filePointer != -1; filePointer--){
            fileHandler.seek( filePointer );
            int readByte = fileHandler.readByte();

             if( readByte == 0xA ) {
                if (filePointer < fileLength) {
                    line = line + 1;
                }
            } else if( readByte == 0xD ) {
                if (filePointer < fileLength-1) {
                    line = line + 1;
                }
            }
            if (line >= lines) {
                break;
            }
            sb.append( ( char ) readByte );
        }

        String lastLine = sb.reverse().toString();
        return lastLine;
    } catch( java.io.FileNotFoundException e ) {
        e.printStackTrace();
        return null;
    } catch( java.io.IOException e ) {
        e.printStackTrace();
        return null;
    }
    finally {
        if (fileHandler != null )
            try {
                fileHandler.close();
            } catch (IOException e) {
            }
    }
}
        public String tail( String path ) 
        {
            File file = new File(path);
    RandomAccessFile fileHandler = null;
    try {
        fileHandler = new RandomAccessFile( file, "r" );
        long fileLength = fileHandler.length() - 1;
        StringBuilder sb = new StringBuilder();

        for(long filePointer = fileLength; filePointer != -1; filePointer--){
            fileHandler.seek( filePointer );
            int readByte = fileHandler.readByte();

            if( readByte == 0xA ) {
                if( filePointer == fileLength ) {
                    continue;
                }
                break;

            } else if( readByte == 0xD ) {
                if( filePointer == fileLength - 1 ) {
                    continue;
                }
                break;
            }

            sb.append( ( char ) readByte );
        }

        String lastLine = sb.reverse().toString();
        return lastLine;
    } catch( java.io.FileNotFoundException e ) {
        e.printStackTrace();
        return null;
    } catch( java.io.IOException e ) {
        e.printStackTrace();
        return null;
    } finally {
        if (fileHandler != null )
            try {
                fileHandler.close();
            } catch (IOException e) {
                /* ignore */
            }
    }
}
	public long fileSizeInKB(String path) //KB
	{
		File file = new File(path);
		long filesize = file.length();
		long filesizeInKB = filesize / 1024;
		//System.out.println("Size of File is: " + filesizeInKB + " KB");
		return filesizeInKB;
	}
	public long fileSizeInB(String path) //KB
	{
		File file = new File(path);
		long filesize = file.length();
		//System.out.println("Size of File is: " + filesize + " B");
		return filesize;
	}
	public void copyFile(File source, File dest) throws IOException 
	{
	 if(!dest.exists()) 
	 {
	  dest.createNewFile();
	 }
	 InputStream in = null;
	 OutputStream out = null;
	 try 
	 {
	  in = new FileInputStream(source);
	  out = new FileOutputStream(dest);
		
	  // Transfer bytes from in to out
	  byte[] buf = new byte[1024];
	  int len;
	  while ((len = in.read(buf)) > 0) 
	  {
		out.write(buf, 0, len);
	  }
	 }
	 finally 
	 {
	  if(in != null) 
	  {
	   in.close();
	  }
	  if(out != null) 
	  {
	   out.close();
	  }
	 }
	 
	 
	 
	}
	
        public void copyFile2(File sourceFile, File destFile) throws IOException //NIO. better memory MANAGMENT!
	{	
		 if(!destFile.exists()) 
		 {
			destFile.createNewFile();
		 }
                 else
                 {
                     System.out.println("File in Destination already exists");
                 }
		 
		 FileChannel source = null;
		 FileChannel destination = null;
		 try 
		 {
			 source = new FileInputStream(sourceFile).getChannel();
			 destination = new FileOutputStream(destFile).getChannel();
			 destination.transferFrom(source, 0, source.size());
		 }
		 finally 
		 {
		  if(source != null) 
		  {
			source.close();
		  }
		  if(destination != null) 
		  {
			destination.close();
		  }
		}
	}
        
	public void allowAndCheckPermissions(File file)
	{
		/*
		System.out.println("Is Execute allow : " + file.canExecute());
		System.out.println("Is Write allow : " + file.canWrite());
		System.out.println("Is Read allow : " + file.canRead());
		*/
		
		file.setExecutable(true);
		file.setReadable(true);
		file.setWritable(true);

		if(file.canExecute()==false || file.canWrite()==false || file.canRead()==false)
		{
			System.out.println("Is Execute allow : " + file.canExecute());
			System.out.println("Is Write allow : " + file.canWrite());
			System.out.println("Is Read allow : " + file.canRead());
		}
	}
}