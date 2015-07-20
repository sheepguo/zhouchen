/*
Test.java
用于调用Person.java和Floor.java，从而进行测试和结果输出

author:guozheng
date:2015/07/20
*/

import java.lang.Integer;
import java.lang.Double;
import java.util.ArrayList;

public class Test
{
    public static void main(String[] args)
    {
        final public static double  SAMPLEPERIOD    =   0.01;
        int  num =   1;
        int  M   =   2;
        int  N   =   2;
        int  t   =   10;
        Floor floor  =   new Floor(M,N,t); //初始化Floor
        double  normalProbability[] =   {0.25,0.25,0.25,0.25};
        double  edgeProbability[]   =   {1/3,1/3,1/3};
        double  cornerProbability[] =   {0.5,0.5};

        int  i,j;
        ArrayList<Person> personList =   new ArrayList<Person>();

        for(i=0;i<num;i++) personList.add(new Person(floor,normalProbability,edgeProbability,cornerProbability));   //Person初始化
        
        for(i=1;i<=(int)(t/SAMPLEPERIOD);i++)
        {
            floor.setCurrentTime(i);
            for(j=0;j<num;j++)
            {
                personList.get(j).move();
            }
        }










    }

}
