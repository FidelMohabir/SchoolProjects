//Fidel Mohabir
//CS211 Section52B
//Hw14 - N Queens
//To make it print the solutions from 1 to N I had to remove the take the if statement out of the backtrack function
//and make it break from certain parts of while loops. Is there any easier way to do this  inside the function?
#include <iostream>
#include <cmath>
#include <stdlib.h>
using namespace std;
bool ok(int q[], int c) {
    for(int i=0; i<c; i++)
		if(q[i]==q[c] || abs(q[i]-q[c])==(c-i)) //Row&Diaganol Test
			return false;
    return true;
}
void backtrack(int& col) {
	col--; //ONLY backtracks to last column doesn't exit if it hits -1
}
int main(){
    int n; 
    cout<<"How any Queens on this board? " << endl;
    cin>>n; //Enters the user input into variable n
    for (int i = 1; i <= n; ++i) { //For loop so I can print the solutions for 1 through n 
        int *q = new int[i];  //Sets up dynamic array
        int c=0, counter=-1; //Since counter can = 0 (e.g. 2 queens), I set count to -1 because I count++ at the end
	q[0]=0; //set queen in first column first row
        bool from_backtrack=false;
        while(true) {
            while(c<i){ // as long as c is less than i(e.g. 1 to n)       
                if(!from_backtrack)
			q[c]=-1;
                from_backtrack=true;
                while(q[c]<i){
                    q[c]++;
                    if(q[c]==i){
                        from_backtrack=true;
                        backtrack(c);
                        break;
                    }
                    if(ok(q,c)){
                        from_backtrack=false;
                        c++;
                        break;
                    }   
                }
                if(c==-1)
			break; //checks if in -1 column (Doesn't exist) then breaks out of while loop (instead of terminating program)
            }
	    counter++; //increment counter here
	    from_backtrack=true;
	    backtrack(c);
	    if(c<=-1)
	    	break; //Since c at this point can equal less than -1 (due to going through backtrack twice possibly break again)
	}
        cout<<"There are "<< counter <<" solutions to the "<< i <<" queens problem."<<endl; //cout number of solutions in that given if
	delete[] q; //Gets rid of dynamic array
   }
   return 0;
}
