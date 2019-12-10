//Fidel Mohabir
//CSCI211 52B
//Shortest path - non recursive
#include <iostream>
using namespace std;
const int rows = 5;
const int cols = 6;

int main(){
    int weight[rows][cols]={
                            {3,4,1,2,8,6},
                            {6,1,8,2,7,4},
                            {5,9,3,9,9,5},
                            {8,4,1,3,2,6},
                            {3,7,2,8,6,4}
                        };

    int memo_cost[rows][cols]={0};
    for (int i=0; i<rows; i++)
        memo_cost[i][0]=weight[i][0]; //fills in first column of memo_cost with first column of weight

    for (int j=1; j<cols; j++) { // fills in the rest of memo_cost array
        for (int i=0; i<rows; i++) {
                int left = memo_cost[i][j-1];
                int up = memo_cost[(i+4)%5][j-1]; //so there are no negative numbers and the array is cylindrical
                int down = memo_cost[(i+1)%5][j-1]; //array is cylindrical
                int min=left;
                if (min>up)
                        min=up;
                if (min>down)
                        min=down;
                memo_cost[i][j]=(min+weight[i][j]); //adds weight[i][j] to the min
        }
   }
    int shortest=0, h; //intialize h to represent row
    for (int i=0; i<rows; i++) // checks the lowest number in the last column
        if (shortest==0 ||shortest>memo_cost[i][cols-1]) {
                shortest=memo_cost[i][cols-1];
                h=i;
        }
    int route[cols]={-1};
    route[cols-1]= h;
    for(int c=cols-2;c>=0;c--){ //checks the lowest numbers going backwards and increments h(the rows) appropriately)
        int left = memo_cost[h][c];
        int up = memo_cost[(h+4)%5][c];
        int down = memo_cost[(h+1)%5][c];
        int min=left;
        if (min>up && down>up) { // checks if up is less than the min
            h--;
        }
        if (min>down && up>down) { // checks if down is less than the min
            h++;
        }
        route[c]= h; sets the route@c to h(row)
    }

    //print route
    for(int i=0;i<cols;i++){
        cout<< "route " << i << " is: " << route[i] << " row." << endl;
    }
    cout << "the shortest path is of length " << shortest << endl;
    return 0;
}


