# Radioactivity_Sim 

Radioactivity_Sim is an experimental program with the goal of predicting the occurrance of all radioactive decay events (and the subsequent events precipated by those events) which cascade from the existance of a user-defined sample of source nuclei.

Radioactivity_Sim is experimental software that is in development.  At any time the developer may rewrite substantial portions of the code and those rewrites may be released with errors.  Furthermore, the program has not been thoroughly tested for accuraccy and agreement with experimentally measured values.  Therefore users are advised not to rely on the output of this program for any calculation that matters. 

The program has reached a state where running trying to test and debug every feature is as or more time consuming then writing additional code.  Therefore I have uploaded a "build" of the project in the form of an executable jar file "Radioactivity_Sim_Terminal.jar" and I invite anyone to download, test it, and reports errors, issues, or suggestions.  I still recommend that anyone verify any of the calculations by comparison with hand calcs or other programs.  While the algorithms seem to be working, there may still be issues with it or with any of the functions that bring the results to the screen.

Running the program:

On a linux machine: install java

The actual install method varies by distro but this can typically be done (with an internet connection) with one of the following commands in a terminal:

sudo apt-get install java //Debian or Ubuntu
sudo yum install java //Fedora

Then clone the git repository with:

git clone https://github.com/jwhart175/Radioactivity_Sim

Change directories and run the program:

cd Radioactivity_Sim
java -jar Radioactivity_Sim_Terminal.jar

The terminal should then open up for you.  Type "help" to get a list of commands, and the first one that I suggest is "set inputDir <yourDirectory>" because in order to get anything to work, you must first point the program towards the dirctory of the Radioactivity_Sim/input files which contain all of the rules by which the program will be calculating.

On windows: 

I forget how java executables exactly work on windows, but if I remember correctly, after you've made sure that java is installed from a trusted source, just go the github website and download the .jar file and all of the input files.  Then double-click on the .jar file and the terminal should open.  Then the first thing to do will again be to set the actual directory to which you downloaded the input files using "set inputDir <yourDirectory>" 

# Contact info

Jonathan Wayne Hart<br> 
<a href="mailto:jwhart1@gmail.com">jwhart1@gmail.com</a><br>
<a href="http://jonathanhartpage.com/hartwp/">jonathanhartpage.com</a><br>

