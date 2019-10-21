
Motion Detector(motiondetector) is an library used for detecting device motion like Free fall, Rotation, Shake etc.
At present, we implemented only FreeFall detection.

This library can be devided into 4 parts(module) basically.
1. Pluggin
2. Device Motion
3. Data &
4. Event logger

Pluggin:
This containing two classes
MotionDetectorProvider and MotionDetector
This is used for accessing client application. MotionDetector is Singletone class, which is having
generic method to access the db content for client apps. Where as provider get executed when user launch the app and 
initialization is doing by provider. Initialization is doing by starting foreground service.


Device Motion:
The aim this module to detect any kind of motions. This is implemented in the manner of scalable way. Tomorrow if we want
to integrate something, then without changing or modifying existing code we can simply add new class with the help of 
enum(MONITOR_MOTIONS). MONITOR_MOTIONS is enum used to define any kind of motions. This module contains 
MotionDetectorService which registered with sensor events. So based on sensor event we can decide what kind of 
motion is happened by executing one by one by Motion class like FreeFallMotion, RotateMotion classes etc

Data:
This is another module responsible for storing content once the event occurred. Storing we can use with the help of 
Event logger module. Where as retrieving is happening from plugin module. This data module made up of with Room 
jetpack component. Here we are using repository design pattern also, so that in future we can deside whether need to 
store in Room db or cloud or both. Room is made up of with database class, entity class and dao class etc.

Event Logger:
And finally this component made up of with builder pattern, so this pattern reduce the effort of storing the content to db.
If we want to store something else then just need to be modify the builder class and dao class respectively.
With these approach, it's making easy way of implementation with scalable , testable as well as more readable.


