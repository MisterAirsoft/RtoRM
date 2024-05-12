package RtoRMusic;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Mp3DragAndDrop extends JFrame {
    private JPanel mp3Panel;
    private JLabel dropFolderLabel;

    public Mp3DragAndDrop() {
        setTitle("MP3 Drag and Drop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mp3Panel = new JPanel();
        mp3Panel.setLayout(new BoxLayout(mp3Panel, BoxLayout.Y_AXIS));
        mp3Panel.setTransferHandler(new Mp3TransferHandler());
        mp3Panel.setDropTarget(new DropTarget(mp3Panel, new Mp3DropTargetListener()));

        dropFolderLabel = new JLabel("Drop MP3 files here");
        dropFolderLabel.setOpaque(true);
        dropFolderLabel.setBackground(Color.LIGHT_GRAY);
        dropFolderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dropFolderLabel.setTransferHandler(new FolderTransferHandler());
        dropFolderLabel.setDropTarget(new DropTarget(dropFolderLabel, new FolderDropTargetListener()));

        JScrollPane scrollPane = new JScrollPane(mp3Panel);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        add(scrollPane, BorderLayout.CENTER);
        add(dropFolderLabel, BorderLayout.SOUTH);

        setSize(400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Mp3DragAndDrop::new);
    }

    private class Mp3TransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            Transferable transferable = support.getTransferable();
            try {
                java.util.List<File> fileList = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                for (File file : fileList) {
                    if (file.getName().toLowerCase().endsWith(".mp3")) {
                        addMp3File(file);
                    }
                }
                return true;
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    private class Mp3DropTargetListener extends DropTargetAdapter {
        @Override
        public void drop(DropTargetDropEvent event) {
            event.acceptDrop(DnDConstants.ACTION_COPY);
            Transferable transferable = event.getTransferable();
            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                try {
                    java.util.List<File> fileList = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : fileList) {
                        if (file.getName().toLowerCase().endsWith(".mp3")) {
                            addMp3File(file);
                        }
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class FolderTransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            Transferable transferable = support.getTransferable();
            try {
                java.util.List<File> fileList = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                for (File file : fileList) {
                    if (file.isDirectory()) {
                        // Implement folder handling logic here
                        JOptionPane.showMessageDialog(null, "Folder " + file.getName() + " received.");
                    }
                }
                return true;
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private class FolderDropTargetListener extends DropTargetAdapter {
        @Override
        public void drop(DropTargetDropEvent event) {
            event.acceptDrop(DnDConstants.ACTION_COPY);
            Transferable transferable = event.getTransferable();
            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                try {
                    java.util.List<File> fileList = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : fileList) {
                        if (file.isDirectory()) {
                            // Implement folder handling logic here
                            JOptionPane.showMessageDialog(null, "Folder " + file.getName() + " received.");
                        }
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void addMp3File(File file) {
        JLabel mp3Label = new JLabel(file.getName());
        mp3Panel.add(mp3Label);
        mp3Panel.revalidate();
        mp3Panel.repaint();

        // Définir le chemin complet du répertoire "Music"
        String musicFolderPath = "Music/";

        // Créer le dossier "Music" s'il n'existe pas
        File musicFolder = new File(musicFolderPath);
        if (!musicFolder.exists()) {
        	musicFolder.mkdir();
           
        }

        // Déplacer/copier le fichier MP3 dans le dossier "Music"
        try {
            Files.copy(file.toPath(), new File(musicFolderPath + File.separator + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            
        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }

}
