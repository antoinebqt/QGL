package fr.unice.polytech.si3.qgl.les_genies;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.si3.qgl.les_genies.game.deserializer.JsonRW;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.Game;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.InitGame;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.NextRound;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;

public class Cockpit implements ICockpit {

	private Game game;
	public Game getGame(){
		return this.game;
	}

	protected static final List<String> log = new ArrayList<>();
	public void initGame(String initStr, boolean setPositive) {
		try {
			InitGame initGame = JsonRW.parseFromJson(initStr, InitGame.class);
			this.game = new Game(initGame, setPositive);
		} catch (JsonProcessingException e) {
			Cockpit.addLog("Error :" + e);
		}
	}

	public void initGame(String initStr) {
		try {
			InitGame initGame = JsonRW.parseFromJson(initStr, InitGame.class);
			this.game = new Game(initGame, true);
		} catch (JsonProcessingException e) {
			Cockpit.addLog("Error :" + e);
		}
	}

	public String nextRound(String round) {
		String actions = "[]";
		try {
			NextRound nextRound = JsonRW.parseFromJson(round, NextRound.class);
			game.updateGame(nextRound);
			actions = game.nextRound();
		}catch (NullPointerException e) {
			Cockpit.addLog("Game is null | Error :" + e);
		}catch (JsonProcessingException e){
			Cockpit.addLog("Json issue | Error :" + e);
		}

		return actions;
	}

	public static void addLog(String s) {
		log.add(s);
	}

	@Override
	public List<String> getLogs() {
		return log;
	}

	public static int getLogsSize(){
		return log.size();
	}
}
