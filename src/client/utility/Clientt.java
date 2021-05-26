package client.utility;

import common.Request;
import common.Response;
import common.ResponseCode;
import common.User;
import exceptions.ConnectionException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class Clientt {
    private String host;
    private int port;
    private Console console;
    private AuthManager authManager;
    private User user;

    private DatagramChannel datagramChannel;
    private SocketAddress address;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(16384);
    private Selector selector;

    public Clientt(String hostname, int port, Console console,AuthManager authManager){
        this.host=hostname;
        this.port=port;
        this.console=console;
        this.authManager=authManager;

    }

    private void processAuthentication() throws ConnectionException {
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = authManager.handle();//формиркем запрос на сервер с зависимости от того, есть пароль и логин у клиента
                if (requestToServer.isEmpty()) continue;
                send(requestToServer);
                serverResponse = receive();
                System.out.println(serverResponse.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
                System.err.println("Произошла ошибка при отправке данных на сервер!");
            } catch (ClassNotFoundException exception) {
                System.err.println("Произошла ошибка при чтении полученных данных!");
            } catch (IOException exception) {
                System.err.println("Соединение с сервером разорвано!");


            }
        } while (serverResponse == null || !serverResponse.getResponseCode().equals(ResponseCode.OK));
        user = requestToServer.getUser();
    }

    public void run(){
        System.out.println("Клиент запущен.");
        try {
            datagramChannel = DatagramChannel.open();
            address = new InetSocketAddress(this.host, this.port);
            datagramChannel.connect(address);
            datagramChannel.configureBlocking(false);
            selector = Selector.open();
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            Request requestToServer = null;
            Response serverResponse = null;
            processAuthentication();
            do {
                requestToServer = serverResponse != null ? console.workMode(serverResponse, user) :
                        console.workMode(null, user);

                if (requestToServer.isEmpty()) continue;

                send(requestToServer);

                serverResponse = receive();

                System.out.print(serverResponse.getResponseBody());
            } while(!requestToServer.getCommandName().equals("exit"));
        } catch (IOException | ClassNotFoundException | ConnectionException exception) {
            System.out.println("Произошла ошибка при работе с сервером!");
            System.exit(0);
        }
    }

    private void makeByteBufferToRequest(Request request) throws IOException {
        byteBuffer.put(serialize(request));
        byteBuffer.flip();
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Response deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return response;
    }

    private void send(Request request) throws IOException {
        makeByteBufferToRequest(request);
        DatagramChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isWritable()) {
                    channel = (DatagramChannel) key.channel();
                    channel.write(byteBuffer);
                    channel.register(selector, SelectionKey.OP_READ);
                    break;
                }
            }
        }
        byteBuffer.clear();
    }

    private Response receive() throws IOException, ClassNotFoundException {
        DatagramChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isReadable()) {
                    channel = (DatagramChannel) key.channel();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    channel.register(selector, SelectionKey.OP_WRITE);
                    break;
                }
            }
        }
        return deserialize();
    }
}