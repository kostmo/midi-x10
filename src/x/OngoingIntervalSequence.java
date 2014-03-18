package x;

class OngoingIntervalSequence {
	private int position = 0;
	final int[] pattern;
	
	final private String name;
	
	OngoingIntervalSequence(String name, int[] pattern) {
		this.name = name;
		this.pattern = pattern;
	}
	
	public String getName() {
		return this.name;
	}
	
	boolean isCompleted(int interval) {
		if (interval == pattern[this.position]) {
			
			this.position++;
			if (this.position == pattern.length) {
				this.position = 0;
				return true;
			}
			
		} else {
			this.position = 0;
		}

		return false;
	}
}