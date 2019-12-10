//Fidel Mohabir
//CS211 Section 52B
//Homework #8
//I could not get this to print effectively. It also infinitely loops much like my last homework. I would appreciate knowing where I messed up
#include <iostream>
#include <cmath>
#include <stdlib.h>
using namespace std;
bool ok(int q[8], int arr, int c) { //Passes in the q array, the value at arr[c][temp] and the value of c
    for(int i=0; i<c; i++) { //for loop for n times. n= the current value of c
        if (q[i]==q[c]||(abs(q[c]-q[arr]) == 1)) //if there the value is the same or within 1 value place of each other (checks for consecutive numbers) 
			return false; //returns false or 0 if the if statement is true
    }
    return true;
}
void print(int q[]) { //prints the array into a cross
    cout << " " << q[1] << q[4] << " " << endl;
	cout << q[0] << q[2] << q[5] << q[7] << endl;
	cout << " "  << q[3] << q[6] << " " << endl << endl;
}
void backtrack(int &c){ //backtracks if value is over 8 or after print
    c--;
    if (c==-1) //if c is -1 then the program terminates
        exit(1);
}
int main() {
    int q[8]={0}, c=0, temp;
    int arr[8][5] ={ {-1,},{ 0,-1},{ 0, 1,-1},{ 0, 2,-1},{ 1, 2,-1},{ 1, 2, 3, 4,-1},{ 2, 3, 5,-1},{ 4, 5, 6,-1} };
    while(true){ //infinite loop so it can backtrack numerous times
       	while(c<8) { //The array size is max of 8 so as long as it is 0 to less than 8 we know we are still in the array
        	q[c]++; //Increments the value of q[c] ([q[0],q[1],q[2]...q[7])
            	if (q[c]>8) { //Makes sure the values stay within our own limitations of 1-8
                	q[c]=0; //if it does go past then reset the column and go back to the last column 
                	backtrack(c); //Backtracks to last column
                	break; //restart the while loop
            	}
	    	temp = 0; //temp incrementation
            	while (temp < 5){ //goes through max size of the array that checks adjacency (arr[][5]) 
                	if(arr[c][temp]==-1){ //if the last element of the adjacency which is always a -1 then go to the next column
                    		c++;
                    		if(c==8) {
                        		print(q);
                        		backtrack(c);
				}
			 break; //breaks out of the while loop when this occurs
			}
			temp++; //incrementation for temp
		}
		if(ok(q,arr[c][temp],c)==0) //checks if the ok function is fall or not
			break; //breaks if it is
            } 	
    }
    return 0;
}
