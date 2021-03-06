package project4;

import java.util.Scanner;

/*****************************************************************
 * Class that accepts the user input from the user and interprets what needs to
 * happen
 * 
 * @author Gage Vander Clay
 * @version April 13, 2014
 ******************************************************************/
public class Editor implements IEditor {

	/** LinkedList of Strings that will be displayed in the program */
	LinkedList<String> list = new LinkedList<String>();

	/**
	 * LinkedList of other LinkedList that contains the information 
	 * that is cut with the cut method
	 */
	LinkedList<LinkedList> clipBoard = new LinkedList<LinkedList>();

	/**************************************
	 * processes the given editor command
	 * 
	 * @param command
	 *            what the user inputs
	 **************************************/
	@Override
	public void processCommand(String command) {

		// if the user doesn't put in anything
		if (command.equals("")) {
			System.out.println("There was no command");
			return;
		}

		// the action that the user wants to happen
		char input;

		// data the user inputs or the amount of times the user does
		// something
		String data;

		// if the user inputs "cut" it sets the input to n so the user
		// can cut something
		if (command.length() >= 3 && command.charAt(0) == 'c'
				&& command.charAt(1) == 'u' && command.charAt(2) == 't') {
			input = 'n';
			data = command.substring(3).trim();
		}

		// same as above only with pas and p
		else if (command.length() >= 3 && command.charAt(0) == 'p'
				&& command.charAt(1) == 'a' && command.charAt(2) == 's') {
			input = 'p';
			data = command.substring(3).trim();
		}

		// default
		else {
			input = command.charAt(0);
			data = command.substring(1).trim();
		}

		// switch statement that detects which input is needed based
		// on the user
		switch (input) {

		// insert before
		case 'b':
			list.insertBeforeCurrent(data);
			break;

		// insert after
		case 'i':
			list.insertAfterCurrent(data);
			break;

		// insert last
		case 'e':
			list.insertAfterLast(data);

			// move the current line down
			break;
		case 'm':
			// default number for n
			int n = 1;
			try {
				n = Integer.parseInt(data);
			}
			// doesn't move if the user puts in something wrong
			catch (NumberFormatException e) {
				if (data.equals("")) {
					n = 1;
				} else {
					System.out.println("Invalid Input");
					return;
				}
			}

			// moves the line down based on how much the user entered
			for (int i = 0; i < n; i++) {
				list.moveCurrentDown();
			}
			break;

		// moves the current line up
		case 'u':
			n = 1;
			try {
				n = Integer.parseInt(data);
			} catch (NumberFormatException e) {
				if (data.equals("")) {
					n = 1;
				} else {
					System.out.println("Invalid Input");
					return;
				}
			}
			for (int i = 0; i < n; i++) {
				list.moveCurrentUp();
			}
			break;

		// removes lines
		case 'r':
			n = 1;
			try {
				n = Integer.parseInt(data);
			} catch (NumberFormatException e) {
				if (data.equals("")) {
					n = 1;
				} else {
					System.out.println("Invalid Input");
					return;
				}
			}
			for (int i = 0; i < n; i++) {
				list.removeCurrent();
			}
			break;

		// displays the list
		case 'd':

			// if the user only enters d, displays the whole list
			if (data.equals("")) {
				list.display();
			}

			// if the user wants to display only certain lines
			else {

				// creates an array of ints
				String[] strAry = data.split(" ");
				int[] index = new int[strAry.length];
				try {
					for (int i = 0; i < strAry.length; i++) {
						index[i] = Integer.parseInt(strAry[i]);
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input");
					break;
				}
				if (index.length == 2) {
					list.display(index[0], index[1]);
				}

				// if the user only wants to see one line
				else if (index.length == 1) {
					list.display(index[0], index[0]);
				} else if (index.length > 2) {
					System.out.println("Can only display between "
							+ "two numbers");
				}
			}
			break;

		// clears the list
		case 'c':
			if (list.isSaved()) {
				list.clearAll();
			} else {
				System.out.println("The file is not saved");
			}
			break;

		// saves the list
		case 's':
			if (data.trim().equals("")) {
				System.out.println("Must save under a name");
			} else
				list.save(data);
			break;

		// loads a file to the list
		case 'l':
			if (list.isSaved()) {
				if (data.trim().equals("")) {
					System.out.println("Must input a file to load");
				} else {
					list.load(data);
				}
			} else {
				System.out.println("Current file isn't saved");
			}
			break;

		// displays the help page
		case 'h':
			displayHelp();
			break;

		// exits the program
		case 'x':
			if (list.isSaved()) {
				System.exit(0);
			} else
				System.out.println("Current file isn't saved");
			break;

		// cuts certain lines of code
		case 'n':
			if (data.equals("")) {
				System.out.println("Must choose lines to cut");
			} else {
				// uses the input and makes an array of ints
				String[] strAry = data.split(" ");
				int[] parts = new int[strAry.length];
				try {

					// puts all the ints into an array
					for (int i = 0; i < strAry.length; i++) {
						parts[i] = Integer.parseInt(strAry[i]);
						if (parts[i] < 0) {
							System.out
									.println("All numbers must be 0 or "
											+ "greater");
							return;
						}
					}
					// the clipboard index must be greater than 0
					if (parts[2] == 0) {
						System.out
								.println("Clipboard must be greater than"
										+ " 0");
						return;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input");
					break;
				}

				// only works if the user cuts between a line that is
				// above the the second line
				if (parts[1] >= parts[0]) {

					// if the user did input 3 numbers
					if (parts.length == 3) {

						// LinkedList that holds the data that is cut
						LinkedList<String> temp = new LinkedList<String>();
						temp.setIndex(parts[2]);

						// puts the cut data into temp and removes it
						// from the list
						for (int i = parts[0]; i <= parts[1]; i++) {
							temp.insertAfterLast(list.getLine(parts[0]));
							list.removeLine(parts[0]);
						}

						// if there is nothing in the clipboard
						if (clipBoard.getTop() == null) {
							clipBoard.insertAfterCurrent(temp);
						} else {
							Node<LinkedList> indexNode = clipBoard
									.getTop();

							// checks if there is already something in
							// the clipboard with a similar number if
							// the list contains only one
							if (indexNode.getNext() == null) {
								if (indexNode.getData().getIndex() == parts[2]) {
									indexNode.setData(temp);
									return;
								}
							}
							// runs through a loop to check if the list
							// contains any clipboard with the same index
							else {
								while (indexNode.getNext() != null) {
									if (indexNode.getData().getIndex() == parts[2]) {
										indexNode.setData(temp);
										return;
									} else {
										clipBoard
												.insertAfterCurrent(temp);
									}
								}
							}
						}

					}
				} else {
					System.out.println("Starting number must be > "
							+ "nding number");
				}
			}
			break;
		case 'p':

			// node that will be used to store data that is going into
			// the main list
			Node<String> temp = null;

			// if the user didn't specify a clipboard to paste
			if (data.equals("")) {
				System.out.println("Must choose a clipboard to paste");
			} else {

				// number of the clipboard being looked for
				int clipNum;
				try {
					clipNum = Integer.parseInt(data);
				} catch (NumberFormatException e) {
					System.out.println("That isn't a number");
					return;
				}

				// node that checks the clipboard
				Node<LinkedList> position = clipBoard.getTop();

				// keeps track of what line of the clipboard we are on
				int index = 0;
				if (position == null) {
					System.out.println("There are no clipboards");
					return;
				}
				// checks the list for the designated clipboard
				while (position.getData().getIndex() != clipNum) {
					if (position.getNext() == null) {
						System.out
								.println("That clipboard doesn't exist");
						return;
					} else {
						position = position.getNext();
						index++;
					}
				}

				// runs through the list to look for the correct
				// clipboard
				temp = position.getData().getTop();
				if (temp == null) {
					System.out.println("That clipboard doesn't exist");
				} else {
					int i = 0;
					while (temp.getNext() != null) {
						list.insertBeforeCurrent((String) position
								.getData().getLine(i));
						list.moveCurrentDown();
						temp = temp.getNext();
						i++;
					}
					list.insertBeforeCurrent((String) position
							.getData().getLine(i));
					clipBoard.removeLine(index);
				}
			}
			break;
		default:
			System.out.println("Command not recognized");
		}
	}

	/********************************************************
	 * Gets the designated line in the list
	 * 
	 * @param lineNbr
	 *            number of the line that the user wants
	 ********************************************************/
	@Override
	public String getLine(int lineNbr) {
		return list.getLine(lineNbr);
	}

	/************************************
	 * Gets the current line of the list
	 ************************************/
	@Override
	public String getCurrentLine() {
		return list.getCurrent();
	}

	/**********************************************************
	 * Displays what commands can be entered and their meaning
	 **********************************************************/
	private void displayHelp() {
		System.out.println("Command \t Meaning");
		System.out.println("b <sentence> \t Insert sentence before "
				+ "the current line; make the inserted line the "
				+ "current line");
		System.out.println("i <sentence> \t Insert sentence after "
				+ "the current line; make the inserted line the "
				+ "current line");
		System.out.println("m \t\t Move current line indicator down 1 "
				+ "position");
		System.out
				.println("m # \t\t Move current line indicator down # "
						+ "positions");
		System.out.println("u \t\t Move current line indicator up 1 "
				+ "position");
		System.out.println("u # \t\t Move current line indicator up "
				+ "# positions");
		System.out.println("r \t\t Remove the current line;"
				+ "make the next line the current line, if there"
				+ " is a\n\t\t next line;"
				+ " otherwise make the previous line the current "
				+ "line if there is a previous line");
		System.out.println("r # \t\t Remove # lines starting at the "
				+ "current line; make the next line the current "
				+ "line,\n\t\t if there is a next line; "
				+ "otherwise make the previous line the current "
				+ "line.");
		System.out
				.println("d \t\t Display all lines in the buffer/file "
						+ "/with line numbers");
		System.out.println("d # * \t\t Display from line # to * "
				+ "(inclusive) in the buffer/file with line "
				+ "numbers");
		System.out.println("c \t\t Clear (remove) all lines in the "
				+ "buffer/file; set current line indicator "
				+ "appropriately");

		System.out.println("s <filename> \t Save the contents to the "
				+ "specified text file");
		System.out.println("l <filename> \t Load the contents of the "
				+ "specified text file into editor buffer"
				+ " replacing the current contents;\n\t\t make "
				+ "the first line the current line");
		System.out
				.println("h \t\t Display a help page of editor commands");
		System.out.println("x \t\t Exit the editor");
		System.out
				.println("cut # $ * \t Cut lines from the file from # "
						+ "to $ into clipboard *, and append onto "
						+ "an internal clipboard *");
		System.out.println("pas * \t\t Paste Clipboard * before the "
				+ "current line position");
	}

	/**************
	 * main method
	 **************/
	public static void main(String[] args) {
		Editor e = new Editor();
		e.displayHelp();
		while (true) {
			Scanner s = new Scanner(System.in);
			String input = s.nextLine();
			e.processCommand(input);
		}
	}
}
