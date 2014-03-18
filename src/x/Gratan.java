package x;

import java.io.IOException;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;
import javax.usb.UsbException;

import com.jand.cm19a.CM19a;
import com.jand.cm19a.CM19aException;

public class Gratan {
	public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

	static void printKey(ShortMessage sm) {
		int key = sm.getData1();
		int octave = (key / 12) - 1;
		int note = key % 12;
		String noteName = NOTE_NAMES[note];
		int velocity = sm.getData2();
		System.out.println(noteName + octave + " key=" + key + " velocity: " + velocity);
	}
	

	static MidiDevice getInputDevice() throws MidiUnavailableException {
		Info[] device_infos = MidiSystem.getMidiDeviceInfo();

		for (Info device_info : device_infos) {
			System.out.println("------");
			System.out.println("Vendor:" + device_info.getVendor());
			System.out.println("Name:" + device_info.getName());
			System.out.println("Description:" + device_info.getDescription());
			System.out.println("Version:" + device_info.getVersion());

			if (device_info.getDescription().contains("CASIO")) {
				MidiDevice midi_device = MidiSystem.getMidiDevice(device_info);
				if (midi_device.getMaxTransmitters() != 0)					
					return midi_device;
			}
		}

		return null;
	}
	
	public static void main(String[] args) throws MidiUnavailableException, IOException, CM19aException, UsbException {
		
		
		CM19a transceiver = new CM19a();
		
		
		if (true) {
			try {
					transceiver.startDriver();
			} catch ( Exception e ) {
					System.out.println( "Problem starting driver: " + e.getMessage());
	//				System.exit(0);
			}
		}

		
		
		

		MidiDevice keyboard_input = getInputDevice();
		keyboard_input.open();

		Receiver rx = new MidiReader(transceiver);
		Transmitter transmitter = keyboard_input.getTransmitter();
		transmitter.setReceiver(rx);
		
		
		
		
		// Wait for user to hit the "Enter" key before exiting.
		System.in.read();
		
		System.out.println("The user hit a key; should exit now...");


		keyboard_input.close();

		System.out.println("Closed keyboard input.  Now attempting to release transceiver device...");
		
		transceiver.releaseDevice();
		
		System.out.println("Transceiver device released!");
		
		
		// XXX FIXME This shouldn't be necessary; the program should exit when all of the user
		// threads are terminated.
		// QUESTION: Which threads are still running at this point???
		System.exit(0);
	}
}