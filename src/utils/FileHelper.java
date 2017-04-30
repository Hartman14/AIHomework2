package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class FileHelper {

	@SuppressWarnings({ "resource", "unused" })
	public ArrayList<Link> readFile(String fileName) throws IOException {

		Scanner file = new Scanner(new FileReader(fileName));

		ArrayList<Link> network = new ArrayList<Link>();

		// read each line of text file
		while (file.hasNextLine()) {
			Link link = new Link();
			int row = 0;
			int col = 0;
			for (int i = 0; i < 3; i++) {
				if (col == 0) {
					link.setNode1((int) file.nextInt());
				}
				if (col == 1) {
					link.setNode2((int) file.nextInt());
				}
				if (col == 2) {
					link.setCost((double) file.nextDouble());
				}
				col++;
			}
			//link.setActive(0);
			col = 0;
			row++;
			network.add(link);
		}
		return network;
	}

}
