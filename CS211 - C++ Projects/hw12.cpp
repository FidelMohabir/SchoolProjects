//Fidel Mohabir
//CS211 Section 52B
//HW #12 - Intregral Problem
#include <iostream>
using namespace std;
typedef double(*FUNC)(double);
double integrate(FUNC f, double a, double b);
double line(double x) { //f(x)=x
	return x;
}
double square(double x) { //f(x)=x squared
	return x*x;
}
double cube(double x) { //f(x)=x cubed
	return x*x*x;
}
int main() {
	cout << "The integral of f(x)=x between 1 and 5 is:" << integrate(line,1,5) << endl;
	cout << "The integral of f(x)=x^2 between 1 and 5 is:" << integrate(square, 1, 5) << endl;
	cout << "The integral of f(x)=x^3 between 1 and 5 is:" << integrate(cube, 1, 5) << endl;
	return 0;
}
double integrate(FUNC f, double a, double b) { //function uses midpoint riemann sums
	double tempArea=0, c=a; //stores the area under the curve
	for (c=a; c<b;c=(c+0.001)) //goes from a to b in increments of 0.001)
		tempArea+=((f(c)+f(c+0.001)/2)*0.001); /*add the both f(x) and f(x+.001) then divide by 2 to find midpoint of both points then multiply
						   by .001 which is the distance between f(x) and (x+.001)*/
   	 return tempArea;
}
