package training.adv.bowling.impl.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.impl.shike.BowlingGameFactoryImpl;

public class BowlingGameTest {
	
	private BowlingGameFactory factory = new BowlingGameFactoryImpl();

	@Test
	public void testNoPins() {
		BowlingGame game = factory.getGame();
		
		game.addScores();
		assertEquals((int)Integer.valueOf(0), game.getTotalScore());
	}
	
	@Test
	public void testNegative() {
		BowlingGame game = factory.getGame();
		
		game.addScores(-1);
		assertEquals((int)Integer.valueOf(0), game.getTotalScore());
	}
	
	
	@Test
	public void testPartialStrike() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
	}
	
	@Test
	public void testTotalStrike() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
	}
	
	@Test
	public void testTotalStrikeSeparately() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
	}
	
	@Test
	public void testGreaterThanMaxPins() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
		
		game.addScores(10, 10, 20);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
	}
	
	@Test
	public void testInvalidPins() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
		
		game.addScores(5, 6, 7, 8);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
	}
	
	@Test
	public void testSpareCalculation() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
		
		game.addScores(5, 5, 5, 5);
		assertEquals((int)Integer.valueOf(100), game.getTotalScore());
	}
	
	@Test
	public void testSpareSeparately() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10, 5);
		assertEquals((int)Integer.valueOf(75), game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals((int)Integer.valueOf(100), game.getTotalScore());
	}
	
	@Test
	public void testExtraTwoPins() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
		
		game.addScores(5, 5);
		assertEquals((int)Integer.valueOf(285), game.getTotalScore());
	}
	
	@Test
	public void testExtraTwoPinsWithStrike() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
		
		game.addScores(10, 5, 5);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
		
		game.addScores(10, 5);
		assertEquals((int)Integer.valueOf(295), game.getTotalScore());
		
	}
	
	@Test
	public void testLastTurnSpareSeparately() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
		assertEquals((int)Integer.valueOf(255), game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals((int)Integer.valueOf(255), game.getTotalScore());
		
		game.addScores(5, 5);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
	}
	
	@Test
	public void testFinishedGameNotAcceptNewPins() {
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
		assertEquals((int)Integer.valueOf(235), game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals((int)Integer.valueOf(255), game.getTotalScore());
		
		game.addScores(5, 5);
		assertEquals((int)Integer.valueOf(255), game.getTotalScore());
	}
	
	@Test
	public void multiThreadTest() throws InterruptedException {
		List<BowlingGame> games = Arrays.asList(factory.getGame(), factory.getGame(), factory.getGame());
		for (int i = 0; i < 21; i++) {
			games.parallelStream().forEach(g -> g.addScores(5));
		}
		games.stream().forEach(g -> {
			assertEquals((int)Integer.valueOf(150), g.getTotalScore());
		});
	}
	
}