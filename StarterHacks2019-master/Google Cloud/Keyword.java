public class Keyword {
	String file;
	String keyword = "pineapple";
	boolean found;
	
	public Keyword(String file) {
		this.file = file;
		found = false;
		search();
	}
	
	private void search() {
		String[] words = file.split(" ");
		for (int i=0;i<words.length;i++) {
			if (words[i].equals(keyword)) {
				found = true;
				break;
			}
		}
	}
	
	public boolean getFound() {
		return found;
	}
}
