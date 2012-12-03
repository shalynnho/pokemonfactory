Factory Project - Team 09
======

Fall 2012, CSCI 201 / 200  
Arjun Bhargava, Michael Gendotti, Ross Newman, Daniel Paje  
Adiran Cagaanan, Chris Gebert, Neetu George, Aaron Harris, Shalynn Ho, Vansh Jain, James Li, Harry Trieu, Matt Zecchini, Peter Zhang

**Follow the symlink _source_ to view the code.**    
You may also import the directory eclipse_fies/ into Eclipse to view the project. Actual codes are located under eclipse_files/src/

**If terminal is used to compile**    
You'll need to edit the image path. Find line 25 in `Utils.Constants.java`, change:

`public static final String IMAGE_PATH = "src/images/";`

to

`public static final String IMAGE_PATH = "images/";`


**Servers and clients are properly implemented.**    
To run our application, **start Networking/Server** first, and then run our managers:

* manager.FactoryProductionManager
* manager.PartsManager
* manager.KitManager
* manager.KitAssemblyManager
* manager.LaneManager
* manager.GantryRobotManager

** Make sure all managers are started before starting operations**

**Read the V2 Submission Notes**