package application;

import java.util.Queue;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Instructie;
import model.Proces;
import xmlReader.instructionReader;


public class GuiController {
	private Instructie instructie;
	private Queue<Instructie> instructies=null;
	private int time=0;
	private int aantalInstructies=0;
	
	//alle FXML textfields
	@FXML
    private TextField timer;
	@FXML
    private TextField wRam;
	@FXML
    private TextField wDisk;
	@FXML
    private TextField hPid;
	@FXML
    private TextField vPid;
	@FXML
    private TextField hInstructie;
	@FXML
    private TextField vInstructie;
	@FXML
    private TextField hVirtueelAdres;
	@FXML
    private TextField vVirtueelAdres;
	@FXML
    private TextField hPageNummer;
	@FXML
    private TextField vPageNummer;
	@FXML
    private TextField hFrameNummer;
	@FXML
    private TextField vFrameNummer;
	@FXML
    private TextField hOffset;
	@FXML
    private TextField vOffset;
	
	
	public void file1(){
		System.out.println("file1");
		clear();
		instructionReader ir=new instructionReader();
		instructies=ir.readIn("Instructions_30_3.xml");
		setAantalInstructies(instructies.size());
		
		System.out.println(instructies.size());
		
		
	}
	public void file2(){
		System.out.println("file2");
		clear();
		instructionReader ir=new instructionReader();
		instructies=ir.readIn("Instructions_20000_4.xml");
		setAantalInstructies(instructies.size());
		
		
		System.out.println(instructies.size());
		

	}
	public void file3(){
		System.out.println("file3");
		clear();
		instructionReader ir=new instructionReader();
		instructies=ir.readIn("Instructions_20000_20.xml");
		setAantalInstructies(instructies.size());
		
		System.out.println(instructies.size());
		
	}
	public void eenInstructie(){
		if(instructies!=null){
			System.out.println("1 instructie");
			upTime();
			if(time<=aantalInstructies){
				
				timer.setText(String.valueOf(time));
			
				Instructie huidigeInstructie =instructies.remove();
			
				hPid.setText(String.valueOf(huidigeInstructie.getPid()));
				hInstructie.setText(huidigeInstructie.getOperatie());
				hVirtueelAdres.setText(String.valueOf(huidigeInstructie.getVirtueelAdres()));
				hPageNummer.setText(String.valueOf(huidigeInstructie.getVirtueelAdres()/4096));
				hOffset.setText(String.valueOf(huidigeInstructie.getVirtueelAdres()%4096));
				
				instructieUitvoeren(huidigeInstructie);
				
				vPid.setText(String.valueOf(instructies.peek().getPid()));
				vInstructie.setText(instructies.peek().getOperatie());
				vVirtueelAdres.setText(String.valueOf(instructies.peek().getVirtueelAdres()));
				vPageNummer.setText(String.valueOf(instructies.peek().getVirtueelAdres()/4096));
				vOffset.setText(String.valueOf(instructies.peek().getVirtueelAdres()%4096));
				huidigeInstructie=null;
			}
			
		}
		
	}
	
	public void instructieUitvoeren(Instructie huidigeInstructie) {
		switch(huidigeInstructie.getOperatie()){
			case "Start":Start(huidigeInstructie);break;
			case "Read":Read(huidigeInstructie);break;
			case "Write":Write(huidigeInstructie);break;
			case "Terminate":Terminate(huidigeInstructie);break;
		}
		
		
	}
	
	public void Start(Instructie huidigeInstructie) {
		Proces p=new Proces(huidigeInstructie.getPid());
		System.out.println("start");
		
		
	}
	public void Read(Instructie huidigeInstructie) {
		System.out.println("read");
		
		
	}
	public void Write(Instructie huidigeInstructie) {
		System.out.println("write");
		
	}

	public void Terminate(Instructie huidigeInstructie) {
		System.out.println("terminate");
		
		
	}



	public void allInstructie(){
		System.out.println("all instructie");
		
	}
	private void clear() {
		instructies=null;
		setTime(0);
		setAantalInstructies(0);
		timer.setText("");
		hPid.setText("");
		hInstructie.setText("");
		hVirtueelAdres.setText("");
		hPageNummer.setText("");
		hOffset.setText("");
		
		vPid.setText("");
		vInstructie.setText("");
		vVirtueelAdres.setText("");
		vPageNummer.setText("");
		vOffset.setText("");
		
		
	}
	
	public void upTime(){
		time++;
	}
	
	
	//getters en setters
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getAantalInstructies() {
		return aantalInstructies;
	}
	public void setAantalInstructies(int aantalInstructies) {
		this.aantalInstructies = aantalInstructies;
	}


}
