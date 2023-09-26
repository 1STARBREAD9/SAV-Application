import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.JTableHeader;
import javax.swing.ImageIcon;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GestionClientSav extends JFrame {
    private JTextField nomField, prenomField, telephoneField, numeroTicketField;
    private JTextArea reclamationArea;
    private DefaultTableModel tableModel;
    private JTextField dateField;
    private JComboBox<String> etatTicketComboBox;

    // Générez un numéro de ticket aléatoire
    Random random = new Random();
    private int genererNumeroTicketUnique() {
        int numeroTicket;
        do {
            numeroTicket = new Random().nextInt(1000) + 1;
        } while (ticketsTableContainsNumeroTicket(numeroTicket));

        return numeroTicket;
    }

    private boolean ticketsTableContainsNumeroTicket(int numeroTicket) {
        // Votre logique pour vérifier si le numéro de ticket existe déjà dans le tableau
        return false;
    }

    private JLabel createLogoLabel(int width, int height) {
        ImageIcon logoIcon = new ImageIcon("C:/Users/pavly/IdeaProjects/Application SAV/src/LogoMain.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        return logoLabel;
    }

    public GestionClientSav() {
        setTitle("Formulaire de demande d'assistance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel formulairePanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        JPanel tablePanel = new JPanel(new BorderLayout());

        int logoWidth = 120;
        int logoHeight = 120;
        JLabel logoLabel = createLogoLabel(logoWidth, logoHeight);
        formulairePanel.add(logoLabel, BorderLayout.NORTH);

        inputPanel.add(new JLabel("Nom :"));
        nomField = new JTextField(20);
        inputPanel.add(nomField);

        inputPanel.add(new JLabel("Prénom :"));
        prenomField = new JTextField(20);
        inputPanel.add(prenomField);

        inputPanel.add(new JLabel("Téléphone :"));
        telephoneField = new JTextField(20);
        inputPanel.add(telephoneField);

        inputPanel.add(new JLabel("Réclamation :"));
        reclamationArea = new JTextArea(5, 20);
        reclamationArea.setWrapStyleWord(true);
        reclamationArea.setLineWrap(true);
        inputPanel.add(new JScrollPane(reclamationArea));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        inputPanel.add(new JLabel("Date d'aujourd'hui :"));
        dateField = new JTextField(currentDate);
        dateField.setEditable(false);
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Numéro de ticket :"));
        numeroTicketField = new JTextField(20);
        numeroTicketField.setEditable(false); // Le champ numéro de ticket ne sera pas éditable manuellement
        inputPanel.add(numeroTicketField);

        etatTicketComboBox = new JComboBox<>(new String[]{"Ouvert", "En cours", "Fermé"});
        inputPanel.add(new JLabel("État du ticket :"));
        inputPanel.add(etatTicketComboBox);

        JButton envoyerButton = new JButton("Envoyer");
        envoyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String telephone = telephoneField.getText();
                String reclamation = reclamationArea.getText();
                String date = dateField.getText();
                int numeroTicket = genererNumeroTicketUnique(); // Générez le numéro de ticket aléatoire
                numeroTicketField.setText(String.valueOf(numeroTicket)); // Mettez à jour le champ numéro de ticket
                String etatTicket = etatTicketComboBox.getSelectedItem().toString();

                String[] rowData = {nom, prenom, telephone, reclamation, date, String.valueOf(numeroTicket), etatTicket};
                tableModel.addRow(rowData);
            }
        });
        inputPanel.add(envoyerButton);

        formulairePanel.add(inputPanel, BorderLayout.CENTER);

        String[] columnNames = {"ID", "Nom", "Prénom", "Téléphone", "Réclamation", "Date d'aujourd'hui", "Numéro de ticket", "État du ticket"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Rendre toutes les cellules du tableau non éditables
                return false;
            }
        };
        JTable tableau = new JTable(tableModel);

        JTableHeader header = tableau.getTableHeader();
        tablePanel.add(header, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(tableau);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        formulairePanel.add(tablePanel, BorderLayout.SOUTH);

        add(formulairePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Ajoutez un bouton "Supprimer" à votre inputPanel
        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableau.getSelectedRow();
                if (selectedRow != -1) { // Vérifiez si une ligne est sélectionnée
                    tableModel.removeRow(selectedRow); // Supprimez la ligne sélectionnée
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        inputPanel.add(supprimerButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionClientSav();
            }
        });
    }
}
