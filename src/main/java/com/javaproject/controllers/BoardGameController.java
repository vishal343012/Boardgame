package com.javaproject.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaproject.beans.BoardGame;
import com.javaproject.beans.ErrorMessage;
import com.javaproject.database.DatabaseAccess;

// REST controller for BoardGames
@RestController
@RequestMapping("/boardgames")
public class BoardGameController {

    private DatabaseAccess da;

    public BoardGameController(DatabaseAccess da) {
        this.da = da;
    }

    /**
     * Retrieve all boardgames
     */
    @GetMapping
    public List<BoardGame> getBoardGames() {
        return da.getBoardGames();
    }

    /**
     * Handles requests for a specific boardgame
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBoardGame(@PathVariable Long id) {
        BoardGame boardGame = da.getBoardGame(id);
        if (boardGame != null) {
            return ResponseEntity.ok(boardGame);
        } else {
            // Using the new constructor that accepts a message
            ErrorMessage error = new ErrorMessage("No such record");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Add a new boardgame
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> postBoardGame(@RequestBody BoardGame boardGame) {
        try {
            Long id = da.addBoardGame(boardGame);
            boardGame.setId(id);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(id).toUri();
            return ResponseEntity.created(location).body(boardGame);
        } catch (Exception e) {
            // Using the new constructor that accepts a message
            ErrorMessage error = new ErrorMessage("Name already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }
}
