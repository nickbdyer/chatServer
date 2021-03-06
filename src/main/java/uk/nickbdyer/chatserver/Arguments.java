package uk.nickbdyer.chatserver;

public class Arguments {

    public static Integer getPort(String[] args) {
        if(args.length != 1) return 4444;
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Incorrect Port Format");
        }
    }

}
