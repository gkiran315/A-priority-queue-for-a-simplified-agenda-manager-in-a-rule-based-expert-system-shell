// **************************************************************************************************************/
// Author  : Kiran Patrudu G
// Problem : A priority queue for a simplified agenda manager
//           in a rule-based expert system shell
// Course  : Analysis of Algorithms
// **************************************************************************************************************/

import java.io.*;
import java.lang.*;
import java.util.*;
import java.net.*;

public class AgendaMgr {
	
	
	/* Operations performed by the Agenda Manager ::
	 * 
	 * BuildQueue(): builds a priority queue (heap) from an unsorted array of rules with priorities
     * Heapify(): used by BuildQueue to maintain the heap properly
     * ExtractMax(): returns the rule with the highest priority
     * Insert_into_Heap(): adds a new rule to the queue
     * Delete_from_Heap(): removes a rule from the queue	 
	 *  
	 */

	// Variables used in the program
		 public String[] input_String;
		 long Start_time, End_time;	 
		 public int arr_size;
		 public int Execution_cycle = 0;
	
	
	// Associated array lists used in the program
 	 ArrayList<Integer> input_list 	= new ArrayList<Integer>(); 
	 ArrayList<String>  list_of_names = new ArrayList<String>(); 
	 ArrayList<Integer> insert_input_list = new ArrayList<Integer>(); 
	 ArrayList<String>  insert_list_of_names = new ArrayList<String>(); 
     
	 public static void main(String[] args) 
	 {
	    	    //creating a new object to class AgendaMgr
		  		AgendaMgr A = new AgendaMgr();
	 }
	
    
	 //Creating a Constructor 
 	 public AgendaMgr(){
	 
 	 int name_count=1;
	 int count=1;
	 
	 //To read the input file
	 try
	 {
	 		read_file();
	 }
	 
	 catch(Exception e)
	 {
	 		System.out.println("Exception caught!! Please check the input file!!");
	 }
	 
	 input_list.add(-1);
	 list_of_names.add("Head");
	 
	 for (int i=0;i<input_String.length;i++)
	 {
	  
	 	// To check the format of the given input File 
		 
		 /*
		 if ((input_String[i].isEmpty()))
		 { 
			 System.out.println("....Input format provided in the given File is incorrect \n"+"Please provide the Input in the correct Format");
		     System.exit(1);
		 }
		 */
		 
		if ((!(input_String[i].isEmpty())) && ((input_String[i].indexOf('(')==-1) && (input_String[i].indexOf(')')==-1)))
		{ 
			System.out.println("Input format provided in the given File is incorrect \n"+"Please provide the Input in the correct Format");
		    System.exit(1);
		}
	 	
		if (!(input_String[i].isEmpty()))
		{
			String[] parsed_array = input_String[i].split("[(),]+");

			for (int j=0;j<parsed_array.length;j++)
			{	
				parsed_array[j].trim();
				
				//D:\Softwares\Java Workspace\test2.txt
			
				if ((!(parsed_array[j].isEmpty())) && (!(parsed_array[j].equals(" "))))
				{
					if (isInteger(parsed_array[j])) 
					{
						if (i==0) 	
							input_list.add((input_list.size()),(Integer.parseInt((parsed_array[j].trim()))));		
						else 
						insert_input_list.add(Integer.parseInt((parsed_array[j].trim())));
					}
					else
					{		

						if (i==0) 
							list_of_names.add((list_of_names.size()),parsed_array[j]);	
						else 
							insert_list_of_names.add(parsed_array[j]);	
					}
				}
				
			}
	 
			//System.out.println("Rules added to the queue are: ");
			//System.out.println("\n"+input_String[i]+" ----> are the new rules added to the queue(heap) \n");		
			
			if ((i==0) && (Execution_cycle<30))
			{
							
				// Building the Queue(Heap)
				BuildQueue();
				ExtractMax();
			}
			else
			{
				// Inserting into the Queue(heap)
				if(Execution_cycle<30)
				{
					for(int k=0;k<insert_input_list.size();k++)
					{	
						Insert_into_Heap(insert_list_of_names.get(k),insert_input_list.get(k));				
					}
					insert_input_list.clear();
					insert_list_of_names.clear();
					ExtractMax();
				}
			}	 
		}	
	 }
	 
	 // Extracting the maximum element after reading the input file
	 while ((input_list.size() >1) && (Execution_cycle<30))
	 {	
		ExtractMax();
	 }
		
	 // To Display the Elapsed time	
	 End_time = System.currentTimeMillis();
	 System.out.println("The current inference will be terminated after "+Execution_cycle+" cycles");
	 double time_diff = End_time - Start_time;
	 double elapsed_time = time_diff/1000;
	 System.out.println("Time taken to execute "+ Execution_cycle +" cycles is: "+elapsed_time+" Secs");
	 System.out.println("\n\n=================================End of Execution=================================");
	 
	}

	//Function to build the Queue(heap) 
	public void BuildQueue()
	{
		
		arr_size = input_list.size()-1;
	 	for (int i=((input_list.size())/2); i>0; i--)
	 	{		 
	 	     Heapify(i);
	 	}	
	}
	
