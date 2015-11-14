/*
Floor.java
表示一块地板的受力情况

author:guozheng
date:2015/07/20
*/


public class Floor
{
    double samplePeriod;   //采样率
    int M;  //地板的长度
    int N;  //地板的宽度
    int t;  //总测试时间，单位是秒
    
    int sample; //总采样点数，等于t/0.01;
    double  force[][];
    int currentTime;    //当前时间，单位是0.01s
    int distribution[];   //地板上的各个点是否有人站着，若有人，则该位置对应位置1；无人置0

    public Floor(int M, int N, int t)
    {
        this.M  =   M;
        this.N  =   N;
        this.t  =   t;
        sample  =   (int)(t/0.01);
        force   =   new double[M*N][sample];
        currentTime =   0;
        distribution    =   new int[M*N];

        int i,j;
        for(i=0;i<M*N;i++)
            distribution[i]=0;

        for(i=0;i<M*N;i++)
            for(j=0;j<sample;j++)
                force[i][j] =   0;
    }

    public void setDistribution(int position)
    {
        //System.out.println("position=" + position);
        distribution[position]  =   1;
    }

    public void clearDistribution(int position)
    {
        distribution[position]  =   0;
    }   
    
    public int getDistribution(int position)
    {
        return  distribution[position];
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
    
    //计算受力大小
    public double calculateForce(int period, int counter, double randomPhase)
    {
        double forceTemp;
        double alpha1  =   0.43349/(period*samplePeriod)-0.37219;
        double alpha2  =   0.1339;
        double alpha3  =   0.0964;
        double phase1  =   0;
        double phase2  =   Math.PI/2;
        double phase3  =   Math.PI/2;
        forceTemp   =   ( 1 + alpha1*Math.sin(2*Math.PI*1*counter/period+randomPhase-phase1) );
        forceTemp   +=  ( alpha2*Math.sin( 2*Math.PI*2*counter/period+randomPhase-phase2 ) );
        forceTemp   +=  ( alpha3*Math.sin( 2*Math.PI*3*counter/period+randomPhase-phase3 ) );
        return  forceTemp;
    }


    public void setForce(int position, int period, double randomPhase)    //当有人正好踩上某个位置时，触发setForce，其中position是位置点，period是这一脚对应的周期，之所以用周期，因为周期可以是整数，方便计算
    {
        int i=0;
        try
        {
            for(i=0;i<period && currentTime+i<sample;i++)   //从当前时间，到这一个周期结束，都会对这一点造成受力的影响
            {
                //force[position][currentTime+i]  =   1;
                force[position][currentTime+i]  +=   calculateForce(period,i,randomPhase);;
            }
        }catch(ArrayIndexOutOfBoundsException e) 
        {
            System.out.println("position=" +position+ ", currentTime=" +currentTime+ ",i=" +i);     
            e.printStackTrace(); 
        }
    }

    public static void main(String[] args)
    {
        Floor floor = new Floor(1,1,1);




    }


}
