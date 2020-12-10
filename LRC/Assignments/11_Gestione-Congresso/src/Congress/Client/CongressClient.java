package Congress.Client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import Congress.Common.CongressService;

public class CongressClient {
    public static void main(String[] args) {
        String addr;
        int port;
        try {
            addr = args[0];
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.println("Usage: CongressClient <address> <port>");
            return;
        }

        Scanner stdin = new Scanner(System.in);
        CongressService service;
        try {
            Registry r = LocateRegistry.getRegistry(addr, port);
            service = (CongressService) r.lookup(Congress.Common.Const.REG_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            stdin.close();
            return;
        }

        final int DAYS;
        final int SESSIONS;

        try {
            DAYS = service.getDays();
            SESSIONS = service.getSessions();
            sendHelp(DAYS, SESSIONS);
            while(true) {
                while(!stdin.hasNextLine());
                String command = stdin.nextLine();
                String[] split = command.split(" ");
                if(split[0].equals("help") || split[0].equals("h")) {
                    sendHelp(DAYS, SESSIONS);
                } else if(split[0].equals("add") || split[0].equals("a")) {
                    int day;
                    int session;
                    try {
                        day = Integer.parseInt(split[split.length-2])-1;
                        session = Integer.parseInt(split[split.length-1])-1;
                    } catch (NumberFormatException e) {
                        System.out.println("Command incomprehensible");
                        continue;
                    }
                    StringBuilder sb = new StringBuilder();
                    for(int i = 1; i < split.length - 2; i++) {
                        sb.append(split[i]);
                        if(i < split.length - 3) sb.append(" ");
                    }
                    String name = sb.toString();
                    int position = service.addSpeaker(name, day, session);
                    switch (position) {
                        case -1:
                            System.out.println("The request is unfulfillable");
                            break;
                        case -2:
                            System.out.println("The requested session is already full");
                            break;
                        default:
                            System.out.println("Operation completed successfully");
                            break;
                    }
                } else if(split[0].equals("read") || split[0].equals("r")) {
                    int day;
                    int session;
                    switch(split.length) {
                        case 1:
                            List<List<List<String>>> arr = service.getSession();
                            if(arr == null) {
                                System.out.println("The request is unfulfillable");
                                break;
                            }
                            for(day = 0; day < DAYS; day++) {
                                for (session = 0; session < SESSIONS; session++) {
                                    System.out.printf("Day %d\tSession %d\t%s\n", day+1, session+1, arr.get(day).get(session));
                                }
                                System.out.println();
                            }
                            break;
                        case 2:
                            try {
                                day = Integer.parseInt(split[1])-1;
                            } catch (NumberFormatException e) {
                                System.out.println("The <day> is not a number.");
                                break;
                            }
                            List<List<String>> ar = service.getSession(day);
                            if(ar == null) {
                                System.out.println("The request is unfulfillable");
                                break;
                            }
                            for(session = 0; session < SESSIONS; session++) {
                                System.out.printf("Day %d\tSession %d\t%s\n", day+1, session+1, ar.get(session));
                            }
                            break;
                        default:
                            try {
                                day = Integer.parseInt(split[1])-1;
                                session = Integer.parseInt(split[2])-1;
                            } catch (NumberFormatException e) {
                                System.out.println("The <day> or <session> is not a number.");
                                break;
                            }
                            List<String> a = service.getSession(day, session);
                            if(a == null) {
                                System.out.println("The request is unfulfillable");
                                break;
                            }
                            System.out.printf("Day %d\tSession %d\t%s\n", day+1, session+1, a);
                            break;
                    }
                } else if(split[0].equals("exit") || split[0].equals("e")) {
                    break;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        stdin.close();
    }

    private static void sendHelp(int days, int sessions) {
        System.out.printf("Use the following syntax to communicate with the server:\n\th[elp]\n\ta[dd] <name> <day 1-%d> <session 1-%d>\n\tr[ead] <?day 1-%d> <?session 1-%d>\n\te[xit]\n", days, sessions, days, sessions);
    }
}
