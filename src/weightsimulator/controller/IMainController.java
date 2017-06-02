package weightsimulator.controller;

import weightsimulator.socket.ISocketController;
import weightsimulator.weight.IWeightInterfaceController;

public interface IMainController {
	void init(ISocketController socketHandler, IWeightInterfaceController uiController);
	void start();

}
