COMPILE
javac Cupcake.java
java Cupcake.java <*Int Flag>

*
There is optional int flag that you can input to change the number of people that exists else by default it uses N = 100
ex. java Cupcake.java 50
to instead test for 50 people

STRATEGY
Following the prisoner strategey that we discussed in class, we assigne one person to be leader. When the leader encounters an empty plate they will 
count +1 and request a new cupcake. Everyone else will keep on entering the maze and eat the cupcake at the end only one and leave it empty if they
reach an empty cupcake. This will terminate when the leader's count reaches the number of participants meaning that everyone has entered the maze.