
import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;

public class New {

    boolean isEmpty = true;
    
    Node firstNode; 

    
    void add(String s) {
        
        if (isEmpty) {
            if (s.length() == 1) {
           
            	firstNode = new Node(s.charAt(0), null, null);                   
           firstNode.end = true;                                            
    
           
           return;
            } else {
                
            	firstNode = new Node(s.charAt(0), null, null);                
        for (int i = 1; i < s.length(); ++i) {                          
        	firstNode.child = new Node(s.charAt(i), null, null);
           
                    firstNode = firstNode.child; }
             firstNode.end = true;                                
          }
        isEmpty = false;                                                    
       
        }

        Node nNode = firstNode;
       
        
        for (int i = 0; i < s.length();) {
        
        	int notFound = 0;                                       
        	if (nNode.next != null) {                                     
            
        		if (nNode.value != s.charAt(i)) {                           // roW
                    nNode = nNode.next;
                    ++notFound;
                } else {                                                           // COL
                    ++i;
                    nNode = nNode.child;
                    ++notFound;
                }
            }
            if (nNode.next == null && nNode.value == s.charAt(i) && notFound == 0) {
                ++i;
                nNode = nNode.child;
                ++notFound;
            }
            
            if (notFound == 0) {
            	
                nNode.next = new Node(s.charAt(i), null, null);
                    nNode = nNode.next;
            for (int j = i + 1; j < s.length(); ++j) {
                
            	nNode.child = new Node(s.charAt(j), null, null);
                    nNode = nNode.child;
                }
                
            nNode.end = true;
            
            
            break;
            }
        }

    }

  

    
    public static void main(String args[]) throws IOException {
        New Trieve = new New();
        
        Trieve.buildTrie(); //create trie from dict
        
        //input
        Scanner scan = new Scanner(System.in);

        
        // validate input
        if (args.length == 0) {
            System.out.println("Enter a  password or type quit");
            String input = scan.nextLine();
            while (!input.equalsIgnoreCase("quit")) {
                if (input.length() == 0) {
                    System.out.println("Yo Password or type quit dont make me say it again");
                    input = scan.nextLine();
                } 
                 else {
                  
                    System.out.println(Trieve.firstNode.value);

}
                }
            return;
       }
   }

// CheckPass
    boolean checkPass(String input) throws IOException {
        
    	
    	return false;
        }

    void buildTrie() throws IOException {
      
    	File here = new File(".");
    	
    	System.out.println(here.getAbsolutePath());
   BufferedReader read = new BufferedReader(new FileReader("dictionary.txt"));
      String line;
       
        while ((line = read.readLine()) != null) {
            add(line); 
        }
        read.close();
    }



    private class Node {
private Node next;
private Node child;
	
	private char value;
private boolean end = false;

        private Node(char letter, Node nextNode, Node childNode) {
        	this.value = letter;
        	
this.next = nextNode;
this.child = childNode;

    }
  }
}
