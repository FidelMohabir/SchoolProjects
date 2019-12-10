//Fidel Mohabir
//CS211 Section211
//HW10 - Fancy 8 queens
//I wasn't able to get this to point to the arrays wQ and bQ. char(219) wouldn't print properly either so I'm not sure if that is affecting it
#include <iostream>
#include <cmath>
#include <stdlib.h>
using namespace std;
bool isValid(int q[], int c) { //ok function checks both rows to the left and left up and down diaganols
	for (int i=0; i<c; i++)
		if (q[c]==q[i] || (c-i)==abs(q[c]-q[i]))
			return false;
	return true;
}
void backtrack(int& c) { //Backtracks through the problem til column = -1 then it will execute the program
	c--;
	if (c==-1)
		exit(1);
}
void print(int q[]) { //print function creates the fancy board that will be printed from the 1d array q[]
	int i,j,k,l;
	typedef char box[5][7];
	box bb,wb,*board[8][8], wQ={char(219)},bQ={' '}; //in addition to the variables Dr. Waxman gave, I intialized the box wQ to have all char(219) a.k.a the black squares
						       //bQ was similarly initialized to have the character ' ' a.k.a the blank spaces
	for(i=0;i<5;i++)
		for( j=0;j<7;j++) { //changes all positions in wb to ' ' and all positions in bb into char(219)
			wb[i][j]=' ';
			bb[i][j]=char(219);
		}
	for(i=0;i<8;i++)
		for(j=0;j<8;j++) //checks if i+j is even then puts the 2d array wb into board at this poisition else puts in bb
			if((i+j)%2==0)
				board[i][j]=&wb;
			else
				board[i][j]=&bb;
	cout<<" ";
	for(i=0;i<7*8;i++)
		cout<<'_';
	cout<<endl;
	for(i=0;i<8;i++)
		for(k=0;k<5;k++) {
			cout<<" "<<char(179); 
			for(j=0;j<8;j++)
				for(l=0;l<7;l++)
					cout<<(*board[i][j])[k][l];
			cout<<char(179)<<endl;
		}
	cout<<" ";
	for(i=0;i<7*8;i++)
		cout<<char(196);
	cout<<endl;
	for (i=0;i<5;i++) { //forms the visual Queens a.k.a crown
		if (i==1) {
			wQ[i][1]=' '; //since wQ is on a char(219) filled array it changes the crown portion to ' '
			wQ[i][3]=' '; //since wQ is on a char(219) filled array it changes the crown portion to ' '
			wQ[i][5]=' '; //since wQ is on a char(219) filled array it changes the crown portion to ' '
			bQ[i][1]=char(219); //since bQ is on a ' ' filled array it changes the crown portion to char(219)
			bQ[i][3]=char(219); //since bQ is on a ' ' filled array it changes the crown portion to char(219)
			bQ[i][5]=char(219); //since bQ is on a ' ' filled array it changes the crown portion to char(219)
		}	
		else if (i==2 || i==3) {
			wQ[i][1]=' '; //since wQ is on a char(219) filled array it changes the crown portion to ' '
			wQ[i][2]=' '; //since wQ is on a char(219) filled array it changes the crown portion to ' '
			wQ[i][3]=' '; //since wQ is on a char(219) filled array it changes the crown portion to ' '
			wQ[i][4]=' '; //since wQ is on a char(219) filled array it changes the crown portion to ' '
			wQ[i][5]=' '; //since wQ is on a char(219) filled array it changes the crown portion to ' '
			bQ[i][1]=char(219); //since bQ is on a ' ' filled array it changes the crown portion to char(219)
			bQ[i][2]=char(219); //since bQ is on a ' ' filled array it changes the crown portion to char(219)
			bQ[i][3]=char(219); //since bQ is on a ' ' filled array it changes the crown portion to char(219)
			bQ[i][4]=char(219); //since bQ is on a ' ' filled array it changes the crown portion to char(219)
			bQ[i][5]=char(219); //since bQ is on a ' ' filled array it changes the crown portion to char(219)
		}
				
	}
	for(i=0;i<8;i++)
       		 for(j=0;j<8;j++)
            		if((i+j)%2==0){ //Checks if row + column%2 is even which creates the checkerboard pattern
                		if (q[i]==j)
					board[i][j]=&bQ; //if value at q[i] is equal to j then point to bq
                		else 
                    			board[i][j]=&wb; //point to wb
                		
            		} 
			else { //in every i+j%2==1 a.k.a odd place a bb
                		if(q[i]==j)
                    			board[i][j]=&wQ; // if value at q[i] is equal to j then point to wQ 
                		 else 
                   			board[i][j]=&bb; //point to bb
            		}
}
int main(){
    int q[8],c=1; //creates 1d array q and sets column to 1
	q[0]=0;  //set first queen
    bool from_backtrack=false;
    while(true){
        while(c<8){ //goes through all columns      
            if(!from_backtrack)
		q[c]=-1; 
            from_backtrack=true;
            while(q[c]<8){
                q[c]++; //increments values in q[c]
                if(q[c]==8){ //prevents q[c] from being greater than 7
                    from_backtrack=true;
                    backtrack(c); 
                    break;
                }
                if(isValid(q,c)){ //checks if queen placement is valid
                    from_backtrack=false;
                    c++; //if so then go to next column
                    break;
                }   
            }       
        }
        print(q); //print once c=8
        from_backtrack=true;
        backtrack(c); //backtracks for other solutions
    }
}
