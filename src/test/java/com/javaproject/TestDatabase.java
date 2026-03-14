package com.javaproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.javaproject.beans.BoardGame;
import com.javaproject.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class TestDatabase {

    private DatabaseAccess da;

    @Autowired
    public void setDatabase(DatabaseAccess da) {
        this.da = da;
    }

    @Test
    public void testDatabaseAddBoardGame() {

        BoardGame boardGame = new BoardGame();
        boardGame.setName("onecard");
        boardGame.setDescription("Fun Party Game");
        boardGame.setLevel(1);
        boardGame.setMinPlayers(2);
        boardGame.setMaxPlayers(6);
        boardGame.setGameType("Party Game");

        int originalSize = da.getBoardGames().size();

        da.addBoardGame(boardGame);
        int newSize = da.getBoardGames().size();

        assertEquals(originalSize + 1, newSize);
    }
}