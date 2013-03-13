public class Name {

	protected String[] name = {
		"Abbey", "Abigail", "Agnes", "Allyson", "Alisa",
		"Audrey", "Avril", "Bailey", "Barbara", "Belinda",
		"Benita", "Bernard", "Bertha", "Cadence", "Cathy",
		"Clara", " Ciel", "Claudia", "Corra", "Cindy",
		"Daniela", "Daphne", "Desiree", "Gerald", "Emile",
		"Bernd", "Thomas", "James", "Sanchez", "Anthony",
		"Stefan", "Martina", "Jana", "Sarah", "Nicole"		
	};
	
	// returns the number of elements in the array
	public int getArrayLength() {
		return name.length;
	}
	
	// returns a random name form the array
	public String getRandomName() {
		return name[getRandomIndex()];
	}

	// returns a random index within the range of array
	public int getRandomIndex() {
		return (int) (Math.random() * (getArrayLength()));
	}

	// returns the longest word saved in the array
	public String getLongestWord() {
		String tmp = name[0];
		for (int i = 1; i < name.length; i++) {
			if (tmp.length() < name[i].length()) {
				tmp = name[i];
			}
		}
		return tmp;
	}
}
