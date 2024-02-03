package ru.kostyushin.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kostyushin.minesweeper.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
}
