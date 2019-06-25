package AircraftCabinLoadingSoftware;


import java.util.HashMap;
import java.util.*;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toMap;
import java.lang.*;
import java.math.*;
import java.text.*;
import java.util.*;



public class Aircraft {

    private String name;
    public double Xcenter = 0;
    public double Ycenter = 0;
    public String PlaneId;
    public int Rows;
    public int SeatsPerRow;
    public static int PlaneCount = 0;
    public int TotalAmountOfSeats;
    public int PassengerId = 0; //This identifies a uniq passenger or a unique group of passengers.

    public Map<Integer, List> Passengers;
    public Map<Integer, List> Seats;
    public Map<Integer, List> SeatsPosMatrix;
    public Map<Integer, List> SeatsEuclDistFromCenter;

    //Main functionality
    public Aircraft(String PlaneId, int Rows, int SeatsPerRow){
        Aircraft.PlaneCount += 1;
        this.PlaneId = PlaneId;
        this.Rows = Rows;
        this.SeatsPerRow = SeatsPerRow;
        this.TotalAmountOfSeats = this.Rows * this.SeatsPerRow;

        this.Passengers = new HashMap<>();
        this.Seats = new HashMap<>();
        this.SeatsPosMatrix = new HashMap<>();
        this.SeatsEuclDistFromCenter = new HashMap<>();
        this.CreateSeats();
        this.CreateDistanceMatrix();

    }

    public void CreateSeats(){
        //Form the distribution of seats.
        for (int i = 1; i <= this.Rows; i++) {
            this.Seats.put(i, new ArrayList<String>());
            this.SeatsEuclDistFromCenter.put(i, new ArrayList<Integer>());
            for (int j = 1; j <= this.SeatsPerRow; j++) {
                this.Seats.get(i).add("empty");
                this.SeatsEuclDistFromCenter.get(i).add(0);
            }
        }
    }
    public void CreateDistanceMatrix(){

        //Calculate the coords of the center of the plane
        if((this.Rows & 1) == 0){
            this.Xcenter = (double)(this.Rows + 1) / 2;
        }else{
            this.Xcenter = this.Rows / 2;
        }
        if((this.SeatsPerRow & 1) == 0){
            this.Ycenter = (double)(this.SeatsPerRow + 1) / 2;
        }else{
            this.Ycenter = this.SeatsPerRow / 2;
        }

        //Create Center coordinates
        ArrayList Center = new ArrayList<Integer>();
        Center.add(this.Xcenter);
        Center.add(this.Ycenter);

        //Form the distribution of seats.
        for (int i = 1; i <= this.Rows; i++) {
            this.SeatsPosMatrix.put(i, new ArrayList<Integer>());
            for (int j = 1; j <= this.SeatsPerRow; j++) {
                this.SeatsPosMatrix.get(i).add(j);
            }
        }

        //Creating Euclidian Distance Mtatrix to Show the distance of every seat to the center of the plane
        for (int x = 1; x <= this.Rows; x++) {
            for (int y = 0; y < this.SeatsPerRow; y++) {
                this.SeatsEuclDistFromCenter.get(x).set(y, Euclidian(x,y+1,this.Xcenter,this.Ycenter));
            }
        }
    }

    public void MarkSeatAsUnavailable(int row, char seat){
        char seatIn = Character.toLowerCase(seat);
        int seatNumber;
        String temp;
        seatNumber = seatIn - 97;
        //System.out.println(seatNumber);

        //casting an object to an integer
        temp = (String) this.Seats.get(row).get(seatNumber);
        if(temp.equals("empty")){
            this.Seats.get(row).set(seatNumber, "unavailable");
            this.SeatsEuclDistFromCenter.get(row).set(seatNumber, -1.0);
        }else{
            System.out.println("Seat ("+row+","+seat+") is already unavailable.");
        }

    }
    public void MarkSeatAsAvailable(int row, char seat){
        char seatIn = Character.toLowerCase(seat);
        int seatNumber;
        seatNumber = seatIn - 97;
        String temp;

        //casting an object to an integer
        temp = (String) this.Seats.get(row).get(seatNumber);
        if(!temp.equals("empty")){
            this.Seats.get(row).set(seatNumber, "empty");
            this.SeatsEuclDistFromCenter.get(row).set(seatNumber, Euclidian(row,seatNumber+1,this.Xcenter,this.Ycenter));
            //System.out.println("New coords:"+row+","+seatNumber+","+this.Xcenter+","+this.Ycenter);
        }else{
            System.out.println("Seat ("+row+","+seat+") is already available.");
        }

    }

    public void PlacePassengerOnSeat(Integer row, char seat, String lastName){
        char seatIn = Character.toLowerCase(seat);
        int seatNumber;
        String temp;
        seatNumber = seatIn - 97;

        //casting an object to an integer
        temp = (String) this.Seats.get(row).get(seatNumber);
        if(temp.equals("empty")){
            this.Seats.get(row).set(seatNumber, lastName);
            this.SeatsEuclDistFromCenter.get(row).set(seatNumber, -1.0);
        }else{
            System.out.println("Seat ("+row+","+seat+") is unavailable.");
        }

    }

