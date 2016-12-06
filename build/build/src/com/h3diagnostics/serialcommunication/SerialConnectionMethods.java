package com.h3diagnostics.serialcommunication;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Set;

import javax.usb.UsbException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.h3diagnostics.constants.Constants;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;;


public class SerialConnectionMethods {
    private static String readJsonString = "{\"patient\":{\"PatientID\":\"CHILDHSUM1000001\",\"PatientName\":\"Abhishek\",\"Gender\":\"M\",\"Dateofbirth\":\"2001-02-28 00:00:00.000\",\"Age\":\"15\",\"Address\":\"Mohali\",\"test\":\"Bp, bg, hb\"}}";

	private static SerialPort serialPort;
	private static JSONObject jsonObject;
	
	/**
	 * get the list of ports available on the PC
	 * 
	 * @throws SerialPortException
	 */
	public static void getPortListOnPcJssc() throws SerialPortException{
		String[] portNames = SerialPortList.getPortNames("");
		if (portNames.length == 0) {
		    System.out.println("There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
		    System.out.println("Press Enter to exit...");
		    try {
		        System.in.read();
		    } catch (IOException e) {
		         // TODO Auto-generated catch block
		          e.printStackTrace();
		    }
		    return;
		}

		for (int i = 0; i < portNames.length; i++){
		    System.out.println(portNames[i]);
	    }
		startSerialConnection(portNames);
		
	}
	/**
	 * start serial connection with the defined port
	 * 
	 * @param portNames
	 */
	public static void startSerialConnection(String [] portNames){
		setSerialPort(new SerialPort(portNames[0]));
		
		try{
			
			getSerialPort().openPort();
			Thread.sleep(2000);
			setDeviceConfig();
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the device's configuration 
	 * 
	 * @throws InterruptedException
	 */
	
	public static void setDeviceConfig() throws InterruptedException{
		try {
			getSerialPort().setParams(Constants.DEVICE_BAUD_RATE,
			        SerialPort.DATABITS_8,
			        SerialPort.STOPBITS_1,
			        SerialPort.PARITY_NONE);
			getSerialPort().setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
	                SerialPort.FLOWCONTROL_RTSCTS_OUT);
			
			getSerialPort().addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
			getSerialPort().writeString("@*1234");
			Thread.sleep(150);
			//serialPort.closePort();
			System.out.println("write success");
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			System.out.println("There are an error on writing string to port т: " + e);
		}
		
	}
	
	/**
	 * 
	 * Read data from the serial port
	 * @author Paras Dhanta
	 *
	 */
	private static class PortReader implements SerialPortEventListener {

		@Override
		public void serialEvent(SerialPortEvent event) {
			// TODO Auto-generated method stub
			  if(event.isRXCHAR() && event.getEventValue() > 0) {
		            try {
		                String receivedData = getSerialPort().readString(event.getEventValue());
		                System.out.println("Received response: " + receivedData);
		            }
		            catch (SerialPortException ex) {
		                System.out.println("Error in receiving string from COM-port: " + ex);
		            }
		        }
			
		}
		
	}
	
	/**
	 * Write to file with json from the server, which will serve as an input
	 * 
	 * Write to file with the output from the HC device, which will serve as the output for CDAC
	 * @param jsonString
	 * @param fileType
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void writeToFile(String jsonString,String fileType) throws IOException, ParseException{
		//write in input file
		File inputFile = null;
		/*create a new directory*/
		File mainDirectory = new File("E:\\junk\\cdac");
		
		/*this logic runs only once if the directory and file is not being created, otherwise else block executes*/
		if(!mainDirectory.exists()){
			if(mainDirectory.mkdir()){
				if(fileType.equals("input")){
				    inputFile = new File(mainDirectory.getAbsolutePath()+"/"+"inputFile.json");
				    readFromFileServer(inputFile.getAbsolutePath());
				}
				else{
					inputFile = new File(mainDirectory.getAbsolutePath()+"/"+"outputFile.txt");
				}
				FileWriter file = new FileWriter(inputFile);
				file.write(jsonString);
				file.flush();
				file.close();
			}
		}
		else{
			if(fileType.equals("input")){
			    inputFile = new File(mainDirectory.getAbsolutePath()+"/"+"inputFile.json");
			    readFromFileServer(inputFile.getAbsolutePath());
			}
			else{
				inputFile = new File(mainDirectory.getAbsolutePath()+"/"+"outputFile.txt");
			}
			FileWriter file = new FileWriter(inputFile);
			file.write(jsonString);
			file.flush();
			file.close();
		}
	}
	
	/**
	 * 
	 * read input file 
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void readFromFileServer(String filePath) throws FileNotFoundException, IOException, ParseException{
		//read from the input file
		HashMap<String,String> values = new HashMap<String, String>();
		
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(new FileReader(filePath));
		
		JSONObject jsonObjectFrmFIle = (JSONObject) obj;
		
		setJsonObject((JSONObject) jsonObjectFrmFIle.get("patient"));
		@SuppressWarnings("unchecked")
		Set<String> keys = getJsonObject().keySet();
		
		for (String value: keys)
        { 
			values.put(value,getJsonObject().get(value).toString());
        } 
		
	}

	public static JSONObject getJsonObject() {
		return jsonObject;
	}

	public static void setJsonObject(JSONObject jsonObject) {
		SerialConnectionMethods.jsonObject = jsonObject;
	}
	
	/**
	 *  polling service to listen for any changes made in the defined directory, will run infinitely in the background 
	 *  if required can be closed.
	 * @param directoryToBeWatched
	 */
	public static void watchServiceSample(String directoryToBeWatched){
	    try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			
			Path dir = Paths.get("E:\\junk\\cdac");
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
			
			System.out.println("Watch Service registered for dir: " + dir.getFileName());
			
			//infinit loop to check for events 
			while (true) {
			    WatchKey key;
			    try {
			        // wait for a key to be available
			        key = watcher.take();
			    } catch (InterruptedException ex) {
			        return;
			    }
			 
			    for (WatchEvent<?> event : key.pollEvents()) {
			        // get event type
			        WatchEvent.Kind<?> kind = event.kind();
			 
			        // get file name
			        @SuppressWarnings("unchecked")
			        WatchEvent<Path> ev = (WatchEvent<Path>) event;
			        Path fileName = ev.context();
			 
			        System.out.println(kind.name() + ": " + fileName);
			 
			        if (kind == OVERFLOW) {
			            continue;
			        } else if (kind == ENTRY_CREATE) {
			            System.out.print("create");
			            // process create event
			 
			        } else if (kind == ENTRY_DELETE) {
			            
			            // process delete event
			        	// delete a file from the directory
			 
			        } else if (kind == ENTRY_MODIFY) {
			 
			            // process modify event
			            System.out.print("modify");
			            //remove if required 
			            watcher.close();
			        }
			    }
			 
			    // IMPORTANT: The key must be reset after processed
			    boolean valid = key.reset();
			    if (!valid) {
			        break;
			    }
			}
			
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * perform test selected
	 * 
	 */
	public static void startTestLogic(String testName){
		//send the desired command to the machine to start the test
		//make a switch case to handle commands and start the test to get results 
	}
	
	/**
	 * 
	 * get the result from the machine for the test
	 */
	public static void getValuesFromMachine(){
		
		/*write the output to the output file*/
		try {
			writeToFile("test","output");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
    public static void jsonParser(String jsonString) throws SerialPortException, IOException, ParseException{
		try {
			  
	          SerialConnectionMethods.writeToFile(jsonString,"input");
	          
	          //file change event background service 
	          SerialConnectionMethods.watchServiceSample(null);
	          
              SerialConnectionMethods.getPortListOnPcJssc();

	          
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
	public static SerialPort getSerialPort() {
		return serialPort;
	}
	public static void setSerialPort(SerialPort serialPort) {
		SerialConnectionMethods.serialPort = serialPort;
	}
	
	
	public static void main(String[] args) throws SerialPortException, SecurityException, UsbException, IOException, ParseException {
        /*write the json read and write functions*/
		
        jsonParser(readJsonString);
       
    }

}
