package project4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*******************************************************
 * Double LinkedList that has multiple forms of editing
 * 
 * @author Gage Vander Clay
 * @version April 13, 2014
 *******************************************************/
public class LinkedList<E> {
	/** Top node in the LinkedList */
	private Node<E> top;

	/** Current node that is being pointed at in the LinkedList */
	private Node<E> current;

	/** Boolean that says whether the current list is saved */
	private boolean isSaved;

	/** int that is used when a file is cut or pasted */
	private int index;

	/**********************************************
	 * Constructor that defines a blank LinkedList
	 **********************************************/
	public LinkedList() {
		this.top = null;
		this.current = top;
		this.isSaved = true;
	}

	/***************************************
	 * Inserts data before the current line
	 * 
	 * @param data
	 *            data being input
	 ***************************************/
	public void insertBeforeCurrent(E data) {
		Node<E> temp = new Node<E>(data, null, null);

		// no list
		if (top == null) {
			top = temp;
		}

		// if the current node is at the top
		else if (current == top) {
			temp.setNext(current);
			current.setPrev(temp);
			top = temp;
		}

		else {
			temp.setNext(current);
			temp.setPrev(current.getPrev());
			current.getPrev().setNext(temp);
			current.setPrev(temp);

		}
		current = temp;
		isSaved = false;
	}

	/**************************************
	 * Inserts data after the current line
	 * 
	 * @param data
	 *            data being input
	 **************************************/
	public void insertAfterCurrent(E data) {
		Node<E> temp = new Node<E>(data, null, null);

		// no list
		if (top == null) {
			top = temp;
		}

		// if there is nothing after the current line
		else if (current.getNext() == null) {
			temp.setPrev(current);
			current.setNext(temp);
		} else {
			temp.setPrev(current);
			temp.setNext(current.getNext());
			current.setNext(temp);
			temp.getNext().setPrev(temp);
		}
		current = temp;
		isSaved = false;
	}

	/************************************************
	 * Inserts after the last line in the LinkedList
	 * 
	 * @param data
	 ************************************************/
	public void insertAfterLast(E data) {
		Node<E> temp = new Node<E>(data, null, null);

		// if the list is empty
		if (top == null) {
			top = temp;
		} else {
			Node<E> position = top;
			while (position.getNext() != null) {
				position = position.getNext();
			}
			temp.setPrev(position);

			position.setNext(temp);
		}
		current = temp;
		isSaved = false;
	}

	/******************************
	 * Moves the current line down
	 ******************************/
	public void moveCurrentDown() {

		// the list is empty
		if (top == null) {

		} else if (current.getNext() != null) {
			current = current.getNext();
		}
	}

	/****************************
	 * Moves the current line up
	 ****************************/
	public void moveCurrentUp() {

		// the list is empty
		if (top == null) {

		}
		if (current.getPrev() != null) {
			current = current.getPrev();
		}
	}

	/***************************
	 * Removes the current line
	 ***************************/
	public void removeCurrent() {
		// no list
		if (top == null) {
		}

		// remove from the top
		else if (current == top) {
			if (current.getNext() != null) {
				current.getNext().setPrev(null);
				current = current.getNext();
				top = current;
				isSaved = false;
			} else {
				top = null;
				isSaved = false;
			}
		}

		// if there is nothing after the current line
		else if (current.getNext() == null) {
			current.getPrev().setNext(null);
			current = current.getPrev();
			isSaved = false;
		}

		else {
			current.getPrev().setNext(current.getNext());
			current.getNext().setPrev(current.getPrev());
			current = current.getNext();
			isSaved = false;
		}
	}

	/**
	 * If the current list is saved
	 * 
	 * @return boolean if the list is currently saved
	 */
	public boolean isSaved() {
		return isSaved;
	}

	/**********************************
	 * Displays the whole LinkedList
	 **********************************/
	public void display() {
		Node<E> temp = top;
		int lineNumber = 0;

		// runs through the list and prints out each line
		while (temp != null) {
			if (temp == current) {
				System.out.println(lineNumber++ + ": " + ">" + "\t"
						+ temp.getData());
			} else {
				System.out.println(lineNumber++ + ": " + "\t"
						+ temp.getData());
			}
			temp = temp.getNext();
		}
	}

	/***********************************************************
	 * Displays the desired portion of the list
	 * 
	 * @param start
	 *            start of the list the user wants to display
	 * @param end
	 *            end of the list the user wants to display
	 ***********************************************************/
	public void display(int start, int end) {
		Node<E> temp = top;
		int i = 0;

		// if the the list exists and the numbers are correctly input
		if (temp != null && start <= end && start >= 0 && end >= 0) {

			// gets the temp node up to where the user wants to
			// start displaying
			while (i < start) {
				if (temp.getNext() != null) {
					temp = temp.getNext();
					i++;
				} else {
					System.out.println("Cannot start from that point");
					return;
				}
			}

			// displays only one line
			if (start == end) {
				System.out.println(i + ": " + ">" + "\t"
						+ temp.getData());
			} else {

				// displays the list up to where the user entered, or
				// until the list ends
				while (i <= end) {
					if (temp == current) {
						System.out.println(i++ + ": " + ">" + "\t"
								+ temp.getData());
					} else {
						System.out.println(i++ + ": " + "\t"
								+ temp.getData());
					}
					if (temp.getNext() == null) {
						return;
					}
					temp = temp.getNext();
				}
			}
		} else if (start < 0 || end < 0) {
			System.out.println("Numbers must be positive");
		} else if (start > end) {
			System.out
					.println("Starting number must be > ending number");
		} else
			return;
	}

