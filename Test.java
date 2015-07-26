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
    final public static double  SAMPLEPERIOD    =   0.01;
    
    public static void main(String[] args)
    {
        int  num =   1;
        int  M   =   2;
        int  N   =   2;
        int  t   =   2;
        Floor floor  =   new Floor(M,N,t); //初始化Floor
        double  normalProbability[] =   {0.25,0.25,0.25,0.25};
        double  edgeProbability[]   =   {1/3,1/3,1/3};
        double  cornerProbability[] =   {0.5,0.5};

        int  i,j;
        ArrayList<Person> personList =   new ArrayList<Person>();

        for(i=0;i<num;i++) 
        {
            System.out.println("第"+i+"个人的初始化和参数设置");
            personList.add(new Person(floor,normalProbability,edgeProbability,cornerProbability));   //Person初始化
            personList.get(i).setStartCondition();  //设置第一步的参数
        }

        //进行t/0.01次循环，每次循环是一个最小的时间单位
        for(i=1;i<=(int)(t/Test.SAMPLEPERIOD);i++)
        {
            floor.setCurrentTime(i);
            for(j=0;j<num;j++)
            {
                personList.get(j).move();
            }
        }

        //将floor.force进行输出
        for(i=1;i<=M*N;i++)
        {
            System.out.println("第"+i+"块底板-->");
            for(j=0;j<(int)(t/Test.SAMPLEPERIOD);j++)
            {
                System.out.print(floor.force[i-1][j] + "  ");
            }
            System.out.println("\n"); 
        }
        
    }

}
