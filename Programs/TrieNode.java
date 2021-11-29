
//Class : Trie Node -  Node of the trie tree
public class TrieNode
{
	// Data field
	char letter;          //letter to be stored in TrieNode
	TrieNode[] next;      // links to next 26 nodes
	boolean fullWord;    // fullword field
	
	
	// Constructor of trie node
	TrieNode(char letter )
    {
        this.letter = letter;
        next = new TrieNode[26];
        fullWord = false;       // initial will be false because it will be true only when a word is completed at that node
    }

	
}
