COMPILE
javac Vase.java
java Vase.java <*int flag optional>

*
There is optional int flag that you can input to change the number of people that exists else by default it uses N = 100
ex. java Vase.java 50
to instead test for 50 people

Comparison of Strategies
Strategy 1
Pros:
- If there is nobody there then it's a simple check and the results will be seen very quickly
Cons:
- Could cause a pile up situation where if multiple people were to try to enter at once
- This causes delay when someone is attempting to leave the room since multiple people are trying to open the door
- This also causes people in the back to be unable to even reach the lock and check the door.
- As everyone is trying to test the same door, eventually they would have to release the door and let another person test it, and it will be unknown when they can reach it again.
Strategy 2
Pros:
- This allows people to continue on with their own individual activities before coming back and checking if it is availiable
- Similar to strategy 1, guest will be able to quickly determing if it is availiable with no one around
- This further improves the strategy one by 
Cons:
- Similar to strategey 1 if there are a lot of people crowding around the door to see if it is availiable then they will interfer with one another
- Also a situation could occur where the person leaving would have a crowd facing him as he is trying to leave.
Strategy 3
Pros:
-First come and First out ordering
-Fairness in the ordering of whoever shows up to the line
-Everyone is guarenteed to reach the door without hassle from being over crowding
-No matter the size of the party everyone could eventually see the crystal vase.
Cons:
- Space is needed for creating the line
- They would also need to enter the line instead of going straight through the door if the queue is empty.


In Conclusion

The strategy I choose is Strategy 3 because of the fairness and scaleability. This will be represented by having a ThreadPool handle the queue and assigning a random thread with a task.
