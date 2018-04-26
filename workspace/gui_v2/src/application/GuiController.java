package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import xmlReader.instructionReader;


public class GuiController {
	private Instructie instructie;
	private Queue<Instructie> instructies=null;
	private int time=0;
	private int aantalInstructies=0;

    private Instructie huidigeInstructie, volgendeInstructie;

	private RAM RAM = new RAM();
	
	private List<PageTableEntry> ramGUI; // nodig voor ramtable op te stellen in GUI
	
	private List<PageTableEntry> pageTabel; // nodig voor pagetable op te stellen in GUI
	
	
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
	@FXML
    private TextField pidText;
	
	
	//RAM
    @FXML
    private TableView<PageTableEntry> ramTabelGui;
    
    @FXML
    private TableColumn<PageTableEntry, Integer> rFrameNummer;

    @FXML
    private TableColumn<PageTableEntry, Integer> rPageNummer;

    @FXML
    private TableColumn<PageTableEntry, Integer> rPid;
    
    
    
    //pageTabel
    @FXML
    private TableView<PageTableEntry> pageTabelGui;
    
    @FXML
    private TableColumn<PageTableEntry, Integer> pPageNummer;
    
    @FXML
    private TableColumn<PageTableEntry, Integer> pPresentBit;

    @FXML
    private TableColumn<PageTableEntry, Integer> pModifyBit;

    @FXML
    private TableColumn<PageTableEntry, Integer> pLastAccesTime;

    @FXML
    private TableColumn<PageTableEntry, Integer> pFrameNummer;
	
	
	public void file1(){
		System.out.println("file1");
		clear();
		instructionReader ir=new instructionReader();
		instructies=ir.readIn("Instructions_30_3.xml");
		setAantalInstructies(instructies.size());
		System.out.println(instructies.size());
		init();
		aanmakenRAMGui();
	}

	public void file2(){
		System.out.println("file2");
		clear();
		instructionReader ir=new instructionReader();
		instructies=ir.readIn("Instructions_20000_4.xml");
		setAantalInstructies(instructies.size());
		System.out.println(instructies.size());
		init();
		aanmakenRAMGui();

		

	}
	public void file3(){
		System.out.println("file3");
		clear();
		instructionReader ir=new instructionReader();
		instructies=ir.readIn("Instructions_20000_20.xml");
		setAantalInstructies(instructies.size());
		System.out.println(instructies.size());
		init();
		aanmakenRAMGui();

		
	}

	private void init() {
		timer.setText(String.valueOf(time));
		vPid.setText(String.valueOf(instructies.peek().getPid()));
		vInstructie.setText(instructies.peek().getOperatie());
		vVirtueelAdres.setText(String.valueOf(instructies.peek().getVirtueelAdres()));
		vPageNummer.setText(String.valueOf(instructies.peek().getVirtueelAdres()/4096));
		vOffset.setText(String.valueOf(instructies.peek().getVirtueelAdres()%4096));
		volgendeInstructie=instructies.remove();
		
	}
	

	public void eenInstructie(){
        upTime();
        timer.setText(String.valueOf(time));
		if(instructies!=null){

		    if(!instructies.isEmpty()) {

                System.out.println("1 instructie");
                if (time <= aantalInstructies) {

                    

                    huidigeInstructie = volgendeInstructie;

                    hPid.setText(String.valueOf(huidigeInstructie.getPid()));
                    hInstructie.setText(huidigeInstructie.getOperatie());
                    hVirtueelAdres.setText(String.valueOf(huidigeInstructie.getVirtueelAdres()));
                    hPageNummer.setText(String.valueOf(huidigeInstructie.getVirtueelAdres() / 4096));
                    hOffset.setText(String.valueOf(huidigeInstructie.getVirtueelAdres() % 4096));

                    instructieUitvoeren(huidigeInstructie);

                    volgendeInstructie = instructies.remove();

                    vPid.setText(String.valueOf(volgendeInstructie.getPid()));
                    vInstructie.setText(volgendeInstructie.getOperatie());
                    vVirtueelAdres.setText(String.valueOf(volgendeInstructie.getVirtueelAdres()));
                    vPageNummer.setText(String.valueOf(volgendeInstructie.getVirtueelAdres() / 4096));
                    vOffset.setText(String.valueOf(volgendeInstructie.getVirtueelAdres() % 4096));
                }
            } else{
            	System.out.println("Einde file");

                huidigeInstructie = volgendeInstructie;

                hPid.setText(String.valueOf(huidigeInstructie.getPid()));
                hInstructie.setText(huidigeInstructie.getOperatie());
                hVirtueelAdres.setText(String.valueOf(huidigeInstructie.getVirtueelAdres()));
                hPageNummer.setText(String.valueOf(huidigeInstructie.getVirtueelAdres() / 4096));
                hOffset.setText(String.valueOf(huidigeInstructie.getVirtueelAdres() % 4096));

                instructieUitvoeren(huidigeInstructie);
            }
        } else timer.setText("Kies eerst een file aub");
        
    }
	
