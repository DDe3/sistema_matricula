package com.ing_software.forms;

import com.ing_software.Principal;
import com.ing_software.abstraccion.Case;
import com.ing_software.abstraccion.Effect;
import com.ing_software.abstraccion.Result;
import com.ing_software.entity.Cuenta;
import com.ing_software.servicios.ServicioCuenta;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import java.awt.*;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static com.ing_software.abstraccion.Case.*;
import static com.ing_software.abstraccion.Result.*;


public class Signin extends JFrame {


    ServicioCuenta servicio = Principal.se.select(ServicioCuenta.class).get();
    final Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");


    private JTextField nombre;
    private JButton registrarseButton;
    private JButton regresarButton;
    private JPanel panelSign;
    private JPasswordField pass;
    private JPasswordField passConf;

    CompletableFuture<List<Cuenta>> aux;
    List<String> disponibles;


    public Signin(String title) throws HeadlessException {
        super(title);
        setup();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelSign);
        this.pack();





        registrarseButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nombre.getText();
                String con = pass.getText();
                String conConf = passConf.getText();
                validacion();

                Result<String> result = Case.match(
                        defaultCase( () -> failure("Correo no valido")),
                        mcase( () -> nom.equals(""), ()->failure("Mail no puede ser nulo")),
                        mcase( () -> con.equals(""), ()->failure("Ingrese todos los campos")),
                        mcase( () -> conConf.equals(""), ()->failure("Ingrese todos los campos")),
                        mcase( () -> con.length()<5, ()->failure("La password debe tener al menos 5 digitos")),
                        mcase( () -> !con.equals(conConf), ()->failure("Confirmacion erronea")),
                        mcase( () -> disponibles.contains(nom), ()-> failure("El correo ingreso ya esta registrado")),
                        mcase( () -> emailPattern.matcher(nom).matches(), () -> success("Cuenta registrada con exito"))
                );

                result.bind(    x ->
                {
                    servicio.crearCuenta(nom,con);
                    JOptionPane.showMessageDialog(null, x);
                    goBack();}, x -> JOptionPane.showMessageDialog(null, x));

            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }


    private void setup() {
        aux = servicio.nombresDisponibles();
    }

    private void validacion() throws ExecutionException, InterruptedException {
        disponibles = (List<String>) aux.get().stream()
                .map(Cuenta::getNombre)
                .collect(Collectors.toList());
    }



    private void goBack() {
        JFrame login = new Login("Sistema de informacion");
        login.setVisible(true);
        this.dispose();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelSign = new JPanel();
        panelSign.setLayout(new GridBagLayout());
        nombre = new JTextField();
        nombre.setText("");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 5);
        panelSign.add(nombre, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Correo");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 15, 0, 15);
        panelSign.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 15, 0, 15);
        panelSign.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Confirmar Password");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 15, 0, 15);
        panelSign.add(label3, gbc);
        registrarseButton = new JButton();
        registrarseButton.setText("Registrarse");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        panelSign.add(registrarseButton, gbc);
        regresarButton = new JButton();
        regresarButton.setText("Regresar");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        panelSign.add(regresarButton, gbc);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("JetBrains Mono", Font.PLAIN, 28, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Ingrese sus datos");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panelSign.add(label4, gbc);
        pass = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 5);
        panelSign.add(pass, gbc);
        passConf = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 5);
        panelSign.add(passConf, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelSign;
    }
}
