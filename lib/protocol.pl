#!/usr/bin/perl -w
$PROTOCOL="/home/jroberts/dev/cm19a/doc/protocol.txt";
open PROTOCOL or die "Can't find $PROTOCOL \n";
while ( <PROTOCOL> ) {
	($device, $command, $data1, $data2)= split /\s+/, $_ ;
	
	$complement1 = ~$data1;
	$hex1 = unpack("H*", pack("B*", $data1));
	$hex2 = unpack("H*", pack("B*", $complement1));
	$complement2 = ~$data2;
	$hex3 = unpack("H*", pack("B*", $data2));
	$hex4 = unpack("H*", pack("B*", $complement2));
	#print $line, " ", $complement, "\n";
	print $device, $command, "  0x20", "  0x", $hex1, "  0x", $hex2,"  0x", $hex3, "  0x", $hex4, "\n";

}