	public void instructieUitvoeren(Instructie huidigeInstructie) {
        clearGuiPageTable();
        clearGuiRamTable();
        
        switch(huidigeInstructie.getOperatie()){
			case "Start":Start(huidigeInstructie);break;
			case "Read":io(0);break;
			case "Write":io(1);break;
			case "Terminate":Terminate(huidigeInstructie);break;
		}
        updateGui();
		
	}

	public void Start(Instructie huidigeInstructie) {
		System.out.println("start");
		Proces p=new Proces(huidigeInstructie.getPid());
		RAM.newProces(p);
		RAM.makeRoom(p);

	}

	public void io(int modifybit){
		if(!RAM.hasProces(huidigeInstructie.getPid())) RAM.makeRoom(huidigeInstructie.getPid());
		RAM.newInstruction(huidigeInstructie.getPid(),(int)huidigeInstructie.getVirtueelAdres()/4096,modifybit,time);

	}

	public void Terminate(Instructie huidigeInstructie) {
		System.out.println("terminate");
	}
	




	public void allInstructie(){
		while(!instructies.isEmpty()){
			//1instructie oproepen?
		}
		
	}
	public void aanmakenRAMGui() {
		ramGUI=new ArrayList<>();
		for(int i=0;i<12;i++){
			PageTableEntry pte=new PageTableEntry(i,-1,-1);  // framenummer pid pagenummer
			ramGUI.add(pte);
			ramTabelGui.getItems().add(pte); //GUI
		}
		rFrameNummer.setCellValueFactory(new PropertyValueFactory<>("frameNumber"));
		rPageNummer.setCellValueFactory(new PropertyValueFactory<>("pageNumber"));
		rPid.setCellValueFactory(new PropertyValueFactory<>("pid"));
	}
	
	public void aanmakenPageTabel(int pid) {
		pageTabel=new ArrayList<>();
		pidText.setText(String.valueOf(pid));
		for(int i=0;i<16;i++){
			PageTableEntry pte=new PageTableEntry(i,-0,0,-1,-1);  // pagenummer presentbit modifybit lastaccestime framenummer
			pageTabel.add(pte);
			pageTabelGui.getItems().add(pte); //GUI
		}
		pPageNummer.setCellValueFactory(new PropertyValueFactory<>("pageNumber"));
		pPresentBit.setCellValueFactory(new PropertyValueFactory<>("presentBit"));
		pModifyBit.setCellValueFactory(new PropertyValueFactory<>("modifyBit"));
		pLastAccesTime.setCellValueFactory(new PropertyValueFactory<>("lastAccessTime"));
		pFrameNummer.setCellValueFactory(new PropertyValueFactory<>("frameNumber"));
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
		//RAM.clear();

		//updateGui();

		
		for ( int i = 0; i<ramTabelGui.getItems().size(); i++) {	//tabel ram clearen
		    ramTabelGui.getItems().clear();
		}
		
	}
	
	public void clearGuiPageTable() {
		for ( int i = 0; i<pageTabelGui.getItems().size(); i++) {	//pagetableGuiwissen
			pageTabelGui.getItems().clear();
		}
			
	}
	public void clearGuiRamTable() {
		for ( int i = 0; i<ramTabelGui.getItems().size(); i++) {	//pagetableGuiwissen
			ramTabelGui.getItems().clear();
		}
			
	}
	public void upTime(){
		time++;
	}
	
	public void updateGui(){
		//----update RAMGUI-------
		for(PageTableEntry pte:RAM.getFrames()){
			ramTabelGui.getItems().add(pte);
		}
		rFrameNummer.setCellValueFactory(new PropertyValueFactory<>("frameNumber"));
		rPageNummer.setCellValueFactory(new PropertyValueFactory<>("pageNumber"));
		rPid.setCellValueFactory(new PropertyValueFactory<>("pid"));
		
		//-----update pagetableGUI------
		for(PageTableEntry pte:RAM.geefPageTableProces(huidigeInstructie.getPid())){
			pageTabelGui.getItems().add(pte);

		}
		pPageNummer.setCellValueFactory(new PropertyValueFactory<>("pageNumber"));
		pPresentBit.setCellValueFactory(new PropertyValueFactory<>("presentBit"));
		pModifyBit.setCellValueFactory(new PropertyValueFactory<>("modifyBit"));
		pLastAccesTime.setCellValueFactory(new PropertyValueFactory<>("lastAccessTime"));
		pFrameNummer.setCellValueFactory(new PropertyValueFactory<>("frameNumber")); 
		
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
