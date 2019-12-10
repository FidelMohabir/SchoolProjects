#include <iostream>
using namespace std;
//Fidel Mohabir
//Cs211 Section 52
//Problem 2

/* The function takes in two arrays and the array length
I intialize tempA at the length of the two arrays entered this will copy array a[]
It will allow the program to shift the array without changing positions in the original array a[]
I initialized tempNum at 0 which holds the value of tempA[n-1] while tempA's value in the same position changes
I initialized count to serve as a counter confirming that array tempA[] and b[] are equal at each value
The first for loop transfers the value of a at position i to tempA at position i
The second for-loop shifts the array TempA if A at c does not equal array b at c
This runs for a maximum of n times which is the array length
The third for-loop double checks that each value is the same in both array tempA and array b at all positions
If it is then the counter will equal n (the length of the array)
The function ends by seeing if count is equal to n which returns a true result otherwise it returns false
I included the example presented in the problem to test
*/

bool equivalent (int a[], int b[], int n) {
        int tempA[n];
        int tempNum=0;
        int count=0;
        for (int i=0; i<n; i++) {
                tempA[i]=a[i];
        }
        for (int c=0; c<n; c++) {
                if (tempA[c]!=b[c]) {
                        for (int j=0; j<n; j++) {
                                tempNum=tempA[n-1];
                                tempA[n-1]=tempA[j];
                                tempA[j]=tempNum;
                        }
                }
        }
        for (int k=0; k<n; k++) {
                if (tempA[k]==b[k])
                        count++;
        }
        if (count==n)
                return true;
        return false;
}
int main() {
	int a[]={1,2,3,4,5};
	int b[]={3,4,5,1,2};
	if (equivalent(a,b,5))
		cout << "Array a is indeed a right shifted array of Array b" << endl;
	else
		cout << "Array a is not a right shifted array of Array b" << endl;
	return 0;
}
