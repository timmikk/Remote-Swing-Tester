package testApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App extends JFrame implements ActionListener
{
    private final static Logger LOGGER = Logger.getLogger(App.class.getName());


    JButton button;
    JLabel label;
    public static void main( String[] args )
    {
        LOGGER.info("Starting test application");
        new App().initUi();
    }

    public void initUi() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        button = new JButton("Click me");
        button.addActionListener(this);
        label = new JLabel("");
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(label);
        panel.add(button);
        this.add(panel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(200, 100));
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {
            label.setText(label.getText() + "*");
            System.out.println("Click");
        }
    }
}
