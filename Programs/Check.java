import java.io.*;
import java.util.*;

// Class : Check - Contains methods that prompt user to enter a text, checks it for spelling and lets user save the text in a file
public class Check 
{
	static int count=0;                            // count to count no of lines user entered
	static String[] line = new String[500];     
	 static Scanner myinput = new Scanner(System.in);
	 
	 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 /////// AddToDataBase method - lets user add a new word to database  

	public static void AddToDataBase(String word) throws IOException
	 {
			WriteFile file2 = new WriteFile ("DataBase.txt" , true);
			file2.WriteToFile(word);
			System.out.println("'" + word + "' added in database");
	 } 
	 
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////Save file method - To save text entered by user in a file/////
	 public static void SaveFile(String[] word) throws IOException
	 {
		 // Takes file name from users
	         	 System.out.println(" Enter file name");
				 String filename = myinput.next();
				 filename = filename +".txt";
				 WriteFile file3 = new WriteFile (filename , true); 
		// Saving text in that file entered by user
				for (int m=0 ; m<=count; m++)
				 file3.WriteToFile(word[m]);
				 System.out.println("done! file saved"); 		 
	 }
	 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////CheckText method - To check the input text/////
	 public void CheckText()
	 {
	     TrieTree tree = new TrieTree();
	     TrieNode rootNode = tree.createTree();
		 String[] searchword = new String[20];
		 String[] words;
		 
		 String choice;
		 try
		 {
			 //Reading database
			 Read file1 = new Read("DataBase.txt");
			 words = file1.OpenFile();

			// Creating trie tree out of database
			for (int i = 0; i < words.length; i++)
				{
					 tree.insertWord(rootNode, words[i]);
				}

		   
		// User starts entering the text
			 System.out.println("Enter the text ");
			 Scanner input = new Scanner(System.in);
		        
			       line[count] = input.nextLine();
			       
			       //Searchword contains words of the text line after removing punctuations
			       searchword = line[count].replace(".", "").replace(",", "").replace(")", "").replace("?", "").replace("!","").replace(":", "").replace("(", "").split(" ");
			    
			       // Making spell checker independent of case sensitivity
			       for(int c=0; c<searchword.length; c++)
			       {
			    	  searchword[c] = searchword[c].toLowerCase(); 
			       }
			       
			       
			       // Spell Checking
			       for(int i=0 ; i<searchword.length ; i++)
			       {
			    	   // Checking if each word of the text line is there in the database
			    	   if (!tree.find(rootNode, searchword[i])) // IF NOT THERE 
			    	   {
			    		   System.out.println("'" + searchword[i] + "' is not in database");
			    		  
			    		   // 3 Options given - Add that word to database , Suggest words for that word , and Ignore and write next line
			    		   System.out.println("Enter 'add' to add '" + searchword[i] + "' to database and enter 'suggest' to look for suggestions for '" + searchword[i] + "' or enter 'ignore' ");
			    		   choice = myinput.next();
			    		    
			    		   // If the User selects add - adds word to database and trie tree
			    		   if(choice.equalsIgnoreCase("add"))
			        		{
			    			   AddToDataBase(searchword[i]);
			        			tree.insertWord(rootNode,searchword[i]);
			        		}
			    		   
			    		   //If the User selects suggest - suggest words
			    		   // Then 2 Options given - replace or not
			    		   if (choice.equalsIgnoreCase("suggest"))
			    		   {
			    			   // Checks suggestion for that word
				        		tree.suggest (rootNode , searchword[i]);
				        		
				        		//To replace that word
				        		System.out.println("Enter 'replace' to replace '" + searchword[i] + "' or enter 'not' if you dont want to replace");
				        		choice = myinput.next();
				        		if(choice.equalsIgnoreCase("replace"))
				        		{
				        			//Enter word to replace this word
					    		   System.out.println("Enter word to replace '" + searchword[i] + "' with:");
					    		   String replace = myinput.next();
					    		   //Replacing it
					    		   line[count] = line[count].replace(searchword[i], replace);
					    		   System.out.println("'" + searchword[i] + "' is replaced ");
				        		}
			    		   }
			    	   }
			    	   
			       }
			       
			      
			       //2 Options to write further or to exit and save file
			       System.out.println("Enter 1 to write another text and 0 to save file and exit");
			       
			       int l = myinput.nextInt();
			       if(l==1)
			       {
			    	   count++;
			    	   //Writing another line
			    	   CheckText();
			       }
			       else
			       {
			    	   SaveFile(line);
			    	  System.exit(0);
			       }
		} 
		
		catch(IOException e){
			System.out.println(e.getMessage());
		}  
	 }
}
