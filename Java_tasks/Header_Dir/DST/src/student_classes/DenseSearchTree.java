package student_classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;




/**
 * A Binary Search Tree data-structure that maintains a count
 * of repetitive values (as nodes).
 * 
 * @author UMD CS Dept.
 *
 * @param <T>
 */
public class DenseSearchTree<T extends Comparable<T>> implements Iterable<T> {

	/* Inner class for Binary Tree Node.
	 * I give each node a value(T), Integer mult, and pointers left and 
	 * right(BTNode). I have two constructors for the node, one which passes 
	 * in the value, mult, and both pointers and one that only passes in the 
	 * value.
	 */
	class TNode {
		T	value;
		Integer mult;
		TNode left, right;

		TNode( T value, Integer multiplicity, TNode l, TNode r ) {
			this.value = value;
			this.mult = multiplicity;
			this.left = l;
			this.right = r;
		}

		TNode( T value ) {
			this( value, 1, null, null );
		}
	}

	//Instance variable
	protected TNode root = null;

	//Constructor for the class DST
	public DenseSearchTree(){
		root = null;
	}

	/*
	 * My add method has to account for the placement of the node depending
	 * on the value. First, if the value passed in is null, I don't do anything.
	 * Then if the current tree is empty, I create a new node with passed in
	 * value and make it the root of the current tree. Otherwise I use a helper
	 * function which passes in the root of the current tree and the value I 
	 * want to assign the new node. The helper method, addHelper, uses 
	 * recursion to add the new node. I use compareTo to compare the passed in
	 * value with the value of the currentNode. I continue the recursion, using
	 * compareTo to find a suitable place to put the node. If the value is
	 * greater than the current node value, I iterate right, otherwise I iterate
	 * left. I continue this until I reach an empty node, in which I add the new
	 * node with the passed in value to this appropriate location. If I reach
	 * a node with the same value as the value passed in, I increment it's 
	 * multiplicity.
	 * 
	 */
	public void add(T value){
		if(value == null){
			return;
		} 
		if(root == null){
			root = new TNode(value);
		} else{
			this.addHelper(root, value);
		}
	}
	private void addHelper(TNode currentNode, T value){
		if(currentNode == null){
			currentNode = new TNode(value);
		} else if(value.compareTo(currentNode.value)>0){
			if(currentNode.right == null){
				currentNode.right = new TNode(value);
			} else{
				addHelper(currentNode.right, value);
			}
		} else if(value.compareTo(currentNode.value)<0){
			if(currentNode.left == null){
				currentNode.left = new TNode(value);
			} else{
				addHelper(currentNode.left, value);
			}
		} else{
			//If value == currentNode.value
			currentNode.mult = currentNode.mult + 1;
		}
	}

	/*
	 * I first check if the passed in value is null or if the tree is empty,
	 * in which case I return false. I use a helper method, containsHelper, and
	 * I pass in the root of the current tree and the value we are looking for.
	 * In containsHelper, I compare the passed in value and the value of the
	 * current node to see if it is greater than or less than. I follow the 
	 * natural order of the BST and make recursive calls with the appropriate
	 * child of the current node in order to get closer to the value we are 
	 * looking for. If the value is not in the tree, the current Node will 
	 * recursively iterate to a null and the method returns false. Otherwise, it
	 * will find the value and it returns true.
	 */
	public boolean contains(T ele){
		if(ele == null){
			return false;
		}
		if(root == null){
			return false;
		} 
		return containsHelper(root, ele);

	}
	private boolean containsHelper(TNode currentNode, T valueIn){
		if(currentNode == null){
			//Value is never reached, meaning it is not in the tree. I return
			//false.
			return false;
		}
		if(valueIn.compareTo(currentNode.value) > 0){
			//The valueIn > currentNode.value, so we recursively call the 
			//containsHelper method with currentNode.right, getting closer to 
			//the value we are looking for.
			return containsHelper(currentNode.right, valueIn);
		} else if(valueIn.compareTo(currentNode.value) < 0){
			//The valueIn < currentNode.value, so we recursively call the 
			//containsHelper method with currentNode.left, getting closer to the
			//value we are looking for.
			return containsHelper(currentNode.left, valueIn);
		} else{
			//valueIn.compareTo(currentNode.value) = 0. ValueIn is found in 
			//a node, return true.
			return true;
		}
	}


