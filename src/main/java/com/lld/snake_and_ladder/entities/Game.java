package com.lld.snake_and_ladder.entities;

import java.io.InputStream;
import java.util.*;

public class Game {
    private final int id;
    private final int numberOfDice;
    private Queue<Player> players;
    private List<Result> results;
    private Board board;
    private DiceRoller diceRoller;
    private String gamesStatus = "TO BE STARTED";
    private static Scanner scanner = new Scanner(System.in);

    public Game(int id, int numberOfDice) {
        this.id = id;
        if (numberOfDice == 0) {
            throw new RuntimeException("Please specify the number of dice");
        }
        this.numberOfDice = numberOfDice;
        diceRoller = new DiceRoller(numberOfDice);
        players = new LinkedList<>();
        results = new ArrayList<>();
    }

    public void setUpBoard(int numberOfSnakes, int numberOfLadders) {
        setUpBoard(100, numberOfSnakes, numberOfLadders);
    }

    public void setUpBoard(int numberOfSquares, int numberOfSnakes, int numberOfLadders) {
        this.board = new Board(numberOfSquares, id, numberOfSnakes, numberOfLadders);
    }

    public Player addPlayer(String name) {
        if (gamesStatus.equals("STARTED")) {
            throw new RuntimeException("Can not add players as the game is on going");
        }
        int size = players.size();
        Player player = new Player(size + 1, name);
        players.add(player);
        return player;
    }

    public List<Player> allPlayers(List<String> names) {
        if (gamesStatus.equals("STARTED")) {
            throw new RuntimeException("Can not add players as the game is on going");
        }
        int size = players.size();
        List<Player> newList = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            newList.add(new Player(size + 1 + i, names.get(i)));
        }
        players.addAll(newList);
        return newList;
    }

    public Player getTheNextPlayer() {
        return players.peek();
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Player getTheFirstPlayer() {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            indexes.add(i);
        }
        int idx = getThePlayerToStartFirst(indexes);
        for (int i = 0; i < idx; i++) {
            Player player = players.poll();
            players.add(player);
        }

        return players.peek();
    }

    private int getThePlayerToStartFirst(List<Integer> idxes) {
        if (idxes.size() == 1) {
            return idxes.get(0);
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>((int[] o1, int[] o2) -> o2[1] - o1[1]);
        List<Integer> shortenedIndex = new ArrayList<>();
        List<Player> playerList = players.stream().toList();
        for (Integer idx : idxes) {
            Player player = playerList.get(idx);
            System.out.println(player.getName() + ": Press Enter to roll the dice");
            scanner.nextLine();
            int value = diceRoller.rollDice();
            System.out.printf("Dice value : %s", value);
            queue.add(new int[]{idx, value});
            System.out.println("--------------------");
        }

        int[] highestScorer = queue.poll();
        shortenedIndex.add(highestScorer[0]);
        while (!queue.isEmpty() && queue.peek()[0] == highestScorer[0]) {
            shortenedIndex.add(queue.poll()[0]);
        }

        return getThePlayerToStartFirst(shortenedIndex);
    }

    public void startGame() {
        if (Objects.isNull(board)) {
            throw new RuntimeException("Please configure the board");
        }

        if (players.isEmpty() || players.size() == 1) {
            throw new RuntimeException("Add players to start the game");
        }

        gamesStatus = "STARTED";

        Player player = getTheFirstPlayer();

        System.out.println(player.getName() + " Starts the first turn \n Let the Game Begin ....");
        int position = 0;
        while (players.size() > 1) {
            Player currentPlayer = players.peek();
            System.out.printf("\n %s , Press Enter to roll the dice and make your move", currentPlayer.getName());
            scanner.nextLine();
            int value = diceRoller.rollDice();
            System.out.printf("\n Dice value is : %s", value);
            int result = board.updatePlayerPositionAndCheckIfWon(currentPlayer.getId(), value, numberOfDice);
            if(result == -2){
                System.out.printf("\n %s is out of the game%n", currentPlayer.getName());
                players.poll();
                continue;
            }
            if (result == 2) {
                position++;
                results.add(new Result(id, currentPlayer.getId(), currentPlayer.getName(), position));
                System.out.printf("\n %s has won the game with %s position%n", currentPlayer.getName(), position);
                players.poll();
                continue;
            }

            if (result == 1) {
                continue;
            }

            players.poll();
            players.add(currentPlayer);
        }
        gamesStatus = "ENDED";
        displayResult();
    }

    public void displayResult() {
        for (Result result : results) {
            System.out.printf("%s : Position %s%n", result.getPlayerName(), result.getPosition());
        }
    }
}