    public void AddPassenger(String lastName){
        this.PassengerId ++;
        this.Passengers.put(this.PassengerId, new ArrayList<String>() );
        this.Passengers.get(this.PassengerId).add(lastName);
        System.out.println("Passenger "+lastName+" was added");
        DistributePassengers();

    }
    public void AddPassenger(String lastName1, String lastName2){
        this.PassengerId ++;
        this.Passengers.put(this.PassengerId, new ArrayList<String>() );
        this.Passengers.get(this.PassengerId).add(lastName1);
        this.Passengers.get(this.PassengerId).add(lastName2);
        System.out.println("Passengers "+lastName1+", "+lastName2+" were added");
        DistributePassengers();

    }
    public void AddPassenger(String lastName1, String lastName2, String lastName3){
        this.PassengerId ++;
        this.Passengers.put(this.PassengerId, new ArrayList<String>() );
        this.Passengers.get(this.PassengerId).add(lastName1);
        this.Passengers.get(this.PassengerId).add(lastName2);
        this.Passengers.get(this.PassengerId).add(lastName3);
        System.out.println("Passengers "+lastName1+", "+lastName2+", "+lastName3+" were added");
    }
    public void AddPassenger(String lastName1, String lastName2, String lastName3, String lastName4){
        this.PassengerId ++;
        this.Passengers.put(this.PassengerId, new ArrayList<String>() );
        this.Passengers.get(this.PassengerId).add(lastName1);
        this.Passengers.get(this.PassengerId).add(lastName2);
        this.Passengers.get(this.PassengerId).add(lastName3);
        this.Passengers.get(this.PassengerId).add(lastName4);
        System.out.println("Passengers "+lastName1+", "+lastName2+", "+lastName3+", "+lastName4+" were added");
        DistributePassengers();
    }
    public void DeletePassenger(String lastName){
        boolean deleteError = true;
        int size1 = this.Passengers.size();
        for (int x = 1; x <= size1; x++) {
            Iterator itr = this.Passengers.get(x).iterator();
            while (itr.hasNext()){
                String name = (String)itr.next();
                if(name.equals(lastName)){
                    itr.remove();
                    System.out.println("The passenger:" +lastName+" was successfully removed");
                    deleteError = false;
                    DistributePassengers();
                }
            }
        }
        if(deleteError){
            System.out.println("The passenger:" +lastName+" could not be found");
        }
    }

    public void ResetPassengerDistribution(){
        String temp;

        for (int row = 1; row<= this.Seats.size(); row++) {
            for (int seat=0; seat< this.Seats.get(row).size(); seat++) {
                temp = (String) this.Seats.get(row).get(seat);
                if (!temp.equals("empty") && !temp.equals("unavailable")) {
                    //System.out.println("Seat (" + row + "," + seat + ") is already available or marked as unavailable.");
                    this.Seats.get(row).set(seat, "empty");
                    this.SeatsEuclDistFromCenter.get(row).set(seat, Euclidian(row, seat + 1, this.Xcenter, this.Ycenter));
                    //System.out.println("New coords:" + row + "," + seat + "," + this.Xcenter + "," + this.Ycenter);
                }
            }
        }
    }


    public List<Point> FindBestSeats(int GroupSize){
        List<Point> points = new ArrayList<Point>();
        List<Point> BestPoints = new ArrayList<Point>();
        Double Distance = 100000.0;
        int needToDecrease = 0;
        int toReach = GroupSize;
        int ReachCount = 0;
        int valueCount = 0;

        for (Map.Entry<Integer, List> entry : this.SeatsEuclDistFromCenter.entrySet()) {
            List<Double> row = entry.getValue();
            ReachCount = 0;
            valueCount = 0;//new row valueCount must start from 0
            points.clear();

            while (valueCount < row.size()){
                if (row.get(valueCount) >= 0) {
                    ReachCount++;
                    points.add(new Point(entry.getKey(), valueCount));
                    if (ReachCount == toReach) {
                        if(ReachCount>1){
                            needToDecrease = GroupSize - 1;
                            valueCount = valueCount - needToDecrease;
                        }
                        if (CalculateDistanceOfPoints(points) < Distance) {
                            Distance = CalculateDistanceOfPoints(points);
                            BestPoints.clear();
                            BestPoints = SafePoints(points);
                            points.clear();
                            ReachCount = 0;
                        }else{
                            points.clear();
                            ReachCount = 0;
                        }
                    }
                }
                else {// If the row was interrupted before it reached the needed length
                    ReachCount = 0;
                    points.clear();
                }
                valueCount++;
            }
        }
        return BestPoints;
    }
    public void DistributePassengers(){
        SortPassengers();
        ResetPassengerDistribution();
        int id = 0;
        char seatChar;
        List<Point> SeatsToUse = new ArrayList<Point>();

        for (Map.Entry<Integer, List> entry : this.Passengers.entrySet()) {
            SeatsToUse = FindBestSeats(entry.getValue().size());//need 4 passengers to be placed
            for(Point seat : SeatsToUse) {
                //System.out.println("Seats to use ("+seat.getX()+","+seat.getY()+") for "+entry.getValue().size()+" Amount of passengers");
                seatChar = ConvertIntToChar(seat.getY());
                PlacePassengerOnSeat(seat.getX(),seatChar,(String)entry.getValue().get(id));
                id++;
            }
            id=0;
        }
    }


