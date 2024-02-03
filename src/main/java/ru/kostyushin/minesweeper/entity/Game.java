package ru.kostyushin.minesweeper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Game {

    @Id
    private String gameId;
    private int width;
    private int height;
    private int minesCount;
    private boolean completed;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String field;

    public char[][] getFieldAsArray() {
        String[] rows = field.split("\n");
        char[][] result = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].toCharArray();
        }
        return result;
    }
}

