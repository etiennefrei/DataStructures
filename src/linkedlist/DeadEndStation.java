import java.util.List;

public class DeadEndStation {
    private static Stack<TrainType> s1 = new Stack<>();
    private static Stack<TrainType> s2 = new Stack<>();
    private static Stack<TrainType> s3 = new Stack<>();

    public DeadEndStation() {
    }

    public static void main(String[] args) {
        init();
        displayTracks();
        while (!s1.isEmpty()){
            TrainType t = s1.pop();
            if (t == TrainType.A){
                s2.push(t);
            } else {
                s3.push(t);
            }
        }
        while (!s2.isEmpty()){
            TrainType t = s2.pop();
            if (t == TrainType.A){
                s1.push(t);
            } else {
                s3.push(t);
            }
        }
        while (!s3.isEmpty()){
            s2.push(s3.pop());
        }
        displayTracks();
    }

    private static void init() {
        s1.push(TrainType.A);
        s1.push(TrainType.B);
        s1.push(TrainType.B);
        s1.push(TrainType.A);
        s1.push(TrainType.B);
        s2.push(TrainType.B);
        s2.push(TrainType.A);
        s2.push(TrainType.A);
        s2.push(TrainType.B);
        s2.push(TrainType.A);
    }

    private static void displayTracks(){
        System.out.println("S1: " + s1.show());
        System.out.println("S2: " + s2.show());
        System.out.println("S3: " + s3.show());
    }
}

enum TrainType {
    A,B
}
