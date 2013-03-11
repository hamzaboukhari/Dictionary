package dictionary;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/*
 * Binary search tree based implementation of the Dictionary
 * interface. The nodes of the tree are ordered by an associated key-attribute
 * key of type K, such that every node's left subtree contains only nodes 
 * whose key-attributes are less than key, and every node's right subtree
 * contains only nodes whose key-attributes are greater than key. A
 * linear order is defined on keys through the Comparable interface.
 * Duplicate keys are not permitted.
 */
public class BinarySearchTree<K extends Comparable<? super K>, V> implements
        Dictionary<K, V> {

	private BinarySearchTreeEntry<K, V> root;
	private int numNodes = 0;
	private int numMods = 0;
	
	public V getRootElem() throws NoSuchElementException {
		if (root == null) { 
			throw new NoSuchElementException();
		} else {
			return root.getValue();
		}
	}
	
	public boolean search(BinarySearchTreeEntry<K, V> node, K searchKey) {
		if (node == null) {
			return false;
		} else if (searchKey.compareTo(node.getKey()) == 0) {
			return true;
		} else if (searchKey.compareTo(node.getKey()) < 0) {
			return search(node.getLeft(), searchKey);
		} else {
			return search(node.getRight(), searchKey);
		}
	}
	
	public boolean contains(K searchKey) {
		return search(root, searchKey);
	}
	
	@Override
	public void clear() {
		root = null;
	}
	
	private BinarySearchTreeEntry<K, V> getElem(
			BinarySearchTreeEntry<K, V> node, K searchKey) {
		if (node != null) {
			K key = node.getKey();
			if (key == searchKey) {
				return node;
			} else if (searchKey.compareTo(key) < 0) {
				return getElem(node.getLeft(), searchKey);
			} else {
				return getElem(node.getRight(), searchKey);
			}
		} else {
			return null;
		}
	}

	@Override
	public V get(K key) throws NoSuchElementException {
		BinarySearchTreeEntry<K, V> elem = getElem(root, key);
		if (elem == null) {numNodes--;
			throw new NoSuchElementException();
		} else {
			return elem.getValue();
		}
	}

	@Override
	public boolean isEmpty() {
		return numNodes == 0;
	}
	
	private BinarySearchTreeEntry<K, V> putElem(
			BinarySearchTreeEntry<K, V> node, K key, V newValue) {
		if (node == null) {
			BinarySearchTreeEntry<K, V> newNode = new BinarySearchTreeEntry<K, V>(key, newValue, null, null);
			node = newNode;
			numNodes++;
			numMods++;
		} else if (key.compareTo(node.getKey()) == 0) {
			node.setValue(newValue);
		} else if (key.compareTo(node.getKey()) < 0) {
			node.setLeft(putElem(node.getLeft(), key, newValue));
		} else {
			node.setRight(putElem(node.getRight(), key, newValue));
		}
		return node;
	}

	@Override
	public void put(K key, V newValue) {
		root = putElem(root, key, newValue);
		
	}

	private BinarySearchTreeEntry<K, V> removeElem(
			BinarySearchTreeEntry<K, V> node, K key) {
		if (node == null) {
			throw new NoSuchElementException();
		} else if (node.getKey().compareTo(key) == 0) {
			node = removeNode(node);
			numNodes--;
			numMods++;
		} else if (node.getKey().compareTo(key) > 0) {
			node.setLeft(removeElem(node.getLeft(), key));
		} else {
			node.setRight(removeElem(node.getRight(), key));
		}
		return node;
	}
	
	private BinarySearchTreeEntry<K, V> removeNode(
			BinarySearchTreeEntry<K, V> node) {
		if (node.getLeft() == null && node.getRight() == null) {
			return null;
		} else if (node.getLeft() != null && node.getRight() == null) {
			return node.getLeft();
		} else if (node.getLeft() == null && node.getRight() != null) {
			return node.getRight();
		} else {
			BinarySearchTreeEntry<K, V> replacementNode = findLeftMost(node.getRight());
			BinarySearchTreeEntry<K, V> newRight = deleteLeftMost(node.getRight());
			replacementNode.setRight(newRight);
			replacementNode.setLeft(node.getLeft());
			return replacementNode;
		}
	}
	
	private BinarySearchTreeEntry<K, V> findLeftMost(BinarySearchTreeEntry<K, V> node) {
		if (node == null) {
			return null;
		} if (node.getLeft() == null) {
			return node;
		} else {
			return findLeftMost(node.getLeft());
		}
	}
	
	private BinarySearchTreeEntry<K, V> deleteLeftMost(BinarySearchTreeEntry<K, V> node) {
		if (node.getLeft() == null) {
			return node.getRight();
		} else {
			BinarySearchTreeEntry<K, V> newChild = deleteLeftMost(node.getLeft());
			node.setLeft(newChild);
			return node;
		}
	}
	
	@Override
	public void remove(K searchKey) throws NoSuchElementException {
		root = removeElem(root, searchKey);
		
	}

	@Override
	public int size() {
		return numNodes;
	}

	@Override
	public Iterator<DictionaryEntry<K, V>> iterator() {
		return new TreeIterator();
	}
	
	public class TreeIterator implements Iterator<dictionary.DictionaryEntry<K, V>> {

		private BinarySearchTreeEntry<K, V> result;
		private BinarySearchTreeEntry<K, V> current;
		private Stack<BinarySearchTreeEntry<K, V>> workList;
		private int currentNumMods;
		
		private TreeIterator() {
			result = null;
			current = root;
			workList = new Stack<BinarySearchTreeEntry<K, V>>();
			currentNumMods = numMods;
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}
		
		@Override
		public DictionaryEntry<K, V> next() {
			if (currentNumMods != numMods) {
				throw new ConcurrentModificationException();
			}
			
			while (current != null) {
				workList.push(current);
				current = current.getLeft();
			} 
			
			if (!workList.empty()) {
				result = workList.pop();
				current = result.getRight();
				return result;  
			} else {
				throw new UnsupportedOperationException();
			}
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
