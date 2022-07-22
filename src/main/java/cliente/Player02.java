package cliente;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Player02 {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String nickName;
    private String heroi;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        String x = System.lineSeparator() + "Procurando partida...";
        x += System.lineSeparator() + "Escolha seu Nickname: ";
        System.out.println(x);

        String username = scanner.nextLine();

        Socket socket = new Socket("localhost", 9001);

        //escolhendo avatar
        String y = System.lineSeparator() + "Escolha ser heroi: ";
        y += System.lineSeparator() +"1-D.Va";
        y += System.lineSeparator() +"2-Winston";
        y += System.lineSeparator() +"3-Roadhog";
        y += System.lineSeparator() +"4-Orisa";
        System.out.println(y);

        Integer H = Integer.valueOf(scanner.nextLine());

        //Loga no servidor passando o Nickname e heroi
        Player02 player01 = new Player02(socket, username, personagens().get(H));

        String z = System.lineSeparator() + "partida encontrada";
        z += System.lineSeparator();
        System.out.println(z);

        player01.listenForMessage();
        player01.sendMessage();
    }


    public Player02(Socket socket, String username, String heroi) {
        try {
            this.socket = socket;
            this.nickName = username;
            this.heroi = heroi;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(nickName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(nickName + "-" + heroi + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, String> personagens() {
        HashMap<Integer, String> persona = new HashMap<Integer, String>();
        persona.put(1, "D.Va.");
        persona.put(2, "Winston.");
        persona.put(3, "Roadhog.");
        persona.put(4, "Orisa.");

        return persona;
    }

}
