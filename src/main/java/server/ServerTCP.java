package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9001);
        ServerTCP serverTCP = new ServerTCP(serverSocket);
        serverTCP.startServer();
    }

    private ServerSocket serverSocket;

    public ServerTCP(ServerSocket socket) {
        this.serverSocket = socket;
    }

    public void startServer() {
        try{
            System.out.println("Iniciando servidor");
            while (!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                System.out.println("player conected");
                ClientHandler clienteHandler = new ClientHandler(socket);

                Thread thread = new Thread(clienteHandler);
                thread.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeServerSocket() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
