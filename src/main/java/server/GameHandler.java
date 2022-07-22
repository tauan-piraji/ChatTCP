package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameHandler {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private final String player;
    private String avatar;
    private ArrayList<String> heroiPartida;
    private int map;
    public static ArrayList<ClientHandler> gameHandler = new ArrayList<>();

    public GameHandler(String player, String avatar) {
        this.player = player;
        this.avatar = avatar;
    }

    public void heroHandler(Socket socker) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //escolhendo avatar
            String y = System.lineSeparator() + "Escolha ser heroi: ";
            y += System.lineSeparator() +"1-D.Va";
            y += System.lineSeparator() +"2-Winston";
            y += System.lineSeparator() +"3-Roadhog";
            y += System.lineSeparator() +"4-Orisa";
            System.out.println(y);

            String H = null;

            //seleciona heroi que ainda não esta na partida
            do{
                Scanner scanner = new Scanner(System.in);
                Integer Hnumber = Integer.valueOf(scanner.nextLine());
                if(heroiPartida == null) {
                    H = personagens().get(Hnumber);
                }else{
                    if(Hnumber.equals(1) || Hnumber.equals(2) || Hnumber.equals(3) || Hnumber.equals(4)) {
                        while (H == null) {

                            if(heroiPartida.contains(H)) {
                                String msg = System.lineSeparator() + "Esse horoi já foi pego, escolha um desses: ";
                                for(String hr : heroiPartida) {
                                    if(hr != H) {
                                        msg += System.lineSeparator() + hr;
                                    }
                                }
                                msg += System.lineSeparator();
                                System.out.println(msg);
                                H = null;
                            }

                        }
                    }else{
                        System.out.println("escolha um número valido");
                    }
                }

            }while (H == null);

            bufferedWriter.write(player + " Escolheu: ");
            bufferedWriter.write(H);
            bufferedWriter.newLine();
            avatar = H;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
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

    public static HashMap<Integer, String> maps() {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "Ilhas");
        map.put(2, "Continente");

        return map;
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


}
