# INFSCI2711_Homework3

## Team Member:
Chuyu Wang(chw122@pitt.edu)<br/>
Jie Wen(jiw100@pitt.edu)<br/>
Yue Zhao(yuz82@pitt.edu)<br/>

## Tutorial
It explains how to use the Eclipse environment in the virtual machine, including how to create a project, how to run jobs, and how to debug jobs. 

### File Dictionary
WordCount & WordCount.jar

## Question 1
Write a Hadoop MapReduce program, which outputs the number of words that start with each letter. This means that for every letter we want to count the total number of words that start with that letter. In your implementation ignore the letter case, i.e., consider all words as lower case. You can ignore all non-alphabetic characters.

### File Dictionary
Question 1 & Question1.jar<br/>
Result:Question1/output

### Description
The goal of this question is to count the total number of words that start with that letter.<br/>
For using the approach of Hadoop MapReduce, we design to get all the words that start with all letters through Map, and then using Reduce to count the words that start with the same letter to get the result.

### Java File
https://github.com/chuyuwang/INFSCI2711_Homework3/blob/master/Question1/src/edu/pitt/sis/infsci2711/wordcount/WordCount.java
### Input
https://github.com/chuyuwang/INFSCI2711_Homework3/blob/master/Question1/pg100.txt
### Output
https://github.com/chuyuwang/INFSCI2711_Homework3/blob/master/Question1/output/part-r-00000
### Result
a	85667<br/>
b	45597<br/>
c	34694<br/>
d	29830<br/>
e	19116<br/>
f	36930<br/>
g	21021<br/>
h	60736<br/>
i	62305<br/>
j	3354<br/>
k	9491<br/>
l	29697<br/>
m	55800<br/>
n	26834<br/>
o	43590<br/>
p	27860<br/>
q	2378<br/>
r	14396<br/>
s	66069<br/>
t	126395<br/>
u	9185<br/>
v	5745<br/>
w	59844<br/>
x	14<br/>
y	25888<br/>
z	79<br/>

### How to Run
1. Download the file Question1.jar and text file pg100.txt
2. You have two way to run the project.<br/>
   a.You can run it in the eclipse.<br/>
    • Import the Question1 and pg100.txt to the eclipse <br/>
    • Right-click on the project and select Run As → Run Configuration <br/>
    • Enter the name in the Name field,Project name and Path in the Main class field. <br/>
    • Switch to the Arguments tab and put pg100.txt output in the Program arguments field. <br/>
    • Run the project <br/>
   b.You can run it in the terminal.<br/>
    • Open a terminal and run the following commands:<br/>
      hadoop fs -put pg100.txt <br/>
      hadoop jar Question1.jar edu.pitt.infsci2711.wordcount.WordCount pg100.txt output <br/>
    • Run the command: <br/>
      hadoop fs -ls output<br/>
    • Run the command: <br/>
      hadoop fs -cat output/part\* | head

## Question 2
Write a MapReduce program in Hadoop that implements a simple “People You Might Know” social network friendship recommendation algorithm. The key idea is that if two people have a lot of mutual friends, then the system should recommend that they connect with each other.

### File Dictionary
Question 2 & Question2.jar<br/>
Result:Question2/output

### Description
Map: In the Map process, we use two ways to store the relation between users. First, User ID is regarded as the key, and value includes the relational user ID and relationship. Second, each two friends of one User ID has relation with User ID. so key is one of the friends and value includes another friend and their mutual friend.
For example, User ID 0 makes friends with User ID 1,2,3. The output using Mapping is (0,(1,-1)),(0,(2,-1)),(0,(3,-1)),(1,(2,0)),(2,(1,0)),(1,(3,0)),(3,(1,0)),(2,(3,0)),(3,(2,0))
<br/><br/>
Reduce: In the Reduce process, we need to consider all the same conditions, if two users are friends already, they don't need to recommend friends each other, so we need to check this condition, and if they are not friends yet, all the mutual friends will be count into the result.
<br/><br/>
Rank: After getting the result of mutual friends, we need to compare the number of mutual friends and return the rank result of mutual friends to get the result of recommendation.

### Java File
https://github.com/chuyuwang/INFSCI2711_Homework3/blob/master/Question2/src/edu/pitt/sis/infsci2711/wordcount/FriendRecom.java
<br/>
https://github.com/chuyuwang/INFSCI2711_Homework3/blob/master/Question2/src/edu/pitt/sis/infsci2711/wordcount/FriendsWritable.java
### Input
https://github.com/chuyuwang/INFSCI2711_Homework3/blob/master/Question2/soc-LiveJournal1Adj.txt
### Output
https://raw.githubusercontent.com/chuyuwang/INFSCI2711_Homework3/master/Question2/output/part-r-00000

### Result
924: 2409,11860,15416,43748,45881, <br/>
8941: 8943,8944,8940, <br/>
8942: 8939,8940,8943,8944, <br/>
9019: 9022,9023, <br/>
9020: 9021,9016,9017,9022,9023, <br/>
9021: 9020,9016,9017,9022,9023, <br/>
9022: 9019,9020,9021,9016,9017,9023, <br/>
9990: 13134,13478,13877,34299,34485,34642,37941, <br/>
9992: 9989,35667,9991, <br/>
9993: 9991,13134,13478,13877,34299,34485,34642,37941, <br/>

### How to Run
1. Download the file Question2.jar and text file soc-LiveJournal1Adj.txt
2. You have two way to run the project.<br/>
   a.You can run it in the eclipse.<br/>
    • Import the Question2 and soc-LiveJournal1Adj.txt to the eclipse <br/>
    • Right-click on the project and select Run As → Run Configuration <br/>
    • Enter the name in the Name field,Project name and Path in the Main class field. <br/>
    • Switch to the Arguments tab and put soc-LiveJournal1Adj.txt output in the Program arguments field. <br/> 
    • Run the project <br/>
   b.You can run it in the terminal.<br/>
    • Open a terminal and run the following commands:<br/>
      hadoop fs -put soc-LiveJournal1Adj.txt <br/>
      hadoop jar Question2.jar edu.pitt.infsci2711.wordcount.FriendRecom soc-LiveJournal1Adj.txt output <br/>
    • Run the command: <br/>
      hadoop fs -ls output<br/>
    • Run the command:<br/>
      hadoop fs -cat output/part\* | head
