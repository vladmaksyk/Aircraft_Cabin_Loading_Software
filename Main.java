package AircraftCabinLoadingSoftware;

import java.util.Map;
import java.util.Set;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Aircraft Boing474 = new Aircraft("WD1923", 8, 4);

        Boing474.MarkSeatAsUnavailable(2,'A');
        Boing474.MarkSeatAsUnavailable(2,'B');
        Boing474.MarkSeatAsUnavailable(4,'B');
        Boing474.MarkSeatAsUnavailable(6,'C');
        Boing474.MarkSeatAsUnavailable(5,'A');
        Boing474.MarkSeatAsUnavailable(5,'B');
        Boing474.MarkSeatAsUnavailable(7,'B');
        Boing474.MarkSeatAsAvailable(2,'B');


        Boing474.AddPassenger("Semalina", "Pikachu", "Kirtoka","Furtel");
        Boing474.AddPassenger("Ronaldo", "Messia", "Mbappe");
        Boing474.AddPassenger("Maylil","Rak","Ratatuy");
        Boing474.AddPassenger("Lesly","Azumaril");
        Boing474.AddPassenger("VanDijk", "Salah");
        Boing474.AddPassenger("Nessa", "Jessica");
        Boing474.AddPassenger("lilla","Ulesly");
        Boing474.AddPassenger("Phoenix");
        Boing474.AddPassenger("Camilla");
        Boing474.AddPassenger("Sheila");
        Boing474.AddPassenger("Vetikan");
        Boing474.AddPassenger("Maksyk");
        Boing474.GetAllSeatsInformation();
        Boing474.RepresentSeatsInPlaneView();















    }






}
