package sample.bank;

import java.io.*;

public final class Serializer {
    private static Serializer instance;

    private Serializer(){ }

    public static Serializer getInstance() {
        if (instance == null) {
            instance = new Serializer();
        }
        return instance;
    }

    public void saveSerialized(String file, Object object) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(object);
        oos.close();
    }

    public Object loadSerialized(String file){
        ObjectInputStream ois;
        try
        {
            ois = new ObjectInputStream(new FileInputStream(file));
            Object object = ois.readObject();
            ois.close();
            return object;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
