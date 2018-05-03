/*
Dibuat oleh kelompok:
- Vionilyn (00000011576)
- Octavany (00000011597)
- Anthony (00000011609)
 */

package app;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class Controller {
    // START DECLARATION AND INITIALIZATION
    @FXML Label lblInput, lblBefore, lblAfter;
    @FXML
    TextField txtInput;
    @FXML
    Button btnAdd, btnReset, btnStart, btnNext, btnPrev, btnFirst, btnDelete;
    @FXML
    ListView lvNumberArray;
    @FXML
    Pane SimulationPane;
    @FXML
    AnchorPane anchorPaneApp;
    @FXML
    TableView<TableData> tblNumber;
    @FXML TableColumn col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13, col14, col15, col16, col17, col18, col19, col20;


    // LABEL UNTUK TEMPAT YANG BENAR
    Label[] lblPlace = new Label[30];
    Label lblPosition = new Label("Position");

    //INITALIZATION FOR SHAPE
    Rectangle[] r = new Rectangle[25];
    Rectangle[] rSort = new Rectangle[25];

    // LABEL YANG MUNCUL BARENG RECTANGLE
    Label[] lblNum = new Label[30];
    // LABEL DI TABLE
    Label[] lblCount = new Label[30];
    // LABEL FOR SHOWING MESSAGE WHEN ANIMATION START
    Label lblKetPlus = new Label("Sum up the current index with the previous index");
    Label lblKetSort = new Label("Place the objects to their correct position and decrease the count by one");

    // ALERT FOR SHOWING MESSAGE BEFORE CLICK START BUTTON
    Alert message;
    //INITIALIZATION FOR COUNTSORT
    Integer temp, max;
    static ObservableList<Data> number = FXCollections.observableArrayList();

    //INITIALIZATION FOR DATA LISTVIEW AND TABLE
    ObservableList<Data> lstOfData = FXCollections.observableArrayList();
    ObservableList<TableData> tbl = FXCollections.observableArrayList();
    TableData table = new TableData();

    //FOR COUNT HOW MANY DATA INSERTED TO LISTVIEW OR TABLE
    Integer idx = -1;
    //DECLARATION VAR FOR BTN NEXT
    Integer step = 0; // AND PREV
    boolean count = true;
    boolean turnSum = true;
    Integer firstTurn = 0;
    Integer idxSum = 2;
    Integer idxSort = 0;
    Integer turnSort = 0;

    boolean turnCount = true;

    Rectangle rectSum = new Rectangle();

    //DECLARATION VAR FOR BTN PREV
    boolean count2 = true;
    boolean turnSub = true;

    //BOOLEAN TO SHOW IN SIMULATIONPANE
    boolean tes = true;
    boolean show = true;
    // END DECLARATION AND INITIALIZATION

    // START METHOD
    public void settingButton(boolean disRes, boolean disStart, boolean visStart, boolean visNext, boolean visPrev, boolean visRep, boolean disNext, boolean disPrev, boolean disRep, boolean visAdd, boolean disAdd){
        btnReset.setDisable(disRes);
        btnStart.setDisable(disStart);
        btnStart.setVisible(visStart);
        btnNext.setVisible(visNext);
        btnPrev.setVisible(visPrev);
        btnFirst.setVisible(visRep);
        btnNext.setDisable(disNext);
        btnPrev.setDisable(disPrev);
        btnFirst.setDisable(disRep);
        btnAdd.setVisible(visAdd);
        btnAdd.setDisable(disAdd);
    }

    public void createLabelPlaces(int max, int code){
        if(code == 2){
            lblPosition.setFont(new Font("Lucida Sans Unicode",12));
            lblPosition.relocate(14, 495);
            lblPosition.setVisible(false);
            SimulationPane.getChildren().add(lblPosition);
        }

        else if(code == 1)
            lblPosition.setVisible(true);
        else{
            lblPosition.setVisible(false);
        }
        for(Integer i = 1; i<=max+1; i++){

            if(code == 1)
                lblPlace[i].setVisible(true);
            else if(code == 0)
                lblPlace[i].setVisible(false);
            else if(code == 2){
                lblPlace[i] = new Label("" + i.toString());
                lblPlace[i].setFont(new Font("Lucida Sans Unicode",12));
                lblPlace[i].relocate(54.0 + i*40, 495);
                SimulationPane.getChildren().add(lblPlace[i]);
                lblPlace[i].setVisible(false);
            }

        }
    }

    public void createLabelNumber(int n){
        for(int i = 1; i<=n; i++){
            lblCount[i] = new Label();
            lblCount[i].setText("0");
            lblCount[i].setFont(new Font("Lucida Sans Unicode",12));
            lblCount[i].relocate(135 + (i-1) * 35, 262);
            SimulationPane.getChildren().add(lblCount[i]);
        }
    }

    public void createRectangle(Integer num){
        r[idx] = new Rectangle(40, num * 8, Color.CORAL);
        r[idx].setX(80 + (idx * 40));
        r[idx].setY(180 - (num * 8));

        r[idx].setAccessibleText(new String(String.valueOf(num)));
        r[idx].setStroke(Color.RED);
        SimulationPane.getChildren().add(r[idx]);

        lblNum[idx] = new Label();
        lblNum[idx].setText(num.toString());
        lblNum[idx].setFont(new Font("Lucida Sans Unicode",14));
        lblNum[idx].relocate(94 + (idx * 40), 182);
        SimulationPane.getChildren().add(lblNum[idx]);
    }

    public void initMsg(Label namaLabel, int posX, int posY, Paint color){
        namaLabel.relocate(posX, posY);
        namaLabel.setVisible(false);
        namaLabel.setFont(new Font("Lucida Sans Unicode",14));
        namaLabel.setTextFill(color);
        SimulationPane.getChildren().add(namaLabel);
    }

    public void initRectSum(double w, double h, double initX, double initY) {
        rectSum = new Rectangle(w, h, Color.TRANSPARENT);
        rectSum.setStroke(Color.GREEN);
        rectSum.setStrokeWidth(3);
        rectSum.setVisible(false);
        rectSum.relocate(initX, initY);
        SimulationPane.getChildren().add(rectSum);
    }

    public void showMessage(String title, String header, String text,Alert.AlertType type){
        message = new Alert(type);
        message.setTitle(title);
        message.setHeaderText(header);
        message.setContentText(text);
        message.show();
    }


    public static void printArray(ObservableList<Data> A){
        A.forEach(i -> System.out.println(i.getValue()));
    }
    public static void reset(){
        number.removeAll(number);
    }

    public void fadeIn(Integer idx, int code){
        FadeTransition ft = new FadeTransition(Duration.millis(1000), lblCount[idx]);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

        temp = Integer.parseInt(lblCount[idx].getText().toString());
        if(code == 1){
            temp = temp+1;
        }
        else if(code == 2){
            temp = temp-1;
        }
        else if(code == 3){
            temp = temp + Integer.parseInt(lblCount[idx-1].getText().toString());
        }
        else if(code == 4){
            temp = temp - Integer.parseInt(lblCount[idx-1].getText().toString());
        }
        lblCount[idx].setText("" + temp.toString());
    }

    public void fadeOut(Integer idx){
        FadeTransition ft  = new FadeTransition(Duration.millis(1000), lblCount[idx]);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    public void handleBtnAdd() {
        if (show) {
            btnReset.setDisable(false);
            Integer num = null;
            try {
                num = Integer.parseInt(txtInput.getText());
            } catch (NumberFormatException ex) {
                showMessage(new String("Input Warning!"), new String("Input Invalid!"),
                        new String("Only insert a number"), Alert.AlertType.WARNING);
                System.out.println("You can only insert a number");
            }

            if (idx < 19) {
                if (num != null) {
                    if (num <= 20 && num > 0) {
                        lstOfData.add(new Data(num));
                        lvNumberArray.getItems().add(num);
                        idx++;
                        createRectangle(num);
                    } else  {
                        showMessage(new String("Input Warning!"), new String("Out of Range"),
                                new String("Your input must be greater than 0 but lesser or equal than 20"), Alert.AlertType.WARNING);
                        System.out.println("Maksimal angka adalah 20!");
                    }
                }
            } else {
                showMessage(new String("Input Warning!"), new String("Maximum Total Number Inserted"), new String("Maximum number is 20"), Alert.AlertType.WARNING);
                System.out.println("Maksimal banyak angka adalah 20");
            }
            txtInput.setText(null);
        }
    }

    @FXML
    public void handleBtnStart(){
        if(idx >= 1) {
            if(show) {
                settingButton(false,true,false,true,true,true,false,
                        true,true,false,true);
                txtInput.setVisible(false);
                lblInput.setVisible(false);

                if (!tes) {
                    SimulationPane.getChildren().add(tblNumber);
                }

                if(tes) {
                    tbl.add(table);
                    tblNumber.setItems(tbl);
                }
                tblNumber.refresh();

                //COUNT SORT START HERE

                tblNumber.setVisible(true);

                //init message to tell what will happen next
                initMsg(lblKetPlus, 270, 210, Color.BLUE);
                initMsg(lblKetSort, 200, 300, Color.BLUE);
                //INIT RECT IN TABLE
                initRectSum(30,53,125.0,232.0);

                //SEARCH FOR MAX VALUE
                max = -1;
                for(Data a: lstOfData){
                    if(max < a.getValue()){
                        max = a.getValue();
                    }
                }
                createLabelNumber(max);

                createLabelPlaces(idx, 2);

                show = false;

                btnDelete.setDisable(true);
            }
        }
        else if(idx < 1){
            showMessage(new String("Input Warning!"), new String("Minimal Total Number Inserted!"),new String("Minimal number is 2"), Alert.AlertType.WARNING);
            System.out.println("Minumum numbers to be sorted are 2");
        }
    }
    @FXML
    public void handleBtnReset(){
        step = 0;
        count = true;
        count2 = true;
        //CLEAR ALL DATA IN LIST
        reset();
        lstOfData.removeAll(lstOfData);
        settingButton(true,false,true,false,false,false,
                true,true,true,true,false);;
        txtInput.setVisible(true);
        lblInput.setVisible(true);

        tes = false;
        show = true;
        idx = -1;
        idxSum = 2;
        idxSort = 0;
        turnSort = 0;
        turnCount = true;
        turnSum = true;
        firstTurn = 0;
        turnSub = true;

        //CLEAR ALL OBJECT IN LISTVIEW AND PANE
        lvNumberArray.getItems().clear();
        SimulationPane.getChildren().clear();
        SimulationPane.getChildren().add(lblBefore);
        SimulationPane.getChildren().add(lblAfter);

        btnDelete.setDisable(false);
    }

    @FXML
    public void handleBtnNext(){
        step++;
        // START BUAT ANIMASI YANG HITUNG JUMLAH ANGKA TERSEBUT YANG MUNCUL
        if(step > 2 && turnCount && step < 2*lstOfData.size() + 2){ //buat ganti warna kotak yg udah dikerjain jadi biru
            r[(step-2)/2].setStrokeWidth(1);
            r[(step-2)/2].setStroke(Color.LIGHTSEAGREEN);
            r[(step-2)/2].setFill(Color.LIGHTSKYBLUE);

            if(step == 2*lstOfData.size() + 1)
                rectSum.setVisible(false);

            count = true;
        }
        if(step > 2*lstOfData.size()){ //ini nandain nambah angka di label sesuai sama angka kotak udah selesai, makanya count nya di false
            count = false;
        }
        if(count){
            if(turnCount){ //turncount = true -> buat nandain kotak yg lg dikerjain jd warna ijo
                r[step/2].setStroke(Color.GREEN);
                r[step/2].setStrokeWidth(3);
                rectSum.setVisible(true);

                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), rectSum);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.setCycleCount(1);
                fadeTransition.setAutoReverse(false);
                fadeTransition.play();

                rectSum.relocate(88+(Integer.parseInt(lblNum[step/2].getText().toString())) * 35, 232);

                turnCount = !turnCount;
            }
            else{ //ditambah angka labelnya
                fadeOut(lstOfData.get((step-1)/2).getValue());
                fadeIn(lstOfData.get((step-1)/2).getValue(), 1);
                turnCount = !turnCount;
            }
        }
        // END BUAT ANIMASI YANG HITUNG JUMLAH ANGKA TERSEBUT YANG MUNCUL
        // START BUAT ANIMASI JUMLAHIN ARRAY INDEX SKRG DG SBLMNYA
        else if(step == 2*lstOfData.size() + 2){ //munculin kotak ijo buat animasi jumlahin array
            initRectSum(60,20,125.0,262.0);

            FadeTransition ftKet  = new FadeTransition(Duration.millis(2000), lblKetPlus);
            ftKet.setFromValue(0.0);
            ftKet.setToValue(1.0);
            ftKet.setCycleCount(1);
            lblKetPlus.setVisible(true);
            ftKet.play();
        }
        else if(step > 2*lstOfData.size() + 2 && step < 2*lstOfData.size() + 1 + 2*max){ //ini buat jumlahin sama geser kotak
            if(turnSum){
                if(firstTurn == 0){  //klo masih 0, munculin dulu kotaknya
                    rectSum.setVisible(true);
                    firstTurn++;
                }
                else{ //klo bukan 0, kotaknya dipindah, firstTurn tuh buat jd index pindain kotak
                    TranslateTransition translateTransition =
                            new TranslateTransition(Duration.millis(1000), rectSum);
                    translateTransition.setFromX(rectSum.getX());
                    translateTransition.setToX((rectSum.getX()+34));
                    translateTransition.setCycleCount(1);
                    translateTransition.play();
                    rectSum.relocate(125 + (firstTurn-1) * 35, 262);
                    firstTurn++;
                }

                turnSum = !turnSum; //ini buat ganti2an sama nambahin
                turnSub = !turnSub; //ini diubah2 biar pas di prev nya sesuai
            }
            else{ //dijumlahin sama array index sblmnya
                fadeOut(idxSum);
                fadeIn(idxSum, 3);
                idxSum++;//buat nandain array index ke brp yg dijumlahin
                turnSum = !turnSum; //ganti2an sama geser kotak
                turnSub = !turnSub; //ini diubah2 biar pas di prev nya sesuai
            }
        }
        // END BUAT ANIMASI JUMLAHIN ARRAY INDEX SKRG DG SBLMNYA
        // START BUAT ANIMASI PINDAHIN RECT JADI TERURUT
        else if(step == lstOfData.size()*2 + 1 + 2*max){ //munculin kotak ijo di kotak awal sama di kotak tabel angka itu
            rectSum.setVisible(false);
            FadeTransition ftKet  = new FadeTransition(Duration.millis(2000), lblKetPlus);
            ftKet.setFromValue(1.0);
            ftKet.setToValue(0.0);
            ftKet.setCycleCount(1);

            ftKet.play();
            FadeTransition ftKet2  = new FadeTransition(Duration.millis(2000), lblKetSort);
            ftKet2.setFromValue(0.0);
            ftKet2.setToValue(1.0);
            ftKet2.setCycleCount(1);
            lblKetSort.setVisible(true);
            ftKet2.play();
            initRectSum(30,53,125.0,232.0);

            createLabelPlaces(idx, 1);
        }
        else if(step > lstOfData.size()*2 + 1 + 2*max && step < lstOfData.size() * 2 + 2 + 2*max + 3*(idx+1)){
            if(turnSort%3 == 0){ //turnSort tuh buat flag, soalnya ada 3 animasi, muncul kotak ijo, pindain kotak, nilai di tabel berkurang, klo 0 berarti muncul kotak ijo
                rectSum.setVisible(true);
                rSort[idxSort] = new Rectangle(40, 8 * Integer.parseInt(lblNum[idxSort].getText().toString()), Color.MEDIUMPURPLE);
                rSort[idxSort].setX(80 + (idxSort * 40));
                rSort[idxSort].setY(180 - (Integer.parseInt(lblNum[idxSort].getText().toString()) * 8));

                rSort[idxSort].setAccessibleText(new String(String.valueOf(Integer.parseInt(lblNum[idxSort].getText().toString()))));
                rSort[idxSort].setStroke(Color.MEDIUMORCHID);
                SimulationPane.getChildren().add(rSort[idxSort]);

                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), rectSum);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.setCycleCount(1);
                fadeTransition.setAutoReverse(false);
                fadeTransition.play();

                rectSum.relocate(88+(Integer.parseInt(lblNum[idxSort].getText().toString())) * 35, 232);

                turnSort++;
            }
            else if(turnSort % 3 == 1){ // kalo 1 berarti mindain kotak
                TranslateTransition translateTransition =
                        new TranslateTransition(Duration.millis(2000), rSort[idxSort]);
                translateTransition.setFromX(0);
                translateTransition.setFromY(0);
                translateTransition.setToX(((Integer.parseInt(lblCount[lstOfData.get(idxSort).getValue()].getText().toString()) - 1) * 40) - idxSort * 40);
                translateTransition.setToY(310);
                translateTransition.setCycleCount(1);
                translateTransition.play();
                turnSort++;
            }
            else{ //klo 2 berarti buat ngurangin nilainya
                fadeOut(lstOfData.get(idxSort).getValue());
                fadeIn(lstOfData.get(idxSort).getValue(), 2);
                turnSort++;
                idxSort++;
            }
        }
        // END BUAT ANIMASI PINDAHIN RECT JADI TERURUT
        else if(step == lstOfData.size() * 2 + 2 + 2*max + 3*(idx+1)){
            rectSum.setVisible(false);
        }
        else if(step > lstOfData.size() * 2 + 2 + 2*max + 3*(idx+1)){//count sort done
            settingButton(false,true,false,true,true,true,true,true,true,false,true);
            showMessage(new String("Sorting Finished!"), new String("Horeee!!"), new String("The sorting is already finished..."),Alert.AlertType.INFORMATION);
        }

        // handle flag prev
        if(step > 0 && step <= lstOfData.size() * 2 + 2 + 2*max + 3*(idx+1)){
            btnPrev.setDisable(false);
            btnFirst.setDisable(false);
            if(step <= lstOfData.size()*2){
                if(step % 2 == 1){
                    count2 = false;
                }
                else{
                    count2 = true;
                }
            }
        }
    }

    @FXML
    public void handleBtnPrev(){
        step--;
        if(step == lstOfData.size() * 2 + 1 + 2*max + 3*(idx+1)){
            rectSum.setVisible(true);

        }
        else if(step < lstOfData.size() * 2 + 1 + 2*max + 3*(idx+1) && step > lstOfData.size()*2 + 2*max){
            turnSort--;
            //System.out.println(turnSort);
            if(turnSort % 3 == 2){

                idxSort--;
                fadeOut(lstOfData.get(idxSort).getValue());
                fadeIn(lstOfData.get(idxSort).getValue(), 1);
            }
            else if(turnSort % 3 == 1){

                TranslateTransition translateTransition =
                        new TranslateTransition(Duration.millis(2000), rSort[idxSort]);
                translateTransition.setFromX(((Integer.parseInt(lblCount[lstOfData.get(idxSort).getValue()].getText().toString()) - 1) * 40) - (idxSort) * 40);
                translateTransition.setFromY(310);
                translateTransition.setToX(0);
                translateTransition.setToY(0);
                translateTransition.setCycleCount(1);
                translateTransition.play();
            }
            else{
                rSort[idxSort].setVisible(false);
                if(turnSort == 0){
                    rectSum.setVisible(false);
                }
                else {

                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), rectSum);
                    fadeTransition.setFromValue(0.0);
                    fadeTransition.setToValue(1.0);
                    fadeTransition.setCycleCount(1);
                    fadeTransition.setAutoReverse(false);
                    fadeTransition.play();

                    rectSum.relocate(88 + (Integer.parseInt(lblNum[idxSort - 1].getText().toString())) * 35, 232);
                }
            }
        }
        else if(step == lstOfData.size()*2 + 2*max){
            initRectSum(60,20,125.0,262.0);
            rectSum.relocate(125 + (firstTurn-1) * 35, 262);
            rectSum.setVisible(true);

            createLabelPlaces(idx, 0);

            FadeTransition ftKet  = new FadeTransition(Duration.millis(500), lblKetPlus);
            ftKet.setFromValue(0.0);
            ftKet.setToValue(1.0);
            ftKet.setCycleCount(1);
            lblKetPlus.setVisible(true);
            ftKet.play();

            lblKetSort.setVisible(false);
        }
        if(step < lstOfData.size()*2 + 2*max && step > lstOfData.size()*2 + 1){
            if(turnSub){
                fadeOut(idxSum-1);
                fadeIn(idxSum-1, 4);
                turnSub = !turnSub;
                turnSum = !turnSum;
                idxSum--;
            }
            else{
                if(step != lstOfData.size()*2 + 2) {
                    TranslateTransition translateTransition =
                            new TranslateTransition(Duration.millis(1000), rectSum);
                    translateTransition.setFromX(rectSum.getX());
                    translateTransition.setToX((rectSum.getX() - 34));
                    translateTransition.setCycleCount(1);
                    translateTransition.play();
                    rectSum.relocate(125 + (firstTurn - 1) * 35, 262);
                    firstTurn--;

                }
                else{
                    rectSum.setVisible(false);
                    firstTurn--;
                }
                turnSum = !turnSum;
                turnSub = !turnSub;
            }
        }
        else if(step == lstOfData.size()*2 + 1){
            lblKetPlus.setVisible(false);
        }
        else if(step == lstOfData.size()*2){
            initRectSum(30,53,125.0,232.0);
            rectSum.relocate(88+(Integer.parseInt(lblNum[(step-1)/2].getText().toString())) * 35, 232);
            rectSum.setVisible(true);
            r[(step-1)/2].setStroke(Color.GREEN);
            r[(step-1)/2].setStrokeWidth(3);
        }
        else if(step < lstOfData.size()*2 && step > 0){
            if(count2){
                fadeOut(lstOfData.get((step-1)/2).getValue());
                fadeIn(lstOfData.get((step-1)/2).getValue(), 2);
                count2 = !count2;
            }
            else{
                r[(step-1)/2].setStroke(Color.GREEN);
                r[(step-1)/2].setStrokeWidth(3);
                rectSum.setVisible(true);

                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), rectSum);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.setCycleCount(1);
                fadeTransition.setAutoReverse(false);
                fadeTransition.play();

                rectSum.relocate(88+(Integer.parseInt(lblNum[(step-1)/2].getText().toString())) * 35, 232);
                count2 = !count2;
            }
        }
        if(step == 0){
            rectSum.setVisible(false);
            btnPrev.setDisable(true);
            btnFirst.setDisable(true);
            r[(step)/2].setStrokeWidth(1);
            r[(step)/2].setStroke(Color.RED);
            r[(step)/2].setFill(Color.CORAL);
        }

        if(step < lstOfData.size()*2 && count2){
            r[(step)/2].setStrokeWidth(1);
            r[(step)/2].setStroke(Color.RED);
            r[(step)/2].setFill(Color.CORAL);
        }

        //handle flag next;
        if(step <= lstOfData.size()){
            count = true;
            if(step % 2 == 1){
                turnCount = false;
            }
            else{
                turnCount = true;
            }
        }

    }
    //END METHOD


    @FXML
    public void handleBtnFirst(){
        //remove labelcount (buat hitung2, yg ditabel)
        SimulationPane.getChildren().removeAll(lblCount);
        //create lg labelcount yang baru
        createLabelNumber(max);

        //reset var for btnnext and btnprev
        step = 0;
        count = true;
        turnSum = true;
        firstTurn = 0;
        idxSum = 2;
        idxSort = 0;
        turnSort = 0;
        turnCount = true;
        count2 = true;
        turnSub= true;
        createLabelPlaces(idx, 0);
        //set visible rectSum dan inisialisasi ulang letak rectSum
        rectSum.setVisible(false);
        initRectSum(30,53,125.0,232.0);

        //reset disable button
        btnFirst.setDisable(true);
        btnPrev.setDisable(true);

        //reset color rectangle
        for(int i = 0;i<lstOfData.size();i++){
            r[i].setFill(Color.CORAL);
            r[i].setStroke(Color.RED);
            r[i].setStrokeWidth(1);
        }

        //remove rsort rectangle
        SimulationPane.getChildren().removeAll(rSort);

        //reset visible label ket
        lblKetPlus.setVisible(false);
        lblKetSort.setVisible(false);
    }
    //END METHOD

    @FXML
    public void handleBtnDelete()
    {
        int idx = lvNumberArray.getSelectionModel().getSelectedIndex();
        if (idx != -1) {
            System.out.println(idx);
            SimulationPane.getChildren().clear();
            lstOfData.remove(idx);
            this.idx = -1;
            lstOfData.forEach((data) -> {
                System.out.println(data.value);
                this.idx++;
                createRectangle(data.value);
            });
            lvNumberArray.getItems().remove(idx);
            tes= false;
            tbl.add(table);
            tblNumber.setItems(tbl);
            tblNumber.refresh();
            SimulationPane.getChildren().add(lblBefore);
            SimulationPane.getChildren().add(lblAfter);
        } else {
            showMessage("Warning", "Cannot delete an element",
                    "Please select a number!", Alert.AlertType.WARNING);
        }
    }
}
