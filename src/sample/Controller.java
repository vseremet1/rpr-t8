package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;

import javafx.scene.control.Button;


public class Controller {
    public TextField podstring;
    public Button trazi;
    public ListView spisak;
    public File korijenski = new File(System.getProperty("user.home"));
    private Thread t;

    private ObservableList<String> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        spisak.setItems(lista);
    }

    private void IzvrsavanjePretrage(String put, String nalazilo_se_u_trenutku_slanja) {

        File trazeni = new File(put);

        if (trazeni.isFile())
            if (trazeni.getName().contains(podstring.getText()))
                lista.add(trazeni.getAbsolutePath());


        if (trazeni.isDirectory())
            for (File novi : trazeni.listFiles())
                IzvrsavanjePretrage(novi.getAbsolutePath(), nalazilo_se_u_trenutku_slanja);

        File f = new File(put);

        if (f.getAbsolutePath().equals(korijenski.getAbsolutePath()))
            trazi.setDisable(false);

    }

    public void Pretrazi(ActionEvent actionEvent) {

        trazi.setDisable(true);
        lista.clear();

        Pretrazivanje pr = new Pretrazivanje();
        t = new Thread(pr);
        t.start();
    }

    public class Pretrazivanje implements Runnable {
        @Override
        public void run() {
            IzvrsavanjePretrage(korijenski.getAbsolutePath(), podstring.getText());
        }
    }

}