package vttp.batch5.sdf.task01;

import java.io.*;
import java.util.*;
import vttp.batch5.sdf.task01.models.BikeEntry;

public class Main {

	// attributes not in Utilities class
	private static final String[] POSITIONS = { "highest", "second highest", "third highest", "fourth highest",
			"fifth highest" };
	private static final String[] WEATHER_CONDITIONS = { "Clear, Few clouds, Partly cloudy, Partly cloudy",
			"Mist + Cloudy, Mist + Broken clouds, Mist + Few clouds, Mist",
			"Light Snow, Light Rain + Thunderstorm + Scattered clouds, Light Rain + Scattered clouds",
			"Heavy Rain + Ice Pallets + Thunderstorm + Mist, Snow + Fog" }; // Replace as needed based on Readme.txt

	public static void main(String[] args) throws IOException {

		String filePath = "task01/day.csv";
		BufferedReader br = new BufferedReader(new FileReader(filePath)); // read the csv file

		String line;
		br.readLine(); // Skip header line

		ArrayList<BikeEntry> bikeEntries = new ArrayList<>();

		while ((line = br.readLine()) != null) { // Read and process each line
			String[] values = line.split(",");

			BikeEntry entry = BikeEntry.toBikeEntry(values); // Reading each line into a instance of BikeEntry
			bikeEntries.add(entry);
		}
		br.close();

		// Sort by total cyclists in descending order and get the top 5 entries
		bikeEntries.sort((a, b) -> Integer.compare(
				(b.getCasual() + b.getRegistered()),
				(a.getCasual() + a.getRegistered())));

		for (int i = 0; i < 5; i++) {
			BikeEntry entry = bikeEntries.get(i);
			int totalCyclists = entry.getCasual() + entry.getRegistered();

			String position = POSITIONS[i];
			String season = Utilities.toSeason(entry.getSeason());
			String day = Utilities.toWeekday(entry.getWeekday());
			String month = Utilities.toMonth(entry.getMonth());
			String weather = WEATHER_CONDITIONS[entry.getWeather() - 1];
			String holiday = entry.isHoliday() ? "a holiday" : "not a holiday";

			// Print expected output
			System.out.printf(
					"The %s(position) recorded number of cyclists was in %s (season), on a %s (day) in the month of %s (month). There were a total of %d (total) cyclists. The weather was %s (weather).\n"
							+
							"%s (day) was %s.\n\n",
					position, season, day, month, totalCyclists, weather, day, holiday);
		}
	}
}