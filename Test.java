/*
Test.java
用于调用Person.java和Floor.java，从而进行测试和结果输出

author:guozheng
date:2015/07/20
*/

import java.lang.Integer;
import java.lang.Double;
import java.util.ArrayList;
import java.io.*;

public class Test
{
    final public static double  SAMPLEPERIOD    =   0.01;
    
    public static void main(String[] args)
    {
        if(args.length!=16)
        {
            System.out.println("输入参数数量不对");
            return;
        }
        else    System.out.println("开始计算-->");
        //for(int k=0;k<args.length;k++)
        //{
        //    System.out.println(args[k]);
        //}
    
        int  num;
        int  M  ;
        int  N  ;
        int  t  ;

        double  normalProbability[] =   new double[4];
        double  edgeProbability[]   =   new double[3];
        double  edgeProbability2[]  =   new double[3];
        double  cornerProbability[] =   new double[2];
       
        num =   Integer.parseInt(args[0]);
        M   =   Integer.parseInt(args[1]);
        N   =   Integer.parseInt(args[2]);
        t   =   Integer.parseInt(args[3]);

        normalProbability[0]=   Double.parseDouble(args[4]);
        normalProbability[1]=   Double.parseDouble(args[5]);
        normalProbability[2]=   Double.parseDouble(args[6]);
        normalProbability[3]=   Double.parseDouble(args[7]);
        
        edgeProbability[0]  =   Double.parseDouble(args[8]);
        edgeProbability[1]  =   Double.parseDouble(args[9]);
        edgeProbability[2]  =   Double.parseDouble(args[10]);
        
        edgeProbability2[0]  =   Double.parseDouble(args[11]);
        edgeProbability2[1]  =   Double.parseDouble(args[12]);
        edgeProbability2[2]  =   Double.parseDouble(args[13]);
        
        cornerProbability[0]    =   Double.parseDouble(args[14]);
        cornerProbability[1]    =   Double.parseDouble(args[15]);
        //num =   4;
        //M   =   2;
        //N   =   2;
        //t   =   2;
        Floor floor  =   new Floor(M,N,t); //初始化Floor
        floor.samplePeriod  =   Test.SAMPLEPERIOD;
        //normalProbability[] =   {0.25,0.25,0.25,0.25};
        //edgeProbability[]   =   {1/3,1/3,1/3};
        //edgeProbability2[]  =   {1/3,1/3,1/3};
        //cornerProbability[] =   {0.5,0.5};

        int  i,j;
        ArrayList<Person> personList =   new ArrayList<Person>();

        for(i=0;i<num;i++) 
        {
            //System.out.println("第"+i+"个人的初始化和参数设置");
            personList.add(new Person(floor,normalProbability,edgeProbability,edgeProbability2,cornerProbability,i+1));   //Person初始化
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
        //for(i=1;i<=M*N;i++)
        //{
        //    System.out.println("第"+i+"块底板-->");
        //    for(j=0;j<(int)(t/Test.SAMPLEPERIOD);j++)
        //    {
        //        System.out.print(floor.force[i-1][j] + "  ");
        //    }
        //    System.out.println("\n"); 
        //}

        System.out.println("计算结束，开始输出到文件");
        //将floor.force输出到文件
        for(i=1;i<=M*N;i++)
        {
            try
            {
                // Create file 
                FileWriter fstream = new FileWriter("../files/" +i+ ".txt");
                BufferedWriter out = new BufferedWriter(fstream); 
                for(j=0;j<(int)(t/Test.SAMPLEPERIOD);j++)
                {
                    String temp =   Double.toString( (j+1.0)*Test.SAMPLEPERIOD );
                    //if(temp.length()==3) 
                    int afterPoint  =   temp.length()-temp.indexOf(".")-1;    //小数点后面的位数
                    if(afterPoint==1) temp=temp+"0";
                    else temp=temp.substring(0,temp.indexOf(".")+3);
                   
                    String  temp2   =   Double.toString( floor.force[i-1][j] ); //受力值
                    afterPoint  =   temp2.length() - temp2.indexOf(".")-1;  //小数点后面的位数
                    if(afterPoint <= 4) temp2 = temp2;
                    else    temp2 = temp2.substring(0,temp.indexOf(".")+5);


                    //out.write(temp + " " + temp2 +"\r\n");
                    out.write(temp2 +"\r\n");
                    //else out.write(temp.substring(0,temp.indexOf(".")+3)  + "    " + Double.toString( floor.force[i-1][j] ) +"\n");

                }
                out.close();
            }
            catch(Exception e)
            {//Catch exception if any'
                e.printStackTrace();
                System.err.println("Error: " + e.getMessage());
            }
        
        }

            try
            {
                // Create file 
                FileWriter fstream = new FileWriter("../files/" + "random.txt");
                BufferedWriter out = new BufferedWriter(fstream); 
                for(int k=0;k<num;k++)
                {
                    out.write(personList.get(k).period + "\r\n");
                }
                out.write("\r\n\r\n"); 
                for(int k=0;k<num;k++)
                {
                    out.write(personList.get(k).randomPhase + "\r\n");
                }
                out.close(); 
            } 
            catch(Exception e)
            {//Catch exception if any'
                e.printStackTrace();
                System.err.println("Error: " + e.getMessage());
            }


    }
}
