# TechneDraw
This is a short test project for an interview process.
## Description

This project involves the creation of a basic command line drawing application written entirely in Kotlin 1.6.2. 
It uses Gradle to build and manage dependencies.
Based on command line input this application is capable of drawing lines and rectangles on a domain, and filling in connected "pixels" of like "colour" using a recursive flood fill algorithm.

## Getting Started

### Dependencies

Must be running at least Java 11.

### Installing

* Clone this repository on to your machine
* open a command prompt into this folder and type ```.\gradlew install```.
* once this step has completed, go ahead and run ```.\gradlew clean build```. This will build the application.

### Executing program

* Open a command prompt to the root of the project (same level as `build.gradle.kts`)
* Inside this command prompt type `.\gradlew clean run`. This will check if you have an instance already built, and will run it. If you do not have an instance built, it will build one.
* Once the application is running, use `C w h` where `w` `h` are the width and height of the domain. **The maximum size of a domain is 500x500 characters.**
* Once you have a domain, you can draw lines using `L x1 y1 x2 y2` where `x1` `y1` are the coordinates for the start of the line, and `x2` `y2` are the coordinates of the end point of the line.
* Or you can draw rectangles using one of two methods. Either by drawing a line where `x1` != `x2` or `y1` != `y2` or by using `R x1 y1 x2 y2` as above.
* In order to fill an area, you can use the `B x y c` command where `x` `y` are the coordinates of the initial poin to fill, and `c` is the colour which the area will be filled with.
* To quit the application, type `Q`.

## Authors
Alex Pettus Agathocleous