    //Representing Methods
    public void RepresentSeatsInPlaneView(){
        for (Map.Entry<Integer, List> entry : this.Seats.entrySet()) {
            System.out.println(entry.getKey()+" = "+entry.getValue());
        }
    }
    public void RepresentSeatsCoordsInPlaneView(){
        for (Map.Entry<Integer, List> entry : this.SeatsPosMatrix.entrySet()) {
            System.out.println(entry.getKey()+" = "+entry.getValue());
        }
    }
    public void RepresentEuclDistPlaneView(){
        for (Map.Entry<Integer, List> entry : this.SeatsEuclDistFromCenter.entrySet()) {
            System.out.println(entry.getKey()+" = "+entry.getValue());
        }
    }
    public void GetAllSeatsInformation(){
        int available = 0;
        int allocated = 0;
        String temp;

        for (int x = 1; x <= this.Rows; x++) {
            for (int y = 0; y < this.SeatsPerRow; y++) {
                temp = (String) this.Seats.get(x).get(y);
                if(temp.equals("empty")){
                    available ++;
                }else{
                    allocated ++;
                }
            }
        }
        System.out.println("Seats information: Available seats: "+available +", Allocated seats:"+allocated);

    }
    public void GetSeatInformation(int row, char seat){
        char seatIn = Character.toLowerCase(seat);
        int seatNumber;
        seatNumber = seatIn - 97;
        String temp;

        //casting an object to an integer
        temp = (String) this.Seats.get(row).get(seatNumber);
        if(temp.equals("empty")){
            System.out.println("Seat ("+row+","+seat+") is available.");
        }else{
            System.out.println("Seat ("+row+","+seat+") is allocated.");
        }

    }
    public void ShowPassengerList(){
        for (Map.Entry<Integer, List> entry : this.Passengers.entrySet()) {
            System.out.println(entry.getKey()+" = "+entry.getValue());
        }
    }
    public void RepresentObject(List<Point> BestPoints){
        boolean nothingtoPrint = true;
        for(Point point : BestPoints) {
            nothingtoPrint = false;
            System.out.println("These are the points saved: "+point.getX()+","+point.getY());
        }
        if(nothingtoPrint){
            System.out.println("The object is empty");
        }
    }



    //Helper Methods
    public Double Euclidian(double x1, double y1, double x2, double y2){
        //Calculating Euclidian distance for given 2 points
        //System.out.println("x1:"+x1+", y1;"+ y1+", x2:" + x2+ ", y2:"+  y2);
        Double result = Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
        Double truncatedDouble = BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return truncatedDouble;
    }
    static ArrayList<Integer> Reverse(ArrayList<Integer> array) {
        for (int i = 0; i < array.size() / 2; i++) {
            Integer temp = array.get(i);
            array.set(i, array.get(array.size()-1-i));
            array.set(array.size()-1-i,temp);
        }
        return array;
    }
    public void SortPassengers() {
        this.Passengers = this.Passengers.entrySet().stream().sorted(comparingInt(e -> e.getValue().size())).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> { throw new AssertionError(); }, LinkedHashMap::new));
        LinkedHashMap<Integer, List> Temp = new LinkedHashMap<>();
        ArrayList keyList =  new ArrayList(this.Passengers.keySet());
        keyList = Reverse(keyList);
        keyList.forEach((temp) -> { Temp.put((Integer) temp, this.Passengers.get(temp));});
        this.Passengers = Temp;
    }
    public char ConvertIntToChar(int value){
        value = value + 97;
        char valueOut = (char)value;
        char seat = Character.toUpperCase(valueOut);
        return seat;
    }
    public List<Point> SafePoints(List<Point> points1){
        List<Point> Buffer = new ArrayList<Point>();
        for(Point point1 : points1) {
            Buffer.add(new Point(point1.getX(),point1.getY()));
        }
        return Buffer;
    }
    public Double CalculateDistanceOfPoints(List<Point> points){
        Double Sum = 0.0;
        Integer X = 0;
        Integer Y = 0;
        Double PointDistance = 0.0;
        for (Point point : points) {
            X = point.getX();
            Y = point.getY();
            PointDistance = (Double) this.SeatsEuclDistFromCenter.get(X).get(Y);
            Sum = Sum + PointDistance;
        }
        return Sum;
    }
}
