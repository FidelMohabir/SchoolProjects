//Fidel Mohabir
//CSCI 211 52B
//k bishops
//wasn't able to get this to work would like feedback
#include <iostream>
#include <stdlib.h>
#include <cmath>
using namespace std;
bool ok(int* q, int c, int n) {
	for (int i=0; i<c; i++)
		if (abs((q[i]%n)-(q[c]%n))==abs((q[c]/n)-(q[i]/n))) //if row@c-row@i==col@c-col@i
			return false;
	return true;
}
void backtrack(int c) { //backtrack function
	c--;
}
int main() {
	int n, k, c, counter, placement;
	while (true) {
		cout << "What is the size of the board?" << endl;
		cin >> n; 
		if (n==-1) //checks if board size is -1
			break;
		cout << "Enter the number of bishops" << endl;
		cin >> k;
		while (k>n) {
			cout << "There cannot be more bishops than the size of the board, enter another value" << endl;
			cin >> k; //makes sure there are no more than n bishops on the board
		}
		int *q = new int[k];
		c = 0;
		counter = 0;
		placement = -1; //placement in first box
		q[0]= 0;
		bool from_backtrack=false;
		while(true) {
			while (c<k) { //problem line here. Not entering this loop for some reason
				if (!from_backtrack)
					q[c] = placement; //q[c]=placement (-1 the first time)
				while (q[c]<(n*n)) {
					q[c]++; //increment q[c] and check if it is ok
					if (q[c]==(n*n)) {
						from_backtrack=true;
						backtrack(c);
						break;
					}
					if (ok(q,c,n)) {
						from_backtrack=false;
						c++;
						break;
					}
				}
				placement = q[c];
				if (c==-1) //breaks if c=-1
					break;
			}
			counter++; //counter for possible outcomes
			from_backtrack=true;
			backtrack(c);
			if (c<0) //breaks when c less than 0
				break;
		}
		cout << "There are " << counter << " solutions the " << k << " bishops in " << n << " size board" << endl;
		delete[] q;
	}
	return 0;
}
