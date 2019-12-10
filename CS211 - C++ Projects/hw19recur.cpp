//Fidel Mohabir
//CSCI211 52B
//Shortest path with recursion 
//Wasn't able to get this to fill in the memo_cost recorrectly would appreciate feedback
#include<iostream>
using namespace std;
const int rows = 5;
const int cols = 6;

int cost(int i, int j,int memo_cost[rows][cols]){ // i is the row, j is the column
    int weight[rows][cols]={ //i put the weight inside the recursive call
                            {3,4,1,2,8,6},
                            {6,1,8,2,7,4},
                            {5,9,3,9,9,5},
                            {8,4,1,3,2,6},
                            {3,7,2,8,6,4}
                        };

	//base case
	if(j==0){
    	memo_cost[i][j] = weight[i][0]; //if the column is 0 set the memo_cost[i][0] to whats located in the weight array
    	return weight[i][0]; //return the weight[i][0] 
	}
	// recursive call
	int left,up,down;
	left = cost(i, j-1, memo_cost); //checks the left 
	up = cost((i-4)%5, j-1, memo_cost); //checks up
	down = cost((i+1)%5, j-1, memo_cost); //checks down

	int min = left; //set min to left
	//do not forget memoization
	if (up<min) //check if up is less than whats in min
		min = up;
	if (down<min) //check if up is less than whats in min
		min = down;
	return weight[i][j]+min; //return the weight+min value
	}
int main(){
    int memo_cost[rows][cols]={0}; //initialie memo_cost
    int shortest=0, h; //h represents the rows
    for (int i=0; i<rows; i++)
        if (shortest==0 ||shortest>cost(i, cols-1, memo_cost)) {
                shortest=cost(i, cols-1, memo_cost); //checks for the smallest value in the last column
                h=i;
        }

    int route[cols]={-1};
    route[cols-1]= h;
    for(int c=cols-2;c>=0;c--){
        int left = memo_cost[h][c];
        int up = memo_cost[(h+4)%5][c];
        int down = memo_cost[(h+1)%5][c];
        int min=left;
        if (min>up) {
            h--;
        }
        else if (min>down) {
            h++;
        }
        route[c]= h;
    }
    //print route
    for(int i=0;i<cols;i++){
        cout<<"route "<<i<<" is: "<<route[i]<<" row."<<endl;
    }
    cout<<"the shortest path is of length "<< shortest << endl;
    return 0;
} 
