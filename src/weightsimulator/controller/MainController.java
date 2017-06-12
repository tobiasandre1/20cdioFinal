package weightsimulator.controller;

import java.util.ArrayList;
import java.util.List;

import webapplication.datalayer.MySQLOperatoerDAO;
import webapplication.datalayer.MySQLProduktBatchDAO;
import webapplication.datalayer.MySQLProduktBatchKomponentDAO;
import webapplication.datalayer.MySQLRaavareBatchDAO;
import webapplication.datalayer.MySQLRaavareDAO;
import webapplication.datalayer.MySQLViewDAO;
import webapplication.datalayerinterfaces.DALException;
import webapplication.datalayerinterfaces.OperatoerDAO;
import webapplication.datalayerinterfaces.ProduktBatchDAO;
import webapplication.datalayerinterfaces.ProduktBatchKompDAO;
import webapplication.datalayerinterfaces.RaavareBatchDAO;
import webapplication.datalayerinterfaces.RaavareDAO;
import webapplication.datalayerinterfaces.ViewDAO;
import webapplication.datatransferobjects.OperatoerDTO;
import webapplication.datatransferobjects.ProduktBatchDTO;
import webapplication.datatransferobjects.ProduktBatchKompDTO;
import webapplication.datatransferobjects.RaavareBatchDTO;
import webapplication.datatransferobjects.RaavareDTO;
import webapplication.datatransferobjects.ViewRaavareNavneDTO;
import webapplication.sqlconnector.*;

import java.lang.Math;
import java.sql.ResultSet;
import java.sql.SQLException;

import weightsimulator.socket.ISocketController;
import weightsimulator.socket.ISocketObserver;
import weightsimulator.socket.SocketInMessage;
import weightsimulator.socket.SocketOutMessage;
import weightsimulator.socket.SocketInMessage.SocketMessageType;
import weightsimulator.weight.IWeightInterfaceController;
import weightsimulator.weight.IWeightInterfaceObserver;
import weightsimulator.weight.KeyPress;

/**
 * MainController - integrating input from socket and ui. Implements ISocketObserver and IUIObserver to handle this.
 * @author Christian Budtz
 * @version 0.1 2017-01-24
 *
 */
public class MainController implements IMainController, ISocketObserver, IWeightInterfaceObserver {

	private ISocketController socketHandler;
	private IWeightInterfaceController weightController;
	private KeyState keyState = KeyState.K4;
	private Connector conn;

	private Double total = 0.0;
	private List<Character> numbers = new ArrayList<Character>();
	private int numbersPointer = 0;
	private String numberMessage;
	private boolean allowCommands = true;
	private int tempOutput = 0;

	private int opr_id;
	private int rb_id;
	private int pb_id;
	private double weight = 0.0;
	private double tarWeight = 0.0;
	boolean key1 = false;


	//Hardcoded batch and id
	private int idN = 12, batchN = 1234;
	private String idS = "Anders And", batchS = "Salt";

	public MainController(ISocketController socketHandler, IWeightInterfaceController weightInterfaceController) {
		this.init(socketHandler, weightInterfaceController);
	}

