package com.lld.snake_and_ladder;

import com.lld.snake_and_ladder.entities.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class SnakeAndLadderApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SnakeAndLadderApplication.class, args);
		Game game = new Game(1, 2);
		/* Set Board*/
		Scanner scanner = new Scanner(System.in);
		System.out.println("Lets set up the board by entering below details : ");
		System.out.println("\n Total number of squares for the board :");
		int squares = scanner.nextInt();
		System.out.println("\n Total number of snakes:");
		int snakes = scanner.nextInt();
		System.out.println("\n Total number of ladders:");
		int ladders = scanner.nextInt();

		System.out.println("\n Set up in progress .......");
		game.setUpBoard(squares, snakes, ladders);

		System.out.println("\n Set up in complete");

		System.out.println("\n*************************************************");

		System.out.println("Enter the number of players");
		int playersCount = scanner.nextInt();
		List<String> players = new ArrayList<>();
		System.out.println("Enter the names");
		for(int i=0; i<playersCount; i++){
			players.add(scanner.next());
		}

		game.allPlayers(players);

		System.out.println("\n*************************************************");

		System.out.println("Press any bottom to continue");
		scanner.next();

		game.startGame();
	}

}
