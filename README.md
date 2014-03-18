X10 installation steps:
=========================

Uses usb4java.
http://usb4java.org/quickstart.html

Detach command found here:
http://www.linuxha.com/USB/detach.html

Sometime, I need to run this command before launching the app:
./detach 0bc7 0002


Deprecated:
----------------
Add following lines to /etc/modprobe.d/blacklist.conf:

# Blacklist to enable CM19a driver
blacklist lirc_atiusb
blacklist ati_remote