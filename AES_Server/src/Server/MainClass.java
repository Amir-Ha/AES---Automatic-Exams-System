package Server;
import GUI.ServerController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {

	public static void main( String args[] ) throws Exception
	{
		//launch Connection frame
		launch(args);	
	} // end main
	
	@Override
	public void start(Stage arg0) throws Exception {
		//start Connection frame
		ServerController co=new ServerController();
		co.start(arg0);
	}

}
