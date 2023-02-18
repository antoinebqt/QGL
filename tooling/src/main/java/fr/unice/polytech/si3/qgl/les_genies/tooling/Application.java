package fr.unice.polytech.si3.qgl.les_genies.tooling;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.tooling.simulation.Simulation;
import fr.unice.polytech.si3.qgl.les_genies.tooling.simulation.Map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Application {
	private static Map map = null;
	public static int WEEK = 10;

	public static void main(String [] args) {
		launchApp();
	}

	public static void launchApp(){
		Cockpit cockpit = new Cockpit();

		String init = getStringFromJsonFile("InitGame"+WEEK+".json");
		cockpit.initGame(init);

		map = initMap("Map" + WEEK + ".json");
		Simulation simulation = new Simulation(cockpit, map);

		simulation.launch();

		printLog(cockpit.getLogs());
	}

	public static String getStringFromJsonFile(String path){
		Path fullPath = Paths.get(System.getProperty("user.dir") + "/tooling/src/main/Data/Inits/" + path);
		try {
			return Files.readString(fullPath);
		}catch (IOException e){
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Need to be in /tooling/src/main/Data/Maps/ file
	 * @param fileName name of the file
	 * @return the map object
	 */
	static Map initMap(String fileName){
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			String fullPath = System.getProperty("user.dir") + "/tooling/src/main/Data/Maps/" + fileName;
			map = objectMapper.readValue(new File(fullPath), Map.class);
			return map;
		}catch (IOException ioe){
			ioe.printStackTrace();
		}
		return null;
	}

	private static void printLog(List<String> logs){
		for (String str : logs){
			System.out.println(str);
		}
	}
}
