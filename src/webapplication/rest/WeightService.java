package webapplication.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import weightsimulator.controller.IMainController;
import weightsimulator.controller.MainController;
import weightsimulator.socket.ISocketController;
import weightsimulator.socket.SocketController;
import weightsimulator.weight.IWeightInterfaceController;
import weightsimulator.weight.gui.WeightInterfaceControllerGUI;


@Path("/weightservice")
public class WeightService {

	@GET
	@Path("/startweight")
	public void startWeight(){
		ISocketController socketHandler = new SocketController();
		IWeightInterfaceController weightController = new WeightInterfaceControllerGUI();
		//Injecting socket and uiController into mainController - Replace with improved versions...
		IMainController mainCtrl = new MainController(socketHandler, weightController);
		//.init and .start could be merged
		mainCtrl.start();
	}
}
