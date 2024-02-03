package ru.kostyushin.minesweeper.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kostyushin.minesweeper.entity.Game;
import ru.kostyushin.minesweeper.rest.request.GameRequest;
import ru.kostyushin.minesweeper.rest.request.TurnRequest;
import ru.kostyushin.minesweeper.service.GameService;

@RestController
@AllArgsConstructor
@RequestMapping("/minesweeper")
public class MinesweeperController {

    private final GameService service;

    @PostMapping("/new")
    public ResponseEntity<Game> newGame(@RequestBody GameRequest request) {
        if (service.checkParams(request.getWidth(), request.getHeight(), request.getMinesCount())){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(service.createNewGame(request.getWidth(), request.getHeight(), request.getMinesCount()));
    }

    @PostMapping("/turn")
    public ResponseEntity<Game> makeTurn(@RequestBody TurnRequest request) {
        Game game = service.returnGame(request.getGameId());
        if (game == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (game.isCompleted()) {
            return ResponseEntity.badRequest().body(null);
        }

        int row = request.getRow();
        int col = request.getCol();

        if (service.isInCorrectCell(row, col, game)){
            return ResponseEntity.badRequest().body(null);
        }

        if (game.getFieldAsArray()[row][col] == 'X') {
            game.getFieldAsArray()[row][col] = 'X';
            game.setCompleted(true);
            return ResponseEntity.ok(game);
        }

        // Если вокруг выбранной ячейки нет мин, открываем все смежные пустые ячейки
        service.openAdjacentEmptyCells(game, row, col);

        // Проверяем, остались ли неоткрытые ячейки без мин
        if (service.checkRemainingCells(game)) {
            game.setCompleted(true);
        }

        return ResponseEntity.ok(game);
    }
}