	@Override
	public void init(ISocketController socketHandler, IWeightInterfaceController weightInterfaceController) {
		this.socketHandler = socketHandler;
		this.weightController=weightInterfaceController;
		try {
			this.conn = new Connector();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		if (socketHandler!=null && weightController!=null){
			//Makes this controller interested in messages from the socket
			socketHandler.registerObserver(this);
			//Starts socketHandler in it's own thread
			new Thread(socketHandler).start();
			//weightController setup
			weightController.registerObserver(this);
			//Starts weightController in it's own thread
			new Thread(weightController).start();
		} else {
			System.err.println("No controllers injected!");
		}
	}

	//Listening for socket input
	//When we notify observers, this is the controller that gets the input
	@Override
	public void notify(SocketInMessage message) {
		if(allowCommands){
			switch (message.getType()) {
			case B:
				try{
					if (Double.parseDouble(message.getMessage()) < -weight){
						weightController.showMessageSecondaryDisplay("Cant withdraw more weight than currently on weight");
					} else{
						weight = weight + Double.parseDouble(message.getMessage());
						weightController.showMessagePrimaryDisplay(weight+"kg");
						weightController.showMessageSecondaryDisplay("Unmodified total weight:");
					}
					break;
				}
				catch(NumberFormatException e){
					weightController.showMessageSecondaryDisplay("Error: Wrong format " + e.getMessage());
					break;
				}
			case D:
				weightController.showMessagePrimaryDisplay(message.getMessage()); 
				socketHandler.sendMessage(new SocketOutMessage("D A"));
				break;
			case Q:
				quit();
				break;
			case RM204: //Does not have to be implemented
				break;
			case RM208: 
				allowCommands = false;
				pb_id = 0;
				synchronized(this){							
					try {

						try {
							doName();
						} catch (DALException e1) {
							e1.printStackTrace();
						}

						try {
							doPB();
						} catch (DALException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							int q = getRowCount(pb_id);
							List<String> Names = getProductName(pb_id); // Simulere raavare navn's liste
							for(int i = 0; i < q ; i++){

								//
								//	HER SKAL VI SÆTTE STATUS TIL 1
								//

								weightController.showMessageSecondaryDisplay("Current product to be weighted: "+Names.get(i)); // Simulere raavare navn's liste gennemgang

								weightController.showMessageSecondaryDisplay("Productbatch ID set. Place container on weight and tara.");
								key1 = true;
								this.wait();
								weightController.showMessageSecondaryDisplay("Tara set. Weight product: " + "FILLER" + " and press send.");

							}
						} catch (DALException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						//
						//	HER SKAL VI SÆTTE STATUS TIL 2
						//

						allowCommands = true;

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			case S:
				total = weight - tarWeight;
				weightController.showMessageSecondaryDisplay("The current weight is:");
				weightController.showMessagePrimaryDisplay(total.toString());
				break;
			case T:
				tara();
				break;
			case DW:
				weightController.showMessagePrimaryDisplay(message.getMessage());
				break;
			case K:
				handleKMessage(message);
				break;
			case P111:
				weightController.showMessageSecondaryDisplay(message.getMessage());
				break;
			}
		}
		else{
			weightController.showMessageSecondaryDisplay("RM20 is currently active. Send a number before proceeding.");
		}

	}

	private void handleKMessage(SocketInMessage message) {
		switch (message.getMessage()) {
		case "1" :
			this.keyState = KeyState.K1;
			break;
		case "2" :
			this.keyState = KeyState.K2;
			break;
		case "3" :
			this.keyState = KeyState.K3;
			break;
		case "4" :
			this.keyState = KeyState.K4;
			break;
		default:
			socketHandler.sendMessage(new SocketOutMessage("ES"));
			break;
		}
	}
	//Listening for UI input
	@Override
	public void notifyKeyPress(KeyPress keyPress) {
		//TODO implement logic for handling input from ui
		switch (keyPress.getType()) {
		case SOFTBUTTON:
			break;
		case TARA:
			tara();
			break;
		case TEXT:
			numbers.add(keyPress.getCharacter());
			numbersPointer++;
			numberMessage = "";
			for(int i = 0; i<numbersPointer; i++){
				numberMessage += numbers.get(i);
			}
			weightController.showMessageSecondaryDisplay(numberMessage);
			break; 
		case ZERO:
			weight = 0.0;
			tarWeight = 0.0;
			total = 0.0;
			weightController.showMessagePrimaryDisplay(total.toString());
			weightController.showMessageSecondaryDisplay(""); 
			break;
		case C:
			numbers = new ArrayList<Character>();
			numbersPointer = 0;
			System.out.println("C");
			break;
		case EXIT:
			quit();
			break;
		case SEND:
			synchronized (this){
				if (/*keyState.equals(KeyState.K4) ||*/ keyState.equals(KeyState.K3) ){
					socketHandler.sendMessage(new SocketOutMessage("K A 3"));
				}
				else if(keyState.equals(KeyState.K4)){
					socketHandler.sendMessage(new SocketOutMessage(numbers.toString()));
					//					weightController.showMessageSecondaryDisplay("You sent the numbers: " + numbers.toString());
					numbersPointer = 0;
					tempOutput = 0;
					for(int i = 0; i < numbers.size(); i++){
						tempOutput = (tempOutput*10+(numbers.get(i)-48));		//-48 to convert from ASCII to integer
					}
					numbers = new ArrayList<Character>();
				}
				else{ 
					weightController.showMessageSecondaryDisplay("No command was expecting an input. Input discarded.");
					System.out.println("No command was expecting an input. Input discarded.");
				}
				if(!key1){
					notify();
				}
			}
			break;
		}

	}

	@Override
	public void notifyWeightChange(double newWeight) {
		this.weight = newWeight; //Set the weight to be equal to the new weight
		weightController.showMessagePrimaryDisplay(weight-tarWeight+"kg"); //Print this to the GUI

	}

	public void tara(){
		tarWeight = weight;
		weightController.showMessagePrimaryDisplay(total.toString());
		System.out.println("Tarweight is: " + tarWeight);
		synchronized (this){
			if(key1){
				notify();
				key1 = false;
			}
		}
	}

	public void quit(){
		System.exit(0);
	}

	public boolean getCommandStatus(){
		return allowCommands;
	}

	private void doName() throws DALException{
		OperatoerDAO oprDAO = new MySQLOperatoerDAO();
		List<OperatoerDTO> oprList = oprDAO.getOperatoerList();
		weightController.showMessageSecondaryDisplay("Enter your operator ID: ");
		do{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if((tempOutput < oprList.size()) && (tempOutput > 0)){
				weightController.showMessagePrimaryDisplay(oprList.get(tempOutput).getOprNavn());
				opr_id = tempOutput;
			} else{
				weightController.showMessageSecondaryDisplay("Invalid user ID. Enter new ID.");
				tempOutput = 0;
			}
		}while(tempOutput < 1);
	}


	private void doPB() throws DALException{
		ProduktBatchDAO pbDAO = new MySQLProduktBatchDAO();
		List<ProduktBatchDTO> pbList = pbDAO.getProduktBatchList();
		weightController.showMessageSecondaryDisplay("Enter the ID for the productbatch you want to weight");
		do{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (tempOutput <= pbList.size() && tempOutput > 0){
				if(pbList.get(tempOutput-1).getStatus() == 0){
					pb_id = tempOutput;
				} else{
					weightController.showMessageSecondaryDisplay("The productbatch of the ID is finished or in progress, submit new ID.");

					tempOutput = 0;
				}

			} else {
				weightController.showMessageSecondaryDisplay("Invalid ID, submit new ID.");
				
				tempOutput = 0;
			}
		}while(tempOutput < 1);
		System.out.println("pb_id is: " + pb_id);
	}

	private int getRowCount(int pb_id) throws DALException{
		ProduktBatchKompDAO pbkDAO = new MySQLProduktBatchKomponentDAO();
		List<ProduktBatchKompDTO> pbList = pbkDAO.getProduktBatchKompList(pb_id);

		return pbList.size();
	}

	private List<String> getProductName(int pb_id) throws DALException{
		ViewDAO view = new MySQLViewDAO();
		List<ViewRaavareNavneDTO> viewList = view.getRaavareNavneListPbId(pb_id);

		List<String> Names = new ArrayList<String>();
		for (int i = 0; i < viewList.size(); i++){
			Names.add(viewList.get(i).getRaavareNavn());
		}
		return Names;

	}
}
