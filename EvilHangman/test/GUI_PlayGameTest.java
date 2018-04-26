import static org.junit.Assert.*;

import java.awt.Font;

import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;

public class GUI_PlayGameTest {
	
	GUI_PlayGame hardGame;
	GUI_PlayGame easyGame;
	final static char [] alphabet = {'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 
			'V', 'W', 'X', 'Y', 'Z'};
	
	@Before
	public void setup() {
		hardGame = new GUI_PlayGame(2, 3);
		hardGame.result = new JLabel("");
        hardGame.label1 = new JLabel("Let's play Evil Hangman!");
        hardGame.label2 = new JLabel("Secret Word: "+ hardGame.game.displayGameState());       
        hardGame.label3 = new JLabel(String.valueOf("Guesses Remaining: "+ hardGame.game.numGuessesRemaining() +"\n"));
		
        easyGame = new GUI_PlayGame(2, 25);
        easyGame.result = new JLabel("");
        easyGame.label1 = new JLabel("Let's play Evil Hangman!");
        easyGame.label2 = new JLabel("Secret Word: "+ easyGame.game.displayGameState());       
        easyGame.label3 = new JLabel(String.valueOf("Guesses Remaining: "+ easyGame.game.numGuessesRemaining() +"\n"));

        
	}

	@Test
	public void testBadCharState() {
		hardGame.inputLetter = '?';
		hardGame.controller();
	}
	
	@Test
	public void testLowercaseCharState() {
		hardGame.inputLetter = 'a';
		hardGame.controller();
		assertEquals("Nope!", hardGame.result.getText());
	}
	
	@Test
	public void testUpperCaseCharState() {
		hardGame.inputLetter = 'A';
		hardGame.controller();
		assertEquals("Nope!", hardGame.result.getText());
	}
	
	@Test
	public void testLoseGame() {
		
		hardGame.inputLetter = 'A';
		hardGame.controller();
		assertEquals("Secret Word: _ _ ", hardGame.label2.getText());
		assertEquals("Guesses Remaining: 2", hardGame.label3.getText());
		assertEquals("Nope!", hardGame.result.getText());
		hardGame.inputLetter = 'E';
		hardGame.controller();
		assertEquals("Secret Word: _ _ ", hardGame.label2.getText());
		assertEquals("Guesses Remaining: 1", hardGame.label3.getText());
		assertEquals("Nope!", hardGame.result.getText());
		hardGame.inputLetter = 'O';
		hardGame.controller();
		assertEquals("Nope!", hardGame.result.getText());
		
	}
	
	@Test
	public void testWinGame() {
		int count = 0;
		assertTrue(easyGame.isEvil);
		assertEquals("Secret Word: _ _ ", easyGame.label2.getText());
		for (int i = 0; i < alphabet.length; i++) {
			if (alphabet[i]!= 'E' && alphabet[i] != 'Y') {
				easyGame.inputLetter = alphabet[i];
				easyGame.controller();
				assertTrue(easyGame.result.getText().equals("Nope!")); 
				assertEquals("Guesses Remaining: " + (25-++count), easyGame.label3.getText());
			}
				

		}
		assertEquals(EvilHangMan.class, easyGame.game.getClass());
		int guesses = easyGame.game.numGuessesRemaining();
		easyGame.inputLetter = 'E';
		easyGame.controller();
		assertEquals("Secret Word: _ E ", easyGame.label2.getText());
		assertEquals("Guesses Remaining: " + guesses, easyGame.label3.getText());
		assertFalse(easyGame.isEvil);
		assertNotEquals(EvilHangMan.class, easyGame.game.getClass());
		assertNotEquals("Nope!", easyGame.result.getText());
		assertEquals("Yes!", easyGame.result.getText());
		
		easyGame.inputLetter = 'Y';
		easyGame.controller();
		assertEquals("Yes!", easyGame.result.getText());
		assertEquals("Secret Word: Y E ", easyGame.label2.getText());
	}
}
