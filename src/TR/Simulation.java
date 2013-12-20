package TR;

import java.util.Random;
import java.lang.Math;

public class Simulation {

    // Constantes en secondes
    static final int OPENING_DATE  = 28800 ; // 8h
    static final int CLOSURE_DATE  = 43200 ; // 12h
    static final int COMING_DELAY  = 60 ; // 1mn
    static final int TIME_INTERVAL = 60 ; // 1mn
    // Estimation du temps maximal à attendre dans la queue
    // (combien seront passés avant vous à votre guichet)
    static final int PATIENCE = 5 ;

    // Attributs
    private Staff staff ; // guichets
    private EventHeap schedule ; // file de priorite
    private CustomerQueue queue ; // file d'attente
    private Random rd ;
    private int customers, lost ; // clients, clients non satisfaits
    private String results ; // bilan
    
    // Constructeur
    public Simulation (int staffSize, long seed) {
        staff = new Staff(staffSize) ;
        schedule = new EventHeap() ;
        queue = new CustomerQueue(staffSize*PATIENCE) ;
        rd = new Random(seed) ;
        Customer.initRandom(seed) ;
    }
    
    // Simulation d'une loi de Poisson
    private int nextPoisson( double lambda ) {
      double elambda = Math.exp(-1*lambda);
      double product = 1;
      int count =  0;
      do {
        product *= rd.nextDouble();
        count++ ;
      } while( product >= elambda ) ;
      return count;
    }

    // Prévisions d'arrivée des clients
    private void initSchedule () {
        customers = 0 ;
        lost      = 0 ;
        int date  = OPENING_DATE ;
        int nb_arrivals ;
        while (date < CLOSURE_DATE) {
            nb_arrivals = nextPoisson( (double) TIME_INTERVAL / (double) COMING_DELAY ) ;
            customers += nb_arrivals ;
            while( nb_arrivals > 0 ) {
              Customer customer = new Customer() ;
              schedule.insert(new Event(date, customer)) ;
              nb_arrivals-- ;
            }
            date += TIME_INTERVAL ;
        }
    }
    // Déroulement du service
    private void run () {
        Customer customer ;
        Employee employee ;
        results = "" ;
        while (! schedule.isEmpty()) {
            Event event = schedule.getFirst() ;
            int date = event.getDate() ;
            if (date >= CLOSURE_DATE) {
                // on vire les clients qui attendent encore
                lost += queue.size() ;
                queue.clear() ;
            }
            if (event.getType() == Event.Type.COMING) {
                // un client arrive a la poste
                customer = event.getCustomer() ;
                employee = staff.getFree() ;
                if (employee == null)
                    if (queue.isFull()) lost += 1 ; // le client s'en va
                    else queue.addLast(customer) ; // il se met à la queue
                else { // un employé est libre
                    employee.setBusy(date) ;
                    event.setEmployee(employee) ;
                    // prévoir la libération de l'employé
                    date += customer.getDuration() ;
                    schedule.insert(new Event(date, employee)) ;
                }
            }
            else { // event.getType == Event.LEAVING
                // un employe se libere
                employee = event.getEmployee() ;
                if (queue.isEmpty())
                    employee.setFree(date) ;
                else { // l'employé s'occupe d'un autre client
                    customer = queue.getFirst() ;
                    event.setCustomer(customer) ;
                    // prévoir la libération de l'employé
                    date += customer.getDuration() ;
                    schedule.insert(new Event(date, employee)) ;
                }
            }
            results = results + event + '\n' ;
        }
    }
    
    private void showSimulation () {
        System.out.println(results) ;
    }
    
    private static String percent (int n, int d) {
        // affichage de n/d en pourcentage
        return " (" + Math.round(1000.0*n/d)/10.0 + " %)" ;
    }

    public static void main (String args[]) {
        Simulation simulation = new Simulation(5, 1) ;
        simulation.initSchedule() ;
        simulation.run() ;
        simulation.showSimulation() ;
        System.out.print("clients perdus : " + simulation.lost) ;
        System.out.println(percent(simulation.lost, simulation.customers)) ;
        System.out.println("efficacite des employes :") ;
        for (int i = 0 ; i < simulation.staff.getSize() ; i++) {
            Employee e = simulation.staff.getEmployee(i) ;
            System.out.println("  employe n¡ " + (i + 1)
                + percent(e.getWorkingTime(), CLOSURE_DATE - OPENING_DATE)) ;
        }
    }
}
