package RtoRMusic;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.TreeSet;

public class Mp3DragAndDrop extends JFrame {
    private JPanel mp3Panel;
    private JLabel dropFolderLabel;
    private Model m;

    public Mp3DragAndDrop(Model m) {
    	this.m=m;
        setTitle("MP3 Drag and Drop");
        addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Fermer la JFrame actuelle
				dispose();
			}
		});
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
        Musique musique = new Musique(file.getName());
		m.play_list.put(m.normalisation_text(musique.titre + musique.artist.split("/")[0]), musique);
		
		ImageIcon imageIcon = new ImageIcon(musique.artwork.getBinaryData());
		Image resizedImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
		imageLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
		musique.image_musique=imageLabel;
		
		if (file.getName().compareTo(
				m.normalisation_text(musique.titre + musique.artist.split("/")[0]) + ".mp3") != 0) {

			file.renameTo(new File("Music/"
					+ m.normalisation_text(musique.titre + musique.artist.split("/")[0]) + ".mp3"));
		}
		

		for (String artist : musique.artist.split("/")) {

			if (m.listeAlbumParArtiste.containsKey(artist)) {
				if (!(m.listeAlbumParArtiste.get(artist).contains(musique.album))) {
					m.listeAlbumParArtiste.get(artist).add(musique.album);
				}

			} else {

				TreeSet<String> listeAlbum = new TreeSet<>();
				listeAlbum.add(musique.album);
				m.listeAlbumParArtiste.put(artist, listeAlbum);

			}

		}

		if (m.listeMusiqueParAlbum.containsKey(musique.album)
				&& (m.listeMusiqueParAlbum.get(musique.album).last() != musique)) {
			m.listeMusiqueParAlbum.get(musique.album).add(musique);
		} else if ((m.listeMusiqueParAlbum.containsValue(musique)) == false) {

			TreeSet<String> listeMusique = new TreeSet<>();
			listeMusique.add(musique.titre);
			m.listeAlbumParArtiste.put(musique.album, listeMusique);

		}




    }

}
