package ru.kostyushin.minesweeper.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameRequest {
    private int width;
    private int height;
    private int minesCount;
}
