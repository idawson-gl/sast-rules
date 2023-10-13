// License: MIT (c) GitLab Inc.

#include <iostream>  
#include <algorithm>  
#include <vector>

using namespace std;  

int main()  
{  
    int newints[]={20,40,60,80,100};  
    int smallints[]={10,20};
    std::vector<int> newvector(newints, newints+5);  
    std::vector<int> smallvector(smallints, smallints);
    if(std::equal(newvector.begin(),newvector.end(), smallvector.begin())) // segmentation fault here
    {
        std::cout<<"Both the containers have matching elements.\n";  
    }
    else  
    {
        std::cout<<"Both the containers have difference elements.\n";  
    }

    return 0;  
}  