	//Function to fetch the maximum value from the Queue(heap)
	public void ExtractMax()
	{
		int max_value=0;
		String max_name = " ";
		if (input_list.size() >= 1) 
		{
			
			max_value = input_list.get(1);
			max_name = list_of_names.get(1);
			
			Execution_cycle++;
			System.out.println("\n********* Current Rule in execution : Rule"+Execution_cycle+" *********");
			System.out.println("\n Activated rules in the Agenda Manager are : \n");
			
		/*	for (int i=1;i<input_list.size();i++)
			{
				System.out.print("("+list_of_names.get(i)+","+input_list.get(i)+") ");
				if (i<(input_list.size()-1))
					System.out.print(", ");
			} */
			
			for (int i=(input_list.size()-1);i>0;i--)
			{
				System.out.print("("+list_of_names.get(i)+","+input_list.get(i)+") ");
				//if (i<(input_list.size()-1))
				if(i>1)
					System.out.print(", ");
			}
					
			input_list.set(1,input_list.get((input_list.size()-1)));
			list_of_names.set(1,list_of_names.get((list_of_names.size()-1)));
			arr_size = Delete_from_Heap() ;
			Heapify(1);
		}
		
		System.out.println("");	
		//System.out.println("\n");		
		System.out.println(" ");		
		System.out.println("Rule which is executed and removed from the heap : "+max_name);	
		System.out.println("\n\n\n===================================================================================");
		System.out.println(" ");
		System.out.println("\n");		

	} 
	
	//function to build the heap
	public void Heapify(int i)
	{
	
		int large_no = i;
		int left=2*i;
		int right=(2*i)+1;
		String temp_name= " ";
		int temp = 0;
			
		if (left <=arr_size)
		{
			if (input_list.get(left)>input_list.get(i))
			{
				 large_no = left;
			}
		}
		 
		if (right <=arr_size) 
		{
		   if (input_list.get(right)> input_list.get(large_no)) 
		   {
			 	large_no = right;
		   }
		}	 
		 
		if (large_no != i)
		{
			temp = input_list.get(large_no);
			temp_name = list_of_names.get(large_no);
			input_list.set(large_no,input_list.get(i));
			list_of_names.set(large_no,list_of_names.get(i));
			input_list.set(i,temp);		
			list_of_names.set(i,temp_name);
			Heapify(large_no);
		}
	}
   
	//function to insert element into the heap
	public void Insert_into_Heap(String element_name, int element)
	{
		int i = input_list.size();
		
		//System.out.println("$$$$$$$$$$$$$$$$$");
		//System.out.println(i);
		//System.out.println(element);
		
		System.out.println("("+element_name+", "+element+") ----> is the new rule added to the queue(heap) \n");
		
		while ((input_list.get(i/2) < element) && (i>1))
		{	
			if (i==input_list.size())
			{		
				input_list.add(i,input_list.get(i/2));
				list_of_names.add(i,list_of_names.get(i/2));
			}
			else
			{
				input_list.set(i,input_list.get(i/2));
				list_of_names.set(i,list_of_names.get(i/2));
			}
			i=i/2;
		}	
		
		if (i==input_list.size())
		{		
			input_list.add(i,element);
			list_of_names.add(i,element_name);
		}
		else
		{
			input_list.set(i,element);
			list_of_names.set(i,element_name);
		}
		arr_size++;
	}
	
	 //function to remove an element from the heap
	 public int Delete_from_Heap()
	 {
		
		input_list.remove((input_list.size()-1));
		list_of_names.remove((list_of_names.size()-1));
		int newsize = input_list.size()-1;
	 	return newsize;
	 } 
	 
	 // function to read input file
	 public void read_file() throws Exception
	 {
	 
	 	Scanner scan = new Scanner (System.in);
		
		// Input to be provided by the user
		System.out.println("Please provide the complete path of the file you want to check:");
		String Filepath = scan.nextLine();
		Start_time = System.currentTimeMillis();
		
		File file_check =new File(Filepath);
		
		// Existing file check
		if (!(file_check.exists()))
		{
			System.out.println("Please cross-check the given path.. \n"+"File can not be found");
			System.exit(2);
		}
		
		FileInputStream FIStream	= new FileInputStream(Filepath);
  		FileReader fileReader = new FileReader(Filepath);
      BufferedReader File_read_eachLine = new BufferedReader(fileReader);
      List<String> lines = new ArrayList<String>();
  
  		String strLine;
		
  		//To Read each Line of the File
  		while ((strLine = File_read_eachLine.readLine()) != null)   {
		   lines.add(strLine);		  
  		}
		
  		//To Close the File Input Stream
  		File_read_eachLine.close();
		
		input_String = lines.toArray(new String[lines.size()]);		 
	}
    
   // To differentiate between the Priority Value & Priority Name	
	public boolean isInteger( String element )
	{
 	 	try
  	 	{
 	 		Integer.parseInt( (element.trim()) );
 	 		return true;
  	 	}
 	 	catch( Exception e)
 	 	{
 	 		return false;
 	 	}
	}
  
	 public static void throwException(String message) throws Exception 
	 {
        throw new Exception(message);
     }
	 
   
}  