	/*************************
	 * Removes a desired line
	 * 
	 * @param lineNbr
	 *            number of line being removed
	 *************************/
	public void removeLine(int lineNbr) {
		// index node
		Node<E> temp = top;

		// makes sure the list isn't empty and we aren't deleting
		// an empty node
		if (temp != null && lineNbr >= 0) {

			// index counts how many lines change
			int index = 0;

			// gets index up to the line number
			while (index < lineNbr) {
				if (temp.getNext() != null) {
					temp = temp.getNext();
					index++;
				}
				// if the lineNbr is to large, quit
				else if (temp.getNext() == null) {
					return;
				}
			}
			// remove from the top
			if (temp == top) {
				if (temp.getNext() != null) {
					temp.getNext().setPrev(null);
					temp = temp.getNext();
					top = temp;
					if ((current.getNext() != null && current.getNext()
							.getPrev() == null) || current == null) {
						current = top;
					}
					isSaved = false;
				} else {
					top = null;
					isSaved = false;
				}
			}

			// remove from bottom
			else if (temp.getNext() == null) {
				current = temp.getPrev();
				temp.getPrev().setNext(null);
				temp = temp.getPrev();
				isSaved = false;
			}

			// remove from anywhere else
			else {
				current = temp.getNext();
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				temp = temp.getNext();
				isSaved = false;
			}

		}
	}

	/**********************************************************
	 * Gets the desired line number
	 * 
	 * @param lineNbr
	 *            number of the line that will be returned
	 * @return line being displayed
	 **********************************************************/
	public E getLine(int lineNbr) {
		Node<E> temp = top;
		if (temp != null && lineNbr >= 0) {
			int index = 0;

			// gets the temp node to the desired line
			while (index < lineNbr) {
				if (temp.getNext() != null) {
					temp = temp.getNext();
					index++;
				}
				// if the line doesn't exist, display nothing
				else {
					return (E) "";
				}
			}
			return temp.getData();
		}
		return (E) "";
	}

	/*************************************************
	 * Gets the number that the clipboard is saved as
	 * 
	 * @return number the clipboard is saved as
	 *************************************************/
	public int getIndex() {
		return index;
	}

	/******************************************************
	 * Sets the number the clipboard is saved as
	 * 
	 * @param index
	 *            number the clipboard will be saved as
	 ******************************************************/
	public void setIndex(int index) {
		this.index = index;
	}

	/************************
	 * gets the current line
	 * 
	 * @return the current line
	 ************************/
	public E getCurrent() {
		Node<E> temp = top;
		if (temp == null) {
			return (E) "";
		}
		while (temp != current) {
			temp = temp.getNext();
		}
		return temp.getData();
	}

	/***********************************************
	 * Gets the node that is at the top of the list
	 * 
	 * @return node at the top of the list
	 ***********************************************/
	public Node<E> getTop() {
		return top;
	}

	/************************
	 * Clears the LinkedList
	 ************************/
	public void clearAll() {
		current = top = null;
	}

	/**
	 * Saves the current LinkedList to a file
	 * 
	 * @param fileName
	 *            name of the file that is being saved
	 */
	public void save(String fileName) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Node<E> temp = top;

		// saves each line of the LinkedList
		while (temp != null) {
			out.println(temp.getData());
			temp = temp.getNext();
		}
		isSaved = true;
		System.out.println("The file is saved as " + fileName);
		out.close();
	}

	/*******************************************
	 * Loads the desired LinkedList from a file
	 * 
	 * @param fileName
	 *            name of file being loaded
	 *******************************************/
	public void load(String fileName) {
		try {
			Scanner fileReader = new Scanner(new File(fileName));
			clearAll();
			while (fileReader.hasNextLine()) {
				insertAfterCurrent((E) fileReader.nextLine());
			}
			current = top;
			isSaved = true;
		} catch (IOException error) {
			System.out.println("Oops!  Something went wrong.");
		}
	}

	public String toString() {
		String list = "";
		Node<E> temp = top;
		while (temp != null) {
			list += temp.getData() + "\n";
			temp = temp.getNext();
		}
		return list;
	}

	public boolean equals(LinkedList<?> list) {
		if (this.toString() == list.toString()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();

		list.insertAfterCurrent("1");
		list.display();
		System.out.println("---------------");
		list.insertAfterCurrent("2");
		list.display();
		System.out.println("---------------");
		list.insertAfterCurrent("3");
		list.display();
		System.out.println("---------------");
		list.insertAfterCurrent("4");
		list.insertAfterCurrent("5");
		list.insertAfterCurrent("6");
		list.insertAfterCurrent("7");
		list.insertAfterCurrent("8");
		list.insertAfterCurrent("9");
		list.display();
		System.out.println(list.getLine(1));
		list.removeLine(4);
		System.out.println("---------------");
		list.display();
		list.removeLine(0);
		System.out.println("---------------");
		list.display();
		list.removeLine(7);
		System.out.println("---------------");
		list.display();
		list.removeLine(3);
		System.out.println("---------------");
		list.display();
		list.moveCurrentDown();
		list.moveCurrentDown();
		list.removeLine(5);
		System.out.println("---------------");
		list.display();
	}
}
