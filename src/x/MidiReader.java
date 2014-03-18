package x;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import com.google.common.base.Joiner;
import com.jand.cm19a.CM19a;
import com.jand.cm19a.CM19aException;

class MidiReader implements Receiver {

	static enum LightState {
		OFF, ON
	}

	final static String LIGHTS_ADDRESS = "A1";
	
	IntervalSequenceDetector sequence_detector = new IntervalSequenceDetector();
	int current_light_state_index = 0;
	
	final CM19a transceiver;
	public MidiReader(CM19a transceiver) {
		this.transceiver = transceiver;
	}
	
	void cycleLightState() {
		
		this.current_light_state_index = (this.current_light_state_index + 1) % LightState.values().length;
		
		try {
			this.transceiver.send(this.transceiver.formatCommand( Joiner.on("").join(new String[] {
					LIGHTS_ADDRESS,
					LightState.values()[this.current_light_state_index].name(),
			})));
		} catch (CM19aException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(MidiMessage message, long timeStamp) {

		if (message instanceof ShortMessage) {
			ShortMessage short_message = (ShortMessage) message;
			if (ShortMessage.NOTE_ON == short_message.getCommand()) {
				Gratan.printKey(short_message);
				
				Collection<OngoingIntervalSequence> completed_sequences = this.sequence_detector.readNote(short_message.getData1());
				
				for (OngoingIntervalSequence seq : completed_sequences) {
					System.out.println("Completed pattern: " + seq.getName());
					
					if ( seq.getName().equalsIgnoreCase("windmill") ) {
						cycleLightState();
					}
				}
			}
		}
	}

	@Override
	public void close() {
		System.out.println("I closed!");
	}
}