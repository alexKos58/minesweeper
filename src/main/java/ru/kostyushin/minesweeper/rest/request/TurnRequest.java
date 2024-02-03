package ru.kostyushin.minesweeper.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TurnRequest {
    private String gameId;
    private int row;
    private int col;
}