	/*
	 * If the DST contains the value we are looking for, I use the same logic 
	 * as the contains method to find the node with the value we looking for.
	 * Once it is found, I return its multiplicity. Else, the value is not
	 * in the tree and so I return 0.
	 */
	public int count(T value){
		//If value is null
		if(value == null){
			return 0;
		}
		if(this.contains(value)){
			//If value is in a node, return the node's multiplicity.
			return countHelper(root, value);
		} else{
			//Value is not in the tree
			return 0;
		}
	}
	private int countHelper(TNode currentNode, T valueIn){
		if(valueIn.compareTo(currentNode.value) > 0){
			//The valueIn > currentNode.value, so we recursively call the 
			//containsHelper method with currentNode.right, getting closer to 
			//the value we are looking for.
			return countHelper(currentNode.right, valueIn);
		} else if(valueIn.compareTo(currentNode.value) < 0){
			//The valueIn < currentNode.value, so we recursively call the 
			//containsHelper method with currentNode.left, getting closer to the
			//value we are looking for.
			return countHelper(currentNode.left, valueIn);
		} else{
			//valueIn.compareTo(currentNode.value) = 0. ValueIn is found in 
			//a node, return the current node's multiplicity.
			return ((int) currentNode.mult);
		}
	}

	/*
	 * I return the max value in the tree by returning the value of the node
	 * in the lower right corner of the tree.
	 */
	public T getMax(){
		if(root == null){
			throw new IllegalStateException();
		} else{
			return getMaxHelper(root);
		}
	}
	private T getMaxHelper(TNode currentNode){
		if(currentNode.right == null){
			return currentNode.value;
		} else{
			return getMaxHelper(currentNode.right);
		}
	}

	/*
	 * I return the min value in the tree by returning the value of the node
	 * in the lower left corner of the tree.
	 */
	public T getMin(){
		if(root == null){
			throw new IllegalStateException();
		} else{
			return getMinHelper(root);
		}
	}
	private T getMinHelper(TNode currentNode){
		if(currentNode.left == null){
			return currentNode.value;
		} else{
			return getMinHelper(currentNode.left);
		}
	}


	/*
	 * Remove one occurrence of value from the tree, the first one in sort
	 * order. First I check to see if the value passed in is null or the 
	 * tree is empty, in which case I don't do anything. I use a remove helper
	 * method that passes in root and value to actually perform the remove.
	 * In the helper method, if currentNode ever reaches null, the value passed
	 * in is not in the tree and I do not make any modification. I iterate 
	 * through a path, following the natural order, using compareTo and 
	 * recursion, to find the passed in value. When the value is found, I check 
	 * if the current node has a right node or a left node. If it doesn't, I 
	 * simply return null, pointing the previous node to null instead of 
	 * the current Node, removing the current node from the tree. If it does 
	 * have a right node, I make the current Node's value to the value 
	 * of the minimum value of its right sub tree. Then I call the remove helper
	 * function with the minimum value of the current Node's right subtree in
	 * order to remove it from the tree. This successfully performs a the remove
	 * of the current Node while maintaining the natural order. If the current
	 * node does not have a right node child but has a left node child, I simply
	 * make the pointer of the previous node point to the left child instead of
	 * the node we want removed. This successfully performs the remove and 
	 * maintains the natural order. Finally, if the the multiplicity of the node
	 * we want removed is greater than one, it short circuits without doing the
	 * above steps, and just decrements the multiplicity.
	 */
	public void remove(T value){
		if(value == null){
			return;
		}
		if(root == null){
			return;
		} 
		remove_aux(root, value);

	}
	private TNode remove_aux(TNode currentNode, T valueIn){
		if(currentNode == null){
			//Value is never reached, meaning it is not in the tree. Nothing
			//is modified in the tree. Return the same tree.
			return currentNode;
		}
		if(valueIn.compareTo(currentNode.value) > 0){
			//The valueIn > currentNode.value, so we recursively call the 
			//remove_aux method with currentNode.right, getting closer to the
			//value we are looking for.
			currentNode.right = remove_aux(currentNode.right, valueIn);
			return currentNode;
		} else if(valueIn.compareTo(currentNode.value) < 0){
			//The valueIn < currentNode.value, so we recursively call the 
			//remove_aux method with currentNode.left, getting closer to the
			//value we are looking for.
			currentNode.left = remove_aux(currentNode.left, valueIn);
			return currentNode;
		} else{
			//valueIn is found in a node.
			if(currentNode.mult > 1){
				currentNode.mult = currentNode.mult - 1;
				return currentNode;
			}
			if(!(currentNode.right == null)){
				//If the node to be removed is not a leaf, I find the smallest
				//value from the currentNode.right.left branch. I give this
				//minimum value to the node I want to remove. Then I 
				//remove the minimum value node I had taken the value from.
				//Finally the remove has been completed so I return the
				//currentNode so the recursion above can be completed and the 
				//modified tree returned.
				currentNode.value = treeLeaf(currentNode.right);
				currentNode.right = remove_aux(currentNode.right, 
						currentNode.value);
				return currentNode;
			} else if(!(currentNode.left == null)){
				//The node to be removed, currentNode, has a left branch. I 
				//make the right pointer of the previous node point to the
				//currentNode.left, ultimately removing the currentNode.
				return currentNode.left;
			}
			//The node to be removed does not branch out. So, I just make it 
			//null.
			return null;
		}
	}
	private T treeLeaf(TNode currentNode){
		//I use this method to find the minimum value on this tree. I keep
		//iterating left until I get to the minimum value leaf of this tree.
		while(!(currentNode.left == null)){
			currentNode = currentNode.left;
		}
		//I return the minimum value of the tree.
		return currentNode.value;
	}



