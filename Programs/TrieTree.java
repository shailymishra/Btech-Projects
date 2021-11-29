
// Class : Trie Tree
// It contains method such as creating tree, insertion , searching , suggesting  word, printing
public class TrieTree
{

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////Create tree method - creates root node of the tree///////////////////
	 TrieNode createTree()
	 {
	     return(new TrieNode('\0'));
	 }
	
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////Insert Word method - inserts a word in trie tree/////////////////////
	  void insertWord(TrieNode root, String word)
	 {
	        int l = word.length();
	        char[] letters = word.toCharArray();
	        TrieNode curNode = root;
	        
	        for (int i = 0; i < l; i++)
	        {
	        	// Adds all letters of word
	        	int position = letters[i]-97;
	            if (curNode.next[position] == null)
	                curNode.next[position] = new TrieNode(letters[i]);
	            curNode = curNode.next[position];
	        }
	        //After all letters are added, node containing last letter is a fullword node
	        curNode.fullWord=true;
	 }
	 

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////// Find method - finds if a word if there in a trie tree or not///////////////////
	  boolean find(TrieNode root, String word)
	    {
	        char[] letters = word.toCharArray();
	        int l = letters.length;
	        TrieNode curNode = root;
	        
	        int i;
	        // Checks if the first (l-1) nodes are present or not
	        for (i = 0; i < l; i++)
	        {
	            if (curNode == null) // If not there returns false
	                return false;
	            curNode = curNode.next[letters[i]-97];
	        }
	        
	        // Checks if the last node i.e. lth node is there or not
	        if (i == l && curNode == null)  //If not there returns false
	            return false;
	        
	        //if all the nodes are present but the last node is not a full word field then return false
	        if (curNode != null && !curNode.fullWord)
	            return false;
	        
	        return true;
	    }
	 
	 
//////////////////////////////////////////////////////////////////////////////////////////////////
////////////// printTree method - to print all the words in tree//////////////////
	  void printTree(TrieNode root, int level, char[] branch)
	    {
		  // Traverses through all the nodes
		  // Prints all the words
	        if (root == null)
	            return;
	        
	        for (int i = 0; i < root.next.length; i++)
	        {
	            branch[level] = root.letter;
	            printTree(root.next[i], level+1, branch);    
	        }
	        
	        if (root.fullWord)
	        {
	            for (int j = 1; j <= level; j++)
	                System.out.print(branch[j]);
	            System.out.println();
	        }
	    }
	    
//////////////////////////////////////////////////////////////////////////////////////////////////
////////////// printSuggest - to print suggestion of word///////////////////////////////
	  void printSuggest(TrieNode root, int level, char[] branch, String searchword)
	    {
		  
	        if (root == null)
	            return;
	        
	        for (int i = 0; i < root.next.length; i++)
	        {
	            branch[level] = root.letter;
	            printSuggest(root.next[i], level+1, branch, searchword);    
	        }
	        
	        if (root.fullWord)
	        {
	        	System.out.print(searchword);
	            for (int j = 1; j <= level; j++)
	                System.out.print(branch[j]);
	            System.out.println();
	        }
	    }
	 
//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////// suggest - to give suggestion//////////////////////////////////////
	  void suggest ( TrieNode root , String word)
	 {	 
		 char[] letters = word.toCharArray();
	     int l = letters.length;
	     TrieNode curNode = root;
	     
	  // if the prefix of word is there in trie tree then only gives suggestion
	// words with the same prefix are printed
	     int i=0;
	     for ( i=0 ; i<l ; i++)
	     {
	    	 if(curNode!=null)
	    	 curNode = curNode.next[letters[i]-97];
	     }
	     if (curNode == null)
	    	 System.out.println( " No suggestion for : " + word);
	     else
	     {
	    	 System.out.println(" Suggested words for : " + word );
	    	 char[] branch = new char[50];
	    	 printSuggest( curNode , 0 , branch, word );
	     }

	 }
	  
}
