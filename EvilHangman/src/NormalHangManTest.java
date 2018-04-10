import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NormalHangManTest {

	NormalHangMan apple;
	
	
	@Before
	public void setup() {
		apple = new NormalHangMan("apple", 5, "OUIX");
	}
	
	@Test
	public void testMakeGuessNotACharacter() {
		assertFalse(apple.makeGuess('?'));
		assertFalse(apple.lettersGuessed().contains("?"));
		assertEquals(5, apple.numGuessesRemaining());
		assertEquals(4, apple.numLettersRemaining());
	}
	
	@Test
	public void testMakeGuessNotInWord() {
		assertEquals("_ _ _ _ _ ", apple.currentState);
		assertFalse(apple.makeGuess('B'));
		assertTrue(apple.lettersGuessed().contains("B"));
		assertEquals(4, apple.numGuessesRemaining());
		assertEquals(4, apple.numLettersRemaining());
		assertEquals("_ _ _ _ _ ", apple.currentState);
	}
	
	@Test
	public void testMakeGuessInWord() {
		assertEquals("_ _ _ _ _ ", apple.currentState);
		assertTrue(apple.makeGuess('p'));
		assertEquals("_ p p _ _ ", apple.currentState);
		assertTrue(apple.lettersGuessed().contains("p"));
		assertEquals(5, apple.numGuessesRemaining());
		assertEquals(3, apple.numLettersRemaining());
	}
	
	@Test
	public void testMakeGuessDupe() {
		assertTrue(apple.makeGuess('p'));
		assertFalse(apple.makeGuess('p'));
		assertTrue(apple.lettersGuessed().contains("p"));
		assertFalse(apple.lettersGuessed().contains("pp"));
		assertEquals(5, apple.numGuessesRemaining());
		assertEquals(3, apple.numLettersRemaining());
		assertEquals("_ p p _ _ ", apple.currentState);
	}

}
