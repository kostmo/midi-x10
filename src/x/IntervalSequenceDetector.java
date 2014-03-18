package x;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.Lists;

class IntervalSequenceDetector {
	int last_note;
	boolean started = false;
	
	Collection<OngoingIntervalSequence> ongoing_interval_sequences = new ArrayList<>();
	
	IntervalSequenceDetector() {
		ongoing_interval_sequences.add(
				new OngoingIntervalSequence(
					"Windmill",
					new int[] {4, 2, -6, 4, 2}
				)
		);
	}
	
	/**
	 * Returns list of completed sequences
	 */
	public Collection<OngoingIntervalSequence> readNote(int note) {


		Collection<OngoingIntervalSequence> completed = Lists.newArrayList();
		if (this.started) {
			int interval = note - this.last_note;

			for (OngoingIntervalSequence seq : ongoing_interval_sequences)
				if (seq.isCompleted(interval))
					completed.add(seq);
		}
		
		this.started = true;
		this.last_note = note;

		return completed;
	}
}