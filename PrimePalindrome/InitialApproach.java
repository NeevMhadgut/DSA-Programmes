import java.util.*;

class Solution
{
    //TO CHECK IF IT IS A PRIME NUMBER:
    boolean isPrime(int n)
    {
        int i,k=0;
        for(i=1;i<=n;i++)
        {
            if(n%i==0)
                k++;
        }
        if(k==2)
            return(true);
        return(false);
    }

    //TO CHECK IF IT IS A PALINDROME
    boolean isPalin(int n)
    { 
        int t=n,r,p=0;
        while(t>0)
        {
            r=t%10;
            p=p*10+r;
            t=t/10;
        }
        if(n==p)
            return(true);
        return(false);
    }

    public int primePalindrome(int n) 
    {
        while (true)
        {
            boolean r1 = isPalin(n);
            boolean r2 = isPrime(n);
            if (r1 == true && r2 == true)
                return n;
            n++;
        }
    }
}
