package project4;

/*********************************************
 * Nodes that are used in a double LinkedList
 * 
 * @author Gage Vander Clay
 * @version April 13, 2014
 *********************************************/
public class Node<E> {

	/** Data that will be held in the node */
	public E data;

	/** Node that comes after the current node in the LinkedList */
	public Node<E> next;

	/** Node that comes before the current node in the LinkedList */
	public Node<E> prev;

	/**********************************************
	 * Constructor that defines a node
	 * 
	 * @param data
	 *            data contained in the node
	 * @param next
	 *            next node in the LinkedList
	 * @param prev
	 *            previous node in the LinkedList
	 **********************************************/
	public Node(E data, Node<E> next, Node<E> prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
	}

	/*****************************************
	 * Constructor that creates an empty node
	 *****************************************/
	public Node() {
	}

	/********************************************
	 * Gets the data that is contained in a node
	 * 
	 * @return data that is in the node
	 ********************************************/
	public E getData() {
		return data;
	}

	/*****************************************
	 * Sets the data in the node
	 * 
	 * @param data
	 *            data being put into a node
	 *****************************************/
	public void setData(E data) {
		this.data = data;
	}

	/***************************************
	 * Gets the next node in the LinkedList
	 * 
	 * @return next node in the LinkedList
	 ***************************************/
	public Node<E> getNext() {
		return next;
	}

	/*****************************************************
	 * Sets the next node in the LinkedList
	 * 
	 * @param next
	 *            Node that will be set as the next node
	 *****************************************************/
	public void setNext(Node<E> next) {
		this.next = next;
	}

	/***************************************
	 * Gets the previous node in the LinkedList
	 * 
	 * @return previous node in the LinkedList
	 ***************************************/
	public Node<E> getPrev() {
		return prev;
	}

	/*****************************************************
	 * Sets the previous node in the LinkedList
	 * 
	 * @param prev
	 *            Node that will be set as the next node
	 *****************************************************/
	public void setPrev(Node<E> prev) {
		this.prev = prev;
	}

}