	/*
	 * I use a helper method that passes in the current tree's root. The helper
	 * method recursively gets the size by traversing through every node and 
	 * adding the multiplicity of each node. When null is reached, 0 is returned
	 * so the size is not incremented.
	 */
	public int size(){
		return sizeHelper(this.root);
	}
	public int sizeHelper(TNode currentNode){
		if(currentNode == null){
			return 0;
		} else if(currentNode.mult == 1){
			return (1 + sizeHelper(currentNode.left) +
					sizeHelper(currentNode.right));
		} else{
			//currentNode.mult > 1
			return (currentNode.mult + sizeHelper(currentNode.left) +
					sizeHelper(currentNode.right));
		}
	}

	/*
	 * Return set representation of tree. I create a new set and I use a helper
	 * method that traverses through the entire tree, adding the value of each
	 * node to the set.
	 */
	public Set<T> asSet(){
		//Returns set with all values in tree, no duplicates.
		Set<T> set = new HashSet<T>();
		setHelper(root, set);
		return set;
	}
	public void setHelper(TNode currentNode, Set<T> set){
		if(currentNode != null){
			set.add(currentNode.value);
			setHelper(currentNode.left, set);
			setHelper(currentNode.right, set);
		}
	}

	/*
	 * Iterator(non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 * I add all the values of the DST to an ArrayList, accounting for 
	 * duplicates by looking at the multiplicity of each node. I then use simple
	 * iterator logic for next and hasNext.
	 */
	public Iterator<T> iterator() {
		return new theIterator(root);
	}

	public class theIterator implements Iterator<T>{
		private TNode root;
		private int indexCounter;
		private ArrayList<TNode> list;

		public theIterator(TNode node){
			indexCounter = 0;
			root = node;
			list = new ArrayList<TNode>();
			listAdder(root);
		}
		private void listAdder(TNode node){
			if(node == null){
				return;
			}
			//In order traversal to add nodes on tree in order from
			//least to greatest to list.
			listAdder(node.left);
			for(int i = 1; i <= node.mult; i++){
				list.add(node);
			}
			listAdder(node.right);
		}

		@Override
		public boolean hasNext(){
			boolean x = list.size() == indexCounter;
			return (!x);
		}

		@Override
		public T next(){
			if(hasNext()){
				return list.get(indexCounter++).value;
			} else{
				throw new RuntimeException();
			}
		}
		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

}