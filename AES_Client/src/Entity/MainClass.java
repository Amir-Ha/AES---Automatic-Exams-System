package Entity;
import GUI.ConnectionController;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Main Class - to Start the UI
 *
 */
public class MainClass extends Application {

	public static void main( String args[] ) throws Exception
	{
		//launch Connection frame
		launch(args);		
	} // end main
	
	@Override
	public void start(Stage arg0) throws Exception {
		//start Connection frame
		ConnectionController co=new ConnectionController();
		co.start(arg0);
	}

}
