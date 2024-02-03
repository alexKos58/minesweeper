package ru.kostyushin.minesweeper.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kostyushin.minesweeper.entity.Game;
import ru.kostyushin.minesweeper.repository.GameRepository;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final int MAXSIZE = 30;

    public boolean checkParams(int width, int height, int minesCount){
        return (width > MAXSIZE || height > MAXSIZE
                ||minesCount > width * height - 1);
    }

    public Game createNewGame(int width, int height, int minesCount) {
        char[][] field = new char[height][width];
        Random random = new Random();
        for (int i = 0; i < minesCount; i++) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);
            if (field[row][col] != 'X') {
                field[row][col] = 'X';
            } else {
                i--;
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field[i][j] != 'X') {
                    field[i][j] = ' ';
                }
            }
        }
        Game game = new Game(UUID.randomUUID().toString(), width, height, minesCount, false, Arrays.deepToString(field));
        gameRepository.save(game);
        return game;
    }

    public Game returnGame(String id){
       return gameRepository.findById(id).orElse(null);
    }

    public void openAdjacentEmptyCells(Game game, int row, int col) {
        if (game.getFieldAsArray()[row][col] != ' ') {
            return;
        }
        game.getFieldAsArray()[row][col] = '0';
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < game.getHeight() && j >= 0 && j < game.getWidth() && !(i == row && j == col)) {
                    openAdjacentEmptyCells(game, i, j);
                }
            }
        }
    }

    public boolean checkRemainingCells(Game game) {
        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                if (game.getFieldAsArray()[i][j] == ' ' && game.getFieldAsArray()[i][j] != 'X') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isInCorrectCell(int row, int col, Game game){
        return (row < 0
                || row >= game.getHeight()
                || col < 0
                || col >= game.getWidth()
                || game.getFieldAsArray()[row][col] != ' ');
    }
}