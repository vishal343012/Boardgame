package com.javaproject.controllers;

import com.javaproject.beans.ErrorMessage;
import com.javaproject.beans.BoardGame;
import com.javaproject.services.BoardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boardgames")
public class BoardGameController {

    @Autowired
    private BoardGameService boardGameService;

    // Get all board games
    @GetMapping
    public ResponseEntity<List<BoardGame>> getAllGames() {
        List<BoardGame> games = boardGameService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    // Get a single board game by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable Long id) {
        BoardGame game = boardGameService.getGameById(id);
        if (game == null) {
            return new ResponseEntity<>(new ErrorMessage("Board game not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    // Create a new board game
    @PostMapping
    public ResponseEntity<?> createGame(@RequestBody BoardGame game) {
        try {
            BoardGame savedGame = boardGameService.createGame(game);
            return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage("Failed to create board game: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Update a board game
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGame(@PathVariable Long id, @RequestBody BoardGame game) {
        try {
            BoardGame updatedGame = boardGameService.updateGame(id, game);
            if (updatedGame == null) {
                return new ResponseEntity<>(new ErrorMessage("Board game not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedGame, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage("Failed to update board game: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a board game
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id) {
        try {
            boolean deleted = boardGameService.deleteGame(id);
            if (!deleted) {
                return new ResponseEntity<>(new ErrorMessage("Board game not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage("Failed to delete board game: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
