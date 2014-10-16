package project4;

import static org.junit.Assert.*;

import org.junit.Test;

/**********************************************
 * Test class for the single line text editor
 * 
 * @author Gage VanderClay
 * @version April 13, 2014
 **********************************************/
public class EditorTesting {

	/****************************************************************
	 * Tests whether the insertBefore command works when the list is 
	 * empty and when the current is on top
	 ****************************************************************/
	@Test
	public void testProcessCommandInsertBefore() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("b Line " + i);
			assertTrue(("Line " + i).equals(ed.getCurrentLine()));
		}
	}

	/*******************************************************************
	 * Tests whether insertBefore works when the current line is in the
	 * middle of the test
	 *******************************************************************/
	@Test
	public void testProcessCommandInsertBefore2() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("b Line" + i);
		}
		ed.processCommand("m");
		for (int i = 1; i <= 1500; i++) {
			ed.processCommand("b Line " + i);
			assertTrue(("Line " + i).equals(ed.getCurrentLine()));
		}
	}

	/*******************************************************************
	 * Tests whether the insert after last works when the list is empty 
	 * and on the last line of the list
	 *******************************************************************/
	@Test
	public void testProcessCommandInsertAfterLast() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("i Line " + i);
			assertTrue(("Line " + i).equals(ed.getCurrentLine()));
		}
	}

	/**************************************
	 * Tests the move current down command
	 **************************************/
	@Test
	public void testProcessCommandMoveCurrentDownSingle() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("b Line " + i);
		}
		for (int i = 15000; i >= 1; i--) {
			assertTrue(("Line " + i).equals(ed.getCurrentLine()));
			ed.processCommand("m");

		}
	}

	/*****************************************************************
	 * Tests the moveCurrentDown when the user wants to move multiple 
	 * lines at once
	 *****************************************************************/
	@Test
	public void testProcessCommandMoveCurrentDownMultiple() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("b Line " + i);
		}
		ed.processCommand("m " + 7500);
		assertTrue(("Line 7500").equals(ed.getCurrentLine()));

		// large number should take it all the way down
		ed.processCommand("m 142334");
		assertTrue(("Line 1").equals(ed.getCurrentLine()));
	}

	/**************************************************
	 * Tests whether the move current up command works
	 **************************************************/
	@Test
	public void testProcessCommandMoveCurrentUpSingle() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("i Line " + i);

		}
		for (int i = 15000; i >= 1; i--) {
			assertTrue(("Line " + i).equals(ed.getCurrentLine()));
			ed.processCommand("u");
		}
	}

	@Test
	public void testProcessCommandMoveCurrentUpMultiple() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("i Line " + i);
		}
		ed.processCommand("u 7500");
		assertTrue("Line 7500".equals(ed.getCurrentLine()));
		ed.processCommand("u 32433");
		assertTrue("Line 1".equals(ed.getCurrentLine()));
	}

	/***************************************************************
	 * Tests if the remove command works when removing single lines
	 ***************************************************************/
	@Test
	public void testProcessCommandRemoveSingle() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("b Line " + i);
		}
		for (int i = 14999; i >= 1; i--) {
			ed.processCommand("r");
			assertTrue(("Line " + i).equals(ed.getCurrentLine()));
		}
	}

	/********************************************************************
	 * Tests remove when the user wants to remove multiple lines at once
	 ********************************************************************/
	@Test
	public void testProcessCommandRemoveMultiple() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("b Line " + i);
		}
		ed.processCommand("r 15000");
		for (int i = 1; i <= 15000; i++)
			assertTrue(ed.getLine(i).equals(""));
	}

	/****************************************************
	 * Tests the method that clears the whole linkedlist
	 ****************************************************/
	@Test
	public void testProcessCommandClearAll() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("b Line " + i);
		}

		// file needs to be saved before clearing
		ed.processCommand("s test");
		ed.processCommand("c");
		for (int i = 1; i <= 15000; i++)
			assertTrue(ed.getLine(i - 1).equals(""));
	}

	/****************************************************************
	 * Tests the save and load methods by saving and then loading a 
	 * linkedlist then comparing to a linkedlist that should be the 
	 * same
	 ****************************************************************/
	@Test
	public void testProcessCommandSaveLoad() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("b Line " + i);
		}
		ed.processCommand("s test");
		ed.processCommand("c");
		ed.processCommand("l test");
		Editor vi = new Editor();
		for (int i = 1; i <= 15000; i++) {
			vi.processCommand("b Line " + i);
		}
		for (int i = 15000; i >= 1; i--) {
			assertTrue(vi.getCurrentLine().equals(ed.getCurrentLine()));
			ed.processCommand("m");
			vi.processCommand("m");
		}
	}

	/***********************************
	 * Tests the insertAfterLast method
	 ***********************************/
	@Test
	public void testProcessCommandInsertLast() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15000; i++) {
			ed.processCommand("e Line " + i);
			assertTrue(("Line " + i).equals(ed.getLine(i - 1)));
		}
	}

	/**********************************
	 * Tests the cut and paste methods
	 **********************************/
	@Test
	public void testProcessCommandCutPaste() {
		Editor ed = new Editor();
		for (int i = 1; i <= 15; i++) {
			ed.processCommand("e Line " + i);
		}
		Editor vi = ed;
		for (int i = 0; i <= 150; i++) {
			for (int j = i; j <= 150; j++) {
				ed.processCommand("cut " + i + " " + j + " " + 1);
				ed.processCommand("pas 1");
				ed.processCommand("u " + 200);
				for (int e = 0; e <= 150; e++) {
					assertTrue(ed.getLine(e).equals(vi.getLine(e)));
				}
			}
		}
	}

}