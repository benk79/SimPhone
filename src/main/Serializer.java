package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer
{
	/**
	 * This method is used to read data from file and return deserialized object.
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object get (String file) throws IOException, ClassNotFoundException
	{
		FileInputStream fileInputStream = new FileInputStream(file);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

		Object object = objectInputStream.readObject();

		objectInputStream.close();

		return object;
	}


	/**
	 * This method is used to write serialized data to file
	 *
	 * @param file
	 * @param object
	 * @throws IOException When the file can not be written
	 */
	public static void set (String file, Object object) throws IOException
	{
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.close();
	}
}
