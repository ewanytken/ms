package practicemodelingsystem;

import java.io.IOException;


public class TheoryOfGame2xN {

    public static void main(String[] args) throws IOException {
        int n = 3;

        double k1;
        double k2;

        double[][] PayMatrix = {
				                {1, 0.5, 0},
				                {0.35, 0, 0.6}};

        Line lines[] = new Line[n];
        for(int i = 0; i<n; i++){
            lines[i]=new Line();
        }

        for(int j = 0; j<n; j++) {
            k1 = PayMatrix[0][j];
            k2 = PayMatrix[1][j];

            lines[j].A = (k1-k2);
            //System.out.println(lines[j].A);
            lines[j].B = 1;
            //System.out.println(lines[j].B);
            lines[j].C = -k1;
            //System.out.println(lines[j].C);
        }
        CrossPoint crossPoints[][];
        crossPoints = new CrossPoint[n][n];

        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                crossPoints[i][j] = new CrossPoint();
                if(i==j){
                    crossPoints[i][j].exist=false;
                    continue;
                }
                if(Math.abs((PayMatrix[0][i]-PayMatrix[0][j])-(PayMatrix[1][i]-PayMatrix[1][j]))<0.00001){
                    crossPoints[i][j].exist=false;
                    continue;
                }
                crossPoints[i][j].x=(lines[i].C-lines[j].C)/(lines[j].A-lines[i].A);
                crossPoints[i][j].y=-lines[i].A*crossPoints[i][j].x-lines[i].C;
                if((crossPoints[i][j].x<0) || (crossPoints[i][j].x>1)){
                    crossPoints[i][j].x=0;
                    crossPoints[i][j].y=0;
                }
            }
        }
        for(int i = 0; i<n; i++){
            for(int j =0; j<n;j++){
                System.out.print(String.valueOf(crossPoints[i][j].x)+" "+String.valueOf(crossPoints[i][j].y)+" ");
            }
            System.out.println();
        }
        int minIndex=0;
        double min = PayMatrix[0][0];
        double min2 = PayMatrix[1][0];
        for(int i =1; i<n; i++){
            if((PayMatrix[0][i]<min) || ((PayMatrix[0][i]==min) && (PayMatrix[1][i]<min2))){
                min = PayMatrix[0][i];
                min2 = PayMatrix[1][i];
                minIndex = i;
            }
        }
        System.out.println(minIndex);
        System.out.println(min);
        System.out.println(min2);
        double price=0;
        double beg = 0;
        double tod = 1;
        int point = minIndex;
        int minPoint = minIndex;
        boolean cross = false;
        for(int i = 0; i<crossPoints[minIndex].length; i++){
            if(crossPoints[minIndex][i].x != 0 || crossPoints[minIndex][i].y != 0){
                cross = true;
                break;
            }
        }
        if(cross){
            for(;;){

                for(int i=0; i<crossPoints[minIndex].length; i++) {
                    if ((crossPoints[minIndex][i].x>beg) && (crossPoints[minIndex][i].x<tod) && (minIndex != i)){
                        tod = crossPoints[minIndex][i].x;
                        minPoint = i;
                    }
                }
                if(crossPoints[minIndex][minPoint].y>price)
                    price = crossPoints[minIndex][minPoint].y;
                if(tod==1)
                    break;

                System.out.println(minIndex);
                System.out.println(tod);
                System.out.println(minPoint);
                minIndex=minPoint;
                beg = tod;
                tod = 1;
            }
        }
        else{
            price = min2;
        }
        System.out.println(beg);
        System.out.println(price);

        double EStrateg1 = PayMatrix[0][0];
        double EStrateg2 = PayMatrix[1][0];
        int Str1 = 0;
        int Str2 = 0;
        for(int i = 1; i < PayMatrix[0].length; i++){
            if(PayMatrix[0][i] < EStrateg1){
                EStrateg1 = PayMatrix[0][i];
                Str1 = i;
            }
            if(PayMatrix[1][i] < EStrateg2){
                EStrateg2 = PayMatrix[1][i];
                Str2 = i;
            }

        }
        System.out.println(EStrateg1);
        System.out.println(EStrateg2);
        double p1 = (PayMatrix[1][Str2]-PayMatrix[1][Str1])/(PayMatrix[0][Str1] + PayMatrix[1][Str2] - PayMatrix[0][Str2] - PayMatrix[1][Str1]);
        double p2 = 1-p1;
        System.out.println("V=" + Double.toString(price) + " p"+ String.valueOf(Str1+1) + "=" + String.valueOf(p1) + " p" + String.valueOf(Str2+1) + "=" + String.valueOf(p2));
    }
}

class Line{
    double A,B,C;
    Line(){
        A = 0;
        B = 0;
        C = 0;
    }
}
class CrossPoint{
    double x,y;
    boolean exist;
    CrossPoint(){
        x = 0;
        y = 0;
        exist = false;
    }
}