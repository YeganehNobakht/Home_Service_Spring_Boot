package ir.maktab.homeservices.service.applicationService;//package ir.maktab.service.applicationService;
//
//import ir.maktab.data.entity.Customer;
//import ir.maktab.service.customerService.CustomerService;
//import ir.maktab.exceptions.unchecked.InvalidInputRangeException;
//import org.springframework.stereotype.Service;
//
//import java.util.Scanner;
//
//@Service
//public class ApplicationServiceImpl implements ApplicationService{
//
//    private final Scanner scanner;
//    private final CustomerService customerService;
//
//    public ApplicationServiceImpl(Scanner scanner, CustomerService customerService) {
//        this.scanner = scanner;
//        this.customerService = customerService;
//    }
//
//    @Override
//    public void runApplication() throws Exception {
//        while (true) {
//            System.out.println("1.Sign in as manager \n" +
//                    "2.Sign in as customer \n" +
//                    "3.Sign in as employee \n"+
//                    "4.Quit");
//
//            boolean appInput = false;
//
//            while (!appInput) {
//                //TODO::method get instruction
//                int instructionItem = 0;
//                try {
//                    String instruction = scanner.next();
//                    instructionItem = Integer.parseInt(instruction);
//                    if (instructionItem < 1 || instructionItem > 3)
//                        throw new InvalidInputRangeException("Out of legal input range");
//                    appInput = true;
//                } catch (NumberFormatException e) {
//                    throw new NumberFormatException("Wrong input type! try again...");
//                } catch (InvalidInputRangeException exception) {
//                    throw new InvalidInputRangeException("Invalid input");
//                }
//                //TODO :: method
//                switch (instructionItem) {
//                    case 1:
//                        break;
//                    case 2:
//                        getCustomerOptions();
//                        break;
//                    case 3:
//                        break;
//                    case 4:
//                        break;
//
//                }
//
//            }
//        }
//    }
//    private void getCustomerOptions() throws Exception {
//        Customer customer = customerService.customerSignIn();
//        System.out.println("Chose an option:");
//        System.out.println("1.add new order\n2.Change password\n3.Giving score\n4.Quit");
//        String option = scanner.next();
//        while (option.equalsIgnoreCase("q") || option.equals("4")){
//            switch (option){
//                case "1":
//                    //customerService.addOrder(customer);
//                    break;
//                case "2":
//                    //customerService.changePassword(customer);
//                    break;
//                case "3":
//                   // customerService.givingScore();
//                    break;
//                default:
//                    System.out.println("Wrong input.");
//            }
//            System.out.println("Chose an option:");
//            System.out.println("1.add new order\n2.Change password\n3.Giving score\n4.Quit");
//            //option = scanner.next();
//        }
//
//    }
//}
