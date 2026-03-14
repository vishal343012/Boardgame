package com.javaproject.services;

import com.javaproject.beans.BoardGame;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardGameService {

    private List<BoardGame> games = new ArrayList<>();

    public List<BoardGame> getAllGames() {
        return games;
    }

    public BoardGame getGameById(Long id) {
        return games.stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public BoardGame createGame(BoardGame game) {
        games.add(game);
        return game;
    }

    public BoardGame updateGame(Long id, BoardGame game) {
        for (BoardGame g : games) {
            if (g.getId().equals(id)) {
                g.setName(game.getName());
                g.setDescription(game.getDescription());
                return g;
            }
        }
        return null;
    }

    public boolean deleteGame(Long id) {
        return games.removeIf(g -> g.getId().equals(id));
    }
}
