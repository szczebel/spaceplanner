package sandbox.spaceplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sandbox.spaceplanner.model.ElementManager;
import sandbox.spaceplanner.model.RenderableElement;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JFileChooser.APPROVE_OPTION;

@Component
public class FilePersister {

    @Autowired
    ElementManager elementManager;

    JFileChooser fileChooser = new JFileChooser();

    public void load() {
        if (APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
            try {
                loadFrom(fileChooser.getSelectedFile());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();//todo tell user
            }
        }
    }

    public void save() {
        if (APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
            try {
                saveAs(fileChooser.getSelectedFile());
            } catch (IOException e) {
                e.printStackTrace();//todo tell user
            }
        }
    }

    private void saveAs(File file) throws IOException {
        List<RenderableElement> elements = new ArrayList<>();
        elementManager.forEach(elements::add);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(elements);
        }
    }

    private void loadFrom(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<RenderableElement> elements = (List<RenderableElement>) ois.readObject();
            elementManager.replaceAllWith(elements);
        }
    }
}
