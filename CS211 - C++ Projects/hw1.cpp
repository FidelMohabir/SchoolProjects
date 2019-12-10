#include <iostream>
using namespace std;
int main() {
//Fidel Mohabir
//CS211 Section 52 Homework 1
//Problem 1

/* Variable z will hold the product of i*i as it goes through the for-loop and is being checked for the last two digits being both odd
The for-loop will infinitely loop unless a number modulus 2 (the very last number) results in an odd number
Then it will move on to check the number divided by 10 modulus 2 (which gives the second to last number) and sees if this number is also odd.
If it is then the for-loop will first cout what z is then break to end the loop. */
	int z; 
	for (int i=0;; i++) {
		z=i*i;
		if (z>10) {
			if (z%2==1) {
				if ((z/10)%2==1) {
					cout << z << endl;
					break;
				}
			}
		}
	}
	return 0;
}
