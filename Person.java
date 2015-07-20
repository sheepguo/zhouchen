/*
Person.java
表示每个人的走路情况

author:guozheng
date:2015/07/20
*/

import java.util.Random;

public class Person
{
    final public static int UP      = 0;   
    final public static int RIGHT   = 1;   
    final public static int DOWN    = 2;   
    final public static int LEFT    = 3;  

    final public static double  SAMPLEPERIOD    =   0.01;   
    
    int startDelay; //起步延时，单位是0.01s
    int direction;  //上一次行动时的方向；0是无方向，1234分别代表上下左右

    //double  startProbability[4];    //开始时，四个方向的出现概率，分别是上下左右
    double  normalProbability[4];   //正常情况下，四个方向的出现概率，分别是前后左右；
    double  edgeProbability[3];     //当走到边沿时，三个方向的出现概率，分别是前左右
    double  cornerProbability[2];   //当走到角落时，两个方向的出现概率，分别是向后和向边上
   
    double  normalSum[4];           //正常情况下，四个方向的累加值，用于确认方向使用，在构造函数中赋值，在setDirection()中使用
    double  edgeSum[3];
    double  cornerSum[2];

    int position;   //这个人现在所处的位置
    int period;     //实时的走路周期
    int countDown;  //倒计时，用来标志什么时候跨步
    Random  random  =   new Random();
    Floor   floor;


    public Person(Floor floor, double normalProbability[], double edgeProbability[], double cornerProbability[])
    {
        this.floor                  =   floor;
        this.normalProbability[0]   =   normalProbability[0];
        this.normalProbability[1]   =   normalProbability[1];
        this.normalProbability[2]   =   normalProbability[2];
        this.normalProbability[3]   =   normalProbability[3];
        this.edgeProbability[0]     =   edgeProbability[0];
        this.edgeProbability[1]     =   edgeProbability[1];
        this.edgeProbability[2]     =   edgeProbability[2];
        this.cornerProbability[0]   =   cornerProbability[0];
        this.cornerProbability[1]   =   cornerProbability[1];
       
        normalSum[0]    =   normalProbability[0];
        normalSum[1]    =   normalSum[0]    +   normalProbability[1];
        normalSum[2]    =   normalSum[1]    +   normalProbability[2];
        normalSum[3]    =   normalSum[2]    +   normalProbability[3];
   
        edgeSum[0]      =   edgeProbability[0];
        edgeSum[1]      =   edgeSum[0]      +   edgeProbability[1];
        edgeSum[2]      =   edgeSum[1]      +   edgeProbability[2];
    
        cornerSum[0]    =   cornerProbability[0];
        cornerSum[1]    =   cornerSum[0]    +   cornerProbability[1];
    }

    public void setPeriod() //每一步走完后，需要重新确认下一步的周期period是多少
    {
        double  fre     =   random.nextGaussian()*0.483+1.8295; //每一步的频率根据高斯分布得到
        if(fre<0)   fre =   0;
        
        period          =   (int)(fre/SAMPLEPERIOD);
    }

    public void setDirection(int position)  //每一步走完后，需要根据当前的位置，确认下一步的行走方向
    {
        int M   =   floor.getM();
        int N   =   floor.getN();
        double  temp    =   random.nextDouble();

        if(position == 0 || position == M-1 || position == M*N-1 || position == M*N-M)      //如果处于角落
        {
            if(temp>edgeSum[0]) 
            {
                direction   =   (direction-1+4)%4; //向左转
                if(nextPosition(position,direction) >=M*N || nextPosition(position,direction) < 0)  //如果向左转超出了范围，就改为向右转
                    direction   =   (direction+1)%4; //向右转
            }
            else direction   =   (direction+2)%4; //向后转
        }
        else if(position < M || position > M*N-M || position%M == 0 || position%M == M-1)   //如果处于边沿
        {
            if(temp>edgeSum[1]) direction   =   (direction+1)%4; //向右转 
            else if(temp>edgeSum[0]) direction   =   (direction-1+4)%4; //向左转 
            else    direction   =   (direction+2)%4; //向后转 
        }
        else    //如果处于中间位置
        {
            if(temp>normalSum[2]) direction =  (direction+1)%4; //向右转 
            else if(temp>normalSum[1]) direction =  (direction-1+4)%4; //向左转 
            else if(temp>normalSum[0]) direction =  (direction+2)%4; //向后转 
            //如果向前，则方向不变
        }
    }

    public int nextPosition(int oldPosition, int direction)
    {
        int newPosition;
        int M   =   floor.getM();

        if(direction == UP) newPosition = oldPosition - M;
        else if(direction == RIGHT) newPosition = oldPosition + 1;
        else if(direction == DOWN)  newPosition = oldPosition - M;
        else if(direction == LEFT)  newPosition = oldPosition - 1;
        
        return  newPosition;
    }


    public void setStartCondition() //设置第一步的参数
    {
        position    =   random.nextInt(floor.getM()*floor.getN());  //初始位置，从0到M*N-1
        
        direction   =   random.nextInt(4);                          //第一步的方向 
        
        double  fre     =   random.nextGaussian()*0.483+1.8295;     //第一步的频率根据高斯分布得到
        if(fre<0)   fre =   0;
        period          =   (int)(fre/SAMPLEPERIOD);
        
        startDelay  =   (int) (random.nextDouble()*peroid);         //第一步的延时，单位是0.01s

        countDown   =   period+startDelay;                          //倒计时，等于周期加上延时
    }

    public void move()  //根据countDown进行判断是否移动，如果移动，则触发floor.setForce()，同时设置新的移动方向和周期
    {
        countDown--;
        if(countDown==0)    //表示一个周期到了，可以跨出一步了
        {
            position    =   nextPosition(position,direction);
            floor.setForse(position,period);
            setPeriod();
            setDirection(position);

        }

    }

}
