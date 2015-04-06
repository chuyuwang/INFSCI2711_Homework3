# INFSCI2711_Homework3
## Question 1
Write a Hadoop MapReduce program, which outputs the number of words that start with each letter. This means that for every letter we want to count the total number of words that start with that letter. In your implementation ignore the letter case, i.e., consider all words as lower case. You can ignore all non-alphabetic characters.
### File Name
Question 1
Result:output
### Description
The goal of this question is to count the total number of words that start with that letter.<br/>
For using the approach of Hadoop MapReduce, we design to get all the words that start with all letters through Map, and then using Reduce to count the words that start with the same letter to get the result.
### Java File

### Input

### Output

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
1. Download the file Question1 and text file pg100.txt
2. You have two way to run the project.<br/>
   a.You can run it in the eclipse.<br/>
     You need to choose run configuration,
   b.You can run it in the terminal.<br/>
## Question 2
