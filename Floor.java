/*
Floor.java
表示一块地板的受力情况

author:guozheng
date:2015/07/20
*/


public class Floor
{
    int M;  //地板的长度
    int N;  //地板的宽度
    int t;  //总测试时间，单位是秒
    
    int sample; //总采样点数，等于t/0.01;
    double  force[][];
    int currentTime;    //当前时间，单位是0.01s


    public Floor(int M, int N, int t)
    {
        this.M  =   M;
        this.N  =   N;
        this.t  =   t;
        sample  =   (int)(t/0.01);
        force   =   new double[M*N][sample];
        currentTimt =   0;

        int i,j;
        for(i=0;i<M*N;i++)
            for(j=0;j<sample;j++)
                force[i][j] =   0;
    }

    public int  getM()
    {
        return M;
    }

    public int  getN()
    {
        return N;
    }
   

    public void setCurrentTime(int currentTime)
    {
        this.currentTime    =   currentTime;
    }

    public static setForce(int position, int period)    //当有人正好踩上某个位置时，触发setForce，其中position是位置点，period是这一脚对应的周期，之所以用周期，因为周期可以是整数，方便计算
    {
        int i;
        for(i=0;i<period;i++)   //从当前时间，到这一个周期结束，都会对这一点造成受力的影响
        {
            force[position][currentTime+i]  =   1;
        }
    }

    public static void main(String[] args)
    {
        Floor floor = new Floor(1,1,1);




    }


}
