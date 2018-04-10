import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EvilHangManTest {
	
	EvilHangMan eh1;
	EvilHangMan eh2;
	final static char [] alphabet = {'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 
			'V', 'W', 'X', 'Y', 'Z'};
	
	@Before
	public void setup() {
		eh1 = new EvilHangMan(2, 20);
		eh2 = new EvilHangMan(2, 1);
	}

	@Test
	public void testMakeGuessNotACharacter() {
		assertFalse(eh1.makeGuess('?'));
		assertFalse(eh1.guessResult);
		assertFalse(eh1.letterGuessHistory.contains("?"));
	}
	
	@Test
	public void testMakeGuessRepeatInput() {
		eh1.makeGuess('A');
		assertFalse(eh1.guessResult);
		assertTrue(eh1.letterGuessHistory.contains("A"));
		assertFalse(eh1.makeGuess('A'));
		assertFalse(eh1.letterGuessHistory.contains("AA"));
		assertFalse(eh1.guessResult);
	}
	
	@Test
	public void testMakeGuessIncorrect() {
		assertFalse(eh1.makeGuess('A'));
		assertFalse(eh1.guessResult);
		for (int i = 0; i < eh1.numWords; i++) {
			assertFalse(eh1.wordlist[i].contains("A"));
		}
	}
	
	@Test
	public void testNumGuesses() {
		assertEquals(1, eh2.guess);
		assertFalse(eh2.makeGuess('A'));
		assertEquals(0, eh2.guess);
		
	}
	
	@Test
	public void testNumWords() {
		assertEquals(94, eh2.numWords);
		assertFalse(eh2.makeGuess('A'));
		assertEquals(68, eh2.numWords);
		assertFalse(eh2.makeGuess('A'));
		assertEquals(68, eh2.numWords);
		assertFalse(eh2.makeGuess('B'));
		assertEquals(64, eh2.numWords);
	}
	
	@Test
	public void testWordList() {
		assertFalse(eh2.makeGuess('A'));
		assertFalse(eh2.makeGuess('B'));
		for (int i  = 0; i <  eh2.wordlist.length; i++) {
			assertFalse(eh2.wordlist[i].contains("A"));
			assertFalse(eh2.wordlist[i].contains("B"));	
		}

	}
	
	@Test
	public void testMakeGuessCorrectGuess() {
		assertEquals("", eh1.getSecretWord());
		
		for (int i = 0; i < alphabet.length; i++) {
			if (alphabet[i]!= 'E' && alphabet[i] != 'Y') {
				assertFalse(eh1.makeGuess(alphabet[i]));
				assertEquals(alphabet[i], eh1.letterGuess);
				assertFalse(eh1.guessResult);
				assertTrue(eh1.letterGuessHistory.contains((Character.valueOf(alphabet[i]).toString())));

			}
		}
		assertFalse(eh1.letterGuessHistory.contains("E"));
		assertFalse(eh1.letterGuessHistory.contains("Y"));
		assertTrue(eh1.makeGuess('E'));
		assertTrue(eh1.guessResult);
		assertEquals("YE", eh1.getSecretWord());
		assertTrue(eh1.makeGuess('Y'));
		assertTrue(eh1.guessResult);
		assertFalse(eh1.letterGuessHistory.contains("E"));
		assertFalse(eh1.letterGuessHistory.contains("Y"));
	}

}
