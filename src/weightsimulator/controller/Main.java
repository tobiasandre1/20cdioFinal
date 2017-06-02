package weightsimulator.controller;

import weightsimulator.socket.SocketController;
import weightsimulator.weight.IWeightInterfaceController;
import weightsimulator.weight.gui.WeightInterfaceControllerGUI;
import weightsimulator.socket.ISocketController;
/**
 * Simple class to fire up application and inject implementations
 * @author Christian
 *
 */
public class Main {
	//private static boolean gui= true;

	public static void main(String[] args) {
		ISocketController socketHandler = new SocketController();
		IWeightInterfaceController weightController = new WeightInterfaceControllerGUI();
		//Injecting socket and uiController into mainController - Replace with improved versions...
		IMainController mainCtrl = new MainController(socketHandler, weightController);
		//.init and .start could be merged
		mainCtrl.start();
		
	}
}
