package com.example.register;

import com.example.register.service.RegisterService;
import java.io.IOException;
import org.springframework.boot.CommandLineRunner;

/**
 * allows to execute register operations from command line
 */
public class RegisterCmdLineRunner implements CommandLineRunner {

    private final RegisterService service;

    public RegisterCmdLineRunner(RegisterService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        printHelp();
        while (true) {
            String line = readLine();
            switch (line) {
                case "h":
                    printHelp();
                    break;
                case "r":
                    rechargeRegister();
                    break;
                case "t":
                    transferBetweenRegisters();
                    break;
                case "b":
                    printCurrentBalances();
                    break;
                case "q":
                    return;
            }
        }
    }

    private void printHelp() {
        System.out.println("(h) print help");
        System.out.println("(r) rechange register");
        System.out.println("(t) transfer beetween registers");
        System.out.println("(b) print current balances of all registers");
        System.out.println("(q) quit");
    }

    private void rechargeRegister() throws IOException {
        try {
            System.out.print("register name: ");
            var registerName = readLine();
            System.out.print("amount: ");
            var amount = readInt();
            service.recharge(registerName, amount);
            System.out.println("recharge done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void transferBetweenRegisters() throws IOException {
        try {
            System.out.print("source register name: ");
            var srcRegisterName = readLine();
            System.out.print("destination register name: ");
            var dstRegisterName = readLine();
            System.out.print("amount: ");
            var amount = readInt();
            service.transfer(srcRegisterName, dstRegisterName, amount);
            System.out.println("transfer done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printCurrentBalances() {
        var balances = service.getCurrentBalances();
        for (var name : balances.keySet()) {
            System.out.println(String.format("%s: %s", name, balances.get(name)));
        }
    }

    private String readLine() throws IOException {
        var sbuf = new StringBuilder();
        while (true) {
            var ch = System.in.read();
            if (ch <= 0 || ch == '\n' || ch == '\r') {
                return sbuf.toString();
            } else {
                sbuf.append((char) ch);
            }
        }
    }

    private int readInt() throws IOException {
        var line = readLine();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("'%s' is not an integer number", line));
        }
    }
}
