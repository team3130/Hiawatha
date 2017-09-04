# Hiawatha Turret
The 2017 "SteamWorks" summer turret robot code

## Adding userlibs to reference libraries

This code requires json-simple-1.1.1.jar as a library for Android-RIO communication. This JAR file needs to be added to the Referenced Libraries of the project. To do this:
1. Expand the "Hiawatha" project dropdown by left-clicking on it
2. Right click on the "Refenced Libraries" item
3. Navigate to "Build Path>Configure Build Path..." and open it
4. Select "Libraries" tab in the opened dialog box
5. Select the "Add JARs..." button on the right side
6. Navigate to "Hiawatha>lib>json-simple-1.1.1.jar" select it and hit OK
7. Hit apply and OK

## ADB Installation and Operation notes

The adb.sh and RIOdroid.sh from Team Spectrum have Windows end of line characters (CRLF) that need to be changed to linux (LF). Check this before deploying robot code.

-When connecting and disconnecting the Nexus 5 from the RoboRio usb connection, ensure the robot is powered off. 


